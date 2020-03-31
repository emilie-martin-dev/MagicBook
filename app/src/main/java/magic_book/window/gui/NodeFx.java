package magic_book.window.gui;

import javafx.beans.property.FloatProperty;
import javafx.scene.paint.Color;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

public class NodeFx extends RectangleFx {
	
	private AbstractBookNode node;
	

	public NodeFx(AbstractBookNode node, FloatProperty zoom) {
		super(Color.GREEN, zoom);
		this.node = node;
		colorNode();
	}

	public AbstractBookNode getNode() {
		return node;
	}

	public void setNode(AbstractBookNode node) {
		this.node = node;
	}
	
	public void colorNode(){
		if(node instanceof BookNodeCombat){
			setColor(Color.GOLD);
		} else if(node instanceof BookNodeTerminal){
			BookNodeTerminal nodeTerminal = (BookNodeTerminal) node;
			if(nodeTerminal.getBookNodeStatus() == BookNodeStatus.FAILURE)
				setColor(Color.DARKRED);
			else
				setColor(Color.DARKGREEN);
		} else if(node instanceof BookNodeWithChoices){
				setColor(Color.CHOCOLATE);
		} else if(node instanceof BookNodeWithRandomChoices){
				setColor(Color.LIGHTSKYBLUE);
		}
	}
	
}
