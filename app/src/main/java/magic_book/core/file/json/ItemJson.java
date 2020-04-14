package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;

/**
 * Représente un item au format JSON
 */
public class ItemJson {

	/**
	 * L'id de l'item
	 */
	private String id;
	/**
	 * Le nom de l'item
	 */
	private String name;
	/**
	 * Les dégats de l'item
	 */
	private Integer damage;
	/**
	 * La durabilité de l'item (nombre d'utilisation)
	 */
	private Integer durability;
	/**
	 * La résistance de l'item (réduction des dégats)
	 */
	private Integer resistance;
	/**
	 * Le nombre de soin de rendu
	 */
	private Integer hp;
	/**
	 * Le type de l'item
	 */
	@SerializedName("item_type")
	private ItemType itemType;

	/**
	 * Retourne l'id
	 * @return L'id 
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'id 
	 * @param id L'id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Retourne le nom 
	 * @return Le nom 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change le nom
	 * @param name Le nom
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne les dégats
	 * @return Les dégats
	 */
	public Integer getDamage() {
		return damage;
	}

	/**
	 * Change les dégats
	 * @param damage Les dégats
	 */
	public void setDamage(Integer damage) {
		this.damage = damage;
	}

	/**
	 * Retourne la durablité
	 * @return La durabilité
	 */
	public Integer getDurability() {
		return durability;
	}

	/**
	 * Change la durabilité
	 * @param durability La durabilité
	 */
	public void setDurability(Integer durability) {
		this.durability = durability;
	}

	/**
	 * Retourne la résistance
	 * @return La résistance
	 */
	public Integer getResistance() {
		return resistance;
	}

	/**
	 * Change la résistance
	 * @param resistance La résistance
	 */
	public void setResistance(Integer resistance) {
		this.resistance = resistance;
	}

	/**
	 * Retourne les pv rendus / enlevés
	 * @return Les pv rendus / enlevés
	 */
	public Integer getHp() {
		return hp;
	}

	/**
	 * Change les pv rendus / enlevés
	 * @param hp Les pv rendus / enlevés
	 */
	public void setHp(Integer hp) {
		this.hp = hp;
	}

	/**
	 * Retourne le type de l'item
	 * @return Le type de l'item
	 */
	public ItemType getItemType() {
		return itemType;
	}

	/**
	 * Change le type de l'item
	 * @param itemType Le type de l'item
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
}
