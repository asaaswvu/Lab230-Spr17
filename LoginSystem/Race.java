import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Race {
	private Set<String> candidates;
	private String raceName;
	private HashMap<String,String> voterIDS;

	public Race(String raceName){
		this.raceName = raceName;
		candidates = new HashSet<String>();
		voterIDS = new HashMap<String, String>();
	}

	public Set<String> getRandomCandidates() {
		ArrayList<String> candArray = new ArrayList<String>();
		for(String name : candidates){
			candArray.add(name);
		}
		Collections.shuffle(Arrays.asList(candArray));
		candidates.clear();
		for(String name : candArray){
			candidates.add(name);
		}
		return candidates;
	}
	public Set<String> getCandidates() {
		return candidates;
	}
	public void addCandidate(String name) {
		candidates.add(name);
	}
	public void disqualify(String name){
		System.out.println("@Race " + raceName + "set of cands before removing: candidate '" +name+"'"+candidates.toString());
		candidates.remove(name);
		System.out.println("@Race " + raceName + "set of cands after remove: " + candidates.toString());

	}
	public boolean vote(String voterID, String candidateName){
		if(voterIDS.containsKey(voterID)){
			return false; // ALREADY VOTED
		}
		voterIDS.put(voterID, candidateName);
		System.out.println("new Vote for candidateName!");
		return true;
	}
	public HashMap<String,Integer> getCandidateTotals(){
		HashMap<String,Integer> candTotals = new HashMap<String,Integer>();
		for(String name : candidates){
			candTotals.put(name,Collections.frequency(voterIDS.values(), name));
		}
		return candTotals;
	}
}
