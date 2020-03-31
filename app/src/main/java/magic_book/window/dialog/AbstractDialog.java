package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import magic_book.window.UiConsts;

public abstract class AbstractDialog extends Stage {
	
	public AbstractDialog(String title) {
		initDialogUI(title, false);
	}
	
	public AbstractDialog(String title, boolean hideMarginTop) {
		initDialogUI(title, hideMarginTop);
	}
	
	private void initDialogUI(String title, boolean hideMargins) {
		BorderPane root = new BorderPane();
		int margin = hideMargins ? 0 : UiConsts.DEFAULT_MARGIN_DIALOG;
 		root.setPadding(new Insets(margin, margin, UiConsts.DEFAULT_MARGIN_DIALOG, margin));
		
		HBox controlButtons = getControlButtons();
		controlButtons.setPadding(new Insets(UiConsts.DEFAULT_MARGIN, UiConsts.DEFAULT_MARGIN_DIALOG, 0, 0));
		
		root.setCenter(getMainUI());
		root.setBottom(controlButtons);
		
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
		
		return box;
	}
	
	public void notANumberAlertDialog(NumberFormatException ex){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setContentText(ex.getMessage().replace("For input string: ", "") + " n'est pas un entier");
		alertDialog.show();
	}
	

}
