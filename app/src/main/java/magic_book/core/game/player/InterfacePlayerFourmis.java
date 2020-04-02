package magic_book.core.game.player;

import java.util.List;
import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.game.player.Jeu.ChoixCombat;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.item.BookItemLink;

public interface InterfacePlayerFourmis {
	
	/**
	* Permet de prendre, au début de la partie, les items et les skills disponible dans le prélude
	* @param book contient tout le livre
	* @param characterCreation skill ou item disponible
	* @param state Sauvegarde actuelle de la partie
	*/
	public void execPlayerCreation(Book book, AbstractCharacterCreation characterCreation, BookState state);
	
	/**
	* Permet de choisir entre Attaque, Inventaire et Evasion lors d'un combat
	* @param bookNodeCombat Noeud du combat actuel
	* @param remainingRoundBeforeEvasion Nombre de tour avant la possibilité de l'évasion du joueur
	* @param state Sauvegarde actuelle de la partie
	* @return Choix
	*/
	public ChoixCombat combatChoice(BookNodeCombat bookNodeCombat, int remainingRoundBeforeEvasion, BookState state);
	
	/**
	* Permet de choisir l'ennemi à attaquer
	* @param listEnnemis Contient la liste des ennemis en vie
	* @return L'ennemi choisi
	*/
	public BookCharacter chooseEnnemi(List<BookCharacter> listEnnemis);
	
	/**
	* Permet de prendre un item disponible dans un noeud
	* @param state Sauvegarde actuelle de la partie
	* @param bookItemLinks Item(s) disponible(s) sur le lien actuel
	* @param nbItemMax Items maximum pouvant être pris dans ce noeud
	*/
	public void prendreItems(BookState state, List<BookItemLink> bookItemLinks, int nbItemMax);	
	
	/**
	* Permet d'effectuer un choix
	* @param node Noeud de choix actuel
	* @return Choix
	*/
	public int makeAChoice(AbstractBookNodeWithChoices node);
	
	/**
	* Permet d'utiliser un objet de l'inventaire
	* @param state Sauvegarde actuelle de la partie
	*/
	public void useInventaire(BookState state);
	
}
