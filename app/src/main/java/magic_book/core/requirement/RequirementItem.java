package magic_book.core.requirement;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.parser.Descriptible;

public class RequirementItem extends AbstractRequirement implements Descriptible {
	
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

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Vous devez posséder l'item ");
		buffer.append(book.getItems().get(itemId).getName());
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
