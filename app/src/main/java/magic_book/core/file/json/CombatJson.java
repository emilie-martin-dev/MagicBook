package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CombatJson {
	
	private ChoiceJson win;
	private ChoiceJson evasion;
	private ChoiceJson loose;
	private List<String> enemies;
	@SerializedName("combat_skill")
	private int combatSkill;
	@SerializedName("evasion_round")
	private int evasionRound;

	public ChoiceJson getWin() {
		return win;
	}

	public void setWin(ChoiceJson win) {
		this.win = win;
	}

	public ChoiceJson getEvasion() {
		return evasion;
	}

	public void setEvasion(ChoiceJson evasion) {
		this.evasion = evasion;
	}

	public ChoiceJson getLoose() {
		return loose;
	}

	public void setLoose(ChoiceJson loose) {
		this.loose = loose;
	}

	public List<String> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<String> enemies) {
		this.enemies = enemies;
	}

	public int getCombatSkill() {
		return combatSkill;
	}

	public void setCombatSkill(int combatSkill) {
		this.combatSkill = combatSkill;
	}

	public int getEvasionRound() {
		return evasionRound;
	}

	public void setEvasionRound(int evasionRound) {
		this.evasionRound = evasionRound;
	}
	
}
