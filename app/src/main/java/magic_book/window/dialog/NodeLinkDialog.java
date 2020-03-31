package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.window.UiConsts;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class NodeLinkDialog extends AbstractDialog{
	
	private BookNodeLink nodeLink;
	
	private String linkType;
	
	public static final String EVASION = "Lien pour l'évasion";
	public static final String PERDRE = "Lien si le combat est perdu";
	public static final String GAGNE = "Lien si le combat est gagné";
	
	private TextArea texte;
	private TextField chanceTextField;
	private TextField hpTextField;
	private TextField goldTextField;
	
	private CheckBox autoBox;
	private ChoiceBox<String> choixLienBox;

	private AbstractBookNode firstNode;
	
	private BorderPane root;
	private GridPane mainUi;
	private GridPane randomUi;
	private GridPane combatUi;
	
	public NodeLinkDialog(AbstractBookNode firstNode) {
		super("Création du choix");
		
		this.firstNode = firstNode;
		
		if(firstNode instanceof BookNodeWithRandomChoices){
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			
			showRandomUi();
		} else if(firstNode instanceof BookNodeCombat){
			List<String> listChoixCombat = choixCombatRestants();
			
			for(String choixCombat : listChoixCombat)
				choixLienBox.getItems().add(choixCombat);
			
			choixLienBox.setValue(listChoixCombat.get(0));
			showCombatUi();
		}
		
		this.showAndWait();
	}

	public NodeLinkDialog(BookNodeLink nodeLink, AbstractBookNode firstNode) {
		super("Modification du choix");
		
		this.firstNode = firstNode;
		
		if(nodeLink instanceof BookNodeLinkRandom){
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			
			chanceTextField.setText(""+nodeLinkRandom.getChance());
			
			showRandomUi();
		} else if(firstNode instanceof BookNodeCombat){
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
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
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
			
			linkType = choixLienBox.getValue();

			close();
		};
	}
	
	private void showRandomUi() {
		root.setBottom(randomUi);
	}
	
	private void showCombatUi() {
		root.setBottom(combatUi);
	}
	
	private List<String> choixCombatRestants(){
		List<String> listeChoix = new ArrayList();
		
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

	public BookNodeLink getNodeLink() {
		return nodeLink;
	}

	public String getLinkType() {
		return linkType;
	}

}
