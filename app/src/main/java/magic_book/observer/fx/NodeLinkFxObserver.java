package magic_book.observer.fx;

import javafx.scene.input.MouseEvent;
import magic_book.window.gui.NodeLinkFx;

/**
 * Observer pour réagir aux clics d'un NodeLinkFx
 */
public interface NodeLinkFxObserver {
	
	/**
	 * Un NodeLinkFx a été cliqué
	 * @param nodeLinkFx Le NodeLinkFx cliqué
	 * @param event L'évènement du clic
	 */
	public void onNodeLinkFXClicked(NodeLinkFx nodeLinkFx, MouseEvent event);
	
}
