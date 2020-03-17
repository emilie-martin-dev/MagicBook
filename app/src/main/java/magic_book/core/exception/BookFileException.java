package magic_book.core.exception;


public class BookFileException extends Exception {

	public BookFileException() {
		this("Le fichier est inccorect");
	}

	public BookFileException(String string) {
		super(string);
	}

	public BookFileException(String string, Throwable thrwbl) {
		super(string, thrwbl);
	}

	public BookFileException(Throwable thrwbl) {
		super(thrwbl);
	}

	
	
}
