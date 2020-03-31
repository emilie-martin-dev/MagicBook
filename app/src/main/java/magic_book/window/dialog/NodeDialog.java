package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.window.UiConsts;
import magic_book.window.component.ItemListComponent;

public class NodeDialog extends AbstractDialog {

	private static final String BASIC = "Basic";
	private static final String RANDOM = "Aléatoire";
	private static final String COMBAT = "Combat";
	private static final String FAILURE = "Défaite";
	private static final String VICTORY = "Victoire";
	 
	private TextArea texte;
	private TextField texteEvasion;
	private TextField hpTextField;
	private TextField nbrItemTextField;
	private Label combatLabel;
	private Label evasionLabel;
	private Label hpLabel;
	private Label nbrItemLabel;
	private AbstractBookNode node = null;
	private ChoiceBox<String> nodeType;
	private Button bouton;
	private GridPane root;
	private GridPane rootCharacter;
	private List<Integer> dernier = new ArrayList();
	private HashMap<List<Integer>,ComboBox> listCombo = new LinkedHashMap();
	private List<String> listEnnemiBox;
	private Book book;
	private ItemListComponent itemLinksList;
	
	public NodeDialog(Book book) {
		super("Creation d'une page");
		
		this.book = book;
		itemLinksList = new ItemListComponent(book);
		root.add(itemLinksList, 0, 5);
		this.listEnnemiBox = addEnnemieBox();
		this.showAndWait();
	}

