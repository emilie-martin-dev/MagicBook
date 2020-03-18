package magic_book.core.file.json;


public class RequirementJson {

	private String id;
	private String type;
	private Integer amount;
	private Boolean not;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
