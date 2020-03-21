package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.HashMap;
import magic_book.core.Book;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeTerminal extends AbstractBookNode {

	private BookNodeStatus bookNodeStatus;

	public BookNodeTerminal(String texte, BookNodeStatus bookNodeStatus){
		super(texte);
		
		this.bookNodeStatus = bookNodeStatus;
	}

	@Override
	public String getTextForBookText(Book book, HashMap<AbstractBookNode, Integer> nodesIndex) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getTextForBookText(book, nodesIndex));
		buffer.append("\n");
		
		if(bookNodeStatus == BookNodeStatus.FAILURE) {
			buffer.append("Vous avez perdu\n");
		}

		if(bookNodeStatus == BookNodeStatus.VICTORY) {
			buffer.append("Félicitation vous avez gagné\n");
		}
		
		return buffer.toString();
	}
	
	
	public BookNodeStatus getBookNodeStatus() {
		return bookNodeStatus;
	}

	public void setBookNodeStatus(BookNodeStatus bookNodeStatus) {
		this.bookNodeStatus = bookNodeStatus;
	}

	@Override
	public ArrayList<BookNodeLink> getChoices() {
		return new ArrayList<>();
	}

}