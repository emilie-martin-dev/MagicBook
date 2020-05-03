package magic_book.core.game.player;

import java.util.List;
import java.util.Scanner;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationSkill;
import magic_book.core.game.player.Jeu.ChoixCombat;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;

/**
 * Permet au joueur de jouer en utilisant la console
 */
public class Player implements InterfacePlayerFourmis {

	public Player(){
	}

	@Override
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state) {
		boolean choixValide = false;
		ChoixCombat choixCombat = null;
		//Affichage des choix lors du tour de combat du player
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

			choixCombat = ChoixCombat.values()[choix-1];

			//Choisi un objet dans l'inventaire puis retourne au choix
			if (choixCombat == ChoixCombat.INVENTAIRE){
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

	/**
	* Le joueur effectue un choix par oui ou non
	* @return Choix décidé par le joueur
	*/
	private boolean choixYesNo(){
		System.out.println("0 - oui");
		System.out.println("1 - non");
		Scanner scanner = new Scanner(System.in);
		int choixJoueur = -1;

		while(choixJoueur != 0 && choixJoueur != 1) {
			choixJoueur = scanner.nextInt();
		}

		return choixJoueur == 0;
	}

	/**
	* Si l'item est plein, affichage de tout les items du joueur
	* @param state Sauvegarde actuelle de la partie
	*/
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

	/**
	* Supprime l'item choisi de l'inventaire du joueur
	* @param state Sauvegarde actuelle de la partie
	*/
	private void itemSupp(BookState state){
		System.out.println("Quel item voulez-vous supprimer ?");
		int i = 0;
		for(String itemState : state.getMainCharacter().getItems()){
			System.out.println(i + " - "+state.getBook().getItems().get(itemState));
			i++;
		}
		System.out.println("-1 - Annuler");
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

	/**
	* Ajoute l'item choisi dans l'inventaire du joueur
	* @param state Sauvegarde actuelle de la partie
	* @param bookItemLinks Item(s) disponible(s) sur le lien actuel
	*/
	private int itemAdd(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax){
		System.out.println("Quel item voulez-vous ?");
		int i=0;
		for(BookItemLink itemLink : bookItemLinks){
			System.out.println(i+" - "+state.getBook().getItems().get(itemLink.getId()).getName());
			i++;
		}
		System.out.println("-1 - Annuler");
		boolean choixValide = false;
		int choix = -1;
		
		while(!choixValide){
			Scanner scanner = new Scanner(System.in);
			choix = scanner.nextInt();

			if(choix >= 0 && choix <= (bookItemLinks.size()-1)){
				BookItemLink itemLink = bookItemLinks.get(choix);
				System.out.println("L'item "+state.getBook().getItems().get(itemLink.getId()).getName()+" a été rajouté");
				state.getMainCharacter().getItems().add(itemLink.getId());

				itemLink.setAmount(itemLink.getAmount()-1);
				
				if(itemLink.getAmount() == 0)
					bookItemLinks.remove(itemLink);
				nbItemMax--;
				
				choixValide = true;
			} else if(choix == -1) {
				choixValide = true;
			} else {
				System.out.println("vous ne pouvez pas effectuer ce choix");
			}
		}
		return nbItemMax;
	}

	@Override
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax){

		while(nbItemMax != 0 && !bookItemLinks.isEmpty()){
			System.out.println("Les items suivant sont disponible:");
			int i=0;
			for(BookItemLink itemLink : bookItemLinks){
				System.out.println(i+" - "+state.getBook().getItems().get(itemLink.getId()).getName());
				i++;
			}

			System.out.println("Voulez vous un item ?");
			if(choixYesNo()){
				int itemMax = state.getMainCharacter().getItemsMax();

				if(itemMax == state.getMainCharacter().getItems().size()){
					itemPlein(state);

					System.out.println("Voici vos choix:");

					if(!choixYesNo())
						nbItemMax = 0;
					else
						itemSupp(state);
				} else {
					nbItemMax = itemAdd(state, bookItemLinks, nbItemMax);
				}

			} else {
				nbItemMax = 0;
			}

			if(bookItemLinks.isEmpty())
				nbItemMax = 0;
		}
	}

	/**
	* Permet au joueur d'utiliser un objet de son inventaire
	* @param state Sauvegarde actuelle de la partie
	*/
	@Override
	public void useInventaire(BookState state){
		List<String> itemsPerso = state.getMainCharacter().getItems();

		System.out.println("Vos Item: ");
		int i = 0;
		for(String itemState : itemsPerso){
			System.out.println(i + " - "+state.getBook().getItems().get(itemState).getName());
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
				System.out.println("Vous ne pouvez pas effectuer ce choix");
			}
		}

		bookItem = state.getBook().getItems().get(itemsPerso.get(choix));

		if(bookItem instanceof BookItemDefense){
			state.setBookItemDefense((BookItemDefense) bookItem);
		} else if(bookItem instanceof BookItemHealing){
			state.getMainCharacter().heal(((BookItemHealing) bookItem).getHp());
			System.out.println("Vous avez gagné "+ ((BookItemHealing) bookItem).getHp());
			System.out.println("Vous avez "+state.getMainCharacter().getHp()+ " hp");
			state.getMainCharacter().getItems().remove(itemsPerso.get(choix));
		} else if(bookItem instanceof BookItemWeapon){
			state.setBookItemArme((BookItemWeapon) bookItem);
		} else {
			System.out.println("Cette objet n'est pas utilisable en combat");
		}
	}

	@Override
	public void execPlayerCreation(Book book, AbstractCharacterCreation characterCreation, BookState state){
		System.out.println(characterCreation.getText());

		//Choix des items du début
		if(characterCreation instanceof CharacterCreationItem){
			CharacterCreationItem characterCreationItem = (CharacterCreationItem) characterCreation;
			prendreItems(state, characterCreationItem.getItemLinks(), characterCreationItem.getAmountToPick());
		}
		//Choix des skill du début
		else if(characterCreation instanceof CharacterCreationSkill){
			CharacterCreationSkill characterCreationSkill = (CharacterCreationSkill) characterCreation;

			int nbItemMax = characterCreationSkill.getAmountToPick();

			while(nbItemMax != 0 && !characterCreationSkill.getSkillLinks().isEmpty()){
				System.out.println("Les compétences suivantes sont disponibles:");
				int i = 0;
				for(String skillId : characterCreationSkill.getSkillLinks()){
					System.out.println(i + " - " + state.getBook().getSkills().get(skillId).getDescription(state.getBook()));
					i++;
				}

				skillAdd(state, characterCreationSkill);
				nbItemMax--;
			}
		}
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
				System.out.println("Vous ne pouvez pas effectuer ce choix");
			}
		}

		return listEnnemis.get(choix);
	}

	/**
	* Ajoute le skill choisi au personnage
	* @param state Sauvegarde actuelle de la partie
	* @param characterCreationState skill disponible
	*/
	private void skillAdd(BookState state, CharacterCreationSkill characterCreationSkill){
		System.out.println("Voulez vous une compétence ?");
		if(choixYesNo()){
			System.out.println("Quel compétence voulez-vous ?");
			boolean choixValide = false;
			int choix = -1;

			while(!choixValide){
				Scanner scanner = new Scanner(System.in);
				choix = scanner.nextInt();

				if(choix >= 0 && choix <= (characterCreationSkill.getSkillLinks().size()-1)){
					choixValide = true;
				} else {
					System.out.println("Vous ne pouvez pas effectuer ce choix");
				}
			}

			String skill = characterCreationSkill.getSkillLinks().get(choix);
			state.getMainCharacter().addSkill(skill);
			System.out.println("La compétence "+skill+" a été aprise");
			characterCreationSkill.getSkillLinks().remove(skill);

			characterCreationSkill.setAmountToPick(characterCreationSkill.getAmountToPick()-1);
		} else {
			characterCreationSkill.getSkillLinks().clear();
		}
	}
}
