package magic_book.window.dialog;

import java.util.ArrayList;
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

/**
 * Boite de dialog pour la création/édition des noeuds
 */
public class NodeDialog extends AbstractDialog {

	/**
	 * Type de noeud basic
	 */
	private static final String BASIC = "Basic";
	/**
	 * Type de noeud aléatoire
	 */
	private static final String RANDOM = "Aléatoire";
	/**
	 * Type de noeud combat
	 */
	private static final String COMBAT = "Combat";
	/**
	 * Type de noeud défaite
	 */
	private static final String FAILURE = "Défaite";
	/**
	 * Type de noeud victoire
	 */
	private static final String VICTORY = "Victoire";
	
	/**
	 * Texte du noeud
	 */
	private TextArea texte;
	/**
	 * Nombre de tour avant l'évasion
	 */
	private TextField texteEvasion;
	/**
	 * Nombre de gain/perte de point de vie
	 */
	private TextField hpTextField;
	/**
	 * Nombre d'item maximum que le player peut prendre sur ce noeud
	 */
	private TextField nbrItemTextField;
	/**
	 * Le noeud créer
	 */
	private AbstractBookNode node = null;
	/**
	 * Choix du type de noeud
	 */
	private ChoiceBox<String> nodeType;
	/**
	 * Bouton d'ajout d'ennemi(s)
	 */
	private Button bouton;
	/**
	 * BorderPane qui contient toutes les informations de la boite de dialog
	 */
	private BorderPane rootBorder;
	/**
	 * Pane contenant les informations communes à toutes les types de noeuds
	 */
	private GridPane root;
	/**
	 * Pane contenant tout les ennemis ajouté
	 */
	private GridPane rootCharacter;
	/**
	 * Liste de comboBox contenant les ennemis ajoutés
	 */
	private List<ComboBox<String>> listCombo = new ArrayList<>();
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * Permet l'ajout d'items disponible sur ce noeud ainsi que le montant des items disponible
	 */
	private ItemListComponent itemLinksList;
	
	/**
	 * Pane contenant les informations si c'est un noeuds basic
	 */
	private GridPane basicPane;
	/**
	 * VBox contenant les informations si c'est un noeuds de combat
	 */
	private VBox combatPane;
	
	/**
	 * Initialisations des valeurs et de l'affichage de départ de la boite de dialog
	 * @param book Livre contenant toutes les informations
	 */
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

	/**
	 * Edition de la boite de dialog ainsi que la mise en place de l'affichage en fonction du type de noeud
	 * @param book Livre contenant toutes les informations
	 * @param node Noeud existant
	 */
	public NodeDialog(Book book, AbstractBookNode node) {
		super("Edition de la page", false, true);
		
		this.book = book;
		
		texte.setText(node.getText());
		
		itemLinksList = new ItemListComponent(book);
		basicPane.add(itemLinksList, 0, 2, 2, 1);
		
		//Si le noeud existant est un noeud à choix (autre qu'un noeud terminal) cela affiche les hp, le nombre d'item, le nom des items à prendre
		if(node instanceof AbstractBookNodeWithChoices){
			AbstractBookNodeWithChoices bookNode = (AbstractBookNodeWithChoices) node;
			
			itemLinksList.setBookItemLinks(bookNode.getItemLinks());
		
			nbrItemTextField.setText(""+bookNode.getNbItemsAPrendre());
			hpTextField.setText(""+bookNode.getHp());	
		}
		
		//Si le noeud existant est un noeud Terminal cela n'affiche que le Texte
		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			
			rootBorder.setBottom(null);
			
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
		} 
		//Si le noeud existant est un noeud de combat, cela affiche le Pane de combat
		else if (node instanceof BookNodeCombat){
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
			
		} 
		//Si le noeud existant est un noeud random
		else if (node instanceof BookNodeWithRandomChoices){
			nodeType.setValue(RANDOM);
			rootBorder.setBottom(basicPane);
		}
		//Si le noeud existant est un noeud basic
		else {
			nodeType.setValue(BASIC);
			rootBorder.setBottom(basicPane);
		}
		
		this.setMaxHeight(400);
		this.setHeight(400d);
		this.showAndWait();
	}
	
	@Override
	protected Node getMainUI() {
		//Génération des Pane
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
		
		//ChoiceBox définissant le type de noeud
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
		
		//Si le bouton d'ajout de personnage est appuyé
		bouton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				//Ajout d'une ComboBox pour l'ajout d'un ennemi
				addComboBox();
			}
		});
		
		nodeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				//Réinitialisation du bas la boite de dialog
				rootBorder.setBottom(null);
				//Si le type de noeud est défini sur Combat
				if (nodeType.getValue() == COMBAT){
					rootBorder.setBottom(combatPane);
				}
				//Si le type de noeud est défini sur Basic ou Random
				else if (nodeType.getValue() == BASIC ||  nodeType.getValue() == RANDOM){
					rootBorder.setBottom(basicPane);
				}
			}
		});
		
		//Ajout des parties commune à toutes les boites de dialog
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		root.add(labelChoix, 0, 2);
		root.add(nodeType, 1, 2);
		
		//Ajout des parties commune pour les noeuds de type basic ou random
		basicPane.add(new Label("hp (gain ou perte) :"), 0, 0);
		basicPane.add(hpTextField, 1, 0);
		basicPane.add(new Label("Nombre d'items max :"), 0, 1);
		basicPane.add(nbrItemTextField, 1, 1);
		
		basicPane.setHgap(UiConsts.DEFAULT_MARGIN);
		basicPane.setVgap(UiConsts.DEFAULT_MARGIN);
		
		//Ajout des parties si le noeud est de type combat
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
		combatPane.getChildren().addAll(combatGridPane);
		
		return rootBorder;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = texte.getText();
			
			//Création d'un noeud basic
			if(nodeType.getValue() == BASIC) {
				NodeDialog.this.node = new BookNodeWithChoices();
			}
			//Création d'un noeud random
			else if (nodeType.getValue() == RANDOM){
				NodeDialog.this.node = new BookNodeWithRandomChoices();
			}
			//Création d'un noeud de combat
			else if (nodeType.getValue() == COMBAT){
				int tourEvasion = 0;
				
				//La valeur saisie doit être un entier
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
			}
			//Création d'un noeud terminal
			else {
				NodeDialog.this.node = new BookNodeTerminal(texteHistoire, nodeType.getValue() == VICTORY ? BookNodeStatus.VICTORY : BookNodeStatus.FAILURE);
			}
			
			//Modification/Création des valeurs saisie
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
	
	/**
	 * Créer et ajoute une ComboBox au Pane rootCharacter
	 * @return ComboxBox avec la liste ennemis
	 */
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
	
	/**
	 * Dresse une liste d'ennemis en regardant les valeurs des ComboBox
	 * @return Liste d'ennemis
	 */
	private List<String> getSelectedEnnemis(){
		List<String> listEnnemis = new ArrayList();
		
		for(ComboBox<String> comboBox : listCombo){
			if(comboBox.getValue() != null && !comboBox.getValue().equals(" ")){
				listEnnemis.add(comboBox.getValue());
			}
		}
		
		return listEnnemis;
	}
	
	/**
	 * Donne le noeud
	 * @return Le noeud
	 */
	public AbstractBookNode getNode() {
		return node;
	}
}
