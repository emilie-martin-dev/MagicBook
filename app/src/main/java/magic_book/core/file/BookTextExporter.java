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
import magic_book.core.graph.node_link.BookNodeLink;
import magic_book.core.parser.TextParser;

/**
 * Exporte le livre au format texte
 */
public class BookTextExporter {

	/**
	 * Permet de générer le livre au format texte
	 * @param book Le à écrire au format texte
	 * @param path Le chemin où écrire le livre
	 * @throws IOException Exception en cas d'erreur IO
	 */
	public static void generateBook(Book book, String path) throws IOException {
		HashMap<Integer, AbstractBookNode> nodes = book.getNodes();
		nodes = shuffle(nodes);

		// On construit une HashMap inverse afin de pouvoir facilement retrouver le numéro d'un noeud
		HashMap<AbstractBookNode, Integer> nodesInv = new HashMap<>();
		for(Map.Entry<Integer, AbstractBookNode> entry : nodes.entrySet()){
			nodesInv.put(entry.getValue(), entry.getKey());
		}

		FileWriter fileWritter = new FileWriter(path);
		fileWritter.write(TextParser.parseText(book.getTextPrelude(), book.getItems(), book.getCharacters()));
		fileWritter.write("\n");

		writeSeparator(fileWritter);

		writeCharacterCreation(book, fileWritter);

		writeSeparator(fileWritter);

		for(int i = 0 ; i < nodes.size() ; i++) {
			writeNode(nodes.get(i+1), nodesInv, book, fileWritter);

			if(i < nodes.size() - 1){
				writeSeparator(fileWritter);
			}
		}

		fileWritter.close();
	}

	/**
	 * Permet de mélanger l'ordre des noeuds du livre dans une nouvelle map
	 * @param nodes La liste des noeuds originaux
	 * @return Une nouvelle liste avec des noeuds mélangés
	 */
	private static HashMap<Integer, AbstractBookNode> shuffle(HashMap<Integer, AbstractBookNode> nodes) {
		HashMap<Integer, AbstractBookNode> shuffle = new HashMap<>();
		List<Integer> leftNumber = new ArrayList<>();

		// Créé la liste des numéro libre
		for(int i = 1 ; i < nodes.size() ; i++) {
			leftNumber.add(i);
		}

		List<AbstractBookNode> postNodes = new ArrayList();

		Random rand = new Random();
		for(Map.Entry<Integer, AbstractBookNode> entry : nodes.entrySet()) {
			// S'il s'agit du premier noeud, on le place en premier
			if(entry.getKey() == 1) {
				shuffle.put(1, entry.getValue());
				continue;
			// S'il s'agit d'un noeud terminal, on le place à la fin
			} else if(entry.getValue() instanceof BookNodeTerminal) {
				BookNodeTerminal bookNodeTerminal = (BookNodeTerminal) entry.getValue();
				if(bookNodeTerminal.getBookNodeStatus() == BookNodeStatus.VICTORY) {
					shuffle.put(leftNumber.get(leftNumber.size() - 1) + 1, bookNodeTerminal);
					leftNumber.remove(leftNumber.size() - 1);
					continue;
				}
			}

			postNodes.add(entry.getValue());
		}

		// On peut placer le reste des noeuds de manière aléatoire
		for(AbstractBookNode bookNode : postNodes) {
			int index = rand.nextInt(leftNumber.size());
			shuffle.put(leftNumber.get(index)+1, bookNode);
			leftNumber.remove(index);
		}

		return shuffle;
	}

	/**
	 * Permet d'écrire une séparation entre les parties du livre ainsi que les paragraphes
	 * @param fileWritter Le flux dans lequel écrire
	 * @throws IOException Exception en cas d'erreur IO
	 */
	private static void writeSeparator(FileWriter fileWritter) throws IOException {
		fileWritter.write("\n");
		fileWritter.write("==================================================\n");
		fileWritter.write("\n");
	}

	/**
	 * Permet d'écrire les différentes étapes de la création du personnage
	 * @param book Le livre à exporter au format texte
	 * @param fileWritter Le flux dans lequel écrire
	 * @throws IOException Exception en cas d'erreur IO
	 */
	private static void writeCharacterCreation(Book book, FileWriter fileWritter) throws IOException {
		for(int i = 0 ; i < book.getCharacterCreations().size() ; i++) {
			fileWritter.write(book.getCharacterCreations().get(i).getDescription(book));

			if(i < book.getCharacterCreations().size() - 1)
				writeSeparator(fileWritter);
		}
	}

	/**
	 *
	 * @param node Le noeud à écrire
	 * @param nodesIndex Une liste qui associe à un noeud son numéro de paragraphe. Permet de retrouver facilement la destination d'un choix
	 * @param book Le livre à exporter au format texte
	 * @param fileWritter Le flux dans lequel écrire
	 * @throws IOException Exception en cas d'erreur IO
	 */
	private static void writeNode(AbstractBookNode node, HashMap<AbstractBookNode, Integer> nodesIndex, Book book, FileWriter fileWritter) throws IOException {
		fileWritter.write("Paragraphe " + nodesIndex.get(node) + " :\n");
		fileWritter.write("\n");

		fileWritter.write(node.getDescription(book));

		if(!node.getChoices().isEmpty()) {
			fileWritter.write("\nCorrespondance entre les choix et les paragraphes : \n\n");

			// Permet de connaitre les paragraphes suivants en fonction du choix
			for(BookNodeLink nodeLink : node.getChoices()) {
				if(!nodeLink.getText().isEmpty()) {
					fileWritter.write("- ");
					fileWritter.write(TextParser.parseText(nodeLink.getText(), book.getItems(), book.getCharacters()));
					fileWritter.write(" ");
				}

				fileWritter.write("- Paragraphe suivant : ");
				fileWritter.write(""+nodesIndex.get(book.getNodes().get(nodeLink.getDestination())));
				fileWritter.write("\n");
			}
		}
	}

}
