package magic_book.core.node;

import java.util.ArrayList;

public class Node {
	
	private String texte;
	private NodeType type;
	private ArrayList<NodeLink> choix;
	
	public Node(String texte, NodeType type, ArrayList<NodeLink> choix){
		this.texte=texte;
		this.type = type;
		this.choix = choix;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public NodeType getType() {
		return type;
	}

	public void setType(NodeType type) {
		this.type = type;
	}

	public ArrayList<NodeLink> getChoix() {
		return choix;
	}

	public void setChoix(ArrayList<NodeLink> choix) {
		this.choix = choix;
	}
	
	@Override
	public String toString(){
		return this.texte;
	}
	
}
