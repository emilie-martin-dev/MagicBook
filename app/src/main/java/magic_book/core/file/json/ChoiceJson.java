package magic_book.core.file.json;

import java.util.List;

public class ChoiceJson {

	private String text;
	private int section;
	private List<Integer> range;

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

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

}
