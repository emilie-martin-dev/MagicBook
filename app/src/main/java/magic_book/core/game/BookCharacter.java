package magic_book.core.game;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterJson;
import magic_book.core.parser.Descriptible;
import magic_book.core.parser.Parsable;

/**
 * Création du personnage
 */
public class BookCharacter implements Parsable, Descriptible, JsonExportable<CharacterJson> {

	/**
	 * ID du personnage (permet de le retrouver)
	 */
	private String id;
	/**
	 * Nom du personnage
	 */
	private String name;
	/**
	 * Dommage de base
	 */
	private int baseDamage;
	/**
	 * Vie du personnage
	 */
	private int hp;
	/**
	 * Vie maximum du personnage
	 */
	private int hpMax;
	/**
	 * Liste de skill du personnage
	 */
	private List<String> skills;
	/**
	 * Liste d'imunités du personnage
	 */
	private List<String> immunes;
	/**
	 * Liste d'items du personnage
	 */
	private List<String> items;
	/**
	 * Argent et type d'argent du personnage
	 */
	private HashMap<String, Integer> moneys;
	/**
	 * Item maximum que peut posséder le personnage
	 */
	private int itemsMax;
	/**
	 * Peut réaliser un double dommage
	 */
	private boolean doubleDamage;
	
	/**
	 * Création d'un personnage
	 */
	public BookCharacter() {
		this("", "", 0, 0, null, null, 0);
	}

	/**
	 * Création d'un personnage normal
	 * @param id Permet de retrouver le personnage
	 * @param name Nom du personnage
	 * @param baseDamage Dommage de base du personnage
	 * @param hpMax Point de vie maximum du personnage
	 * @param listSkills Liste de compétence du personnage
	 * @param listItems Liste d'item du personnage
	 * @param itemsMax Item maximum possédé
	 */
	public BookCharacter(String id, String name, int baseDamage, int hpMax, List<String> listSkills, List<String> listItems, int itemsMax) {
		this(id, name, baseDamage, hpMax, listSkills, null, listItems, itemsMax, false);
	}
	
	/**
	 * Création d'un personnage avec tout les paramètres
	 * @param id Permet de retrouver le personnage
	 * @param name Nom du personnage
	 * @param baseDamage Dommage de base du personnage
	 * @param hpMax Point de vie maximum du personnage
	 * @param listSkills Liste de compétence du personnage
	 * @param listImmune Liste d'immunité du personnage
	 * @param listItems Liste d'item du personnage
	 * @param itemsMax Item maximum possédé
	 * @param doubleDamage Double dommage activé (true) ou non (false)
	 */
	public BookCharacter(String id, String name, int baseDamage, int hpMax, List<String> listSkills, List<String> listImmune, List<String> listItems, int itemsMax, boolean doubleDamage) {
		this.id = id;
		this.name = name;
		this.baseDamage = baseDamage;
		this.hp = hpMax;
		this.hpMax = hpMax;
		this.skills = listSkills;
		this.immunes = listImmune;
		this.items = listItems;
		this.itemsMax = itemsMax;
		this.doubleDamage = doubleDamage;
		this.moneys = new HashMap<>();
		
		if(this.skills == null) {
			this.skills = new ArrayList<>();
		}
		
		if(this.immunes == null) {
			this.immunes = new ArrayList<>();
		}
		
		if(this.items == null) {
			this.items = new ArrayList<>();
		}
	}

	/**
	 * Modification d'un personnage
	 * @param bookCharacter Nouveau personnage
	 */
	public BookCharacter(BookCharacter bookCharacter) {
		this.id = bookCharacter.id;
		this.name = bookCharacter.name;
		this.baseDamage = bookCharacter.baseDamage;
		this.hp = bookCharacter.hp;
		this.hpMax = bookCharacter.hpMax;
		
		this.skills = new ArrayList<>();
		for(String s : bookCharacter.skills) {
			this.skills.add(s);
		}
		
		this.immunes = new ArrayList<>();
		for(String i : bookCharacter.immunes) {
			this.immunes.add(i);
		}
		
		this.items = new ArrayList<>();
		for(String i : bookCharacter.skills) {
			this.items.add(i);
		}
		
		this.moneys = new HashMap<>();
		if(!bookCharacter.moneys.isEmpty()){
			for(Entry<String, Integer> entry : bookCharacter.moneys.entrySet()) {
				this.moneys.put(entry.getKey(), entry.getValue());
			}
		} else {
			this.moneys.put("gold", 0);
		}
		
		this.itemsMax = bookCharacter.itemsMax;
		this.doubleDamage = bookCharacter.doubleDamage;
	}

