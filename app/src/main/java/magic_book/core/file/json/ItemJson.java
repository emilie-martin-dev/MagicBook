package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;

public class ItemJson {

	private String id;
	private String name;
	private Integer damage;
	private Integer durability;
	private Integer resistance;
	private Integer hp;
	@SerializedName("item_type")
	private ItemType itemType;

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

	public Integer getDamage() {
		return damage;
	}

	public void setDamage(Integer damage) {
		this.damage = damage;
	}

	public Integer getDurability() {
		return durability;
	}

	public void setDurability(Integer durability) {
		this.durability = durability;
	}

	public Integer getResistance() {
		return resistance;
	}

	public void setResistance(Integer resistance) {
		this.resistance = resistance;
	}

	public Integer getHp() {
		return hp;
	}

	public void setHp(Integer hp) {
		this.hp = hp;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
}
