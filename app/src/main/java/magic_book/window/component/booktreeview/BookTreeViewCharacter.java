package magic_book.window.component.booktreeview;

import java.util.Map;
import javafx.scene.control.TreeItem;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.observer.book.BookCharacterObserver;
import magic_book.window.dialog.CharacterDialog;

public class BookTreeViewCharacter extends AbstractBookTreeView<BookCharacter> implements BookCharacterObserver {

	public BookTreeViewCharacter(Book book) {
		super(book);
	}

	@Override
	protected void bookChanged(Book oldBook, Book newBook) {
		if(oldBook != null) {
			oldBook.removeCharacterObserver(this);
		}
		
		newBook.addCharacterObserver(this);
		
		for(Map.Entry<String, BookCharacter> entry : newBook.getCharacters().entrySet()) {
			if(!entry.getKey().equals(Book.MAIN_CHARACTER_ID))
				characterAdded(entry.getValue());
		}
	}

	@Override
	protected BookCharacter createRootElement() {
		return new BookCharacter("", "Personnages");
	}

	@Override
	protected void addBookElementContextMenu() {
		CharacterDialog characterDialog = new CharacterDialog(getBook());
				
		if(characterDialog.isValid()) {
			BookCharacter perso = characterDialog.getCharacter();

			getBook().addCharacter(perso);
		}
	}

	@Override
	protected void updateBookElementContextMenu(BookCharacter element) {
		CharacterDialog characterDialog = new CharacterDialog(element, getBook());

		if(!characterDialog.isValid())
			return;

		BookCharacter newCharacter = characterDialog.getCharacter();

		getBook().updateCharacter(element, newCharacter);
	}

	@Override
	protected void removeBookElementContextMenu(BookCharacter element) {
		getBook().removeCharacter(element);
	}
	
	@Override
	public void characterAdded(BookCharacter character) {
		getRoot().getChildren().add(new TreeItem<>(character));
	}

	@Override
	public void characterEdited(BookCharacter oldCharacter, BookCharacter newCharacter) {
		for(TreeItem<BookCharacter> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == oldCharacter) {
				treeItem.setValue(newCharacter);
				break;
			}
		}
	}

	@Override
	public void characterDeleted(BookCharacter character) {
		for(TreeItem<BookCharacter> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == character) {
				getRoot().getChildren().remove(treeItem);
				break;
			}
		}
	}

	@Override
	protected String getAddContextMenuString() {
		return "Ajouter un personnage";
	}

	@Override
	protected String getUpdateContextMenuString() {
		return "Modifier un personnage";
	}

	@Override
	protected String getDeleteContextMenuString() {
		return "Supprimer un personnage";
	}

}
