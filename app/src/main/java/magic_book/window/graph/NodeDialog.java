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

public class NodeDialog extends Stage {

	private Node node = null;
	private TextArea histoire;
	private ChoiceBox nodeType;


	public NodeDialog() {
		initStageUI("Creation d'une page");
		
		this.showAndWait();
	}
	
	public NodeDialog(Node node) {
		initStageUI("Edition de la page");
		histoire.setText(node.getText());
		nodeType.setValue(node.getNodeType());
		
		this.showAndWait();
	}
	
	public void initStageUI(String title){
		GridPane root = new GridPane();
		root.setPadding(new Insets(25));

		root.setHgap(10);
		root.setVgap(10);

		Label labelTexte = new Label("Texte :");
		
		histoire = new TextArea();
		
		Label labelChoix = new Label("Choix du type du noeud :");

		nodeType = new ChoiceBox();

		nodeType.getItems().add(NodeType.BASIC);
		nodeType.getItems().add(NodeType.VICTORY);
		nodeType.getItems().add(NodeType.FAILURE);
		nodeType.setValue(NodeType.BASIC);
		
		HBox choix = new HBox(nodeType);

		Button boutonAnnuler = new Button("Annuler");

		boutonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				close();
			}
		});

		Button boutonValider = new Button("Valider");

		boutonValider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String texteHistoire = (String) histoire.getText();
				NodeType choixBox = (NodeType) nodeType.getValue();
				NodeDialog.this.node = new Node(texteHistoire, choixBox, null);
				close();
			}
		});

		root.add(labelTexte, 0, 0);
		root.add(histoire, 0, 1, 2, 1);
		root.add(labelChoix, 0, 2);
		root.add(choix, 1, 2);
		
		HBox box = new HBox();
		box.setSpacing(10d);
		box.setAlignment(Pos.BASELINE_RIGHT);
		box.getChildren().add(boutonAnnuler);
		box.getChildren().add(boutonValider);
		root.add(box, 0, 3, 2, 1);
		
		Scene scene = new Scene(root);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		this.setTitle(title);
		this.setScene(scene);
	}

	public Node getNode() {
		return this.node;
	}

}
