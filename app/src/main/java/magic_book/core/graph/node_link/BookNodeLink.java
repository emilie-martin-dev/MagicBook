package magic_book.core.graph.node_link;

import magic_book.core.graph.node.AbstractBookNode;
import java.util.ArrayList;
import java.util.List;

import magic_book.core.Book;
import magic_book.core.file.JsonExportable;
import magic_book.core.file.json.ChoiceJson;
import magic_book.core.file.json.RequirementJson;
import magic_book.core.file.json.TypeJson;
import magic_book.core.game.BookState;
import magic_book.core.parser.Descriptible;
import magic_book.core.parser.TextParser;
import magic_book.core.requirement.AbstractRequirement;
import magic_book.core.requirement.RequirementItem;
import magic_book.core.requirement.RequirementMoney;
import magic_book.core.requirement.RequirementSkill;

public class BookNodeLink implements Descriptible, JsonExportable<ChoiceJson> {

	private String text;
	private AbstractBookNode destination;
	private List<List<AbstractRequirement>> requirements;
	private int hp;
	private int gold;
	private boolean auto;
	
	public BookNodeLink() {
		this("", null);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination) {
		this(text, destination, null);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination, List<List<AbstractRequirement>> requirements) {
		this(text, destination, requirements, 0, 0, false);
	}
	
	public BookNodeLink(String text, AbstractBookNode destination, List<List<AbstractRequirement>> requirements, int hp, int gold, boolean auto) {
		this.text = text;
		this.destination = destination;
		this.requirements = requirements;
		this.hp = hp;
		this.gold = gold;
		this.auto = auto;
		
		if(this.requirements == null)
			this.requirements = new ArrayList<>();
	}
	
	public boolean isAvailable(BookState state) {
		if(requirements.isEmpty()) 
			return true;
		
		for(List<AbstractRequirement> groupRequirement : requirements) {
			boolean satisfied = true;
			for(AbstractRequirement r : groupRequirement) {
				if(!r.isSatisfied(state)) {
					satisfied = false;
					break;
				}
			}
			
			if(satisfied)
				return true;
		}
		
		return false;
	}

	@Override
	public String getDescription(Book book) {
		StringBuffer buffer = new StringBuffer();
		
		if(!text.isEmpty()) {
			buffer.append("- ");
			buffer.append(TextParser.parseText(text, book.getItems(), book.getCharacters()));
			buffer.append("\n");
		}
		
		if(hp < 0) {
			buffer.append("Vous perdrez ");
			buffer.append(Math.abs(hp));
			buffer.append(" HP.\n");
		} else if(hp > 0) {
			buffer.append("Vous gagnerez ");
			buffer.append(hp);
			buffer.append(" HP.\n");
		}
		
		if(gold < 0) {
			buffer.append("Vous perdrez ");
			buffer.append(Math.abs(gold));
			buffer.append(" gold.\n");
		} else if(gold > 0) {
			buffer.append("Vous gagnerez ");
			buffer.append(gold);
			buffer.append(" gold.\n");
		}
		
		
		if(!requirements.isEmpty()) {
			buffer.append("Pour faire ce choix vous devez remplir les coditions suivantes : \n");
		}
		
		for(int i = 0 ; i < requirements.size() ; i++) {
			List<AbstractRequirement> subrequirements = requirements.get(i);
			for(int j = 0 ; j < subrequirements.size() ; j++) {
				buffer.append(subrequirements.get(j).getDescription(book));
				if(j < subrequirements.size() - 1)
					buffer.append("et\n");
			}
			
			if(i < requirements.size() - 1)
				buffer.append("ou\n");
		}
		 
		if(auto) {
			buffer.append("Ce choix est obligatoire si vous remplissez les prérequis.\n");
		}
		
		return buffer.toString();
	}
	
	public ChoiceJson toJson() {
		ChoiceJson choiceJson = new ChoiceJson();
		
		choiceJson.setText(text);
		
		if(!requirements.isEmpty()) {
			choiceJson.setRequirements(new ArrayList<>());
			for(List<AbstractRequirement> subrequirement : requirements) {
				List<RequirementJson> subrequirementsJson = new ArrayList<>();
				for(AbstractRequirement requirement : subrequirement) {
					RequirementJson requirementJson = requirement.toJson();
					
					subrequirementsJson.add(requirementJson);
				}
				
				choiceJson.getRequirements().add(subrequirementsJson);
			}
		}
		
		if(auto)
			choiceJson.setAuto(true);
		
		if(gold != 0)
			choiceJson.setGold(gold);
		
		if(hp != 0)
			choiceJson.setHp(hp);
		
		return choiceJson;
	}

	@Override
	public void fromJson(ChoiceJson json) {		
		if(json.getAuto() == null)
			auto = false;
		else
			auto = json.getAuto();
		
		if(json.getGold() == null)
			gold = 0;
		else
			gold = json.getGold();
		
		if(json.getHp() == null)
			hp = 0;
		else
			hp = json.getHp();
		
		text = json.getText();
		
		if(json.getRequirements() != null) {
			for(List<RequirementJson> requirementsJson : json.getRequirements()) {
				List<AbstractRequirement> subrequirements = new ArrayList<>();
				for(RequirementJson requirementJson : requirementsJson) {
					AbstractRequirement abstractRequirement = null;
					
					if(requirementJson.getType() == TypeJson.ITEM) {
						abstractRequirement = new RequirementItem();
					} else if (requirementJson.getType() == TypeJson.SKILL) {
						abstractRequirement = new RequirementSkill();						
					} else if (requirementJson.getType() == TypeJson.MONEY) {
						abstractRequirement = new RequirementMoney();						
					}
					
					abstractRequirement.fromJson(requirementJson);
					
					subrequirements.add(abstractRequirement);
				}
				
				requirements.add(subrequirements);
			}
		}
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public AbstractBookNode getDestination() {
		return destination;
	}

	public void setDestination(AbstractBookNode destination) {
		this.destination = destination;
	}

	public List<List<AbstractRequirement>> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<List<AbstractRequirement>> requirements) {
		this.requirements = requirements;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public boolean getAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}
		
}