	@Override
	public CharacterJson toJson() {
		CharacterJson characterJson = new CharacterJson();
		
		characterJson.setId(id);
		characterJson.setName(name);
		characterJson.setCombatSkill(baseDamage);
		characterJson.setHp(hpMax);
		characterJson.setItemMax(itemsMax);
		characterJson.setMoney(moneys.get("gold"));
		
		if(doubleDamage)
			characterJson.setDoubleDamage(true);
		
		if(!immunes.isEmpty())
			characterJson.setImmune(immunes);
		
		if(!skills.isEmpty())
			characterJson.setSkills(skills);
		
		return characterJson;
	}

	@Override
	public void fromJson(CharacterJson json) {
		this.id = json.getId();
		this.name = json.getName();
		this.baseDamage = json.getCombatSkill();
		this.hp = json.getHp();
		this.hpMax = json.getHp();
		this.skills.clear();
		if(json.getSkills() != null) {
			for(String skill : json.getSkills()) {
				this.skills.add(skill);
			}
		}
		
		this.itemsMax = 0;
		if(json.getItemMax() != null) {
			this.itemsMax = json.getItemMax();
		}
		
		this.items.clear();
		
		this.immunes.clear();
		if(json.getImmune() != null) {
			for(String immune : json.getImmune()) {
				immunes.add(immune);
			}
		}
		
		this.moneys.clear();
		this.moneys.put("gold", 0);
		if(json.getMoney()!= null) {
			this.moneys.put("gold", json.getMoney());
		}
		
		if(json.getDoubleDamage() != null)
			this.doubleDamage = json.getDoubleDamage();
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(name);
		buffer.append("\n");
		buffer.append("HP : ");
		buffer.append(hp);
		buffer.append("\n");
		buffer.append("Dégats : ");
		buffer.append(baseDamage);
		buffer.append("\n");
		buffer.append("Items max : ");
		buffer.append(itemsMax);
		buffer.append("\n");
		buffer.append("Argent : ");
		buffer.append(moneys.get("gold"));
		buffer.append("\n");
		
		if(!skills.isEmpty()) {
			buffer.append("Skills : \n");
			for(String skillId : skills) {
				buffer.append("- ");
				buffer.append(book.getSkills().get(skillId).getName());
				buffer.append("\n");
			}
		}
		
		if(!immunes.isEmpty()) {
			buffer.append("Immunisé : \n");
			for(String skillId : immunes) {
				buffer.append("- ");
				buffer.append(book.getSkills().get(skillId).getName());
				buffer.append("\n");
			}
		}
		
		if(doubleDamage) {
			buffer.append("Modifications : Double dégats\n");
		}
		
		return buffer.toString();
	}
	
	/**
	 * Si le personnage est encore en vie
	 * @return True (hp>0) sinon false
	 */
	public boolean isAlive() {
		return this.hp > 0;
	}
	
	/**
	 * Modifie le nombre de hp en fonction des dommages subit
	 * @param amount Point de dommage
	 */
	public void damage(int amount) {
		this.hp -= amount;
		if (this.hp < 0){
			this.hp = 0;
		}
	}
	
	/**
	 * Modifie le nombre d'hp en fonction du soin
	 * @param amount Point de soin
	 */
	public void heal(int amount) {
		this.hp += amount;
		
		if (hp > hpMax) {
			this.hp = hpMax;
		}
	}
	
	/**
	 * Donne le nombre d'argent en fonction du nom de la monaie demandé
	 * @param moneyId Nom de la monnaie
	 * @return Nombre d'argent en fonction du nom de la monnaie
	 */
	public int getMoney(String moneyId) {
		if(moneys.get(moneyId) != null) {
			return moneys.get(moneyId);
		} else {
			return 0;
		}
	}
	
