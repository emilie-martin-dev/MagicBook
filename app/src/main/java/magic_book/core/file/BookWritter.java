package magic_book.core.file;

import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import magic_book.core.Book;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SetupJson;
import magic_book.core.file.json.SkillJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.item.BookItem;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node_link.BookNodeLink;

public class BookWritter {
	
	public void write(String path, Book book) throws IOException {		
		BookJson bookJson = convertIntoBookJson(book);
		
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
		out.write(gson.toJson(bookJson).getBytes());
		out.close();
	}

	private BookJson convertIntoBookJson(Book book) {
		BookJson bookJson = new BookJson();
		bookJson.setPrelude(book.getTextPrelude());
		bookJson.setSetup(convertIntoSetupJson(book));
		bookJson.setSections(convertIntoSectionJson(book));
		
		return bookJson;
	}

	private SetupJson convertIntoSetupJson(Book book) {
		SetupJson setup = new SetupJson();
		
		List<CharacterJson> characters = new ArrayList<>();
		List<ItemJson> items = new ArrayList<>();
		List<SkillJson> skills = new ArrayList<>();
		List<CharacterCreationJson> characterCreations = new ArrayList<>();
		
		for(Entry<String, BookSkill> entry : book.getSkills().entrySet()) {
			SkillJson skillJson = entry.getValue().toJson();
			
			skills.add(skillJson);
		}
		
		for(BookCharacter character : book.getCharacters().values()) {
			CharacterJson characterJson = character.toJson();
			
			characters.add(characterJson);
		}
		
		for(BookItem bookItem : book.getItems().values()) {
			items.add(bookItem.toJson());
		}
		
		for(AbstractCharacterCreation abstractCharacterCreation : book.getCharacterCreations()) {
			CharacterCreationJson characterCreationJson = abstractCharacterCreation.toJson();
			characterCreations.add(characterCreationJson);
		}
		
		setup.setCharacters(characters);
		setup.setItems(items);
		setup.setSkills(skills);
		setup.setCharacterCreation(characterCreations);
				
		return setup;
	}

	private Map<Integer, SectionJson> convertIntoSectionJson(Book book) {
		HashMap<Integer, SectionJson> sections = new HashMap<>();

		for(Map.Entry<Integer, AbstractBookNode> entry : book.getNodes().entrySet()) {
			AbstractBookNode node = entry.getValue();
			SectionJson sectionJson = node.toJson();
			
			sections.put(entry.getKey(), sectionJson);
		}
		
		return sections;
	}

}
