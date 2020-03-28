package magic_book.core.game.player;

import java.util.List;
import java.util.Random;
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

public class Fourmi implements InterfacePlayerFourmis{
	
	public Fourmi(){
	}
	
	@Override
	public int makeAChoice(AbstractBookNodeWithChoices node) {
		Random random = new Random();
		return random.nextInt(node.getChoices().size());
	}
	
	@Override
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state) {
		boolean choixValide = false;
		Random random = new Random();
		ChoixCombat choixCombat = null;
		
		while(!choixValide){
			int choix = random.nextInt(ChoixCombat.values().length);
			choixCombat = ChoixCombat.values()[choix];
			
			//Si inventaire, il choisis puis reviens sur le choix
			if (choixCombat == ChoixCombat.INVENTAIRE){
				if(!state.getMainCharacter().getItems().isEmpty())
					useInventaire(state);
			} else {
				choixValide = true;
			}
		}
		
		return choixCombat;
	}
		
	public void useInventaire(BookState state){
		List<String> listItemState = state.getMainCharacter().getItems();
		
		Random random = new Random();
		int choix = random.nextInt(listItemState.size());
		BookItem bookItem = state.getBook().getItems().get(listItemState.get(choix));
		
		if(bookItem instanceof BookItemDefense){
			state.setBookItemDefense((BookItemDefense) bookItem);
		} else if(bookItem instanceof BookItemHealing){
			BookItemHealing bookItemHealing = (BookItemHealing) bookItem;
			state.getMainCharacter().setHp((state.getMainCharacter().getHp() + bookItemHealing.getHp()));
			state.getMainCharacter().getItems().remove(listItemState.get(choix));
		} else if(bookItem instanceof BookItemWeapon){
			state.setBookItemArme((BookItemWeapon) bookItem);
		}
	}
	
	@Override
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax){
		int choix = 0;
		while(nbItemMax != 0 || choix != -1){
			int itemMax = state.getMainCharacter().getItemsMax();
			
			if(state.getMainCharacter().getItems().size() < itemMax){
				Random random = new Random();
				choix = random.nextInt(bookItemLinks.size());
				
				BookItemLink itemLink = bookItemLinks.get(choix);
				
				state.getMainCharacter().addItem(itemLink.getId());
				itemLink.setAmount(itemLink.getAmount()-1);
				
				if(itemLink.getAmount() == 0)
					bookItemLinks.remove(itemLink);
			} else {
				choix = -1;
			}
			
			if(nbItemMax != -1)
				nbItemMax--;
		}
	}
	
	@Override
	public BookCharacter execPlayerCreation(Book book) {
		return new BookCharacter("Test", "Personnage Test", 3, 50, null, null, null, 5, true);
	}

	@Override
	public BookCharacter chooseEnnemi(List<BookCharacter> listEnnemis) {
		return listEnnemis.get(0);
	}

}
