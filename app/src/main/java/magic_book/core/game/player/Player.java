package magic_book.core.game.player;

import java.util.ArrayList;
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
import magic_book.core.item.BookItemLink;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkills;

public class Player implements InterfacePlayerFourmis {
	
	private AbstractBookNode bookNodeChoice;
	private int str;
		private Scanner scanner;

	
	public Player(){
		this.bookNodeChoice = bookNodeChoice;
	}
	
	public void execNodeWithChoices(BookNodeWithChoices node, BookState state){
		System.out.println(node.getText());
		int lienValide = 0;
		for (BookNodeLink bookNodeLink : node.getChoices()){
			if(bookNodeLink.isAvailable(state))
				lienValide += 1;
		}
		if (lienValide != 0){
			boolean choixValide = false;
			BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNodeChoice;
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

						if(listItemState.size() == itemMax){
							
							System.out.println("Votre inventaire est plein");
							System.out.println("Voulez vous supprimer un item ?");
							System.out.println("Voici vos choix:");
							System.out.println("0 pour oui");
							System.out.println("1 pour non");
							System.out.println("Que choisissez-vous ?");
							scanner = new Scanner(System.in);
							str = scanner.nextInt();
							
							if(str == 1){
								nbItemDispo = 0;
							}
							
							for(String itemState : listItemState){
								System.out.println(itemState);
							}
							
							System.out.println("Quel item voulez-vous supprimer ?");
							scanner = new Scanner(System.in);
							str = scanner.nextInt();
							listItemState.remove(listItemState.get(str));
							state.getMainCharacter().setItems(listItemState);
						} else {
							System.out.println("Quel item voulez-vous ?");
							scanner = new Scanner(System.in);
							str = scanner.nextInt();
							
							listItemState.add(node.getItemLinks().get(str).getId());							
							List<BookItemLink> itemLinl = node.getItemLinks();
							itemLinl.remove(str);
							
							node.setItemLinks(itemLinl);
							node.setNbItemsAPrendre(node.getNbItemsAPrendre()-1);
						}
					}
					nbItemDispo -= 1;
				}
			}
			
			System.out.println("Voici vos choix : ");
			for (BookNodeLink bookNodeLink : node.getChoices()){
				System.out.println(""+bookNodeLink.getText());
			}
			System.out.println();
			while (choixValide == false){
				System.out.println("Que choisissez-vous ?");
				scanner = new Scanner(System.in);
				str = scanner.nextInt();
				System.out.println(node.getChoices().get(str).getRequirements());
				if(node.getChoices().get(str).isAvailable(state)){
					this.bookNodeChoice = node.getChoices().get(str).getDestination();
					System.out.println(""+node.getChoices().get(str).getDestination());
					choixValide = true;
				} else {
					System.out.println("Vous ne possédez pas : ");
					System.out.println(""+node.getChoices().get(str).getRequirements());
				}
			}
		} else {
			System.out.println("pas d'item adéquat, dommage");
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items adéquats", BookNodeStatus.FAILURE);
			this.bookNodeChoice = bookNodeTerminalFail;
		}
			
	}
	

	@Override
	public void execNodeCombat(BookNodeCombat node, BookState state) {
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

			if(node.getChoices().get(str) == node.getEvasionBookNodeLink()){
				System.out.println("Vous ne pouvez pas encore vous échaper");
			} else {
				choix = 1;
			}
			evasionRound -= 1;
		}
		
		if (evasionRound == 0 && choix == 0){
			while(choix == 0){
				System.out.println("Choix : ");
				scanner = new Scanner(System.in);
				str = scanner.nextInt();
			}
		}
		this.bookNodeChoice = node.getChoices().get(str).getDestination();
	}

	@Override
	public void execNodeWithRandomChoices(BookNodeWithRandomChoices node, BookState state) {
		BookNodeWithRandomChoices bookNodeWithRandom = (BookNodeWithRandomChoices) bookNodeChoice;
		System.out.println(node.getText());
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		this.bookNodeChoice = randomChoices.getDestination();
	}

	@Override
	public void execNodeTerminal(BookNodeTerminal node, BookState state) {
		System.out.println(""+node.getText());
	}

	public AbstractBookNode getBookNodeChoice() {
		return bookNodeChoice;
	}


}
