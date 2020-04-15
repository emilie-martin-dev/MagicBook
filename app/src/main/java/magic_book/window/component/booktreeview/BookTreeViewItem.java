package magic_book.window.component.booktreeview;

import java.util.Map;
import javafx.scene.control.TreeItem;
import magic_book.core.Book;
import magic_book.core.item.BookItem;
import magic_book.observer.book.BookItemObserver;
import magic_book.window.dialog.ItemDialog;


public class BookTreeViewItem extends AbstractBookTreeView<BookItem> implements BookItemObserver {

	public BookTreeViewItem(Book book) {
		super(book);
	}

	@Override
	protected void bookChanged(Book oldBook, Book newBook) {
		if(oldBook != null) {
			oldBook.removeItemObserver(this);
		}
		
		newBook.addItemObserver(this);
		
		for(Map.Entry<String, BookItem> entry : newBook.getItems().entrySet()) {
			itemAdded(entry.getValue());
		}
	}

	@Override
	protected BookItem createRootElement() {
		return new BookItem("", "Items");
	}

	@Override
	protected void addBookElementContextMenu() {
		ItemDialog itemDialog = new ItemDialog(getBook());
				
		if(itemDialog.isValid()) {
			getBook().addItem(itemDialog.getItem());
		}
	}

	@Override
	protected void updateBookElementContextMenu(BookItem element) {
		ItemDialog newItemDialog = new ItemDialog(element, getBook());

		if(!newItemDialog.isValid()){
			return;
		}

		BookItem newItem = newItemDialog.getItem();

		getBook().updateItem(element, newItem);
		
	}

	@Override
	protected void removeBookElementContextMenu(BookItem element) {
		getBook().removeItem(element);
	}
	
	@Override
	public void itemAdded(BookItem item) {
		getRoot().getChildren().add(new TreeItem<>(item));	
	}

	@Override
	public void itemEdited(BookItem oldItem, BookItem newItem) {
		for(TreeItem<BookItem> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == oldItem) {
				treeItem.setValue(newItem);
				break;
			}
		}
	}

	@Override
	public void itemDeleted(BookItem item) {
		for(TreeItem<BookItem> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == item) {
				getRoot().getChildren().remove(treeItem);
				break;
			}
		}
	}

	@Override
	protected String getAddContextMenuString() {
		return "Ajouter un item";
	}

	@Override
	protected String getUpdateContextMenuString() {
		return "Modifier un item";
	}

	@Override
	protected String getDeleteContextMenuString() {
		return "Supprimer un item";
	}

}
