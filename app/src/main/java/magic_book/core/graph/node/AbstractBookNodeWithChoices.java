package magic_book.core.graph.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.item.BookItemLink;
import magic_book.core.graph.node_link.BookNodeLink;


public abstract class AbstractBookNodeWithChoices <T extends BookNodeLink> extends AbstractBookNode {
	
	private Integer nbItemsAPrendre;
	private List<BookItemLink> itemLinks;
	private List<BookItemLink> shopItemLinks;
	private List<T> choices;
	private boolean mustEat;
	private int hp;
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices){
		this(text, nbItemsAPrendre, itemLinks, shopItemLinks, choices, false, 0);
	}
	
	public AbstractBookNodeWithChoices(String text, Integer nbItemsAPrendre, List<BookItemLink> itemLinks, List<BookItemLink> shopItemLinks, List<T> choices, boolean mustEat, int hp){
		super(text);
		
		this.nbItemsAPrendre = nbItemsAPrendre;
		this.itemLinks = itemLinks;
		this.shopItemLinks = shopItemLinks;
		this.choices = choices;
		this.mustEat = mustEat;
		this.hp = hp;
		
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

	public boolean isMustEat() {
		return mustEat;
	}

	public void setMustEat(boolean mustEat) {
		this.mustEat = mustEat;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
