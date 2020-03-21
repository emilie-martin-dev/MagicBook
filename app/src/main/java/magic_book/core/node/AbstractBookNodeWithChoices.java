package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.graph.node.AbstractBookNode;
import magic_book.core.item.BookItemLink;


public abstract class AbstractBookNodeWithChoices <T extends BookNodeLink> extends AbstractBookNode {
	
	private Integer nbItemsAPrendre;
	private List<BookItemLink> itemLinks;
	private List<BookItemLink> shopItemLinks;
	List<T> choices;
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices){
		super(text);
		this.nbItemsAPrendre = nbItemsAPrendre;
		this.itemLinks = itemLinks;
		this.shopItemLinks = shopItemLinks;
		this.choices = choices;
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
		
		if(this.itemLinks == null)
			this.itemLinks = new ArrayList<>();
		
		if(this.shopItemLinks == null)
			this.shopItemLinks = new ArrayList<>();
	}
	
	@Override
	public List<T> getChoices() {
		return choices;
	}
	
	public void addChoices(T newChoices){
		this.choices.add(newChoices);
	}
	
	public void addShopItemLink(BookItemLink newShopItemLink){
		this.shopItemLinks.add(newShopItemLink);
	}

	public void addItemLink(BookItemLink newItemLink){
		this.itemLinks.add(newItemLink);
	}
	
	public Integer getNbItemsAPrendre() {
		return nbItemsAPrendre;
	}

	public void setNbItemsAPrendre(Integer nbItemsAPrendre) {
		this.nbItemsAPrendre = nbItemsAPrendre;
	}

	public List<BookItemLink> getItemLinks() {
		return itemLinks;
	}

	public void setItemLinks(List<BookItemLink> itemLinks) {
		this.itemLinks = itemLinks;
	}

	public List<BookItemLink> getShopItemLinks() {
		return shopItemLinks;
	}

	public void setShopItemLinks(List<BookItemLink> shopItemLinks) {
		this.shopItemLinks = shopItemLinks;
	}
	
	public void setChoices(List<T> choices) {
		this.choices = choices;
	}
	
}
