package magic_book.core.file.json;

import java.util.List;

public class ChoiceJson {

	private String text;
	private SectionJson section;
	private List<String> words;
	private List<Integer> range;

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Integer> getRange() {
		return range;
	}

	public void setRange(List<Integer> range) {
		this.range = range;
	}

	public SectionJson getSection() {
		return section;
	}

	public void setSection(SectionJson section) {
		this.section = section;
	}

}
