package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.window.UiConsts;
import magic_book.window.component.RequirementComponent;

/**
 * Boite de dialogue pour la création/édition des liens entre les noeuds
 */
public class NodeLinkDialog extends AbstractDialog{
	
	/**
	 * Lien entre les noeuds
	 */
	private BookNodeLink nodeLink;
	
	/**
	 * Type de lien
	 */
	private String linkType;
	
	/**
	 * Type de lien EVASION
	 */
	public static final String EVASION = "Lien pour l'évasion";
	/**
	 * Type de lien PERDRE
	 */
	public static final String PERDRE = "Lien si le combat est perdu";
	/**
	 * Type de lien GAGNE
	 */
	public static final String GAGNE = "Lien si le combat est gagné";
	
	/**
	 * Texte du lien
	 */
	private TextArea texte;
	/**
	 * Chance si le noeud de départ est un noeud aléatoire
	 */
	private TextField chanceTextField;
	/**
	 * Nombre HP perdu ou gagné en passant par ce lien
	 */
	private TextField hpTextField;
	/**
	 * Nombre d'argent perdu ou gagné en passant par ce lien
	 */
	private TextField goldTextField;
	
	/**
	 * Le TabPane qui permet de gérer les différents onglets de la boite de dialogue
	 */
	private TabPane tabPane;
	
	/**
	 * CheckBox de chemin automatique
	 */
	private CheckBox autoBox;
	/**
	 * Choix du type de lien
	 */
	private ChoiceBox<String> choixLienBox;

	/**
	 * Premier noeud qui est cliqué (commencement du lien)
	 */
	private AbstractBookNode firstNode;
	
	/**
	 * BorderPane contenant tout ce qu'il y a dans la boite de dialog
	 */
	private BorderPane root;
	/**
	 * Pane contenant toutes les informations communes à tout les types de lien
	 */
	private GridPane mainUi;
	/**
	 * Pane contenant toutes les informations si le noeud de départ est un noeud à choix aléatoire
	 */
	private GridPane randomUi;
	/**
	 * Pane contenant toutes les informations si le noeud de départ est un noeud de combat
	 */
	private GridPane combatUi;
	
	/**
	 * Livre contenant toute les informations
	 */
	private Book book;
	
	/**
	 * Accordion des requirements
	 */
	private Accordion accordion;
	
	/**
	 * Liste des RequirementComponent
	 */
	private List<RequirementComponent> requirementComponentList;
	
	/**
	 * Initialisation des valeurs et de la fenêtre de dialog en fonction du fistNode
	 * @param firstNode Noeud où le lien va démarer
	 */
	public NodeLinkDialog(AbstractBookNode firstNode, Book book) {
		super("Création du choix", true);
		
		this.firstNode = firstNode;
		this.book = book;
		
		requirementComponentList = new ArrayList();
		
		//Si le premier noeud est de type aléatoire
		if(firstNode instanceof BookNodeWithRandomChoices){			
			showRandomUi();
		}
		//Si le premier noeud est de type combat
		else if(firstNode instanceof BookNodeCombat){
			List<String> listChoixCombat = choixCombatRestants();
			
			for(String choixCombat : listChoixCombat)
				choixLienBox.getItems().add(choixCombat);
			
			choixLienBox.setValue(listChoixCombat.get(0));
			showCombatUi();
		}
		
		this.showAndWait();
	}

