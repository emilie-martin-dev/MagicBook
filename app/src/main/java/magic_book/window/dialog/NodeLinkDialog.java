package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
	 * L'onglet qui permet de sélectionner les prérequis
	 */
	private Tab requirementTab;
	
	/**
	 * Le TabPane qui permet de gérer les différents onglets de l boite de dialogue
	 */
	private TabPane tabPane;
	
	/**
	 * Permet l'ajout de prérequis
	 */
	private RequirementComponent requirement;
	
	/**
	 * CheckBox de chemin automatique
	 */
	private CheckBox autoBox;
	/**
	 * Choix du type de lien
	 */
	private ChoiceBox<String> choixLienBox;

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
	
	private Book book;
	
	/**
	 * Initialisation des valeurs et de la fenêtre de dialog en fonction du fistNode
	 * @param firstNode Noeud où le lien va démarer
	 */
	public NodeLinkDialog(AbstractBookNode firstNode, Book book) {
		super("Création du choix");
		
		this.firstNode = firstNode;
		this.book = book;
		
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
		
		showNodeLink();
		
		this.showAndWait();
	}

	/**
	 * Edition de la fenêtre de dialog
	 * @param nodeLink Lien déjà existante
	 * @param firstNode Noeud où le lien démarre
	 */
	public NodeLinkDialog(BookNodeLink nodeLink, AbstractBookNode firstNode, Book book) {
		super("Modification du choix");
		
		this.firstNode = firstNode;
		this.book = book;
		
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
		
		showNodeLink();
		requirement.setRequirement(nodeLink.getRequirements());
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {	
		tabPane = new TabPane();
		
		Tab nodeTab = new Tab("Lien");
		
		nodeTab.setClosable(false);
		nodeTab.setContent(createNodeLinkPane());
		
		tabPane.getTabs().addAll(nodeTab);
		
		return tabPane;
	}
	
	private BorderPane createNodeLinkPane(){
		//Génération des Pane
		root = new BorderPane();
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
		mainUi.add(new Label("Gold (gain ou perte)"), 0, 3);
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
					nodeLinkRandom.setRequirements(getRequirement());
					
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

	private void showNodeLink(){
		requirementTab = createRequirementTab();
	}
	
	private Tab createRequirementTab() {
		deleteItemsTab(requirementTab);
		
		requirementTab = new Tab("Requirement");

		requirementTab.setClosable(false);
		
		if(requirement == null) {
			requirement = new RequirementComponent(book);
			requirementTab.setContent(requirement);
			System.out.println("REQUIREMENT NULL");
			requirement.setPadding(UiConsts.DEFAULT_INSET_DIALOG);
		}
		
		tabPane.getTabs().addAll(requirementTab);
		return requirementTab;
	}
	
	private List<List<AbstractRequirement>> getRequirement(){
		if(requirement == null)
			return null;
		
		List<List<AbstractRequirement>> listAbstractRequirement = new ArrayList();
		listAbstractRequirement.add(requirement.getRequirementItem());
		listAbstractRequirement.add(requirement.getRequirementMoney());
		
		return listAbstractRequirement;
	}
	
	private void deleteItemsTab(Tab tab) {
		tabPane.getTabs().remove(tab);
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
