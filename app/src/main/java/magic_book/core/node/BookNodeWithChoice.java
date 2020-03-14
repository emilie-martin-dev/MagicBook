package magic_book.core.node;

import java.util.ArrayList;
import java.util.List;


public class BookNodeWithChoice extends AbstractBookNodeWithChoice {
	
	public BookNodeWithChoice(String text, List<BookNodeLink> choices, BookNodeType nodeType){
		super(text, choices);
		
		if(this.choices == null)
			this.choices = new ArrayList<>();
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<BookNodeLink> getChoices() {
		return choices;
	}

	public void setChoices(List<BookNodeLink> choices) {
		this.choices = choices;
	}

	@Override
	public String toString(){
		return this.text;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}
}
