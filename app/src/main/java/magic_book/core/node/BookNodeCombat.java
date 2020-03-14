package magic_book.core.node;

import java.util.List;
import magic_book.window.gui.NodeFx;


public class BookNodeCombat extends AbstractBookNode{
	private boolean winBookNodeLink;
	private boolean looseBookNodeLink;
	private NodeFx evasionBookNodeLink;
	private int evasionTurn;
	private List<Personnages> ennemies;
	
	public BookNodeCombat(String text, boolean winBookNodeLink, boolean looseBookNodeLink, NodeFx evasionBookNodeLink, int evasionTurn, List<Personnages> ennemies){
		super(text);
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionTurn = evasionTurn;
		this.ennemies = ennemies;
		
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
}
