package magic_book.observer.book;

import magic_book.core.game.BookSkill;
import magic_book.observer.Observable;

/**
 * Permet de notifier d'un changement sur les skills du livre
 */
public class BookSkillObservable extends Observable<BookSkillObserver> {

	/**
	 * Notifie qu'un skill a été ajouté
	 * @param skill Le skill ajouté
	 */
	public void notifySkillAdded(BookSkill skill) {
		for (BookSkillObserver skillObserver : getObservers()) {
			skillObserver.skillAdded(skill);
		}
	}

	/**
	 * Notifie qu'un skill a été supprimé
	 * @param skill Le skill supprimé
	 */
	public void notifySkillDeleted(BookSkill skill) {
		for (BookSkillObserver skillObserver : getObservers()) {
			skillObserver.skillDeleted(skill);
		}
	}

	/**
	 * Notifie qu'un skill a été mis à jour
	 * @param oldSkill L'ancien skill
	 * @param newSkill Le nouveau skill
	 */
	public void notifySkillEdited(BookSkill oldSkill, BookSkill newSkill) {
		for (BookSkillObserver skillObserver : getObservers()) {
			skillObserver.skillEdited(oldSkill, newSkill);
		}
	}

}
