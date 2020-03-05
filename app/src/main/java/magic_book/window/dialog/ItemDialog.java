package magic_book.window.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import magic_book.core.item.BookItem;

public class ItemDialog extends AbstractDialog {

	private BookItem item;

	private TextField idTextField;
	private TextField nameTextField;

	public ItemDialog() {
		super("Ajout d'un item");

		this.showAndWait();
	}

	public ItemDialog(BookItem item) {
		super("Edition de " + item.getNom());

		idTextField.setText(item.getId());
		nameTextField.setText(item.getNom());

		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");

		idTextField = new TextField();
		nameTextField = new TextField();
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()) {
				return;
			}

			ItemDialog.this.item = new BookItem(idTextField.getText().trim(), nameTextField.getText().trim());
			
			close();
		};
	}

	public BookItem getItem() {
		return item;
	}

}
