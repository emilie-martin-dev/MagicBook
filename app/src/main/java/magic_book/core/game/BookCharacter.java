package magic_book.core.game;

import java.util.List;
import java.util.ArrayList;
import magic_book.core.parser.Parsable;

public class BookCharacter implements Parsable { 

	private String id;
	private String name;
	private int baseDamage;
	private int hp;
	private int hpMax;
	private List<String> skills;
	private List<String> immunes;
	private List<String> items;
	private int itemsMax;
	private boolean doubleDamage;

	public BookCharacter(String id, String name, int baseDamage, int hpMax, List<String> listSkills, List<String> listItems, int itemsMax) {
		this(id, name, baseDamage, hpMax, listSkills, null, listItems, itemsMax, false);
	}
	
	public BookCharacter(String id, String name, int baseDamage, int hpMax, List<String> listSkills, List<String> listImmune, List<String> listItems, int itemsMax, boolean doubleDamage) {
		this.id = id;
		this.name = name;
		this.baseDamage = baseDamage;
		this.hp = hpMax;
		this.hpMax = hpMax;
		this.skills = listSkills;
		this.immunes = listImmune;
		this.items = listItems;
		this.itemsMax = itemsMax;
		this.doubleDamage = doubleDamage;
		
		if(this.skills == null) {
			this.skills = new ArrayList<>();
		}
		
		if(this.immunes == null) {
			this.immunes = new ArrayList<>();
		}
		
		if(this.items == null) {
			this.items = new ArrayList<>();
		}
	}
	
	public boolean isAlive(int hp) {
		return this.hp > 0;
	}
	
	public void damage(int amount) {
		this.hp -= amount;
		if (this.hp < 0){
			this.hp = 0;
		}
	}
	
	public void heal(int amount) {
		this.hp += amount;
		
		if (hp > hpMax) {
			this.hp = hpMax;
		}
	}
	
	@Override
	public String getParsableId() {
		return this.id;
	}
	
	@Override
	public String getParsableText() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public void addItem(String item) {
		this.items.add(item);
	}

	public void addSkill(String skill) {
		this.skills.add(skill);
	}
	
	public void removeItem(String item) {
		this.items.remove(item);
	}

	public void removeSkill(String skill) {
		this.skills.remove(skill);
	}

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

	public int getBaseDamage() {
		return baseDamage;
	}

	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public List<String> getSkills() {
		return skills;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public List<String> getImmunes() {
		return immunes;
	}

	public void setImmunes(List<String> immunes) {
		this.immunes = immunes;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public int getItemsMax() {
		return itemsMax;
	}

	public void setItemsMax(int itemsMax) {
		this.itemsMax = itemsMax;
	}

	public boolean isDoubleDamage() {
		return doubleDamage;
	}

	public void setDoubleDamage(boolean doubleDamage) {
		this.doubleDamage = doubleDamage;
	}

}