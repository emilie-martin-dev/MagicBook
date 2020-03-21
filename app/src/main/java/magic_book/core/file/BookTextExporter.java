package magic_book.core.file;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import magic_book.core.Book;

import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.graph.node.BookNodeStatus;
import magic_book.core.graph.node.BookNodeTerminal;

public class BookTextExporter {

	public static void generateBook(Book book, String path) throws IOException {
		HashMap<Integer, AbstractBookNode> nodes = book.getNodes();
		nodes = shuffle(nodes);
		
		HashMap<AbstractBookNode, Integer> nodesInv = new HashMap<>();
		for(Map.Entry<Integer, AbstractBookNode> entry : nodes.entrySet()){
			nodesInv.put(entry.getValue(), entry.getKey());
		}
		
		FileWriter fileWritter = new FileWriter(path);
		fileWritter.write(book.getTextPrelude());
		fileWritter.write("\n");
		
		fileWritter.write("\n");
		fileWritter.write("==================================================\n");
		fileWritter.write("\n");
		
		for(int i = 0 ; i < book.getCharacterCreations().size() ; i++) {
			fileWritter.write(book.getCharacterCreations().get(i).getTextForBookText(book, nodesInv));
			if(i < book.getCharacterCreations().size() - 1)
				fileWritter.write("\n");
		}
		
		fileWritter.write("\n");
		fileWritter.write("==================================================\n");
		fileWritter.write("\n");
			
		for(AbstractBookNode node : nodes.values())
			write(node, nodesInv, book, fileWritter);
		
		fileWritter.close();
	}
	
	private static HashMap<Integer, AbstractBookNode> shuffle(HashMap<Integer, AbstractBookNode> nodes) {
		HashMap<Integer, AbstractBookNode> shuffle = new HashMap<>();
		List<Integer> usedNumber = new ArrayList<>();
		
		for(int i = 1 ; i < nodes.size() ; i++) {
			usedNumber.add(i);
		}
		
		List<AbstractBookNode> postNodes = new ArrayList();
		
		Random rand = new Random();
		for(Map.Entry<Integer, AbstractBookNode> entry : nodes.entrySet()) {
			if(entry.getKey() == 1) {
				shuffle.put(1, entry.getValue());
				continue;
			} else if(entry.getValue() instanceof BookNodeTerminal) {
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) entry.getValue();
				if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY) {
					shuffle.put(usedNumber.get(usedNumber.size() - 1) + 1, bookNodeTerminal);
					usedNumber.remove(usedNumber.size() - 1);
					continue;
				}
			}
			
			postNodes.add(entry.getValue());
		}
		
		for(AbstractBookNode bookNode : postNodes) {
			int index = rand.nextInt(usedNumber.size());
			shuffle.put(usedNumber.get(index)+1, bookNode);
			usedNumber.remove(index);
		}
		
		return shuffle;
	}

	private static void write(AbstractBookNode node, HashMap<AbstractBookNode, Integer> nodesIndex, Book book, FileWriter fileWritter) throws IOException {
		fileWritter.write("Paragraphe " + nodesIndex.get(node) + " :\n");
		fileWritter.write("\n");
		
		fileWritter.write(node.getTextForBookText(book, nodesIndex));
		
		fileWritter.write("\n");
		fileWritter.write("==================================================\n");
		fileWritter.write("\n");
	}

}
