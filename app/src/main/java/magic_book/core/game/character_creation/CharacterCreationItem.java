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
	 * Constructeur basique
	 */
	public CharacterCreationItem() {
		this("", null, -1);
	}
	
	/**
	 * Constructeur complet
	 * @param text Texte à afficher
	 * @param itemLinks Liste des items disponibles
	 * @param amountToPick Nombre d'item maximum pouvant être pris
	 */
	public CharacterCreationItem(String text, List<BookItemLink> itemLinks, int amountToPick) {
		super(text);
		
		this.itemLinks = itemLinks;
		this.amountToPick = amountToPick;
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
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
		
		characterCreationJson.setType(TypeJson.ITEM);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.itemLinks.clear();
		
		if(json.getItems() != null) {
			for(ItemLinkJson itemLinkJson : json.getItems()) {
				BookItemLink bookItemsLink = new BookItemLink();
				bookItemsLink.fromJson(itemLinkJson);

				itemLinks.add(bookItemsLink);
			}
		}

		amountToPick = json.getAmountToPick();
	}
	
	/**
	 * Ajoute l'item à la liste
	 * @param itemLink Nouvelle item à ajouter
	 */
	public void addItemLink(BookItemLink itemLink) {
		this.itemLinks.add(itemLink);
	}

	/**
	 * Donnne la liste d'item disponibles
	 * @return Liste d'item
	 */
	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	/**
	 * Modifie la liste d'item
	 * @param itemLinks Nouvelle liste d'item
	 */
	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
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
