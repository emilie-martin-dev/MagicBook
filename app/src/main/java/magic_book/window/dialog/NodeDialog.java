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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
	private AbstractBookNode node = null;
	private ChoiceBox<String> nodeType;
	private Button bouton;
	private BorderPane rootBorder;
	private GridPane root;
	private GridPane rootCharacter;
	private List<ComboBox<String>> listCombo = new ArrayList<>();
	private Book book;
	
	private ItemListComponent itemLinksList;
	
	private GridPane basicPane;
	private VBox combatPane;
	
	public NodeDialog(Book book) {
		super("Creation d'une page", false, true);
		
		this.book = book;
		
		itemLinksList = new ItemListComponent(book);
		basicPane.add(itemLinksList, 0, 2, 2, 1);
		
		rootBorder.setBottom(basicPane);
		
		this.setMaxHeight(400d);
		this.setHeight(400d);
		this.showAndWait();
	}

	public NodeDialog(Book book, AbstractBookNode node) {
		super("Edition de la page", false, true);
		
		this.book = book;
		
		texte.setText(node.getText());
		
		itemLinksList = new ItemListComponent(book);
		basicPane.add(itemLinksList, 0, 2, 2, 1);
		
		if(node instanceof AbstractBookNodeWithChoices){
			AbstractBookNodeWithChoices bookNode = (AbstractBookNodeWithChoices) node;
			
			itemLinksList.setBookItemLinks(bookNode.getItemLinks());
		
			nbrItemTextField.setText(""+bookNode.getNbItemsAPrendre());
			hpTextField.setText(""+bookNode.getHp());
			
			rootBorder.setBottom(basicPane);		
		}
		
		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			
			rootBorder.setBottom(null);
			
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
		} else if (node instanceof BookNodeCombat){
			BookNodeCombat bookNode = (BookNodeCombat) node;
			
			if(!bookNode.getEnnemiesId().isEmpty()){
				for(String ennemiId : bookNode.getEnnemiesId()){
					ComboBox comboBox = addComboBox();
					comboBox.setValue(ennemiId);
				}
			}
			
			texteEvasion.setText("" + bookNode.getEvasionRound());
		
			rootBorder.setBottom(combatPane);
			
			nodeType.setValue(COMBAT);
		} else if (node instanceof BookNodeWithRandomChoices){
			nodeType.setValue(RANDOM);
			rootBorder.setBottom(basicPane);
		} else {
			nodeType.setValue(BASIC);
			rootBorder.setBottom(basicPane);
		}
		
		this.setMaxHeight(400);
		this.setHeight(400d);
		this.showAndWait();
	}
	
	@Override
	protected Node getMainUI() {
		rootBorder = new BorderPane();
		root = new GridPane();
		rootCharacter = new GridPane();
		basicPane = new GridPane();
		combatPane = new VBox();

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
		
		hpTextField = new TextField("0");
		texteEvasion = new TextField("0");
		nbrItemTextField = new TextField("0");
		
		bouton = new Button("Ajouter un personnage");
		
		bouton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				addComboBox();
			}
		});
		
		nodeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				rootBorder.setBottom(null);

				if (nodeType.getValue() == COMBAT){
					rootBorder.setBottom(combatPane);					
				} else if (nodeType.getValue() == BASIC ||  nodeType.getValue() == RANDOM){
					rootBorder.setBottom(basicPane);
				}
			}
		});
		
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		root.add(labelChoix, 0, 2);
		root.add(nodeType, 1, 2);
		
		basicPane.add(new Label("hp (gain ou perte) :"), 0, 0);
		basicPane.add(hpTextField, 1, 0);
		basicPane.add(new Label("Nombre d'items max :"), 0, 1);
		basicPane.add(nbrItemTextField, 1, 1);
		
		basicPane.setHgap(UiConsts.DEFAULT_MARGIN);
		basicPane.setVgap(UiConsts.DEFAULT_MARGIN);
		
		GridPane combatGridPane = new GridPane();
		combatGridPane.add(new Label("Nombre de tour avant evasion :"), 0, 0);
		combatGridPane.add(texteEvasion, 1, 0);
		combatGridPane.add(bouton, 0, 1);
		combatGridPane.add(rootCharacter, 0, 2, 2, 1);
		
		combatGridPane.setHgap(UiConsts.DEFAULT_MARGIN);
		combatGridPane.setVgap(UiConsts.DEFAULT_MARGIN);
		
		rootBorder.setCenter(root);
		
		rootCharacter.setVgap(UiConsts.DEFAULT_MARGIN);
		rootCharacter.setHgap(UiConsts.DEFAULT_MARGIN);
		
		combatPane.setSpacing(UiConsts.DEFAULT_MARGIN);
		combatPane.getChildren().addAll(basicPane, combatGridPane);
		
		return rootBorder;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = texte.getText();
			
			if(nodeType.getValue() == BASIC) {
				NodeDialog.this.node = new BookNodeWithChoices();
			} else if (nodeType.getValue() == RANDOM){
				NodeDialog.this.node = new BookNodeWithRandomChoices();
			} else if (nodeType.getValue() == COMBAT){
				int tourEvasion = 0;
				
				try {
					tourEvasion = Integer.parseInt(texteEvasion.getText());
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
									
				BookNodeCombat bookNode = new BookNodeCombat();
				bookNode.setEvasionRound(tourEvasion);
				bookNode.setEnnemiesId(getSelectedEnnemis());

				NodeDialog.this.node = bookNode;
			} else {
				NodeDialog.this.node = new BookNodeTerminal(texteHistoire, nodeType.getValue() == VICTORY ? BookNodeStatus.VICTORY : BookNodeStatus.FAILURE);
			}
			
			if(nodeType.getValue() == BASIC || nodeType.getValue() == RANDOM || nodeType.getValue() == COMBAT) {
				if (hpTextField.getText().isEmpty() || nbrItemTextField.getText().isEmpty()){
					return;
				}
				
				int hpInt = 0;
				int itemInt = 0;
				try {
					hpInt = Integer.parseInt(hpTextField.getText());
					itemInt = Integer.parseInt(nbrItemTextField.getText());					
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
				
				AbstractBookNodeWithChoices bookNodeWithChoices = (AbstractBookNodeWithChoices) NodeDialog.this.node;
				
				bookNodeWithChoices.setHp(hpInt);
				bookNodeWithChoices.setNbItemsAPrendre(itemInt);
				bookNodeWithChoices.setItemLinks(NodeDialog.this.itemLinksList.getBookItemLinks());
			}
				
			NodeDialog.this.node.setText(texteHistoire);
			
			close();
		};
	}
	
	private ComboBox<String> addComboBox(){
		ComboBox<String> ennemiBox = new ComboBox<>();
		
		ennemiBox.getItems().add(" ");
		for(Map.Entry<String, BookCharacter> listEnnemiBo : book.getCharacters().entrySet()){
			ennemiBox.getItems().add(listEnnemiBo.getValue().getId());
		}		
		
		rootCharacter.add(ennemiBox, listCombo.size()%4, listCombo.size()/4);
		listCombo.add(ennemiBox);
		
		return ennemiBox;
	}
	
	private List<String> getSelectedEnnemis(){
		List<String> listEnnemis = new ArrayList();
		
		for(ComboBox<String> comboBox : listCombo){
			if(comboBox.getValue() != null && !comboBox.getValue().equals(" ")){
				listEnnemis.add(comboBox.getValue());
			}
		}
		
		return listEnnemis;
	}
	
	public AbstractBookNode getNode() {
		return node;
	}
}
