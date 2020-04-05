package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.game.BookState;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

/**
 * Noeud à choix aléatoire
 */
public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	/**
	 * Constructeur vide d'un noeud aléatoire
	 */
	public BookNodeWithRandomChoices() {
		this("");
	}
	
	/**
	 * Constructeur avec texte
	 * @param text Texte du noeud
	 */
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null, null);
	}
	
	/**
	 * Initialisation des valeurs
	 * @param text Texte du noeud
	 * @param nbItemsAPrendre Nombre d'item maximum pouvant être pris sur ce noeud (-1 représente l'infini)
	 * @param itemLinks Liste d'item disponible sur ce noeud
	 * @param shopItemLinks Liste des items pouvant être acheté sur ce noeud
	 * @param choices Liste de BookNodeLinkRandom représentant tout les choix possible à partir de ce noeud
	 */
	public BookNodeWithRandomChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
	}
	
	/**
	 * Choisi un noeud aléatoire de destination en fonction de la chance de chaque liens
	 * @param state Etat actuel du jeu
	 * @return Lien de destination ou null si pas de possibilité
	 */
	public BookNodeLinkRandom getRandomChoices(BookState state){
		List<BookNodeLinkRandom> listNodeLinkDisponible = new ArrayList();
		int somme = 0;
		int nbrChoice = 0;
		
		for (int i = 0 ; i < this.getChoices().size() ; i++){
			if(this.getChoices().get(i).isAvailable(state)){
				listNodeLinkDisponible.add(this.getChoices().get(i));
				somme += this.getChoices().get(i).getChance();
			}
		}
		
		if(listNodeLinkDisponible.isEmpty()){
			return null;
		} else {
			Random random = new Random();
			int nbrRandomChoice;
			if(somme == 0)
				nbrRandomChoice = random.nextInt(listNodeLinkDisponible.size());
			else
				nbrRandomChoice = random.nextInt(somme);
			for (int i = 0 ; i < listNodeLinkDisponible.size() ; i++){
				if(!this.getChoices().get(i).isAvailable(state)){
					continue;
				}
				nbrRandomChoice -= this.getChoices().get(i).getChance();
				if(nbrRandomChoice < 0){
					nbrChoice = i;
					break;
				}
			}
		}
		
		return this.getChoices().get(nbrChoice) ;
	}

	@Override
	public SectionJson toJson() {
		SectionJson sectionJson = super.toJson();
	
		sectionJson.setIsRandomPick(true);
	
		return sectionJson;
	}

	@Override
	public void fromJson(SectionJson json) {
		super.fromJson(json);
		
		if(json.getChoices() != null) {
			for(ChoiceJson choiceJson : json.getChoices()) {		
				BookNodeLinkRandom nodeLinkRandom = new BookNodeLinkRandom();
				nodeLinkRandom.fromJson(choiceJson);
				
				addChoice(nodeLinkRandom);
			}
		}
	}
	
}
