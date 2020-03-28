package magic_book.core.game.player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.Alert;
import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.BookReader;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.item.BookItemLink;
import org.junit.Assert;
import org.junit.Test;

public class JeuTest {
	@Test
	public void runGame(){
		//Variables
		Book book;
		float victoire;
		List<BookNodeLink> listAbstractBookNode ;
		BookNodeWithChoices bookNodeWithChoices;
		HashMap<Integer, AbstractBookNode> nodes;
		Jeu jeu;
		
		//Création des Noeuds
		BookNodeTerminal bookNodeTerminalVictory = new BookNodeTerminal("C'est un noeud de victoire", BookNodeStatus.VICTORY);
		BookNodeTerminal bookNodeTerminalFailure = new BookNodeTerminal("C'est un noeud de défaite", BookNodeStatus.FAILURE);
		
		BookNodeLink bookNodeLink1 = new BookNodeLink("C'est le bookNodeLink", 2);
		BookNodeLink bookNodeLink2 = new BookNodeLink("C'est le bookNodeLink", 3);
		
		//1er test : noeud Terminal Failure
		listAbstractBookNode = new ArrayList();
		listAbstractBookNode.add(bookNodeLink1);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes = new HashMap();
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalFailure);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire == 0);
		
		//2eme test : noeud Terminal Victoire
		nodes.put(1, bookNodeWithChoices);
		nodes.put(2, bookNodeTerminalVictory);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire == 100);
		
		//3eme test : noeud Terminal Failure et Victoire
		listAbstractBookNode.add(bookNodeLink2);
		
		bookNodeWithChoices = new BookNodeWithChoices("", 0, null, null, listAbstractBookNode);
		
		nodes.put(3, bookNodeTerminalFailure);
		
		book = new Book();
		book.setNodes(nodes);
		jeu = new Jeu(book);
		victoire = jeu.fourmis(10000);
		Assert.assertTrue(victoire > 49 && victoire < 51);
	}
}
