import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;


public class Race implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	HashMap<String,Integer> candidates;
	private String raceName;

	public Race(String raceName){
		this.raceName = raceName;
		candidates = new HashMap<String,Integer>();
	}

	public ArrayList<String> getRandomCandidates() {
		System.out.println("@Race.RandBegin");
		ArrayList<String> candArray = new ArrayList<String>();
		for(String name : candidates.keySet()){
			candArray.add(name);
		}
		Collections.shuffle(candArray);
		System.out.println("@Race.RandCands"+Arrays.toString(candArray.toArray()));
		return candArray;
	}
	public Set<String> getCandidates() {
		return candidates.keySet();
	}
	public void addCandidate(String name) {
		System.out.println("ADDED Cand >:" + name +"to race " + raceName);
		candidates.put(name,0);
		System.out.println("CANDS: adfter added : "+candidates.entrySet());
	}
	public void disqualify(String name){
		System.out.println("@Race " + raceName + "set of cands before removing: candidate '" +name+"'"+candidates.toString());
		candidates.remove(name);
		System.out.println("@Race " + raceName + "set of cands after remove: " + candidates.toString());

	}
	public void vote(String candidateName){
		try{
			candidates.put(candidateName,((candidates.get(candidateName))+1));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public HashMap<String,Integer> getCandidateTotals(){
		return candidates;
	}
}
