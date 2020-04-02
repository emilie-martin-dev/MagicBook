package magic_book.window.dialog;

import java.util.Map.Entry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.Book;

import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.item.BookItemWithDurability;
import magic_book.window.UiConsts;

/**
 * Boite de dialog pour l'ajout des items
 */
public class ItemDialog extends AbstractDialog {
	
	/**
	 * Item de type Healing
	 */
	private static final String HEALING = "Healing";
	/**
	 * Item de type key
	 */
	private static final String KEY_ITEM = "Key";
	/**
	 * Item de type money
	 */
	private static final String MONEY = "Money";
	/**
	 * Item de type weapon
	 */
	private static final String WEAPON = "Weapon";
	/**
	 * Item de type defense
	 */
	private static final String DEFENSE = "Défense";

	/**
	 * Item créer
	 */
	private BookItem item;
	
	/**
	 * Id de l'item
	 */
	private TextField idTextField;
	/**
	 * Nom de l'item
	 */
	private TextField nameTextField;
	/**
	 * Point de vie donné
	 */
	private TextField vieTextField;
	/**
	 * Point de degat
	 */
	private TextField degatTextField;
	/**
	 * Point de defense
	 */
	private TextField defenseTextField;
	/**
	 * Point d'usure
	 */
	private TextField usureTextField;
	
	/**
	 * Type de l'item
	 */
	private ChoiceBox<String> itemType;
	
	private Label vieLabel;
	private Label degatLabel;
	private Label defenseLabel;
	private Label usureLabel;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	private String baseId = "";
	
	/**
	 * Initialisation des valeurs et de la fenêtre de dialog
	 * @param book Le livre contenant toutes les informations
	 */
	public ItemDialog(Book book) {
		super("Ajout d'un item");

		this.book = book;
		this.showAndWait();
	}

