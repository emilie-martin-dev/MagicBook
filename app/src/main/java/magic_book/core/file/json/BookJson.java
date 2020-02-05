package magic_book.core.file.json;

import java.util.List;

public class BookJson {

	private String prompt;
	private List<String> intro_sequence;
	private SetupJson setup;
	private List<List<String>> synonymes;
	private List<SectionJson> sections;

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public List<String> getIntro_sequence() {
		return intro_sequence;
	}

	public void setIntro_sequence(List<String> intro_sequence) {
		this.intro_sequence = intro_sequence;
	}

	public SetupJson getSetup() {
		return setup;
	}

	public void setSetup(SetupJson setup) {
		this.setup = setup;
	}

	public List<List<String>> getSynonymes() {
		return synonymes;
	}

	public void setSynonymes(List<List<String>> synonymes) {
		this.synonymes = synonymes;
	}

	public List<SectionJson> getSections() {
		return sections;
	}

	public void setSections(List<SectionJson> sections) {
		this.sections = sections;
	}

}
