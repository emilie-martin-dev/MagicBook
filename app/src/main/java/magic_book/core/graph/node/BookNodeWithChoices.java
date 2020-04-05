package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.item.BookItemLink;

/**
 * Noeud avec choix basic
 */
public class BookNodeWithChoices extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	/**
	 * Constructeur vide d'un noeud basic
	 */
	public BookNodeWithChoices() {
		this("");
	}
	
	/**
	 * Constructeur avec texte
	 * @param text Tete du noeud
	 */
	public BookNodeWithChoices(String text) {
		super(text, 0, null, null, null);
	}
	
	/**
	 * Initialisation des valeurs
	 * @param text Texte du noeud basic
	 * @param nbItemsAPrendre Nombre d'item maximum pouvant être pris sur ce noeud (-1 représente l'infini)
	 * @param itemLinks Liste des items disponible sur ce noeud
	 * @param shopItemLinks Liste des items pouvant être acheté sur ce noeud
	 * @param choices Liste de BookNodeLink représentant tout les choix possible à partir de ce noeud
	 */
	public BookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
		
	}	

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
				
		if(json.getChoices() != null) {
			for(ChoiceJson choiceJson : json.getChoices()) {		
				BookNodeLink nodeLink = new BookNodeLink();
				nodeLink.fromJson(choiceJson);
				
				addChoice(nodeLink);
			}
		}
	}

}
