package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import magic_book.core.item.BookItemLink;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkills;

public class Player implements InterfacePlayerFourmis {
	
	private BookState state;
	private HashMap<String, BookItem> mapBookItem;
	private AbstractBookNode bookNodeChoice;
	private int str;
	private Scanner scanner;
	private boolean choix;

	
	public Player(BookState state, HashMap<String, BookItem> mapBookItem){
		this.state = state;
		this.mapBookItem = mapBookItem;
		this.bookNodeChoice = bookNodeChoice;
	}
	
	public void execNodeWithChoices(BookNodeWithChoices node){
		
		verifGetNodeHp(node);
		
		System.out.println(node.getText());
		int lienValide = 0;
		for (BookNodeLink bookNodeLink : node.getChoices()){
			if(bookNodeLink.isAvailable(state))
				lienValide += 1;
		}
		
		if (lienValide != 0){
			boolean choixValide = false;
			BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNodeChoice;
			
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
				
				if(str <= node.getChoices().size() && str >= 0){
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
			System.out.println("pas d'item adéquat, dommage");
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items adéquats", BookNodeStatus.FAILURE);
			this.bookNodeChoice = bookNodeTerminalFail;
		}
			
	}
	

	@Override
	public void execNodeCombat(BookNodeCombat node) {
		BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNodeChoice;
		System.out.println(node.getText());
		
		//afficher les choix
		for(BookNodeLink bookNodeLink : node.getChoices()){
			System.out.println(""+bookNodeLink.getText());
		}		
		
		//avoir le nombre de tour avant evasion
		int evasionRound = node.getEvasionRound();
		
		//enlever evasion
		int choix = 0;
		int str = 0;
		while(evasionRound != 0 && choix == 0){
			//afficher les choix

			System.out.println("Choix : ");
			scanner = new Scanner(System.in);
			str = scanner.nextInt();
			
			if(str <= node.getChoices().size() && str >= 0){
				if(node.getChoices().get(str) == node.getEvasionBookNodeLink())
					System.out.println("Vous ne pouvez pas encore vous échaper");
				else
					choix = 1;
				
				evasionRound -= 1;
			}
			else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		
		if (evasionRound == 0 && choix == 0){
			while(choix == 0){
				System.out.println("Choix : ");
				scanner = new Scanner(System.in);
				str = scanner.nextInt();
				if(str <= node.getChoices().size() && str >= 0){
					choix = 1;
				} else 
					System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		this.bookNodeChoice = node.getChoices().get(str).getDestination();
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
		if(node.getHp() != 0){
			state.getMainCharacter().setHp(node.getHp());
			System.out.println("Vous avez pris "+ node.getHp() + " hp");
		}
	}
	
	private void verifGetNodeItem(BookNodeWithChoices node){
		if (!node.getItemLinks().isEmpty()){
			int nbItemDispo = node.getItemLinks().size();
			while(nbItemDispo != 0){
				for(BookItemLink itemLink : node.getItemLinks()){
					System.out.println("Les items suivant sont disponible:");
					System.out.println(""+itemLink.getId());
				}
				System.out.println("Voulez vous un item ?");
				System.out.println("0 pour oui");
				System.out.println("1 pour non");
				System.out.println("Que choisissez-vous ?");
				scanner = new Scanner(System.in);
				int itemOui = scanner.nextInt();
				
				if(itemOui == 0){
					int itemMax = state.getMainCharacter().getItemsMax();
					List<String> listItemState = state.getMainCharacter().getItems();
					if(listItemState.size() == itemMax && itemMax!= 0){
						
						System.out.println("Votre inventaire est plein");
						System.out.println("Voulez vous supprimer un item ?");
						System.out.println("Vos Item: ");
						
						
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
							System.out.println("Vos item:");
							for(String itemState : listItemState){
								System.out.println("- "+itemState);
							}

							System.out.println("Quel item voulez-vous supprimer ?");
							choix = false;
							while(choix != true){
								scanner = new Scanner(System.in);
								str = scanner.nextInt();
								if(str <= listItemState.size() && str >= 0){
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
							if(str <= node.getItemLinks().size() && str >= 0){
								choix = true;
							}
							System.out.println("vous ne pouvez pas effectuer ce choix");
						}

						listItemState.add(node.getItemLinks().get(str).getId());							
						List<BookItemLink> itemLinl = node.getItemLinks();
						itemLinl.remove(str);

						node.setItemLinks(itemLinl);
						node.setNbItemsAPrendre(node.getNbItemsAPrendre()-1);
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


}
