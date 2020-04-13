package magic_book.window.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.game.BookSkill;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemMoney;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;
import magic_book.window.UiConsts;

public class RequirementComponent extends VBox {

	private Button addItem;
	private Button addSkill;

	private Book book;
	
	private int compt;
	
	private GridPane itemPane;
	private GridPane addItemPane;
	private GridPane skillPane;
	private GridPane addSkillPane;
	private TextField moneyTextField;
	
	private HashMap<Integer,ComboBox<String>> itemComboBox = new HashMap();
	private List<ComboBox<String>> skillComboBox = new ArrayList<>();
	private HashMap<Integer,TextField> moneyList = new HashMap();
	

	public RequirementComponent(Book book) {
		System.out.println("REQUIREMENT COMPONENT");
		this.book = book;
		this.compt = 0;
		this.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		
		
		
		this.getChildren().addAll(createItemPane(), createSkillPane());
	}


	public GridPane createItemPane() {
		itemPane = new GridPane();
		itemPane.setVgap(UiConsts.DEFAULT_MARGIN);
		itemPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		addItemPane = new GridPane();
		addItemPane.setVgap(UiConsts.DEFAULT_MARGIN);
		addItemPane.setHgap(UiConsts.DEFAULT_MARGIN);

		addItem = new Button("Ajouter un item");
		addItem.setOnAction((ActionEvent e) -> {
			addItemComboBox();
		});
		
		
		
		itemPane.add(new Label("Item :"), 0, 0);
		itemPane.add(addItem, 1, 1);
		itemPane.add(addItemPane, 2, 1);
		
		return itemPane;
	}
	
	public GridPane createSkillPane() {
		skillPane = new GridPane();
		skillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		skillPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		addSkillPane = new GridPane();
		addSkillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		addSkillPane.setHgap(UiConsts.DEFAULT_MARGIN);

		addSkill = new Button("Ajouter un skill");
		addSkill.setOnAction((ActionEvent e) -> {
			addSkillComboBox();
		});
		
		
		
		skillPane.add(new Label("Compétence :"), 0, 0);
		skillPane.add(addSkill, 1, 1);
		skillPane.add(addSkillPane, 2, 1);
		
		return skillPane;
	}
	
	private ComboBox<String> addItemComboBox(){
		ComboBox<String> itemBox = new ComboBox<>();
		
		itemBox.getItems().add(" ");
		for(Map.Entry<String, BookItem> listItem : book.getItems().entrySet()){
			itemBox.getItems().add(listItem.getValue().getId());
		}
		
		itemBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if(book.getItems().get(itemBox.getValue()) instanceof BookItemMoney) {
					for(Map.Entry<Integer,ComboBox<String>> itemCombo : itemComboBox.entrySet()){
						if(itemCombo.getValue() == itemBox)
							showItemMoney(itemCombo.getKey());
					}
				}
			}
		});

		addItemPane.add(itemBox, (itemComboBox.size() / UiConsts.DEFAULT_MARGIN)*2, itemComboBox.size() % UiConsts.DEFAULT_MARGIN);
		
		itemComboBox.put(compt,itemBox);
		compt+=1;
		return itemBox;
	}
	
	private void showItemMoney(int key){
		moneyTextField = new TextField("0");
		moneyList.put(key, moneyTextField);
		addItemPane.add(moneyTextField, ((key / UiConsts.DEFAULT_MARGIN)*2)+1, key % UiConsts.DEFAULT_MARGIN);
	}
	
	private ComboBox<String> addSkillComboBox(){
		ComboBox<String> skillBox = new ComboBox<>();
		
		skillBox.getItems().add(" ");
		for(Map.Entry<String, BookSkill> listSkill : book.getSkills().entrySet()){
			skillBox.getItems().add(listSkill.getValue().getId());
		}
		
		addSkillPane.add(skillBox, skillComboBox.size() % UiConsts.DEFAULT_MARGIN, skillComboBox.size() / UiConsts.DEFAULT_MARGIN);
		
		skillComboBox.add(skillBox);
		
		return skillBox;
	}
	
	public List<AbstractRequirement> getRequirementItem() {
		List<AbstractRequirement> listItem = new ArrayList();
		
		for(Map.Entry<Integer,ComboBox<String>> itemBox : itemComboBox.entrySet()){
			if(itemBox.getValue().getValue() != null && !itemBox.getValue().getValue().equals(" ") && !(book.getItems().get(itemBox.getValue().getValue()) instanceof BookItemMoney)){
				RequirementItem item = new RequirementItem(itemBox.getValue().getValue());
				listItem.add(item);
			}
		}
		return listItem;
	}

	public List<AbstractRequirement> getRequirementMoney() {
		List<AbstractRequirement> listMoney= new ArrayList();
		
		for(Map.Entry<Integer,ComboBox<String>> moneyBox : itemComboBox.entrySet()){
			if(book.getItems().get(moneyBox.getValue().getValue()) instanceof BookItemMoney){
				int moneyAmount = 0;
				try{
					System.out.println(moneyBox.getKey());
					System.out.println(moneyList);
					moneyAmount = Integer.parseInt(moneyList.get(moneyBox.getKey()).getText());
				} catch (NumberFormatException ex){
					moneyAmount = 0;
				}
				RequirementMoney money = new RequirementMoney(moneyBox.getValue().getValue(), moneyAmount);
				listMoney.add(money);
			}
		}
		return listMoney;
	}
	
	public List<AbstractRequirement> getRequirementSkill() {
		List<AbstractRequirement> listSkill = new ArrayList();
		
		for(ComboBox<String> skillBox : skillComboBox){
			if(skillBox.getValue() != null && !skillBox.getValue().equals(" ")){
				RequirementSkill skill = new RequirementSkill(skillBox.getValue());
				listSkill.add(skill);
			}
		}
		return listSkill;
	}
	
	public void setRequirement(List<List<AbstractRequirement>> listAbstractRequirement){
		for(List<AbstractRequirement> listAbstract : listAbstractRequirement){
			for(AbstractRequirement listRequirement : listAbstract){
				if(listRequirement instanceof RequirementItem){
					RequirementItem item = (RequirementItem) listRequirement;
					ComboBox itemBox = addItemComboBox();
					itemBox.setValue(item.getItemId());
				}
				else if(listRequirement instanceof RequirementMoney){
					RequirementMoney money = (RequirementMoney) listRequirement;
					ComboBox itemBox = addItemComboBox();
					itemBox.setValue(money.getMoneyId());
					showItemMoney(compt-1);
					moneyTextField.setText(String.valueOf(money.getAmount()));
				}
				else if(listRequirement instanceof RequirementSkill){
					RequirementSkill skill = (RequirementSkill) listRequirement;
					ComboBox skillBox = addSkillComboBox();
					skillBox.setValue(skill.getSkillId());
				}
			}
		}
		
	}
	
}
