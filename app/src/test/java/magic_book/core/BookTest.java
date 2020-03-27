package magic_book.core;

import magic_book.core.graph.node.BookNodeTerminal;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BookTest {
	
	@Test	
	public void getNodeIndex() {
		Book book = new Book();
		
		BookNodeTerminal node = new BookNodeTerminal();
		book.addNode(node);
		
		Assert.assertEquals("Test index 2", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test index null car noeud pas dans le livre", -1, book.getNodeIndex(new BookNodeTerminal()));
	}
	
	@Test
	public void changeFirstNode() {		
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		
		// Test avec un livre sans premier noeud en ajout direct
		Book book = new Book();
		book.changeFirstNode(node); // 1
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		Assert.assertTrue("Test index 1 par ajout - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 par ajout - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille par ajout - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille par ajout - inv", 3, book.getNodes().size());
		Assert.assertTrue("Test taille index libre ajout", book.getMissingIndexes().isEmpty());
		
		// Test avec un livre sans premier noeud par changement
		book = new Book();
		book.addNode(node); // 2
		book.addNode(node2); // 3
		book.addNode(node3); // 4
		book.changeFirstNode(node); // 1
		Assert.assertTrue("Test index 1 par changement - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 1 par changement - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille par changement - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille par changement - inv", 3, book.getNodes().size());
		Assert.assertEquals("Test taille index libre changement", 1, book.getMissingIndexes().size());
		Assert.assertTrue("Test index libre 2 changement", book.getMissingIndexes().get(0) == 2);
		
		// Test avec un livre qui contient un premier noeud en ajout direct
		book = new Book();
		book.changeFirstNode(node); // 1
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node2); // 2 -> 1 
		Assert.assertTrue("Test index 1 par ajout - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 par ajout - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 par ajout - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 par ajout - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille par ajout - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille par ajout - inv", 3, book.getNodes().size());
		Assert.assertTrue("Test taille index libre ajout", book.getMissingIndexes().isEmpty());
		
		// Test avec un livre qui contient un premier premier noeud par changement
		book = new Book();
		book.changeFirstNode(node); // 1
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		book.changeFirstNode(node2); // 2 -> 1 
		Assert.assertTrue("Test index 1 par changement - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 par changement - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 par changement - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 par changement - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille par changement - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille par changement - inv", 3, book.getNodes().size());
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().isEmpty());
		
		// Test un livre où l'on change successivement le premier noeud par des noeuds non ajoutés
		book = new Book();
		book.changeFirstNode(node); // 1
		book.changeFirstNode(node2); // 2
		Assert.assertTrue("Test index 1 par changement - normal", book.getNodes().get(1) == node2);
		Assert.assertEquals("Test index 1 par changement - inv", 1, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 2 par changement - normal", book.getNodes().get(2) == node);
		Assert.assertEquals("Test index 2 par changement - inv", 2, book.getNodeIndex(node));
		Assert.assertEquals("Test taille par changement - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille par changement - inv", 2, book.getNodes().size());
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().isEmpty());
	}
	
	@Test
	public void addNode() {
		BookNodeTerminal node = new BookNodeTerminal();
		BookNodeTerminal node2 = new BookNodeTerminal();
		BookNodeTerminal node3 = new BookNodeTerminal();
		
		// Vérifie l'ajout simple de 2 noeuds
		Book book = new Book();
		book.addNode(node2); // 2
		book.addNode(node3); // 3
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodes().size());
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().isEmpty());
		
		// Vérifie l'ajout avec un index manquant
		int missingIndex = 25;
		book.getMissingIndexes().add(missingIndex);
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().size() == 1);
		
		book.addNode(node); // 25
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(3) == node3);
		Assert.assertEquals("Test index 3 - inv", 3, book.getNodeIndex(node3));
		Assert.assertTrue("Test index " + missingIndex + " - normal", book.getNodes().get(missingIndex) == node);
		Assert.assertEquals("Test index " + missingIndex + " - inv", missingIndex, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 3, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 3, book.getNodes().size());
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().isEmpty());
		
		// Vérifie si un noeud "1" existe, si un ajout sera bien en "2"
		book = new Book();
		book.changeFirstNode(node); // 1 
		book.addNode(node2); // 2
		Assert.assertTrue("Test index 2 - normal", book.getNodes().get(2) == node2);
		Assert.assertEquals("Test index 2 - inv", 2, book.getNodeIndex(node2));
		Assert.assertTrue("Test index 3 - normal", book.getNodes().get(1) == node);
		Assert.assertEquals("Test index 3 - inv", 1, book.getNodeIndex(node));
		Assert.assertEquals("Test taille - normal", 2, book.getNodes().size());
		Assert.assertEquals("Test taille - inv", 2, book.getNodes().size());
		Assert.assertTrue("Test taille index libre changement", book.getMissingIndexes().isEmpty());
	}
	
	/*
	public void updateNode(AbstractBookNode oldNode, AbstractBookNode newNode) {
	}
	
	public void removeNode(AbstractBookNode node) {
	}
	
	public void addNodeLink(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
	}
	
	public void updateNodeLink(BookNodeLink oldBookNodeLink, BookNodeLink newBookNode) {
	}
	
	public void removeNodeLink(BookNodeLink nodeLink) {
	}
	*/

}
