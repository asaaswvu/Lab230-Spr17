import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

class ClientHandler extends Thread {

	BufferedReader brIn;
	PrintWriter pwOut;
	Server server;
	Socket socket;
	String currentUserName;

	ClientHandler(Socket sock, Server serv) {
		try {
			brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pwOut = new PrintWriter(sock.getOutputStream(), true);
			server = serv;
			socket = sock;
		} catch (Exception e) {
			System.out.println("Error initiating ClientHandler");
		}
	}

	public void run() {
		createElectionList("<initElections>,");
		String line = new String();
		try {
			while (true) {
				line = brIn.readLine();
				System.out.println("input to ClientHandler is: " + line);
				String[] data = line.split(",");
				switch (data[0]) {
				case "<login>":
					loginUser(data);
					break;
				case "<logout>":
					server.removeFromConnected(this,currentUserName);
					break;
				case "<addElection>":
					server.addElection(data[1], data[2]);
					pwOut.println("<AddedElection>," + data[1] + "," + data[2]);
					consoleGUI.updateCurrentElections(server.elections.keySet());
					break;
				case "<getElections>":
					createElectionList("<getElections>,");
					break;
				case "<initElections>":
					createElectionList("<initElections>,");
					break;

				case "<removeElection>":
					if (server.removeElection(data[1])) {
						pwOut.println("<removedElection>," + data[1]);
					} else {
						pwOut.println("<removedElection>," + data[1] + ",fail");
					}
					consoleGUI.updateCurrentElections(server.elections.keySet());
					break;
				case "<addRace>":
					System.out.println("@Adding Race");
					server.addRace(data[1], data[2]);
					pwOut.println("<addedRace>," + data[1] + "," + data[2]);
					break;
				case "<removeRace>":
					System.out.println("@removing Race");
					server.elections.get(data[1]).removeRace(data[2]);
					pwOut.println("<removedRace>," + data[1] + "," + data[2]);
					break;
				case "<getRaces>":
					Set<String> raceNames = server.elections.get(data[1]).getAllRaces();
					StringBuilder races = new StringBuilder("<getRaces>,");
					for (String r : raceNames) {
						races.append((r + ","));
					}
					pwOut.println(races.toString());
					break;
				case "<addCand>":
					System.out.println("@Adding Cand");
					server.elections.get(data[1]).getRace(data[2]).addCandidate(data[3]);
					pwOut.println("<addedCand>," + data[1] + "," + data[2] + "," + data[3]);
					break;
				case "<removeCand>":
					System.out.println("@removing Cand");
					server.elections.get(data[1]).getRace(data[2]).disqualify(data[3]);
					pwOut.println("<removedCand>," + data[1] + "," + data[2] + "," + data[3]);
					break;

				case "<getCands>":
					Set<String> candNames = server.elections.get(data[1]).getRace(data[2]).getCandidates();
					StringBuilder cands = new StringBuilder("<pushCands>,");
					for (String r : candNames) {
						cands.append((r + ","));
					}
					pwOut.println(cands.toString());
					break;
				case "<getRandCands>":
					ArrayList<String> randCandNames = server.elections.get(data[1]).getRace(data[2]).getRandomCandidates();
					System.out.println(randCandNames);
					StringBuilder randCands = new StringBuilder("<pushCands>,");

					for (String r : randCandNames) {
						randCands.append((r + ","));
					}
					pwOut.println(randCands.toString());
					break;
				case "<getElectionStructure>":
					Election currentE = server.elections.get(data[1]);
					StringBuilder structure = new StringBuilder("<electStructure>," + currentE.getAllRaces().size() + ",");
					for (String raceName : currentE.getAllRaces()) {
						structure.append(raceName + "," + currentE.getVoteCount().get(raceName).keySet().size() + ",");
						ArrayList<String> randName = server.elections.get(data[1]).getRace(raceName).getRandomCandidates();
						for (String candName : randName){
							structure.append(candName+ ",");
						}
					}
					pwOut.println(structure.toString());
					break;
				case "<vote>":
					// Add a server.election check for if current user has voted
					// id = data[1]
					server.elections.get(data[2]).getRace(data[3]).vote(data[4]);
					// pwOut.println("<voteReceived>,Fail");
					break;
				case "<voteDone>":
					pwOut.println("<voteReceived>,Success");
					break;
				case "<getVoteCount>":
					Election currentElection = server.elections.get(data[1]);
					StringBuilder votes = new StringBuilder("<voteCounts>," + currentElection.getAllRaces().size() + ",");
					for (String raceName : currentElection.getAllRaces()) {
						votes.append(raceName + "," + currentElection.getVoteCount().get(raceName).keySet().size() + ",");
						for (String candName : currentElection.getVoteCount().get(raceName).keySet())
							votes.append(candName + "," + currentElection.getVoteCount().get(raceName).get(candName) + ",");
					}
					pwOut.println(votes.toString());
					break;
				case "<backup>":
					for(String electName : server.elections.keySet()){
						server.backup(electName);
					}
					break;
				case "<die>":
					die();
				default:
					pwOut.println("<error>");
				}
			}
		} catch (SocketException x) {
			System.out.println("socket disconnected - User x'd out");
			try {
				server.removeFromConnected(this, currentUserName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			server.die();
			die();
		}
	}

	private void loginUser(String[] data) {
		if (data.length == 3) {
			if (server.onlineUsers.contains(data[1])) {
				pwOut.println("<logged>,YiJing");
				return;
			} else if (server.loginUser(data[1], data[2])) {
				createElectionList("<initElections>,");
				String userType = server.getUserType(data[1]);
				String userID = server.getUserID(data[1]);
				currentUserName = data[1];
				server.log(data[1]);
				pwOut.println("<logged>," + userType + "," + data[1] + "," + userID);
				return;
			}
		}
		System.out.println("Invalid Credentials");
		pwOut.println("<logged>,FAIL");
	}

	private void createElectionList(String start) {
		Set<String> keys = server.elections.keySet();
		System.out.println("@CREATEELECTLIST: " + keys);
		StringBuilder elections = new StringBuilder(start);
		for (String k : keys) {
			elections.append((k + ","));
		}
		pwOut.println(elections.toString());
	}

	public void sendmsg(String msg) {
		pwOut.println(("<serverBROADCAST>," + msg));
	}

	private void die() {
		System.out.println("@CLIENTHANDLER.DIE");
		try {
			socket.close();
			server.die();
		} catch (Exception e) {
			// shutting down
		}
		System.exit(0);
	}
}
