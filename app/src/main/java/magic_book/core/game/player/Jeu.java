package magic_book.core.game.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Alert;
import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.BookReader;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.window.gui.NodeFx;


public class Jeu {
	
	private BookState state;
	private List<NodeFx> listeNoeud;
	private Player player;
	private Fourmi fourmi;
	private AbstractBookNode bookNode;
	private boolean end;
	private int victoire;
	private int defaite;
	private Book book;
	
	
	public Jeu(BookState state, Book book){
		this.state = state;
		this.book = book;

	}
	
	public void play(){
		BookState statePlayer = getState();

		bookNode = book.getRootNode();

		player = new Player(statePlayer, book.getItems(), book.getCharacters());
		end = false;

		System.out.println(book.getTextPrelude());
		while(end == false){
			if(bookNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
				player.execNodeCombat(bookNodeCombat);
			}
			else if(bookNode instanceof BookNodeWithChoices){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
				player.execNodeWithChoices(bookNodeWithChoices);
				
			}
			else if(bookNode instanceof BookNodeWithRandomChoices){
				BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNode;
				player.execNodeWithRandomChoices(bookNodeWithRandomChoices);
			}
			else if(bookNode instanceof BookNodeTerminal){
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
				player.execNodeTerminal(bookNodeTerminal);
				end = true;
			}

			this.bookNode = player.getBookNodeChoice();
		}
	}
	
	public float fourmis(int nbrFourmis){
		victoire = 0;
		for ( int i = 0 ; i < nbrFourmis ; i++){

			end = false;
			
			bookNode = book.getRootNode();
			
			BookState stateFourmis = getState();
			
			if(stateFourmis != null)
				fourmi = new Fourmi(stateFourmis, book.getItems(), book.getCharacters());
			 else 
				fourmi = new Fourmi(book.getItems(), book.getCharacters());
			
			while(end == false){
				if(bookNode instanceof BookNodeCombat){
					BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
					fourmi.execNodeCombat(bookNodeCombat);
				}
				else if(bookNode instanceof BookNodeWithChoices){
					BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
					fourmi.execNodeWithChoices(bookNodeWithChoices);
				}
				else if(bookNode instanceof BookNodeWithRandomChoices){
					BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNode;
					fourmi.execNodeWithRandomChoices(bookNodeWithRandomChoices);
				}
				else if(bookNode instanceof BookNodeTerminal){
					BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
					fourmi.execNodeTerminal(bookNodeTerminal);
					this.victoire += fourmi.getVictoire();
					end = true;
				}
				this.bookNode = fourmi.getBookNodeChoice();
			}
		}
		return ((float)victoire / (float)nbrFourmis) * 100f;
	}
	
	private BookState getState(){
		BookCharacter bookCharacter = new BookCharacter("Test", "Personnage Test", 3, 50, null, null, null, 5, true);
		BookState getState = new BookState();
		getState.setMainCharacter(bookCharacter);
		return getState;
	}
}
