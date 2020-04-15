package magic_book.window.pane;

import java.io.InputStream;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import magic_book.core.Book;
import magic_book.window.Mode;
import magic_book.window.UiConsts;
import magic_book.window.component.booktreeview.BookTreeViewCharacter;
import magic_book.window.component.booktreeview.BookTreeViewItem;
import magic_book.window.component.booktreeview.BookTreeViewSkill;

/**
 * Création du coté gauche de la fenêtre Windows (Mode, Personnages, Items)
 */
public class LeftPane extends ScrollPane {
	
	/**
	 * GraphPane sur lequel on met à jour le mode
	 */
	private GraphPane graphPane;
	
	/**
	 * Groupe qui contient la liste des boutons pour le mode
	 */
	private ToggleGroup toggleGroup;
	/**
	 * TreeView pour les items
	 */
	private BookTreeViewItem bookTreeViewItem;
	/**
	 * TreeView pour les personnages
	 */
	private BookTreeViewCharacter bookTreeViewCharacter;
	/**
	 * TreeView pour les skills
	 */
	private BookTreeViewSkill bookTreeViewSkill;
	
	/**
	 * Constructeur
	 * @param graphPane Centre de la fenêtre MainWindows
	 * @param book Livre contenant toutes les informations
	 */
	public LeftPane(GraphPane graphPane, Book book){
		this.graphPane = graphPane;
		
		this.setMaxWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setMinWidth(UiConsts.LEFT_PANEL_SIZE);
		this.setPadding(UiConsts.DEFAULT_INSET);
		this.setFitToWidth(true);
				
		this.setContent(createLeftPanel(book));
		setBook(book);
	}
	
	/**
	 * Création de tout le panel : bouton de mode, vue sur les items, vue sur les personnages
	 * @return Tout le panel
	 */
	private Node createLeftPanel(Book book) {
		VBox leftContent = new VBox();
		
		//Création des boutons de mode
		ToggleButton selectToogle = createToggleButton("icons/select.png", Mode.SELECT);
		ToggleButton addNodeToggle = createToggleButton("icons/add_node.png", Mode.ADD_NODE);
		ToggleButton addNodeLinkToggle = createToggleButton("icons/add_link.png", Mode.ADD_NODE_LINK);
		ToggleButton suppNode = createToggleButton("icons/delete.png", Mode.DELETE);
		ToggleButton firstNode = createToggleButton("icons/first_node.png", Mode.FIRST_NODE);
		
		selectToogle.setSelected(true);
		graphPane.setMode(Mode.SELECT);

		FlowPane flow = new FlowPane();
		flow.setHgap(UiConsts.DEFAULT_MARGIN);
		flow.setVgap(UiConsts.DEFAULT_MARGIN);
		flow.getChildren().addAll(selectToogle, addNodeToggle, addNodeLinkToggle, suppNode, firstNode);
		leftContent.setSpacing(UiConsts.DEFAULT_MARGIN);
		leftContent.getChildren().add(flow);
		
		//Box pour les treeview
		VBox treeViews = bookTreeViews(book);
		leftContent.getChildren().add(treeViews);
		
		return leftContent;
	}
	
	/**
	 * Création des treeview
	 * @return VBox contenant la vue
	 */
	private VBox bookTreeViews(Book book){
		bookTreeViewItem = new BookTreeViewItem(book);
		bookTreeViewCharacter = new BookTreeViewCharacter(book);
		bookTreeViewSkill = new BookTreeViewSkill(book);
		
		VBox vBox = new VBox(bookTreeViewCharacter, bookTreeViewItem, bookTreeViewSkill);
		vBox.setSpacing(UiConsts.DEFAULT_MARGIN);

		return vBox;
	}
	
	/**
	 * On change de livre, toute la vue sur les items et personnages se met à jour
	 * @param book Nouveau livre contenant toutes les informations
	 */
	public void setBook(Book book) {
		bookTreeViewCharacter.setBook(book);
		bookTreeViewItem.setBook(book);
		bookTreeViewSkill.setBook(book);
	}

	/**
	 * Création d'un bouton de mode
	 * @param path Adresse de l'image
	 * @param mode Mode attribué à ce bouton
	 * @return Le bouton créer
	 */
	private ToggleButton createToggleButton(String path, Mode mode) {
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
		ImageView imageView = new ImageView(new Image(stream));
		imageView.setFitHeight(40);		
		imageView.setFitWidth(40);
		ToggleButton toggleButton = new ToggleButton("", imageView);
		
		imageView.setPreserveRatio(true);
		toggleButton.setMinSize(UiConsts.CONTROL_BUTTON_SIZE, UiConsts.CONTROL_BUTTON_SIZE);
		toggleButton.setMaxSize(UiConsts.CONTROL_BUTTON_SIZE, UiConsts.CONTROL_BUTTON_SIZE);

		toggleButton.setOnAction((ActionEvent e) -> {
			if(toggleGroup.getSelectedToggle() == null) {
				toggleGroup.selectToggle(toggleButton);
				return;
			}
			
			graphPane.setMode(mode);
			graphPane.setSelectedNodeFx(null);
		});

		if (this.toggleGroup == null) {
			this.toggleGroup = new ToggleGroup();
		}

		this.toggleGroup.getToggles().add(toggleButton);

		return toggleButton;
	}
	
}
