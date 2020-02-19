package magic_book.core;

import java.util.List;
import java.util.ArrayList;
import magic_book.core.utils.Parsable;

public class BookCharacter implements Parsable { 

	private String id;
	private String name;
	private String race;
	private int baseDamage;
	private int hp;
	private int hpMax;
	private List<String> listSkills;
	private List<String> listItems;
	private int itemsMax;

	public BookCharacter(String id, String name, String race, int baseDamage, int hpMax, List<String> listSkills, List<String> listItems, int itemsMax) {
		this.id = id;
		this.name = name;
		this.race = race;
		this.baseDamage = baseDamage;
		this.hp = hpMax;
		this.hpMax = hpMax;
		this.listSkills = listSkills;
		this.listItems = listItems;
		this.itemsMax = itemsMax;
		
		if(this.listSkills == null) {
			this.listSkills = new ArrayList<>();
		}
		
		if(this.listItems == null) {
			this.listItems = new ArrayList<>();
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
	
	public void addItem(String item) {
		this.listItems.add(item);
	}

	public void addSkill(String skill) {
		this.listSkills.add(skill);
	}
	
	public void removeItem(String item) {
		this.listItems.remove(item);
	}

	public void removeSkill(String skill) {
		this.listSkills.remove(skill);
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

	public String getRace() {
		return race;
	}

	public void setRace(String race) {
		this.race = race;
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

	public List<String> getListSkills() {
		return listSkills;
	}

	public void setListSkills(List<String> listSkills) {
		this.listSkills = listSkills;
	}

	public List<String> getListItems() {
		return listItems;
	}

	public void setListItems(List<String> listItems) {
		this.listItems = listItems;
	}

	public int getItemsMax() {
		return itemsMax;
	}

	public void setItemsMax(int itemsMax) {
		this.itemsMax = itemsMax;
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
