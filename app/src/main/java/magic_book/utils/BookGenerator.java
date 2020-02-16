package magic_book.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeType;
import magic_book.core.utils.Parsable;
import magic_book.core.utils.TextParser;

public class BookGenerator {

	public static void generateBook(BookNode rootNode, List<Parsable> items, List<Parsable> characters, String path) throws IOException {
		HashMap<BookNode, Integer> nodes = exploreNode(rootNode);
		
		FileWriter fileWritter = new FileWriter(path);
		
		write(rootNode, items, characters, nodes, fileWritter);
		nodes.remove(rootNode);
			
		for(BookNode n : nodes.keySet())
			write(n, items, characters, nodes, fileWritter);
		
		fileWritter.close();
	}
	
	private static HashMap<BookNode, Integer> exploreNode(BookNode node) {
		return exploreNode(node, new HashMap<>());
	}
	
	private static HashMap<BookNode, Integer> exploreNode(BookNode node, HashMap<BookNode, Integer> nodes) {
		if(node == null)
			return null;
		
		nodes.put(node, nodes.size()+1);
				
		for(BookNodeLink nodeLink : node.getChoices()) {
			BookNode nodeLinkDestination = nodeLink.getDestination();
			if(nodes.containsKey(nodeLinkDestination))
				continue;
			
			exploreNode(nodeLinkDestination, nodes);
		}
		
		return nodes;
	}

	private static void write(BookNode node, List<Parsable> items, List<Parsable> characters, HashMap<BookNode, Integer> nodes, FileWriter fileWritter) throws IOException {
		fileWritter.write("Paragraphe " + nodes.get(node) + " :\n");
		fileWritter.write("\n");
		
		fileWritter.write(TextParser.parseText(node.getText(), items, characters)+"\n");

		if(node.getNodeType() == BookNodeType.FAILURE) {
			fileWritter.write("\n");
			fileWritter.write("Vous avez perdu\n");
		}
		
		if(node.getNodeType() == BookNodeType.VICTORY) {
			fileWritter.write("\n");
			fileWritter.write("Félicitation vous avez gagné\n");
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
