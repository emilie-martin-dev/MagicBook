package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;

import javafx.stage.Stage;
import magic_book.core.item.BookItem;

public class ItemDialog extends Stage {

	private BookItem item;

	private TextField idTextField;
	private TextField nameTextField;

	public ItemDialog() {
		initStageUI("Ajout d'un item");

		this.showAndWait();
	}

	public ItemDialog(BookItem item) {
		initStageUI("Edition de " + item.getNom());

		idTextField.setText(item.getId());
		nameTextField.setText(item.getNom());

		this.showAndWait();
	}

	private void initStageUI(String title) {
		GridPane root = new GridPane();

		root.setPadding(new Insets(25));
		root.setHgap(10);
		root.setVgap(10);

		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");

		idTextField = new TextField();
		nameTextField = new TextField();

		Button boutonAnnuler = new Button("Annuler");
		boutonAnnuler.setOnAction((ActionEvent e) -> {
			close();
		});

		Button boutonValider = new Button("Valider");
		boutonValider.setOnAction((ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()) {
				return;
			}

			ItemDialog.this.item = new BookItem(idTextField.getText().trim(), nameTextField.getText().trim());

			close();
		});

		HBox buttonsBox = new HBox();
		buttonsBox.setSpacing(10d);
		buttonsBox.setAlignment(Pos.BASELINE_RIGHT);
		buttonsBox.getChildren().add(boutonAnnuler);
		buttonsBox.getChildren().add(boutonValider);

		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		root.add(buttonsBox, 0, 3, 2, 1);

		Scene scene = new Scene(root);

		this.setResizable(false);
		this.initModality(Modality.APPLICATION_MODAL);
		this.setScene(scene);
		this.setTitle(title);
	}

	public BookItem getItem() {
		return item;
	}

}
