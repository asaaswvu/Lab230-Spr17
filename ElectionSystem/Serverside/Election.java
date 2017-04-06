import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Election implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Race> races;
	private String electionName = "";
	private String commissioner;
	public Date startDate;
	public Date endDate;
	public status electionStatus;
	public String electionPassword;
	private HashSet<String> usersVoted;

	public Election(String name, String commissioner) {
		electionName = name;
		this.commissioner = commissioner;
		races = new HashMap<String, Race>();
		electionStatus = status.EDITABLE;
		electionPassword = "password";
		startDate = new Date(System.currentTimeMillis() + (600000000));
		endDate = new Date(System.currentTimeMillis() + (600000000));
		SimpleDateFormat ft = new SimpleDateFormat ("MMM d, yyyy k:mm:ss");
		usersVoted = new HashSet<String>();
	}

	public enum status {
		ACTIVE, COMPLETE, EDITABLE
	}

	public void closePoll(){
		electionStatus = status.COMPLETE;
		System.out.println("CLOSING POLL");
	}
	
	public boolean hasVoted(String user){
		return usersVoted.contains(user);
	}
	
	public void userVoted(String user){
		usersVoted.add(user);
	}

	public void openPoll(){
		electionStatus = status.ACTIVE;
		System.out.println("OPENING POLL");
	}

	public status getStatus(){
		return electionStatus;
	}
	
	public String getPass(){
		return this.electionPassword;
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
	
	public void setCommissioner(String name) {
		commissioner = name;
	}

	public String getCommissioner() {
		return commissioner;
	}

	public void addRace(String nameRace) {
		races.put(nameRace, new Race(nameRace));
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
