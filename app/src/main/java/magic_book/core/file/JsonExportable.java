package magic_book.core.file;

public interface JsonExportable<T> {

	public T toJson();
	
	public void fromJson(T json);
	
}