	/**
	 * Edition de la fenêtre de dialog
	 * @param nodeLink Lien déjà existante
	 * @param firstNode Noeud où le lien démarre
	 */
	public NodeLinkDialog(BookNodeLink nodeLink, AbstractBookNode firstNode, Book book) {
		super("Modification du choix", true);
		
		this.firstNode = firstNode;
		this.book = book;
		requirementComponentList = new ArrayList();
		//Si le premier noeud est de type aléatoire
		if(nodeLink instanceof BookNodeLinkRandom){
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			
			chanceTextField.setText(""+nodeLinkRandom.getChance());
			
			showRandomUi();
		}
		//Si le premier noeud est de type combat
		else if(firstNode instanceof BookNodeCombat){
			List<String> listChoixCombat = choixCombatRestants();
			
			for(String choix : listChoixCombat) {
				choixLienBox.getItems().add(choix);
			}
			
			if(!listChoixCombat.isEmpty())
				choixLienBox.setValue(listChoixCombat.get(0));
			
			BookNodeCombat bookNodeCombat = (BookNodeCombat) firstNode;
			
			if(bookNodeCombat.getEvasionBookNodeLink() == nodeLink){
				choixLienBox.getItems().add(EVASION);
				choixLienBox.setValue(EVASION);
			} else if(bookNodeCombat.getWinBookNodeLink() == nodeLink){
				choixLienBox.getItems().add(GAGNE);		
				choixLienBox.setValue(GAGNE);
			} else if(bookNodeCombat.getLooseBookNodeLink() == nodeLink){
				choixLienBox.getItems().add(PERDRE);			
				choixLienBox.setValue(PERDRE);
			}
			showCombatUi();
		}
		
		hpTextField.setText(""+nodeLink.getHp());
		goldTextField.setText(""+nodeLink.getGold());
		autoBox.setSelected(nodeLink.getAuto());
		
		texte.setText(nodeLink.getText());	
		
		if(nodeLink.getRequirements() != null) {
			for(List<AbstractRequirement> andRequirements : nodeLink.getRequirements())
				createAccordion(andRequirements);
		}
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		tabPane = new TabPane();
		
		Tab nodeLink = new Tab("Lien");
		nodeLink.setClosable(false);
		nodeLink.setContent(createNodeLinkPane());
		
		Tab requirementTab = new Tab("Prérequis");
		requirementTab.setClosable(false);
		requirementTab.setContent(createPaneRequirement());
		
		tabPane.getTabs().addAll(nodeLink, requirementTab);
		
		return tabPane;
	}
	
