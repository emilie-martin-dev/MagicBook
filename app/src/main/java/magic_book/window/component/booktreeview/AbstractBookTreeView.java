package magic_book.window.component.booktreeview;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import magic_book.core.Book;

/**
 * Création du coté gauche de la fenêtre Windows (Mode, Personnages, Items)
 */
public abstract class AbstractBookTreeView<T> extends TreeView<T> {
	
	private static final double MAX_HEIGHT = 250d;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	/**
	 * Le TreeItem parent. Ne peut être supprimé
	 */
	private TreeItem<T> rootItem;
	
	/**
	 * Constructeur
	 * @param book Livre contenant toutes les informations
	 */
	public AbstractBookTreeView(Book book){
		rootItem = new TreeItem<>(createRootElement());
		rootItem.setExpanded(true);
		
		this.setRoot(rootItem);

		ContextMenu contextMenu = new ContextMenu();
		MenuItem menuAddElement = new MenuItem(getAddContextMenuString());
		MenuItem menuUpdateElement = new MenuItem(getUpdateContextMenuString());
		MenuItem menuDeleteElement = new MenuItem(getDeleteContextMenuString());

		menuAddElement.setOnAction((ActionEvent event) -> {
			addBookElementContextMenu();
		});
		
		menuUpdateElement.setOnAction((ActionEvent event) -> {
			TreeItem<T> selectedItem = this.getSelectionModel().getSelectedItem();
			
			if(selectedItem != null && selectedItem != rootItem) {
				updateBookElementContextMenu(selectedItem.getValue());
			}
			
			this.refresh();
		});
		
		menuDeleteElement.setOnAction((ActionEvent event) -> {
			TreeItem<T> selectedItem = this.getSelectionModel().getSelectedItem();
			
			if(selectedItem != rootItem)
				removeBookElementContextMenu(selectedItem.getValue());
		});
		
		contextMenu.getItems().addAll(menuAddElement, menuUpdateElement, menuDeleteElement);
		this.setContextMenu(contextMenu);
		
		this.setBook(book);
		
		this.setMaxHeight(MAX_HEIGHT);
	}
	
	/**
	 * Permet de se désinscrire de l'ancien book et de se mettre à jour suivant le nouveau
	 * @param oldBook L'ancien livre
	 * @param newBook Le nouveau livre
	 */
	protected abstract void bookChanged(Book oldBook, Book newBook);
	
	/**
	 * Créé le TreeItem parent
	 * @return Le TreeItem parent
	 */
	protected abstract T createRootElement();
	
	/**
	 * Action réalisée quand on clique sur le menu ajouter
	 */	
	protected abstract void addBookElementContextMenu();
	
	/**
	 * Action réalisée quand on clique sur le menu mettre à jour
	 * @param element L'élément actuellement sélectionné sur la TreeView
	 */
	protected abstract void updateBookElementContextMenu(T element);
	
	/**
	 * Action réalisée quand on clique sur le menu supprimer
	 * @param element L'élément actuellement sélectionné sur la TreeView
	 */
	protected abstract void removeBookElementContextMenu(T element);
	
	/**
	 * Retourne le texte pour le menu d'ajout
	 * @return le texte pour le menu d'ajout
	 */
	protected abstract String getAddContextMenuString();
	
	/**
	 * Retourne le texte pour le menu de mise à jour
	 * @return le texte pour le menu de mise à jour
	 */
	protected abstract String getUpdateContextMenuString();
	
	/**
	 * Retourne le texte pour le mise de suppression
	 * @return le texte pour le menu de suppression
	 */
	protected abstract String getDeleteContextMenuString();

	/**
	 * Retourne le TreeItem parent
	 * @return 
	 */
	public TreeItem<T> getRootItem() {
		return rootItem;
	}
	
	/**
	 * Change le livre
	 * @param book Le livre
	 */
	public void setBook(Book book) {
		rootItem.getChildren().clear();
		
		bookChanged(this.book, book);
		
		this.book = book;
	}

	/**
	 * Retourne le livre
	 * @return Le livre
	 */
	public Book getBook() {
		return book;
	}
	
}
