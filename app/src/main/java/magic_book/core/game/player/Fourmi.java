package magic_book.core.game.player;

import java.util.Random;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;

public class Fourmi {

	private AbstractBookNode currentNode;
	private int goFourmi = 1000;
	private int victory;
	
	public Fourmi(AbstractBookNode node){
		this.currentNode = node;
	}
	
	public float goFourmi() {
		for(int i = 0 ; i < goFourmi ; i++){
			Player p = new Player(currentNode);
			victory += p.getVictory();
		}
		return ((float)victory / (float)goFourmi) * 100f;
	}

	public AbstractBookNode getCurrentNode() {
		return currentNode;
	}	
}
