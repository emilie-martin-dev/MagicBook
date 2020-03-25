package magic_book.core.item;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.parser.Descriptible;
import magic_book.core.parser.Parsable;

public class BookItem implements Parsable, Descriptible, JsonExportable<ItemJson> {

	private String id;
	private String name;

	public BookItem() {
		this("", "");
	}
	
	public BookItem(String id, String nom) {
		this.id = id;
		this.name = nom;
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Nom : ");
		buffer.append(name);
		buffer.append("\n");
		
		return buffer.toString();
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = new ItemJson();
		
		itemJson.setId(id);
		itemJson.setName(name);
		itemJson.setItemType(ItemType.KEY_ITEM);
		
		return itemJson;
	}

	@Override
	public void fromJson(ItemJson json) {
		id = json.getId();
		name = json.getName();
	}

	@Override
	public String getParsableId() {
		return this.id;
	}

	@Override
	public String getParsableText() {
		return this.name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return this.name;
	}
}