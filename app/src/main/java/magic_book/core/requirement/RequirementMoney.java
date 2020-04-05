package magic_book.core.requirement;

import magic_book.core.Book;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookState;

/**
 * Requiert un certains montant d'argent
 */
public class RequirementMoney extends AbstractRequirement {

	/**
	 * id de la monnaie
	 */
	private String moneyId;
	/**
	 * Montant d'argent minimum à posséder
	 */
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
	
	/**
	 * Donne l'id de la monnaie
	 * @return L'id de la monnaie
	 */
	public String getMoneyId() {
		return moneyId;
	}
	
	/**
	 * Change l'id de la monnaie
	 * @param moneyId La nouvelle id de la monnaie
	 */
	public void setMoneyId(String moneyId) {
		this.moneyId = moneyId;
	}

	/**
	 * Donne le montant minimum requis
	 * @return Le montant minimum requis
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Change le montant minimum requis
	 * @param amount Le montant minimum requis
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
