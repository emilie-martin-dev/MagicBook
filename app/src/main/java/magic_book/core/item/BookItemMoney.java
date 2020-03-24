package magic_book.core.item;

import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemType;

public class BookItemMoney extends BookItem {
	
	public BookItemMoney() {
		this("", "");
	}

	public BookItemMoney(String id, String nom) {
		super(id, nom);
	}

	@Override
	public ItemJson toJson() {
		ItemJson itemJson = super.toJson();		

		itemJson.setItemType(ItemType.MONEY);

		return itemJson;
	}
	

}
