import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Set;


class ClientHandler extends Thread{

	BufferedReader brIn;
	PrintWriter pwOut;
	Server server;
	Socket socket;

	ClientHandler(Socket sock, Server serv){
		try{
			brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pwOut = new PrintWriter(sock.getOutputStream(),true);
			server = serv;
			socket = sock;
		}catch(Exception e){
			System.out.println("Error initiating ClientHandler");
		}
	}

	public void run(){
		String line = new String();
		try{
			while (true){
				line = brIn.readLine();
				System.out.println("input to ClientHandler is: "+line);
				String [] data = line.split(",");
				switch (data[0]){
				case "<login>":
					createElectionList("<initElections>,");
					loginUser(data);
					break;
				case "<addElection>":
					server.addElection(data[1], data[2]);
					pwOut.println("<AddedElection>,"+data[1]+","+data[2]);
					break;
				case "<getElections>":
					createElectionList("<getElections>,");
					break;
				case "<initElections>":
					createElectionList("<initElections>,");
					break;
					
				case "<removeElection>":
					if(server.removeElection(data[1])){
						pwOut.println("<removedElection>,"+data[1]);
					}
					else{
						pwOut.println("<removedElection>,"+data[1]+",fail");
					}
					break;
				case "<addRace>":
					System.out.println("@Adding Race");
					server.addRace(data[1], data[2]);
					pwOut.println("<addedRace>,"+data[1] + "," + data[2]);
					break;
				case "<removeRace>":
					System.out.println("@removing Race");
					server.getElection(data[1]).removeRace(data[2]);
					pwOut.println("<removedRace>,"+data[1] + "," + data[2]);
					break;
				case "<getRaces>":
					Set<String> raceNames = server.getElection(data[1]).getAllRaces();
					StringBuilder races = new StringBuilder("<getRaces>,");
					for(String r:raceNames){
						races.append((r+","));
					}
					pwOut.println(races.toString());
					break;
				case "<addCand>":
					System.out.println("@Adding Cand");
					server.getElection(data[1]).getRace(data[2]).addCandidate(data[3]);
					pwOut.println("<addedCand>,"+data[1] + "," + data[2]+ "," + data[3]);
					break;
				case "<removeCand>":
					System.out.println("@removing Cand");
					server.getElection(data[1]).getRace(data[2]).disqualify(data[3]);
					pwOut.println("<removedCand>,"+data[1] + "," + data[2]+"," + data[3]);
					break;
					
					
				case "<getCands>":
					Set<String> candNames = server.getElection(data[1]).getRace(data[2]).getCandidates();
					StringBuilder cands = new StringBuilder("<pushCands>,");
					for(String r:candNames){
						cands.append((r+","));
					}
					pwOut.println(cands.toString());
					break;
				case "<getRandCands>":
					ArrayList<String> randCandNames = server.getElection(data[1]).getRace(data[2]).getRandomCandidates();
					System.out.println(randCandNames);
					StringBuilder randCands = new StringBuilder("<pushCands>,");
					
					for(String r:randCandNames){
						randCands.append((r+","));
					}
					pwOut.println(randCands.toString());
					break;
					
					
				case "<vote>":
					//Add a server.election check for if current user has voted id = data[1]
					server.getElection(data[2]).getRace(data[3]).vote(data[4]);
					//pwOut.println("<voteReceived>,Fail");
					break;
				case "<voteDone>":
					pwOut.println("<voteReceived>,Success");
					break;
				case "<getVoteCount>":
					Election currentE = server.getElection(data[1]);
					StringBuilder votes = new StringBuilder("<voteCounts>," + currentE.getAllRaces().size()+",");
					for(String raceName : currentE.getAllRaces()){
						votes.append(raceName+","+currentE.getVoteCount().get(raceName).keySet().size()+",");
						for(String candName : currentE.getVoteCount().get(raceName).keySet())
						votes.append(candName+","+currentE.getVoteCount().get(raceName).get(candName)+",");
					}
						pwOut.println(votes.toString());
					break;
				case "<die>" :
					die();
				default:
					pwOut.println("<error>");
				}

			}
		}catch(SocketException x){
			System.out.println("socket disconnected");
		}catch(Exception e){
			server.die();
			die();
		}
	}

	private void loginUser(String [] data){
		if (data.length == 3){
			if(server.loginUser(data[1],data[2])){
				String userType = server.getUserType(data[1]);
				String userID = server.getUserID(data[1]);
				server.log(data[1]);
				pwOut.println("<logged>,"+ userType + "," + data[1]+","+ userID);
			}
			else{
				System.out.println("Invalid Credentials");
				pwOut.println("<logged>,FAIL");
			}
		}
		else{
			pwOut.println("<error>");
		}
	}
	
	private void createElectionList(String start){
		Set<String> keys = server.getElections();
		StringBuilder elections = new StringBuilder(start);
		for(String k:keys){
			elections.append((k+","));
		}
		pwOut.println(elections.toString());
	}
	private void die(){
		try{
			socket.close();
			server.die();
		}catch(Exception e){
			//shutting down
		}
		System.exit(0);
	}
}
