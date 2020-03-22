package magic_book.core.requirement;

import magic_book.core.game.BookState;

public class RequirementMoney extends AbstractRequirement {

	private String moneyId;
	private int amount;
	
	public RequirementMoney(String moneyId, int amount) {
		this.moneyId = moneyId;
		this.amount = amount;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		return state.getMainCharacter().getMoney(moneyId) >= amount;
	}
	
}
