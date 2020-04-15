package magic_book.observer.book;

import magic_book.core.game.BookSkill;

/**
 * Observer pour réagir aux changements sur les skills
 */
public interface BookSkillObserver {

	/**
	 * Un skill a été ajouté
	 * @param skill Le skill ajouté
	 */
	public void skillAdded(BookSkill skill);

	/**
	 * Un skill a été mis à jour
	 * @param oldSkill L'ancien skill
	 * @param newSkill Le nouveau skill
	 */
	public void skillEdited(BookSkill oldSkill, BookSkill newSkill);

	/**
	 * Un skill a été supprimé
	 * @param skill Le skill supprimé
	 */
	public void skillDeleted(BookSkill skill);
	
}
