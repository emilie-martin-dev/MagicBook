package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CharacterJson {
	
	private String id;
	private String name;
	private int hp;
	@SerializedName("combat_skill")
	private int combatSkill;
	private List<String> skills;
	private List<String> immune;
	@SerializedName("double_damage")
	private boolean doubleDamage;			

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getCombatSkill() {
		return combatSkill;
	}

	public void setCombatSkill(int combatSkill) {
		this.combatSkill = combatSkill;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<String> getImmune() {
		return immune;
	}

	public void setImmune(List<String> immune) {
		this.immune = immune;
	}

	public boolean isDoubleDamage() {
		return doubleDamage;
	}

	public void setDoubleDamage(boolean doubleDamage) {
		this.doubleDamage = doubleDamage;
	}
	
}
