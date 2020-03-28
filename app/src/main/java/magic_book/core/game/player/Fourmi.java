package magic_book.core.game.player;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;

public class Fourmi implements InterfacePlayerFourmis{
	private BookState state;
	private HashMap<String, BookItem>  mapBookItem = new HashMap();
	private HashMap<String, BookCharacter>  mapCharacter = new HashMap();
	
	
	private AbstractBookNode bookNodeChoice;
	private int str;
	
	private int victoire;
	private boolean mort;
	private boolean choix;
	private BookItemWeapon bookItemArme;
	private BookItemDefense bookItemDefense;

	private Random random = new Random();
	
	public Fourmi(BookState state, HashMap<String, BookItem> mapBookItem, HashMap<String, BookCharacter> mapCharacter){
		this.state = state;
		this.mapBookItem = mapBookItem;
		this.mapCharacter = mapCharacter;

		
		this.bookNodeChoice = bookNodeChoice;
		this.mort = mort;
		
	}

	
	public int fourmisChoices(int nbr){
		Random random = new Random();
		str = random.nextInt(nbr);
		return str;
	}
	
	

	@Override
	public int execNodeCombat(int nbr){
		choix = false;
		while(choix == false){
			str = random.nextInt(nbr);
			if(str ==2 || str == 0){
				choix = true;
			}
			//Si inventaire, il choisis puis reviens sur le choix
			if (str == 1){
				if(!state.getMainCharacter().getItems().isEmpty())
					useInventaire();
			}
		}
		return str;
	}


	@Override
	public void execNodeTerminal(BookNodeTerminal node) {
		if(node.getBookNodeStatus() == BookNodeStatus.VICTORY){
			this.victoire = 1;
		}
	}
	
	public BookState verifGetNodeItem(List<String> listItemState, List<BookItem> listItemNode, int nbItemDispo){
		while(nbItemDispo != 0){
				int itemMax = state.getMainCharacter().getItemsMax();
				if((listItemState.size()) == itemMax && itemMax!= 0){
					str = random.nextInt(listItemState.size());
					listItemState.remove(listItemState.get(str));
					state.getMainCharacter().setItems(listItemState);
				} else {
					str = random.nextInt(listItemNode.size());

					listItemState.add(listItemNode.get(str).getId());							
					listItemNode.remove(listItemNode.get(str));
				}
				nbItemDispo -= 1;
		}
		return state;
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

}
