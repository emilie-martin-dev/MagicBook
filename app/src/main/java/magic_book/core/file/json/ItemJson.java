package magic_book.core.file.json;

public class ItemJson {

	private String id;
	private String name;
	private int endurance;
	private boolean is_consumable;
	private boolean is_permanent;
	private int combat_skill;
	private String ac_section;
	private float item_worth;
	private int value;
	private boolean is_unlimited;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAc_section() {
		return ac_section;
	}

	public void setAc_section(String ac_section) {
		this.ac_section = ac_section;
	}

	public float getItem_worth() {
		return item_worth;
	}

	public void setItem_worth(float item_worth) {
		this.item_worth = item_worth;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isIs_unlimited() {
		return is_unlimited;
	}

	public void setIs_unlimited(boolean is_unlimited) {
		this.is_unlimited = is_unlimited;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public boolean isIs_consumable() {
		return is_consumable;
	}

	public void setIs_consumable(boolean is_consumable) {
		this.is_consumable = is_consumable;
	}

	public boolean isIs_permanent() {
		return is_permanent;
	}

	public void setIs_permanent(boolean is_permanent) {
		this.is_permanent = is_permanent;
	}

	public int getCombat_skill() {
		return combat_skill;
	}

	public void setCombat_skill(int combat_skill) {
		this.combat_skill = combat_skill;
	}

}
