package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
	private TextField evasionRoundTextField;
	/**
	 * Nombre de gain/perte de point de vie
	 */
	private TextField hpTextField;
	/**
	 * Nombre d'item maximum que le player peut prendre sur ce noeud
	 */
	private TextField nbrItemTextField;
	/**
	 * Le noeud créé
	 */
	private AbstractBookNode node = null;
	/**
	 * Choix du type de noeud
	 */
	private ChoiceBox<String> nodeType;
	/**
	 * Bouton d'ajout d'un ennemi
	 */
	private Button addEnnemiButton;
	/**
	 * Pane contenant les champs communs aux noeuds de type abstractNodeWithChoice
	 */
	private GridPane abstractNodeWithChoicePane;
	/**
	 * GridPane contenant les informations des noeuds de combat
	 */
	private GridPane combatPane;
	/**
	 * Contients tous les champs pour éditer le noeud
	 */
	private VBox nodeFieldsPane;
	/**
	 * Pane contenant les informations communes à toutes les types de noeuds
	 */
	private GridPane nodeTextTypePane;
	/**
	 * Pane contenant tout les ennemis ajoutés
	 */
	private GridPane ennemisPane;
	/**
	 * Liste de comboBox contenant les ennemis ajoutés
	 */
	private List<ComboBox<String>> ennemisComboBox = new ArrayList<>();
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;

	/**
	 * L'onglet qui permet de sélectionner les items qui peuvent être pris
	 */
	private Tab itemsTab;

	/**
	 * L'onglet qui permet de sélectionner les items qui peuvent être acheté
	 */
	private Tab shopTab;

	/**
	 * Le TabPane qui permet de gérer les différents onglets de l boite de dialogue
	 */
	private TabPane tabPane;

	/**
	 * Permet l'ajout d'items disponible sur ce noeud ainsi que le montant des items disponible
	 */
	private ItemListComponent itemLinksList;

	/**
	 * Permet l'ajout d'items à acheter sur ce noeud ainsi que le montant des items à acheter
	 */
	private ItemListComponent shopLinksList;

	/**
	 * Constructeur pour créer un nouveau noeud
	 * @param book Livre contenant toutes les informations
	 */
	public NodeDialog(Book book) {
		super("Creation d'un noeud", true);

		this.book = book;
		createPostBookSetUi();

		showNodeWithChoice();

		this.showAndWait();
	}

	/**
	 * Constructeur pour éditer un noeud
	 * @param book Livre contenant toutes les informations
	 * @param node Le noeud à éditer
	 */
	public NodeDialog(Book book, AbstractBookNode node) {
		super("Edition d'un noeud", true);

		this.book = book;
		createPostBookSetUi();

		texte.setText(node.getText());

		if(node instanceof BookNodeTerminal) {
			showNodeTerminal();

			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
		} else if (node instanceof BookNodeCombat){
			showNodeCombat();

			BookNodeCombat nodeCombat = (BookNodeCombat) node;

			// On ajoute la liste des ennemis
			if(!nodeCombat.getEnnemiesId().isEmpty()){
				for(String ennemiId : nodeCombat.getEnnemiesId()){
					ComboBox comboBox = addEnnemiComboBox();
					comboBox.setValue(ennemiId);
				}
			}

			evasionRoundTextField.setText("" + nodeCombat.getEvasionRound());

			nodeType.setValue(COMBAT);
		} else if (node instanceof BookNodeWithRandomChoices){
			showNodeWithRandomChoice();

			nodeType.setValue(RANDOM);
		} else if (node instanceof BookNodeWithChoices){
			showNodeWithChoice();

			nodeType.setValue(BASIC);
		} else {
			// Si on arrive ici, un type de noeud n'est pas encore pris en compte. On empêche donc l'édition de celui-ci
			Alert a = new Alert(Alert.AlertType.ERROR);
			a.setTitle("Erreur lors de l'edition du noeud");
			a.setHeaderText("Ce type de noeud n'est pas encore disponible à l'édition");
			a.showAndWait();
			close();
		}

		// Change toutes les valeurs communes aux noeuds de type AbstractBookNodeWithChoices
		if(node instanceof AbstractBookNodeWithChoices){
			AbstractBookNodeWithChoices abstractBookNodeWithChoices = (AbstractBookNodeWithChoices) node;

			itemLinksList.setBookItemLinks(abstractBookNodeWithChoices.getItemLinks());
			shopLinksList.setBookItemLinks(abstractBookNodeWithChoices.getShopItemLinks());

			nbrItemTextField.setText(""+abstractBookNodeWithChoices.getNbItemsAPrendre());
			hpTextField.setText(""+abstractBookNodeWithChoices.getHp());
		}

		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		tabPane = new TabPane();

		Tab nodeTab = new Tab("Noeud");

		nodeTab.setClosable(false);
		nodeTab.setContent(getNodeTab());

		tabPane.getTabs().addAll(nodeTab);

		return tabPane;
	}

	private void createPostBookSetUi() {
		itemLinksList = new ItemListComponent(book, false);
		itemLinksList.setPadding(UiConsts.DEFAULT_INSET_DIALOG);

		shopLinksList = new ItemListComponent(book, true);
		shopLinksList.setPadding(UiConsts.DEFAULT_INSET_DIALOG);
	}

	/**
	 * Permet de récupérer le contenu de l'onglet d'édition du noeud
	 * @return Le Node qui contient le contenu de l'onglet
	 */
	private Node getNodeTab() {
		nodeFieldsPane = new VBox();
		nodeFieldsPane.setPadding(UiConsts.DEFAULT_INSET_DIALOG);
		nodeFieldsPane.setSpacing(UiConsts.DEFAULT_MARGIN);

		// Construction des champs en commun à tous les noeuds
		nodeTextTypePane = new GridPane();
		nodeTextTypePane.setHgap(UiConsts.DEFAULT_MARGIN);
		nodeTextTypePane.setVgap(UiConsts.DEFAULT_MARGIN);

		texte = new TextArea();
		texte.setWrapText(true);

		nodeType = new ChoiceBox<>();

		nodeType.getItems().add(BASIC);
		nodeType.getItems().add(RANDOM);
		nodeType.getItems().add(COMBAT);
		nodeType.getItems().add(VICTORY);
		nodeType.getItems().add(FAILURE);
		nodeType.setValue(BASIC);

		// Change les champs affichés si on change le type de noeud
		nodeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (nodeType.getValue() == VICTORY || nodeType.getValue() == FAILURE){
					showNodeTerminal();
				} else if (nodeType.getValue() == BASIC){
					showNodeWithChoice();
				} else if (nodeType.getValue() == RANDOM){
					showNodeWithRandomChoice();
				} if(nodeType.getValue() == COMBAT) {
					showNodeCombat();
				}
			}
		});

		nodeTextTypePane.add(new Label("Texte :"), 0, 0);
		nodeTextTypePane.add(texte, 0, 1, 2, 1);
		nodeTextTypePane.add(new Label("Choix du type du noeud :"), 0, 2);
		nodeTextTypePane.add(nodeType, 1, 2);

		// Construction des champs en commun à tous les noeuds avec des choix
		abstractNodeWithChoicePane = new GridPane();
		abstractNodeWithChoicePane.setHgap(UiConsts.DEFAULT_MARGIN);
		abstractNodeWithChoicePane.setVgap(UiConsts.DEFAULT_MARGIN);

		hpTextField = new TextField("0");
		nbrItemTextField = new TextField("0");

		abstractNodeWithChoicePane.add(new Label("hp (gain ou perte) :"), 0, 0);
		abstractNodeWithChoicePane.add(hpTextField, 1, 0);
		abstractNodeWithChoicePane.add(new Label("Nombre d'items max :"), 0, 1);
		abstractNodeWithChoicePane.add(nbrItemTextField, 1, 1);

		// Construction des champs pour les noeuds de combat
		combatPane = new GridPane();
		combatPane.setHgap(UiConsts.DEFAULT_MARGIN);
		combatPane.setVgap(UiConsts.DEFAULT_MARGIN);

		evasionRoundTextField = new TextField("0");
		addEnnemiButton = new Button("Ajouter un personnage");
		addEnnemiButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				addEnnemiComboBox();
			}
		});

		ennemisPane = new GridPane();
		ennemisPane.setVgap(UiConsts.DEFAULT_MARGIN);
		ennemisPane.setHgap(UiConsts.DEFAULT_MARGIN);

		combatPane.add(new Label("Nombre de tour avant evasion :"), 0, 0);
		combatPane.add(evasionRoundTextField, 1, 0);
		combatPane.add(addEnnemiButton, 0, 1);
		combatPane.add(ennemisPane, 0, 2, 2, 1);

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(nodeFieldsPane);
		scrollPane.setFitToWidth(true);

		return scrollPane;
	}

	/**
	 * Créer l'onglet Items et Shop
	 */
	private void createAbstractNodeWithChoicesTabs() {
		itemsTab = new Tab("Items");
		itemsTab.setClosable(false);
		itemsTab.setContent(getItemShopTabContent(itemLinksList));

		shopTab = new Tab("Shop");
		shopTab.setClosable(false);
		shopTab.setContent(getItemShopTabContent(shopLinksList));

		tabPane.getTabs().addAll(itemsTab, shopTab);
	}

	/**
	 * Permet de récupérer le contenu de l'onglet shop ou item
	 * @param listComponent Le composant de sélection des items
	 * @return Le contenu de l'onglet shop ou item
	 */
	private Node getItemShopTabContent(ItemListComponent listComponent) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(listComponent);
		scrollPane.setFitToWidth(true);

		return scrollPane;
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
					tourEvasion = Integer.parseInt(evasionRoundTextField.getText());
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}

				BookNodeCombat bookNode = new BookNodeCombat();
				bookNode.setEvasionRound(tourEvasion);
				bookNode.setEnnemiesId(getSelectedEnnemis());

				NodeDialog.this.node = bookNode;
			} else if (nodeType.getValue() == FAILURE || nodeType.getValue() == VICTORY){
				NodeDialog.this.node = new BookNodeTerminal(texteHistoire, nodeType.getValue() == VICTORY ? BookNodeStatus.VICTORY : BookNodeStatus.FAILURE);
			} else {
				return;
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
				bookNodeWithChoices.setItemLinks(this.itemLinksList.getBookItemLinks());
				bookNodeWithChoices.setShopItemLinks(this.shopLinksList.getBookItemLinks());
			}

			NodeDialog.this.node.setText(texteHistoire);
			
			validateData();
			close();
		};
	}

	/**
	 * Créé une liste pour sélectionner un item
	 * @return La liste créée
	 */
	private ComboBox<String> addEnnemiComboBox(){
		ComboBox<String> ennemiBox = new ComboBox<>();

		ennemiBox.getItems().add(" ");
		for(Map.Entry<String, BookCharacter> listEnnemiBo : book.getCharacters().entrySet()){
			ennemiBox.getItems().add(listEnnemiBo.getValue().getId());
		}

		ennemisPane.add(ennemiBox, ennemisComboBox.size() % 4, ennemisComboBox.size() / 4);
		ennemisComboBox.add(ennemiBox);

		return ennemiBox;
	}

	/**
	 * Récupère la liste des ennemis sélectionnés
	 * @return La liste des ennemis
	 */
	private List<String> getSelectedEnnemis(){
		List<String> listEnnemis = new ArrayList();

		for(ComboBox<String> comboBox : ennemisComboBox){
			if(comboBox.getValue() != null && !comboBox.getValue().equals(" ")){
				listEnnemis.add(comboBox.getValue());
			}
		}

		return listEnnemis;
	}

	/**
	 * Supprime tout ce qui est affiché et ajoute les éléments communs à tous les noeuds
	 */
	private void clearNode() {
		nodeFieldsPane.getChildren().clear();
		nodeFieldsPane.getChildren().add(nodeTextTypePane);
		tabPane.getTabs().remove(itemsTab);
		tabPane.getTabs().remove(shopTab);
	}

	/**
	 * Affiche les champs pour un noeud terminal
	 */
	private void showNodeTerminal() {
		clearNode();
	}

	/**
	 * Affiche les champs pour un noeud AbstractBookNodeWithChoices
	 */
	private void showAbstractNodeWithChoice() {
		clearNode();

		nodeFieldsPane.getChildren().add(abstractNodeWithChoicePane);

		createAbstractNodeWithChoicesTabs();
	}

	/**
	 * Affiche les champs pour un noeud avec des choix
	 */
	private void showNodeWithChoice() {
		showAbstractNodeWithChoice();
	}

	/**
	 * Affiche les champs pour un noeud avec des choix aléatoire
	 */
	private void showNodeWithRandomChoice() {
		showAbstractNodeWithChoice();
	}

	/**
	 * Affiche les champs pour un noeud combat
	 */
	private void showNodeCombat() {
		showAbstractNodeWithChoice();

		nodeFieldsPane.getChildren().add(combatPane);
		nodeFieldsPane.getChildren().add(ennemisPane);
	}

	/**
	 * Donne le noeud
	 * @return Le noeud
	 */
	public AbstractBookNode getNode() {
		return node;
	}
}
