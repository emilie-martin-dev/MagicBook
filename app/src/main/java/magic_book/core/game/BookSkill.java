package magic_book.core.game;

import magic_book.core.Book;
import magic_book.core.file.json.SkillJson;
import magic_book.core.file.JsonExportable;
import magic_book.core.parser.Descriptible;

public class BookSkill implements Descriptible, JsonExportable<SkillJson> {
	
	private String id;
	private String name;

	public BookSkill() {
		this("", "");
	}
	
	public BookSkill(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@Override
	public SkillJson toJson() {
		SkillJson skillJson = new SkillJson();
		
		skillJson.setId(id);
		skillJson.setName(name);
		
		return skillJson;
	}

	@Override
	public void fromJson(SkillJson json) {
		this.id = json.getId();
		this.name = json.getName();
	}
	

	@Override
	public String getDescription(Book book) {
		return this.name+"\n";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
