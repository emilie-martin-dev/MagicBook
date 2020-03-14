package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookCharacter;

public class BookNodeCombat extends AbstractBookNode{
	private BookNodeLink winBookNodeLink;
	private BookNodeLink looseBookNodeLink;
	private BookNodeLink evasionBookNodeLink;
	private int evasionTurn;
	private List<BookCharacter> ennemies;
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionTurn, List<BookCharacter> ennemies){
		super(text);
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionTurn = evasionTurn;
		this.ennemies = ennemies;
		
	}

	@Override
	public List<BookNodeLink> getChoices() {
		List<BookNodeLink> choices = new ArrayList<>();
		
		if(winBookNodeLink != null)
			choices.add(winBookNodeLink);
		
		if(looseBookNodeLink != null)
			choices.add(looseBookNodeLink);
		
		if(evasionBookNodeLink != null)
			choices.add(evasionBookNodeLink);
		
		return choices;
	}

	public BookNodeLink isWinBookNodeLink() {
		return winBookNodeLink;
	}

	public void setWinBookNodeLink(BookNodeLink winBookNodeLink) {
		this.winBookNodeLink = winBookNodeLink;
	}

	public BookNodeLink isLooseBookNodeLink() {
		return looseBookNodeLink;
	}

	public void setLooseBookNodeLink(BookNodeLink looseBookNodeLink) {
		this.looseBookNodeLink = looseBookNodeLink;
	}

	public BookNodeLink getEvasionBookNodeLink() {
		return evasionBookNodeLink;
	}

	public void setEvasionBookNodeLink(BookNodeLink evasionBookNodeLink) {
		this.evasionBookNodeLink = evasionBookNodeLink;
	}

	public int getEvasionTurn() {
		return evasionTurn;
	}

	public void setEvasionTurn(int evasionTurn) {
		this.evasionTurn = evasionTurn;
	}

	public List<BookCharacter> getEnnemies() {
		return ennemies;
	}

	public void setEnnemies(List<BookCharacter> ennemies) {
		this.ennemies = ennemies;
	}
	
}
