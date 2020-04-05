package magic_book.core.requirement;

import magic_book.core.Book;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookState;

public class RequirementItem extends AbstractRequirement {
	
	private String itemId;
	
	public RequirementItem() {
		this("");
	}
	
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

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Vous devez posséder l'item ");
		buffer.append(book.getItems().get(itemId).getName());
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public RequirementJson toJson() {
		RequirementJson requirementJson = new RequirementJson();
		
		requirementJson.setId(itemId);
		requirementJson.setType(TypeJson.ITEM);
		
		return requirementJson;
	}

	@Override
	public void fromJson(RequirementJson json) {
		itemId = json.getId();
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
