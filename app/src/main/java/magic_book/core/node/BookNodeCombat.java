package magic_book.core.node;

import java.util.List;
import magic_book.core.BookCharacter;
import magic_book.window.gui.NodeFx;


public class BookNodeCombat extends AbstractBookNode{
	private boolean winBookNodeLink;
	private boolean looseBookNodeLink;
	private NodeFx evasionBookNodeLink;
	private int evasionTurn;
	private List<BookCharacter> ennemies;
	
	public BookNodeCombat(String text, boolean winBookNodeLink, boolean looseBookNodeLink, NodeFx evasionBookNodeLink, int evasionTurn, List<BookCharacter> ennemies){
		super(text);
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionTurn = evasionTurn;
		this.ennemies = ennemies;
		
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	public boolean isWinBookNodeLink() {
		return winBookNodeLink;
	}

	public void setWinBookNodeLink(boolean winBookNodeLink) {
		this.winBookNodeLink = winBookNodeLink;
	}

	public boolean isLooseBookNodeLink() {
		return looseBookNodeLink;
	}

	public void setLooseBookNodeLink(boolean looseBookNodeLink) {
		this.looseBookNodeLink = looseBookNodeLink;
	}

	public NodeFx getEvasionBookNodeLink() {
		return evasionBookNodeLink;
	}

	public void setEvasionBookNodeLink(NodeFx evasionBookNodeLink) {
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
