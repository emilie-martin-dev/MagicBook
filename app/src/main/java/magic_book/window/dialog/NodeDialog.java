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
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

 public class NodeDialog extends AbstractDialog {

	private static final String BASIC = "Basic";
	private static final String BASICRANDOM = "Basic random";
	private static final String COMBAT = "Combat";
	private static final String FAILURE = "Défaite";
	private static final String VICTORY = "Victoire";
	 
	private TextArea texte;
	private TextField texteEvasion;
	private Label combatLabel;
	private Label evasionLabel;
 	private AbstractBookNode node = null;
 	private ChoiceBox<String> nodeType;
	private ChoiceBox<String> ennemiBox1;
	private ChoiceBox<String> ennemiBox2;
	private ChoiceBox<String> ennemiBox3;
	private ChoiceBox<String> ennemiBox4;
	private GridPane root;
	
	private Book book;

 	public NodeDialog(Book book) {
 		super("Creation d'une page");
		
		this.book = book;
		this.ennemiBox1 = ennemiBox1;
		this.ennemiBox2 = ennemiBox2;
		this.ennemiBox3 = ennemiBox3;
		this.ennemiBox4 = ennemiBox4;
		this.root = root;
 		this.showAndWait();
 	}

 	public NodeDialog(Book book, AbstractBookNode node) {
		super("Edition de la page");
 		this.book = book;
 		texte.setText(node.getText());
		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal terminalNode = (BookNodeTerminal) node;
			nodeType.setValue(terminalNode.getBookNodeStatus() == BookNodeStatus.FAILURE ? FAILURE : VICTORY);
		} else if (node instanceof BookNodeCombat){
			BookNodeCombat terminalNode = (BookNodeCombat) node;
			nodeType.setValue(COMBAT);
			System.out.println(terminalNode.getEnnemiesId().size());
			if(terminalNode.getEnnemiesId().size() > 0)
				ennemiBox1.setValue(terminalNode.getEnnemiesId().get(0));
			if(terminalNode.getEnnemiesId().size() > 1)
				ennemiBox2.setValue(terminalNode.getEnnemiesId().get(1));
			if(terminalNode.getEnnemiesId().size() > 2)
				ennemiBox3.setValue(terminalNode.getEnnemiesId().get(2));
			if(terminalNode.getEnnemiesId().size() > 3)
				ennemiBox4.setValue(terminalNode.getEnnemiesId().get(3));
			texteEvasion.setText(String.valueOf(terminalNode.getEvasionRound()));
		} else if (node instanceof BookNodeWithRandomChoices){
			nodeType.setValue(BASICRANDOM);
		} else {
			nodeType.setValue(BASIC);
		}
 		this.showAndWait();
 	}
	
	@Override
	protected Node getMainUI() {
		root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label textLabel = new Label("Texte :");
		texte = new TextArea();
		texte.setWrapText(true);
		Label labelChoix = new Label("Choix du type du noeud :");
		
		nodeType = new ChoiceBox<>();

 		nodeType.getItems().add(BASIC);
		nodeType.getItems().add(BASICRANDOM);
		nodeType.getItems().add(COMBAT);
 		nodeType.getItems().add(VICTORY);
 		nodeType.getItems().add(FAILURE);
 		nodeType.setValue(BASIC);

		evasionLabel = new Label("Nombre de tour avant de pouvoir s'évader : ");
		texteEvasion = new TextField("");
		
		combatLabel = new Label("Ajout d'ennemi(s) :");
		
		ennemiBox1 = new ChoiceBox<>();
		ennemiBox2 = new ChoiceBox<>();
		ennemiBox3 = new ChoiceBox<>();
		ennemiBox4 = new ChoiceBox<>();
		ennemiBox1.getItems().add(" ");
		ennemiBox2.getItems().add(" ");
		ennemiBox3.getItems().add(" ");
		ennemiBox4.getItems().add(" ");
		addEnnemieBox();
		
		nodeType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				if (nodeType.getValue() == COMBAT){
					root.getChildren().remove(texte);
					addEnnemieBox();
					addCombatNodeShown();
				} else {
					removeCombatNodeShown();
				}
			}
		});
		
		root.add(textLabel, 0, 0);
		root.add(texte, 0, 1, 2, 1);
		root.add(labelChoix, 0, 2);
		root.add(nodeType,1 ,2 );

		
		return root;
	}

	@Override
	protected EventHandler<ActionEvent> getValidButtonEventHandler() {
		return (ActionEvent e) -> {
			String texteHistoire = (String) texte.getText();
			
			if(nodeType.getValue() == BASIC) {
				NodeDialog.this.node = new BookNodeWithChoices(texteHistoire);
			} else if (nodeType.getValue() == BASICRANDOM){
				NodeDialog.this.node = new BookNodeWithRandomChoices(texteHistoire);
			} else if (nodeType.getValue() == COMBAT){
				if (texteEvasion.getText().isEmpty()){
					return;
				} 
				try {
					int tourEvation = Integer.parseInt(texteEvasion.getText());
					NodeDialog.this.node = new BookNodeCombat(texteHistoire, null, null, null, tourEvation, listEnnemie());
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
	
	private void addCombatNodeShown(){
		root.add(texte, 0, 1, 4, 1);
		root.add(combatLabel, 0, 4);
		root.add(ennemiBox1, 1, 4);
		root.add(ennemiBox2, 1, 5);
		root.add(ennemiBox3, 2, 4);
		root.add(ennemiBox4, 2, 5);
		root.add(evasionLabel, 3, 4);
		root.add(texteEvasion, 3, 5);
	}
	
	private void removeCombatNodeShown(){
		root.getChildren().remove(texte);
		root.add(texte, 0, 1, 2, 1);
		root.getChildren().remove(combatLabel);
		root.getChildren().remove(ennemiBox1);
		root.getChildren().remove(ennemiBox2);
		root.getChildren().remove(ennemiBox3);
		root.getChildren().remove(ennemiBox4);
		root.getChildren().remove(evasionLabel);
		root.getChildren().remove(texteEvasion);
	}
	
	private void addEnnemieBox(){
		if(book != null){
			if(!book.getCharacters().isEmpty()){
				for(Map.Entry<String, BookCharacter> mapBookCharacter : book.getCharacters().entrySet()){
					ennemiBox1.getItems().add(mapBookCharacter.getValue().getName());
					ennemiBox2.getItems().add(mapBookCharacter.getValue().getName());
					ennemiBox3.getItems().add(mapBookCharacter.getValue().getName());
					ennemiBox4.getItems().add(mapBookCharacter.getValue().getName());
				}
			}
		}
	}
	
	private List<String> listEnnemie(){
		List<String> listEnnemie = new ArrayList();
		
		if(ennemiBox1.getValue() != null && ennemiBox1.getValue() != " "){
			listEnnemie.add(ennemiBox1.getValue());
		}
		if(ennemiBox2.getValue() != null && ennemiBox2.getValue() != " "){
			listEnnemie.add(ennemiBox2.getValue());
		}
		if(ennemiBox3.getValue() != null && ennemiBox3.getValue() != " "){
			listEnnemie.add(ennemiBox3.getValue());
		}
		if(ennemiBox4.getValue() != null && ennemiBox4.getValue() != " "){
			listEnnemie.add(ennemiBox4.getValue());
		}
		System.out.println(listEnnemie);
		
		return listEnnemie;
	}
	
	
	public AbstractBookNode getNode() {
		return node;
	}
 }
