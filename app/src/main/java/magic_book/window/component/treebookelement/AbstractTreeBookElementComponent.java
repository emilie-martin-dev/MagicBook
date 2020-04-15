package magic_book.window.component.treebookelement;

import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import magic_book.core.Book;

/**
 * Création du coté gauche de la fenêtre Windows (Mode, Personnages, Items)
 */
public abstract class AbstractTreeBookElementComponent<T> extends TreeView<T> {
	
	private static final double MAX_HEIGHT = 250d;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book book;
	
	private TreeItem<T> rootItem;
	
	/**
	 * Constructeur
	 * @param book Livre contenant toutes les informations
	 */
	public AbstractTreeBookElementComponent(Book book){
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
	
	protected abstract void bookChanged(Book oldBook, Book newBook);
	
	protected abstract T createRootElement();
	
	protected abstract void addBookElementContextMenu();
	
	protected abstract void updateBookElementContextMenu(T element);
	
	protected abstract void removeBookElementContextMenu(T element);
	
	protected abstract String getAddContextMenuString();
	
	protected abstract String getUpdateContextMenuString();
	
	protected abstract String getDeleteContextMenuString();

	public TreeItem<T> getRootItem() {
		return rootItem;
	}
	
	public void setBook(Book book) {
		rootItem.getChildren().clear();
		
		bookChanged(this.book, book);
		
		this.book = book;
	}

	public Book getBook() {
		return book;
	}
	
}
