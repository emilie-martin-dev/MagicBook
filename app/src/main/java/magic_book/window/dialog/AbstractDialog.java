package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class AbstractDialog extends Stage {
	
	public AbstractDialog(String title) {
		initDialogUI(title);
	}
	
	private void initDialogUI(String title) {
		BorderPane root = new BorderPane();
 		root.setPadding(new Insets(25));
		
		root.setCenter(getMainUI());
		root.setBottom(getControlButtons());
				
		
		Scene scene = new Scene(root);
 		this.initModality(Modality.APPLICATION_MODAL);
 		this.setResizable(false);
 		this.setTitle(title);
 		this.setScene(scene);
	}
	
	protected abstract Node getMainUI();
	
	protected abstract EventHandler<ActionEvent> getValidButtonEventHandler();
	
	private HBox getControlButtons() {
		Button boutonAnnuler = new Button("Annuler");
 		boutonAnnuler.setOnAction(new EventHandler<ActionEvent>() {
 			@Override
 			public void handle(ActionEvent e) {
 				close();
 			}
 		});
		
		Button boutonValider = new Button("Valider");
		boutonValider.setOnAction(getValidButtonEventHandler());
		
		HBox box = new HBox();
 		box.setSpacing(10d);
 		box.setAlignment(Pos.BASELINE_RIGHT);
 		box.getChildren().addAll(boutonAnnuler, boutonValider);
		
		box.setPadding(new Insets(10, 0, 0, 0));
		
		return box;
	}


}
