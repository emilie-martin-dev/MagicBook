package magic_book.observer;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.NodeFx;

public interface NodeFxObserver {

	public void onNodeFXClicked(NodeFx nodeFx, MouseEvent event);
	
}
