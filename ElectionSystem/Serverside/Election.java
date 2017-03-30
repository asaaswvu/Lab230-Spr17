import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Election implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> eligibleGroups;
	private HashMap<String, Race> races;
	@SuppressWarnings("unused")
	private String electionName = "";
	private String commissioner;
	@SuppressWarnings("unused")
	private Date startDate;
	@SuppressWarnings("unused")
	private Date endDate;
	public status electionStatus;

	public Election(String name, String commissioner) {
		electionName = name;
		this.commissioner = commissioner;
		eligibleGroups = new ArrayList<String>();
		eligibleGroups.add("Admin");
		races = new HashMap<String, Race>();
		electionStatus = status.EDITABLE;
	}
	
	public enum status {
	    ACTIVE, COMPLETE, EDITABLE
	}

	public ArrayList<String> getEligibleGroups() {
		return eligibleGroups;
	}
	
	public status getStatus(){
		return electionStatus;
	}

	public void addEligibleGroup(String group) {
		eligibleGroups.add(group);
	}

	public void removeEligibleGroup(String group) {
		eligibleGroups.remove(group);
	}

	public Race getRace(String name) {
		return races.get(name);
	}

	public Set<String> getAllRaces() {
		return races.keySet();
	}

	public void changeElectionName(String newName) {
		electionName = newName;
	}

	public void setCommissioner(String name) {
		commissioner = name;
	}

	public String getCommissioner() {
		return commissioner;
	}

	public void addRace(String nameRace) {
		races.put(nameRace, new Race(nameRace));
		System.out.println("adding race" + nameRace);
	}

	public void removeRace(String nameRace) {
		races.remove(nameRace);
	}

	public HashMap<String, HashMap<String, Integer>> getVoteCount() {
		HashMap<String, HashMap<String, Integer>> resultCounts = new HashMap<String, HashMap<String, Integer>>();
		for (String raceName : races.keySet()) {
			resultCounts.put(raceName, races.get(raceName).getCandidateTotals());
		}
		return resultCounts;
	}
}
