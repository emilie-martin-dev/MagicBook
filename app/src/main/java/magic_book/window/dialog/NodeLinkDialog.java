package magic_book.window.dialog;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class NodeLinkDialog extends AbstractDialog{
	
	private TextArea texte;
	
	private BookNodeLink nodeLink;
	
	private final String EVASION = "Lien pour l'évasion";
	private final String PERDRE = "Lien si le combat est perdu";
	private final String GAGNE = "Lien si le combat est gagné";
	
	private TextField chanceTextField;
	private TextField hpTextField;
	private TextField goldTextField;
	private Label chanceLabel;
	private Label hpLabel;
	private Label goldLabel;
	private Label autoLabel;
	private Label choixLienLabel;
	
	
	private ChoiceBox autoBox;
	private ChoiceBox choixLienBox;

	private AbstractBookNode firstNode;
	
	private GridPane root;
	
	public NodeLinkDialog(AbstractBookNode firstNode) {
		super("Création du choix");
		this.firstNode = firstNode;
		if(firstNode instanceof BookNodeWithRandomChoices){
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			root.add(chanceLabel, 0, 2);
			root.add(chanceTextField, 1, 2);
			this.nodeLink = nodeLinkRandom;
		} else {
			if(firstNode instanceof BookNodeCombat){
				List<String> listChoixCombat = listeChoix();
				for(String listeChoix : listChoixCombat)
				choixLienBox.getItems().add(listeChoix);
				choixLienBox.setValue(listChoixCombat.get(0));
				root.add(choixLienLabel, 3, 4);
				root.add(choixLienBox, 4, 4);
			}
			root.add(hpLabel, 0, 2);
			root.add(hpTextField, 1, 2);
			root.add(goldLabel, 0, 3);
			root.add(goldTextField, 1, 3);
			root.add(autoLabel, 0, 4);
			root.add(autoBox, 1, 4);
			autoBox.setValue(false);
		}
		this.showAndWait();
	}

	public NodeLinkDialog(BookNodeLink nodeLink,AbstractBookNode firstNode) {
		super("Modification du choix");
		this.firstNode = firstNode;
		this.nodeLink = nodeLink;
		System.out.println(firstNode);
		if(nodeLink instanceof BookNodeLinkRandom){
			root.add(chanceLabel, 0, 2);
			root.add(chanceTextField, 1, 2);
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			chanceTextField.setText(String.valueOf(nodeLinkRandom.getChance()));
			this.nodeLink = nodeLinkRandom;
		} else {
			if(firstNode instanceof BookNodeCombat){
				List<String> listChoixCombat = listeChoix();
				for(String listeChoix : listChoixCombat)
					choixLienBox.getItems().add(listeChoix);
				
				if(((BookNodeCombat) firstNode).getEvasionBookNodeLink() == this.nodeLink){
					choixLienBox.getItems().add(EVASION);
					choixLienBox.setValue(EVASION);
					
				} else if(((BookNodeCombat) firstNode).getWinBookNodeLink() == this.nodeLink){
					choixLienBox.getItems().add(GAGNE);
					choixLienBox.setValue(GAGNE);
					
				} else if(((BookNodeCombat) firstNode).getLooseBookNodeLink() == this.nodeLink){
					choixLienBox.getItems().add(PERDRE);
					choixLienBox.setValue(PERDRE);
				}
				
				root.add(choixLienLabel, 3, 4);
				root.add(choixLienBox, 4, 4);
			}
			root.add(hpLabel, 0, 2);
			root.add(hpTextField, 1, 2);
			root.add(goldLabel, 0, 3);
			root.add(goldTextField, 1, 3);
			root.add(autoLabel, 0, 4);
			root.add(autoBox, 1, 4);
			hpTextField.setText(String.valueOf(nodeLink.getHp()));
			goldTextField.setText(String.valueOf(nodeLink.getGold()));
			autoBox.setValue(nodeLink.getAuto());
		}
		texte.setText(nodeLink.getText());	
		
		this.showAndWait();
	}

	@Override
	protected Node getMainUI() {
		root = new GridPane();
		
		root.setHgap(5);
		root.setVgap(5);
		
		Label textLabel = new Label("Texte choix :");
		texte = new TextArea();
		texte.setWrapText(true);

		chanceLabel = new Label("Chance pour aller dans vers ce lien");
		chanceTextField = new TextField();
		
		hpLabel = new Label("HP (gain ou perte)");
		hpTextField = new TextField();
		
		goldLabel = new Label("Gold (gain ou perte)");
		goldTextField = new TextField();
		
		autoLabel = new Label("Prendre item en auto ?");
		autoBox = new ChoiceBox();
		autoBox.getItems().add(true);
		autoBox.getItems().add(false);
		
		choixLienLabel = new Label("Type de lien");
		choixLienBox = new ChoiceBox();
		
		
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 4, 1);
		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			
			
			if(NodeLinkDialog.this.nodeLink == null) {
				if(NodeLinkDialog.this.firstNode instanceof BookNodeWithRandomChoices){
					if (chanceTextField.getText().isEmpty()){
						return;
					}
					try {
						int chanceInt = Integer.parseInt(chanceTextField.getText());
						NodeLinkDialog.this.nodeLink = new BookNodeLinkRandom(texteHistoire, -1, chanceInt);
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
				}
				else{
					if(hpTextField.getText().isEmpty()||goldTextField.getText().isEmpty()){
						return;
					}
					try {
						int hpInt = Integer.parseInt(hpTextField.getText());
						int goldInt = Integer.parseInt(goldTextField.getText());
						NodeLinkDialog.this.nodeLink = new BookNodeLink(texteHistoire, -1, null, hpInt, goldInt, (boolean) autoBox.getValue());
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
					if(NodeLinkDialog.this.firstNode instanceof BookNodeCombat){
						BookNodeCombat firstNodeCombat = (BookNodeCombat) NodeLinkDialog.this.firstNode;
						if(NodeLinkDialog.this.choixLienBox.getValue() == EVASION)
							firstNodeCombat.setEvasionBookNodeLink(NodeLinkDialog.this.nodeLink);
						else if(NodeLinkDialog.this.choixLienBox.getValue() == PERDRE)
							firstNodeCombat.setLooseBookNodeLink(NodeLinkDialog.this.nodeLink);
						else if(NodeLinkDialog.this.choixLienBox.getValue() == GAGNE)
							firstNodeCombat.setWinBookNodeLink(NodeLinkDialog.this.nodeLink);
					}
					
				}
			} else {
					
				if(NodeLinkDialog.this.firstNode instanceof BookNodeWithRandomChoices){
					if (chanceTextField.getText().isEmpty()){
						return;
					} 
					try {
						int chanceInt = Integer.parseInt(chanceTextField.getText());
						BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
						nodeLinkRandom.setChance(chanceInt);
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
				} else {
					if (hpTextField.getText().isEmpty()||goldTextField.getText().isEmpty()){
						return;
					} 
					try {
						int hpInt = Integer.parseInt(hpTextField.getText());
						int goldInt = Integer.parseInt(goldTextField.getText());
						NodeLinkDialog.this.nodeLink.setGold(goldInt);
						NodeLinkDialog.this.nodeLink.setHp(hpInt);
					} catch (NumberFormatException ex){
						notANumberAlertDialog(ex);
						return;
					}
					NodeLinkDialog.this.nodeLink.setText(texteHistoire);
					if(NodeLinkDialog.this.firstNode instanceof BookNodeCombat){
						BookNodeCombat firstNodeCombat = (BookNodeCombat) NodeLinkDialog.this.firstNode;
						if(NodeLinkDialog.this.choixLienBox.getValue() == EVASION)
							firstNodeCombat.setEvasionBookNodeLink(NodeLinkDialog.this.nodeLink);
						else if(NodeLinkDialog.this.choixLienBox.getValue() == PERDRE)
							firstNodeCombat.setLooseBookNodeLink(NodeLinkDialog.this.nodeLink);
						else if(NodeLinkDialog.this.choixLienBox.getValue() == GAGNE)
							firstNodeCombat.setWinBookNodeLink(NodeLinkDialog.this.nodeLink);
					}
				}
			}

			close();
		};
	}
	
	private List<String> listeChoix(){
		List<String> listeChoix = new ArrayList();
		System.out.println("rentre dans liste choix");
		if(firstNode instanceof BookNodeCombat){
			System.out.println("okip");
			BookNodeCombat firstNodeCombat = (BookNodeCombat) firstNode;
			if(firstNodeCombat.getEvasionBookNodeLink() == null)
				listeChoix.add(EVASION);
			if(firstNodeCombat.getLooseBookNodeLink() == null)
				listeChoix.add(PERDRE);
			if(firstNodeCombat.getWinBookNodeLink() == null)
				listeChoix.add(GAGNE);
		}
		return listeChoix;
	}

	public BookNodeLink getNodeLink() {
		return nodeLink;
	}
	
}
