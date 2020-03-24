package magic_book.core.graph.node;

import java.util.ArrayList;
import magic_book.core.Book;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeTerminal extends AbstractBookNode {

	private BookNodeStatus bookNodeStatus;

	public BookNodeTerminal() {
		this("", null);
	}
	
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