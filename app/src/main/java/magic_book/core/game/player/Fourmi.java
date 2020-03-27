package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;

public class Fourmi implements InterfacePlayerFourmis{
	private BookState state;
	private HashMap<String, BookItem>  mapBookItem = new HashMap();
	private HashMap<String, BookCharacter>  mapCharacter = new HashMap();
	
	
	private AbstractBookNode bookNodeChoice;
	private int str;
	
	private int defaite;
	private int victoire;
	private boolean mort;
	private boolean choix;
	private Random random = new Random();
	private BookItemWeapon bookItemArme;
	private BookItemDefense bookItemDefense;
	
	private int attaque;
	private int resistance;
	private int doubleDamage;

	public Fourmi(BookState state, HashMap<String, BookItem> mapBookItem, HashMap<String, BookCharacter> mapCharacter){
		this.state = state;
		this.mapBookItem = mapBookItem;
		this.mapCharacter = mapCharacter;

		
		this.bookNodeChoice = bookNodeChoice;
		this.mort = mort;
		
	}
	public Fourmi(HashMap<String, BookItem> mapBookItem, HashMap<String, BookCharacter> mapCharacter){
		this.mapBookItem = mapBookItem;
		this.mapCharacter = mapCharacter;
		
		this.bookNodeChoice = bookNodeChoice;
		this.mort = mort;
		execBookState();
	}
	
	private void execBookState(){
		BookCharacter bookCharacter = new BookCharacter("", "Fourmis", 20, 20, null, null, null, 0, false);
		BookState state = new BookState();
		state.setMainCharacter(bookCharacter);
	}
	
	public void execNodeWithChoices(BookNodeWithChoices node){
		verifGetNodeHp(node);
		if(mort == true){
			BookNodeTerminal bookTerminal = new BookNodeTerminal("Vous n'avez plus de vie", BookNodeStatus.FAILURE);
			this.bookNodeChoice = bookTerminal;
		} else {
			int lienValide = 0;
			for (BookNodeLink bookNodeLink : node.getChoices()){
				if(bookNodeLink.isAvailable(state))
					lienValide += 1;
			}

			if (lienValide != 0){
				boolean choixValide = false;

				verifGetNodeItem(node);
				while (choixValide == false){
					str = random.nextInt(node.getChoices().size());
					if(node.getChoices().get(str).isAvailable(state)){
						this.bookNodeChoice = node.getChoices().get(str).getDestination();
						choixValide = true;
					}
				}
				verifGetChoicesItem(node, str);
			} else {
				BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items adéquats", BookNodeStatus.FAILURE);
				this.bookNodeChoice = bookNodeTerminalFail;
			}
		}
	}
	

