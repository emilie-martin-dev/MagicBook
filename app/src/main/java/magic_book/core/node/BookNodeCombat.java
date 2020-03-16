package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;

public class BookNodeCombat extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	private BookNodeLink winBookNodeLink;
	private BookNodeLink looseBookNodeLink;
	private BookNodeLink evasionBookNodeLink;
	private int evasionRound;
	private List<BookCharacter> ennemies;
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionRound, List<BookCharacter> ennemies){
		this(text, winBookNodeLink, looseBookNodeLink, evasionBookNodeLink, evasionRound, ennemies, 0, null, null);
	}
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionTurn, List<BookCharacter> ennemies, int nbItemsAPrendre, List<BookItem> items, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, items, choices);
		
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionRound = evasionTurn;
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
		
		choices.addAll(super.getChoices());
		
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

	public int getEvasionRound() {
		return evasionRound;
	}

	public void setEvasionRound(int evasionRound) {
		this.evasionRound = evasionRound;
	}

	public List<BookCharacter> getEnnemies() {
		return ennemies;
	}

	public void setEnnemies(List<BookCharacter> ennemies) {
		this.ennemies = ennemies;
	}
	
}