	/**
	 * Edition de la boite de dialog ainsi que la mise en place de l'affichage en fonction du type d'item
	 * @param item Item existant
	 * @param book Le livre contenant toutes les informations
	 */
	public ItemDialog(BookItem item, Book book) {
		super("Edition de " + item.getName());
		
		baseId = item.getId();
		
		idTextField.setText(item.getId());
		nameTextField.setText(item.getName());
		
		if(item instanceof BookItemWeapon) {
			itemType.setValue(WEAPON);
			BookItemWeapon itemWeapon = (BookItemWeapon) item;
			
			degatTextField.setText(""+itemWeapon.getDamage());
		} else if (item instanceof BookItemDefense){
			itemType.setValue(DEFENSE);
			BookItemDefense itemDefense = (BookItemDefense) item;
			
			defenseTextField.setText(""+itemDefense.getResistance());
		} else if (item instanceof BookItemHealing){
			itemType.setValue(HEALING);
			BookItemHealing itemHealing = (BookItemHealing) item;
			
			vieTextField.setText(""+itemHealing.getHp());
		} else if (item instanceof BookItemMoney){			
			itemType.setValue(MONEY);
		} else {
			itemType.setValue(KEY_ITEM);
		}
		
		if(item instanceof BookItemWithDurability) {
			BookItemWithDurability itemWithDurability = (BookItemWithDurability) item;
			usureTextField.setText(""+itemWithDurability.getDurability());
		}
		
		this.book = book;
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		GridPane root = new GridPane();
		
		root.setHgap(UiConsts.DEFAULT_MARGIN);
		root.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label idLabel = new Label("Id : ");
		Label nameLabel = new Label("Name: ");
		Label itemLabel = new Label("Choix du type d'item");

		idTextField = new TextField("");
		nameTextField = new TextField("");
		itemType = new ChoiceBox<>();

 		itemType.getItems().add(KEY_ITEM);
		itemType.getItems().add(HEALING);
 		itemType.getItems().add(MONEY);
		itemType.getItems().add(WEAPON);
		itemType.getItems().add(DEFENSE);
		
		itemType.setValue(KEY_ITEM);
		
		degatLabel = new Label("Point d'attaque : ");
		degatTextField = new TextField("");
		
		vieLabel = new Label("Point de vie : ");
		vieTextField = new TextField("");
		
		defenseLabel = new Label("Defense : ");
		defenseTextField = new TextField("");
		
		usureLabel = new Label("Usure du matériel : ");
		usureTextField = new TextField("");
		
		itemType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				setHealingFieldsShown(false);
				setWeaponFieldsShown(false);
				setDefenseFieldsShown(false);
					
				if (itemType.getValue() == WEAPON){
					setWeaponFieldsShown(true);
				} else if (itemType.getValue() == DEFENSE){
					setDefenseFieldsShown(true);
				} else if (itemType.getValue() == HEALING){
					setHealingFieldsShown(true);
				}
				
				if(itemType.getValue() == WEAPON || itemType.getValue() == DEFENSE || itemType.getValue() == HEALING) {
					setDurabilityFieldsShown(true);
				} else {
					setDurabilityFieldsShown(false);
				}
			}
		});
		
		root.add(idLabel, 0, 0);
		root.add(idTextField, 1, 0);
		root.add(nameLabel, 0, 1);
		root.add(nameTextField, 1, 1);
		root.add(itemLabel, 0, 2);
		root.add(itemType, 1, 2);
		root.add(degatLabel, 0, 3);
		root.add(degatTextField, 1, 3);
		root.add(defenseLabel, 0, 3);
		root.add(defenseTextField, 1, 3);
		root.add(vieLabel, 0, 3);
		root.add(vieTextField, 1, 3);
		root.add(usureLabel, 0, 4);
		root.add(usureTextField, 1, 4);
		
		setHealingFieldsShown(false);
		setWeaponFieldsShown(false);
		setDefenseFieldsShown(false);
		setDurabilityFieldsShown(false);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			if (idTextField.getText().trim().isEmpty()
					|| nameTextField.getText().trim().isEmpty()
					|| itemType.getValue() == null){
				return;
			}

			if(book != null){
				if(book.getItems().containsKey(idTextField.getText().trim()) && !baseId.equals(idTextField.getText().trim())){
					showErrorDialog("L'ID n'est pas disponible");
					return;
				}
			}
			if(itemType.getValue() == KEY_ITEM){
				ItemDialog.this.item = new BookItem();
			} else if(itemType.getValue() == MONEY){
				ItemDialog.this.item = new BookItemMoney();
			} else if(itemType.getValue() == WEAPON){
				if (degatTextField.getText().isEmpty() 
					|| usureTextField.getText().isEmpty()) {
					return;
				}
				
				try {
					int damage = Integer.valueOf(degatTextField.getText().trim());
					int usure = Integer.valueOf(usureTextField.getText().trim());
			
					BookItemWeapon bookItemWeapon = new BookItemWeapon();
					bookItemWeapon.setDamage(damage);
					bookItemWeapon.setDurability(usure);
					
					ItemDialog.this.item = bookItemWeapon;
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
			} else if(itemType.getValue() == DEFENSE){
				if (defenseTextField.getText().isEmpty()
					|| usureTextField.getText().isEmpty()){
					return;
				} 
					
				try {
					int defense = Integer.valueOf(defenseTextField.getText().trim());
					int usure = Integer.valueOf(usureTextField.getText().trim());
			
					BookItemDefense bookItemDefense = new BookItemDefense();
					bookItemDefense.setResistance(defense);
					bookItemDefense.setDurability(usure);
					
					ItemDialog.this.item = bookItemDefense;
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
			} else if(itemType.getValue() == HEALING){
				if (vieTextField.getText().isEmpty()
					|| usureTextField.getText().isEmpty()){
					return;
				} 
					
				try {
					int vie = Integer.valueOf(vieTextField.getText().trim());
					int usure = Integer.valueOf(usureTextField.getText().trim());
			
					BookItemHealing bookItemHealing = new BookItemHealing();
					bookItemHealing.setHp(vie);
					bookItemHealing.setDurability(usure);
					
					ItemDialog.this.item = bookItemHealing;
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
			} 
			
			ItemDialog.this.item.setId(idTextField.getText().trim());
			ItemDialog.this.item.setName(nameTextField.getText().trim());
			
			close();
		};
	}
	
	/**
	 * Afficher une boite d'alerte si l'ID est déjà utilisé
	 * @param message Message à afficher
	 */
	private void showErrorDialog(String message){
		Alert alertDialog = new Alert(Alert.AlertType.ERROR);
		
		alertDialog.setTitle("Erreur");
		alertDialog.setHeaderText(message);
		alertDialog.show();
	}
	
	/**
	 * Rendre visible ou non les variables de type healing
	 * @param shown Va permettre d'afficher ou de ne pas afficher 
	 */
	private void setHealingFieldsShown(boolean shown){
		vieLabel.setVisible(shown);
		vieTextField.setVisible(shown);
		
		if(!shown)
			vieTextField.setText("");
	}
	
	/**
	 * Rendre visible ou non les variables de type Weapon
	 * @param shown Va permettre d'afficher ou de ne pas afficher 
	 */
	private void setWeaponFieldsShown(boolean shown){
		degatLabel.setVisible(shown);
		degatTextField.setVisible(shown);
		
		if(!shown)
			degatTextField.setText("");
	}
	
	/**
	 * Rendre visible ou non les variables de type Defense
	 * @param shown Va permettre d'afficher ou de ne pas afficher 
	 */
	private void setDefenseFieldsShown(boolean shown){
		defenseLabel.setVisible(shown);
		defenseTextField.setVisible(shown);
		
		if(!shown)
			defenseTextField.setText("");
	}
	
	/**
	 * Rendre visible ou non la durabilité
	 * @param shown Va permettre d'afficher ou de ne pas afficher 
	 */
	private void setDurabilityFieldsShown(boolean shown){
		usureLabel.setVisible(shown);
		usureTextField.setVisible(shown);
		
		if(!shown)
			usureTextField.setText("");
	}

	/**
	 * Donne l'item
	 * @return L'item
	 */
	public BookItem getItem() {
		return item;
	}

}
