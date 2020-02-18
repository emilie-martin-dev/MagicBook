/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 package magic_book.window.graph;

 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.application.Application;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.TextArea;
 import javafx.scene.control.Label;
 import javafx.scene.layout.GridPane;
 import javafx.scene.control.ChoiceBox;
 import javafx.scene.layout.HBox;
 import javafx.stage.Modality;
 import javafx.stage.Stage;

 import magic_book.core.node.Node;
 import magic_book.core.node.NodeType;

 public class NodeDialog extends AbstractDialog {

	private TextArea texte;
 	private Node node = null;
 	private ChoiceBox nodeType;


 	public NodeDialog() {
 		super("Creation d'une page");

 		this.showAndWait();
 	}

 	public NodeDialog(Node node) {
 		super("Edition de la page");
 		texte.setText(node.getText());
 		nodeType.setValue(node.getNodeType());

 		this.showAndWait();
 	}
	
	@Override
	protected javafx.scene.Node getMainUI() {
		GridPane root = new GridPane();
		
		root.setHgap(10);
		root.setVgap(10);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		Label labelChoix = new Label("Choix du type du noeud :");
		root.add(labelChoix, 0, 2);
		
		nodeType = new ChoiceBox();

 		nodeType.getItems().add(NodeType.BASIC);
 		nodeType.getItems().add(NodeType.VICTORY);
 		nodeType.getItems().add(NodeType.FAILURE);
 		nodeType.setValue(NodeType.BASIC);

 		HBox choix = new HBox(nodeType);
		root.add(choix,1 ,2 );
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String texteHistoire = (String) texte.getText();
				NodeType choixBox = (NodeType) nodeType.getValue();
				NodeDialog.this.node = new Node(texteHistoire, choixBox, null);
				close();
			}
		};
	}
	
	public Node getNode() {
		return node;
	}
 }
