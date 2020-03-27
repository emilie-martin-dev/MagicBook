package magic_book.core;

import java.util.ArrayList;
import magic_book.core.game.BookCharacter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.item.BookItem;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.observer.book.BookNodeObservable;
import magic_book.observer.book.BookNodeObserver;

public class Book {
	
	private String textPrelude;
	private HashMap<Integer, AbstractBookNode> nodes;
	private HashMap<String, BookItem> items;
	private HashMap<String, BookCharacter> characters;
	private HashMap<String, BookSkill> skills;
	private List<AbstractCharacterCreation> characterCreations;
	
	private BookNodeObservable bookNodeObservable;

	private List<Integer> missingIndexes;
	private HashMap<AbstractBookNode, Integer> nodesInv;
	
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
		
		this.missingIndexes = new ArrayList<>();
		this.nodesInv = new HashMap<>();
		
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
		
		for(Map.Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()){
			nodesInv.put(entry.getValue(), entry.getKey());
		}
		
		
		this.bookNodeObservable = new BookNodeObservable();
	}	

	public void addNode(AbstractBookNode node) {
		if(this.missingIndexes.isEmpty()) {
			int offset = (this.nodes.containsKey(1)) ? 1 : 2;
			this.nodes.put(this.nodes.size()+offset, node);
			this.nodesInv.put(node, this.nodesInv.size()+offset);
		} else {
			this.nodes.put(missingIndexes.get(0), node);
			this.nodesInv.put(node, missingIndexes.get(0));
			this.missingIndexes.remove(0);
		}
		
		bookNodeObservable.notifyNodeAdded(node);
	}
	
	public void changeFirstNode(AbstractBookNode node) {
		if(!this.nodes.containsValue(node)) {
			addNode(node);
			changeFirstNode(node);
		} 
		
		int indexOfNode = this.nodesInv.get(node);
		AbstractBookNode oldNode = this.nodes.get(1);
		this.nodes.put(1, node);
		this.nodesInv.put(node, 1);
		if(oldNode != null) {
			this.nodes.put(indexOfNode, oldNode);
			this.nodesInv.put(oldNode, indexOfNode);
		} else {
			this.missingIndexes.add(indexOfNode);
			this.nodes.remove(indexOfNode);
		}
	}
	
	public void updateNode(AbstractBookNode oldNode, AbstractBookNode newNode) {
		Integer indexOfNode = this.nodesInv.get(oldNode);
		if(indexOfNode != null) {
			this.nodes.put(indexOfNode, newNode);
			this.nodesInv.put(newNode, indexOfNode);
			
			bookNodeObservable.notifyNodeEdited(oldNode, newNode);
		} else {
			addNode(newNode);
		}
	}
	
	public void removeNode(AbstractBookNode node) {
		Integer indexOfNode = this.nodesInv.get(node);
		if(indexOfNode != null) {
			this.missingIndexes.add(indexOfNode);
			this.nodes.remove(indexOfNode);
			this.nodesInv.remove(node);
			
			bookNodeObservable.notifyNodeDeleted(node);
			
			HashMap<AbstractBookNodeWithChoices, BookNodeLink> postRemove = new HashMap<>();
			for(Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()) {
				if(!(entry.getValue() instanceof AbstractBookNodeWithChoices))
					continue;
			
				AbstractBookNodeWithChoices currentChoice = (AbstractBookNodeWithChoices) entry.getValue();
				for(BookNodeLink nodeLink : entry.getValue().getChoices()) {
					if(nodeLink.getDestination() == indexOfNode) {
						postRemove.put(currentChoice, nodeLink);
						break;
					}
				}
			}
			
			for(Entry<AbstractBookNodeWithChoices, BookNodeLink> entry : postRemove.entrySet()) {
				entry.getKey().removeChoice(entry.getValue());
			}
		}
	}
	
	public void addNodeLink(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
		node.addChoices(nodeLink);
	}
	
	public void updateNodeLink(BookNodeLink oldBookNodeLink, BookNodeLink newBookNode) {
		List<AbstractBookNodeWithChoices> postUpdate = new ArrayList<>();
		
		for(Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()) {
			if(!(entry.getValue() instanceof AbstractBookNodeWithChoices))
				continue;
			
			AbstractBookNodeWithChoices currentChoice = (AbstractBookNodeWithChoices) entry.getValue();
			for(BookNodeLink nl : entry.getValue().getChoices()) {
				if(nl == oldBookNodeLink) {
					postUpdate.add(currentChoice);
					break;
				}
			}
		}
		
		for(AbstractBookNodeWithChoices node : postUpdate) {
			node.removeChoice(oldBookNodeLink);
			node.addChoice(newBookNode);
		}
	}
	
	public void removeNodeLink(BookNodeLink nodeLink) {
		List<AbstractBookNodeWithChoices> postRemove = new ArrayList<>();
		
		for(Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()) {
			if(!(entry.getValue() instanceof AbstractBookNodeWithChoices))
				continue;
			
			AbstractBookNodeWithChoices currentChoice = (AbstractBookNodeWithChoices) entry.getValue();
			for(BookNodeLink nl : entry.getValue().getChoices()) {
				if(nl == nodeLink) {
					postRemove.add(currentChoice);
					break;
				}
			}
		}
		
		for(AbstractBookNodeWithChoices node : postRemove) {
			node.removeChoice(nodeLink);
		}
	}

	public int getNodeIndex(AbstractBookNode node) {
		return nodesInv.containsKey(node) ? nodesInv.get(node) : -1;
	}
	
	public void addNodeObserver(BookNodeObserver observer) {
		bookNodeObservable.addObserver(observer);
	}
	
	public void removeNodeObserver(BookNodeObserver observer) {
		bookNodeObservable.removeObserver(observer);
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

	public List<Integer> getMissingIndexes() {
		return missingIndexes;
	}
	
}
