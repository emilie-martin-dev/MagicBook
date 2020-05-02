package magic_book.core;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookSkill;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.item.BookItem;
import magic_book.observer.book.BookCharacterObservable;
import magic_book.observer.book.BookCharacterObserver;
import magic_book.observer.book.BookItemObservable;
import magic_book.observer.book.BookItemObserver;
import magic_book.observer.book.BookNodeLinkObservable;
import magic_book.observer.book.BookNodeLinkObserver;
import magic_book.observer.book.BookNodeObservable;
import magic_book.observer.book.BookNodeObserver;
import magic_book.observer.book.BookSkillObservable;
import magic_book.observer.book.BookSkillObserver;

/**
 * Gère un livre
 */
public class Book {

	/**
	 * Constante pour l'ID du personnage principal
	 */
	public static final String MAIN_CHARACTER_ID = "main_character";

	/**
	 * Le texte du prélude
	 */
	private String textPrelude;
	/**
	 * Les différents noeuds
	 */
	private HashMap<Integer, AbstractBookNode> nodes;
	/**
	 * Les items
	 */
	private HashMap<String, BookItem> items;
	/**
	 * Les peronnages
	 */
	private HashMap<String, BookCharacter> characters;
	/**
	 * Les compétences
	 */
	private HashMap<String, BookSkill> skills;
	/**
	 * Les phases de la création du personnage
	 */
	private List<AbstractCharacterCreation> characterCreations;

	/**
	 * Observable pour les noeuds
	 */
	private BookNodeObservable bookNodeObservable;
	/**
	 * Observable pour les liens entre les noeuds
	 */
	private BookNodeLinkObservable bookNodeLinkObservable;
	/**
	 * Observable pour les items
	 */
	private BookItemObservable bookItemObservable;
	/**
	 * Observable pour les personnages
	 */
	private BookCharacterObservable bookCharacterObservable;
	/**
	 * Observable pour les compétences
	 */
	private BookSkillObservable bookSkillObservable;

	/**
	 * La liste des numéros de noeud manquants
	 */
	private List<Integer> missingIndexes;
	/**
	 *L'inverse de nodes
	 */
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

		this.bookNodeObservable = new BookNodeObservable();
		this.bookNodeLinkObservable = new BookNodeLinkObservable();
		this.bookItemObservable = new BookItemObservable();
		this.bookCharacterObservable = new BookCharacterObservable();
		this.bookSkillObservable = new BookSkillObservable();

