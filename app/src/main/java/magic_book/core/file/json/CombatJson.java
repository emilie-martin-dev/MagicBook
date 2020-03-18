package magic_book.core.file.json;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CombatJson {
	
	private ChoiceJson win;
	private ChoiceJson evasion;
	private ChoiceJson loose;
	private List<String> enemies;
	@SerializedName("combat_skill")
	private Integer combatSkill;
	@SerializedName("evasion_round")
	private Integer evasionRound;

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

	public Integer getCombatSkill() {
		return combatSkill;
	}

	public void setCombatSkill(Integer combatSkill) {
		this.combatSkill = combatSkill;
	}

	public Integer getEvasionRound() {
		return evasionRound;
	}

	public void setEvasionRound(Integer evasionRound) {
		this.evasionRound = evasionRound;
	}

}
