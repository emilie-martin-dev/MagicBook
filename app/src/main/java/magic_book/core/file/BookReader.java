package magic_book.core.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import magic_book.core.Book;
import magic_book.core.file.deserializer.BookNodeStatusDeserializer;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;
import magic_book.core.node.AbstractBookNode;
import magic_book.core.node.AbstractBookNodeWithChoices;
import magic_book.core.node.BookNodeCombat;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeLinkRandom;
import magic_book.core.node.BookNodeStatus;
import magic_book.core.node.BookNodeTerminal;
import magic_book.core.node.BookNodeWithChoices;
import magic_book.core.node.BookNodeWithRandomChoices;

public class BookReader {
	
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;
	private HashMap<Integer, AbstractBookNode> nodes;
	
	public Book read(String path) throws FileNotFoundException, IOException {		
		BookJson bookJson = readFileWithGson(path);
		
		items = getEveryItems(bookJson);
		characters = getEveryCharacters(bookJson);
		nodes = getEveryNodes(bookJson);
		
		return new Book(nodes, items, characters);
	}

	private BookJson readFileWithGson(String path) throws FileNotFoundException {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BookNodeStatus.class, new BookNodeStatusDeserializer());
		
		Gson gson = builder.create(); 
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path)); 
		
		return gson.fromJson(bufferedReader, BookJson.class);
	}

	private HashMap<String, BookItem> getEveryItems(BookJson bookJson) {
		HashMap<String, BookItem> items = new HashMap<>();
		
		for(ItemJson i : bookJson.getSetup().getItems()) {
			BookItem item = new BookItem(i.getId(), i.getName());
			items.put(item.getId(), item);
		}
			
		return items;
	}
	
	private HashMap<String, BookCharacter> getEveryCharacters(BookJson bookJson) {
		HashMap<String, BookCharacter> characters = new HashMap<>();
		
		for(CharacterJson c : bookJson.getSetup().getCharacters()) {
			BookCharacter character = new BookCharacter(c.getId(), c.getName(), c.getCombatSkill(), c.getHp(), null, null, 8);
			characters.put(character.getId(), character);
		}
			
		return characters;
	}
	
	private HashMap<Integer, AbstractBookNode> getEveryNodes(BookJson bookJson) throws IOException {
		HashMap<Integer, AbstractBookNode> nodes = new HashMap<>();
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			AbstractBookNode node = null;
			
			if(sectionJson.getCombat() != null) {
				node = createBookNodeCombat(sectionJson);
			} else if(sectionJson.isRandomPick()) {
				node = createBookNodeWithRandomChoices(sectionJson);
			} else if(sectionJson.getChoices() != null && sectionJson.getChoices().isEmpty()) {
				node = new BookNodeTerminal(sectionJson.getText(), sectionJson.getType());
			} else if(sectionJson.getChoices() != null && !sectionJson.getChoices().isEmpty()){
				node = createBookNodeWithChoices(sectionJson);
			} else {
				throw new IOException("Noeuds invalide");
			}
			
			nodes.put(entry.getKey(), node);
		}
		
		return linkEveryNodes(bookJson, nodes);
	}
	
	private BookNodeWithRandomChoices createBookNodeWithRandomChoices(SectionJson sectionJson) {
		BookNodeWithRandomChoices bookNodeWithRandomChoices = new BookNodeWithRandomChoices(sectionJson.getText());
		bookNodeWithRandomChoices = (BookNodeWithRandomChoices) fillCommunNodeValues(sectionJson, bookNodeWithRandomChoices);
		
		return bookNodeWithRandomChoices;
	}
	
	private BookNodeWithChoices createBookNodeWithChoices(SectionJson sectionJson) {
		BookNodeWithChoices bookNodeWithChoices = new BookNodeWithChoices(sectionJson.getText());	
		bookNodeWithChoices = (BookNodeWithChoices) fillCommunNodeValues(sectionJson, bookNodeWithChoices);
		
		return bookNodeWithChoices;
	}
	
	private BookNodeCombat createBookNodeCombat(SectionJson sectionJson) {
		BookNodeCombat bookNodeCombat = new BookNodeCombat(sectionJson.getText(), null, null, null, sectionJson.getCombat().getEvasionRound(), null);	
		bookNodeCombat = (BookNodeCombat) fillCommunNodeValues(sectionJson, bookNodeCombat);
		//TODO : Gestion des ennemis
		
		return bookNodeCombat;
	}
	
	private AbstractBookNodeWithChoices fillCommunNodeValues(SectionJson section, AbstractBookNodeWithChoices node) {
		node.setNbItemsAPrendre(section.getAmountToPick());
		// TODO : Gestion des items et shop
		
		return node;
	}
	
	private HashMap<Integer, AbstractBookNode> linkEveryNodes(BookJson bookJson, HashMap<Integer, AbstractBookNode> nodes) {
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			AbstractBookNode node = nodes.get(entry.getKey());
			
			if(sectionJson.getChoices() != null) {
				for(ChoiceJson choiceJson : sectionJson.getChoices()) {			
					AbstractBookNodeWithChoices nodeWithChoices = (AbstractBookNodeWithChoices) node;
					BookNodeLink nodeLink = null;

					if(choiceJson.getWeight() != 0) {
						nodeLink = new BookNodeLinkRandom(choiceJson.getText(), nodes.get(choiceJson.getSection()), choiceJson.getWeight());
					} else {
						nodeLink = new BookNodeLink(choiceJson.getText(), nodes.get(choiceJson.getSection()));
					}

					nodeWithChoices.getChoices().add(nodeLink);
				}
			}
			
			if(sectionJson.getCombat() != null) {
				CombatJson combatJson = sectionJson.getCombat();
				BookNodeCombat nodeCombat = (BookNodeCombat) node;
				
				if(combatJson.getWin() != null)
					nodeCombat.setWinBookNodeLink(new BookNodeLink(combatJson.getWin().getText(), nodes.get(combatJson.getWin().getSection())));
			
				if(combatJson.getLoose()!= null)
					nodeCombat.setLooseBookNodeLink(new BookNodeLink(combatJson.getLoose().getText(), nodes.get(combatJson.getLoose().getSection())));
			
				if(combatJson.getEvasion()!= null)
					nodeCombat.setLooseBookNodeLink(new BookNodeLink(combatJson.getEvasion().getText(), nodes.get(combatJson.getEvasion().getSection())));
			}
		}
		
		return nodes;
	}

	
}
