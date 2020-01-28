/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magic_book.core;

import java.util.ArrayList;


/**
 *
 * @author 21806986
 */
public class Node {
	private String texte;
	private NodeType type;
	private ArrayList<NodeLink> choix;
	
	public Node(String texte, NodeType type, ArrayList<NodeLink> choix){
		this.texte=texte;
		this.type = type;
		this.choix = choix;
	}

	public void getTexte(){
		return this.texte;
	}
	
	public String setTexte(String texte){
		this.texte = texte;
	}

	public void getType(){
		return this.type;
	}

	public String setType(NodeType type){
		this.type = type;
	}
	
	public void getChoix(){
		return this.choix;
	}

	public String setChoix(ArrayList<NodeLink> choix){
		this.choix = choix;
	}
	
	@Override
	public String toString(){
		return this.texte;
	}
	public NodeLink faireChoix(){
	return 
	
	
	
	
	
	


}
