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

	public GridPane createItemPane(){
		GridPane itemPane = new GridPane();
		itemPane.setVgap(UiConsts.DEFAULT_MARGIN);
		itemPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		itemListComponent = new ItemListComponent(book, false);

		itemPane.add(new Label("Item :"), 0, 0);
		itemPane.add(itemListComponent, 0, 1);
		
		return itemPane;
	}

	public GridPane createMoneyPane() {
		GridPane moneyPane = new GridPane();
		moneyPane.setVgap(UiConsts.DEFAULT_MARGIN);
		moneyPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		// On récupère la liste de tous les items de type MONEY
		List<BookItem> moneysList = new ArrayList<>();
		for(Map.Entry<String, BookItem> entry : book.getItems().entrySet()){
			if(entry.getValue() instanceof BookItemMoney)
				moneysList.add(entry.getValue());
		}
		
		moneyListComponent = new ItemListComponent(book, false);
		moneyListComponent.setAvailableItem(moneysList);
		
		moneyPane.add(new Label("Monnaie :"), 0, 0);
		moneyPane.add(moneyListComponent, 0, 1);
		
		return moneyPane;
	}
	
	public GridPane createSkillPane() {
		GridPane skillPane = new GridPane();
		skillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		skillPane.setHgap(UiConsts.DEFAULT_MARGIN);
		
		addSkillPane = new GridPane();
		addSkillPane.setVgap(UiConsts.DEFAULT_MARGIN);
		addSkillPane.setHgap(UiConsts.DEFAULT_MARGIN);

		addSkill = new Button("Ajouter une compétences");
		addSkill.setOnAction((ActionEvent e) -> {
			addSkillComboBox();
		});
		
		skillPane.add(new Label("Compétence :"), 0, 0);
		skillPane.add(addSkill, 0, 1);
		skillPane.add(addSkillPane, 0, 2);
		
		return skillPane;
	}
	
	private ComboBox<String> addSkillComboBox(){
		ComboBox<String> listSkills = new ComboBox<>();
		
		listSkills.getItems().add(" ");
		for(Map.Entry<String, BookSkill> listSkill : book.getSkills().entrySet()){
			listSkills.getItems().add(listSkill.getValue().getId());
		}
		
		addSkillPane.add(listSkills, skillComboBox.size() % 4, skillComboBox.size() / 4);
		
		skillComboBox.add(listSkills);
		
		return listSkills;
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
			if(!skillBox.getValue().trim().isEmpty()){
				listRequirement.add(new RequirementSkill(skillBox.getValue()));
			}
		}
	}
	
	public void setRequirement(List<AbstractRequirement> listAbstractRequirement){
		List<BookItemLink> listSelectedItem = new ArrayList();
		List<BookItemLink> listSelectedMoney = new ArrayList();
		
		for(AbstractRequirement requirement : listAbstractRequirement){
			if(requirement instanceof RequirementItem){
				RequirementItem requirementItem = (RequirementItem) requirement;
				
				BookItemLink itemLink = new BookItemLink();
				itemLink.setId(requirementItem.getItemId());
				listSelectedItem.add(itemLink);
			}
			else if(requirement instanceof RequirementMoney){
				RequirementMoney requirementMoney = (RequirementMoney) requirement;
				
				BookItemLink itemLink = new BookItemLink();
				itemLink.setId(requirementMoney.getMoneyId());
				itemLink.setAmount(requirementMoney.getAmount());
				listSelectedMoney.add(itemLink);
			}
			else if(requirement instanceof RequirementSkill){
				RequirementSkill requirementSkill = (RequirementSkill) requirement;
				
				ComboBox skillBox = addSkillComboBox();
				skillBox.setValue(requirementSkill.getSkillId());
			}
		}
		
		itemListComponent.setBookItemLinks(listSelectedItem);
		moneyListComponent.setBookItemLinks(listSelectedMoney);
		
	}
	
}