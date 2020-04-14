package magic_book.core.file.json;

import java.util.List;

/**
 * Représente un choix au format JSON
 */
public class ChoiceJson {

	/**
	 * Le texte du choix
	 */
	private String text;
	/**
	 * Le numéro du paragraphe suivant
	 */
	private Integer section;
	/**
	 * La probabilité de tirer ce choix si le noeud est aléatoire
	 */
	private Integer weight;
	/**
	 * Le nombre de pv à ajouter / enlever
	 */
	private Integer hp;
	/**
	 * La quantité d'argent à ajouter / enlever
	 */
	private Integer gold;
	/**
	 * Choisir ce choix automatiquement si les conditions sont remplis
	 */
	private Boolean auto;
	/**
	 * La liste des conditions à remplir
	 */
	private List<List<RequirementJson>> requirements;
	
	
	/**
	 * Retourne le texte du choix
	 * @return Le texte du choix
	 */
	public String getText() {
		return text;
	}

	/**
	 * Change le texte du choix
	 * @param text Le texte du choix
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Retourne le numéro du paragraphe suivant
	 * @return Le numéro du paragraphe suivant
	 */
	public Integer getSection() {
		return section;
	}

	/**
	 * Change le numéro du paragraphe suivant
	 * @param section Le numéro du paragraphe suivant
	 */
	public void setSection(Integer section) {
		this.section = section;
	}

	/**
	 * Retourne la probabilité de tirer ce choix si le noeud est aléatoire
	 * @return La probabilité de tirer ce choix si le noeud est aléatoire
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * Change la probabilité de tirer ce choix si le noeud est aléatoire
	 * @param weight La probabilité de tirer ce choix si le noeud est aléatoire
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * Retourne le nombre de pv à ajouter / enlever
	 * @return Le nombre de pv à ajouter / enlever
	 */
	public Integer getHp() {
		return hp;
	}

	/**
	 * Change le nombre de pv à ajouter / enlever
	 * @param hp Le nombre de pv à ajouter / enlever
	 */
	public void setHp(Integer hp) {
		this.hp = hp;
	}

	/**
	 * Retourne la quantité d'argent à ajouter / enlever
	 * @return La quantité d'argent à ajouter / enlever
	 */
	public Integer getGold() {
		return gold;
	}

	/**
	 * Change la quantité d'argent à ajouter / enlever
	 * @param gold La quantité d'argent à ajouter / enlever
	 */
	public void setGold(Integer gold) {
		this.gold = gold;
	}

	/**
	 * Retourne si ce choix est automatiquement sélectionné si les conditions sont remplis
	 * @return True si ce choix est automatiquement sélectionné si les conditions sont remplis, false sinon
	 */
	public Boolean getAuto() {
		return auto;
	}

	/**
	 * Change si ce choix est automatiquement sélectionné si les conditions sont remplis
	 * @param auto True si ce choix est automatiquement sélectionné si les conditions sont remplis, false sinon
	 */
	public void setAuto(Boolean auto) {
		this.auto = auto;
	}

	/**
	 * Retourne la liste des conditions à remplir
	 * @return La liste des conditions à remplir
	 */
	public List<List<RequirementJson>> getRequirements() {
		return requirements;
	}

	/**
	 * Change la liste des conditions à remplir
	 * @param requirements La liste des conditions à remplir
	 */
	public void setRequirements(List<List<RequirementJson>> requirements) {
		this.requirements = requirements;
	}

}
