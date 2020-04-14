package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Représente un personnage au format JSON
 */
public class CharacterJson {
	
	/**
	 * L'id du personnage
	 */
	private String id;
	/**
	 * Le nom du personnge
	 */
	private String name;
	/**
	 * La santé max du personnage
	 */
	private Integer hp;
	/**
	 * La quantité de dégat du personnage
	 */
	@SerializedName("combat_skill")
	private Integer combatSkill;
	/**
	 * Les skills du personnages
	 */
	private List<String> skills;
	/**
	 * Les skills auxquels il résiste
	 */
	private List<String> immune;
	/**
	 * Possibilité de doubler ses dégats lors d'une attaque
	 */
	@SerializedName("double_damage")
	private Boolean doubleDamage;
	/**
	 * Le nombre d'item maximum dans l'inventaire
	 */
	@SerializedName("item_max")
	private Integer itemMax;
	/**
	 * La somme d'argent qu'il possède
	 */
	@SerializedName("money")
	private Integer money;

	/**
	 * Retourne l'id du personnage
	 * @return L'id du personnage
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'id du personnage
	 * @param id L'id du personnage
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retourne le nom du personnage
	 * @return Le nom du personnage
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change le nom du personnage
	 * @param name Le nom du personnage
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne la santé maximum du personnage
	 * @return La santé maximum du personnage
	 */
	public Integer getHp() {
		return hp;
	}

	/**
	 * Change la santé maximum du personnage
	 * @param hp La santé maximum du personnage
	 */
	public void setHp(Integer hp) {
		this.hp = hp;
	}

	/**
	 * Retourne la quantité de dégat du personnage
	 * @return La quantité de dégat du personnage
	 */
	public Integer getCombatSkill() {
		return combatSkill;
	}

	/**
	 * Change la quantité de dégat du personnage
	 * @param combatSkill La quantité de dégat du personnage
	 */
	public void setCombatSkill(Integer combatSkill) {
		this.combatSkill = combatSkill;
	}

	/**
	 * Retourne les skills du personnage
	 * @return Les skills du personnage
	 */
	public List<String> getSkills() {
		return skills;
	}

	/**
	 * Change les skills du personnage
	 * @param skills Les skills du personnage
	 */
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	/**
	 * Retourne la liste des skills auxquels le personnage est immunisé
	 * @return La liste des skills auxquels le personnage est immunisé
	 */
	public List<String> getImmune() {
		return immune;
	}

	/**
	 * Change la liste des skills auxquels le personnage est immunisé
	 * @param immune La liste des skills auxquels le personnage est immunisé
	 */
	public void setImmune(List<String> immune) {
		this.immune = immune;
	}

	/**
	 * Retourne si le personnage a la possibilité de doubler ses dégats lors d'une attaque
	 * @return Possibilité de doubler ses dégats lors d'une attaque
	 */
	public Boolean getDoubleDamage() {
		return doubleDamage;
	}

	/**
	 * Change si le personnaga a la possibilité de doubler ses dégats lors d'une attaque
	 * @param doubleDamage Possibilité de doubler ses dégats lors d'une attaque
	 */
	public void setDoubleDamage(Boolean doubleDamage) {
		this.doubleDamage = doubleDamage;
	}

	/**
	 * Retourne le nombre d'items maximum dans l'inventaire
	 * @return Le nombre d'items maximum dans l'inventaire
	 */
	public Integer getItemMax() {
		return itemMax;
	}

	/**
	 * Change le nombre d'items maximum dans l'inventaire
	 * @param itemMax Le nombre d'items maximum dans l'inventaire
	 */
	public void setItemMax(Integer itemMax) {
		this.itemMax = itemMax;
	}

	/**
	 * Retourne la somme d'argent qu'il possède
	 * @return La somme d'argent qu'il possède
	 */
	public Integer getMoney() {
		return money;
	}

	/**
	 * Change la somme d'argent qu'il possède
	 * @param money La somme d'argent qu'il possède
	 */
	public void setMoney(Integer money) {
		this.money = money;
	}
}
