package magic_book.core.game.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import magic_book.core.Book;
import magic_book.core.game.BookCharacter;
import magic_book.core.game.BookState;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node.BookNodeWithChoices;
import magic_book.core.graph.node.BookNodeWithRandomChoices;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItem;
import magic_book.core.item.BookItemDefense;
import magic_book.core.item.BookItemLink;
import magic_book.core.item.BookItemWeapon;
import magic_book.core.requirement.AbstractRequirement;

public class Jeu {
	
	public enum ChoixCombat {
		ATTAQUER, INVENTAIRE, EVASION;
	}
	
	private BookState state;
	private InterfacePlayerFourmis player;
	private Book book;
	private boolean showMessages;
	
	public Jeu(Book book){
		this.book = book;
	}
	
	public void play(){
		player = new Player();
		
		showMessages = true;
		
		runGame();
	}
	
	public float fourmis(int nbrFourmis){
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
	
	private boolean runGame() {
		boolean gameFinish = false;
		boolean win = false;
		
		showMessage(this.book.getTextPrelude());
		this.state = createNewState();
		
		AbstractBookNode currentNode = this.book.getRootNode();
		
		while(!gameFinish){
			if(currentNode instanceof BookNodeCombat){
				BookNodeCombat bookNodeCombat = (BookNodeCombat) currentNode;
				currentNode = execNodeCombat(bookNodeCombat);
			}
			else if(currentNode instanceof BookNodeWithChoices){
				BookNodeWithChoices bookNodeWithChoices = (BookNodeWithChoices) currentNode;
				currentNode = execNodeWithChoices(bookNodeWithChoices);
			}
			else if(currentNode instanceof BookNodeWithRandomChoices){
				BookNodeWithRandomChoices bookNodeWithRandomChoices = (BookNodeWithRandomChoices) currentNode;
				currentNode = execNodeWithRandomChoices(bookNodeWithRandomChoices);
			}
			else if(currentNode instanceof BookNodeTerminal){
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) currentNode;
				execNodeTerminal(bookNodeTerminal);
				
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
	
	private BookState createNewState(){
		BookCharacter bookCharacter;
		BookState newState = new BookState();
		if(this.book.getCharacters().get("0") == null){
			bookCharacter = player.execPlayerCreation(this.book);
			newState.setMainCharacter(bookCharacter);
			System.out.println("1");
		} else {
			bookCharacter = this.book.getCharacters().get("0");
			newState.setMainCharacter(bookCharacter);
			System.out.println("2"+this.book.getCharacters());
		}
		
		System.out.println("newState "+newState.getMainCharacter());
		newState.setBook(this.book);
		return newState;
	}
	
	public AbstractBookNode execAbstractNodeWithChoices(AbstractBookNodeWithChoices node){
		showMessage(node.getText());
		
		execNodeHp(node);
		if(!state.getMainCharacter().isAlive()){
			BookNodeTerminal nodeTerminal = new BookNodeTerminal();
			nodeTerminal.setText("Vous êtes morts...");
			nodeTerminal.setBookNodeStatus(BookNodeStatus.FAILURE);

			return nodeTerminal;
		}
		
		if(!node.getItemLinks().isEmpty())
			chooseItems(node);
		
		return null;
	}
	
	public AbstractBookNode execNodeWithChoices(BookNodeWithChoices node){
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		
		if(returnedNode != null) 
			return returnedNode;		
		
		int lienValide = 0;
		for (BookNodeLink bookNodeLink : node.getChoices()){
			if(bookNodeLink.isAvailable(state))
				lienValide += 1;
		}

		if (lienValide != 0){
			showMessage("Voici vos choix : ");
			
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
				System.out.println(choice);
				if(choice > 0 && choice <= node.getChoices().size()){
					selectedBookNodeLink = node.getChoices().get(choice-1);
					if(selectedBookNodeLink.isAvailable(state)){
						choixValide = true;
					} else {
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
		} else {
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Vous ne pouvez plus continuer, vous n'avez pas les items / compétences /argent adéquats", BookNodeStatus.FAILURE);
			return bookNodeTerminalFail;
		}
	}
	
	public void execNodeTerminal(BookNodeTerminal node){
		showMessage(node.getText());
		
		if(node.getBookNodeStatus() == BookNodeStatus.VICTORY)
			showMessage("Vous avez gagné");
		else
			showMessage("Vous avez perdu");
	}

	public AbstractBookNode execNodeCombat(BookNodeCombat node){
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		
		if(returnedNode != null) 
			return returnedNode;
		
		
		System.out.println("excecNodeCombat "+ node.getEnnemiesId());
		
		if(node.getEnnemiesId().isEmpty())
			return book.getNodes().get(node.getWinBookNodeLink().getDestination());
		
		int evasionRound = node.getEvasionRound();
		boolean finCombat = false;
		
		List<BookCharacter> listEnnemis = new ArrayList();
		showMessage("Il y a "+node.getEnnemiesId().size() + " ennemies !");
		
		for(String ennemieNode : node.getEnnemiesId()){
			listEnnemis.add(new BookCharacter(book.getCharacters().get(ennemieNode)));
		}
		
		while(!finCombat){
			for(BookCharacter ennemi : listEnnemis)
				showMessage(ennemi.getDescription(book));
			
			ChoixCombat choixCombat = player.combatChoice(node, evasionRound, state);
			
			if (choixCombat == ChoixCombat.ATTAQUER) {
				System.out.println("ATTAQUE " +listEnnemis);
				BookCharacter ennemi = player.chooseEnnemi(listEnnemis);
				attaque(ennemi);
				
				if(ennemi.isAlive()){
					showMessage(ennemi.getName() + " est mort");
					listEnnemis.remove(ennemi);
					System.out.println("Mort " +listEnnemis);
				} else {
					showMessage(ennemi.getName() + " a " +ennemi.getHp() + " hp");
				}
			} else if(choixCombat == ChoixCombat.EVASION) {
				if(evasionRound <= 0 && node.getEvasionBookNodeLink() != null) {
					execBookNodeLink(node.getEvasionBookNodeLink());
					return book.getNodes().get(node.getEvasionBookNodeLink().getDestination());
				}
				else
					showMessage("vous ne pouvez pas encore vous evader");
			}
			
			ennemiTour(listEnnemis);
			
			if(!state.getMainCharacter().isAlive()) {
				if(node.getLooseBookNodeLink() != null)  {
					execBookNodeLink(node.getLooseBookNodeLink());
					return book.getNodes().get(node.getLooseBookNodeLink().getDestination());
				} else 
					return new BookNodeTerminal("Vous succombez à vos blessures", BookNodeStatus.FAILURE);
			}
			
			if(listEnnemis.isEmpty())
				finCombat = true;
			
			evasionRound -= 1;
		}

		execBookNodeLink(node.getWinBookNodeLink());
		return book.getNodes().get(node.getWinBookNodeLink().getDestination());
	}

	private int getDamageAmount(BookCharacter attaquant, BookItemWeapon weapon, BookItemDefense bookItemDefense) {
		int attaque = 0;
		if(weapon != null) {
			attaque = weapon.getDamage();
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
		if(attaquant.isDoubleDamage() && r > 2){
			showMessage("Double domage !");
			damageMultiplicator = 2;
		}

		r = random.nextInt(5);
		if (r > 3){
			showMessage("Coup critique !");
			damageMultiplicator += 0.25;
		}
		
		int resistance = 0;
		if(bookItemDefense != null) {
			resistance = bookItemDefense.getResistance();
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
	
	private void attaque(BookCharacter ennemi){
		System.out.println("Avant Mort " +ennemi.getHp());
		ennemi.damage(getDamageAmount(state.getMainCharacter(), state.getBookItemArme(), null));
		System.out.println("Mort " +ennemi.getHp());
	}
	
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

	public AbstractBookNode execNodeWithRandomChoices(BookNodeWithRandomChoices node) {
		AbstractBookNode returnedNode = execAbstractNodeWithChoices(node);
		
		if(returnedNode != null) 
			return returnedNode;
		
		BookNodeLinkRandom randomChoices = node.getRandomChoices(state);
		
		if(randomChoices == null) {
			BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Dommage.. Vous êtes mort", BookNodeStatus.FAILURE);
			return bookNodeTerminalFail;
		}
		
		execBookNodeLink(randomChoices);
		return book.getNodes().get(randomChoices.getDestination());
	}
	
	private void execNodeHp(AbstractBookNodeWithChoices node){
		int nodeHp = node.getHp();
		
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
	
	private void chooseItems(AbstractBookNodeWithChoices node){
		List<BookItemLink> nodeItems = new ArrayList<>();
		for(BookItemLink bookItemLink : (List<BookItemLink>) node.getItemLinks()) {
			nodeItems.add(new BookItemLink(bookItemLink));
		}
		
		if (!node.getItemLinks().isEmpty()){			
			player.prendreItems(state, nodeItems, node.getNbItemsAPrendre());
		}
	}
	
	private void execBookNodeLink(BookNodeLink bookNodeLink){
		if(bookNodeLink.getGold() != 0){
			BookItem bookItem = book.getItems().get("gold");
			state.getMainCharacter().changeMoneyAmount(bookItem.getId(), bookNodeLink.getGold());
			showMessage("Vous avez pris "+ bookNodeLink.getGold() +" "+ bookItem.getName());
		}
		
		if(bookNodeLink.getHp() != 0){
			state.getMainCharacter().setHp(bookNodeLink.getHp());
			showMessage("Vous avez pris "+ bookNodeLink.getHp() + " hp");
		}
	}
	
	private void showMessage() {
		showMessage("");
	}
	
	private void showMessage(String str) {
		if(showMessages)
			System.out.println(str);
	}

}