	@Override
	public void execNodeCombat(BookNodeCombat node) {
		this.mort = false;
		int evasionRound = node.getEvasionRound();
		int str = 0;
		boolean finCombat = false;
		List<BookCharacter> listEnnemis=  new ArrayList();

		for(String ennemieNode : node.getEnnemiesId()){
			listEnnemis.add(mapCharacter.get(ennemieNode));
		}
		
		
		while(finCombat == false){
			choix = false;
			while(choix == false){
				str = random.nextInt(listEnnemis.size());
				
				if(str ==2 || str == 0){
					choix = true;
					break;
				}
				//Si inventaire, il choisis puis reviens sur le choix
				if (str == 1){
					if(!state.getMainCharacter().getItems().isEmpty())
						useInventaire();
				}
			}
			//Si attaque
			if (str == 0){
				for(BookCharacter ennemi : listEnnemis){
					
					attaque = 0;
					if(bookItemArme != null)
						attaque = bookItemArme.getDamage();
					doubleDamage = 1;
					if(state.getMainCharacter().isDoubleDamage()){
						Random random = new Random();
						int r = random.nextInt(5);
						if (r > 3){
							doubleDamage = 2;
						}
					}
					ennemi.damage(state.getMainCharacter().getBaseDamage()*doubleDamage+attaque);
					System.out.println(ennemi.getHp());
					if(ennemi.getHp()<=0){
						listEnnemis.remove(ennemi);
						break;
					}
				}
				if(listEnnemis.isEmpty())
					finCombat = true;
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

			if (str == 2 && evasionRound == 0){
				finCombat = true;
			}
			if(str != 2){
				for(BookCharacter ennemi : listEnnemis){
					doubleDamage = 1;
					if(ennemi.isDoubleDamage()){
						Random random = new Random();
						int r = random.nextInt(5);
						if (r > 3){
							doubleDamage = 2;
						}
					}
					resistance = 0;
					if(bookItemDefense != null)
						resistance = bookItemDefense.getResistance();

					state.getMainCharacter().damage(ennemi.getBaseDamage()*doubleDamage-resistance);
					
					if(state.getMainCharacter().getHp() <= 0){
						mort = true;
						finCombat = true;
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
				evasionRound -= 1;
			}
		}
		BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNodeChoice;
		if(mort == false && str != 2){
			this.bookNodeChoice = node.getWinBookNodeLink().getDestination();
		} else if(mort == false && str == 2){
			this.bookNodeChoice = node.getEvasionBookNodeLink().getDestination();
		} else {
			this.bookNodeChoice = node.getLooseBookNodeLink().getDestination();
		}
	}

	@Override
	public void execNodeWithRandomChoices(BookNodeWithRandomChoices node) {
		BookNodeWithRandomChoices bookNodeWithRandom = (BookNodeWithRandomChoices) bookNodeChoice;
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		this.bookNodeChoice = randomChoices.getDestination();
	}

	@Override
	public void execNodeTerminal(BookNodeTerminal node) {
		if(node.getBookNodeStatus() == BookNodeStatus.VICTORY){
			this.victoire = 1;
		} else {
			this.defaite = 1;
		}
	}
	
	private void verifGetNodeHp(BookNodeWithChoices node){
		this.mort = false;
		if(node.getHp() != 0){
			if(state.getMainCharacter().getHpMax() <= (state.getMainCharacter().getHp()+node.getHp())){
				state.getMainCharacter().setHp(state.getMainCharacter().getHpMax());
			} else {
				state.getMainCharacter().setHp(state.getMainCharacter().getHp()+node.getHp());
			}
			if ((state.getMainCharacter().getHp()+node.getHp())<= 0)
				mort = true;
			
		}
	}
	
	private void verifGetNodeItem(BookNodeWithChoices node){
		List<String> listItemState = state.getMainCharacter().getItems();
		List<BookItem> listItemNode = new ArrayList();

		if (!node.getItemLinks().isEmpty()){
			int nbItemDispo = node.getItemLinks().size();
			
			while(nbItemDispo != 0){

				for(BookItemLink itemLink : node.getItemLinks()){
					listItemNode.add(mapBookItem.get(itemLink.getId()));
				}
					int itemMax = state.getMainCharacter().getItemsMax();
					if((listItemState.size()) == itemMax && itemMax!= 0){
						str = random.nextInt(listItemState.size());
						listItemState.remove(listItemState.get(str));
						state.getMainCharacter().setItems(listItemState);
					} else {
						str = random.nextInt(listItemNode.size());

						listItemState.add(node.getItemLinks().get(str).getId());							
						listItemNode.remove(node.getItemLinks().get(str));
					}
					nbItemDispo -= 1;
			}
		}
	}


	
	private void verifGetChoicesItem(BookNodeWithChoices node, int str){
		if(node.getChoices().get(str).getGold() != 0){
			BookItem bookItem = mapBookItem.get("gold");
			state.getMainCharacter().changeMoneyAmount(bookItem.getId(), node.getChoices().get(str).getGold());
		}
		if(node.getChoices().get(str).getHp() != 0){
			state.getMainCharacter().setHp(node.getChoices().get(str).getHp());
		}
	}
	
	public void useInventaire(){
		List<String> listItemState = state.getMainCharacter().getItems();
		
		str = random.nextInt(listItemState.size());
		BookItem bookItem = mapBookItem.get(listItemState.get(str));
		
		if(bookItem instanceof BookItemDefense){
			BookItemDefense bookItemDefenseTrans = (BookItemDefense) bookItem;
			bookItemDefense = bookItemDefenseTrans;
		} else if(bookItem instanceof BookItemHealing){
			state.getMainCharacter().setHp((state.getMainCharacter().getHp()+((BookItemHealing) bookItem).getHp()));
			state.getMainCharacter().getItems().remove(listItemState.get(str));
		} else if(bookItem instanceof BookItemWeapon){
			BookItemWeapon bookItemArmeTrans = (BookItemWeapon) bookItem;
			bookItemArme = bookItemArmeTrans;
		} else if(bookItem instanceof BookItemMoney){
		} else{
		}
	}

	public int getVictoire() {
		return victoire;
	}
	
	public AbstractBookNode getBookNodeChoice() {
		return bookNodeChoice;
	}

	
	


}
