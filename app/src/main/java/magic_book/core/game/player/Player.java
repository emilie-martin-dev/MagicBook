package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
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
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;

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

	private int destination;
	private List<BookItem> listItemNode;
	private List<String> listItemState;
	
	private HashMap<String, BookItem>  mapBookItem;
	private HashMap<String, BookCharacter>  mapCharacter;
	
	public Player(BookState state, HashMap<String, BookItem> mapBookItem, HashMap<String, BookCharacter> mapCharacter){
		this.state = state;
		this.mapBookItem = mapBookItem;
		this.mapCharacter = mapCharacter;

		this.bookNodeChoice = bookNodeChoice;
		this.mort = mort;
		this.listItemNode = listItemNode;
		this.listItemState = listItemState;
	}
	
	
	public int stateChoices(){
		scanner = new Scanner(System.in);
		str = scanner.nextInt();
		return str;
	}



	@Override
	public int execNodeCombat(int evasionRound) {
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
		return str;
	}
		


	@Override
	public void execNodeTerminal(BookNodeTerminal node) {
		System.out.println(""+node.getText());
	}
	
	private void choixYesNo(){
		System.out.println("0 pour oui");
		System.out.println("1 pour non");
		System.out.println("Que choisissez-vous ?");
	}
	
	private void itemPlein(){
		System.out.println("Votre inventaire est plein");
		System.out.println("Voulez vous supprimer un item ?");
		System.out.println("Vos Item: ");
		for(String itemState : listItemState){
			System.out.println("- "+itemState);
		}
	}
	
	private void itemSupp(){
		System.out.println("Quel item voulez-vous supprimer ?");
		choix = false;
		while(choix != true){
		scanner = new Scanner(System.in);
		str = scanner.nextInt();
		if(str <= (listItemState.size()-1) && str >= 0){
			choix = true;
		} else {
			System.out.println("vous ne pouvez pas effectuer ce choix");
		}
		listItemState.remove(listItemState.get(str));
		state.getMainCharacter().setItems(listItemState);
		}
	}
		
	private void itemAdd(){
		System.out.println("Quel item voulez-vous ?");
		choix = false;
		while(choix != true){
			scanner = new Scanner(System.in);
			str = scanner.nextInt();
			if(str <= (listItemNode.size()-1) && str >= 0){
				choix = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		System.out.println("L'item "+listItemNode.get(str).getName()+" a été rajouté");
		listItemState.add(listItemNode.get(str).getId());							
		listItemNode.remove(listItemNode.get(str));
		state.getMainCharacter().setItems(listItemState);
	}
	
	public BookState verifGetNodeItem(List<String> listItemState, List<BookItem> listItemNode, int nbItemDispo){
		this.listItemNode = listItemNode;
		this.listItemState = listItemState;
		while(nbItemDispo != 0){
			for(BookItem itemName : listItemNode){
				System.out.println("Les items suivant sont disponible:");
				System.out.println("- "+itemName.getName());
			}
			System.out.println("Voulez vous un item ?");
			choixYesNo();
			scanner = new Scanner(System.in);
			int itemOui = scanner.nextInt();

			if(itemOui == 0){
				int itemMax = state.getMainCharacter().getItemsMax();

				if((listItemState.size()-1) == itemMax && itemMax!= 0){
					itemPlein();


					System.out.println("Voici vos choix:");
					choixYesNo();
					choix = false;
					while(choix != true){
						scanner = new Scanner(System.in);
						str = scanner.nextInt();
						if(str == 0 || str == 1){
							choix = true;
						}
						System.out.println("vous ne pouvez pas effectuer ce choix");
					}

					if(str == 1)
						nbItemDispo = 0;			
					else if (str == 0)
						itemSupp();

				} else {
					itemAdd();
				}
				nbItemDispo -= 1;
			} else if (itemOui == 1){
				nbItemDispo = 0;
			}
		}
		return state;
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
