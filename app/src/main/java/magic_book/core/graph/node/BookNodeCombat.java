package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookNodeCombat extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	private BookNodeLink winBookNodeLink;
	private BookNodeLink looseBookNodeLink;
	private BookNodeLink evasionBookNodeLink;
	private int evasionRound;
	private List<String> ennemiesId;
	
	public BookNodeCombat(){
		this("", null, null, null, 0, null, 0, null, null, null);
	}
	
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
	
	public String getCombatDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("\nVous entrez en combat contre :\n\n");
		for(int i = 0 ; i < ennemiesId.size() ; i++) {
			BookCharacter character = book.getCharacters().get(ennemiesId.get(i));
			buffer.append(character.getDescription(book));
			if(i < ennemiesId.size()-1)
				buffer.append("\n");
		}
		
		buffer.append("Vous pouvez vous évader au tour ");
		buffer.append(evasionRound);
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append(getCombatDescription(book));
		
		if(winBookNodeLink != null) {
			buffer.append("\nSi vous gagnez : \n\n");
			buffer.append(winBookNodeLink.getDescription(book));
		}
			
		if(looseBookNodeLink != null) {
			buffer.append("\nSi vous perdez : \n\n");
			buffer.append(looseBookNodeLink.getDescription(book));
		}
		
		if(evasionBookNodeLink != null) {
			buffer.append("\nSi vous souhaitez vous évader : \n\n");
			buffer.append(evasionBookNodeLink.getDescription(book));
			buffer.append("\n");
		}
		
		return buffer.toString();
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
		CombatJson combatJson = new CombatJson();
				
		if(evasionRound != 0)
			combatJson.setEvasionRound(evasionRound);

		combatJson.setEnemies(ennemiesId);

		if(winBookNodeLink != null) {
			combatJson.setWin(winBookNodeLink.toJson());
		}

		if(looseBookNodeLink != null) {
			combatJson.setLoose(looseBookNodeLink.toJson());
		}

		if(evasionBookNodeLink != null) {
			combatJson.setEvasion(evasionBookNodeLink.toJson());
		}

		sectionJson.setCombat(combatJson);
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		this.evasionRound = 0;
		
		if(json.getCombat().getEvasionRound() != null)
			evasionRound = json.getCombat().getEvasionRound();
		
		for(String ennemie : json.getCombat().getEnemies()) {
			addEnnemieId(ennemie);
		}
		
		if(json.getCombat().getWin() != null) {
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getWin());
			
			winBookNodeLink = bookNodeLink;
		}

		if(json.getCombat().getLoose() != null){
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getLoose());
			
			looseBookNodeLink = bookNodeLink;
		}

		if(json.getCombat().getEvasion() != null){
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getEvasion());
			
			evasionBookNodeLink = bookNodeLink;
		}
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
