package magic_book.core.node;


public class BookNodeTerminal {

	private BookNodeStatus bookNodeStatus;
	
	public BookNodeTerminal(BookNodeStatus bookNodeStatus){
		this.bookNodeStatus = bookNodeStatus;
	}
	
	
	public boolean isTerminal() {
		return true;
	}

	public BookNodeStatus getBookNodeStatus() {
		return bookNodeStatus;
	}

	public void setBookNodeStatus(BookNodeStatus bookNodeStatus) {
		this.bookNodeStatus = bookNodeStatus;
	}
	
	
}
