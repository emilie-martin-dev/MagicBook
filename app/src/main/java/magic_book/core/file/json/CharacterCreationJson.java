package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Représente une étape de la conception du personnage au format JSON
 */
public class CharacterCreationJson {
	
	/**
	 * Le texte à afficher lors de cette étape
	 */
	private String text;
	/**
	 * Le type d'étape
	 */
	private TypeJson type;
	/**
	 * Le montant d'éléments à prendre
	 */
	@SerializedName("amount_to_pick")
	private Integer amountToPick;
	/**
	 * Les items que l'on peut prendre
	 */
	private List<ItemLinkJson> items;
	/**
	 * Les items disponibles à la vente
	 */
	private List<ItemLinkJson> shop;
	/**
	 * Les skills que l'on peut prendre
	 */
	private List<String> skills;

	
	/**
	 * Retourne le texte de l'étape
	 * @return Le texte de l'étape
	 */
	public String getText() {
		return text;
	}

	/**
	 * Change le texte de l'étape
	 * @param text Le texte de l'étape
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Récupère le type de l'étape
	 * @return Le type de l'étape
	 */
	public TypeJson getType() {
		return type;
	}

	/**
	 * Change le type de l'étape
	 * @param type Le type de l'étape
	 */
	public void setType(TypeJson type) {
		this.type = type;
	}

	/**
	 * Retourne le montant d'éléments à prendre
	 * @return Le montant d'éléments à prendre
	 */
	public Integer getAmountToPick() {
		return amountToPick;
	}

	/**
	 * Change le montant d'éléments à prendre
	 * @param amountToPick Le montant d'éléments à prendre
	 */
	public void setAmountToPick(Integer amountToPick) {
		this.amountToPick = amountToPick;
	}

	/**
	 * Retourne les items que l'on peut prendre
	 * @return Les items que l'on peut prendre
	 */
	public List<ItemLinkJson> getItems() {
		return items;
	}

	/**
	 * Change les items que l'on peut prendre
	 * @param items Les items que l'on peut prendre
	 */
	public void setItems(List<ItemLinkJson> items) {
		this.items = items;
	}

	/**
	 * Retourne les skills que l'on peut prendre
	 * @return Les skills que l'on peut prendre
	 */
	public List<String> getSkills() {
		return skills;
	}

	/**
	 * Change les skills que l'on peut prendre
	 * @param skills Les skills que l'on peut prendre
	 */
	public void setSkills(List<String> skills) {
		this.skills = skills;
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
	
}
