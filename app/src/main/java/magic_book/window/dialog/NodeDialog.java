package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;

import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeType;

 public class NodeDialog extends AbstractDialog {

	private TextArea texte;
 	private BookNode node = null;
 	private ChoiceBox<BookNodeType> nodeType;

 	public NodeDialog() {
 		super("Creation d'une page");

 		this.showAndWait();
 	}

 	public NodeDialog(BookNode node) {
 		super("Edition de la page");
 		texte.setText(node.getText());
 		nodeType.setValue(node.getNodeType());

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

 		nodeType.getItems().add(BookNodeType.BASIC);
 		nodeType.getItems().add(BookNodeType.VICTORY);
 		nodeType.getItems().add(BookNodeType.FAILURE);
 		nodeType.setValue(BookNodeType.BASIC);

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
			BookNodeType choixBox = (BookNodeType) nodeType.getValue();
			
			NodeDialog.this.node = new BookNode(texteHistoire, choixBox, null);
			
			close();
		};
	}
	
	public BookNode getNode() {
		return node;
	}
 }
