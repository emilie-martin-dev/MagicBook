package magic_book.core.node;


public enum BookNodeStatus {
	VICTORY("Victoire"), FAILURE("Perdu");

	private String name;

	BookNodeStatus(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
