package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import magic_book.core.Book;
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
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;

public class Jeu {
	
	private BookState state;
	private Player player;
	private Fourmi fourmi;
	private AbstractBookNode bookNode;
	private boolean end;
	private int victoire;
	private Book book;
	private boolean statePlay;
	private boolean mort;
	
	private int str;
	private int destination;
	private int doubleDamage;
	private boolean finCombat;
	
	private BookItemWeapon bookItemArme;
	private BookItemDefense bookItemDefense;
	
	public Jeu(BookState state, Book book){
		this.state = state;
		this.book = book;
	}
	
	public void play(){
		BookState statePlayer = getState();
		this.state = getState();
		bookNode = book.getRootNode();

		player = new Player(statePlayer, book.getItems(), book.getCharacters());
		
		end = false;
		statePlay = true;
		
		System.out.println(book.getTextPrelude());
		
		while(end == false){
			if(bookNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
				execNodeCombat(bookNodeCombat);
			}
			else if(bookNode instanceof BookNodeWithChoices){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
				execNodeWithChoices(bookNodeWithChoices);
			}
			else if(bookNode instanceof BookNodeWithRandomChoices){
				BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNode;
				execNodeWithRandomChoices(bookNodeWithRandomChoices);
			}
			else if(bookNode instanceof BookNodeTerminal){
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
				execNodeTerminal(bookNodeTerminal);		
				end = true;
			}

			bookNode = book.getNodes().get(destination);
		}
		statePlay = false;
	}
	
