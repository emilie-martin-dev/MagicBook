package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.item.BookItemLink;

/**
 * Étape de la "Création du personnage" dans lequel on peut acheter des items de départ
 */
public class CharacterCreationShop extends AbstractCharacterCreation {

	/**
	 * Nombre d'item pouvant être pris
	 */
	private int amountToPick;
	/**
	 * Liste des items à acheter
	 */
	private List<BookItemLink> itemShopLinks;
	
	/**
	 * Constructeur basique
	 */
	public CharacterCreationShop() {
		this("", null, -1);
	}
	
	/**
	 * Constructeur complet
	 * @param text Texte à afficher
	 * @param itemShopLinks Liste des items à acheter
	 * @param amountToPick Nombre d'item maximum pouvant être pris
	 */
	public CharacterCreationShop(String text, List<BookItemLink> itemShopLinks, int amountToPick) {
		super(text);
		
		this.itemShopLinks = itemShopLinks;
		this.amountToPick = amountToPick;
	
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
		
		characterCreationJson.setShop(new ArrayList<>());
		for(BookItemLink itemShopLinks : itemShopLinks) {
			characterCreationJson.getShop().add(itemShopLinks.toJson());
		}
		
		characterCreationJson.setType(TypeJson.SHOP);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.itemShopLinks.clear();
		
		if(json.getShop() != null) {
			for(ItemLinkJson itemShopLinkJson : json.getShop()) {
				BookItemLink bookItemsShopLink = new BookItemLink();
				bookItemsShopLink.fromJson(itemShopLinkJson);

				itemShopLinks.add(bookItemsShopLink);
			}
		}

		amountToPick = json.getAmountToPick();
	}
	
	/**
	 * Ajoute l'item à la liste d'item à acheter
	 * @param itemShopLink Nouvelle item à ajouter 
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
	 * @param itemShopLinks Nouvelle liste d'item à acheter
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
