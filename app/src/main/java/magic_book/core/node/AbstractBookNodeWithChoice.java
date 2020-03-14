package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;
import magic_book.core.item.BookItem;


public abstract class AbstractBookNodeWithChoice <T extends BookNodeLink> extends AbstractBookNode{
	private int nbItemsAPrendre;
	private List<BookItem> items;
	private List<T> choices;
	
	public AbstractBookNodeWithChoice(String text, int nbItemsAPrendre, List<BookItem> items, List<T> choices){
		super(text);
		this.nbItemsAPrendre = nbItemsAPrendre;
		this.items = items;
		this.choices = choices;
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
		
		if(this.items == null)
			this.items = new ArrayList<>();
	}
	
	public void addChoices(T newChoices){
		this.choices.add(newChoices);
	}

	public void addItem(BookItem newItem){
		this.items.add(newItem);
	}
	
	public void removeItem(BookItem removeItem){
		this.items.remove(removeItem);
	}
	
	
	public int getNbItemsAPrendre() {
		return nbItemsAPrendre;
	}

	public void setNbItemsAPrendre(int nbItemsAPrendre) {
		this.nbItemsAPrendre = nbItemsAPrendre;
	}

	public List<BookItem> getItems() {
		return items;
	}

	public void setItems(List<BookItem> items) {
		this.items = items;
	}

	public List<T> getChoices() {
		return choices;
	}

	public void setChoices(List<T> choices) {
		this.choices = choices;
	}
	
}