	public float fourmis(int nbrFourmis){
		victoire = 0;
		for ( int i = 0 ; i < nbrFourmis ; i++){

			end = false;
			
			bookNode = book.getRootNode();
			this.state = getState();
			BookState stateFourmis = getState();
			
			if(stateFourmis != null)
				fourmi = new Fourmi(stateFourmis, book.getItems(), book.getCharacters());
			 else 
				fourmi = new Fourmi(getState(), book.getItems(), book.getCharacters());
			
			
			while(end == false){
				if(bookNode instanceof BookNodeCombat){
					BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNode;
					execNodeCombat(bookNodeCombat);
				}
				else if(bookNode instanceof BookNodeWithChoices){
					BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNode;
					execNodeWithChoices(bookNodeWithChoices);
				}
				else if(bookNode instanceof BookNodeWithRandomChoices){
					BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) bookNode;
					execNodeWithRandomChoices(bookNodeWithRandomChoices);
				}
				else if(bookNode instanceof BookNodeTerminal){
					BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) bookNode;
					execNodeTerminal(bookNodeTerminal);
					end = true;
				}
				this.victoire += fourmi.getVictoire();
				bookNode = book.getNodes().get(destination);
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
	
	
	public void execNodeWithChoices(BookNodeWithChoices node){
		verifGetNodeHp(node);
		if(mort == true){
			BookNodeTerminal nodeTerminal = new BookNodeTerminal();
			execNodeTerminal(nodeTerminal);
		} else {
			if (statePlay)
				System.out.println(node.getText());
			
			int lienValide = 0;
			for (BookNodeLink bookNodeLink : node.getChoices()){
				if(bookNodeLink.isAvailable(state))
					lienValide += 1;
			}

			if (lienValide != 0){
				boolean choixValide = false;

				verifGetNodeItem(node);
				
				if (statePlay){
					System.out.println("Voici vos choix : ");
					for (BookNodeLink bookNodeLink : node.getChoices()){
						System.out.println("- "+bookNodeLink.getText());
					}
					System.out.println();
				}
				
				choixValide = false;
				while (choixValide == false){
					
					if(statePlay){
						System.out.println("Que choisissez-vous ?");
						str = player.stateChoices();
					} else{
						str = fourmi.fourmisChoices(node.getChoices().size());
					}
					if(str <= (node.getChoices().size()-1) && str >= 0){
						if(node.getChoices().get(str).isAvailable(state)){
							destination = node.getChoices().get(str).getDestination();
							choixValide = true;
						} else {
							if(statePlay){
								for(List<AbstractRequirement> abstractRequirements : node.getChoices().get(str).getRequirements()) {
									for(AbstractRequirement requirement : abstractRequirements) {
										System.out.println(requirement.getDescription(book));
									}
								}
							}
						}
						
						verifGetChoicesItem(node, str);
					}
					else {
						if(statePlay)
							System.out.println("vous ne pouvez pas effectuer ce choix");
					}
				}
			}else {
				BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items adéquats", BookNodeStatus.FAILURE);
				execNodeTerminal(bookNodeTerminalFail);
			}
		}
	}
	

	public void execNodeCombat(BookNodeCombat node){
		this.mort = false;
		int evasionRound = node.getEvasionRound();
		int str = 0;
		boolean finCombat = false;
		List<BookCharacter> listEnnemis=  new ArrayList();

		if(statePlay){
			System.out.println(node.getText());
			System.out.println("Il y a "+node.getEnnemiesId().size() + " ennemies !");
		}
		
		for(String ennemieNode : node.getEnnemiesId()){
			listEnnemis.add(book.getCharacters().get(ennemieNode));
		}
		
		
		while(finCombat == false){
			
			if (statePlay)
				str = player.execNodeCombat(evasionRound);
			else
				str = fourmi.execNodeCombat(listEnnemis.size());
			
			if (str == 0)
				attaque(listEnnemis);
			finCombat = this.finCombat;
			if (str == 2)
				evasion(listEnnemis, evasionRound);
			finCombat = this.finCombat;
			ennemiTour(listEnnemis);
			finCombat = this.finCombat;
			evasionRound -= 1;
		}

		if(mort == false && str != 2){
			destination = node.getWinBookNodeLink().getDestination();
		} else if(mort == false && str == 2){
			destination = node.getEvasionBookNodeLink().getDestination();
		} else {
			destination = node.getLooseBookNodeLink().getDestination();
		}
	}

	private void attaque(List<BookCharacter> listEnnemis){
		for(BookCharacter ennemi : listEnnemis){
			int attaque = 0;
			if(bookItemArme != null)
				attaque = bookItemArme.getDamage();
			doubleDamage = 1;
			if(state.getMainCharacter().isDoubleDamage()){
				Random random = new Random();
				int r = random.nextInt(5);
				if (r > 3){
					if (statePlay)
						System.out.println("Coup critique !");
					doubleDamage = 2;
				}
			}
			ennemi.damage(state.getMainCharacter().getBaseDamage()*doubleDamage+attaque);
			if(ennemi.getHp()<=0){
				if (statePlay)
					System.out.println(ennemi+" est mort");
				listEnnemis.remove(ennemi);
				break;
			} else {
				if (statePlay)
					System.out.println(ennemi+" a "+ennemi.getHp()+" hp");
			}
		}
		if(listEnnemis.isEmpty())
			this.finCombat = true;
		if(bookItemArme != null){
			/*bookItemArme.setDurability(bookItemArme.getDurability()-1);
			if(bookItemArme.getDurability()<=0){
				state.getMainCharacter().getItems().remove(bookItemArme.getId());
				System.out.println("La durabilité de l'arme "+bookItemArme.getName()+"est arrivé à terme");
				System.out.println("Arme détruite");
			}
			mapBookItem.put(bookItemArme.getId(), bookItemArme);*/
			bookItemArme = null;
		}

}
	private void evasion(List<BookCharacter> listEnnemis, int evasionRound){
		if ( evasionRound <= 0){
			this.finCombat = true;
		} else if (evasionRound > 0){
			if (statePlay)
				System.out.println("vous ne pouvez pas encore vous evader");
		}
	}
	
	private void ennemiTour(List<BookCharacter> listEnnemis){
		for(BookCharacter ennemi : listEnnemis){
			doubleDamage = 1;
			if(ennemi.isDoubleDamage()){
				Random random = new Random();
				int r = random.nextInt(5);
				if (r > 3){
					if (statePlay)
						System.out.println("Coup critique de "+ennemi+" !");
					doubleDamage = 2;
				}
			}
			int resistance = 0;
			if(bookItemDefense != null)
				resistance = bookItemDefense.getResistance();

			state.getMainCharacter().damage(ennemi.getBaseDamage()*doubleDamage-resistance);
			if (statePlay)
				System.out.println(ennemi + " a attaquer, il vous reste" + state.getMainCharacter().getHp()+" hp");
			if(state.getMainCharacter().getHp() <= 0){
				mort = true;
				this.finCombat = true;
			}
			/*if(bookItemDefense != null){
			bookItemDefense.setDurability(bookItemDefense.getDurability()-1);
			if(bookItemDefense.getDurability()<=0){
				state.getMainCharacter().getItems().remove(bookItemDefense.getId());
				System.out.println("La durabilité de l'arme "+bookItemDefense.getName()+"est arrivé à terme");
				System.out.println("Arme détruite");
			}
			mapBookItem.put(bookItemDefense.getId(), bookItemDefense);
			bookItemDefense = null;*/
		}
	}


	public void execNodeWithRandomChoices(BookNodeWithRandomChoices node) {
		if(statePlay)
			System.out.println(node.getText());
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		destination = randomChoices.getDestination();
	}

	public void execNodeTerminal(BookNodeTerminal node) {
		if(statePlay)
			player.execNodeTerminal(node);
		else
			fourmi.execNodeTerminal(node);
		end = true;
	}
	
	private void verifGetNodeHp(AbstractBookNode node){
		this.mort = false;
		int getNodehp = 0;
		if(node instanceof BookNodeWithChoices){
			BookNodeWithChoices bookNodeGet = (BookNodeWithChoices) node;
			getNodehp = bookNodeGet.getHp();
		} else if(node instanceof BookNodeCombat){
			BookNodeCombat bookNodeGet = (BookNodeCombat) node;
			getNodehp = bookNodeGet.getHp();
		} else if(node instanceof BookNodeWithRandomChoices){
			BookNodeWithRandomChoices bookNodeGet = (BookNodeWithRandomChoices) node;
			getNodehp = bookNodeGet.getHp();
		}
		if(getNodehp != 0){
			if(state.getMainCharacter().getHpMax() <= (state.getMainCharacter().getHp()+getNodehp)){
				if(statePlay)
					System.out.println("Vos HP sont au max");
				state.getMainCharacter().heal(str);
			} else {
				if(getNodehp > 0)
					state.getMainCharacter().heal(getNodehp);
				else 
					state.getMainCharacter().damage(-getNodehp);
				if(statePlay){
					System.out.println("Vous avez pris "+ getNodehp + " hp");
					System.out.println("Vos hp : "+ state.getMainCharacter().getHp());
				}
			}
			if ((state.getMainCharacter().getHp()+getNodehp)<= 0)
				mort = true;
		}
	}
	
	private void verifGetNodeItem(AbstractBookNode node){
		List<String> listItemState = state.getMainCharacter().getItems();
		List<BookItem> listItemNode = new ArrayList();
		
		List<BookItemLink> getNodeItem = null;
		if(node instanceof BookNodeWithChoices){
			BookNodeWithChoices bookNodeGet = (BookNodeWithChoices) node;
			getNodeItem = bookNodeGet.getItemLinks();
		} else if(node instanceof BookNodeCombat){
			BookNodeCombat bookNodeGet = (BookNodeCombat) node;
			getNodeItem = bookNodeGet.getItemLinks();
		} else if(node instanceof BookNodeWithRandomChoices){
			BookNodeWithRandomChoices bookNodeGet = (BookNodeWithRandomChoices) node;
			getNodeItem = bookNodeGet.getItemLinks();
		}
		
		if (!getNodeItem.isEmpty()){
			int nbItemDispo = getNodeItem.size();
			
			for(BookItemLink itemLink : getNodeItem){
				listItemNode.add(book.getItems().get(itemLink.getId()));
			}
			if(statePlay)
				state = player.verifGetNodeItem(listItemState, listItemNode, nbItemDispo);
			else
				state = fourmi.verifGetNodeItem(listItemState, listItemNode, nbItemDispo);
		}
	}

	
	private void verifGetChoicesItem(AbstractBookNode node, int str){
		if(node.getChoices().get(str).getGold() != 0){
			BookItem bookItem = book.getItems().get("gold");
			state.getMainCharacter().changeMoneyAmount(bookItem.getId(), node.getChoices().get(str).getGold());
			if(statePlay)
				System.out.println("Vous avez pris "+ node.getChoices().get(str).getGold() +" "+ bookItem.getId());
		}
		if(node.getChoices().get(str).getHp() != 0){
			state.getMainCharacter().setHp(node.getChoices().get(str).getHp());
			if(statePlay)
				System.out.println("Vous avez pris "+ node.getChoices().get(str).getHp() + " hp");
		}
	}

}

