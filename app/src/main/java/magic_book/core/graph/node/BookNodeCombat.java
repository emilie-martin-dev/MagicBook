package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.Book;
import magic_book.core.file.json.CombatJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;

/**
 * Noeud de combat
 */
public class BookNodeCombat extends AbstractBookNodeWithChoices<BookNodeLink> {
	
	/**
	 * Lien vers un noeud si victoire
	 */
	private BookNodeLink winBookNodeLink;
	/**
	 * Lien vers un noeud si défaite
	 */
	private BookNodeLink looseBookNodeLink;
	/**
	 * Lien vers un noeud si evasion
	 */
	private BookNodeLink evasionBookNodeLink;
	/**
	 * Nombre de tour avant évasion
	 */
	private int evasionRound;
	/**
	 * Liste d'ID des ennemis sur ce noeud
	 */
	private List<String> ennemiesId;
	
	/**
	 * Constructeur vide
	 */
	public BookNodeCombat(){
		this("", null, null, null, 0, null, 0, null, null, null);
	}
	
	/**
	 * Constructeur simple du BookNodeCombat
	 * @param text Texte du noeud de combat
	 * @param winBookNodeLink Lien vers un noeud si victoire
	 * @param looseBookNodeLink Lien vers un noeud si défaite
	 * @param evasionBookNodeLink Lien vers un noeud si évasion
	 * @param evasionRound Nombre de tour avant d'avoir le droit de s'évader
	 * @param ennemiesId ID des ennemis sur ce noeud
	 */
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionRound, List<String> ennemiesId){
		this(text, winBookNodeLink, looseBookNodeLink, evasionBookNodeLink, evasionRound, ennemiesId, 0, null, null, null);
	}
	
	/**
	 * 
	* @param text Texte du noeud de combat
	 * @param winBookNodeLink Lien vers un noeud si victoire
	 * @param looseBookNodeLink Lien vers un noeud si défaite
	 * @param evasionBookNodeLink Lien vers un noeud si évasion
	 * @param evasionTurn Nombre de tour avant d'avoir le droit de s'évader
	 * @param ennemiesId ID des ennemis sur ce noeud
	 * @param nbItemsAPrendre Nombre d'item maximum pouvant être pris sur ce noeud (-1 représente l'infini)
	 * @param itemLinks Liste des items disponible sur ce noeud
	 * @param shopItemLinks Liste des items pouvant être acheté sur ce noeud
	 * @param choices Liste de BookNodeLink représentant tout les choix possible à partir de ce noeud
	 */
	public BookNodeCombat(String text, BookNodeLink winBookNodeLink, BookNodeLink looseBookNodeLink, BookNodeLink evasionBookNodeLink, int evasionTurn, List<String> ennemiesId, int nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLink> choices){
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
		
		this.winBookNodeLink = winBookNodeLink;
		this.looseBookNodeLink = looseBookNodeLink;
		this.evasionBookNodeLink = evasionBookNodeLink;
		this.evasionRound = evasionTurn;
		this.ennemiesId = ennemiesId;
		
		if(this.ennemiesId == null)
			this.ennemiesId = new ArrayList<>();
	}
	
	/**
	 * Donne un texte décrivant le combat
	 * @param book Livre contenant toute les informations
	 * @return Texte comprennant le nom des ennemis ainsi que le nombre de tour avant évasion
	 */
	public String getCombatDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("\nVous entrez en combat contre :\n\n");
		for(int i = 0 ; i < ennemiesId.size() ; i++) {
			BookCharacter character = book.getCharacters().get(ennemiesId.get(i));
			buffer.append(character.getDescription(book));
			if(i < ennemiesId.size()-1)
				buffer.append("\n");
		}
		
		buffer.append("Vous pouvez vous évader au tour ");
		buffer.append(evasionRound);
		buffer.append("\n");
		
		return buffer.toString();
	}
	
	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(super.getDescription(book));
		
		buffer.append(getCombatDescription(book));
		
		if(winBookNodeLink != null) {
			buffer.append("\nSi vous gagnez : \n\n");
			buffer.append(winBookNodeLink.getDescription(book));
		}
			
		if(looseBookNodeLink != null) {
			buffer.append("\nSi vous perdez : \n\n");
			buffer.append(looseBookNodeLink.getDescription(book));
		}
		
		if(evasionBookNodeLink != null) {
			buffer.append("\nSi vous souhaitez vous évader : \n\n");
			buffer.append(evasionBookNodeLink.getDescription(book));
			buffer.append("\n");
		}
		
		return buffer.toString();
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
		CombatJson combatJson = new CombatJson();

		combatJson.setEnemies(ennemiesId);

		if(winBookNodeLink != null) {
			combatJson.setWin(winBookNodeLink.toJson());
		}

		if(looseBookNodeLink != null) {
			combatJson.setLoose(looseBookNodeLink.toJson());
		}

		if(evasionBookNodeLink != null) {
			combatJson.setEvasion(evasionBookNodeLink.toJson());
			combatJson.setEvasionRound(evasionRound);
		}

		sectionJson.setCombat(combatJson);
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		this.evasionRound = 0;
		
		if(json.getCombat().getEvasionRound() != null)
			evasionRound = json.getCombat().getEvasionRound();
		
		for(String ennemie : json.getCombat().getEnemies()) {
			addEnnemieId(ennemie);
		}
		
		if(json.getCombat().getWin() != null) {
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getWin());
			
			winBookNodeLink = bookNodeLink;
		}

		if(json.getCombat().getLoose() != null){
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getLoose());
			
			looseBookNodeLink = bookNodeLink;
		}

		if(json.getCombat().getEvasion() != null){
			BookNodeLink bookNodeLink = new BookNodeLink();
			bookNodeLink.fromJson(json.getCombat().getEvasion());
			
			evasionBookNodeLink = bookNodeLink;
		}
	}
	
	@Override
	public void updateChoice(BookNodeLink oldNodeLink, BookNodeLink newNodeLink) {
		if(oldNodeLink == winBookNodeLink) {
			winBookNodeLink = newNodeLink;
		} 
		
		if(oldNodeLink == looseBookNodeLink) {
			looseBookNodeLink = newNodeLink;			
		}
		
		if(oldNodeLink == evasionBookNodeLink) {
			evasionBookNodeLink = newNodeLink;			
		}
	}
	
	@Override
	public void removeChoice(BookNodeLink nodeLink) {
		if(nodeLink == winBookNodeLink) {
			winBookNodeLink = null;
		} 
		
		if(nodeLink == looseBookNodeLink) {
			looseBookNodeLink = null;			
		}
		
		if(nodeLink == evasionBookNodeLink) {
			evasionBookNodeLink = null;			
		}
	}

	@Override
	public List<BookNodeLink> getChoices() {
		List<BookNodeLink> choices = new ArrayList<>();
		
		if(winBookNodeLink != null)
			choices.add(winBookNodeLink);
		
		if(looseBookNodeLink != null)
			choices.add(looseBookNodeLink);
		
		if(evasionBookNodeLink != null)
			choices.add(evasionBookNodeLink);
		
		return choices;
	}

	/**
	 * Ajoute l'id d'un ennemi sur le noeud
	 * @param ennemieId Nouvelle id de l'ennemi
	 */
	public void addEnnemieId(String ennemieId) {
		this.ennemiesId.add(ennemieId);
	}
	
	/**
	 * Lien si victoire
	 * @return BookNodeLink de victoire
	 */
	public BookNodeLink getWinBookNodeLink() {
		return winBookNodeLink;
	}

	/**
	 * Modification du lien de victoire
	 * @param winBookNodeLink Nouveau BookNodeLink de victoire
	 */
	public void setWinBookNodeLink(BookNodeLink winBookNodeLink) {
		this.winBookNodeLink = winBookNodeLink;
	}

	/**
	 * Lien si défaite
	 * @return BookNodeLink de défaite
	 */
	public BookNodeLink getLooseBookNodeLink() {
		return looseBookNodeLink;
	}

	/**
	 * Modification du lien de défaite
	 * @param looseBookNodeLink Nouveau BookNodeLink de défaite
	 */
	public void setLooseBookNodeLink(BookNodeLink looseBookNodeLink) {
		this.looseBookNodeLink = looseBookNodeLink;
	}

	/**
	 * Lien si évasion
	 * @return BookNodeLink d'évasion
	 */
	public BookNodeLink getEvasionBookNodeLink() {
		return evasionBookNodeLink;
	}

	/**
	 * Modification du lien d'évasion
	 * @param evasionBookNodeLink Nouveau BookNodeLink d'évasion
	 */
	public void setEvasionBookNodeLink(BookNodeLink evasionBookNodeLink) {
		this.evasionBookNodeLink = evasionBookNodeLink;
	}

	/**
	 * Donne le nombre de tour avant évasion
	 * @return Nombre de tour
	 */
	public int getEvasionRound() {
		return evasionRound;
	}

	/**
	 * Modifie le nombre de tour avant évasion
	 * @param evasionRound Nouveau nombre de tour avant
	 */
	public void setEvasionRound(int evasionRound) {
		this.evasionRound = evasionRound;
	}

	/**
	 * Donne la liste des ID des ennemis rattaché noeud
	 * @return Liste des ID des ennemis
	 */
	public List<String> getEnnemiesId() {
		return ennemiesId;
	}

	/**
	 * Modifie la liste des ennemis
	 * @param ennemiesId Nouvelle liste des ennemis
	 */
	public void setEnnemiesId(List<String> ennemiesId) {
		this.ennemiesId = ennemiesId;
	}
	
}
