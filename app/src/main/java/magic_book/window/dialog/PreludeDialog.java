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

 public class PreludeDialog extends AbstractDialog {

	private String textePrelude;
	private TextArea texte;

 	public PreludeDialog() {
 		super("Creation du Prelude");

 		this.showAndWait();
 	}

 	public PreludeDialog(BookNode node) {
 		super("Edition du Prelude");
		
		this.node = node;
		texte.setText(node.getText());
		this.showAndWait();
 	}
	
	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		root.setHgap(10);
		root.setVgap(10);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			textePrelude = (String) texte.getText();
			close();
		};
	}
	
	public String getTexteHistoire() {
		return textePrelude;
	}
 }
