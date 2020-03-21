package magic_book.core.requirement;

import magic_book.core.game.BookState;

public class RequirementItem extends AbstractRequirement{
	
	private String itemId;
	
	public RequirementItem(String itemId){
		this.itemId = itemId;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		for (String i : state.getMainCharacter().getItems()){
			if(i.equals(itemId)) {
				return true;
			}
		}
		
		return false;
	}
}
