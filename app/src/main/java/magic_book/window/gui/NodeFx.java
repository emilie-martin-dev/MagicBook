package magic_book.window.gui;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import magic_book.core.node.BookNode;
import magic_book.observer.ModeObserver;
import magic_book.window.dialog.NodeDialog;

public class NodeFx extends Rectangle implements ModeObserver {
	
	private BookNode node;
	
	private EventHandler<MouseEvent> addNodeEvent;
	private EventHandler<MouseEvent> modEventHandler;

	public NodeFx(BookNode node) {
		this.node = node;
		
		addNodeEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				NodeDialog dialog = new NodeDialog(NodeFx.this.node);

				if(dialog.getNode() != null) {
					NodeFx.this.node = dialog.getNode();
				}
				
				event.consume();
			}
		};
		
		modEventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
					if(event.getClickCount() == 2){
						System.out.println("Double clicked");
						NodeDialog dialog = new NodeDialog(NodeFx.this.node);
						if(dialog.getNode() != null) {
							NodeFx.this.node = dialog.getNode();
						}
					}
				event.consume();
			}
		};
				
	}

	public BookNode getNode() {
		return node;
	}

	public void setNode(BookNode node) {
		this.node = node;
	}	

	@Override
	public void modeChanged(Mode mode) {
		if (mode == Mode.ADD_NODE){
			System.out.println("ca passe si le modeest add_node");
			this.addEventHandler(MouseEvent.MOUSE_PRESSED, addNodeEvent);
		} else {
			System.out.println("ca passe si le mode n'est pas add_node");
			this.removeEventHandler(MouseEvent.MOUSE_PRESSED, addNodeEvent);
		}

		if (mode == Mode.SELECT){
			System.out.println("ca passe si le modeest select");
			this.addEventHandler(MouseEvent.MOUSE_PRESSED, modEventHandler);
		} else {
			System.out.println("ca passe si le mode n'est pas select");
			this.removeEventHandler(MouseEvent.MOUSE_PRESSED, modEventHandler);
		}
	}
	
}
