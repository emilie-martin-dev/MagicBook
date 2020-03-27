package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

 public class PreludeDialog extends AbstractDialog {

	private String textePrelude;
	private TextArea texte;
	

 	public PreludeDialog() {
 		super("Creation du Prelude");

 		this.showAndWait();
 	}

 	public PreludeDialog(String textePrelude) {
 		super("Edition du Prelude");
	
		texte.setText(textePrelude);
		
		this.showAndWait();
 	}
	
	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		texte.setWrapText(true);
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			this.textePrelude = (String) texte.getText();
			
			close();
		};
	}
	
	public String getTextePrelude() {
		return textePrelude;
	}
 }
