package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;

public class BookNodeCombat extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	private BookNodeLink winBookNodeLink;
	private BookNodeLink looseBookNodeLink;
	private BookNodeLink evasionBookNodeLink;
	private Integer evasionRound;
	private List<String> ennemiesId;
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, Integer evasionRound, List<String> ennemiesId){
		this(text, winBookNodeLink, looseBookNodeLink, evasionBookNodeLink, evasionRound, ennemiesId, 0, null, null, null);
	}
	
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, Integer evasionTurn, List<String> ennemiesId, int nbItemsAPrendre, List<BookItemsLink> itemLinks, List<BookItemsLink> shopItemLinks, List<BookNodeLink> choices){
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

	public Integer getEvasionRound() {
		return evasionRound;
	}

	public void setEvasionRound(Integer evasionRound) {
		this.evasionRound = evasionRound;
	}

	public List<String> getEnnemiesId() {
		return ennemiesId;
	}

	public void setEnnemiesId(List<String> ennemiesId) {
		this.ennemiesId = ennemiesId;
	}
	
}
