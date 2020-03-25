package magic_book.core.requirement;

import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.parser.Descriptible;

public class RequirementMoney extends AbstractRequirement {

	private String moneyId;
	private int amount;
	
	public RequirementMoney() {
		this("", 0);
	}
	
	public RequirementMoney(String moneyId, int amount) {
		this.moneyId = moneyId;
		this.amount = amount;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		return state.getMainCharacter().getMoney(moneyId) >= amount;
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Vous devez posséder ");
		buffer.append(amount);
		buffer.append(" ");
		buffer.append(book.getItems().get(moneyId).getName());
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	@Override
	public RequirementJson toJson() {
		RequirementJson requirementJson = new RequirementJson();
		
		requirementJson.setId(moneyId);
		requirementJson.setAmount(amount);
		requirementJson.setType(TypeJson.MONEY);
		
		return requirementJson;
	}

	@Override
	public void fromJson(RequirementJson json) {
		moneyId = json.getId();
		amount = json.getAmount();
	}
	
	public String getMoneyId() {
		return moneyId;
	}

	public void setMoneyId(String moneyId) {
		this.moneyId = moneyId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
