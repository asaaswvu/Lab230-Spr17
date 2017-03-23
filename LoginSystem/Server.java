import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.io.IOException;

class Server extends Thread{

	Hashtable<String, String[]> users;
	HashMap<String, Election> elections;
	ServerSocket ss;


	Server(){
		users = new Hashtable<String,String[]>();
		elections = new HashMap<String,Election>();
		String[] user1 = {"password","Student","123"};
		String[] user2 = {"password","Admin","105"};
		String[] user3 = {"password","Commissioner","106"};
		String[] user4 = {"password","Student","124"};

		users.put("bob", user1);
		users.put("jim", user4);
		users.put("admin", user2);
		users.put("commis", user3);
	}

	public void run(){
		try{
			ss = new ServerSocket(50000);   //high port numbers aren't normally dedicated
			System.out.println("Server Started");
			while(true){
				Socket client = ss.accept();
				new ClientHandler(client,this).start();
				System.out.println("Started ClientHandler");
			}
		}catch(SocketException f){
			System.out.println("User Quit @ Socket Exception");

		}catch(IOException e){
			System.out.print("Server IOException");
			e.printStackTrace();
		}
	}
	public void die(){
		try{
			ss.close();
		}catch(IOException e){
			//don't care, shutting down
		}
		System.exit(0);
	}

	public boolean loginUser(String strUser, String strPass){
		if(!users.containsKey(strUser)){
			System.out.println("User " + strUser + " does not exist in database!");
			return false;
		}
		String tempPass = users.get(strUser)[0];
		if (tempPass != null && tempPass.equals(strPass)){
			return true;
		}
		return false;
	}

	public String getUserType(String strUser){
		return users.get(strUser)[1];
	}
	
	public String getUserID(String strUser){
		return users.get(strUser)[2];
	}

	public boolean removeElection(String election){
		if(elections.containsKey(election)){
			elections.remove(election);
			return true;
		}
		return false;
	}

	public void addElection(String electionName, String commissioner){
		elections.put(electionName, new Election(electionName, commissioner));
		System.out.println("Current Elections: " + elections.keySet().toString());
	}

	public void changeCommissioner(String election, String newName){
		elections.get(election).setCommissioner(newName);
	}

	public void addRace(String election, String race){
		elections.get(election).addRace(race);
		System.out.println("@Server.addRace");
	}

	public void addCandidate(Election election, String race, String name){
		elections.get(election).getRace(race).addCandidate(name);
	}

	public void log(String strUser){
		System.out.println("["+getUserType(strUser)+"]"+strUser +" has logged in. [ID]>>" + getUserID(strUser));
	}
	
	public Set<String> getElections(){
		/* Set<String> availableElects = new HashSet<String>();
		for(String k : elections.keySet()){
			System.out.println("@Election: " + k + " UserGroup is: "+userGroup);
			if(elections.get(k).getEligibleGroups().contains(userGroup)){
				System.out.println("@added election "+k);
				availableElects.add(k);
			}
		}*/
		return elections.keySet();
	}
	
	public Election getElection(String electionName){
		return elections.get(electionName);
	}

	public static void main(String args[]){
		new Server().start();
	}
}
