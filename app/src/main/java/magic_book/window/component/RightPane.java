/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_book.window.component;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;

/**
 *
 * @author norah
 */
public class RightPane extends Pane {
	
	private GraphPane graphPane;
	
	public RightPane(GraphPane graphPane){
		this.graphPane = graphPane;
		this.getChildren().add(createRightPanel());
	}
	
	private Node createRightPanel() {
		//Label prefixLabelNodeCount = new Label("Total de noeuds : " + this.listeNoeud.size());

		VBox statsLayout = new VBox();
		//statsLayout.getChildren().addAll(labelNodeCount);

		return statsLayout;
	}
}
