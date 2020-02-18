package magic_book.core.file.json;

import java.util.List;

class SetupJson {

	private List<String> disciplines;
	private List<String> weapons;
	private List<ItemJson> equipment;

	public List<String> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<String> disciplines) {
		this.disciplines = disciplines;
	}

	public List<String> getWeapons() {
		return weapons;
	}

	public void setWeapons(List<String> weapons) {
		this.weapons = weapons;
	}

	public List<ItemJson> getEquipment() {
		return equipment;
	}

	public void setEquipment(List<ItemJson> equipment) {
		this.equipment = equipment;
	}

}
