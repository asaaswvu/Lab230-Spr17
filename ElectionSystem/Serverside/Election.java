import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Election implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> eligibleGroups;
	private HashMap<String, Race> races;
	private String electionName = "";
	private String commissioner;
	public Date startDate;
	public Date endDate;
	public status electionStatus;
	public String electionPassword;

	public Election(String name, String commissioner) {
		electionName = name;
		this.commissioner = commissioner;
		eligibleGroups = new ArrayList<String>();
		eligibleGroups.add("Admin");
		races = new HashMap<String, Race>();
		electionStatus = status.EDITABLE;
		electionPassword = "password";
		startDate = new Date(System.currentTimeMillis() + (600000000));
		endDate = new Date(System.currentTimeMillis() + (600000000));
		SimpleDateFormat ft = new SimpleDateFormat ("MMM d, yyyy k:mm:ss");
		System.out.println("START: "+ft.format(startDate));
		System.out.println("END: "+ft.format(endDate));
	}

	public enum status {
		ACTIVE, COMPLETE, EDITABLE
	}

	public void closePoll(){
		electionStatus = status.COMPLETE;
		System.out.println("CLOSING POLL");
	}

	public void openPoll(){
		electionStatus = status.ACTIVE;
		System.out.println("OPENING POLL");
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
	
	public String getElectionName() {
		return this.electionName;
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
