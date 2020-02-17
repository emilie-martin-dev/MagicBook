package magic_book.window;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindow extends Stage {
	
	enum Mode {
		SELECT, ADD_NODE, ADD_NODE_LINK;
	}
	
	private Mode mode;
	private ToggleGroup toggleGroup;

	public MainWindow() {
		BorderPane root = new BorderPane();
		
		Pane mainContent = new Pane();
		mainContent.setCursor(Cursor.CLOSED_HAND);
		mainContent.addEventHandler(MouseEvent.MOUSE_PRESSED, (new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (mode == Mode.ADD_NODE) {
					createANode();
				}
			}
		}));
		
		root.setLeft(createLeftPanel());
		root.setCenter(mainContent);
		
		Scene scene = new Scene(root, 1000, 800);

		this.setTitle("Magic Book");
		this.setScene(scene);
		this.show();
	}
	
	private Node createLeftPanel() {
		ToggleButton selectToogle = createToggleButton("Selectionner", Mode.SELECT);
		selectToogle.setSelected(true);
		this.mode = Mode.SELECT;
		ToggleButton addNodeToggle = createToggleButton("Ajouter noeud", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("Ajouter lien", Mode.ADD_NODE_LINK);

		FlowPane flow = new FlowPane();
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle);
		flow.setPadding(new Insets(5, 5, 5, 5));
		
		return flow;
	}
	
	private ToggleButton createToggleButton(String text, Mode mode) {
		ToggleButton toggleButton = new ToggleButton(text);
		
		toggleButton.setOnAction((ActionEvent e) -> {
			MainWindow.this.mode = mode;
		});
		
		toggleButton.setPrefSize(100, 100);
		
		if(this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}
		
		this.toggleGroup.getToggles().add(toggleButton);
		
		return toggleButton;
	}
	
	private void createANode() {
	
	}
}
