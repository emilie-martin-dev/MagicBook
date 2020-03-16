package magic_book.window.component;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
