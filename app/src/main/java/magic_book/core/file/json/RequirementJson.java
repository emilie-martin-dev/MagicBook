package magic_book.core.file.json;

/**
 * Représente un prérequis au format JSON
 */
public class RequirementJson {

	/**
	 * L'id de l'élément en prérequis
	 */
	private String id;
	/**
	 * Le type de prérequis
	 */
	private TypeJson type;
	/**
	 * Le montant requis (pour l'argent par exemple)
	 */
	private Integer amount;
	
	/**
	 * Inverse le prérequis
	 */
	private Boolean not;

	/**
	 * Retourne l'id de l'élément en prérequis
	 * @return L'id de l'élément en prérequis
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'id de l'élément en prérequis
	 * @param id L'id de l'élément en prérequis
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retourne le type de prérequis
	 * @return Le type de prérequis
	 */
	public TypeJson getType() {
		return type;
	}

	/**
	 * Change le type de prérequis
	 * @param type Le type de prérequis
	 */
	public void setType(TypeJson type) {
		this.type = type;
	}

	/**
	 * Récupère le montant requis
	 * @return Le montant requis
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * Change le montant requis
	 * @param amount Le montant requis
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	/**
	 * Récupère si le prérequis est inversé ou non
	 * @return True si le prérequis est inversé, false sinon
	 */
	public Boolean getNot() {
		return not;
	}

	/**
	 * Change si le prérequis est inversé ou non
	 * @param not True si le prérequis est inversé, false sinon
	 */
	public void setNot(Boolean not) {
		this.not = not;
	}
	
}
