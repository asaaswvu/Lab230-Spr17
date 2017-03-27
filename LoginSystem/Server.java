import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

class Server extends Thread {

	Vector<ClientHandler> currentClients = new Vector<ClientHandler>();
	Vector<String> onlineUsers = new Vector<String>();
	Hashtable<String, String[]> users;
	HashMap<String, Election> elections;
	ServerSocket ss;
	
	Server() {
		users = new Hashtable<String, String[]>();
		elections = new HashMap<String, Election>();
		String[] user1 = { "password", "Student", "123" };
		String[] user2 = { "password", "Admin", "105" };
		String[] user3 = { "password", "Commissioner", "106" };
		String[] user4 = { "password", "Student", "124" };

		users.put("bob", user1);
		users.put("jim", user4);
		users.put("admin", user2);
		users.put("commis", user3);

		//backup("fake");
		//restore();
		new consoleGUIFrame(this);
		// System.out.println(elections.get("e1").getVoteCount().entrySet());
		// System.out.println(elections.get("e1").getVoteCount().entrySet());
		// backup("e1");
		// run();
	}

	public void run() {
		System.out.println("Server starting...");
		try {
			ss = new ServerSocket(50000); // high port numbers aren't normally
											// dedicated
			System.out.println("Server Started");
			while (true) {
				Socket client = ss.accept();
				ClientHandler newClient = new ClientHandler(client, this);
				newClient.start();
				currentClients.add(newClient);
				System.out.println("User has connected," + client.getRemoteSocketAddress().toString());
			}
		} catch (SocketException f) {
			System.out.println("Server.run.socketException >> Socket Closed or in use!");

		} catch (IOException e) {
			System.out.print("Server IOException");
			e.printStackTrace();
		}
	}

	public void die() {
		System.out.println("Backing up current Elections...");
		for (String backupElectionName : elections.keySet()) {
			backup(backupElectionName);
		}
		try {
			forceDisconnectAllClients();
			ss.close();
		} catch (IOException e) {
			// don't care, shutting down
		}
		System.out.println("Server shutting down...");
		System.exit(0);
	}
	
	public void logToGUI(String msg){
		consoleGUI.txtpnHello.setText(consoleGUI.txtpnHello.getText()+"\n"+msg);
		consoleGUI.txtpnHello.repaint();
		consoleGUI.txtpnHello.revalidate();
	}

	public boolean loginUser(String strUser, String strPass) {
		if (!users.containsKey(strUser)) {
			System.out.println("User " + strUser + " does not exist in database!");
			return false;
		}
		String tempPass = users.get(strUser)[0];
		if (tempPass != null && tempPass.equals(strPass)) {
			onlineUsers.add(strUser);
			//consoleGUIFrame.updateOnlineUsers();
			consoleGUI.updateOnlineUsers(onlineUsers);
			return true;
		}
		return false;
	}

	public String getUserType(String strUser) {
		return users.get(strUser)[1];
	}

	public String getUserID(String strUser) {
		return users.get(strUser)[2];
	}

	public boolean removeElection(String election) {
		if (elections.containsKey(election)) {
			elections.remove(election);
			return true;
		}
		return false;
	}

	public void addElection(String electionName, String commissioner) {
		elections.put(electionName, new Election(electionName, commissioner));
		System.out.println("Current Elections: " + elections.keySet().toString());
	}

	public void changeCommissioner(String election, String newName) {
		elections.get(election).setCommissioner(newName);
	}

	public void addRace(String election, String race) {
		elections.get(election).addRace(race);
		System.out.println("@Server.addRace");
	}

	public void addCandidate(Election election, String race, String name) {
		elections.get(election).getRace(race).addCandidate(name);
	}

	public void log(String strUser) {
		System.out.println("[" + getUserType(strUser) + "]" + strUser + " has logged in. [ID]>>" + getUserID(strUser));
	}
	

	public void backup(String electionName) {
		try {
			//URL resource = Election.class.getResource("/backups");
			//String filePath = (Paths.get(resource.toURI()).toFile().toString()).replace("\\bin", "\\LoginSystem");

			//File newBackup = new File(filePath + "\\" + electionName + ".ser");
			File newBackup = new File(electionName + ".ser");
			newBackup.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(newBackup, false);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(elections.get(electionName));
			out.close();
			fileOut.close();
			System.out.println("BACKUP'D@: ");

			//File backupLedger = new File(filePath + "\\" + "ledger.txt");
			File backupLedger = new File("ledger.txt");
			backupLedger.createNewFile();
			PrintWriter ledgerWriter = new PrintWriter(backupLedger);
			for (String electName : elections.keySet()) {
				ledgerWriter.println(electName);
			}
			ledgerWriter.flush();
			ledgerWriter.close();
			//System.out.println("Ledger Updated@: " + filePath);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void restore() {
		Election e = null;
		try {
			//URL resource = Election.class.getResource("/backups");
			//String filePath = (Paths.get(resource.toURI()).toFile().toString()).replace("\\bin", "\\LoginSystem");

			HashSet<String> backedElections = new HashSet<String>();
			//Scanner fileRead = new Scanner(new FileReader(filePath + "\\" + "ledger.txt"));
			File backupLedger = new File("ledger.txt");
			backupLedger.createNewFile();
			Scanner fileRead = new Scanner(new FileReader("ledger.txt"));
			while (fileRead.hasNext()) {
				backedElections.add(fileRead.nextLine());
				System.out.println("election found >> : " + backedElections.toString());
			}
			for (String currElectionBackup : backedElections) {
				File newBackup = new File(currElectionBackup + ".ser");
				FileInputStream fileIn = new FileInputStream(newBackup);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				e = (Election) in.readObject();
				elections.put(currElectionBackup, e);
				in.close();
				fileIn.close();
				consoleGUI.updateCurrentElections(elections.keySet());
			}
			fileRead.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		//} catch (URISyntaxException e1) {
			//e1.printStackTrace();
		}
	}

	void forceDisconnectAllClients() throws IOException {
		System.out.println("Forcing Disconnect" +currentClients.toString());
		for (Iterator<ClientHandler> iter = currentClients.iterator(); iter.hasNext();) {
		      ClientHandler c = iter.next();
				c.socket.close();
		}
		currentClients.clear();
		for (Iterator<ClientHandler> iter = currentClients.iterator(); iter.hasNext();) {
				iter.remove();
		}
	}
	
	void removeFromConnected(ClientHandler c, String Name) throws IOException{
		for (Iterator<String> iter = onlineUsers.iterator(); iter.hasNext();) {
			String removingName = iter.next();
				if(Name.equals(removingName))
					iter.remove();
		}
		consoleGUI.updateOnlineUsers(onlineUsers);
		System.gc();
	}

	public static void main(String args[]) {
		new Server().start();
		//GUI.test
	}
}
