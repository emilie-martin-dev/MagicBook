package magic_book.core.game.player;

import java.util.List;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.window.gui.NodeFx;


public class Jeu {
	
	private BookState state;
	private List<NodeFx> listeNoeud;
	private Player player = new Player();
	private Fourmi fourmi;
	private AbstractBookNode bookNode;
	private boolean end;
	private int victoire;
	
	public Jeu(BookState state, List<NodeFx> listeNoeud){
		play();
	}
	
	private void play(){
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
		end = false;
		AbstractBookNode bookNode = listeNoeud.get(0).getNode();
		System.out.println(bookNode.getText());
		for ( int i = 0 ; i < nbrFourmis ; i++){
			fourmi = new Fourmi();
			fourmi.execBookState();
			while(end = false){
				if(bookNode instanceof BookNodeCombat){
					BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
					fourmi.execNodeCombat(bookNodeCombat, state);
				}
				else if(bookNode instanceof BookNodeCombat){
					BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
					fourmi.execNodeCombat(bookNodeCombat, state);
				}
				else if(bookNode instanceof BookNodeWithChoices){
					BookNodeWithRandomChoices BookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNode;
					fourmi.execNodeWithRandomChoices(BookNodeWithRandomChoices, state);
				}
				else if(bookNode instanceof BookNodeTerminal){
					BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
					player = new Player();
					player.execNodeTerminal(bookNodeTerminal, state);
					end = true;
				}
				this.bookNode = player.getBookNodeChoice();
			}
			victoire += fourmi.getVictoire();
		}
		return ((float)victoire / (float)nbrFourmis) * 100f;
	}
}
