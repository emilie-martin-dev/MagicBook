package magic_book.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import magic_book.core.game.BookCharacter;
import magic_book.core.graph.node.AbstractBookNodeWithChoices;
import magic_book.core.graph.node.BookNodeCombat;
import magic_book.core.graph.node.BookNodeTerminal;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;
import magic_book.core.item.BookItem;

public class BookValidator {

	public static boolean isCharacterValid(BookCharacter character) {
		if(!isIdValid(character.getId())) {
			return false;
		}
		
		if(character.getName() == null) {
			return false;
		}
		
		if(character.getName().trim().isEmpty()) {
			return false;
		}
		
		if(character.getBaseDamage() < 0) {
			return false;
		}
		
		if(character.getHpMax() < 0) {
			return false;
		}
		
		if(character.getItemsMax() < 0) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isItemValid(BookItem item) {
		if(!isIdValid(item.getId())) {
			return false;
		}
		
		if(item.getName() == null) {
			return false;
		}
		
		if(item.getName().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isNodeCombatValid(BookNodeCombat bookNodeCombat) {
		if(!isNodeValid(bookNodeCombat)) {
			return false;
		} 
		
		if(bookNodeCombat.getWinBookNodeLink() == null) {
			return false;
		}
		
		if(bookNodeCombat.getEvasionRound() < 0) {
			return false;
		}
		
		if(bookNodeCombat.getEvasionRound() > 0 && bookNodeCombat.getEvasionBookNodeLink() == null) {
			return false;
		}
		
		if(bookNodeCombat.getEnnemiesId().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isNodeTerminalValid(BookNodeTerminal bookNodeTerminal) {
		if(bookNodeTerminal.getBookNodeStatus() == null) {
			return false;
		}
		
		if(bookNodeTerminal.getText() == null) {
			return false;
		}
		
		if(bookNodeTerminal.getText().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isNodeValid(AbstractBookNodeWithChoices bookNodeWithChoices) {
		if(bookNodeWithChoices.getNbItemsAPrendre() < 0) {
			return false;
		}
		
		if(bookNodeWithChoices.getText() == null) {
			return false;
		}
		
		if(bookNodeWithChoices.getText().isEmpty()) {
			return false;
		}
		
		if(bookNodeWithChoices.getChoices().isEmpty()) {
			return false;
		}
	
		return true;
	}
	
	public static boolean isNodeLinkValid(BookNodeLink bookNodeLink) {
		if(bookNodeLink.getText() == null) {
			return false;
		}
		
		if(bookNodeLink.getText().trim().isEmpty()) {
			return false;
		}
		
		if(bookNodeLink.getDestination() == -1) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isNodeLinkRandomValid(BookNodeLinkRandom bookNodeLinkRandom) {
		if(!isNodeLinkValid(bookNodeLinkRandom)) {
			return false;
		}
		
		if(bookNodeLinkRandom.getChance() <= 0) {
			return false;
		}
		
		return true;
	}
	
	public static boolean isIdValid(String id) {
		if(id == null) {
			return false;
		}
		
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_-]{2,}");
		Matcher matcher = pattern.matcher(id);

		return matcher.matches();
	}
	
}
