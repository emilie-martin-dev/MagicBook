package magic_book.core.file.json;


public class RequirementJson {

	private String id;
	private TypeJson type;
	private Integer amount;
	private Boolean not;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TypeJson getType() {
		return type;
	}

	public void setType(TypeJson type) {
		this.type = type;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getNot() {
		return not;
	}

	public void setNot(Boolean not) {
		this.not = not;
	}
	
}
