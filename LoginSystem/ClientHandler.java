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
					loginUser(data);
					break;
				case "<addElection>":
					server.addElection(data[1], data[2]);
					pwOut.println("<AddedElection>,"+data[1]+","+data[2]);
					break;
				case "<getElections>":
					Set<String> keys = server.getElections();
					StringBuilder elections = new StringBuilder("<getElections>,");
					for(String k:keys){
						elections.append((k+","));
					}
					pwOut.println(elections.toString());
					break;
				case "<initElections>":
					Set<String> initalKeys = server.getElections();
					StringBuilder initElections = new StringBuilder("<initElections>,");
					for(String k:initalKeys){
						initElections.append((k+","));
					}
					pwOut.println(initElections.toString());
					break;
					
				case "<removeElection>":
					if(server.removeElection(data[1])){
						pwOut.println("<removedElection>,"+data[1]);
					}
					else if(data[1].equals("noSelection")){
						pwOut.println("<removedElection>,noSelection");
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
					if(data[1].equals("noSelection")){
						pwOut.println("<removedRace>,noSelection");
					}
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
				String logMessage = "";
				if(userType.equals("Student")){
					logMessage = "<loggedS>";
				}
				else if(userType.equals("Admin")){
					logMessage = "<loggedA>";
				}
				else if(userType.equals("Commissioner")){
					logMessage = "<loggedC>";
				}
				pwOut.println(logMessage + "," + data[1]);
			}
			else{
				System.out.println("Invalid Credentials");
				pwOut.println("<loggedF>");
			}
		}
		else{
			pwOut.println("<error>");
		}
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