	private BorderPane createNodeLinkPane(){
		//Génération des Pane
		root = new BorderPane();
		root.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		mainUi = new GridPane();
		randomUi = new GridPane();
		combatUi = new GridPane();
		
		mainUi.setHgap(UiConsts.DEFAULT_MARGIN);
		mainUi.setVgap(UiConsts.DEFAULT_MARGIN);
		
		randomUi.setHgap(UiConsts.DEFAULT_MARGIN);
		randomUi.setVgap(UiConsts.DEFAULT_MARGIN);
		
		combatUi.setHgap(UiConsts.DEFAULT_MARGIN);
		combatUi.setVgap(UiConsts.DEFAULT_MARGIN);
		
		Label textLabel = new Label("Texte choix :");
		texte = new TextArea();
		texte.setWrapText(true);

		chanceTextField = new TextField("0");
		
		hpTextField = new TextField("0");
		
		goldTextField = new TextField("0");
		
		autoBox = new CheckBox("Prendre ce choix automatiquement ?");
		
		choixLienBox = new ChoiceBox();
		
		mainUi.add(textLabel, 0, 0);
		mainUi.add(texte, 0, 1, 4, 1);	
		mainUi.add(new Label("HP (gain ou perte)"), 0, 2);
		mainUi.add(hpTextField, 1, 2);
		mainUi.add(new Label("Monnaie (gain ou perte)"), 0, 3);
		mainUi.add(goldTextField, 1, 3);
		mainUi.add(autoBox, 0, 4);
		autoBox.setSelected(false);
		
		randomUi.add(new Label("Chance pour aller dans vers ce lien"), 0, 0);
		randomUi.add(chanceTextField, 1, 0);
	
		combatUi.add(new Label("Type de lien"), 3, 0);
		combatUi.add(choixLienBox, 4, 0);
		
		root.setCenter(mainUi);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			//Si le premier noeud est de type aléatoire
			if(NodeLinkDialog.this.firstNode instanceof BookNodeWithRandomChoices){
				if (chanceTextField.getText().isEmpty()){
					return;
				}
				
				try {
					int chanceInt = Integer.parseInt(chanceTextField.getText());
					
					BookNodeLinkRandom nodeLinkRandom = new BookNodeLinkRandom();
					nodeLinkRandom.setChance(chanceInt);
					
					nodeLink = nodeLinkRandom;
				} catch (NumberFormatException ex){
					notANumberAlertDialog(ex);
					return;
				}
			} else {
				nodeLink = new BookNodeLink();
			}
			
			if (hpTextField.getText().isEmpty()|| goldTextField.getText().isEmpty()){
				return;
			}
			
			try {
				int hpInt = Integer.parseInt(hpTextField.getText());
				int goldInt = Integer.parseInt(goldTextField.getText());
				
				nodeLink.setGold(goldInt);
				nodeLink.setHp(hpInt);
			} catch (NumberFormatException ex){
				notANumberAlertDialog(ex);
				return;
			}

			nodeLink.setText(texteHistoire);
			nodeLink.setAuto(autoBox.isSelected());
			nodeLink.setRequirements(getRequirement());
			
			linkType = choixLienBox.getValue();

			close();
		};
	}
	
	/**
	 * Montre la fenêtre pour un noeud de départ à choix random
	 */
	private void showRandomUi() {
		root.setBottom(randomUi);
	}
	
	/**
	 * Montre la fenêtre pour un noeud de départ de combat
	 */
	private void showCombatUi() {
		root.setBottom(combatUi);
	}
	
	/**
	 * Ajoute les choix encore disponible pour un noeud de départ de type combat
	 * @return Liste des choix entre GAGNE, PERDRE, EVASION
	 */
	private List<String> choixCombatRestants(){
		List<String> listeChoix = new ArrayList();
		
		//Si le premier noeud est de type combat, on fait la liste des noeud encore disponible (Evasion, Gagner, Perdu)
		if(firstNode instanceof BookNodeCombat){
			BookNodeCombat firstNodeCombat = (BookNodeCombat) firstNode;
			
			if(firstNodeCombat.getWinBookNodeLink() == null)
				listeChoix.add(GAGNE);
			
			if(firstNodeCombat.getLooseBookNodeLink() == null)
				listeChoix.add(PERDRE);
			
			if(firstNodeCombat.getEvasionBookNodeLink() == null)
				listeChoix.add(EVASION);
		}
		
		return listeChoix;
	}
	
	private Node createPaneRequirement(){
		VBox requirementPane = new VBox();
		requirementPane.setSpacing(UiConsts.DEFAULT_MARGIN);
		
		accordion = new Accordion();
		accordion.setPadding(UiConsts.DEFAULT_INSET_DIALOG_MAIN_UI);
		
		Button addButton = new Button("Ajouter");
		addButton.setOnAction((ActionEvent e) -> {
			createAccordion(null);
		});
		
		VBox buttonBox = new VBox();
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		buttonBox.getChildren().add(addButton);
		buttonBox.setPadding(new Insets(0, UiConsts.DEFAULT_MARGIN_DIALOG, 0, 0));
		
		requirementPane.getChildren().addAll(accordion, buttonBox);
		
		ScrollPane scrollPane = new ScrollPane(requirementPane);
		scrollPane.setFitToWidth(true);
		
		return scrollPane;
	}
	
	private void createAccordion(List<AbstractRequirement> andRequirements) {
		TitledPane titledPane = new TitledPane();
		
		RequirementComponent requirementComponent = new RequirementComponent(book);
		Button remove = new Button("Supprimer cette partie");
		
		if(andRequirements != null)
			requirementComponent.setRequirement(andRequirements);
		
		remove.setOnAction((ActionEvent e) -> {	
			accordion.getPanes().remove(titledPane);
			this.requirementComponentList.remove(requirementComponent);
		});
		
		HBox removeBox = new HBox();
		removeBox.getChildren().add(remove);
		removeBox.setAlignment(Pos.CENTER_RIGHT);
		
		VBox titledPaneBox = new VBox();
		titledPaneBox.getChildren().addAll(requirementComponent, removeBox);
		titledPaneBox.setPadding(UiConsts.DEFAULT_INSET_DIALOG);
		
		titledPane.setContent(titledPaneBox);
		
		accordion.getPanes().add(titledPane);
		this.requirementComponentList.add(requirementComponent);
	}
	
	private List<List<AbstractRequirement>> getRequirement(){
		if(requirementComponentList.isEmpty())
			return new ArrayList<>();
		
		List<List<AbstractRequirement>> listAbstractRequirement = new ArrayList();
		for (RequirementComponent requirementComponent : requirementComponentList){
			List<AbstractRequirement> andRequirements = requirementComponent.getRequirement();
			if(!andRequirements.isEmpty())
				listAbstractRequirement.add(andRequirements);
		}
		
		return listAbstractRequirement;
	}
	
	/**
	 * Donne le lien en fonction du noeud
	 * @return Lien
	 */
	public BookNodeLink getNodeLink() {
		return nodeLink;
	}

	/**
	 * Donne le type de lien
	 * @return Type de lien
	 */
	public String getLinkType() {
		return linkType;
	}

}
