package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import magic_book.core.graph.node.BookNodeStatus;

/**
 * Représente un paragraphe au format JSON
 */
public class SectionJson {

	/**
	 * Le texte du paragraphe
	 */
	private String text;
	/**
	 * Le type de fin
	 */
	@SerializedName("end_type")
	private BookNodeStatus endType;
	/**
	 * La liste des items que le personnage peut prendre
	 */
	private List<ItemLinkJson> items;
	/**
	 * Le montant d'item max que l'on peut prendre
	 */
	@SerializedName("amount_to_pick")
	private Integer amountToPick;
	/**
	 * Les items disponibles à la vente
	 */
	private List<ItemLinkJson> shop;
	/**
	 * La liste des choix possibles
	 */
	private List<ChoiceJson> choices;
	/**
	 * Les paramètres du combat
	 */
	private CombatJson combat;
	/**
	 * Paragraphe à choix aléatoire
	 */
	@SerializedName("is_random_pick")
	private Boolean isRandomPick;
	/**
	 * Le personnage doit-il manger
	 */
	@SerializedName("must_eat")
	private Boolean mustEat;
	/**
	 * La quantité d'hp à rendre / enlever
	 */
	private Integer hp;

	/**
	 * Retourne le texte du paragraphe
	 * @return Le texte du paragraphe
	 */
	public String getText() {
		return text;
	}

	/**
	 * Change le texte du paragraphe
	 * @param text Le texte du paragraphe
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Retourne le type de fin
	 * @return Le type de fin
	 */
	public BookNodeStatus getEndType() {
		return endType;
	}

	/**
	 * Change le type de fin
	 * @param endType Le type de fin
	 */
	public void setEndType(BookNodeStatus endType) {
		this.endType = endType;
	}

	/**
	 * Retourne la liste des items que le personnage peut prendre
	 * @return La liste des items que le personnage peut prendre
	 */
	public List<ItemLinkJson> getItems() {
		return items;
	}

	/**
	 * Change la liste des items que le personnage peut prendre
	 * @param items La liste des items que le personnage peut prendre
	 */
	public void setItems(List<ItemLinkJson> items) {
		this.items = items;
	}

	/**
	 * Retourne le montant d'item max que l'on peut prendre
	 * @return le montant d'item max que l'on peut prendre
	 */
	public Integer getAmountToPick() {
		return amountToPick;
	}

	/**
	 * Change le montant d'item max que l'on peut prendre
	 * @param amountToPick Le montant d'item max que l'on peut prendre
	 */
	public void setAmountToPick(Integer amountToPick) {
		this.amountToPick = amountToPick;
	}

	/**
	 * Retourne les items disponibles à la vente
	 * @return Les items disponibles à la vente
	 */
	public List<ItemLinkJson> getShop() {
		return shop;
	}

	/**
	 * Change les items disponibles à la vente
	 * @param shop Les items disponibles à la vente
	 */
	public void setShop(List<ItemLinkJson> shop) {
		this.shop = shop;
	}

	/**
	 * Retourne les choix disponibles
	 * @return Les choix disponibles
	 */
	public List<ChoiceJson> getChoices() {
		return choices;
	}

	/**
	 * Change les choix disponibles
	 * @param choices Les choix disponibles
	 */
	public void setChoices(List<ChoiceJson> choices) {
		this.choices = choices;
	}

	/**
	 * Retourne les paramètres du combat
	 * @return Les paramètres du combat
	 */
	public CombatJson getCombat() {
		return combat;
	}

	/**
	 * Change les paramètre du combat
	 * @param combat Les paramètres du combat
	 */
	public void setCombat(CombatJson combat) {
		this.combat = combat;
	}

	/**
	 * Retourne si le noeud est aléatoire
	 * @return True si le noeud est aléatoire, false sinon
	 */
	public Boolean isRandomPick() {
		return isRandomPick;
	}

	/**
	 * Change si le noeud est aléatoire
	 * @param isRandomPick True si le noeud est aléatoire, false sinon
	 */
	public void setIsRandomPick(Boolean isRandomPick) {
		this.isRandomPick = isRandomPick;
	}

	/**
	 * Retourne si le joueur doit manger
	 * @return True si le joueur doit manger, false sinon
	 */
	public Boolean getMustEat() {
		return mustEat;
	}

	/**
	 * Change si le joueur doit manger
	 * @param mustEat True si le joueur doit manger, false sinon
	 */
	public void setMustEat(Boolean mustEat) {
		this.mustEat = mustEat;
	}

	/**
	 * Retourne la quantité d'hp enlevé / ajouté
	 * @return La quantité d'hp enlevé / ajouté
	 */
	public Integer getHp() {
		return hp;
	}

	/**
	 * Change la quantité d'hp enlevé / ajouté
	 * @param hp La quantité d'hp enlevé / ajouté
	 */
	public void setHp(Integer hp) {
		this.hp = hp;
	}

}
