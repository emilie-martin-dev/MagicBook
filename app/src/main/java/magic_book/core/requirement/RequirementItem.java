package magic_book.core.requirement;

import magic_book.core.game.BookState;
import magic_book.core.item.BookItem;

public class RequirementItem extends AbstractRequirement{
	
	private BookItem item;
	
	public RequirementItem(BookItem item){
		this.item = item;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		for (String itemState : state.getMainCharacter().getItems()){
			if(item.getName() == itemState) {
				return true;
			}
		}
		return false;
	}
}