		computeNodesInv();
		computeMissingIndexes();
	}

	/**
	 * Ajout d'un noeud
	 * @param node Le noeud à ajouter
	 */
	public void addNode(AbstractBookNode node) {
		if(this.nodes.containsValue(node))
			return;

		// Pas de "trou" dans la liste des paragraphes
		if(this.missingIndexes.isEmpty()) {
			// Décalage de 2 = pas de premier noeud
			int offset = (this.nodes.containsKey(1)) ? 1 : 2;
			this.nodes.put(this.nodes.size()+offset, node);
			this.nodesInv.put(node, this.nodesInv.size()+offset);
		// Il existe un "trou" dans la liste des paragraphes
		} else {
			this.nodes.put(missingIndexes.get(0), node);
			this.nodesInv.put(node, missingIndexes.get(0));
			this.missingIndexes.remove(0);
		}

		bookNodeObservable.notifyNodeAdded(node);
	}

	/**
	 * Change le noeud de départ. Si le noeud n'est pas encore présent dans le livre, l'ajoute d'abord
	 * @param node Le nouveau noeud de départ
	 */
	public void changeFirstNode(AbstractBookNode node) {
		// Si le noeud n'est pas présent dans le livre
		if(!this.nodes.containsValue(node)) {
			addNode(node);
		}

		// On met à jours les liens du noeud 1 vers -1 car il n'est jamais pris et fait office de variable temporaire
		updateDestinations(1, -1);
		// On récupère l'indice du noeud qui sera le premier pour changer tous les liens de celui-ci vers 1, pour faire un échange avec le premier noeud actuel ou ajouter le numéro à la liste des numéros manquants
		int indexOfNode = this.nodesInv.get(node);
		AbstractBookNode oldNode = this.nodes.get(1);
		updateDestinations(indexOfNode, 1);

		this.nodes.put(1, node);
		this.nodesInv.put(node, 1);

		// Il y avait déjà un premier noeud, celui-ci va à la place du nouveau premier noeud
		if(oldNode != null) {
			this.nodes.put(indexOfNode, oldNode);
			this.nodesInv.put(oldNode, indexOfNode);

			updateDestinations(-1, indexOfNode);
		// Il n'y avait pas de premier noeud, on ajoute le numéro du paragraphe à la liste des éléments manquants
		} else {
			this.missingIndexes.add(indexOfNode);
			this.nodes.remove(indexOfNode);
		}
	}

	/**
	 * Met à jour la destination des noeuds d'un ancien indice vers un nouveau
	 * @param oldDestination Ancien indice
	 * @param newDestination Nouvel indice
	 */
	public void updateDestinations(int oldDestination, int newDestination) {
		for(Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()) {
			for(BookNodeLink nodeLink : entry.getValue().getChoices()) {
				if(nodeLink.getDestination() == oldDestination) {
					nodeLink.setDestination(newDestination);
				}
			}
		}
	}

	/**
	 * Met à jour un noeud
	 * @param oldNode L'ancien noeud
	 * @param newNode Le nouveau noeud
	 */
	public void updateNode(AbstractBookNode oldNode, AbstractBookNode newNode) {
		Integer indexOfNode = this.nodesInv.get(oldNode);
		// Si le noeud existe
		if(indexOfNode != null) {
			// Si c'est ou était un noeud combat, ou un type de noeud différent, on supprime tous les liens
			if(oldNode instanceof BookNodeCombat || newNode instanceof BookNodeCombat || oldNode.getClass() != newNode.getClass()) {
				for(BookNodeLink nodeLink : oldNode.getChoices()) {
					bookNodeLinkObservable.notifyNodeLinkDeleted(nodeLink);
				}
			// Si c'est un noeud à choix, on ajoute les liens au nouveau noeud
			} else if(newNode instanceof AbstractBookNodeWithChoices) {
				AbstractBookNodeWithChoices bookNodeWithChoices = (AbstractBookNodeWithChoices) newNode;
				for(BookNodeLink nodeLink : oldNode.getChoices()) {
					bookNodeWithChoices.addChoice(nodeLink);
				}
			}

			this.nodes.put(indexOfNode, newNode);
			this.nodesInv.put(newNode, indexOfNode);
			this.nodesInv.remove(oldNode);

			bookNodeObservable.notifyNodeEdited(oldNode, newNode);
		// Si le noeud n'existe pas, on l'ajoute
		} else {
			addNode(newNode);
		}
	}

	/**
	 * Supprime un noeud
	 * @param node
	 */
	public void removeNode(AbstractBookNode node) {
		Integer indexOfNode = this.nodesInv.get(node);
		if(indexOfNode != null) {
			// Si le noeud n'est pas le premier, alors on doit l'ajouter à la liste des éléments manquants
			if(indexOfNode != 1)
				this.missingIndexes.add(indexOfNode);

			this.nodes.remove(indexOfNode);
			this.nodesInv.remove(node);

			bookNodeObservable.notifyNodeDeleted(node);

			// On doit supprimer tous les liens qui pointent vers le noeud supprimé
			// La liste postRemove est ici pour éviter une exception du à la modification de la liste durant son parcours
			List<AbstractMap.SimpleEntry<AbstractBookNodeWithChoices, BookNodeLink>> postRemove = new ArrayList<>();
			for(Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()) {
				// Si le noeud ne possède pas de liens, on "continue"
				if(!(entry.getValue() instanceof AbstractBookNodeWithChoices))
					continue;

				// Le noeud possède des liens, on les parcours 1 à 1 et on regarde si leur destination était le noeud supprimé
				AbstractBookNodeWithChoices currentNode = (AbstractBookNodeWithChoices) entry.getValue();
				for(BookNodeLink nodeLink : entry.getValue().getChoices()) {
					if(nodeLink.getDestination() == indexOfNode) {
						postRemove.add(new AbstractMap.SimpleEntry<>(currentNode, nodeLink));
					}
				}
			}

			// On supprime le lien du noeud
			for(Entry<AbstractBookNodeWithChoices, BookNodeLink> entry : postRemove) {
				entry.getKey().removeChoice(entry.getValue());
				bookNodeLinkObservable.notifyNodeLinkDeleted(entry.getValue());
			}

			// On prévient de la suppression des liens du noeud supprimé
			for(BookNodeLink nodeLink : node.getChoices()) {
				bookNodeLinkObservable.notifyNodeLinkDeleted(nodeLink);
			}
		}
	}

	/**
	 * Ajout d'un lien sur un noeud
	 * @param nodeLink Le lien à ajouter
	 * @param node Le noeud sur lequel ajouter le lien
	 */
	public void addNodeLink(BookNodeLink nodeLink, AbstractBookNodeWithChoices node) {
		node.addChoice(nodeLink);
		bookNodeLinkObservable.notifyNodeLinkAdded(nodeLink, node);
	}

	/**
	 * Mise à jour d'un lien
	 * @param oldBookNodeLink L'ancien lien
	 * @param newBookNode Le nouveau lien
	 */
	public void updateNodeLink(BookNodeLink oldBookNodeLink, BookNodeLink newBookNode) {
		List<AbstractBookNodeWithChoices> postUpdate = new ArrayList<>();

		// On cherche l'ancien lien parmis les noeuds à choix
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

		// On demande au noeud de mettre a jour son lien
		for(AbstractBookNodeWithChoices node : postUpdate) {
			node.updateChoice(oldBookNodeLink, newBookNode);
		}

		bookNodeLinkObservable.notifyNodeLinkEdited(oldBookNodeLink, newBookNode);
	}

	/**
	 * Supprime un lien
	 * @param nodeLink Le lien à supprimer
	 */
	public void removeNodeLink(BookNodeLink nodeLink) {
		List<AbstractBookNodeWithChoices> postRemove = new ArrayList<>();
		// Parcours les noeuds à choix pour supprimer le lien qui doit l'être
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

		bookNodeLinkObservable.notifyNodeLinkDeleted(nodeLink);
	}

	/**
	 * Ajoute un item
	 * @param item L'item à ajouter
	 */
	public void addItem(BookItem item) {
		items.put(item.getId(), item);

		bookItemObservable.notifyItemAdded(item);
	}

	/**
	 * Met à jour un item
	 * @param oldItem L'ancien item
	 * @param newItem Le nouvel item
	 */
	public void updateItem(BookItem oldItem, BookItem newItem) {
		items.remove(oldItem.getId());
		items.put(newItem.getId(), newItem);

		bookItemObservable.notifyItemEdited(oldItem, newItem);
	}

	/**
	 * Supprime l'item
	 * @param item L'item à supprimer
	 */
	public void removeItem(BookItem item) {
		items.remove(item.getId());

		bookItemObservable.notifyItemDeleted(item);
	}

	/**
	 * Ajoute un personnage
	 * @param character Le personnage à ajouter
	 */
	public void addCharacter(BookCharacter character) {
		characters.put(character.getId(), character);

		bookCharacterObservable.notifyCharacterAdded(character);
	}

	/**
	 * Met à jour un personnage
	 * @param oldCharacter L'ancien personnage
	 * @param newCharacter Le nouveau personnage
	 */
	public void updateCharacter(BookCharacter oldCharacter, BookCharacter newCharacter) {
		characters.remove(oldCharacter.getId());
		characters.put(newCharacter.getId(), newCharacter);

		bookCharacterObservable.notifyCharacterEdited(oldCharacter, newCharacter);
	}

	/**
	 * Supprime un personnage
	 * @param character Le personnage à supprimer
	 */
	public void removeCharacter(BookCharacter character) {
		characters.remove(character.getId());

		bookCharacterObservable.notifyCharacterDeleted(character);
	}

	/**
	 * Change le personnage principal
	 * @param mainCharacter Le nouveau personnage principal
	 */
	public void setMainCharacter(BookCharacter mainCharacter) {
		mainCharacter.setId(MAIN_CHARACTER_ID);
		this.characters.put(MAIN_CHARACTER_ID, mainCharacter);
	}

	/**
	 * Récupère le personnage principal
	 * @return Le personnage principal
	 */
	public BookCharacter getMainCharacter() {
		return this.characters.get(MAIN_CHARACTER_ID);
	}

	/**
	 * Ajout d'un skill
	 * @param skill Le skill à ajouter
	 */
	public void addSkill(BookSkill skill) {
		skills.put(skill.getId(), skill);

		bookSkillObservable.notifySkillAdded(skill);
	}

	/**
	 * Met à jour un skill
	 * @param oldSkill L'ancien skill
	 * @param newSkill Le nouveau skill
	 */
	public void updateSkill(BookSkill oldSkill, BookSkill newSkill) {
		skills.remove(oldSkill.getId());
		skills.put(newSkill.getId(), newSkill);

		bookSkillObservable.notifySkillEdited(oldSkill, newSkill);
	}

	/**
	 * Supprime un skill
	 * @param skill Le skill à supprimer
	 */
	public void removeSkill(BookSkill skill) {
		skills.remove(skill.getId());

		bookSkillObservable.notifySkillDeleted(skill);
	}

	/**
	 * Calcule les éléments manquants dans le livre
	 */
	private void computeMissingIndexes() {
		int i = 2;
		// Si le livre contient un premier noeud, on à vu 1 noeud
		int nbNoeudsVus = (nodes.containsKey(1)) ? 1 : 0;
		// Tant qu'on a pas vu tous les noeuds
		while(nbNoeudsVus < nodes.size()) {
			// Si le noeud i n'est pas dans la map, alors il est manquant
			if(!nodes.containsKey(i)) {
				missingIndexes.add(i);
			} else {
				nbNoeudsVus++;
			}

			i++;
		}
	}

	/**
	 * "Calule" l'inverse de la map nodes
	 */
	private void computeNodesInv() {
		for(Map.Entry<Integer, AbstractBookNode> entry : this.nodes.entrySet()){
			nodesInv.put(entry.getValue(), entry.getKey());
		}
	}

	/**
	 * Retourne l'index d'un noeud
	 * @param node Le noeud pour lequel on veut l'index
	 */
	public int getNodeIndex(AbstractBookNode node) {
		return nodesInv.containsKey(node) ? nodesInv.get(node) : -1;
	}

	/**
	 * Ajoute un BookNodeObserver
	 * @param observer L'observer à ajouter
	 */
	public void addNodeObserver(BookNodeObserver observer) {
		bookNodeObservable.addObserver(observer);
	}

	/**
	 * Supprime un BookNodeObserver
	 * @param observer L'observer à supprimer
	 */
	public void removeNodeObserver(BookNodeObserver observer) {
		bookNodeObservable.removeObserver(observer);
	}

	/**
	 * Ajoute un BookNodeLinkObserver
	 * @param observer L'observer à ajouter
	 */
	public void addNodeLinkObserver(BookNodeLinkObserver observer) {
		bookNodeLinkObservable.addObserver(observer);
	}

	/**
	 * Supprime un BookNodeLinkObserver
	 * @param observer L'observer à supprimer
	 */
	public void removeNodeLinkObserver(BookNodeLinkObserver observer) {
		bookNodeLinkObservable.removeObserver(observer);
	}

	/**
	 * Ajoute un BookCharacterObserver
	 * @param observer L'observer à ajouter
	 */
	public void addCharacterObserver(BookCharacterObserver observer) {
		bookCharacterObservable.addObserver(observer);
	}

	/**
	 * Supprime un BookCharacterObserver
	 * @param observer L'observer à supprimer
	 */
	public void removeCharacterObserver(BookCharacterObserver observer) {
		bookCharacterObservable.removeObserver(observer);
	}

	/**
	 * Ajoute un BookItemObserver
	 * @param observer L'observer à ajouter
	 */
	public void addItemObserver(BookItemObserver observer) {
		bookItemObservable.addObserver(observer);
	}

	/**
	 * Supprime un BookItemObserver
	 * @param observer L'observer à supprimer
	 */
	public void removeItemObserver(BookItemObserver observer) {
		bookItemObservable.removeObserver(observer);
	}

	/**
	 * Ajoute un BookSkillObserver
	 * @param observer L'observer à ajouter
	 */
	public void addSkillObserver(BookSkillObserver observer) {
		bookSkillObservable.addObserver(observer);
	}

	/**
	 * Supprime un BookSkillObserver
	 * @param observer L'observer à supprimer
	 */
	public void removeSkillObserver(BookSkillObserver observer) {
		bookSkillObservable.removeObserver(observer);
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

	/**
	 * Retourne le noeud de départ
	 * @return Le noeud de départ
	 */
	public AbstractBookNode getRootNode() {
		return nodes.get(1);
	}

	public void setNodes(HashMap<Integer, AbstractBookNode> nodes) {
		this.nodes = nodes;

		computeNodesInv();
		computeMissingIndexes();
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

	public HashMap<AbstractBookNode, Integer> getNodesInv() {
		return nodesInv;
	}

	public BookNodeObservable getBookNodeObservable() {
		return bookNodeObservable;
	}

	public void setBookNodeObservable(BookNodeObservable bookNodeObservable) {
		this.bookNodeObservable = bookNodeObservable;
	}

}
