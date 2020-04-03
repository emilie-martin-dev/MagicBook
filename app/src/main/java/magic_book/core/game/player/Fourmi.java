package magic_book.core.game.player;

import java.util.List;
import java.util.Random;
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
 * Permet à la fourmi de jouer en fonction des choix random
 */
public class Fourmi implements InterfacePlayerFourmis{
	
	public Fourmi(){
	}
	
	@Override
	public int makeAChoice(AbstractBookNodeWithChoices node) {
		Random random = new Random();
		return random.nextInt(node.getChoices().size())+1;
	}
	
	@Override
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state) {
		boolean choixValide = false;
		Random random = new Random();
		ChoixCombat choixCombat = null;
		int choix;
		
		//Choix
		while(!choixValide){
			choix = random.nextInt(ChoixCombat.values().length);
			choixCombat = ChoixCombat.values()[choix];
			
			//Prend un item et revient au choix
			if (choixCombat == ChoixCombat.INVENTAIRE){
				if(!state.getMainCharacter().getItems().isEmpty())
					useInventaire(state);
			} else {
				choixValide = true;
			}
		}
		
		return choixCombat;
	}
	
	@Override
	public void useInventaire(BookState state){
		List<String> listItemState = state.getMainCharacter().getItems();
		
		//Choix
		Random random = new Random();
		int choix = random.nextInt(listItemState.size());
		BookItem bookItem = state.getBook().getItems().get(listItemState.get(choix));
		
		//Si l'item choisi est de type défense, ajoute l'item
		if(bookItem instanceof BookItemDefense){
			state.setBookItemDefense((BookItemDefense) bookItem);
		}
		//Si l'item choisi est de type soin, soigne le player
		else if(bookItem instanceof BookItemHealing){
			BookItemHealing bookItemHealing = (BookItemHealing) bookItem;
			state.getMainCharacter().heal(bookItemHealing.getHp());
			state.getMainCharacter().getItems().remove(listItemState.get(choix));
		}
		//Si l'item choisi est de type arme, ajoute l'item
		else if(bookItem instanceof BookItemWeapon){
			state.setBookItemArme((BookItemWeapon) bookItem);
		}
	}
	
	@Override
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax){
		int choix = 0;
		
		//Tant que le nombre d'item maximum pouvant être pris est n'est pas atteint
		while(nbItemMax != 0){
			int itemMax = state.getMainCharacter().getItemsMax();
			//Vérifie item maximum et si il y a encore des items à prendre
			if(state.getMainCharacter().getItems().size() < itemMax && !bookItemLinks.isEmpty()){
				//Choix
				Random random = new Random();
				choix = random.nextInt(bookItemLinks.size());
				
				BookItemLink itemLink = bookItemLinks.get(choix);
				
				state.getMainCharacter().addItem(itemLink.getId());
				itemLink.setAmount(itemLink.getAmount()-1);
				
				if(itemLink.getAmount() == 0)
					bookItemLinks.remove(itemLink);
				
				if(nbItemMax != -1)
					nbItemMax--;
			} else {
				nbItemMax = 0;
			}
		}
	}
	
	@Override
	public void execPlayerCreation(Book book, AbstractCharacterCreation characterCreation, BookState state) {
		
		//Choix des items du début
		if(characterCreation instanceof CharacterCreationItem){
			CharacterCreationItem characterCreationItem = (CharacterCreationItem) characterCreation;
			
			prendreItems(state, characterCreationItem.getItemLinks(), characterCreationItem.getAmountToPick());
		}
		//Choix des skills du début
		else if(characterCreation instanceof CharacterCreationSkill){
			CharacterCreationSkill characterCreationSkill = (CharacterCreationSkill) characterCreation;
			
			Random random = new Random();
			int amountToPick = characterCreationSkill.getAmountToPick();
			while(amountToPick != 0 && !characterCreationSkill.getSkillLinks().isEmpty()) {
				int choix = random.nextInt(characterCreationSkill.getSkillLinks().size());
				state.getMainCharacter().addSkill(characterCreationSkill.getSkillLinks().get(choix));
				characterCreationSkill.getSkillLinks().remove(choix);
				amountToPick--;
			}
		}
	}

	@Override
	public BookCharacter chooseEnnemi(List<BookCharacter> listEnnemis) {
		return listEnnemis.get(0);
	}

}
