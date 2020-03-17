package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;

import magic_book.core.node.AbstractBookNode;
import magic_book.core.node.BookNodeStatus;
import magic_book.core.node.BookNodeTerminal;
import magic_book.core.node.BookNodeWithChoices;

 public class NodeDialog extends AbstractDialog {

	private static final String BASIC = "Basic";
	private static final String FAILURE = "Défaite";
	private static final String VICTORY = "Victoire";
	 
	private TextArea texte;
 	private AbstractBookNode node = null;
 	private ChoiceBox<String> nodeType;

 	public NodeDialog() {
 		super("Creation d'une page");

 		this.showAndWait();
 	}

 	public NodeDialog(AbstractBookNode node) {
 		super("Edition de la page");
		
 		texte.setText(node.getText());
		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
		} else {
			nodeType.setValue(BASIC);
		}
			
 		this.showAndWait();
 	}
	
	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		Label labelChoix = new Label("Choix du type du noeud :");
		
		nodeType = new ChoiceBox<>();

 		nodeType.getItems().add(BASIC);
 		nodeType.getItems().add(VICTORY);
 		nodeType.getItems().add(FAILURE);
 		nodeType.setValue(BASIC);

		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		root.add(labelChoix, 0, 2);
		root.add(nodeType,1 ,2 );
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			
			if(nodeType.getValue() == BASIC) {
				NodeDialog.this.node = new BookNodeWithChoices(texteHistoire);
			} else {
				NodeDialog.this.node = new BookNodeTerminal(texteHistoire, nodeType.getValue() == VICTORY ? BookNodeStatus.VICTORY : BookNodeStatus.FAILURE);
			}
			
			close();
		};
	}
	
	public AbstractBookNode getNode() {
		return node;
	}
 }
