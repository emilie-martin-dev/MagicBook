package magic_book.core.game;

import magic_book.core.Book;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemWeapon;

/**
 * Création de la sauvegarde de la partie en cours
 */
public class BookState {

	/**
	 * Livre contenant toute les informations
	 */
	private Book book;
	
	/**
	 * Personnage principal
	 */
	private BookCharacter mainCharacter;
	
	/**
	 * Arme du personnage principale
	 */
	private BookItemWeapon bookItemArme;
	/**
	 * Item de defense du personnage principale
	 */
	private BookItemDefense bookItemDefense;

	/**
	 * Donne toutes les informations du personnage principal
	 * @return Personnage principal
	 */
	public BookCharacter getMainCharacter() {
		return mainCharacter;
	}

	/**
	 * Modifie le personnage principal
	 * @param mainCharacter Nouveau personnage principal
	 */
	public void setMainCharacter(BookCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	/**
	 * Donne le livre
	 * @return Livre
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * Modifie le livre
	 * @param book Nouveau livre
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * Donne l'arme du personnage principal
	 * @return Item arme
	 */
	public BookItemWeapon getBookItemArme() {
		return bookItemArme;
	}

	/**
	 * Modifie l'arme du personnage principal
	 * @param bookItemArme Nouvelle item arme
	 */
	public void setBookItemArme(BookItemWeapon bookItemArme) {
		this.bookItemArme = bookItemArme;
	}

	/**
	 * Donne l'item de defense du personnal principal
	 * @return Item de défense
	 */
	public BookItemDefense getBookItemDefense() {
		return bookItemDefense;
	}

	/**
	 * Modifie l'item de defense du personnal principal
	 * @param bookItemDefense Nouvelle item de défense
	 */
	public void setBookItemDefense(BookItemDefense bookItemDefense) {
		this.bookItemDefense = bookItemDefense;
	}
	
}
