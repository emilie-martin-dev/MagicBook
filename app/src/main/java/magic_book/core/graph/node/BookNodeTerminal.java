package magic_book.core.graph.node;

import java.util.ArrayList;
import magic_book.core.Book;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;

/**
 * Noeud terminal
 */
public class BookNodeTerminal extends AbstractBookNode {

	/**
	 * Statut du noeud
	 */
	private BookNodeStatus bookNodeStatus;

	/**
	 * Constructeur
	 */
	public BookNodeTerminal() {
		this("", null);
	}
	
	/**
	 * Initialisation des valeurs
	 * @param texte Texte du noeud
	 * @param bookNodeStatus Status du noeud: Failure ou Victory
	 */
	public BookNodeTerminal(String texte, BookNodeStatus bookNodeStatus){
		super(texte);
		
		this.bookNodeStatus = bookNodeStatus;
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		buffer.append("\n");
		
		if(bookNodeStatus == BookNodeStatus.FAILURE) {
			buffer.append("Vous avez perdu\n");
		}

		if(bookNodeStatus == BookNodeStatus.VICTORY) {
			buffer.append("Félicitation vous avez gagné\n");
		}
		
		return buffer.toString();
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
		
		sectionJson.setEndType(bookNodeStatus);
		
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		this.setBookNodeStatus(json.getEndType());
	}
	
	/**
	 * Donne le statut du noeud
	 * @return Statut du noeud
	 */
	public BookNodeStatus getBookNodeStatus() {
		return bookNodeStatus;
	}

	/**
	 * Change le statut du noeud
	 * @param bookNodeStatus Le nouveau statut
	 */
	public void setBookNodeStatus(BookNodeStatus bookNodeStatus) {
		this.bookNodeStatus = bookNodeStatus;
	}

	@Override
	public ArrayList<BookNodeLink> getChoices() {
		return new ArrayList<>();
	}

}