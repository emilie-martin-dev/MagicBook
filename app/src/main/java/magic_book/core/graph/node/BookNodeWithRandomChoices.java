package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.game.BookState;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLinkRandom;

public class BookNodeWithRandomChoices extends AbstractBookNodeWithChoices<BookNodeLinkRandom>{
	
	public BookNodeWithRandomChoices() {
		this("");
	}
	
	public BookNodeWithRandomChoices(String text) {
		this(text, 0, null, null, null);
	}
	
	public BookNodeWithRandomChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<BookNodeLinkRandom> choices) {
		super(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices);
	}
	
	public BookNodeLinkRandom getRandomChoices(BookState state){
		System.out.println("Rentre dans le get ");
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
			/*BookNodeTerminal bookNodeTerminalFail = new BookNodeTerminal("Dommage.. Vous êtes mort", BookNodeStatus.FAILURE);
			BookNodeLinkRandom bookNodeLinkTerminal = new BookNodeLinkRandom("C'est la voie de la raison", bookNodeTerminalFail, null, 0);
			*/
			return null;
		} else {
			Random random = new Random();
			int nbrRandomChoice = random.nextInt(somme);
			int n = 0;
			for (int i = 0 ; i < listNodeLinkDisponible.size() ; i++){
				nbrRandomChoice = this.getChoices().get(i).getChance();
				if(nbrRandomChoice <= 0){
					nbrChoice = i;
					break;
				}
			}
		}
		System.out.println("BYE BYE");
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
