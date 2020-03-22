package magic_book.core.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.deserializer.BookNodeStatusDeserializer;
import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.ItemLinkJson;
import magic_book.core.file.json.ItemType;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.file.json.SkillJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.character_creation.CharacterCreationItem;
import magic_book.core.game.character_creation.CharacterCreationSkill;
import magic_book.core.game.character_creation.CharacterCreationText;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemHealing;
import magic_book.core.item.BookItemMoney;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;

public class BookReader {
	
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;
	private HashMap<Integer, AbstractBookNode> nodes;
	private HashMap<String, BookSkill> skills;
	private List<AbstractCharacterCreation> characterCreations;
	
	public Book read(String path) throws FileNotFoundException, BookFileException {		
		BookJson bookJson = readFileWithGson(path);
		
		skills = getEverySkills(bookJson);
		items = getEveryItems(bookJson);
		characters = getEveryCharacters(bookJson);
		characterCreations = getCharacterCreations(bookJson);
		nodes = getEveryNodes(bookJson);
		
		return new Book(bookJson.getPrelude(), nodes, items, characters, skills, characterCreations);
	}

	private BookJson readFileWithGson(String path) throws FileNotFoundException {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BookNodeStatus.class, new BookNodeStatusDeserializer());
		
		Gson gson = builder.create(); 
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path)); 
		
		return gson.fromJson(bufferedReader, BookJson.class);
	}

	private HashMap<String, BookSkill> getEverySkills(BookJson bookJson) {
		HashMap<String, BookSkill> skills = new HashMap<>();
		
		for(SkillJson s : bookJson.getSetup().getSkills()) {
			BookSkill skill = new BookSkill(s.getId(), s.getName());
			
			skills.put(skill.getId(), skill);
		}
			
		return skills;
	}

	private HashMap<String, BookItem> getEveryItems(BookJson bookJson) throws BookFileException {
		HashMap<String, BookItem> items = new HashMap<>();
		
		for(ItemJson i : bookJson.getSetup().getItems()) {
			BookItem item = null;
			
			if(i.getItemType() == ItemType.DEFENSE) {
				int durability = -1;
				if(i.getDurability() != null) {
					durability = i.getDurability();
				}
				
				item = new BookItemDefense(i.getId(), i.getName(), durability, i.getResistance());
			} else if(i.getItemType() == ItemType.WEAPON) {
				int durability = -1;
				if(i.getDurability() != null) {
					durability = i.getDurability();
				}
				
				item = new BookItemWeapon(i.getId(), i.getName(), durability, i.getDamage());
			} else if(i.getItemType() == ItemType.MONEY) {
				item = new BookItemMoney(i.getId(), i.getName(), 0);
			} else if(i.getItemType() == ItemType.HEALING) {
				int durability = -1;
				if(i.getDurability() != null) {
					durability = i.getDurability();
				}
				
				item = new BookItemHealing(i.getId(), i.getName(), durability, i.getHp());				
			} else if(i.getItemType() == ItemType.KEY_ITEM) {
				item = new BookItem(i.getId(), i.getName());				
			} else {
				throw new BookFileException("L'item avec le type " + i.getItemType() + " est invalide");
			}
			
			items.put(item.getId(), item);
		}
			
		return items;
	}
	
	private HashMap<String, BookCharacter> getEveryCharacters(BookJson bookJson) throws BookFileException {
		HashMap<String, BookCharacter> characters = new HashMap<>();
		
		for(CharacterJson c : bookJson.getSetup().getCharacters()) {
			BookCharacter character = new BookCharacter(c.getId(), c.getName(), c.getCombatSkill(), c.getHp(), null, null, 0);
			
			if(c.getSkills() != null) {
				for(String skill : c.getSkills()) {
					character.getSkills().add(skill);
				}
			}
			
			if(c.getImmune() != null) {
				for(String immune : c.getImmune()) {
					character.getImmunes().add(immune);
				}
			}
			
			if(c.getDoubleDamage() == null) {
				character.setDoubleDamage(false);
			} else {
				character.setDoubleDamage(c.getDoubleDamage());
			}
			
			characters.put(character.getId(), character);
		}
			
		return characters;
	}
	
	private List<AbstractCharacterCreation> getCharacterCreations(BookJson bookJson) {
		List<AbstractCharacterCreation> characterCreations = new ArrayList<>();
		
		for(CharacterCreationJson characterCreationJson : bookJson.getSetup().getCharacterCreation()) {
			AbstractCharacterCreation characterCreation = null;
			
			if(characterCreationJson.getType() == null) {
				characterCreation = new CharacterCreationText(characterCreationJson.getText());
			} else if(characterCreationJson.getType() == TypeJson.ITEM) {
				List<BookItemLink> itemLinks = new ArrayList<>();
				
				if(characterCreationJson.getItems() != null) {
					for(ItemLinkJson itemLinkJson : characterCreationJson.getItems()) {
						itemLinks.add(getBookItemLink(itemLinkJson));
					}
				}
				
				int amountToPick = characterCreationJson.getAmountToPick();
				
				characterCreation = new CharacterCreationItem(characterCreationJson.getText(), itemLinks, amountToPick);
			} else if(characterCreationJson.getType() == TypeJson.SKILL) {
				List<String> skillLinks = new ArrayList<>();
				
				if(characterCreationJson.getSkills() != null) {
					for(String skill : characterCreationJson.getSkills()) {
						skillLinks.add(skill);
					}
				}
				
				int amountToPick = characterCreationJson.getAmountToPick();
				
				characterCreation = new CharacterCreationSkill(characterCreationJson.getText(), skillLinks, amountToPick);
			}
			
			characterCreations.add(characterCreation);
		}
		
		return characterCreations;
	}
	
	private HashMap<Integer, AbstractBookNode> getEveryNodes(BookJson bookJson) throws BookFileException {
		HashMap<Integer, AbstractBookNode> nodes = new HashMap<>();
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			AbstractBookNode node = null;
			
			if(sectionJson.getCombat() != null) {
				node = createBookNodeCombat(sectionJson);
			} else if(sectionJson.isRandomPick() != null && sectionJson.isRandomPick()) {
				node = createBookNodeWithRandomChoices(sectionJson);
			} else if(sectionJson.getEndType() != null) {
				node = new BookNodeTerminal(sectionJson.getText(), sectionJson.getEndType());
			} else if(sectionJson.getChoices() != null && !sectionJson.getChoices().isEmpty()){
				node = createBookNodeWithChoices(sectionJson);
			} else {
				throw new BookFileException("Le noeud numéro " + entry.getKey() + " est invalide");
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
		int evasionRound = 0;
		if(sectionJson.getCombat().getEvasionRound() != null)
			evasionRound = sectionJson.getCombat().getEvasionRound();
		
		BookNodeCombat bookNodeCombat = new BookNodeCombat(sectionJson.getText(), null, null, null, evasionRound, null);	
		bookNodeCombat = (BookNodeCombat) fillCommunNodeValues(sectionJson, bookNodeCombat);
		
		for(String ennemie : sectionJson.getCombat().getEnemies()) {
			bookNodeCombat.addEnnemieId(ennemie);
		}
		
		return bookNodeCombat;
	}
	
	private AbstractBookNodeWithChoices fillCommunNodeValues(SectionJson section, AbstractBookNodeWithChoices node) {
		if(section.getAmountToPick() != null)
			node.setNbItemsAPrendre(section.getAmountToPick());
		else
			node.setNbItemsAPrendre(-1);
		
		if(section.getMustEat() == null) 
			node.setMustEat(false);
		else 
			node.setMustEat(section.getMustEat());
		
		if(section.getHp()== null) 
			node.setHp(0);
		else 
			node.setHp(section.getHp());
		
		if(section.getItems() != null) {
			for(ItemLinkJson itemLinkJson : section.getItems()) {
				BookItemLink bookItemsLink = getBookItemLink(itemLinkJson);

				node.addItemLink(bookItemsLink);
			}
		}
		
		if(section.getShop() != null) {
			for(ItemLinkJson itemLinkJson : section.getShop()) {
				BookItemLink bookItemsLink = getBookItemLink(itemLinkJson);

				node.addShopItemLink(bookItemsLink);
			}
		}
		
		return node;
	}
	
	private HashMap<Integer, AbstractBookNode> linkEveryNodes(BookJson bookJson, HashMap<Integer, AbstractBookNode> nodes) {
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			AbstractBookNode node = nodes.get(entry.getKey());
			
			if(sectionJson.getChoices() != null) {
				for(ChoiceJson choiceJson : sectionJson.getChoices()) {			
					AbstractBookNodeWithChoices nodeWithChoices = (AbstractBookNodeWithChoices) node;
					BookNodeLink nodeLink = createBookNodeLink(nodes, choiceJson);
					
					nodeWithChoices.getChoices().add(nodeLink);
				}
			}
			
			if(sectionJson.getCombat() != null) {
				CombatJson combatJson = sectionJson.getCombat();
				BookNodeCombat nodeCombat = (BookNodeCombat) node;
				
				if(combatJson.getWin() != null)
					nodeCombat.setWinBookNodeLink(createBookNodeLink(nodes, combatJson.getWin()));
			
				if(combatJson.getLoose() != null)
					nodeCombat.setLooseBookNodeLink(createBookNodeLink(nodes, combatJson.getLoose()));
			
				if(combatJson.getEvasion() != null)
					nodeCombat.setEvasionBookNodeLink(createBookNodeLink(nodes, combatJson.getEvasion()));
			}
		}
		
		return nodes;
	}
	
	public BookNodeLink createBookNodeLink(HashMap<Integer, AbstractBookNode> nodes, ChoiceJson choiceJson) {
		BookNodeLink bookNodeLink = null;
		
		if(choiceJson.getWeight() != null) {
			BookNodeLinkRandom bookNodeLinkRandom = new BookNodeLinkRandom();
			bookNodeLinkRandom.setChance(choiceJson.getWeight());
			
			bookNodeLink = bookNodeLinkRandom;
		} else {
			bookNodeLink = new BookNodeLink();
		}
		
		if(choiceJson.getAuto() == null)
			bookNodeLink.setAuto(false);
		else
			bookNodeLink.setAuto(choiceJson.getAuto());
		
		if(choiceJson.getGold() == null)
			bookNodeLink.setGold(0);
		else
			bookNodeLink.setGold(choiceJson.getGold());
		
		if(choiceJson.getHp() == null)
			bookNodeLink.setHp(0);
		else
			bookNodeLink.setHp(choiceJson.getHp());
		
		bookNodeLink.setDestination(nodes.get(choiceJson.getSection()));
		bookNodeLink.setText(choiceJson.getText());
		
		if(choiceJson.getRequirements() != null) {
			List<List<AbstractRequirement>> requirements = new ArrayList<>();
			for(List<RequirementJson> requirementsJson : choiceJson.getRequirements()) {
				List<AbstractRequirement> subrequirements = new ArrayList<>();
				for(RequirementJson requirementJson : requirementsJson) {
					AbstractRequirement abstractRequirement = null;
					
					if(requirementJson.getType() == TypeJson.ITEM) {
						abstractRequirement = new RequirementItem(requirementJson.getId());
					} else if (requirementJson.getType() == TypeJson.SKILL) {
						abstractRequirement = new RequirementSkill(requirementJson.getId());						
					} else if (requirementJson.getType() == TypeJson.MONEY) {
						abstractRequirement = new RequirementMoney(requirementJson.getId(), requirementJson.getAmount());						
					}
					
					subrequirements.add(abstractRequirement);
				}
				
				requirements.add(subrequirements);
			}
		
			bookNodeLink.setRequirements(requirements);
		}
		
		return bookNodeLink;		
	}

	private BookItemLink getBookItemLink(ItemLinkJson itemLinkJson) {
		int amount = 1;
		if(itemLinkJson.getAmount() != null) {
			amount = itemLinkJson.getAmount();
		}
		
		int price = -1;
		if(itemLinkJson.getPrice()!= null) {
			price = itemLinkJson.getPrice();
		}
		
		boolean isAuto = false;
		if(itemLinkJson.isAuto()!= null) {
			isAuto = itemLinkJson.isAuto();
		}
		
		int sellingPrice = -1;		
		if(itemLinkJson.getSellingPrice() != null) {
			sellingPrice = itemLinkJson.getSellingPrice();
		}
		
		return new BookItemLink(itemLinkJson.getId(), amount, price, isAuto, sellingPrice);
	}

}
