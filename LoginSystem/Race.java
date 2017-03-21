import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Race {
	private ArrayList<String> candidates;
	private String raceName;

	public Race(String raceName){
		this.raceName = raceName;
		candidates = new ArrayList<String>();
	}

	public List<ArrayList<String>> getCandidates() {
		Collections.shuffle(Arrays.asList(candidates));
		return Arrays.asList(candidates);
	}
	public void addCandidate(String name) {
		candidates.add(name);
	}
	public void disqualify(String name){
		candidates.remove(name);
	}
}
