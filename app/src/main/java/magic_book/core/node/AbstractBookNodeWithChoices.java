package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractBookNodeWithChoices <T extends BookNodeLink> extends AbstractBookNode {
	
	private Integer nbItemsAPrendre;
	private List<BookItemsLink> itemLinks;
	private List<BookItemsLink> shopItemLinks;
	private List<T> choices;
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemsLink> itemLinks, List<BookItemsLink> shopItemLinks, List<T> choices){
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
	
	public void addShopItemLink(BookItemsLink newShopItemLink){
		this.shopItemLinks.add(newShopItemLink);
	}

	public void addItemLink(BookItemsLink newItemLink){
		this.itemLinks.add(newItemLink);
	}
	
	public Integer getNbItemsAPrendre() {
		return nbItemsAPrendre;
	}

	public void setNbItemsAPrendre(Integer nbItemsAPrendre) {
		this.nbItemsAPrendre = nbItemsAPrendre;
	}

	public List<BookItemsLink> getItemLinks() {
		return itemLinks;
	}

	public void setItemLinks(List<BookItemsLink> itemLinks) {
		this.itemLinks = itemLinks;
	}

	public List<BookItemsLink> getShopItemLinks() {
		return shopItemLinks;
	}

	public void setShopItemLinks(List<BookItemsLink> shopItemLinks) {
		this.shopItemLinks = shopItemLinks;
	}
	
	public void setChoices(List<T> choices) {
		this.choices = choices;
	}
	
}
