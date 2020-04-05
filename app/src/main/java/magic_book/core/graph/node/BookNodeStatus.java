package magic_book.core.graph.node;

/**
 * Status d'une partie
 */
public enum BookNodeStatus {
	/**
	 * Victoire ou Défaite
	 */
	VICTORY("Victoire"), FAILURE("Défaite");

	/**
	 * Permet d'écrire leurs nom
	 */
	private String name;

	/**
	 * Constructeur, prend le nom du statu en paramètre
	 * @param name Nom du BookNodeState
	 */
	BookNodeStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
