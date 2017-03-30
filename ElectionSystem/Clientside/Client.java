import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

public class Client extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	public static Client parentClient;

	BufferedReader brIn;
	static PrintWriter pwOut;
	Socket sock;
	private JTextField txtName;
	private JPasswordField txtPass;
	private JPanel pnlMain = new JPanel();
	private JPanel pnlStudentVoteView = new JPanel();
	private JPanel pnlHomeView = new JPanel(new BorderLayout());
	private JPanel pnlAdminView = new JPanel(new GridBagLayout());
	private ElectionCreationGUI pnlAddElectionView;
	private ElectionEditPanel ballotEdit;
	private ArrayList<String> elections = new ArrayList<String>();
	private ArrayList<String> currentRaces = new ArrayList<String>();
	private ArrayList<String> currentCands = new ArrayList<String>();
	private JList<String> lstElections = new JList<String>();
	private JList<String> lstCandidates = new JList<String>();
	private JList<String> lstRaces = new JList<String>();
	private HashMap<String,ArrayList<String>> electStructure = new HashMap<String,ArrayList<String>>();

	@SuppressWarnings("unused")
	private String userName = "";
	private String userID = "";
	private String userType = "";
	public static String selectedElection = null;
	public static String selectedRace = null;
	private HashMap<String,String> voteChoices = new HashMap<String,String>();


	Client(){
		parentClient = this;
		this.setResizable(false);
		pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.PAGE_AXIS));

		JPanel pnlName = new JPanel();
		JPanel pnlPassword = new JPanel();
		JPanel pnlButtons = new JPanel();

		txtName = new JTextField(15);
		JLabel lblName = new JLabel("Username: ");
		pnlName.add(lblName);
		pnlName.add(txtName);

		txtPass = new JPasswordField(15);
		txtPass.setText("password");
		JLabel lblPass = new JLabel("Password: ");
		pnlPassword.add(lblPass);
		pnlPassword.add(txtPass);

		JButton btnLogin = new JButton("Login");
		btnLogin.setActionCommand("login");
		btnLogin.addActionListener(this);

		JButton btnQuit = new JButton("Quit");
		btnQuit.setActionCommand("quit");
		btnQuit.addActionListener(this);

		pnlButtons.add(btnLogin);
		pnlButtons.add(btnQuit);

		pnlMain.add(pnlName);
		pnlMain.add(pnlPassword);
		pnlMain.add(pnlButtons);

		getContentPane().add(pnlMain);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Election Login");
		setMinimumSize(new Dimension(275,150));
		pack();

		setLocationRelativeTo(null);
		setVisible(true);
		run();
	}

	//**********---SERVER RESPONSE HANDLING BEGIN---**********

	private void run(){
		try{
			sock = new Socket("127.0.0.1",50000);
			//sock = new Socket("10.253.73.124",50000);
			//sock = new Socket("108.61.219.203",50000);
			brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			pwOut = new PrintWriter(sock.getOutputStream(),true);

			while (true){
				String strIn = brIn.readLine();
				String [] data = strIn.split(",");
				System.out.println("Response from Server to Client : " + strIn);

				if (strIn.startsWith("<logged>")){
					if(data[1].equals("FAIL")){
						JOptionPane.showMessageDialog(this,"Login Failed!","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
						continue;
					}
					if(data[1].equals("YiJing")){
						JOptionPane.showMessageDialog(this,"You're already logged in!","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
						continue;
					}
					userType = data[1];
					userName = data[2];
					userID = data[3];

					if(userType.equals("Student")){
						System.out.println("Student has logged in.");
						initMain();
						changeView(pnlHomeView);
					}
					else if(userType.equals("Admin")){
						System.out.println("Admin has logged in.");
						initMain();
						changeView(pnlHomeView);
					}
					else if(userType.equals("Commissioner")){
						System.out.println("Commissioner has logged in.");
						initMain();
						changeView(pnlHomeView);
					}
					//					else if(userType.equals("sudo")){
					//						System.out.println("sudo has logged in.");
					//						initSudo();
					//						changeView(pnlSudoView);
					//					}
					setSize(450,300);
				}
				else if (strIn.startsWith("<serverBROADCAST>")){
					JOptionPane.showMessageDialog(this,data[1],"Message from server",JOptionPane.PLAIN_MESSAGE);
				}
				else if (strIn.startsWith("<AddedElection>")){
					String message = "The " + data[1] + " election has been created.\n"+data[2] + " is the Election Commissioner.";
					JOptionPane.showMessageDialog(this,message,"Election Created",JOptionPane.PLAIN_MESSAGE);
					System.out.println("Election created: " + data[1] + " : Commissioner: " + data[2]);
					initMain();
					changeView(pnlHomeView);
				}
				else if (strIn.startsWith("<removedElection>")){
					String message = "";
					if(data.length == 2){
						message = "The " + data[1] + " election has been removed.";
						System.out.println("Election removed: " + data[1]);
					}
					else{
						message = "The " + data[1] + " election was not found!\nCould not remove.";
					}
					JOptionPane.showMessageDialog(this,message,"Election Removed",JOptionPane.PLAIN_MESSAGE);
				}
				else if(strIn.startsWith("<getElections>")){
					elections.clear();
					for(int i = 1; i < data.length;i++){
						elections.add(data[i]);
					}
					updateElectionList(ballotEdit);
				}
				else if(strIn.startsWith("<initElections>")){
					elections.clear();
					for(int i = 1; i < data.length;i++){
						elections.add(data[i]);
					}
				}
				else if(strIn.startsWith("<getRaces>")){
					currentRaces.clear();
					for(int i = 1; i < data.length;i++){
						currentRaces.add(data[i]);
					}
					updateRaceList(ballotEdit);
				}
				else if(strIn.startsWith("<pushCands>")){
					currentCands.clear();
					for(int i = 1; i < data.length;i++){
						currentCands.add(data[i]);
					}
					updateCandList(ballotEdit);

				}
				else if(strIn.startsWith("<addedCand>")){
					System.out.println("Added Cand: " + data[3] + " to Race: " + data[2]+" in Election: "+ data[1]);
				}
				else if(strIn.startsWith("<removedCand>")){
					System.out.println("Removed Cand: " + data[3] + " from Race: " + data[2]+" in Election: "+ data[1]);
				}
				else if(strIn.startsWith("<addedRace>")){
					System.out.println("Added Race: " + data[1] + " to election: " + data[2]);
				}
				else if(strIn.startsWith("<removedRace>")){
					System.out.println("removed Race: " + data[1] + " from election: " + data[2]);
				}
				else if(strIn.startsWith("<voteReceived>")){
					if(data[1].equals("Success")){
						JOptionPane.showMessageDialog(this,"Your ballot has been cast!","Voting Compete!",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(this,"You have already voted?","Voting Error!",JOptionPane.PLAIN_MESSAGE);
					}
				}
				else if(strIn.startsWith("<voteCounts>")){
					StringBuilder n = new StringBuilder("Election: "+selectedElection+" Results.\n");
					int numRaces = Integer.parseInt(data[1]);
					int i = 1;
					String currCand;
					String currRace;
					HashMap<String,HashMap<String,Integer>> electionResults = new HashMap<String,HashMap<String,Integer>>();
					for(int j = 0; j<numRaces;j++){
						i++;
						n.append("race:"+data[i]+"\n");
						currRace = data[i];
						i++;
						int numCands = (Integer.parseInt(data[i]));
						HashMap<String,Integer> candVotes = new HashMap<String,Integer>();
						for(int k=0; k < numCands;k++){
							i++;
							n.append("\tcand:"+data[i]+" >> ");
							currCand = data[i];
							i++;
							n.append("votes:"+data[i]+"\n");
							candVotes.put(currCand, Integer.parseInt(data[i]));
						}
						electionResults.put(currRace, candVotes);

						if(i == data.length-1) break;

					}
					System.out.println(n.toString());
					JOptionPane.showMessageDialog(this,n.toString(),"Voting Results!",JOptionPane.PLAIN_MESSAGE);
					exportResults(electionResults);
				}
				else if(strIn.startsWith("<electStructure>")){
					int numRaces = Integer.parseInt(data[1]);
					int i = 1;
					String currRace;
					for(int j = 0; j<numRaces;j++){
						i++;
						currRace = data[i];
						i++;
						int numCands = (Integer.parseInt(data[i]));
						ArrayList<String> cands = new ArrayList<String>();
						for(int k=0; k < numCands;k++){
							i++;
							cands.add(data[i]);
						}
						electStructure.put(currRace, cands);
						if(i == data.length-1) break;
						System.out.println(electStructure.entrySet());
					}
					
				    String code = JOptionPane.showInputDialog(
				            this, 
				            "Enter elections password", 
				            "Password Protected", 
				            JOptionPane.WARNING_MESSAGE
				        );
				    if(code!=null && code.equals("password")){//REPLACE FIRST MEMEBR OF ELECT STRUCT PRINT WRITE WITH THE KEY INSTED AND ADJUST ALL ALGS ACCORDINGLY
				    	initStudentVote();
						changeView(pnlStudentVoteView);
				    }
				    else{
				    	JOptionPane.showMessageDialog(this,"Incorrect Password!","Password Protected",JOptionPane.PLAIN_MESSAGE);
				    }
				}
				else
					JOptionPane.showMessageDialog(this,strIn,"Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			}
		}catch(IOException e){
			System.out.println("Server offline. Exiting...");
			//JOptionPane.showMessageDialog(this,"Server is offline\nNow exiting...","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}catch(NullPointerException npe){
			System.out.println("User Quit @ null @ ClientFromServer");
			System.out.println(npe.getMessage());
			pwOut.println("<die>");
			System.exit(0);
		}

	}

	//**********---SERVER RESPONSE HANDLING END---**********

	private void changeView(JPanel currentView){
		getContentPane().removeAll();
		getContentPane().add(currentView);
		getContentPane().doLayout();
		getContentPane().revalidate();
		getContentPane().repaint();
	}

	//**********---GUI INITIALIZATIONS START---**********

	private void initMain(){
		setTitle("Election System - Home");
		setSize(450,300);
		pnlHomeView.removeAll();
		pnlHomeView.setBackground(Color.GRAY);
		pnlHomeView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlHomeView.setLayout(null);

		JLabel lblTitle = new JLabel("Welcome " + userType);
		lblTitle.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 12));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(0, 11, 450, 14);
		pnlHomeView.add(lblTitle);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(335, 227, 89, 23);
		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlHomeView.add(btnLogout);

		JLabel lblElections = new JLabel("Available Elections");
		lblElections.setHorizontalAlignment(SwingConstants.CENTER);
		lblElections.setBounds(36, 36, 151, 14);
		pnlHomeView.add(lblElections);

		lstElections.setBounds(36, 57, 151, 175);
		pnlHomeView.add(lstElections);

		JPanel pnlButtons = new JPanel(new GridLayout(6, 1, 0, 0));
		pnlButtons.setBounds(263, 57, 151, 175);
		pnlButtons.setBackground(Color.GRAY);
		pnlHomeView.add(pnlButtons);

		final JButton btnViewElection = new JButton("View Ballot");
		final JButton btnRemoveElection = new JButton("Remove Election");
		final JButton btnOpenEdit = new JButton("Open EditMode");

		lstElections.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedElection = lstElections.getSelectedValue();
					if(selectedElection != null){
						btnViewElection.setEnabled(true);
						btnRemoveElection.setEnabled(true);
						if(userType.equals("Commissioner")){
							pnlButtons.add(btnOpenEdit);
							pnlHomeView.revalidate();
							pnlHomeView.repaint();
						}
					}
					else{
						btnViewElection.setEnabled(false);
						btnRemoveElection.setEnabled(false);
						if(userType.equals("Commissioner")){
							pnlButtons.remove(btnOpenEdit);
							pnlHomeView.revalidate();
							pnlHomeView.repaint();
						}
					}
				}
			}
		});



		//Buttons ----
		switch(userType){
		case "Commissioner":
			btnOpenEdit.setActionCommand("openEdit");
			btnOpenEdit.addActionListener(this);

		case "Student":
			btnViewElection.setActionCommand("viewElection");
			btnViewElection.addActionListener(this);
			btnViewElection.setEnabled(false);
			pnlButtons.add(btnViewElection);
			break;

		case "Admin":
			JButton btnAddElection = new JButton("Add Election");
			pnlButtons.add(btnAddElection);
			btnAddElection.setActionCommand("addElection");
			btnAddElection.addActionListener(this);
			pnlButtons.add(btnAddElection);

			pnlButtons.add(btnRemoveElection);
			btnRemoveElection.setEnabled(false);
			btnRemoveElection.setActionCommand("removeElection");
			btnRemoveElection.addActionListener(this);
			pnlButtons.add(btnRemoveElection);
			break;
		}
		updateElectionList(pnlHomeView);
	}
	
	private void initBallotEdit2(){
		setSize(525,400);
		currentCands.clear();
		ballotEdit = new ElectionEditPanel(lstRaces,lstCandidates);
		pwOut.println("<getRaces>,"+selectedElection);

	}

	private void initStudentVote(){
		setTitle("Election System - Vote");
		setSize(500,450);
		pnlStudentVoteView.removeAll();
		pnlStudentVoteView.setBackground(Color.GRAY);
		pnlStudentVoteView.setBorder(new EmptyBorder(5, 5, 5, 5));

		ArrayList<JTable> tabs = new ArrayList<JTable>();
		int columnsNeeded = (elections.size() > 6) ? 4: (int) Math.ceil(Math.sqrt(elections.size()));
		int rowsNeeded =  (elections.size() < 4)? 1 : (int)Math.ceil((long)elections.size()/3.0);
		pnlStudentVoteView.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(38, 36, 401, 300);
		pnlStudentVoteView.add(panel);
		panel.setLayout(new GridLayout(rowsNeeded,columnsNeeded));

		JLabel lblBallotTitle = new JLabel(selectedElection + " - BALLOT");
		lblBallotTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotTitle.setFont(new Font("Calibri", Font.BOLD, 19));
		lblBallotTitle.setBounds(10, 11, 464, 14);
		pnlStudentVoteView.add(lblBallotTitle);

		JButton btnVote = new JButton("VOTE");
		btnVote.setBounds(381, 377, 93, 23);
		pnlStudentVoteView.add(btnVote);
		btnVote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JTable t : tabs){
					if(t.getSelectedRow() != -1)
						voteChoices.put(t.getColumnName(0).substring(4),(String) t.getValueAt(t.getSelectedRow(),0));
				}
				System.out.println("VOTE CAST >> CHOICES: "+voteChoices.entrySet());

				for(String race : voteChoices.keySet()){
					pwOut.println("<vote>,"+userID+","+selectedElection+","+race+","+voteChoices.get(race));
					System.out.println("new Vote for : " + voteChoices.get(race));
				}
				voteChoices.clear();
				pwOut.println("<voteDone>");
				initMain();
				changeView(pnlHomeView);
				setSize(450,300);
			}
		});

		JButton btnExitBallot = new JButton("Exit");
		btnExitBallot.setBounds(10, 377, 93, 23);
		pnlStudentVoteView.add(btnExitBallot);
		btnExitBallot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initMain();
				changeView(pnlHomeView);
				setSize(450,300);
				electStructure.clear();				
			}
		});

		JButton btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (JTable t : tabs){
					t.clearSelection();
				}
			}
		});
		btnRestart.setBounds(203, 377, 93, 23);
		pnlStudentVoteView.add(btnRestart);

		for(String s : electStructure.keySet()){
			JPanel tabPan = new JPanel();
			JTable dynamicTable = new JTable();
			dynamicTable.setShowGrid(false);
			dynamicTable.setCellSelectionEnabled(true);

			dynamicTable.setModel(new DefaultTableModel(0,0));
			DefaultTableModel dtm = new DefaultTableModel(0, 0);

			String header[] = new String[] {"For "+s};
			dtm.setColumnIdentifiers(header);
			dynamicTable.setModel(dtm);

			// add row dynamically into the table
			for (String x : electStructure.get(s)) {
				dtm.addRow(new Object[]{x});
			}

			JPanel tablePanel = new JPanel(new BorderLayout());
			tablePanel.add(dynamicTable, BorderLayout.CENTER);
			tablePanel.add(dynamicTable.getTableHeader(), BorderLayout.NORTH);
			tabPan.add(tablePanel);
			panel.add(tabPan);
			tabs.add(dynamicTable);
		}


	}

	//**********---GUI INITIALIZATIONS END---**********

	//**********---LIST UPDATERS BEGIN---**********

	private void updateElectionList(JPanel sourcePanel){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String electName : elections){
			model.addElement(electName);
		}
		lstElections.setModel(model);
		sourcePanel.repaint();
		sourcePanel.revalidate();
	}

	private void updateRaceList(JPanel sourcePanel){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String raceName : currentRaces){
			model.addElement(raceName);
		}
		lstRaces.setModel(model);
		sourcePanel.repaint();
		sourcePanel.revalidate();
	}

	private void updateCandList(JPanel sourcePanel){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String candName : currentCands){
			model.addElement(candName);
		}
		lstCandidates.setModel(model);
		sourcePanel.repaint();
		sourcePanel.revalidate();
	}

	//**********---LIST UPDATERS END---**********

	//**********---ACTIONS PERFORMED BEGIN---**********

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){
		if (!sock.isClosed()){
			switch (e.getActionCommand()){
			case "login":
				pwOut.println("<login>," + txtName.getText() + "," + txtPass.getText());
				System.out.println("<login>," + txtName.getText() + "," + txtPass.getText());
				break;
			case "logout":
				System.out.println("Logging out...");
				pwOut.println("<logout>,");
				txtName.setText("");
				txtPass.setText("password");
				getContentPane().removeAll();
				changeView(pnlMain);
				pack();
				setTitle("Election Login");
				setLocationRelativeTo(null);
				break;
			case "openEdit":
				initBallotEdit2();
				changeView(ballotEdit);
				updateRaceList(ballotEdit);
				break;
			case "exitElectionEdit":
				System.out.println("Exit Edit Mode");
				getContentPane().setLayout(new BorderLayout());
				changeView(pnlAdminView);
				break;
			case "viewResults":
				System.out.println("ResultsView");
				pwOut.println("<getVoteCount>," +selectedElection);
				//changeView(RESULTS);
				break;
			case "addElection":
				pnlAddElectionView = new ElectionCreationGUI();				
				changeView(pnlAddElectionView);
				ActionListener finBut = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						pwOut.println("<addElection>,"+pnlAddElectionView.electionName.getText()+","+pnlAddElectionView.commissName.getText());
					}
				};
				pnlAddElectionView.setActnList(finBut);

				System.out.println("addElection");
				break;
			case "removeElection":
				System.out.println("removeElection");
				if( lstElections.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select election to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					pwOut.println("<removeElection>,"+ selectedElection);
					pwOut.println("<getElections>,");
					selectedElection = null;
				}
				break;
			case "viewElection":
				selectedElection = lstElections.getSelectedValue();
				selectedRace = null;
				pwOut.println("<getElectionStructure>,"+selectedElection);
				System.out.println("Opening Election View");
				break;
			case "addRace":
				String newRaceName = JOptionPane.showInputDialog(this, "Enter Race Name to add:").toString();
				currentCands.clear();
				pwOut.println("<addRace>,"+selectedElection+","+newRaceName);
				pwOut.println("<getRaces>,"+selectedElection);
				updateCandList(ballotEdit);
				break;
			case "removeRace":
				if(lstRaces.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select race to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					currentCands.clear();
					pwOut.println("<removeRace>,"+selectedElection+","+selectedRace);
					pwOut.println("<getRaces>,"+selectedElection);
					updateCandList(ballotEdit);
				}
				break;
			case "addCand":
				if(lstRaces.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select race to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					String newCandName = JOptionPane.showInputDialog(this, "Enter Cand Name to add:").toString();
					pwOut.println("<addCand>,"+selectedElection+","+selectedRace+","+newCandName);
					pwOut.println("<getCands>,"+selectedElection+","+selectedRace);
				}
				break;
			case "removeCand":
				if(lstRaces.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select race to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else if(lstCandidates.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select cand to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					String removeCandName = lstCandidates.getSelectedValue();
					pwOut.println("<removeCand>,"+selectedElection+","+selectedRace+","+removeCandName);
					pwOut.println("<getCands>,"+selectedElection+","+selectedRace);
				}
				break;
			case "finalizeBallot":

			case "exitBallotEdit":
				System.out.println("Exiting Ballot editor");
				pwOut.println("<getElections>,");
				changeView(pnlHomeView);
				setSize(450,300);
				break;
			case "quit":
				pwOut.println("<clientQuit>");
				System.exit(0);
				break;
			}
		}else{
			JOptionPane.showMessageDialog(this,"Socket is Closed","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	//**********---ACTIONS PERFORMED END---**********

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		new Client();

	}

	public void exportResults(HashMap<String,HashMap<String,Integer>> results){
		System.out.println("@ExportResults\n"+results.entrySet());
	}
	
	public static void raceSelected(){
		pwOut.println("<getCands>,"+selectedElection+","+selectedRace);
	}

}
