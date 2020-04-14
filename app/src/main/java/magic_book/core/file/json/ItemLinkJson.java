package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;

/**
 * Permet de faire référence à un item au format JSON
 */
public class ItemLinkJson {
	
	/**
	 * L'id de l'item auquel on fait référence
	 */
	private String id;
	/**
	 * La quantité d'items disponibles
	 */
	private Integer amount;
	/**
	 * Le prix de l'item
	 */
	private Integer price;
	/**
	 * Le prix de rachat de l'item
	 */
	@SerializedName("selling_price")
	private Integer sellingPrice;
	/**
	 * Indique si l'on prend automatiquement l'item
	 */
	private Boolean auto;

	/**
	 * Retourne l'id de l'item auquel on fait référence
	 * @return L'id de l'item auquel on fait référence
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'id de l'item auquel on fait référence
	 * @param id L'id de l'item auquel on fait référence
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retourne la quantité d'items disponibles
	 * @return La quantité d'items disponibles
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * Change la quantité d'items disponibles
	 * @param amount La quantité d'items disponibles
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * Retourne le prix de l'item
	 * @return Le prix de l'item
	 */
	public Integer getPrice() {
		return price;
	}

	/**
	 * Change le prix de l'item
	 * @param price Le prix de l'item
	 */
	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * Retourne le prix de rachat de l'item
	 * @return Le prix de rachat de l'item
	 */
	public Integer getSellingPrice() {
		return sellingPrice;
	}

	/**
	 * Change le prix de rachat de l'item
	 * @param sellingPrice Le prix de rachat de l'item
	 */
	public void setSellingPrice(Integer sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	/**
	 * Retourne si l'on prend automatiquement l'item
	 * @return True si l'on prend automatiquement l'item, false sinon
	 */
	public Boolean isAuto() {
		return auto;
	}

	/**
	 * Change si l'on prend automatiquement l'item
	 * @param auto True si l'on prend automatiquement l'item, false sinon
	 */
	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

}
