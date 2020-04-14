package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Représente un combat au format JSON
 */
public class CombatJson {
	
	/**
	 * Le choix en cas de victoire
	 */
	private ChoiceJson win;
	/**
	 * Le choix en cas d'évasion
	 */
	private ChoiceJson evasion;
	/**
	 * Le choix en cas de défaite
	 */
	private ChoiceJson loose;
	/**
	 * La liste des ennemis
	 */
	private List<String> enemies;
	/**
	 * Le round à partir duquel on peut s'enfuir
	 */
	@SerializedName("evasion_round")
	private Integer evasionRound;

	/**
	 * Retourne le choix en cas de victoire
	 * @return Le choix en cas de victoire
	 */
	public ChoiceJson getWin() {
		return win;
	}

	/**
	 * Change le choix en cas de victoire
	 * @param win Le choix en cas de victoire
	 */
	public void setWin(ChoiceJson win) {
		this.win = win;
	}

	/**
	 * Récupère le choix en cas d'évasion
	 * @return Le choix en cas d'évasion
	 */
	public ChoiceJson getEvasion() {
		return evasion;
	}

	/**
	 * Change le choix en cas d'évasion
	 * @param evasion Le choix en cas d'évasion
	 */
	public void setEvasion(ChoiceJson evasion) {
		this.evasion = evasion;
	}

	/**
	 * Récupère le choix en cas de défaite
	 * @return Le choix en cas de défaite
	 */
	public ChoiceJson getLoose() {
		return loose;
	}

	/**
	 * Change le choix en cas de défaite
	 * @param loose Le choix en cas de défaite
	 */
	public void setLoose(ChoiceJson loose) {
		this.loose = loose;
	}

	/**
	 * Retourne la liste des ennemies
	 * @return La liste des ennemies
	 */
	public List<String> getEnemies() {
		return enemies;
	}

	/**
	 * Change la liste des ennemies
	 * @param enemies La liste des ennemies
	 */
	public void setEnemies(List<String> enemies) {
		this.enemies = enemies;
	}

	/**
	 * Retourne le round à partir duquel on peut s'enfuir
	 * @return Le round à partir duquel on peut s'enfuir
	 */
	public Integer getEvasionRound() {
		return evasionRound;
	}

	/**
	 * Change le round à partir duquel on peut s'enfuir
	 * @param evasionRound Le round à partir duquel on peut s'enfuir
	 */
	public void setEvasionRound(Integer evasionRound) {
		this.evasionRound = evasionRound;
	}

}
