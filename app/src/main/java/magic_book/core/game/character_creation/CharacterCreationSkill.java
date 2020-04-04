package magic_book.core.game.character_creation;

import java.util.ArrayList;
import java.util.List;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.CharacterCreationJson;
import magic_book.core.file.json.TypeJson;

/**
 *  Étape de la "Création du personnage" dans lequel on peut choisir ses skils de départ
 */
public class CharacterCreationSkill extends AbstractCharacterCreation {
	
	/**
	 * Nombre maximum de skills pouvant être pris
	 */
	private int amountToPick;
	
	/**
	 * Liste des skills
	 */
	private List<String> skillLinks;
	
	/**
	 * Constructeur basique
	 */
	public CharacterCreationSkill() {
		this("", null, -1);
	}
	
	/**
	 * Constructeur complet
	 * @param text Texte à afficher
	 * @param skillLinks Liste de skill disponibles
	 * @param amountToPick Nombre de skill maximum pouvant être pris
	 */
	public CharacterCreationSkill(String text, List<String> skillLinks, int amountToPick) {
		super(text);
		
		this.skillLinks = skillLinks;
		this.amountToPick = amountToPick;
		
		if(this.skillLinks == null)
			this.skillLinks = new ArrayList<>();
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getText());
		buffer.append("\n");
		buffer.append("Choisissez ");
		buffer.append(amountToPick);
		buffer.append(" compétences : \n\n");
		
		for(int i = 0 ; i < skillLinks.size() ; i++) {
			buffer.append("- ");
			buffer.append(book.getSkills().get(skillLinks.get(i)).getName());
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
	
	@Override
	public CharacterCreationJson toJson() {
		CharacterCreationJson characterCreationJson = super.toJson();
		
		characterCreationJson.setAmountToPick(amountToPick);
		characterCreationJson.setSkills(skillLinks);
		characterCreationJson.setType(TypeJson.SKILL);
		
		return characterCreationJson;
	}

	@Override
	public void fromJson(CharacterCreationJson json) {
		super.fromJson(json);
		
		this.setAmountToPick(json.getAmountToPick());
		this.setSkillLinks(json.getSkills());
	}
	
	/**
	 * Ajoute un skill à la liste de tout les skills
	 * @param skillLink Skill à ajouter
	 */ 
	public void addSkillLink(String skillLink) {
		this.skillLinks.add(skillLink);
	}
	
	/**
	 * Donne la liste de skills
	 * @return Liste de skills
	 */
	public List<String> getSkillLinks() {
		return skillLinks;
	}

	/**
	 * Modifie toute la liste des skills
	 * @param skillLinks Liste de skills
	 */
	public void setSkillLinks(List<String> skillLinks) {
		this.skillLinks = skillLinks;
	}

	/**
	 * Donne le nombre de skills pouvant être pris
	 * @return Nombre de skills pouvant être pris
	 */
	public int getAmountToPick() {
		return amountToPick;
	}
	
	/**
	 * Change le nombre maximum de skill pouvant être pris
	 * @param amountToPick Nouveau nombre de skill pouvant être pris
	 */
	public void setAmountToPick(int amountToPick) {
		this.amountToPick = amountToPick;
	}
}
