package magic_book.window.component.booktreeview;

import java.util.Map;
import javafx.scene.control.TreeItem;
import magic_book.core.Book;
import magic_book.core.game.BookSkill;
import magic_book.observer.book.BookSkillObserver;
import magic_book.window.dialog.SkillDialog;

public class BookTreeViewSkill extends AbstractBookTreeView<BookSkill> implements BookSkillObserver {

	public BookTreeViewSkill(Book book) {
		super(book);
	}

	@Override
	protected void bookChanged(Book oldBook, Book newBook) {
		if(oldBook != null) {
			oldBook.removeSkillObserver(this);
		}
		
		newBook.addSkillObserver(this);
		
		for(Map.Entry<String, BookSkill> entry : newBook.getSkills().entrySet()) {
			skillAdded(entry.getValue());
		}
	}

	@Override
	protected BookSkill createRootElement() {
		return new BookSkill("", "Compétences");
	}

	@Override
	protected void addBookElementContextMenu() {
		SkillDialog skillDialog = new SkillDialog(getBook());
				
		if(skillDialog.isValid()) {
			getBook().addSkill(skillDialog.getSkill());
		}
	}

	@Override
	protected void updateBookElementContextMenu(BookSkill element) {
		SkillDialog skillDialog = new SkillDialog(element, getBook());

		if(!skillDialog.isValid()){
			return;
		}

		getBook().updateSkill(element, skillDialog.getSkill());
		
	}

	@Override
	protected void removeBookElementContextMenu(BookSkill element) {
		getBook().removeSkill(element);
	}
	
	@Override
	public void skillAdded(BookSkill skill) {
		getRoot().getChildren().add(new TreeItem<>(skill));	
	}

	@Override
	public void skillEdited(BookSkill oldSkill, BookSkill newSkill) {
		for(TreeItem<BookSkill> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == oldSkill) {
				treeItem.setValue(newSkill);
				break;
			}
		}
	}

	@Override
	public void skillDeleted(BookSkill skill) {
		for(TreeItem<BookSkill> treeItem : getRoot().getChildren()) {
			if(treeItem.getValue() == skill) {
				getRoot().getChildren().remove(treeItem);
				break;
			}
		}
	}

	@Override
	protected String getAddContextMenuString() {
		return "Ajouter une compétence";
	}

	@Override
	protected String getUpdateContextMenuString() {
		return "Modifier une compétence";
	}

	@Override
	protected String getDeleteContextMenuString() {
		return "Supprimer une compétence";
	}

}
