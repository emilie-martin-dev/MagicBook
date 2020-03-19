package magic_book.window.dialog;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;

public class ItemDialog extends AbstractDialog {
	
	private static final String HEALING = "Healing";
	private static final String KEY_ITEM = "Key";
	private static final String MONEY = "Money";
	private static final String WEAPON = "Weapon";
	private static final String DEFENSE = "Défense";

	private BookItem item;
	
	private TextField idTextField;
	private TextField nameTextField;

	private TextField vieTextField;
	private TextField moneyTextField;
	private TextField degatTextField;
	private TextField defenseTextField;
	private TextField usureTextField;
	
	private ChoiceBox<String> itemType;
	
	private Label vieLabel;
	private Label moneyLabel;
	private Label degatLabel;
	private Label defenseLabel;
	private Label usureLabel;
	
	public ItemDialog() {
		super("Ajout d'un item");

		this.showAndWait();
	}

	public ItemDialog(BookItem item) {
		super("Edition de " + item.getName());

		idTextField.setText(item.getId());
		nameTextField.setText(item.getName());
		itemType.setValue(item.getItemType());
		
		if(item instanceof BookItemWeapon) {
			BookItemWeapon itemWeapon = (BookItemWeapon) item;
			degatTextField.setText(""+itemWeapon.getDamage());
			usureTextField.setText(""+itemWeapon.getDurability());
		} else if (item instanceof BookItemDefense){
			BookItemDefense itemDefense = (BookItemDefense) item;
			defenseTextField.setText(""+itemDefense.getResistance());
			usureTextField.setText(""+itemDefense.getDurability());
		} else if (item instanceof BookItemHealing){
			BookItemHealing itemHealing = (BookItemHealing) item;
			vieTextField.setText(""+itemHealing.getHp());
		} else if (item instanceof BookItemMoney){
			BookItemMoney itemMoney = (BookItemMoney) item;
			moneyTextField.setText(""+itemMoney.getAmount());
		}
		
		this.item = item;
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label itemLabel = new Label("Choix du type d'item");

		idTextField = new TextField();
		nameTextField = new TextField();
		itemType = new ChoiceBox<>();

 		itemType.getItems().add(KEY_ITEM);
		itemType.getItems().add(HEALING);
 		itemType.getItems().add(MONEY);
		itemType.getItems().add(WEAPON);
		itemType.getItems().add(DEFENSE);
 		itemType.setValue(KEY_ITEM);
		
		degatLabel = new Label("Point d'attaque : ");
		degatTextField = new TextField();
		moneyLabel = new Label("Monaie : ");
		moneyTextField = new TextField();
		vieLabel = new Label("Point de vie : ");
		vieTextField = new TextField();
		defenseLabel = new Label("Defense : ");
		defenseTextField = new TextField();
		
		
		usureLabel = new Label("Usure du matériel : ");
		usureTextField = new TextField();
		
		ChangeListener<String> changeListener = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (itemType.getValue() == KEY_ITEM){
					System.out.println("key item");
					healingFalse(false);
					moneyFalse(false);
					weaponFalse(false);
					defenseFalse(false);
				} else if (itemType.getValue() == WEAPON){
					System.out.println("weapon");
					healingFalse(false);
					moneyFalse(false);
					weaponFalse(true);
					defenseFalse(false);
				} else if (itemType.getValue() == DEFENSE){
					System.out.println("defense");
					healingFalse(false);
					moneyFalse(false);
					weaponFalse(false);
					defenseFalse(true);
				} else if (itemType.getValue() == HEALING){
					System.out.println("HEALING");
					healingFalse(true);
					moneyFalse(false);
					weaponFalse(false);
					defenseFalse(false);
				} else if (itemType.getValue() == MONEY){
					System.out.println("MONEY");
					healingFalse(false);
					moneyFalse(true);
					weaponFalse(false);
					defenseFalse(false);
				}
			}
		};
		itemType.getSelectionModel().selectedItemProperty().addListener(changeListener);
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		root.add(itemLabel, 0, 2);
		root.add(itemType, 1, 2);
		
				
		root.add(degatLabel, 0, 3);
		root.add(degatTextField,1,3);
		root.add(defenseLabel, 0, 3);
		root.add(defenseTextField,1,3);
		root.add(vieLabel, 0, 3);
		root.add(vieTextField,1,3);
		root.add(moneyLabel, 0, 3);
		root.add(moneyTextField,1,3);
		root.add(usureLabel, 0, 4);
		root.add(usureTextField,1,4);
		
		healingFalse(false);
		moneyFalse(false);
		weaponFalse(false);
		defenseFalse(false);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()) {
				return;
			}

			if(itemType.getValue() == KEY_ITEM){
				ItemDialog.this.item = new BookItem(idTextField.getText().trim(), nameTextField.getText().trim(), itemType.getValue().trim());
			} else if(itemType.getValue() == WEAPON){
				ItemDialog.this.item = new BookItemWeapon(idTextField.getText().trim(), nameTextField.getText().trim(), itemType.getValue().trim(), Integer.parseInt(degatTextField.getText()) , Integer.parseInt(usureTextField.getText()));
			} else if(itemType.getValue() == DEFENSE){
				ItemDialog.this.item = new BookItemDefense(idTextField.getText().trim(), nameTextField.getText().trim(), itemType.getValue().trim(), Integer.parseInt(defenseTextField.getText()) , Integer.parseInt(usureTextField.getText()));
			} else if(itemType.getValue() == HEALING){
				ItemDialog.this.item = new BookItemHealing(idTextField.getText().trim(), nameTextField.getText().trim(), itemType.getValue().trim(), Integer.parseInt(vieTextField.getText()) , Integer.parseInt(usureTextField.getText()));
			} else if(itemType.getValue() == MONEY){
				ItemDialog.this.item = new BookItemMoney(idTextField.getText().trim(), nameTextField.getText().trim(), itemType.getValue().trim(), Integer.parseInt(moneyTextField.getText()));
			}
			
			close();
		};
	}
	
	
	private void healingFalse(boolean bool){
		vieLabel.setVisible(bool);
		vieTextField.setVisible(bool);
		vieTextField.setText(null);
	}
	
	private void moneyFalse(boolean bool){
		moneyLabel.setVisible(bool);
		moneyTextField.setVisible(bool);
		moneyTextField.setText(null);
	}
		
	private void weaponFalse(boolean bool){
		degatLabel.setVisible(bool);
		degatTextField.setVisible(bool);
		usureLabel.setVisible(bool);
		usureTextField.setVisible(bool);
		degatTextField.setText(null);
		usureTextField.setText(null);
	}
			
	private void defenseFalse(boolean bool){
		defenseLabel.setVisible(bool);
		defenseTextField.setVisible(bool);
		defenseTextField.setText(null);
		usureTextField.setText(null);
	}

	public BookItem getItem() {
		return item;
	}

}
