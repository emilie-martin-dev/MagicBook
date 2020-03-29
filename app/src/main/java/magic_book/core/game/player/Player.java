package magic_book.core.game.player;

import java.util.List;
import java.util.Scanner;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.player.Jeu.ChoixCombat;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;

public class Player implements InterfacePlayerFourmis {
	
	public Player(){
	}

	@Override
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state) {
		boolean choixValide = false;
		ChoixCombat choixCombat = null;
				
		while(!choixValide){
			System.out.println("Vos choix : ");
			System.out.println("1 - Attaque");
			System.out.println("2 - Inventaire");
			System.out.println("3 - Evasion - reste " + remainingRoundBeforeEvasion + " tours");
			System.out.println("Choix : ");

			Scanner scanner = new Scanner(System.in);
			int choix = scanner.nextInt();
			
			if(choix < 1 || choix > 3) {
				System.out.println("Le choix est invalide");
				continue;
			}
			
			choixCombat = ChoixCombat.values()[choix];
			
			//Si inventaire, il choisis puis reviens sur le choix
			if (choixCombat == ChoixCombat.INVENTAIRE){
				if(!state.getMainCharacter().getItems().isEmpty())
					if(!state.getMainCharacter().getItems().isEmpty())
						useInventaire(state);
					else
						System.out.println("Votre inventaire est vide");
			} else {
				choixValide = true;
			}
		}
		
		return choixCombat;
	}
	
	private boolean choixYesNo(){
		System.out.println("0 pour oui");
		System.out.println("1 pour non");
		System.out.println("Que choisissez-vous ?");
		
		Scanner scanner = new Scanner(System.in);
		int choix = -1;
		
		while(choix != 0 && choix != 1) {
			choix = scanner.nextInt();
		}
		
		return choix == 0;
	}
	
	private void itemPlein(BookState state){
		System.out.println("Votre inventaire est plein");
		System.out.println("Voulez vous supprimer un item ?");
		System.out.println("Vos Item: ");
		
		int i = 0;
		for(String itemState : state.getMainCharacter().getItems()){
			System.out.println(i + " - "+state.getBook().getItems().get(itemState));
			i++;
		}
	}
	
	private void itemSupp(BookState state){
		System.out.println("Quel item voulez-vous supprimer ?");
		boolean choixValide = false;
		
		while(!choixValide){
			Scanner scanner = new Scanner(System.in);
			int choix = scanner.nextInt();
			
			if(choix >= 0 && choix <= (state.getMainCharacter().getItems().size()-1)){				
				state.getMainCharacter().getItems().remove(choix);
				choixValide = true;
			} else if(choix == -1) {
				choixValide = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		
	}
		
	private void itemAdd(BookState state, List<BookItemLink> bookItemLinks){
		System.out.println("Quel item voulez-vous ?");
		boolean choixValide = false;
		int choix = -1;
		
		while(!choixValide){
			Scanner scanner = new Scanner(System.in);
			choix = scanner.nextInt();
			
			if(choix >= 0 && choix <= (bookItemLinks.size()-1)){
				choixValide = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		
		BookItemLink itemLink = bookItemLinks.get(choix);
		System.out.println("L'item "+state.getBook().getItems().get(itemLink.getId()).getName()+" a été rajouté");
		state.getMainCharacter().getItems().add(itemLink.getId());	
		
		itemLink.setAmount(itemLink.getAmount()-1);
				
		if(itemLink.getAmount() == 0)
			bookItemLinks.remove(itemLink);
	}
	
	@Override
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax){
		while(nbItemMax != 0){
			for(BookItemLink itemLink : bookItemLinks){
				System.out.println("Les items suivant sont disponible:");
				System.out.println("- " + state.getBook().getItems().get(itemLink.getId()).getDescription(state.getBook()));
			}
			
			System.out.println("Voulez vous un item ?");
			if(choixYesNo()){
				int itemMax = state.getMainCharacter().getItemsMax();

				if(itemMax == state.getMainCharacter().getItems().size()){
					itemPlein(state);

					System.out.println("Voici vos choix:");

					if(choixYesNo())
						nbItemMax = 0;			
					else
						itemSupp(state);
				} else {
					itemAdd(state, bookItemLinks);
					nbItemMax -= 1;
				}
			} else {
				nbItemMax = 0;
			}
		}
	}

	
	public void useInventaire(BookState state){
		List<String> itemsPerso = state.getMainCharacter().getItems();
		
		System.out.println("Vos Item: ");
		int i = 0;
		for(String itemState : itemsPerso){
			System.out.println(i + " - "+itemState);
			i++;
		}
		
		boolean choixValide = false;
		BookItem bookItem = null;
		int choix = -1;
		while(!choixValide){
			Scanner scanner = new Scanner(System.in);
			choix = scanner.nextInt();
			
			if(choix >= 0 && choix <= 2){
				choixValide = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		
		bookItem = state.getBook().getItems().get(itemsPerso.get(choix));
		
		if(bookItem instanceof BookItemDefense){
			state.setBookItemDefense((BookItemDefense) bookItem);
		} else if(bookItem instanceof BookItemHealing){
			state.getMainCharacter().setHp((state.getMainCharacter().getHp()+((BookItemHealing) bookItem).getHp()));
			System.out.println("Vous avez "+state.getMainCharacter().getHp()+ " hp");
			state.getMainCharacter().getItems().remove(itemsPerso.get(choix));
		} else if(bookItem instanceof BookItemWeapon){
			state.setBookItemArme((BookItemWeapon) bookItem);
		} else {
			System.out.println("Cette objet n'est pas utilisable en combat");
		}
	}

	@Override
	public BookCharacter execPlayerCreation(Book book) {
		return new BookCharacter("Test", "Personnage Test", 3, 50, null, null, null, 5, true);
	}

	@Override
	public int makeAChoice(AbstractBookNodeWithChoices node) {
		Scanner scanner = new Scanner(System.in);
		
		return scanner.nextInt();
	}

	@Override
	public BookCharacter chooseEnnemi(List<BookCharacter> listEnnemis) {
		System.out.println("Qui voulez vous attaquer ?");
		int choix = -1;
		boolean choixValide = false;
		
		while(!choixValide){
			Scanner scanner = new Scanner(System.in);
			choix = scanner.nextInt();
			if(choix >= 0 && choix <= (listEnnemis.size()-1)){
				choixValide = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		
		return listEnnemis.get(choix);
	}

}
