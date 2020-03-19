package magic_book.core.file;

import magic_book.core.parser.TextParser;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import magic_book.core.game.BookCharacter;
import magic_book.core.item.BookItem;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;

public class BookTextExporter {

	public static void generateBook(AbstractBookNode rootNode, List<BookItem> items, List<BookCharacter> characters, String path) throws IOException {
		HashMap<AbstractBookNode, Integer> nodes = exploreNode(rootNode);
		nodes = shuffle(nodes);		
		
		HashMap<Integer, AbstractBookNode> nodesInv = new HashMap<>();
		for(Map.Entry<AbstractBookNode, Integer> entry : nodes.entrySet()){
			nodesInv.put(entry.getValue(), entry.getKey());
		}
		
		FileWriter fileWritter = new FileWriter(path);

		for(AbstractBookNode n : nodesInv.values())
			write(n, items, characters, nodes, fileWritter);
		
		fileWritter.close();
	}
	
	private static HashMap<AbstractBookNode, Integer> exploreNode(AbstractBookNode node) {
		return exploreNode(node, new HashMap<>());
	}
	
	private static HashMap<AbstractBookNode, Integer> exploreNode(AbstractBookNode node, HashMap<AbstractBookNode, Integer> nodes) {
		if(node == null)
			return null;
		
		nodes.put(node, nodes.size()+1);
				
		for(BookNodeLink nodeLink : node.getChoices()) {
			AbstractBookNode nodeLinkDestination = nodeLink.getDestination();
			if(nodes.containsKey(nodeLinkDestination))
				continue;
			
			exploreNode(nodeLinkDestination, nodes);
		}
		
		return nodes;
	}
	
	private static HashMap<AbstractBookNode, Integer> shuffle(HashMap<AbstractBookNode, Integer> nodes) {
		HashMap<AbstractBookNode, Integer> shuffle = new HashMap<>();
		List<Integer> usedNumber = new ArrayList<>();
		
		for(int i = 1 ; i < nodes.size() ; i++) {
			usedNumber.add(i);
		}
		
		Random rand = new Random();
		for(Map.Entry<AbstractBookNode, Integer> entry : nodes.entrySet()) {
			if(entry.getValue() == 1) {
				shuffle.put(entry.getKey(), 1);
				continue;
			}
			
			int index = rand.nextInt(usedNumber.size());
			shuffle.put(entry.getKey(), usedNumber.get(index)+1);
			usedNumber.remove(index);
		}
		
		return shuffle;
	}

	private static void write(AbstractBookNode node, List<BookItem> items, List<BookCharacter> characters, HashMap<AbstractBookNode, Integer> nodes, FileWriter fileWritter) throws IOException {
		fileWritter.write("Paragraphe " + nodes.get(node) + " :\n");
		fileWritter.write("\n");
		
		fileWritter.write(TextParser.parseText(node.getText(), items, characters)+"\n");

		if(node instanceof BookNodeTerminal) {
			BookNodeTerminal nodeTerminal = (BookNodeTerminal) node;
			if(nodeTerminal.getBookNodeStatus() == BookNodeStatus.FAILURE) {
				fileWritter.write("\n");
				fileWritter.write("Vous avez perdu\n");
			}

			if(nodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY) {
				fileWritter.write("\n");
				fileWritter.write("Félicitation vous avez gagné\n");
			}
		}
		
		if(!node.getChoices().isEmpty()) {
			fileWritter.write("\n");
			fileWritter.write("Que souhaitez vous faire ?\n");
		}
		
		for(BookNodeLink nl : node.getChoices()) {
			fileWritter.write("- " + TextParser.parseText(nl.getText(), items, characters) + " - Paragraphe suivant : " + nodes.get(nl.getDestination()) + "\n");
		}
		
		fileWritter.write("\n");
		fileWritter.write("==================================================\n");
		fileWritter.write("\n");
	}

}
