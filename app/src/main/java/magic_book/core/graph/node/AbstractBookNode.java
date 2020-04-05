package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.parser.Descriptible;
import magic_book.core.parser.TextParser;

/**
 * Classe mère de toutes les classes représentant les noeuds
 */
public abstract class AbstractBookNode implements Descriptible, JsonExportable<SectionJson> {
	
	/**
	 * Texte du noeud
	 */
	private String text;
	
	/**
	 * Initialisation des valeurs
	 * @param text Texte du noeud
	 */
	public AbstractBookNode(String text){
		this.text = text;
	}
	
	/**
	 * Méthode permetant de donner la liste des choix
	 * @return Liste de choix de BookNodeLink ou un BookNodeLinkRandom
	 */
	public abstract <T extends BookNodeLink> List<T> getChoices();

	/**
	 * Description des choix
	 * @param book Livre contenant toutes les informations
	 * @return Un texte en remplacent les id des items/personnages par leurs nom
	 */
	public String getDescription(Book book) {
		return TextParser.parseText(text, book.getItems(), book.getCharacters())+"\n";
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = new SectionJson();
		
		sectionJson.setText(text);
		
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		setText(json.getText());
	}
	
	/**
	 * Donne le texte du noeud
	 * @return Texte du noeud
	 */
	public String getText() {
		return text;
	}

	/**
	 * Modifie le texte du noeud
	 * @param text Nouveau texte du noeud
	 */
	public void setText(String text) {
		this.text = text;
	}
	
}
