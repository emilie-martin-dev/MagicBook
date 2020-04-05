package magic_book.core.game.player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import magic_book.core.Book;
import magic_book.core.exception.BookFileException;
import magic_book.core.file.BookReader;
import magic_book.core.file.BookWritter;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.game.character_creation.AbstractCharacterCreation;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;

/**
 * Permet de gérer la partie que ça soit dans le cas d'une fourmi ou d'un joueur
 */
public class Jeu {
	
	/**
	 * Enum des différents choix pour les noeuds de combat
	 */
	public enum ChoixCombat {
		ATTAQUER, INVENTAIRE, EVASION;
	}
	
	/**
	 * État actuelle de la partie
	 */
	private BookState state;
	
	/**
	 * Contient le player actuel (fourmi ou joueur)
	 */
	private InterfacePlayerFourmis player;
	
	/**
	 * Copie du livre sur lequel on joue
	 */
	private Book book;
	
	/**
	 * Permet de savoir si on doit afficher les messages
	 */
	private boolean showMessages;
	
	/**
	 * Livre contenant toutes les informations
	 */
	private Book bookToRead;
	
	/**
	 * Création du jeu
	 * @param book Livre contenant toutes les informations et mis en la variable bookToRead
	 */
	public Jeu(Book book){
		this.bookToRead = book;
	}
	
	/**
	 * Méthode qui est appelé pour jouer en tant que player
	 * @throw IOException : si l'écriture / la lecture est incorecte
	 * @throw BookFileException : si le fichier est incorrect
	 */
	public void play() throws IOException, BookFileException {
		player = new Player();
		
		showMessages = true;
		
		runGame();
	}
	
	/**
	 * Estime la dificulté du livre (fourmis)
	 * @throw IOException : si l'écriture / la lecture est incorecte
	 * @throw BookFileException : si le fichier est incorrect
	 * @param nbrFourmis Nombre de fois que le jeu va se lancer
	 * @return pourcentage de victoire
	 */
	public float fourmis(int nbrFourmis) throws IOException, BookFileException {
		showMessages = false;
		
		int victoire = 0;
		for(int i = 0 ; i < nbrFourmis ; i++){			
			player = new Fourmi();
			
			if(runGame()) {
				victoire++;
			}
		}
		
		return ((float)victoire / (float)nbrFourmis) * 100f;
	}
	
