package magic_book.core.node;

public enum BookNodeType {
	BASIC("Basic"), VICTORY("Victoire"), FAILURE("Échec");

	private String name;

	BookNodeType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}