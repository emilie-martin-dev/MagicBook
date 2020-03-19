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
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SetupJson;
import magic_book.core.file.json.SkillJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationSkill;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.item.BookItemWithDurability;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithRandomChoices;

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
		List<SkillJson> skills = new ArrayList<>();
		List<CharacterCreationJson> characterCreations = new ArrayList<>();
		
		for(Entry<String, BookSkill> entry : book.getSkills().entrySet()) {
			SkillJson skillJson = new SkillJson();
			skillJson.setId(entry.getValue().getId());
			skillJson.setName(entry.getValue().getName());
			
			skills.add(skillJson);
		}
		
		for(BookCharacter character : book.getCharacters().values()) {
			CharacterJson characterJson = new CharacterJson();
			
			characterJson.setId(character.getId());
			characterJson.setName(character.getName());
			characterJson.setHp(character.getHp());
			characterJson.setCombatSkill(character.getBaseDamage());
			
			if(character.isDoubleDamage()) {
				characterJson.setDoubleDamage(true);
			}
			
			if(!character.getSkills().isEmpty()) {
				characterJson.setSkills(new ArrayList<>());
				for(String skill : character.getSkills()) {
					characterJson.getSkills().add(skill);
				}
			}
			
			if(!character.getImmunes().isEmpty()) {
				characterJson.setImmune(new ArrayList<>());
				for(String immune : character.getImmunes()) {
					characterJson.getImmune().add(immune);
				}
			}
			
			characters.add(characterJson);
		}
		
		for(BookItem bookItem : book.getItems().values()) {
			ItemJson itemJson = new ItemJson();
			
			itemJson.setId(bookItem.getId());
			itemJson.setName(bookItem.getName());
			itemJson.setItemType(ItemType.KEY_ITEM);
			
			if(bookItem instanceof BookItemDefense) {
				BookItemDefense bookItemDefense = (BookItemDefense) bookItem;
				itemJson.setResistance(bookItemDefense.getResistance());
				itemJson.setItemType(ItemType.DEFENSE);
			} else if(bookItem instanceof BookItemHealing) {
				BookItemHealing bookItemHealing = (BookItemHealing) bookItem;
				itemJson.setHp(bookItemHealing.getHp());
				itemJson.setItemType(ItemType.HEALING);
			} else if(bookItem instanceof BookItemMoney) {
				itemJson.setItemType(ItemType.MONEY);
			} else if(bookItem instanceof BookItemWeapon) {
				BookItemWeapon bookItemWeapon = (BookItemWeapon) bookItem;
				itemJson.setDamage(bookItemWeapon.getDamage());
				itemJson.setItemType(ItemType.WEAPON);
			} 
			
			if(bookItem instanceof BookItemWithDurability) {
				BookItemWithDurability bookItemWithDurability = (BookItemWithDurability) bookItem;
				itemJson.setDurability(bookItemWithDurability.getDurability());
			}
			
			items.add(itemJson);
		}
		
		for(AbstractCharacterCreation abstractCharacterCreation : book.getCharacterCreations()) {
			CharacterCreationJson characterCreationJson = new CharacterCreationJson();
			characterCreationJson.setText(abstractCharacterCreation.getText());
			
			if(abstractCharacterCreation instanceof CharacterCreationItem) {
				CharacterCreationItem characterCreationItem = (CharacterCreationItem) abstractCharacterCreation;
				
				characterCreationJson.setAmountToPick(characterCreationItem.getAmountToPick());
				
				characterCreationJson.setItems(new ArrayList<>());
				for(BookItemLink itemLink : characterCreationItem.getItemLinks()) {
					ItemLinkJson itemLinkJson = new ItemLinkJson();
					
					itemLinkJson.setAmount(itemLink.getAmount());
					
					if(itemLink.getAuto())
						itemLinkJson.setAuto(true);
					
					itemLinkJson.setId(itemLink.getId());
					
					if(itemLink.getPrice() != 0)
						itemLinkJson.setPrice(itemLink.getPrice());
					
					if(itemLink.getSellingPrice()!= 0)
						itemLinkJson.setSellingPrice(itemLink.getSellingPrice());
					
					characterCreationJson.getItems().add(itemLinkJson);
				}
				
				characterCreationJson.setType(TypeJson.ITEM);
			} else if(abstractCharacterCreation instanceof CharacterCreationSkill) {
				CharacterCreationSkill characterCreationSkill = (CharacterCreationSkill) abstractCharacterCreation;
				
				characterCreationJson.setAmountToPick(characterCreationSkill.getAmountToPick());
				
				characterCreationJson.setSkills(new ArrayList<>());
				for(String skillLink : characterCreationSkill.getSkillLinks()) {
					characterCreationJson.getSkills().add(skillLink);
				}
				
				characterCreationJson.setType(TypeJson.SKILL);
			}
			
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
			SectionJson sectionJson = new SectionJson();
			sectionJson.setText(node.getText());
					
			Set<BookNodeLink> choicesDone = new HashSet<>();
			
			if(node instanceof BookNodeTerminal) {
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) node;
				sectionJson.setEndType(bookNodeTerminal.getBookNodeStatus());
			} else if(node instanceof BookNodeCombat) {
				BookNodeCombat bookNodeCombat = (BookNodeCombat) node;
				CombatJson combatJson = new CombatJson();
				
				if(bookNodeCombat.getEvasionRound() != 0)
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
				
				if(abstractBookNodeWithChoices.getHp() != 0)
					sectionJson.setHp(abstractBookNodeWithChoices.getHp());
				
				if(abstractBookNodeWithChoices.isMustEat())
					sectionJson.setMustEat(true);
				
				if(!abstractBookNodeWithChoices.getItemLinks().isEmpty())
					sectionJson.setAmountToPick(abstractBookNodeWithChoices.getNbItemsAPrendre());
				
				if(!abstractBookNodeWithChoices.getShopItemLinks().isEmpty()) {
					sectionJson.setShop(new ArrayList<>());
					
					for(BookItemLink itemLink : (List<BookItemLink>) abstractBookNodeWithChoices.getShopItemLinks()) {
						ItemLinkJson itemLinkJson = new ItemLinkJson();
						
						itemLinkJson.setId(itemLink.getId());
						if(itemLink.getAuto())
							itemLinkJson.setAuto(true);
						
						if(itemLink.getAmount() != 1)
							itemLinkJson.setAmount(itemLink.getAmount());
						
						if(itemLink.getPrice() != -1)
							itemLinkJson.setPrice(itemLink.getPrice());
						
						if(itemLink.getSellingPrice() != -1)
							itemLinkJson.setSellingPrice(itemLink.getSellingPrice());
						
						sectionJson.getShop().add(itemLinkJson);
					}
				}
				
				if(!abstractBookNodeWithChoices.getItemLinks().isEmpty()) {
					sectionJson.setItems(new ArrayList<>());
					
					for(BookItemLink itemLink : (List<BookItemLink>) abstractBookNodeWithChoices.getItemLinks()) {
						ItemLinkJson itemLinkJson = new ItemLinkJson();
						
						itemLinkJson.setId(itemLink.getId());
						if(itemLink.getAuto())
							itemLinkJson.setAuto(true);
						
						if(itemLink.getAmount() != 1)
							itemLinkJson.setAmount(itemLink.getAmount());
						
						if(itemLink.getPrice() != -1)
							itemLinkJson.setPrice(itemLink.getPrice());
						
						if(itemLink.getSellingPrice() != -1)
							itemLinkJson.setSellingPrice(itemLink.getSellingPrice());
						
						sectionJson.getItems().add(itemLinkJson);
					}
				}
				
				sectionJson.setChoices(new ArrayList<>());
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
		
		//TODO requirements
		
		if(nodeLink.getAuto())
			choiceJson.setAuto(true);
		
		if(nodeLink.getGold() != 0)
			choiceJson.setGold(nodeLink.getGold());
		
		if(nodeLink.getHp() != 0)
			choiceJson.setHp(nodeLink.getHp());
		
		choiceJson.setText(nodeLink.getText());
		
		return choiceJson;
	}

}