	public NodeDialog(Book book, AbstractBookNode node) {
		super("Edition de la page");
		
		this.book = book;
		texte.setText(node.getText());
		this.listEnnemiBox = addEnnemieBox();
		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
			root.getChildren().remove(hpLabel);
			root.getChildren().remove(hpTextField);
		} else if (node instanceof BookNodeCombat){
			BookNodeCombat bookNode = (BookNodeCombat) node;
			hpTextField.setText(String.valueOf(bookNode.getHp()));
			nodeType.setValue(COMBAT);
			if(bookNode.getEnnemiesId().size() > 0){
				for(int i = 0 ; i< bookNode.getEnnemiesId().size() ; i++){
					addComboBox();
				}
				
				int i =0;
				for(Map.Entry<List<Integer>,ComboBox> list : listCombo.entrySet()){
					list.getValue().setValue(bookNode.getEnnemiesId().get(i));
					i+=1;
				}
				addShowComboBoxCombat();
			}
			texteEvasion.setText(String.valueOf(bookNode.getEvasionRound()));
		} else if (node instanceof BookNodeWithRandomChoices){
			BookNodeWithRandomChoices bookNode = (BookNodeWithRandomChoices) node;
			hpTextField.setText(String.valueOf(bookNode.getHp()));
			nodeType.setValue(RANDOM);
		} else {
			BookNodeWithChoices bookNode = (BookNodeWithChoices) node;
			hpTextField.setText(String.valueOf(bookNode.getHp()));
			nodeType.setValue(BASIC);
		}
		if (node instanceof AbstractBookNodeWithChoices){
			AbstractBookNodeWithChoices bookNode = (AbstractBookNodeWithChoices) node;
			itemLinksList = new ItemListComponent(book);
			itemLinksList.setBookItemLinks(bookNode.getItemLinks());
			nbrItemTextField.setText(String.valueOf(bookNode.getNbItemsAPrendre()));
			root.add(itemLinksList, 0,5);
		}
		this.showAndWait();
	}
	
	@Override
	protected Node getMainUI() {
		root = new GridPane();
		rootCharacter = new GridPane();

		root.setHgap(UiConsts.DEFAULT_MARGIN);
		root.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		texte.setWrapText(true);
		Label labelChoix = new Label("Choix du type du noeud :");
		
		nodeType = new ChoiceBox<>();

		nodeType.getItems().add(BASIC);
		nodeType.getItems().add(RANDOM);
		nodeType.getItems().add(COMBAT);
		nodeType.getItems().add(VICTORY);
		nodeType.getItems().add(FAILURE);
		nodeType.setValue(BASIC);
		
		hpLabel = new Label("hp (gain ou perte)");
		hpTextField = new TextField("");

		evasionLabel = new Label("Nombre de tour avant evasion: ");
		texteEvasion = new TextField("");
		
		combatLabel = new Label("Ajout d'ennemi(s) :");
		
		nbrItemLabel = new Label("Nombre d'items max:");
		nbrItemTextField = new TextField("");
		
		bouton = new Button("Ajouter un personnage");
		
		bouton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				addComboBox();
				addShowComboBoxCombat();
			}
		});
		
		nodeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				root.getChildren().remove(hpLabel);
				root.getChildren().remove(hpTextField);
				root.getChildren().remove(nbrItemLabel);
				root.getChildren().remove(nbrItemTextField);
				if (nodeType.getValue() == COMBAT){
					addCombatNodeShown();
				} else {
					removeCombatNodeShown();
				}
				if (nodeType.getValue() == COMBAT || nodeType.getValue() == BASIC ||  nodeType.getValue() == RANDOM){
					root.add(nbrItemLabel, 0, 4);
					root.add(nbrItemTextField, 1, 4);
					root.add(hpLabel, 2, 2);
					root.add(hpTextField, 3, 2);
				}
			}
		});
		
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 5, 1);
		root.add(labelChoix, 0, 2);
		root.add(nodeType,1 ,2);
		root.add(hpLabel, 2, 2);
		root.add(hpTextField, 3, 2);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			
			if(nodeType.getValue() == BASIC) {
				if (hpTextField.getText().isEmpty() || nbrItemTextField.getText().isEmpty()){
					return;
				}
				try {
					BookNodeWithChoices bookNode = (BookNodeWithChoices) node;
					int hpInt = Integer.parseInt(hpTextField.getText());
					int itemInt = Integer.parseInt(nbrItemTextField.getText());
					bookNode = new BookNodeWithChoices(texteHistoire);
					bookNode.setHp(hpInt);
					bookNode.setNbItemsAPrendre(itemInt);
					bookNode.setItemLinks(NodeDialog.this.itemLinksList.getBookItemLinks());
					NodeDialog.this.node = bookNode;
					
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
			} else if (nodeType.getValue() == RANDOM){
				if (hpTextField.getText().isEmpty() || nbrItemTextField.getText().isEmpty()){
					return;
				}
				try {
					BookNodeWithRandomChoices bookNode = (BookNodeWithRandomChoices) node;
					int hpInt = Integer.parseInt(hpTextField.getText());
					int itemInt = Integer.parseInt(nbrItemTextField.getText());
					bookNode = new BookNodeWithRandomChoices(texteHistoire);
					bookNode.setHp(hpInt);
					bookNode.setNbItemsAPrendre(itemInt);
					bookNode.setItemLinks(NodeDialog.this.itemLinksList.getBookItemLinks());
					NodeDialog.this.node = bookNode;
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
				
			} else if (nodeType.getValue() == COMBAT){
				if (texteEvasion.getText().isEmpty() || nbrItemTextField.getText().isEmpty()){
					return;
				}
				try {
					BookNodeCombat bookNode = (BookNodeCombat) node;
					int hpInt = Integer.parseInt(hpTextField.getText());
					int tourEvasion = Integer.parseInt(texteEvasion.getText());
					int itemInt = Integer.parseInt(nbrItemTextField.getText());
					bookNode = new BookNodeCombat(texteHistoire, null, null, null, tourEvasion, listEnnemis());
					bookNode.setHp(hpInt);
					bookNode.setNbItemsAPrendre(itemInt);
					bookNode.setEvasionRound(tourEvasion);
					bookNode.setItemLinks(NodeDialog.this.itemLinksList.getBookItemLinks());
					NodeDialog.this.node = bookNode;
					
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
				
			}else{
				NodeDialog.this.node = new BookNodeTerminal(texteHistoire, nodeType.getValue() == VICTORY ? BookNodeStatus.VICTORY : BookNodeStatus.FAILURE);
			}
			
			close();
		};
	}
	
	private void addComboBox(){
		ComboBox ennemiBox = new ComboBox<>();
		ennemiBox.getItems().add(" ");
		for(String listEnnemiBo : listEnnemiBox){
			ennemiBox.getItems().add(listEnnemiBo);
		}
		addPositionCombo(ennemiBox);
	}
	
	private void addPositionCombo(ComboBox ennemiBox){
		int coordonnerX;
		int coordonnerY;
		List<Integer> listCoordonne = new ArrayList();
		if(listCombo.isEmpty()){
			coordonnerX = 0;
			coordonnerY = 0;
			listCoordonne.add(coordonnerX);
			listCoordonne.add(coordonnerY);
			listCombo.put(listCoordonne, ennemiBox);
			dernier.add(coordonnerX);
			dernier.add(coordonnerY);
		} else {
			if(dernier.get(0) < 4){
				coordonnerX = dernier.get(0)+1;
				coordonnerY = dernier.get(1);
				listCoordonne.add(coordonnerX);
				listCoordonne.add(coordonnerY);
				dernier.clear();
				dernier.add(coordonnerX);
				dernier.add(coordonnerY);
			} else {
				coordonnerX = dernier.get(0)-4;
				coordonnerY = dernier.get(1)+1;
				listCoordonne.add(coordonnerX);
				listCoordonne.add(coordonnerY);
				dernier.clear();
				dernier.add(coordonnerX);
				dernier.add(coordonnerY);
			}
			listCombo.put(listCoordonne, ennemiBox);
		}
	}
	
	
	
	private void addShowComboBoxCombat(){
		int verif = 0;
		if (!listCombo.isEmpty()){
			for(Map.Entry<List<Integer>,ComboBox> list : listCombo.entrySet()){
				if(verif != listCombo.size()-1 && listCombo.size()!=1){
					rootCharacter.getChildren().remove(list.getValue());
				}
				rootCharacter.add(list.getValue(), list.getKey().get(0), list.getKey().get(1));
				verif+=1;
			}
		}
	}
	
	private void addCombatNodeShown(){
		root.add(evasionLabel, 0, 3);
		root.add(texteEvasion, 1, 3);
		root.add(combatLabel, 2, 3);
		root.add(bouton, 3, 3);
		root.add(rootCharacter, 1, 5);
	}
	
	private void removeCombatNodeShown(){
		root.getChildren().remove(bouton);
		root.getChildren().remove(combatLabel);
		root.getChildren().remove(evasionLabel);
		root.getChildren().remove(texteEvasion);
		if (!listCombo.isEmpty() && !rootCharacter.getChildren().isEmpty()){
			for(Map.Entry<List<Integer>,ComboBox> list : listCombo.entrySet()){
					rootCharacter.getChildren().remove(list.getValue());
			}
		}
		root.getChildren().remove(rootCharacter);
		listCombo.clear();
		dernier.clear();
	}
	
	private List<String> addEnnemieBox(){
		List<String> listEnnemiBox = new ArrayList();
		if(book != null){
			if(!book.getCharacters().isEmpty()){
				for(Map.Entry<String, BookCharacter> mapBookCharacter : book.getCharacters().entrySet()){
					listEnnemiBox.add(mapBookCharacter.getValue().getId());
				}
			}
		}
		return listEnnemiBox;
	}
	
	private List<String> listEnnemis(){
		List<String> listEnnemis = new ArrayList();
		
		for(Map.Entry<List<Integer>,ComboBox> list : listCombo.entrySet()){
			if(list.getValue().getValue() != null && list.getValue().getValue() != " "){
				listEnnemis.add((String) list.getValue().getValue());
			}
		}
		return listEnnemis;
	}
	
	
	public AbstractBookNode getNode() {
		return node;
	}
}
