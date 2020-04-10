package magic_book.core.file;

import com.google.gson.Gson;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import magic_book.core.Book;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SetupJson;
import magic_book.core.file.json.SkillJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.item.BookItem;

/**
 * Permet d'écrire le livre au format JSON, idéalement, cette classe ne devrait plus contenir les méthode "convertInto" car la classe Book devrait implémenter JsonExportable.
 */
public class BookWritter {
	
	/**
	 * Écrit le livre au format JSON
	 * @param path Chemin vers lequel enregistrer le livre
	 * @param book Le livre à enregistrer
	 * @throws IOException Exception lors de l'écriture
	 */
	public void write(String path, Book book) throws IOException {		
		BookJson bookJson = convertIntoBookJson(book);
		
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path));
		out.write(gson.toJson(bookJson).getBytes());
		out.close();
	}

	/**
	 * On convertit le livre en son équivalent JSON
	 * @param book Le livre à convertir
	 * @return 
	 */
	private BookJson convertIntoBookJson(Book book) {
		BookJson bookJson = new BookJson();
		bookJson.setPrelude(book.getTextPrelude());
		bookJson.setSetup(convertIntoSetupJson(book));
		bookJson.setSections(convertIntoSectionJson(book));
		
		return bookJson;
	}

	/**
	 * On récupère le SetupJson
	 * @param book Le livre à convertir
	 * @return le SetupJson généré
	 */
	private SetupJson convertIntoSetupJson(Book book) {
		SetupJson setup = new SetupJson();
		
		List<CharacterJson> characters = new ArrayList<>();
		List<ItemJson> items = new ArrayList<>();
		List<SkillJson> skills = new ArrayList<>();
		List<CharacterCreationJson> characterCreations = new ArrayList<>();
		
		// Ajoute les skills
		for(Entry<String, BookSkill> entry : book.getSkills().entrySet()) {
			SkillJson skillJson = entry.getValue().toJson();
			
			skills.add(skillJson);
		}
		
		// Ajoute les personnages
		for(BookCharacter character : book.getCharacters().values()) {
			CharacterJson characterJson = character.toJson();
			
			characters.add(characterJson);
		}
		
		// Ajoute les items
		for(BookItem bookItem : book.getItems().values()) {
			items.add(bookItem.toJson());
		}
		
		// Ajoute les phases de la création du personnage
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

	/**
	 * On convertit les noeuds en une map de sections
	 * @param book Le livre à convertir
	 * @return La Map qui contient la liste des SectionJson
	 */
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
