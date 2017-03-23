import java.io.*;
import java.net.*;
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
				System.out.println("line is "+line);
				String [] data = line.split(",");
				System.out.println("DATA @ 0 is : " + data[0]);
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
					StringBuilder cands = new StringBuilder("<getCands>,");
					for(String r:candNames){
						cands.append((r+","));
					}
					pwOut.println(cands.toString());
					break;
				case "<getRandCands>":
					Set<String> randCandNames = server.getElection(data[1]).getRace(data[2]).getRandomCandidates();
					StringBuilder randCands = new StringBuilder("<getCands>,");
					for(String r:randCandNames){
						randCands.append((r+","));
					}
					pwOut.println(randCands.toString());
					break;
				case "<vote>":
					if(!server.getElection(data[2]).getRace(data[3]).vote(data[1], data[4])){
						pwOut.println("<voteReceived>,Fail");
					}
					break;
				case "<voteDone>":
					pwOut.println("<voteReceived>,Success");
					break;
				case "<getVoteCount>":
					StringBuilder votes = new StringBuilder("<voteCounts>,");
					for(String raceName :server.getElection(data[1]).getVoteCount().keySet()){
						votes.append(raceName+","+server.getElection(data[1]).getVoteCount().get(raceName)[0]+","+server.getElection(data[1]).getVoteCount().get(raceName)[1]+",");
					}
						pwOut.println(votes.toString());
						System.out.println(votes.toString());
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
