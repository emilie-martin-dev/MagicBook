package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import magic_book.core.Book;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.window.gui.NodeFx;


public class Jeu {
	
	private BookState state;
	private List<NodeFx> listeNoeud;
	private Player player = new Player();
	private Fourmi fourmi;
	private AbstractBookNode bookNode;
	private boolean end;
	private int victoire;
	private Book book;
	
	public Jeu(BookState state, Book book){
		this.state = state;
		this.book = book;
	}
	
	public void play(){
		end = false;
		AbstractBookNode bookNode = listeNoeud.get(0).getNode();
		System.out.println(bookNode.getText());
		while(end = false){
			if(bookNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
				player.execNodeCombat(bookNodeCombat, state);
			}
			else if(bookNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
				player.execNodeCombat(bookNodeCombat, state);
			}
			else if(bookNode instanceof BookNodeWithChoices){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
				player.execNodeWithChoices(bookNodeWithChoices, state);
			}
			else if(bookNode instanceof BookNodeTerminal){
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
				player.execNodeTerminal(bookNodeTerminal, state);
				end = true;
			}
			this.bookNode = player.getBookNodeChoice();
		}
	}
	
	public float fourmis(int nbrFourmis){
		victoire = 0;
		List<Integer> listChoicesRandomChances = new ArrayList();
		for ( int i = 0 ; i < nbrFourmis ; i++){
			end = false;
			bookNode = book.getRootNode();
			System.out.println("for "+i);
			fourmi = new Fourmi();
			fourmi.execBookState();
			int r = 0;
			while(end == false ){
				System.out.println("Node WithChoice "+i);
				if(bookNode instanceof BookNodeCombat){
					BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
					fourmi.execNodeCombat(bookNodeCombat, state);
				}
				else if(bookNode instanceof BookNodeWithChoices){
					List<BookNodeLinkRandom> listChoices = new ArrayList();
					for(int y = 0; y < bookNode.getChoices().size() ; y++){
						BookNodeLink bookNodeLink = bookNode.getChoices().get(y);
						Random random = new Random();
						if(i == 0){
							r = random.nextInt(10);
							listChoicesRandomChances.add(r);
						} else {
							r = listChoicesRandomChances.get(y);
						}
						BookNodeLinkRandom bookNodeLinkRandom = new BookNodeLinkRandom(bookNodeLink.getText(), bookNodeLink.getDestination(), bookNodeLink.getRequirements(), r);
						listChoices.add(bookNodeLinkRandom);
						if(i == 0)
							listChoicesRandomChances.add(r);
					}
					System.out.println("Sort liste "+i);
					BookNodeWithRandomChoices bookNodeWithRandomChoices = new BookNodeWithRandomChoices(bookNode.getText(), ((BookNodeWithChoices) bookNode).getNbItemsAPrendre(),
							((BookNodeWithChoices) bookNode).getItemLinks(), ((BookNodeWithChoices) bookNode).getShopItemLinks(), listChoices);
					fourmi.execNodeWithRandomChoices(bookNodeWithRandomChoices, state);
				}
				else if(bookNode instanceof BookNodeTerminal){
					System.out.println("Node terminal "+i);
					BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
					fourmi.execNodeTerminal(bookNodeTerminal, state);
					end = true;
				}
				this.bookNode = fourmi.getBookNodeChoice();
			}
			victoire += fourmi.getVictoire();
		}
		System.out.println("victoire : "+victoire+" nbr fourmis : "+nbrFourmis+ "  %  "+((float)victoire / (float)nbrFourmis) * 100f);
		
		return ((float)victoire / (float)nbrFourmis) * 100f;
	}
}
