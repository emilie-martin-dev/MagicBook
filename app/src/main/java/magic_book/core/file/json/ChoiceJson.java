package magic_book.core.file.json;

public class ChoiceJson {

	private String text;
	private int section;
	private int weight;
	private int hp;
	private int gold;
	private boolean auto;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
}
