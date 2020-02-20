package magic_book.core.file.json;

import java.util.List;
import magic_book.core.node.BookNodeType;

public class SectionJson {

	private String text;
	private BookNodeType type = BookNodeType.BASIC;
	private boolean alterance_choice;
	private boolean trim_choices;
	private boolean is_random_pick;
	private boolean is_special;
	private boolean must_eat;
	private boolean auto;
	private String ac_section;
	private int n_rounds;
	private String immune;
	private int endurance;
	private boolean double_damage;
	private String name;
	private int combat_skill;
	private boolean no_ambiguity;
	private List<ItemJson> items;
	private List<ChoiceJson> choices;
	private int eat_amount;

	public BookNodeType getType() {
		return type;
	}

	public void setType(BookNodeType type) {
		this.type = type;
	}
	
	public boolean isIs_random_pick() {
		return is_random_pick;
	}

	public void setIs_random_pick(boolean is_random_pick) {
		this.is_random_pick = is_random_pick;
	}

	public boolean isIs_special() {
		return is_special;
	}

	public void setIs_special(boolean is_special) {
		this.is_special = is_special;
	}

	public boolean isMust_eat() {
		return must_eat;
	}

	public void setMust_eat(boolean must_eat) {
		this.must_eat = must_eat;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getAc_section() {
		return ac_section;
	}

	public void setAc_section(String ac_section) {
		this.ac_section = ac_section;
	}

	public int getN_rounds() {
		return n_rounds;
	}

	public void setN_rounds(int n_rounds) {
		this.n_rounds = n_rounds;
	}

	public String getImmune() {
		return immune;
	}

	public void setImmune(String immune) {
		this.immune = immune;
	}

	public int getEndurance() {
		return endurance;
	}

	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}

	public boolean isDouble_damage() {
		return double_damage;
	}

	public void setDouble_damage(boolean double_damage) {
		this.double_damage = double_damage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCombat_skill() {
		return combat_skill;
	}

	public void setCombat_skill(int combat_skill) {
		this.combat_skill = combat_skill;
	}

	public boolean isNo_ambiguity() {
		return no_ambiguity;
	}

	public void setNo_ambiguity(boolean no_ambiguity) {
		this.no_ambiguity = no_ambiguity;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isAlterance_choice() {
		return alterance_choice;
	}

	public void setAlterance_choice(boolean alterance_choice) {
		this.alterance_choice = alterance_choice;
	}

	public boolean isTrim_choices() {
		return trim_choices;
	}

	public void setTrim_choices(boolean trim_choices) {
		this.trim_choices = trim_choices;
	}

	public List<ItemJson> getItems() {
		return items;
	}

	public void setItems(List<ItemJson> items) {
		this.items = items;
	}

	public List<ChoiceJson> getChoices() {
		return choices;
	}

	public void setChoices(List<ChoiceJson> choices) {
		this.choices = choices;
	}

	public int getEat_amount() {
		return eat_amount;
	}

	public void setEat_amount(int eat_amount) {
		this.eat_amount = eat_amount;
	}
	
}
