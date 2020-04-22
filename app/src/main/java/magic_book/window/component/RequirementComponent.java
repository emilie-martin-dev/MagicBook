package magic_book.window.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.game.BookSkill;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemMoney;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;
import magic_book.window.UiConsts;

public class RequirementComponent extends VBox {

	private Button addSkill;

	private Book book;

	private ItemListComponent itemListComponent;
	private ItemListComponent moneyListComponent;

	private List<ComboBox<String>> skillComboBox = new ArrayList<>();
	
	private List<AbstractRequirement> listRequirement;

	private GridPane addSkillPane;
	

	public RequirementComponent(Book book) {
		this.book = book;
		this.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		this.getChildren().addAll(createItemPane(), createMoneyPane(), createSkillPane());
	}


	public GridPane createMoneyPane() {
		GridPane moneyPane = new GridPane();
		moneyPane.setVgap(UiConsts.DEFAULT_MARGIN);
		moneyPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		moneyListComponent = new ItemListComponent(book);
		ComboBox moneyComboBox = new ComboBox<>();
		for(Map.Entry<String, BookItem> listMoney : book.getItems().entrySet()){
			if(listMoney.getValue() instanceof BookItemMoney)
				moneyComboBox.getItems().add(listMoney.getValue());
		}
		
		moneyPane.add(new Label("Monnaie :"), 0, 0);
		moneyPane.add(moneyListComponent, 2, 1);
		//Puis refaire une list Faire une lite de tout les item de Requirement money et faite un peut pareil que en bas
		return moneyPane;
	}
	
	public GridPane createItemPane(){
		GridPane itemPane = new GridPane();
		itemPane.setVgap(UiConsts.DEFAULT_MARGIN);
		itemPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		itemListComponent = new ItemListComponent(book);
		ComboBox itemComboBox = new ComboBox<>();
		for(Map.Entry<String, BookItem> listItem : book.getItems().entrySet()){
			if(!(listItem.getValue() instanceof BookItemMoney))
				itemComboBox.getItems().add(listItem.getValue());
		}
		
		itemPane.add(new Label("Item :"), 0, 0);
		itemPane.add(itemListComponent, 2, 1);
		
		return itemPane;
	}
	
	public GridPane createSkillPane() {
		GridPane skillPane = new GridPane();
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
	
	
	public List<AbstractRequirement> getRequirement(){
		listRequirement = new ArrayList();
		getRequirementItem();
		getRequirementMoney();
		getRequirementSkill();
		
		return listRequirement;
	}
			
	private void getRequirementItem() {
		List<BookItemLink> itemList = itemListComponent.getBookItemLinks();
		for(BookItemLink requirementItem : itemList){
			listRequirement.add(new RequirementItem(requirementItem.getId()));
		}
	}
	private void getRequirementMoney() {
		List<BookItemLink> moneyList = moneyListComponent.getBookItemLinks();
		for(BookItemLink requirementMoney : moneyList){
			listRequirement.add(new RequirementMoney(requirementMoney.getId(), requirementMoney.getAmount()));
		}
	}
	
	private void getRequirementSkill() {
		for(ComboBox<String> skillBox : skillComboBox){
			if(skillBox.getValue() != null && !skillBox.getValue().equals(" ")){
				RequirementSkill skill = new RequirementSkill(skillBox.getValue());
				listRequirement.add(skill);
			}
		}
	}
	
	public void setRequirement(List<AbstractRequirement> listAbstractRequirement){
		List<BookItemLink> listItem = new ArrayList();
		List<BookItemLink> listMoney = new ArrayList();
		for(AbstractRequirement requirement : listAbstractRequirement){
			if(requirement instanceof RequirementItem){
				BookItemLink itemLink = new BookItemLink();
				itemLink.setId(((RequirementItem) requirement).getItemId());
				listItem.add(itemLink);
			}
			else if(requirement instanceof RequirementMoney){
				BookItemLink itemLink = new BookItemLink();
				itemLink.setId(((RequirementMoney) requirement).getMoneyId());
				itemLink.setAmount(((RequirementMoney) requirement).getAmount());
				listMoney.add(itemLink);
			}
			else if(requirement instanceof RequirementSkill){
				RequirementSkill skill = (RequirementSkill) requirement;
				ComboBox skillBox = addSkillComboBox();
				skillBox.setValue(skill.getSkillId());
			}
		}
		itemListComponent.setBookItemLinks(listItem);
		moneyListComponent.setBookItemLinks(listMoney);
		
	}
	
}