package magic_book.core.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import magic_book.core.Book;
import magic_book.core.file.deserializer.BookNodeTypeDeserializer;

import magic_book.core.file.json.BookJson;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.ItemJson;
import magic_book.core.file.json.SectionJson;
import magic_book.core.item.BookItem;
import magic_book.core.node.BookNode;
import magic_book.core.node.BookNodeLink;
import magic_book.core.node.BookNodeType;

public class BookReader {
	
	public static Book read(String path) throws FileNotFoundException {		
		BookJson bookJson = readFileWithGson(path);
		
		HashMap<Integer, BookNode> nodes = getEveryNodes(bookJson);
		List<BookItem> items = getEveryItems(bookJson);
		
		return new Book(nodes, items, null);
	}

	private static BookJson readFileWithGson(String path) throws FileNotFoundException {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(BookNodeType.class, new BookNodeTypeDeserializer());
		
		Gson gson = builder.create(); 
		BufferedReader bufferedReader = new BufferedReader(new FileReader(path)); 
		
		return gson.fromJson(bufferedReader, BookJson.class); 
	}
	
	private static HashMap<Integer, BookNode> getEveryNodes(BookJson bookJson) {
		HashMap<Integer, BookNode> nodes = new HashMap<>();
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			BookNode node = new BookNode(sectionJson.getText(), sectionJson.getType(), null);
			
			nodes.put(entry.getKey(), node);
		}
		
		return linkEveryNodes(bookJson, nodes);
	}
	
	private static HashMap<Integer, BookNode> linkEveryNodes(BookJson bookJson, HashMap<Integer, BookNode> nodes) {
		for(Map.Entry<Integer, SectionJson> entry : bookJson.getSections().entrySet()) {
			SectionJson sectionJson = entry.getValue();
			
			for(ChoiceJson choiceJson : sectionJson.getChoices()) {
				BookNodeLink nodeLink = new BookNodeLink(choiceJson.getText(), nodes.get(choiceJson.getSection()));
				nodes.get(entry.getKey()).getChoices().add(nodeLink);
			}
		}
		
		return nodes;
	}

	private static List<BookItem> getEveryItems(BookJson bookJson) {
		List<BookItem> items = new ArrayList<>();
		
		for(ItemJson i : bookJson.getSetup().getEquipment()) {
			BookItem item = new BookItem(i.getId(), i.getName());
			items.add(item);
		}
			
		return items;
	}
	
}
