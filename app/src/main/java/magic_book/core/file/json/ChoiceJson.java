package magic_book.core.file.json;

public class ChoiceJson {

	private String text;
	private Integer section;
	private Integer weight;
	private Integer hp;
	private Integer gold;
	private Boolean auto;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getSection() {
		return section;
	}

	public void setSection(Integer section) {
		this.section = section;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Boolean getAuto() {
		return auto;
	}

	public void setAuto(Boolean auto) {
		this.auto = auto;
	}
	
}
