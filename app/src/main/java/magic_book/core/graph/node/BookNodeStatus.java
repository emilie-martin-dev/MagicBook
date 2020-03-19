package magic_book.core.graph.node;


public enum BookNodeStatus {
	VICTORY("Victoire"), FAILURE("Défaite");

	private String name;

	BookNodeStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
