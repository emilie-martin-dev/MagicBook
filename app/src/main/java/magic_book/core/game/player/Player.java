package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
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
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkills;

public class Player implements InterfacePlayerFourmis {
	
	private BookState state;

	private AbstractBookNode bookNodeChoice;
	private int str;
	private Scanner scanner;
	private boolean choix;
	private boolean mort;
	private BookItemWeapon bookItemArme;
	private BookItemDefense bookItemDefense;
	
	private int resistance;
	private int attaque;
	private int doubleDamage;

	private HashMap<String, BookItem>  mapBookItem;
	private HashMap<String, BookCharacter>  mapCharacter;
	
	public Player(BookState state, HashMap<String, BookItem> mapBookItem, HashMap<String, BookCharacter> mapCharacter){
		this.state = state;
		this.mapBookItem = mapBookItem;
		this.mapCharacter = mapCharacter;

		this.bookNodeChoice = bookNodeChoice;
		this.mort = mort;
	}
	
	public void execNodeWithChoices(BookNodeWithChoices node){
		System.out.println("execNodeWithChoice"+ mort);
		System.out.println(node.getChoices().get(1).getRequirements().size());
		verifGetNodeHp(node);
		if(mort == true){
			BookNodeTerminal bookTerminal = new BookNodeTerminal("Vous n'avez plus de vie", BookNodeStatus.FAILURE);
			this.bookNodeChoice = bookTerminal;
		} else {
			System.out.println(node.getText());
			int lienValide = 0;
			for (BookNodeLink bookNodeLink : node.getChoices()){
				if(bookNodeLink.isAvailable(state))
					lienValide += 1;
			}

			if (lienValide != 0){
				boolean choixValide = false;

				verifGetNodeItem(node);

				System.out.println("Voici vos choix : ");
				for (BookNodeLink bookNodeLink : node.getChoices()){
					System.out.println(""+bookNodeLink.getText());
				}
				System.out.println();
				while (choixValide == false){
					System.out.println("Que choisissez-vous ?");
					scanner = new Scanner(System.in);
					str = scanner.nextInt();

					if(str <= (node.getChoices().size()) && str >= 0){

						if(node.getChoices().get(str).isAvailable(state)){
							this.bookNodeChoice = node.getChoices().get(str).getDestination();
							System.out.println(""+node.getChoices().get(str).getDestination());
							choixValide = true;
						} else {
							System.out.println("Vous ne possédez pas : ");
							System.out.println(""+node.getChoices().get(str).getRequirements());
						}
						verifGetChoicesItem(node, str);
					}
					else {
						System.out.println("vous ne pouvez pas effectuer ce choix");
					}
				}
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

		
		System.out.println(node.getText());
		System.out.println("Il y a "+node.getEnnemiesId().size() + " ennemies !");

		for(String ennemieNode : node.getEnnemiesId()){
			listEnnemis.add(mapCharacter.get(ennemieNode));
		}
		
		
		while(finCombat == false){
			choix = false;
			while(choix == false){
				System.out.println("Vos choix : ");
				System.out.println("Attaque");
				System.out.println("Inventaire");
				System.out.println("Evasion - reste "+evasionRound+" tours");
				System.out.println("Choix : ");

				scanner = new Scanner(System.in);
				str = scanner.nextInt();

				if(str ==2 || str == 0){
					choix = true;
					break;
				} else if (str != 1){
					System.out.println("vous ne pouvez pas effectuer ce choix");
				}
				//Si inventaire, il choisis puis reviens sur le choix
				if (str == 1){
					if(!state.getMainCharacter().getItems().isEmpty())
						useInventaire();
					else
						System.out.println("Votre inventaire est vide");
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
							System.out.println("Coup critique !");
							doubleDamage = 2;
						}
					}
					ennemi.damage(state.getMainCharacter().getBaseDamage()*doubleDamage+attaque);
					System.out.println(ennemi.getHp());
					if(ennemi.getHp()<=0){
						System.out.println(ennemi+" est mort");
						listEnnemis.remove(ennemi);
						break;
					} else {
						System.out.println(ennemi+" a "+ennemi.getHp()+" hp");
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
			} else if (str == 2 && evasionRound != 0){
				System.out.println("vous ne pouvez pas encore vous evader");
			}
			if(str != 2){
				for(BookCharacter ennemi : listEnnemis){
					doubleDamage = 1;
					if(ennemi.isDoubleDamage()){
						Random random = new Random();
						int r = random.nextInt(5);
						if (r > 3){
							System.out.println("Coup critique de "+ennemi+" !");
							doubleDamage = 2;
						}
					}
					resistance = 0;
					if(bookItemDefense != null)
						resistance = bookItemDefense.getResistance();

					state.getMainCharacter().damage(ennemi.getBaseDamage()*doubleDamage-resistance);
					
					System.out.println(ennemi + " a attaquer, il vous reste" + state.getMainCharacter().getHp()+" hp");
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
		System.out.println(node.getText());
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		this.bookNodeChoice = randomChoices.getDestination();
	}

	@Override
	public void execNodeTerminal(BookNodeTerminal node) {
		System.out.println(""+node.getText());
	}
	
	private void verifGetNodeHp(BookNodeWithChoices node){
		this.mort = false;
		if(node.getHp() != 0){
			if(state.getMainCharacter().getHpMax() <= (state.getMainCharacter().getHp()+node.getHp())){
				System.out.println("Vos HP sont au max");
				state.getMainCharacter().heal(str);
			} else {
				if(node.getHp()>0)
					state.getMainCharacter().heal(node.getHp());
				else 
					state.getMainCharacter().damage(-node.getHp());
				System.out.println("Vous avez pris "+ node.getHp() + " hp");
				System.out.println("Vos hp : "+ state.getMainCharacter().getHp());
			}
			if ((state.getMainCharacter().getHp()+node.getHp())<= 0)
				mort = true;
		}
	}
	
	private void verifGetNodeItem(BookNodeWithChoices node){
		List<String> listItemState = state.getMainCharacter().getItems();
		List<BookItem> listItemNode = new ArrayList();
		System.out.println("verifGetNodeItem");
		if (!node.getItemLinks().isEmpty()){
			System.out.println("pasempty");
			int nbItemDispo = node.getItemLinks().size();
			System.out.println("nbItemDispo "+node.getItemLinks().size());
			
			
			while(nbItemDispo != 0){

				for(BookItemLink itemLink : node.getItemLinks()){
					System.out.println("Les items suivant sont disponible:");
					System.out.println(""+itemLink.getId());
					listItemNode.add(mapBookItem.get(itemLink.getId()));
				}
				System.out.println("Voulez vous un item ?");
				System.out.println("0 pour oui");
				System.out.println("1 pour non");
				System.out.println("Que choisissez-vous ?");
				scanner = new Scanner(System.in);
				int itemOui = scanner.nextInt();
				
				if(itemOui == 0){
					int itemMax = state.getMainCharacter().getItemsMax();
					if((listItemState.size()-1) == itemMax && itemMax!= 0){
						
						System.out.println("Votre inventaire est plein");
						System.out.println("Voulez vous supprimer un item ?");
						System.out.println("Vos Item: ");
						for(String itemState : listItemState){
							System.out.println("- "+itemState);
						}
						
						System.out.println("Voici vos choix:");
						System.out.println("0 pour oui");
						System.out.println("1 pour non");
						System.out.println("Que choisissez-vous ?");
						choix = false;
						while(choix != true){
							scanner = new Scanner(System.in);
							str = scanner.nextInt();
							if(str == 0 || str == 1){
								choix = true;
							}
							System.out.println("vous ne pouvez pas effectuer ce choix");
						}
						if(str == 1){
							nbItemDispo = 0;
						}
						else if (str == 0){

							System.out.println("Quel item voulez-vous supprimer ?");
							choix = false;
							while(choix != true){
								scanner = new Scanner(System.in);
								str = scanner.nextInt();
								if(str <= (listItemState.size()-1) && str >= 0){
									choix = true;
								}
								System.out.println("vous ne pouvez pas effectuer ce choix");
							}
							listItemState.remove(listItemState.get(str));
							state.getMainCharacter().setItems(listItemState);
						}
					} else {
						System.out.println("Quel item voulez-vous ?");
						choix = false;
						while(choix != true){
							scanner = new Scanner(System.in);
							str = scanner.nextInt();
							if(str <= (node.getItemLinks().size()) && str >= 0){
								choix = true;
							}
							System.out.println("vous ne pouvez pas effectuer ce choix");
						}

						listItemState.add(node.getItemLinks().get(str).getId());							
						listItemNode.remove(node.getItemLinks().get(str));
					}
					nbItemDispo -= 1;
				} else if (itemOui == 1){
					nbItemDispo = 0;
				}
			}
		}
	}

	
	private void verifGetChoicesItem(BookNodeWithChoices node, int str){
		if(node.getChoices().get(str).getGold() != 0){
			BookItem bookItem = mapBookItem.get("gold");
			state.getMainCharacter().changeMoneyAmount(bookItem.getId(), node.getChoices().get(str).getGold());
			System.out.println("Vous avez pris "+ node.getChoices().get(str).getGold() +" "+ bookItem.getId());
		}
		if(node.getChoices().get(str).getHp() != 0){
			state.getMainCharacter().setHp(node.getChoices().get(str).getHp());
			System.out.println("Vous avez pris "+ node.getChoices().get(str).getHp() + " hp");
		}
	}
	
	public AbstractBookNode getBookNodeChoice() {
		return bookNodeChoice;
	}

	
	public void useInventaire(){
		List<String> listItemState = state.getMainCharacter().getItems();
		System.out.println("Vos Item: ");
		for(String itemState : listItemState){
			System.out.println("- "+itemState);
		}
		choix = false;
		BookItem bookItem ;
		while(choix == false){
			scanner = new Scanner(System.in);
			str = scanner.nextInt();
			bookItem = mapBookItem.get(listItemState.get(str));

			if(str <= 2 && str >= 0){
				choix = true;
				break;
			}
			else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		bookItem = mapBookItem.get(listItemState.get(str));
		
		if(bookItem instanceof BookItemDefense){
			BookItemDefense bookItemDefenseTrans = (BookItemDefense) bookItem;
			bookItemDefense = bookItemDefenseTrans;
		} else if(bookItem instanceof BookItemHealing){
			state.getMainCharacter().setHp((state.getMainCharacter().getHp()+((BookItemHealing) bookItem).getHp()));
			System.out.println("Vous avez "+state.getMainCharacter().getHp()+ " hp");
			state.getMainCharacter().getItems().remove(listItemState.get(str));
		} else if(bookItem instanceof BookItemWeapon){
			BookItemWeapon bookItemArmeTrans = (BookItemWeapon) bookItem;
			bookItemArme = bookItemArmeTrans;
		} else if(bookItem instanceof BookItemMoney){
				System.out.println("Cette objet n'est pas utilisable en combat");
		} else{//si c'est juste un bookItem
			System.out.println("Cette objet n'est pas utilisable en combat");
		}
		
		choix = false;
	}

}