	/**
	 * Change le nombre d'argent disponible en fonction du nom de la monnaie
	 * @param moneyId Nom de la monnaie
	 * @param amount Nombre d'argent en plus ou en moins
	 */
	public void changeMoneyAmount(String moneyId, int amount) {
		int newAmount = amount;
		
		if(moneys.get(id) != null) {
			newAmount += moneys.get(moneyId);
		}
		
		moneys.put(moneyId, newAmount);
	}
	
	@Override
	public String getParsableId() {
		return this.id;
	}
	
	@Override
	public String getParsableText() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * Ajoute un item
	 * @param item ID de l'item
	 */
	public void addItem(String item) {
		this.items.add(item);
	}
	
	/**
	 * Ajoute un skill
	 * @param skill ID du skill
	 */
	public void addSkill(String skill) {
		this.skills.add(skill);
	}
	
	/**
	 * Supprime un item
	 * @param item ID de l'item
	 */
	public void removeItem(String item) {
		this.items.remove(item);
	}

	/**
	 * Supprime un skill
	 * @param skill ID du skill
	 */
	public void removeSkill(String skill) {
		this.skills.remove(skill);
	}

	/**
	 * Donne l'ID du personnage
	 * @return ID du personnage
	 */
	public String getId() {
		return id;
	}

	/**
	 * Change l'ID du personnage
	 * @param id Nouvel ID du personnage
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Donne le nom du personnage
	 * @return Nom du personnage
	 */
	public String getName() {
		return name;
	}

	/**
	 * Change le nom du personnage
	 * @param name Nouveau nom du personnage
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Donne le nombre de dommage que fait le personnage
	 * @return Nombre de dommage
	 */
	public int getBaseDamage() {
		return baseDamage;
	}

	/**
	 * Modifie le nombre de dommage que fait le personnage
	 * @param baseDamage Nombre de dommage
	 */
	public void setBaseDamage(int baseDamage) {
		this.baseDamage = baseDamage;
	}

	/**
	 * Donne la vie du personnage
	 * @return Vie du personnage
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * Modifie la vie du personnage
	 * @param hp Nouveau point de vie du personnage
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * Donne le nombre de point de vie maximum du personnage
	 * @return Point de vie maximum du personnage
	 */
	public int getHpMax() {
		return hpMax;
	}

	/**
	 * Modifie le nombre de point de vie maximum du personnage
	 * @param hpMax Nouveau nombre de point de vie maximum
	 */
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	/**
	 * Donne la liste des skill
	 * @return Liste des skill
	 */
	public List<String> getSkills() {
		return skills;
	}

	/**
	 * Modifie la liste des skill
	 * @param skills Nouvelle liste de skill
	 */
	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	/**
	 * Donne la liste des immunités
	 * @return Liste des immunités
	 */
	public List<String> getImmunes() {
		return immunes;
	}

	/**
	 * Modifie la liste des immunités
	 * @param immunes Nouvelle liste des immunités
	 */
	public void setImmunes(List<String> immunes) {
		this.immunes = immunes;
	}

	/**
	 * Donne la liste des items
	 * @return Liste des items
	 */
	public List<String> getItems() {
		return items;
	}

	/**
	 * Modifie la liste des items
	 * @param items Nouvelle liste des items
	 */
	public void setItems(List<String> items) {
		this.items = items;
	}

	/**
	 * Donne le nombre d'item maximum
	 * @return Nombre d'item maximum
	 */
	public int getItemsMax() {
		return itemsMax;
	}

	/**
	 * Modifie le nombre d'item maximum
	 * @param itemsMax Nouveau nombre d'item maximum
	 */
	public void setItemsMax(int itemsMax) {
		this.itemsMax = itemsMax;
	}

	/**
	 * Donne si le double dommage est activé ou non
	 * @return True ou False
	 */
	public boolean isDoubleDamage() {
		return doubleDamage;
	}

	/**
	 * Modifie le double dommage
	 * @param doubleDamage True ou False
	 */
	public void setDoubleDamage(boolean doubleDamage) {
		this.doubleDamage = doubleDamage;
	}

}