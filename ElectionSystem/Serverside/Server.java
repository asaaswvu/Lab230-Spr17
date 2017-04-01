import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.omg.PortableInterceptor.INACTIVE;

import com.sun.xml.internal.ws.wsdl.parser.InaccessibleWSDLException;

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
	HashMap<String, String> electCommissioners; // Name -> Elect
	Set<String> activeElections;
	Set<String> completeElections;

	ServerSocket ss;
	static Boolean useGUI = true;

	Server() {
		elections = new HashMap<String, Election>();
		activeElections = new HashSet<String>();
		completeElections = new HashSet<String>();
		electCommissioners = new HashMap<String, String>();

		// OIT HANDLE HERE -----
		users = new Hashtable<String, String[]>();
		String[] user1 = { "password", "Student", "123" };
		String[] user2 = { "password", "Admin", "105" };
		String[] user3 = { "password", "Commissioner", "106" };
		String[] user4 = { "password", "Student", "124" };
		users.put("bob", user1);
		users.put("jim", user4);
		users.put("admin", user2);
		users.put("commis", user3);
		// OIT HANDLE HERE -----

		if (useGUI)
			new consoleGUIFrame(this);
		restore();
	}

	public void run() {
		if (useGUI)
			logToGUI("Server starting...");
		else
			logToGUI("GUIless Server starting...");

		new timeManager(this).start();
		try {
			ss = new ServerSocket(50000);
			logToGUI("Server Started");
			while (true) {
				Socket client = ss.accept();
				ClientHandler newClient = new ClientHandler(client, this);
				newClient.start();
				currentClients.add(newClient);
				logToGUI("Client @" + client.getRemoteSocketAddress().toString().substring(1) + " connected.");
			}
		} catch (SocketException f) {
			logToGUI("Server.run.socketException >> Socket Closed or in use!");

		} catch (IOException e) {
			logToGUI("Server IOException");
			e.printStackTrace();
		}
	}
	
	public boolean loginUser(String strUser, String strPass) {
		if (!users.containsKey(strUser)) {
			System.out.println("User " + strUser + " does not exist in database!");
			return false;
		}
		String tempPass = users.get(strUser)[0];
		if (tempPass != null && tempPass.equals(strPass)) {
			onlineUsers.add(strUser);
			if (useGUI)
				consoleGUI.updateOnlineUsers(onlineUsers);
			return true;
		}
		return false;
	}

	public String getUserType(String strUser) {
		return users.get(strUser)[1];
	}
	
	public void changeElectionComplete(String name){
		activeElections.remove(name);
		completeElections.add(name);
	}
	
	public void changeElectionActive(String name){
		activeElections.add(name);
	}

	public String getUserID(String strUser) {
		return users.get(strUser)[2];
	}

	public void addElection(String electionName, String commissioner) {
		elections.put(electionName, new Election(electionName, commissioner));
		electCommissioners.put(commissioner, electionName);
	}
	
	public boolean removeElection(String election) {
		if (elections.containsKey(election)) {
			electCommissioners.remove(elections.get(election).getCommissioner());
			elections.remove(election);
			activeElections.remove(election);
			completeElections.remove(election);
			return true;
		}
		return false;
	}

	public void changeCommissioner(String election, String newName) {
		electCommissioners.remove(elections.get(election).getCommissioner());
		elections.get(election).setCommissioner(newName);
		electCommissioners.put(newName, election);
	}

	public void addRace(String election, String race) {
		elections.get(election).addRace(race);
	}

	public void addCandidate(Election election, String race, String name) {
		elections.get(election).getRace(race).addCandidate(name);
	}

	public void logToGUI(String msg) {
		if (useGUI) {
			consoleGUI.txtpnHello.setText(consoleGUI.txtpnHello.getText()
					+ new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + ":> " + msg + "\n");
			consoleGUI.txtpnHello.repaint();
			consoleGUI.txtpnHello.revalidate();
		} else {
			System.out.println(msg);
		}
	}

	public void backup(String electionName) {
		try {
			File newBackup = new File(electionName + ".ser");
			newBackup.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(newBackup, false);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(elections.get(electionName));
			out.close();
			fileOut.close();
			logToGUI("Backing up " + electionName + ".");

			// File backupLedger = new File(filePath + "\\" + "ledger.txt");
			File backupLedger = new File("ledger.txt");
			backupLedger.createNewFile();
			PrintWriter ledgerWriter = new PrintWriter(backupLedger);
			for (String electName : elections.keySet()) {
				ledgerWriter.println(electName);
			}
			ledgerWriter.flush();
			ledgerWriter.close();
			// System.out.println("Ledger Updated@: " + filePath);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void restore() {
		Election e = null;
		try {
			HashSet<String> backedElections = new HashSet<String>();

			File backupLedger = new File("ledger.txt");			
			if(!backupLedger.createNewFile()){
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
				if (useGUI)
					consoleGUI.updateCurrentElections(elections.keySet());
			}
			fileRead.close();
			}
			else{
				logToGUI("No backups found!");
				}
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Employee class not found");
			c.printStackTrace();
			return;
		}
	}

	void forceDisconnectAllClients() throws IOException {
		System.out.println("Forcing Disconnect" + currentClients.toString());
		for (Iterator<ClientHandler> iter = currentClients.iterator(); iter.hasNext();) {
			ClientHandler c = iter.next();
			c.socket.close();
		}
		currentClients.clear();
	}

	void removeUserFromOnline(String Name) throws IOException {
		for (Iterator<String> iter = onlineUsers.iterator(); iter.hasNext();) {
			String removingName = iter.next();
			if (Name.equals(removingName))
				iter.remove();
		}
		if (useGUI)
			consoleGUI.updateOnlineUsers(onlineUsers);
	}

	void removeClientHandler(ClientHandler c, String ip) {
		for (Iterator<ClientHandler> iter = currentClients.iterator(); iter.hasNext();) {
			ClientHandler currHandler = iter.next();
			if (currHandler == c) {
				logToGUI("Client @" + ip + " terminated.");
				iter.remove();
			}
		}
	}
	
	public void die() {
		logToGUI("Backing up current Elections...");
		for (String backupElectionName : elections.keySet()) {
			backup(backupElectionName);
		}
		try {
			forceDisconnectAllClients();
			ss.close();
		} catch (IOException e) {
			// don't care, shutting down
		}
		logToGUI("Server shutting down...");
		System.exit(0);
	}

	public static void main(String args[]) {
		if (args.length > 0 && args[0].equals("-F"))
			useGUI = false;
		new Server().start();
	}
}
