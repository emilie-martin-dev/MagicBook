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
	private Integer amountToPick;
	private List<ItemLinkJson> shop;
	private List<ChoiceJson> choices;
	private CombatJson combat;
	@SerializedName("alterance_choice")
	private Boolean alteranceChoice;
	@SerializedName("trim_choices")
	private Boolean trimChoices;
	@SerializedName("is_random_pick")
	private Boolean isRandomPick;
	@SerializedName("must_eat")
	private Boolean mustEat;
	private Integer hp;

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

	public Integer getAmountToPick() {
		return amountToPick;
	}

	public void setAmountToPick(Integer amountToPick) {
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

	public Boolean isAlteranceChoice() {
		return alteranceChoice;
	}

	public void setAlteranceChoice(Boolean alteranceChoice) {
		this.alteranceChoice = alteranceChoice;
	}

	public Boolean getTrimChoices() {
		return trimChoices;
	}

	public void setTrimChoices(Boolean trimChoices) {
		this.trimChoices = trimChoices;
	}

	public Boolean isRandomPick() {
		return isRandomPick;
	}

	public void setIsRandomPick(Boolean isRandomPick) {
		this.isRandomPick = isRandomPick;
	}

	public Boolean getMustEat() {
		return mustEat;
	}

	public void setMustEat(Boolean mustEat) {
		this.mustEat = mustEat;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

		
}
