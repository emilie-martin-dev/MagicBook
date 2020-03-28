package magic_book.core.game.player;

import java.util.List;
import java.util.Scanner;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.requirement.AbstractRequirement;

public class Player implements InterfacePlayerFourmis {
	
	private AbstractBookNode bookNodeChoice;
	private int str;
	private Scanner scanner;

	
	public Player(){
		this.bookNodeChoice = bookNodeChoice;
	}
	
	public void execNodeWithChoices(BookNodeWithChoices node, BookState state){
		int lienValide = 0;
		for (BookNodeLink bookNodeLink : node.getChoices()){
			System.out.println("Voici les choix : "+bookNodeLink.getText());
			if(bookNodeLink.isAvailable(state))
				lienValide += 1;
		}
		if (lienValide != 0){
			boolean choixValide = false;
			while (choixValide == false){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) bookNodeChoice;
				scanner = new Scanner(System.in);
				System.out.println("Choix : ");
				str = 0;
				if(node.getChoices().get(str).isAvailable(state)){
					System.out.println("IS AVALIBE OKKKKK");
					this.bookNodeChoice = node.getChoices().get(str).getDestination();
					break;
				} else {
					System.out.println("Vous ne possédez pas : ");
					for(List<AbstractRequirement> listRequirement : node.getChoices().get(str).getRequirements()) {
						for(AbstractRequirement requirement : listRequirement) {
							System.out.println("- "+requirement.toString());
						}
					}
				}
			}
		} else {
			System.out.println("pas d'item adéquat, dommage");
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items adéquats", BookNodeStatus.FAILURE);
			this.bookNodeChoice = bookNodeTerminalFail;
		}
			
	}
	
	//Changer toute les méthode pour enelever de bookNode... et ca va être le this qui va prend le relais

	@Override
	public void execNodeCombat(BookNodeCombat node, BookState state) {
		/*
		Question:
		faire:
		if(node.getChoices == node.getWinBookNodeLink())
			--> alors on regade les items a prendre ? genre c'est shopItemLinks ?
		*/
		
		//transformer le bookNodeChoice
		BookNodeCombat bookNodeCombat = (BookNodeCombat) bookNodeChoice;
		
		//avoir le nombre de tour avant evasion
		int evasionRound = node.getEvasionRound();
		
		//enlever evasion
		int choix = 0;
		int str = 0;
		while(evasionRound != 0 && choix == 0){
			//afficher les choix
			for(BookNodeLink bookNodeLink : node.getChoices()){
				if(bookNodeLink.getText() != "combat evasion")
					System.out.println(""+bookNodeLink.getText());
			}
			System.out.println("Choix : ");
			scanner = new Scanner(System.in);
			
			str = scanner.nextInt();
			
			evasionRound -= 1;
			if(node.getChoices().get(str) == node.getEvasionBookNodeLink()){
				System.out.println("Vous ne pouvez pas encore vous échaper");
			} else {
				choix = 1;
			}
		}
		
		if (evasionRound == 0 && choix == 0){
			//afficher les choix
			for(BookNodeLink bookNodeLink : node.getChoices()){
				System.out.println(""+bookNodeLink.getText());
			}
			while(choix == 0){
				System.out.println("Choix : ");
				//str = scanner.nextInt();
			}
		}
		this.bookNodeChoice = node.getChoices().get(str).getDestination();
	}


	@Override
	public void execNodeTerminal(BookNodeTerminal node, BookState state) {
		for(BookNodeLink bookNodeLink : node.getChoices()){
			System.out.println(""+bookNodeLink.getText());
		}
	}

	public AbstractBookNode getBookNodeChoice() {
		return bookNodeChoice;
	}
}
