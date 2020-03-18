package magic_book.core.file;

import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import magic_book.core.Book;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SetupJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;
import magic_book.core.node.AbstractBookNode;
import magic_book.core.node.AbstractBookNodeWithChoices;
import magic_book.core.node.BookNodeCombat;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeLinkRandom;
import magic_book.core.node.BookNodeTerminal;
import magic_book.core.node.BookNodeWithRandomChoices;

public class BookWritter {
	
	public void write(String path, Book book) throws FileNotFoundException {		
		BookJson bookJson = convertIntoBookJson(book);
		
		Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(bookJson));
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
		
		for(BookCharacter character : book.getCharacters().values()) {
			CharacterJson characterJson = new CharacterJson();
			
			characterJson.setId(character.getId());
			characterJson.setName(character.getName());
			characterJson.setHp(character.getHp());
			characterJson.setCombatSkill(character.getBaseDamage());
			
			characters.add(characterJson);
		}
		
		for(BookItem bookItem : book.getItems().values()) {
			ItemJson itemJson = new ItemJson();
			
			itemJson.setId(bookItem.getId());
			itemJson.setName(bookItem.getName());
			
			items.add(itemJson);
		}
		
		setup.setCharacters(characters);
		setup.setItems(items);
				
		return setup;
	}

	private Map<Integer, SectionJson> convertIntoSectionJson(Book book) {
		HashMap<Integer, SectionJson> sections = new HashMap<>();
		
		for(Map.Entry<Integer, AbstractBookNode> entry : book.getNodes().entrySet()) {
			AbstractBookNode node = entry.getValue();
			SectionJson sectionJson = new SectionJson();
			sectionJson.setText(node.getText());
			sectionJson.setChoices(new ArrayList<>());
					
			Set<BookNodeLink> choicesDone = new HashSet<>();
			
			if(node instanceof BookNodeTerminal) {
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) node;
				sectionJson.setEndType(bookNodeTerminal.getBookNodeStatus());
			} else if(node instanceof BookNodeCombat) {
				BookNodeCombat bookNodeCombat = (BookNodeCombat) node;
				CombatJson combatJson = new CombatJson();
				
				combatJson.setEvasionRound(bookNodeCombat.getEvasionRound());
				combatJson.setEnemies(bookNodeCombat.getEnnemiesId());
				
				if(bookNodeCombat.getWinBookNodeLink() != null) {
					ChoiceJson winChoiceJson = convertIntoChoiceJson(book, bookNodeCombat.getWinBookNodeLink());
					combatJson.setWin(winChoiceJson);
					choicesDone.add(bookNodeCombat.getWinBookNodeLink());
				}
				
				if(bookNodeCombat.getLooseBookNodeLink()!= null) {
					ChoiceJson looseChoiceJson = convertIntoChoiceJson(book, bookNodeCombat.getLooseBookNodeLink());
					combatJson.setLoose(looseChoiceJson);
					choicesDone.add(bookNodeCombat.getLooseBookNodeLink());
				}
				
				if(bookNodeCombat.getEvasionBookNodeLink()!= null) {
					ChoiceJson evasionChoiceJson = convertIntoChoiceJson(book, bookNodeCombat.getEvasionBookNodeLink());
					combatJson.setEvasion(evasionChoiceJson);
					choicesDone.add(bookNodeCombat.getEvasionBookNodeLink());
				}
				
				sectionJson.setCombat(combatJson);
			} else if(node instanceof BookNodeWithRandomChoices) {
				sectionJson.setIsRandomPick(true);
			}
			
			if(node instanceof AbstractBookNodeWithChoices) {
				AbstractBookNodeWithChoices abstractBookNodeWithChoices = (AbstractBookNodeWithChoices) node;
				
				sectionJson.setAmountToPick(abstractBookNodeWithChoices.getNbItemsAPrendre());
				
				if(!abstractBookNodeWithChoices.getShopItemLinks().isEmpty()) {
					
				}
				
				if(!abstractBookNodeWithChoices.getItemLinks().isEmpty()) {
					
				}
				
				for(BookNodeLink nodeLink : (List<BookNodeLink>) abstractBookNodeWithChoices.getChoices()) {
					if(choicesDone.contains(nodeLink)) {
						continue;
					}
					
					ChoiceJson choiceJson = convertIntoChoiceJson(book, nodeLink);
					
					sectionJson.getChoices().add(choiceJson);
				}
			}
			
			sections.put(entry.getKey(), sectionJson);
		}
		
		return sections;
	}
	
	private ChoiceJson convertIntoChoiceJson(Book book, BookNodeLink nodeLink) {
		ChoiceJson choiceJson = new ChoiceJson();
		
		for(Entry<Integer, AbstractBookNode> entry : book.getNodes().entrySet()) {
			if(entry.getValue() == nodeLink.getDestination()) {
				choiceJson.setSection(entry.getKey());
				break;
			}
		}
		
		if(nodeLink instanceof BookNodeLinkRandom) {
			BookNodeLinkRandom nodeLinkRandom = (BookNodeLinkRandom) nodeLink;
			choiceJson.setWeight(nodeLinkRandom.getChance());
		}
		
		choiceJson.setText(nodeLink.getText());
		
		return choiceJson;
	}

}