	/**
	 * Boucle principal du jeu
	 * @throw IOException : si l'écriture / la lecture est incorecte
	 * @throw FileNotFoundException : si le fichier n'est pas trouvé
	 * @throw BookFileException : si le fichier est incorrect
	 * @return true si la partie est gagné, false sinon
	 */
	private boolean runGame() throws IOException, FileNotFoundException, BookFileException {
		boolean gameFinish = false;
		boolean win = false;
		
		//Copie du livre afin de ne pas le modifier l'original
		String tmpPath = ".livreTmpGame";
		BookWritter bookWritter = new BookWritter();
		bookWritter.write(tmpPath, bookToRead);
		BookReader bookReader = new BookReader();
		Book bookCopy = bookReader.read(tmpPath);
		File file = new File(tmpPath);
		file.delete();

		this.book = bookCopy;

		showMessage(this.book.getTextPrelude());
		this.state = createNewState();
		
		//Premier noeud (noeud de départ)
		AbstractBookNode currentNode = this.book.getRootNode();
		
		//Tant que le jeu n'est pas fini
		while(!gameFinish){
			//Si c'est un noeud de combat
			if(currentNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) currentNode;
				currentNode = execNodeCombat(bookNodeCombat);
			}
			//Si c'est un noeud à choix (noeud basic)
			else if(currentNode instanceof BookNodeWithChoices){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) currentNode;
				currentNode = execNodeWithChoices(bookNodeWithChoices);
			}
			//Si c'est un noeud aléatoire à choix (noeud random)
			else if(currentNode instanceof BookNodeWithRandomChoices){
				BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) currentNode;
				currentNode = execNodeWithRandomChoices(bookNodeWithRandomChoices);
			}
			//Si c'est un noeud terminal
			else if(currentNode instanceof BookNodeTerminal){
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) currentNode;
				execNodeTerminal(bookNodeTerminal);
				//Fin de partie
				gameFinish = true;
				
				if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY)
					win = true;
			} else {
				// Noeud inconnu ou possiblement null, on stop le jeu
				BookNodeTerminal nodeTerminal = new BookNodeTerminal();
				nodeTerminal.setText("Vous êtes morts...");
				nodeTerminal.setBookNodeStatus(BookNodeStatus.FAILURE);
				currentNode = nodeTerminal;
			}
		}
		
		return win;
	}
	
	/**
	 * Créer une nouvelle partie avec le personnage principal
	 * @return Nouvelle partie
	 */
	private BookState createNewState(){
		BookCharacter bookCharacter;
		BookState newState = new BookState();
		
		//Création d'un personnage si le personnage principal n'existe pas
		if(this.book.getMainCharacter() == null){
			bookCharacter = new BookCharacter("Test", "Personnage Test", 5, 150, null, null, null, 5, true);
			newState.setMainCharacter(bookCharacter);
		} 
		//Récupération du personnage principal
		else {
			BookCharacter bookCharacterMain = this.book.getMainCharacter();
			newState.setMainCharacter(new BookCharacter(bookCharacterMain));
		}
	
		showMessage("Votre personnage : ");
		showMessage(newState.getMainCharacter().getDescription(book));
		
		newState.setBook(this.book);
		// Lancement des différentes phases de la conception du personnage
		for(AbstractCharacterCreation characterCreation : this.book.getCharacterCreations())
			player.execPlayerCreation(book, characterCreation, newState);
		
		return newState;
	}
	
	/**
	 * Code commun aux noeuds à choix
	 * @param node Noeud actuel
	 * @return Un noeud terminal Failure si le personnage est mort ou null si il est encore en vie
	 */
	public AbstractBookNode execAbstractNodeWithChoices(AbstractBookNodeWithChoices node){
		showMessage(node.getText());
		//Regarde si il y a une perte/gain de point de vie dans le noeud
		execNodeHp(node);
		//Si le personnage n'est plus en vie
		if(!state.getMainCharacter().isAlive()){
			BookNodeTerminal nodeTerminal = new BookNodeTerminal();
			nodeTerminal.setText("Vous êtes morts...");
			nodeTerminal.setBookNodeStatus(BookNodeStatus.FAILURE);
			
			return nodeTerminal;
		}
		
		//Regarde si il y a des item à prendre dans le noeud
		if(!node.getItemLinks().isEmpty())
			chooseItems(node);
		
		return null;
	}
	
	/**
	 * Exécute un noeud basic
	 * @param node Noeud basic
	 * @return La prochaine destination
	 */
	public AbstractBookNode execNodeWithChoices(BookNodeWithChoices node){
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		if(returnedNode != null) 
			return returnedNode;		
		
		int lienValide = 0;
		//Regarde s'il y a au moins un choix où le personnage à accès en fonction des prérequis
		for (BookNodeLink bookNodeLink : node.getChoices()){
			if(bookNodeLink.isAvailable(state))
				lienValide += 1;
		}

		//Si il y a au moins un lien valide
		if (lienValide != 0){
			showMessage("Voici vos choix : ");
			
			//Affichage des choix
			int i = 1;
			for (BookNodeLink bookNodeLink : node.getChoices()){
				showMessage(i + " - "+bookNodeLink.getText());
				i++;
			}
			
			showMessage();
			
			boolean choixValide = false;
			BookNodeLink selectedBookNodeLink = null;
			while (!choixValide){
				showMessage("Que choisissez-vous ?");
				int choice = player.makeAChoice(node);
				if(choice > 0 && choice <= node.getChoices().size()){
					selectedBookNodeLink = node.getChoices().get(choice-1);
					//Vérification des prérequis pour aller vers ce choix
					if(selectedBookNodeLink.isAvailable(state)){
						choixValide = true;
					} else {
						//Affiche les prérequis
						for(List<AbstractRequirement> abstractRequirements : selectedBookNodeLink.getRequirements()) {
							for(AbstractRequirement requirement : abstractRequirements) {
								showMessage(requirement.getDescription(book));
							}
						}
					}
				} else {
					showMessage("vous ne pouvez pas effectuer ce choix");
				}
			}
			
			execBookNodeLink(selectedBookNodeLink);
			
			return book.getNodes().get(selectedBookNodeLink.getDestination());
		} 
		//Si aucun choix n'est valide, le player est automatiquement conduit vers un noeud terminal de défaite
		else {
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items / compétences /argent adéquats", BookNodeStatus.FAILURE);
			
			return bookNodeTerminalFail;
		}
	}
	
	/**
	 * Exécute un noeud terminal
	 * @param node Noeud terminal
	 */
	public void execNodeTerminal(BookNodeTerminal node){
		showMessage(node.getText());
		
		if(node.getBookNodeStatus() == BookNodeStatus.VICTORY)
			showMessage("Vous avez gagné");
		else
			showMessage("Vous avez perdu");
	}

	/**
	 * Exécute un noeud de combat
	 * @param node Noeud de combat
	 * @return La prochaine destination
	 */
	public AbstractBookNode execNodeCombat(BookNodeCombat node){
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		if(returnedNode != null) 
			return returnedNode;
		
		//S'il n'y a pas d'ennemis, le player est automatiquement envoyé vers le noeud de victoire.
		if(node.getEnnemiesId().isEmpty())
			return book.getNodes().get(node.getWinBookNodeLink().getDestination());
		
		int evasionRound = node.getEvasionRound();
		boolean finCombat = false;
		
		List<BookCharacter> listEnnemis = new ArrayList();
		showMessage("Il y a "+node.getEnnemiesId().size() + " ennemis !");
		//Liste des ennemis
		for(String ennemieNode : node.getEnnemiesId()){
			listEnnemis.add(new BookCharacter(book.getCharacters().get(ennemieNode)));
		}
		
		//Tant que le combat n'est pas terminé
		while(!finCombat){
			//Desciption des ennemis
			for(BookCharacter ennemi : listEnnemis)
				showMessage(ennemi.getDescription(book));
			
			//Choix du player
			ChoixCombat choixCombat = player.combatChoice(node, evasionRound, state);
			
			//Si le choix est Attaquer
			if (choixCombat == ChoixCombat.ATTAQUER) {
				//Choix de l'ennemi à attaquer
				BookCharacter ennemi = player.chooseEnnemi(listEnnemis);
				//Attaque du player
				attaque(ennemi);
				//Si l'ennemi n'a plus de vie
				if(!ennemi.isAlive()){
					showMessage(ennemi.getName() + " est mort");
					listEnnemis.remove(ennemi);
				} else {
					showMessage(ennemi.getName() + " a " +ennemi.getHp() + " hp");
				}
			} 
			//Si le choix est Evasion
			else if(choixCombat == ChoixCombat.EVASION) {
				//Vérification qu'il y a bien un noeud d'évasion et que le nombre de tour avant l'évasion est bien égale ou inférieur à zero
				if(evasionRound <= 0 && node.getEvasionBookNodeLink() != null) {
					execBookNodeLink(node.getEvasionBookNodeLink());
					return book.getNodes().get(node.getEvasionBookNodeLink().getDestination());
				}
				else
					showMessage("vous ne pouvez pas encore vous evader");
			}
			
			//Tour des ennemis
			ennemiTour(listEnnemis);
			
			//Si le player n'a plus de vie, fin du combat
			if(!state.getMainCharacter().isAlive()) {
				//Vérification d'un noeud Failure
				if(node.getLooseBookNodeLink() != null)  {
					execBookNodeLink(node.getLooseBookNodeLink());
					return book.getNodes().get(node.getLooseBookNodeLink().getDestination());
				} else 
					return new BookNodeTerminal("Vous succombez à vos blessures", BookNodeStatus.FAILURE);
			}
			
			//Si il n'y a plus d'ennemis, fin de combat
			if(listEnnemis.isEmpty())
				finCombat = true;
			
			evasionRound -= 1;
		}
		
		//Lien vers le noeud de victoire
		execBookNodeLink(node.getWinBookNodeLink());
		return book.getNodes().get(node.getWinBookNodeLink().getDestination());
	}

	/**
	 * Donne le nombre de dommage infligé
	 * @param attaquant Le player ou l'ennemi
	 * @param weapon Item arme de l'attaquant actuel
	 * @param defenseEnnemie Item defense de l'attaqué
	 * @return Totalité des dommages infligés
	 */
	private int getDamageAmount(BookCharacter attaquant, BookItemWeapon weapon, BookItemDefense defenseEnnemie) {
		int attaque = 0;
		//Si l'attaquant a une arme
		if(weapon != null) {
			//Ajout de point de dommage
			attaque = weapon.getDamage();
			
			//Diminution de la durabilité de l'objet
			/*bookItemArme.setDurability(bookItemArme.getDurability()-1);
			if(bookItemArme.getDurability()<=0){
				state.getMainCharacter().getItems().remove(bookItemArme.getId());
				System.out.println("La durabilité de l'arme "+bookItemArme.getName()+"est arrivé à terme");
				System.out.println("Arme détruite");
			}
			mapBookItem.put(bookItemArme.getId(), bookItemArme);*/
		}
		
		Random random = new Random();
		int r = random.nextInt(5);
		int damageMultiplicator = 1;
		//Random sur le double dommage
		if(attaquant.isDoubleDamage() && r > 2){
			showMessage("Double dommage!");
			damageMultiplicator = 2;
		}

		r = random.nextInt(5);
		//Random sur un coup critique
		if (r > 3){
			showMessage("Coup critique !");
			damageMultiplicator += 0.25;
		}
		
		int resistance = 0;
		//Si l'attaqué a un item de defense
		if(defenseEnnemie != null) {
			//Ajout de point de défense
			resistance = defenseEnnemie.getResistance();
			
			//Diminution de la durabilité de l'objet
			/*bookItemDefense.setDurability(bookItemDefense.getDurability()-1);
			if(bookItemDefense.getDurability()<=0){
				state.getMainCharacter().getItems().remove(bookItemDefense.getId());
				System.out.println("La durabilité de l'arme "+bookItemDefense.getName()+"est arrivé à terme");
				System.out.println("Arme détruite");
			}
			mapBookItem.put(bookItemDefense.getId(), bookItemDefense);
			bookItemDefense = null;*/
		}
		
		return (attaquant.getBaseDamage() + attaque) * damageMultiplicator - resistance;
	}
	
	/**
	 * Attaque du player
	 * @param ennemi Personnage ennemi
	 */
	private void attaque(BookCharacter ennemi){
		int damageInt = getDamageAmount(state.getMainCharacter(), state.getBookItemArme(), null);
		ennemi.damage(damageInt);
		showMessage(ennemi +" a perdu "+ damageInt + " hp");
	}
	
	/**
	 * Attaque de(s) l'ennemi(s)
	 * @param listEnnemis Personnage(s) ennemi(s)
	 */
	private void ennemiTour(List<BookCharacter> listEnnemis){
		for(BookCharacter ennemi : listEnnemis){
			state.getMainCharacter().damage(getDamageAmount(ennemi, null, state.getBookItemDefense()));
			
			if (showMessages)
				showMessage(ennemi + " a attaquer, il vous reste" + state.getMainCharacter().getHp()+" hp");
			if(!state.getMainCharacter().isAlive()) {
				return;
			}
			
		}
	}

	/**
	 * Exécute un noeud random
	 * @param node Noeud aléatoire
	 * @return La prochaine destination
	 */
	public AbstractBookNode execNodeWithRandomChoices(BookNodeWithRandomChoices node) {
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		
		if(returnedNode != null) 
			return returnedNode;
		
		//Choix du noeud
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		
		//Si le choix est null
		if(randomChoices == null) {
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Dommage.. Vous êtes mort", BookNodeStatus.FAILURE);
			return bookNodeTerminalFail;
		}
		
		//Vérification de gain/perte d'argent et/ou de vie sur le lien entre le noeud et la destination
		execBookNodeLink(randomChoices);
		return book.getNodes().get(randomChoices.getDestination());
	}
	
	/**
	 * Modifie le nombre de point de vie du player si le noeud en enlève ou en donne
	 * @param node Noeud actuel
	 */
	private void execNodeHp(AbstractBookNodeWithChoices node){
		int nodeHp = node.getHp();
		//Si le noeud fait perdre/gagner de la vie
		if(nodeHp != 0){
			if(nodeHp > 0)
				state.getMainCharacter().heal(nodeHp);
			else
				state.getMainCharacter().damage(-nodeHp);
			showMessage("Vous avez pris "+ nodeHp + " hp");

			if(state.getMainCharacter().getHpMax() == state.getMainCharacter().getHp())
				showMessage("Vos HP sont au max");
			else
				showMessage("Vos hp : "+ state.getMainCharacter().getHp());
		}
	}
	
	/**
	 * Permet de choisir les items à prendre dans le noeud
	 * @param node Noeud actuel
	 */
	private void chooseItems(AbstractBookNodeWithChoices node){
		List<BookItemLink> nodeItems = new ArrayList<>();
		for(BookItemLink bookItemLink : (List<BookItemLink>) node.getItemLinks()) {
			nodeItems.add(new BookItemLink(bookItemLink));
		}
		
		//Si le noeud à des items disponible
		if (!node.getItemLinks().isEmpty()){			
			player.prendreItems(state, nodeItems, node.getNbItemsAPrendre());
		}
	}
	
	/**
	 * Modifie la quantité d'argent et le nombre de point de vie du player si le choix en enlève ou en donne
	 * @param node Choix actuel
	 */
	private void execBookNodeLink(BookNodeLink bookNodeLink){
		//Si le lien fait gagner/perdre de l'argent
		if(bookNodeLink.getGold() != 0){
			state.getMainCharacter().changeMoneyAmount("gold", state.getMainCharacter().getMoney("gold")+bookNodeLink.getGold());
			showMessage("Vous avez pris "+ bookNodeLink.getGold() +" gold");
			showMessage("Vous avez"+ state.getMainCharacter().getMoney("gold") + " gold");
		}
		
		//Si le lien fait gagner/perdre de la vie
		if(bookNodeLink.getHp() != 0){
			if(bookNodeLink.getHp() < 0)
				state.getMainCharacter().damage(-bookNodeLink.getHp());
			else
				state.getMainCharacter().heal(bookNodeLink.getHp());
			showMessage("Vous avez pris "+ bookNodeLink.getHp() + " hp");
			showMessage("Vous avez"+ state.getMainCharacter().getHp() + " hp");
		}
	}
	
	/**
	 * Affiche un saut de ligne si on doit afficher le messages
	 */
	private void showMessage() {
		showMessage("");
	}
	
	/**
	 * Affiche le message si on doit le faire
	 * @param str message à afficher
	 */
	private void showMessage(String str) {
		if(showMessages)
			System.out.println(str);
	}

}

