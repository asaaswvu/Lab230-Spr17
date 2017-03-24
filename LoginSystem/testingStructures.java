import java.util.HashMap;

public class testingStructures {
	public static void main(String[] args) {
		HashMap<String, Election> elections = new HashMap<String,Election>();
		elections.put("e1", new Election("ElectName","commissName"));
		elections.get("e1").addRace("race1");
		elections.get("e1").getRace("race1").addCandidate("c1r1");
		elections.get("e1").getRace("race1").vote("c1r1");
		elections.get("e1").getRace("race1").vote("c1r1");

		elections.get("e1").addRace("race2");
		elections.get("e1").getRace("race2").addCandidate("c2r1");
		elections.get("e1").getRace("race2").vote("c2r1");
		elections.get("e1").getRace("race2").vote("c2r1");
		elections.get("e1").getRace("race2").vote("c2r1");
		elections.get("e1").getRace("race2").vote("c2r1");
		elections.get("e1").getRace("race2").vote("c2r1");

		elections.get("e1").getRace("race2").addCandidate("c1r2");
		elections.get("e1").addRace("race3");
		elections.get("e1").getRace("race3").addCandidate("c1r3");
		
		elections.get("e1").getRace("race3").vote("c1r3");
		elections.get("e1").getRace("race3").addCandidate("c2r3");
		elections.get("e1").getRace("race3").addCandidate("c3r3");


		//HashMap<String,String[]> res = elections.get("e1").getVoteCount();
		//System.out.println("RES KEYSET"+res.keySet()+"\n");
//		for(String candName : elections.get("e1").getRace("race1").getCandidateTotals().keySet()){
//			System.out.print());
//		}
		HashMap<String,HashMap<String,Integer>>resultCounts = new HashMap<String,HashMap<String,Integer>>();

		for(String race : elections.get("e1").getAllRaces()){
			resultCounts.put(race, elections.get("e1").getRace(race).getCandidateTotals());
		}
		System.out.println(resultCounts.toString());
		
		StringBuilder ham = new StringBuilder();
		for(String race: resultCounts.keySet()){
			System.out.println("RACE: "+ race +" >>");
			for(String cand : resultCounts.get(race).keySet()){
				System.out.println("\tCand Name: " + cand +":"+resultCounts.get(race).get(cand));
			}
		}
		System.out.println(elections.get("e1").getRace("race3").getCandidates());
		System.out.println(elections.get("e1").getRace("race3").getRandomCandidates());
//		for(String rName : res.keySet()){
//			System.out.println("Race: " + rName+" >> \n");
//			
//				System.out.print("CAND NAME : "+data +""+);
//			}
//		}
	}
}
