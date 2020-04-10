package magic_book.core.file;

/**
 * Permet l'export de la classe en JSON
 * @param <T> La classe équivalente en JSON
 */
public interface JsonExportable<T> {

	/**
	 * Transforme la classe en son équivalent JSON
	 * @return La classe équivalente en JSON
	 */
	public T toJson();
	
	/**
	 * Permet de changer les attributs de la classe en fonction de son équivalent JSON
	 * @param json La classe équivalente en JSON
	 */
	public void fromJson(T json);
	
}
