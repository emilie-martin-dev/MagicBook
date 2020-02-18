 package magic_book.window.dialog;

 import javafx.event.ActionEvent;
 import javafx.event.EventHandler;
 import javafx.scene.Node;
 import javafx.scene.control.Label;
 import javafx.scene.control.TextArea;
 import javafx.scene.layout.GridPane;
 import magic_book.core.node.BookNodeLink;

 public class NodeLinkDialog extends AbstractDialog{
	 
	private TextArea texte;
	
 	public NodeLinkDialog() {
 		super("Création du texte de choix");
 		this.showAndWait();
 	}

 	public NodeLinkDialog(BookNodeLink nodeLink) {
 		super("Modification du texte de choix");
		texte.setText(nodeLink.getText());	
		
 		this.showAndWait();
 	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		root.setHgap(10);
		root.setVgap(10);
		
		Label textLabel = new Label("Texte choix :");
		texte = new TextArea();
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return new EventHandler<ActionEvent>() {
 			@Override
 			public void handle(ActionEvent e) {
 				String texteHistoire = (String) texte.getText();
 				//NodeType choixBox = (NodeType) choix.getValue();
 				//NodeDialog.this.node = new Node(texteHistoire, choixBox, null);
 				close();
 			}
 		};
	}

 }
