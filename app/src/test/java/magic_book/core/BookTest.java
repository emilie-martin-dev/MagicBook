package magic_book.core;

import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.observer.book.BookNodeObservable;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookTest {
	
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
	}
	
	@Test
	public void changeFirstNode_ajoutSansSwap() {
		BookNodeTerminal node = new BookNodeTerminal();
		
		book.changeFirstNode(node); // 1
		
		Assert.assertTrue("Test index 1 - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 1, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 1, book.getNodesInv().size());
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
	}
	
	@Test
	public void addNode_normal() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		
		// Vérifie l'ajout simple de 2 noeuds
		book.addNode(node2); // 2
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
		
		book.addNode(node3); // 3
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
		
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
	}
	
	@Test
	public void addNode_missingIndex() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeTerminal node4 = new BookNodeTerminal();
		
		// Vérifie l'ajout avec un index manquant
		int missingIndex = 2;
		
		book.addNode(node); // 2
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		book.addNode(node3); // 3
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node3);
		book.changeFirstNode(node); // 1
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().size() == 1);
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().get(0) == missingIndex);
		book.addNode(node2); // 2
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node2);
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
		book.addNode(node4); // 4
		
		Assert.assertTrue("Test index " + missingIndex + " - normal", book.getNodes().get(missingIndex) == node2);
		Assert.assertEquals("Test index " + missingIndex + " - inv", missingIndex, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 4 - normal", book.getNodes().get(4) == node4);
		Assert.assertEquals("Test index 4 - inv", 4, book.getNodeIndex(node4));
		Assert.assertEquals("Test taille - normal", 4, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 4, book.getNodesInv().size());
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
	}
	
	@Test
	public void updateNode_ajout() {
		BookNodeTerminal node3 = new BookNodeTerminal();
		BookNodeTerminal trashNode = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		
		book.addNode(node2);
		book.updateNode(trashNode, node3);
		//Mockito.verify(bookNodeObservable).notifyNodeAdded(node);
		
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertFalse("Test contains trashNode - normal", book.getNodes().containsValue(trashNode));
		Assert.assertEquals("Test index trashNode - inv", -1, book.getNodeIndex(trashNode));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
	}
	
	@Test	
	public void updateNode_update() {
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal trashNode = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		
		book.addNode(trashNode);
		book.addNode(node3);
		book.updateNode(trashNode, node2);
		//Mockito.verify(bookNodeObservable).notifyNodeEdited(trashNode, node2);
		
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertFalse("Test contains trashNode - normal", book.getNodes().containsValue(trashNode));
		Assert.assertEquals("Test index trashNode - inv", -1, book.getNodeIndex(trashNode));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodesInv().size());
		Assert.assertTrue("Test taille index libre", book.getMissingIndexes().isEmpty());
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
	}
	
	/*
	public void addNodeLink(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
	}
	
	public void updateNodeLink(BookNodeLink oldBookNodeLink, BookNodeLink newBookNode) {
	}
	
	public void removeNodeLink(BookNodeLink nodeLink) {
	}
	*/

}
