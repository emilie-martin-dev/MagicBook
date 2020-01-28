package magic_book.core;

import java.util.List;
import java.util.ArrayList;

public class Character { 

	private String id;
	private String name;
	private String race;
	private int baseDamage;
	private int hp;
	private int hpMax;
	private List<String> listSkills;
	private List<String> listItems;
	private int itemsMax;

	public Character(String id, String name, String race, int baseDamage, int hp, int hpMax, List<String> listSkills, List<String> listItems, int itemsMax) {
		this.id = id;
		this.name = name;
		this.race = race;
		this.baseDamage = baseDamage;
		this.hp = hp;
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
	}
	
	public void heal(int amount) {
		this.hp += amount;
		
		if (hp > hpMax) {
			this.hp = hpMax;
		}
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
	
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setRace(String race) {
		this.race = race;
	}
	
	public String getRace() {
		return this.race;
	}
	
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}
	
	public int getBaseDamage() {
		return this.baseDamage;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public int getHp() {
		return this.hp;
	}
		
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}
	
	public int getHpMax() {
		return this.hpMax = hpMax;
	}
	
	public void setListSkills(List<String> listSkills) {
		this.listSkills = listSkills; 
	}
	
	public List<String> getListSkills() {
		return this.listSkills;
	}
	
	public void setListItems(List<String> listItems) {
		this.listItems = listItems; 
	}
	
	public List<String> getListItems() {
		return this.listItems;
	}
	
	public void setItemsMax(int itemsMax) {
		this.itemsMax = itemsMax;
	}
	
	public int getItemsMax() {
		return this.itemsMax;
	}

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
