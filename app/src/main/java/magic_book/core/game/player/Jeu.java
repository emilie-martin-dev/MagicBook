package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import magic_book.core.Book;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItemLink;
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
	private Book book;
	
	public Jeu(BookState state, Book book){
		this.state = state;
		this.book = book;
	}
	
	public void play(){
		player = new Player();
		end = false;
		//bookNode = book.getRootNode();
		this.state.getMainCharacter().addItem("potion");
	/*	BookItemLink armeItemsLink = new BookItemLink("arme", 10, 10, false, 2);
		BookItemLink potionItemsLink = new BookItemLink("potion", 10, 10, false, 2);
		List<BookItemLink> itemLinks = new ArrayList();
		itemLinks.add(armeItemsLink);
		itemLinks.add(potionItemsLink);
		
		BookNodeTerminal bt = new BookNodeTerminal("fin", BookNodeStatus.VICTORY);
		List<BookNodeLink> choices = new ArrayList();
		List<List<AbstractRequirement>> requirements = new ArrayList();
		List<AbstractRequirement> r = new ArrayList();
		RequirementItem ar = new RequirementItem("potion");
		r.add(ar);
		requirements.add(r);
		BookNodeLink bl = new BookNodeLink("C'est un noeud terminal", bt, requirements, 20, 10, false);
		List<BookNodeLink> listbl = new ArrayList();
		listbl.add(bl);
		bookNode = new BookNodeWithChoices("Test", 2, itemLinks, null, listbl);*/
	
		BookItemLink armeItemsLink = new BookItemLink("arme", 10, 10, false, 2);
		BookItemLink potionItemsLink = new BookItemLink("potion", 10, 10, false, 2);
		List<BookItemLink> itemLinks = new ArrayList();
		itemLinks.add(armeItemsLink);
		itemLinks.add(potionItemsLink);
		
		BookNodeTerminal bt = new BookNodeTerminal("fin", BookNodeStatus.VICTORY);
		List<BookNodeLink> choices = new ArrayList();
		List<List<AbstractRequirement>> requirements = new ArrayList();
		List<AbstractRequirement> r = new ArrayList();
		RequirementItem ar = new RequirementItem("potion");
		r.add(ar);
		requirements.add(r);
		BookNodeLink winBookNodeLink = new BookNodeLink("combat win", bt, requirements, 20, 10, false);
		BookNodeLink looseBookNodeLink = new BookNodeLink("combat loose", bt, requirements, 20, 10, false);
		BookNodeLink evasionBookNodeLink = new BookNodeLink("combat evasion", bt, requirements, 20, 10, false);
		List<BookNodeLink> listbl = new ArrayList();
		listbl.add(winBookNodeLink);
		listbl.add(looseBookNodeLink);
		listbl.add(evasionBookNodeLink);
		List<String> ennemiesId = new ArrayList();
		ennemiesId.add("Vache");
		ennemiesId.add("Serpend");
		
		bookNode = new BookNodeCombat("Rentre dans le noeud de combat", winBookNodeLink, looseBookNodeLink, evasionBookNodeLink, 5, ennemiesId, 3, itemLinks, itemLinks, listbl);
	
		while(end == false){
			System.out.println("Rentre dans la while");
			if(bookNode instanceof BookNodeCombat){
				System.out.println("Combat");
				BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
				player.execNodeCombat(bookNodeCombat, state);
			}
			else if(bookNode instanceof BookNodeWithChoices){
				System.out.println("Choix");
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
				player.execNodeWithChoices(bookNodeWithChoices, state);
			}
			else if(bookNode instanceof BookNodeTerminal){
				System.out.println("Term");
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
				player.execNodeTerminal(bookNodeTerminal, state);
				end = true;
			}
			System.out.println("OK");
			this.bookNode = player.getBookNodeChoice();
		}
		System.out.println("Fin");
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
