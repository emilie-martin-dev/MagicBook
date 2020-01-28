/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_book.core;

/**
 *
 * @author 21806986
 */
public class NodeLink {
	private String texte;
	private Node destination;

	public NodeLink(String texte, Node destination){
		this.texte = texte;
		this.destination = destination;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		this.destination = destination;
	}
	
	
	
}
