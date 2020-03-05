package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.NodeLinkFx;

public interface NodeLinkFxObserver {

	public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event);
	
}
