package magic_book.core.game;

import magic_book.core.Book;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemWeapon;

public class BookState {

	private Book book;
	
	private BookCharacter mainCharacter;
	
	private BookItemWeapon bookItemArme;
	private BookItemDefense bookItemDefense;

	public BookCharacter getMainCharacter() {
		return mainCharacter;
	}

	public void setMainCharacter(BookCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public BookItemWeapon getBookItemArme() {
		return bookItemArme;
	}

	public void setBookItemArme(BookItemWeapon bookItemArme) {
		this.bookItemArme = bookItemArme;
	}

	public BookItemDefense getBookItemDefense() {
		return bookItemDefense;
	}

	public void setBookItemDefense(BookItemDefense bookItemDefense) {
		this.bookItemDefense = bookItemDefense;
	}
	
}
