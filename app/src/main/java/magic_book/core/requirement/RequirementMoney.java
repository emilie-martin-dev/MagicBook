package magic_book.core.requirement;

import magic_book.core.game.BookState;

public class RequirementMoney extends AbstractRequirement {

	private int amount;
	private String moneyId;
	
	public RequirementMoney(int amount, String moneyId) {
		this.amount = amount;
		this.moneyId = moneyId;
	}
	
	@Override
	public boolean isSatisfied(BookState state) {
		return state.getMainCharacter().getMoney(moneyId) >= amount;
	}
	
}
