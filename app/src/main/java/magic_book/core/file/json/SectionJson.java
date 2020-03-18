package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import magic_book.core.node.BookNodeStatus;

public class SectionJson {

	private String text;
	@SerializedName("end_type")
	private BookNodeStatus endType;
	private List<ItemLinkJson> items;
	@SerializedName("amount_to_pick")
	private int amountToPick;
	private List<ItemLinkJson> shop;
	private List<ChoiceJson> choices;
	private CombatJson combat;
	@SerializedName("alterance_choice")
	private boolean alteranceChoice;
	@SerializedName("trim_choices")
	private boolean trimChoices;
	@SerializedName("is_random_pick")
	private boolean isRandomPick;
	@SerializedName("must_eat")
	private boolean mustEat;
	private int hp;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BookNodeStatus getEndType() {
		return endType;
	}

	public void setEndType(BookNodeStatus endType) {
		this.endType = endType;
	}

	public List<ItemLinkJson> getItems() {
		return items;
	}

	public void setItems(List<ItemLinkJson> items) {
		this.items = items;
	}

	public int getAmountToPick() {
		return amountToPick;
	}

	public void setAmountToPick(int amountToPick) {
		this.amountToPick = amountToPick;
	}

	public List<ItemLinkJson> getShop() {
		return shop;
	}

	public void setShop(List<ItemLinkJson> shop) {
		this.shop = shop;
	}

	public List<ChoiceJson> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceJson> choices) {
		this.choices = choices;
	}

	public CombatJson getCombat() {
		return combat;
	}

	public void setCombat(CombatJson combat) {
		this.combat = combat;
	}

	public boolean isAlteranceChoice() {
		return alteranceChoice;
	}

	public void setAlteranceChoice(boolean alteranceChoice) {
		this.alteranceChoice = alteranceChoice;
	}

	public boolean isTrimChoices() {
		return trimChoices;
	}

	public void setTrimChoices(boolean trimChoices) {
		this.trimChoices = trimChoices;
	}

	public boolean isRandomPick() {
		return isRandomPick;
	}

	public void setIsRandomPick(boolean isRandomPick) {
		this.isRandomPick = isRandomPick;
	}

	public boolean isMustEat() {
		return mustEat;
	}

	public void setMustEat(boolean mustEat) {
		this.mustEat = mustEat;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	
}
