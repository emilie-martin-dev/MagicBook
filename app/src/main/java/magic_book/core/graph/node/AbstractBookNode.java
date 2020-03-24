package magic_book.core.graph.node;

import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.SectionJson;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.parser.Descriptible;
import magic_book.core.parser.TextParser;

public abstract class AbstractBookNode implements Descriptible, JsonExportable<SectionJson> {
	
	private String text;
	
	public AbstractBookNode(String text){
		this.text = text;
	}
	
	public abstract <T extends BookNodeLink> List<T> getChoices();

	public String getDescription(Book book) {
		return TextParser.parseText(text, book.getItems(), book.getCharacters())+"\n";
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = new SectionJson();
		
		sectionJson.setText(text);
		
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		setText(json.getText());
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
