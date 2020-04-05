package magic_book.core;

import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.book.BookNodeObservable;
import magic_book.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class BookTest extends AbstractTest {

	@Mock
	private BookNodeObservable bookNodeObservable;

	@InjectMocks
	private Book book;

	@Test
	public void getNodeIndex() {
		BookNodeTerminal node = new BookNodeTerminal();
		book.addNode(node);

		Assert.assertEquals("Test index 2", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test index null car noeud pas dans le livre", -1, book.getNodeIndex(new BookNodeTerminal()));

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
	}

	@Test
	public void changeFirstNode_ajoutSansSwap() {
		BookNodeTerminal node = new BookNodeTerminal();

		book.changeFirstNode(node); // 1

		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 1, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 1, book.getNodesInv().size());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
	}

	@Test
	public void changeFirstNode_changementSansSwap() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.addNode(node); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node); // 1

		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertEquals("Test taille index libre", 1, book.getMissingIndexes().size());
		Assert.assertTrue("Test index libre 2", book.getMissingIndexes().get(0) == 2);
	}

	@Test
	public void changeFirstNode_ajoutAvecSwap() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.changeFirstNode(node); // 1
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node2); // 2 -> 1

		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 3, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
	}

	@Test
	public void changeFirstNode_changementDestinations() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeWithChoices node3 = new BookNodeWithChoices();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink2_3 = new BookNodeLink();
		nodeLink2_3.setDestination(3);

		BookNodeLink nodeLink3_4 = new BookNodeLink();
		nodeLink3_4.setDestination(4);

		BookNodeLink nodeLink3_2 = new BookNodeLink();
		nodeLink3_2.setDestination(2);

		BookNodeLink nodeLink3_2_2 = new BookNodeLink();
		nodeLink3_2_2.setDestination(2);

		BookNodeLink nodeLink4_3 = new BookNodeLink();
		nodeLink4_3.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink2_3, node2);
		book.addNodeLink(nodeLink3_4, node3);
		book.addNodeLink(nodeLink3_2, node3);
		book.addNodeLink(nodeLink3_2_2, node3);
		book.addNodeLink(nodeLink4_3, node4);

		book.changeFirstNode(node2); // 2 -> 1

		// 2 -> 1
		// 3 -> 3
		// 4 -> 4

		Assert.assertEquals("Test destination nodeLink2_3", 3, nodeLink2_3.getDestination());
		Assert.assertEquals("Test destination nodeLink3_4", 4, nodeLink3_4.getDestination());
		Assert.assertEquals("Test destination nodeLink3_2", 1, nodeLink3_2.getDestination());
		Assert.assertEquals("Test destination nodeLink3_2_2", 1, nodeLink3_2_2.getDestination());
		Assert.assertEquals("Test destination nodeLink4_3", 3, nodeLink4_3.getDestination());

		book.changeFirstNode(node3); // 3 -> 1

		// 2 -> 3
		// 3 -> 1
		// 4 -> 4

		Assert.assertEquals("Test destination nodeLink2_3", 1, nodeLink2_3.getDestination());
		Assert.assertEquals("Test destination nodeLink3_4", 4, nodeLink3_4.getDestination());
		Assert.assertEquals("Test destination nodeLink3_2", 3, nodeLink3_2.getDestination());
		Assert.assertEquals("Test destination nodeLink3_2_2", 3, nodeLink3_2_2.getDestination());
		Assert.assertEquals("Test destination nodeLink4_3", 1, nodeLink4_3.getDestination());
	}

	@Test
	public void changeFirstNode_changementAvecSwap() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.addNode(node); // 2
		book.changeFirstNode(node); // 2 -> 1
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node2); // 2 -> 1

		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 3, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
	}

	@Test
	public void changeFirstNode_ajoutSuccessifAvecSwap() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();

		book.changeFirstNode(node); // 1
		book.changeFirstNode(node2); // 1

		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
	}

	@Test
	public void addNode_normal() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		// Vérifie l'ajout simple de 2 noeuds
		book.addNode(node2); // 2
		book.addNode(node3); // 3

		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
	}

	@Test
	public void addNode_missingIndexByDelete() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeTerminal node4 = new BookNodeTerminal();

		// Vérifie l'ajout avec un index manquant
		int missingIndex = 2;

		book.addNode(node); // 2
		book.addNode(node3); // 3
		book.removeNode(node); // X
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().size() == 1);
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().get(0) == missingIndex);
		book.addNode(node2); // 2
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
		book.addNode(node4); // 4

		Assert.assertTrue("Test index " + missingIndex + " - normal", book.getNodes().get(missingIndex) == node2);
		Assert.assertEquals("Test index " + missingIndex + " - inv", missingIndex, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 4 - normal", book.getNodes().get(4) == node4);
		Assert.assertEquals("Test index 4 - inv", 4, book.getNodeIndex(node4));
		Assert.assertEquals("Test taille - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 3, book.getNodesInv().size());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node4);
	}

	@Test
	public void addNode_missingIndexByFirstNode() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeTerminal node4 = new BookNodeTerminal();

		// Vérifie l'ajout avec un index manquant
		int missingIndex = 2;

		book.addNode(node); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node); // 1
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().size() == 1);
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().get(0) == missingIndex);
		book.addNode(node2); // 2
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
		book.addNode(node4); // 4

		Assert.assertTrue("Test index " + missingIndex + " - normal", book.getNodes().get(missingIndex) == node2);
		Assert.assertEquals("Test index " + missingIndex + " - inv", missingIndex, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 4 - normal", book.getNodes().get(4) == node4);
		Assert.assertEquals("Test index 4 - inv", 4, book.getNodeIndex(node4));
		Assert.assertEquals("Test taille - normal", 4, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 4, book.getNodesInv().size());


		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node4);
	}

	@Test
	public void addNode_checkOffset() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();

		// Vérifie si un noeud "1" existe, si un ajout sera bien en "2"

		book.changeFirstNode(node); // 1
		book.addNode(node2); // 2

		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
	}

	@Test
	public void updateNode_ajout() {
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeTerminal trashNode = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();

		book.addNode(node2);
		book.updateNode(trashNode, node3);

		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertFalse("Test contains trashNode - normal", book.getNodes().containsValue(trashNode));
		Assert.assertEquals("Test index trashNode - inv", -1, book.getNodeIndex(trashNode));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
	}

	@Test
	public void updateNode_update() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal trashNode = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.addNode(trashNode);
		book.addNode(node3);
		book.updateNode(trashNode, node2);

		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertFalse("Test contains trashNode - normal", book.getNodes().containsValue(trashNode));
		Assert.assertEquals("Test index trashNode - inv", -1, book.getNodeIndex(trashNode));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable).notifyNodeEdited(trashNode, node2);
	}

	@Test
	public void removeNode_exist() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.addNode(node2);
		book.addNode(node3);
		book.removeNode(node2);

		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == null);
		Assert.assertEquals("Test index 2 - inv", -1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertEquals("Test taille - normal", 1, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 1, book.getNodesInv().size());
		Assert.assertEquals("Test taille index libre", 1, book.getMissingIndexes().size());
		Assert.assertTrue("Test taille valeur libre", book.getMissingIndexes().get(0) == 2);

		Mockito.verify(bookNodeObservable).notifyNodeDeleted(node2);
	}

	@Test
	public void removeNode_doesntExist() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();

		book.addNode(node2);
		book.removeNode(node3);

		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == null);
		Assert.assertEquals("Test index 3 - inv", -1, book.getNodeIndex(node3));
		Assert.assertEquals("Test taille - normal", 1, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 1, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());

		Mockito.verify(bookNodeObservable, Mockito.never()).notifyNodeDeleted(node3);
	}

	@Test
	public void addNodeLink() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink = new BookNodeLink();
		nodeLink.setDestination(3);

		BookNodeLink nodeLink4 = new BookNodeLink();
		nodeLink4.setDestination(2);

		BookNodeLink nodeLink4_2 = new BookNodeLink();
		nodeLink4_2.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink, node2);
		book.addNodeLink(nodeLink4, node4);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4);

		book.addNodeLink(nodeLink4_2, node4);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 2, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(1) == nodeLink4_2);
	}

	@Test
	public void removeNode_removeNodeLinks() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink2 = new BookNodeLink();
		nodeLink2.setDestination(3);

		BookNodeLink nodeLink4 = new BookNodeLink();
		nodeLink4.setDestination(4);

		BookNodeLink nodeLink4_2 = new BookNodeLink();
		nodeLink4_2.setDestination(2);

		BookNodeLink nodeLink4_2_2 = new BookNodeLink();
		nodeLink4_2_2.setDestination(2);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink2, node2);
		book.addNodeLink(nodeLink4, node4);
		book.addNodeLink(nodeLink4_2, node4);
		book.addNodeLink(nodeLink4_2_2, node4);

		book.removeNode(node2);

		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4);
	}

	@Test
	public void updateNodeLink_exits() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink = new BookNodeLink();
		nodeLink.setDestination(3);

		BookNodeLink nodeLink4_2 = new BookNodeLink();
		nodeLink4_2.setDestination(3);

		BookNodeLink trashNodeLink = new BookNodeLink();
		trashNodeLink.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink, node2);
		book.addNodeLink(trashNodeLink, node4);

		book.updateNodeLink(trashNodeLink, nodeLink4_2);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4_2);
	}

	@Test
	public void updateNodeLink_doesntExists() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink = new BookNodeLink();
		nodeLink.setDestination(3);

		BookNodeLink nodeLink4_2 = new BookNodeLink();
		nodeLink4_2.setDestination(3);

		BookNodeLink trashNodeLink = new BookNodeLink();
		trashNodeLink.setDestination(3);

		BookNodeLink trashNodeLink2 = new BookNodeLink();
		trashNodeLink2.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink, node2);
		book.addNodeLink(nodeLink4_2, node4);

		book.updateNodeLink(trashNodeLink, nodeLink4_2);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4_2);

		book.updateNodeLink(trashNodeLink, trashNodeLink2);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4_2);
	}

	@Test
	public void removeNodeLink_exits() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink = new BookNodeLink();
		nodeLink.setDestination(3);

		BookNodeLink nodeLink4 = new BookNodeLink();
		nodeLink4.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink, node2);
		book.addNodeLink(nodeLink4, node4);

		book.removeNodeLink(nodeLink4);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertTrue("Test taille choices - node4", book.getNodes().get(4).getChoices().isEmpty());
	}

	@Test
	public void removeNodeLink_doesntExits() {
		BookNodeWithChoices node2 = new BookNodeWithChoices();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeWithChoices node4 = new BookNodeWithChoices();

		BookNodeLink nodeLink2 = new BookNodeLink();
		nodeLink2.setDestination(3);

		BookNodeLink nodeLink4 = new BookNodeLink();
		nodeLink4.setDestination(3);

		BookNodeLink trashNodeLink = new BookNodeLink();
		trashNodeLink.setDestination(3);

		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.addNode(node4); // 4

		book.addNodeLink(nodeLink2, node2);
		book.addNodeLink(nodeLink4, node4);

		book.removeNodeLink(trashNodeLink);

		Assert.assertEquals("Test taille choices - node2", 1, book.getNodes().get(2).getChoices().size());
		Assert.assertTrue("Test choice - node2", book.getNodes().get(2).getChoices().get(0) == nodeLink2);
		Assert.assertTrue("Test taille choices - node3", book.getNodes().get(3).getChoices().isEmpty());
		Assert.assertEquals("Test taille choices - node4", 1, book.getNodes().get(4).getChoices().size());
		Assert.assertTrue("Test choice - node4", book.getNodes().get(4).getChoices().get(0) == nodeLink4);
	}

	/*
	public void addItem() {
	}

	public void updateItem() {
	}

	public void removeItem() {
	}

	public void addCharacter() {
	}

	public void updateCharacter() {
	}

	public void removeCharacter() {
	}

	public void addSkill() {
	}

	public void updateSkill() {
	}

	public void removeSkill() {
	}

	*/

}
