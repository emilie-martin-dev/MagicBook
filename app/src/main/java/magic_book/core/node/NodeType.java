package magic_book.core.node;

public enum NodeType {
	BASIC("Basic"), VICTORY("Victoire"), FAILURE("Échec");

	private String name;

	NodeType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}