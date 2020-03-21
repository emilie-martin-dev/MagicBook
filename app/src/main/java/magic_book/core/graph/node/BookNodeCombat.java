package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeCombat extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	private BookNodeLink winBookNodeLink;
	private BookNodeLink looseBookNodeLink;
	private BookNodeLink evasionBookNodeLink;
	private int evasionRound;
	private List<String> ennemiesId;
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionRound, List<String> ennemiesId){
		this(text, winBookNodeLink, looseBookNodeLink, evasionBookNodeLink, evasionRound, ennemiesId, 0, null, null, null);
	}
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionTurn, List<String> ennemiesId, int nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
		
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionRound = evasionTurn;
		this.ennemiesId = ennemiesId;
		
		if(this.ennemiesId == null)
			this.ennemiesId = new ArrayList<>();
	}
	
	
	@Override
	public void removeChoice(BookNodeLink nodeLink) {
		if(nodeLink == winBookNodeLink) {
			winBookNodeLink = null;
		} 
		
		if(nodeLink == looseBookNodeLink) {
			looseBookNodeLink = null;			
		}
		
		if(nodeLink == evasionBookNodeLink) {
			evasionBookNodeLink = null;			
		}
		
		super.removeChoice(nodeLink);
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

	public void addEnnemieId(String ennemieId) {
		this.ennemiesId.add(ennemieId);
	}
	
	public BookNodeLink getWinBookNodeLink() {
		return winBookNodeLink;
	}

	public void setWinBookNodeLink(BookNodeLink winBookNodeLink) {
		this.winBookNodeLink = winBookNodeLink;
	}

	public BookNodeLink getLooseBookNodeLink() {
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

	public List<String> getEnnemiesId() {
		return ennemiesId;
	}

	public void setEnnemiesId(List<String> ennemiesId) {
		this.ennemiesId = ennemiesId;
	}
	
}