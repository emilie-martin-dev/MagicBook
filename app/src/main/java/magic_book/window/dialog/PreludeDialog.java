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
import magic_book.window.gui.PreludeFx;

 public class PreludeDialog extends AbstractDialog {

	private String textePrelude;
	private TextArea texte;
	//private PreludeFx node = new PreludeFx(null);
	

 	public PreludeDialog() {
 		super("Creation du Prelude");

 		this.showAndWait();
 	}

 	public PreludeDialog(PreludeFx node) {
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
			String textePrelude = (String) texte.getText();
			
			if (PreludeDialog.this.node == null){
				PreludeDialog.this.node = new PreludeFx(textePrelude);
			} else{
				PreludeDialog.this.node.setText(textePrelude);
			}
			
			close();
		};
	}
	
	public String getTexteHistoire() {
		return textePrelude;
	}
 }
