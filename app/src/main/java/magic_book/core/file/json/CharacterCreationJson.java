package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CharacterCreationJson {
	
	private String text;
	private TypeJson type;
	@SerializedName("amount_to_pick")
	private Integer amountToPick;
	private List<ItemLinkJson> items;
	private List<String> skills;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TypeJson getType() {
		return type;
	}

	public void setType(TypeJson type) {
		this.type = type;
	}

	public Integer getAmountToPick() {
		return amountToPick;
	}

	public void setAmountToPick(Integer amountToPick) {
		this.amountToPick = amountToPick;
	}

	public List<ItemLinkJson> getItems() {
		return items;
	}

	public void setItems(List<ItemLinkJson> items) {
		this.items = items;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}
	
	
}
