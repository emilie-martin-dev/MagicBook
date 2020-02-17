/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 package magic_book.window.graph;

 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.control.ChoiceBox;
 import javafx.scene.control.Label;
 import javafx.scene.control.TextArea;
 import javafx.scene.layout.GridPane;
 import javafx.scene.layout.HBox;
 import javafx.stage.Modality;
 import javafx.stage.Stage;

 import magic_book.core.node.Node;
 import magic_book.core.node.NodeLink;
 import magic_book.core.node.NodeType;

 /**
  *
  * @author norah
  */
 public class NodeLinkDialog extends NodeMere{

 	public NodeLinkDialog() {
 		initStageUI("Création du texte de choix");
 		this.showAndWait();
 	}

 	public NodeLinkDialog(NodeLink nodeLink) {
 		initStageUI("Modification du texte de choix");
 		this.showAndWait();
 	}

	@Override
	public void ajout(GridPane root, Button boutonValider){
 		boutonValider.setOnAction(new EventHandler<ActionEvent>() {
 			@Override
 			public void handle(ActionEvent e) {
 				String texteHistoire = (String) texte.getText();
 				//NodeType choixBox = (NodeType) choix.getValue();
 				//NodeDialog.this.node = new Node(texteHistoire, choixBox, null);
 				close();
 			}
 		});
	}

 }
