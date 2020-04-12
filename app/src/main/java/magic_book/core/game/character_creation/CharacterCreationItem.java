package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.item.BookItemLink;

/**
 * Étape de la "Création du personnage" dans lequel on peut choisir ses items de départ
 */
public class CharacterCreationItem extends AbstractCharacterCreation {

	/**
	 * Nombre d'item pouvant être pris
	 */
	private int amountToPick;
	/**
	 * Liste des items disponibles
	 */
	private List<BookItemLink> itemLinks;
	/**
	 * Liste des items à acheter
	 */
	private List<BookItemLink> itemShopLinks;
	
	/**
	 * Constructeur basique
	 */
	public CharacterCreationItem() {
		this("", null, null, -1);
	}
	
	/**
	 * Constructeur complet
	 * @param text Texte à afficher
	 * @param itemLinks Liste des items disponibles
	 * @param itemShopLinks Liste des items à acheter
	 * @param amountToPick Nombre d'item maximum pouvant être pris
	 */
	public CharacterCreationItem(String text, List<BookItemLink> itemLinks, List<BookItemLink> itemShopLinks, int amountToPick) {
		super(text);
		
		this.itemLinks = itemLinks;
		this.itemShopLinks = itemShopLinks;
		this.amountToPick = amountToPick;
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
		if(this.itemShopLinks == null)
			this.itemShopLinks = new ArrayList<>();
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		buffer.append("\n");
		buffer.append("Choisissez ");
		buffer.append(amountToPick);
		buffer.append(" items : \n\n");
		
		for(int i = 0 ; i < itemLinks.size() ; i++) {
			buffer.append(itemLinks.get(i).getDescription(book));
			if(i < itemLinks.size() - 1) 
				buffer.append("\n");
		}
		
		buffer.append(" shop : \n\n");
		
		for(int i = 0 ; i < itemShopLinks.size() ; i++) {
			buffer.append(itemShopLinks.get(i).getDescription(book));
			if(i < itemShopLinks.size() - 1) 
				buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	@Override
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = super.toJson();
		
		characterCreationJson.setAmountToPick(amountToPick);
		
		characterCreationJson.setItems(new ArrayList<>());
		for(BookItemLink itemLink : itemLinks) {
			characterCreationJson.getItems().add(itemLink.toJson());
		}
		
		characterCreationJson.setItemsShop(new ArrayList<>());
		for(BookItemLink itemShopLinks : itemShopLinks) {
			characterCreationJson.getItems().add(itemShopLinks.toJson());
		}
		
		characterCreationJson.setType(TypeJson.ITEM);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.itemLinks.clear();
		this.itemShopLinks.clear();
		
		if(json.getItems() != null) {
			for(ItemLinkJson itemLinkJson : json.getItems()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				itemLinks.add(bookItemsLink);
			}
		}
		if(json.getItemsShop() != null) {
			for(ItemLinkJson itemShopLinkJson : json.getItemsShop()) {
				BookItemLink bookItemsShopLink = new BookItemLink();
				bookItemsShopLink.fromJson(itemShopLinkJson);

				itemShopLinks.add(bookItemsShopLink);
			}
		}

		amountToPick = json.getAmountToPick();
	}
	
	/**
	 * Ajoute l'item à la liste d'item à prendre
	 * @param itemLink Nouvelle item à ajouter
	 */
	public void addItemLink(BookItemLink itemLink) {
		this.itemLinks.add(itemLink);
	}

	/**
	 * Donnne la liste d'item disponibles
	 * @return Liste d'item à prendre
	 */
	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	/**
	 * Modifie la liste d'item à prendre
	 * @param itemLinks Nouvelle liste d'item à prendre
	 */
	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
	}
	
	/**
	 * Ajoute l'item à la liste d'item à acheter
	 * @param itemLink Nouvelle item à ajouter dans la liste d'item à acheter
	 */
	public void addItemShopLink(BookItemLink itemShopLink) {
		this.itemShopLinks.add(itemShopLink);
	}

	/**
	 * Donnne la liste d'item à acheter
	 * @return Liste d'item à acheter
	 */
	public List<BookItemLink> getItemShopLinks() {
		return itemShopLinks;
	}

	/**
	 * Modifie la liste d'item à acheter
	 * @param itemLinks Nouvelle liste d'item à acheter
	 */
	public void setItemShopLinks(List<BookItemLink> itemShopLinks) {
		this.itemShopLinks = itemShopLinks;
	}

	/**
	 * Donne le nombre d'item maximum pouvant être pris
	 * @return Nombre d'item maximum
	 */
	public int getAmountToPick() {
		return amountToPick;
	}

	/**
	 * Change le nombre d'item maximum pouvant être prus
	 * @param amountToPick Nouveau nombre d'item maximum
	 */
	public void setAmountToPick(int amountToPick) {
		this.amountToPick = amountToPick;
	}

}
