package magic_book.core;

import java.util.ArrayList;
import magic_book.core.game.BookCharacter;
import java.util.HashMap;
import java.util.List;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.item.BookItem;
import magic_book.core.node.AbstractBookNode;


public class Book {
	
	private String textPrelude;
	private HashMap<Integer, AbstractBookNode> nodes;
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;
	private HashMap<String, BookSkill> skills;
	private List<AbstractCharacterCreation> characterCreations;

	public Book() {
		this("", null, null, null, null, null);
	}
	
	public Book(String textPrelude, HashMap<Integer, AbstractBookNode> nodes, HashMap<String, BookItem> items, HashMap<String, BookCharacter> characters, HashMap<String, BookSkill> skills, List<AbstractCharacterCreation> characterCreations) {
 		this.textPrelude = textPrelude;
		this.nodes = nodes;
		this.items = items;
		this.characters = characters;
		this.skills = skills;
		this.characterCreations = characterCreations;
		
		if(this.nodes == null)
			this.nodes = new HashMap<>();
		
		if(this.items == null)
			this.items = new HashMap<>();
		
		if(this.characters == null)
			this.characters = new HashMap<>();
		
		if(this.skills == null)
			this.skills = new HashMap<>();
		
		if(this.characterCreations == null)
			this.characterCreations = new ArrayList<>();
	}

	public String getTextPrelude() {
		return textPrelude;
	}

	public void setTextPrelude(String textPrelude) {
		this.textPrelude = textPrelude;
	}
	
	public HashMap<Integer, AbstractBookNode> getNodes() {
		return nodes;
	}

	public AbstractBookNode getRootNode() {
		return nodes.get(1);
	}
	
	public void setNodes(HashMap<Integer, AbstractBookNode> nodes) {
		this.nodes = nodes;
	}

	public HashMap<String, BookItem> getItems() {
		return items;
	}

	public void setItems(HashMap<String, BookItem> items) {
		this.items = items;
	}

	public HashMap<String, BookCharacter> getCharacters() {
		return characters;
	}

	public void setCharacters(HashMap<String, BookCharacter> characters) {
		this.characters = characters;
	}

	public HashMap<String, BookSkill> getSkills() {
		return skills;
	}

	public void setSkills(HashMap<String, BookSkill> skills) {
		this.skills = skills;
	}

	public List<AbstractCharacterCreation> getCharacterCreations() {
		return characterCreations;
	}

	public void setCharacterCreations(List<AbstractCharacterCreation> characterCreations) {
		this.characterCreations = characterCreations;
	}

}
