package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class NodeLinkDialog extends AbstractDialog{
	
	private TextArea texte;
	
	private BookNodeLink nodeLink;
	
	private TextField chanceTextField;
	private Label chanceLabel;
	
	
	private AbstractBookNode firstNode;
	
	private GridPane root;
	
	public NodeLinkDialog(AbstractBookNode firstNode) {
		super("Création du choix");
		this.firstNode = firstNode;
		if(firstNode instanceof BookNodeWithRandomChoices){
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			this.nodeLink = nodeLinkRandom;
		}
		this.showAndWait();
	}

	public NodeLinkDialog(BookNodeLink nodeLink,AbstractBookNode firstNode) {
		super("Modification du choix");
		/*if(firstNode instanceof BookNodeWithRandomChoices){
			BookNodeWithRandomChoices firstNodeRandom = (BookNodeWithRandomChoices) firstNode;
			chanceTextField.setText(String.valueOf(firstNodeRandom.get.getChance()));
			this.nodeLink = nodeLinkRandom;
		} else {
			this.nodeLink = nodeLink;
		}*/
		texte.setText(nodeLink.getText());	
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		root = new GridPane();
		
		root.setHgap(5);
		root.setVgap(5);
		
		Label textLabel = new Label("Texte choix :");
		texte = new TextArea();
		texte.setWrapText(true);
		

		chanceLabel = new Label("Chance pour aller dans vers ce lien");
		chanceTextField = new TextField();
		
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1);
		root.add(chanceLabel, 1, 0);
		root.add(chanceTextField, 1, 1);
		if(firstNode instanceof BookNodeWithRandomChoices){
			root.add(chanceLabel, 1, 0);
			root.add(chanceTextField, 1, 1);
		} else{
			root.getChildren().remove(chanceLabel);
			root.getChildren().remove(chanceTextField);
		}
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			
			
			if(NodeLinkDialog.this.nodeLink == null) {
				if(NodeLinkDialog.this.nodeLink instanceof BookNodeLinkRandom){
					if (chanceTextField.getText().isEmpty()){
						return;
					}
					try {
						int chanceInt = Integer.parseInt(chanceTextField.getText());
						NodeLinkDialog.this.nodeLink = new BookNodeLinkRandom(texteHistoire, -1, chanceInt);
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
				}
				else{
					NodeLinkDialog.this.nodeLink = new BookNodeLink(texteHistoire, -1);
				}
			} else {
					
				if(NodeLinkDialog.this.nodeLink instanceof BookNodeLinkRandom){
					if (chanceTextField.getText().isEmpty()){
						return;
					} 
					try {
						int chanceInt = Integer.parseInt(chanceTextField.getText());
						BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
						nodeLinkRandom.setChance(chanceInt);
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
				}
					
				NodeLinkDialog.this.nodeLink.setText(texteHistoire);
			}

			close();
		};
	}

	public BookNodeLink getNodeLink() {
		return nodeLink;
	}
	
}
