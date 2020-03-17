package magic_book.core.file.json;

import java.util.Map;

public class BookJson {
	
	private String prelude;
	private SetupJson setup;
	private Map<Integer, SectionJson> sections;

	public String getPrelude() {
		return prelude;
	}

	public void setPrelude(String prelude) {
		this.prelude = prelude;
	}

	public SetupJson getSetup() {
		return setup;
	}

	public void setSetup(SetupJson setup) {
		this.setup = setup;
	}

	public Map<Integer, SectionJson> getSections() {
		return sections;
	}

	public void setSections(Map<Integer, SectionJson> sections) {
		this.sections = sections;
	}

}
