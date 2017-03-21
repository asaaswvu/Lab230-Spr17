import java.util.ArrayList;
import java.util.HashMap;

public class Election {
	private ArrayList<String> eligibleGroups;
	private HashMap<String, Race> races;
	private String electionName = "";
	private String commissioner;

	public Election(String name, String commissioner){
		electionName = name;
		this.commissioner = commissioner;
		eligibleGroups = new ArrayList<String>();
		races = null;
	}
	public ArrayList<String> getEligibleGroups() {
		return eligibleGroups;
	}

	public void addEligibleGroup(String group) {
		eligibleGroups.add(group);
	}

	public void removeEligibleGroup(String group){
		eligibleGroups.remove(group);
	}

	public Race getRace(String name) {
		return races.get(name);
	}

	public void changeElectionName(String newName){
		electionName = newName;
	}

	public void setCommissioner(String name){
		commissioner = name;
	}

	public String getCommissioner(){
		return commissioner;
	}

	public void addRace(String nameRace) {
		races.put(nameRace,new Race(nameRace));
	}

	public void removeRace(String nameRace) {
		races.remove(nameRace);
	}
}
