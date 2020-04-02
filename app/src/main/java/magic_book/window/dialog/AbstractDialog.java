package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import magic_book.window.UiConsts;

/**
 * Classe mère de toute les boites de dialog
 */
public abstract class AbstractDialog extends Stage {
	
	/**
	 * Titre de la boite de dialog
	 * @param title Le message à afficher
	 */
	public AbstractDialog(String title) {
		this(title, false, false);
	}
	
	/**
	 * Titre et marge de la boite de dialog
	 * @param title Le message à afficher
	 * @param hideMarginTop Marge pour le prélude
	 */
	public AbstractDialog(String title, boolean hideMarginTop) {
		this(title, hideMarginTop, false);
	}
	
	/**
	 * Titre et marge avec un rajout d'un scroll de la boite de dialog
	 * @param title Le message à afficher
	 * @param hideMarginTop Marge pour le prélude
	 * @param useScroll Scroll du Pane
	 */
	public AbstractDialog(String title, boolean hideMarginTop, boolean useScroll) {
		initDialogUI(title, hideMarginTop, useScroll);
	}
	
	/**
	 * Initialisation de la boite de dialog
	 * @param title Le message à afficher
	 * @param hideMarginTop Marge pour le prélude
	 * @param useScroll Scroll du Pane
	 */
	private void initDialogUI(String title, boolean hideMargins, boolean useScroll) {
		BorderPane rootPane = new BorderPane();
		int margin = hideMargins ? 0 : UiConsts.DEFAULT_MARGIN_DIALOG;
 		rootPane.setPadding(new Insets(margin, margin, UiConsts.DEFAULT_MARGIN_DIALOG, margin));
		
		HBox controlButtons = getControlButtons();
		controlButtons.setPadding(new Insets(UiConsts.DEFAULT_MARGIN, UiConsts.DEFAULT_MARGIN_DIALOG, 0, 0));
		
		rootPane.setCenter(getMainUI());
		rootPane.setBottom(controlButtons);
		
		Parent rootNode = rootPane;
		if(useScroll) {
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setFitToWidth(true);
			scrollPane.setContent(rootPane);
			
			rootNode = scrollPane;
		}
		
		Scene scene = new Scene(rootNode);
 		this.initModality(Modality.APPLICATION_MODAL);
 		this.setResizable(false);
 		this.setTitle(title);
 		this.setScene(scene);
	}
	
	/**
	 * Initialisation des valeur et du GraphPane de la boite de dialog
	 * @return le GraphPane initialisé
	 */
	protected abstract Node getMainUI();
	
	/**
	 * Création des Book(node/lien/item/personnage) en fonction des valeurs saisie.
	 * @return La validation de la création du book(node/lien/item/personnage)
	 */
	protected abstract EventHandler<ActionEvent> getValidButtonEventHandler();
	
	/**
	 * Création des boutons valider et annuler
	 * @return Hbox contenant les boutons
	 */
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
	
	/**
	 * Alert qui apparait si la saisie de certain texte n'est pas un nombre
	 * @param ex Exception d'erreur
	 */
	protected void notANumberAlertDialog(NumberFormatException ex){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setContentText(ex.getMessage().replace("For input string: ", "") + " n'est pas un entier");
		alertDialog.show();
	}
	

}
