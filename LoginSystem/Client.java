import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

class Client extends JFrame implements ActionListener{

	BufferedReader brIn;
	PrintWriter pwOut;
	Socket sock;
	private JTextField txtName;
	private JPasswordField txtPass;
	private JPanel pnlMain = new JPanel();
	private JPanel pnlStudentVoteView = new JPanel();
	private JPanel pnlStudentView = new JPanel(new BorderLayout());
	private JPanel pnlAdminView = new JPanel(new GridBagLayout());
	private JPanel pnlElectEditView = new JPanel(new GridBagLayout());
	private JPanel pnlBallotEditView = new JPanel(new GridBagLayout());
	private Set<String> elections = new HashSet<String>();
	private Set<String> currentRaces = new HashSet<String>();
	private ArrayList<String> currentCands = new ArrayList<String>();
	private JList<String> lstElections = new JList<String>();
	private JList<String> lstCandidates = new JList<String>();
	private JList<String> lstRaces = new JList<String>();
	private boolean isVoting = false;

	private String userName = "";
	private String userID = "";
	private String userType = "";
	private String selectedElection = null;
	private String selectedRace = null;
	private String selectedCand = null;
	private HashMap<String,String> voteChoices = new HashMap<String,String>();


	Client(){
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
		pack();

		setLocationRelativeTo(null);
		setVisible(true);
		run();
	}
	
	private void run(){
		try{
			sock = new Socket("127.0.0.1",50000);
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
					userType = data[1]; 
					userName = data[2];
					userID = data[3];

					if(userType.equals("Student")){
						System.out.println("Student has logged in.");
						initStudent();
						changeView(pnlStudentView);
						setSize(450,300);
					}
					else if(userType.equals("Admin")){
						System.out.println("Admin has logged in.");
						initAdmin();
						changeView(pnlAdminView);
						setSize(450,300);
					}
					else if(userType.equals("Commissioner")){
						System.out.println("Commissioner has logged in.");
						initAdmin();
						changeView(pnlAdminView);
						setSize(450,300);
					}
					continue;
				}
				else if (strIn.startsWith("<AddedElection>")){
					String message = "The " + data[1] + " election has been created.\n"+data[2] + " is the Election Commissioner.";
					JOptionPane.showMessageDialog(this,message,"Election Created",JOptionPane.PLAIN_MESSAGE);
					System.out.println("Election created: " + data[1] + " : Commissioner: " + data[2]);
					continue;
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
					continue;
				}
				else if(strIn.startsWith("<getElections>")){
					elections.clear();
					for(int i = 1; i < data.length;i++){
						elections.add(data[i]);
					}
					System.out.println("@Client: elctions here are " + elections.toString());
					updateElectionList(pnlElectEditView);
					continue;
				}
				else if(strIn.startsWith("<initElections>")){
					elections.clear();
					for(int i = 1; i < data.length;i++){
						elections.add(data[i]);
					}
					System.out.println("@Client: INITIAL elctions here are " + elections.toString());
					continue;
				}
				else if(strIn.startsWith("<getRaces>")){
					currentRaces.clear();
					for(int i = 1; i < data.length;i++){
						currentRaces.add(data[i]);
					}
					System.out.println("@Client: races here are " + currentRaces.toString());
					if(!isVoting){
						updateRaceList(pnlBallotEditView);
					}
					else{
						updateRaceList(pnlStudentVoteView);

					}
					continue;
				}
				else if(strIn.startsWith("<pushCands>")){
					currentCands.clear();
					for(int i = 1; i < data.length;i++){
						currentCands.add(data[i]);
					}
					if(!isVoting){
						updateCandList(pnlBallotEditView);
					}
					else{
						updateCandList(pnlStudentVoteView);
					}
					continue;
				}
				else if(strIn.startsWith("<addedCand>")){
					System.out.println("Added Cand: " + data[3] + " to Race: " + data[2]+" in Election: "+ data[1]);
					continue;
				}
				else if(strIn.startsWith("<removedCand>")){
					System.out.println("Removed Cand: " + data[3] + " from Race: " + data[2]+" in Election: "+ data[1]);
					continue;
				}
				else if(strIn.startsWith("<addedRace>")){
					System.out.println("Added Race: " + data[1] + " to election: " + data[2]);
					continue;
				}
				else if(strIn.startsWith("<removedRace>")){
					System.out.println("removed Race: " + data[1] + " from election: " + data[2]);
					continue;
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
					exportResults(electionResults);
				}
				else
					JOptionPane.showMessageDialog(this,strIn,"Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			}
		}catch(IOException e){
			System.out.println("Server offline. Exiting...");
			JOptionPane.showMessageDialog(this,"Server is offline\nNow exiting...","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}catch(NullPointerException npe){
			System.out.println("User Quit @ null @ ClientFromServer");
			System.out.println(npe.getMessage());
			pwOut.println("<die>");
			System.exit(0);
		}

	}

	private void changeView(JPanel currentView){
		getContentPane().removeAll();
		getContentPane().add(currentView);
		getContentPane().doLayout();
		getContentPane().revalidate();
		getContentPane().repaint();

	}

	private void initStudent(){
		setTitle("Election System - Home");
		setSize(450,300);
		pnlStudentView.removeAll();
		pnlStudentView.setBackground(Color.GRAY);
		pnlStudentView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlStudentView.setLayout(null);

		JLabel lblTitle = new JLabel("Welcome Student");
		lblTitle.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 12));
		lblTitle.setBounds(186, 11, 71, 14);
		pnlStudentView.add(lblTitle);

		JButton btnViewElection = new JButton("View Election");
		btnViewElection.setBounds(311, 127, 111, 23);
		btnViewElection.setActionCommand("viewElection");
		btnViewElection.addActionListener(this);
		pnlStudentView.add(btnViewElection);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setBounds(335, 227, 89, 23);
		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlStudentView.add(btnLogout);

		btnViewElection.setEnabled(false);

		updateElectionList(pnlStudentView);
		lstElections.setBounds(131, 49, 170, 149);
		pnlStudentView.add(lstElections);

		lstElections.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedElection = lstElections.getSelectedValue();
					if(selectedElection != null){
						btnViewElection.setEnabled(true);
					}
					else{
						btnViewElection.setEnabled(false);
					}
				}
			}
		});
	}

	private void initAdmin(){
		setTitle("Election System - Home");
		pnlAdminView.removeAll();
		pnlAdminView.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel greeting = new JLabel("Welcome " + userType);
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10,10,10,10);  //top padding
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 0;
		pnlAdminView.add(greeting, c);

		JButton btnOpenEdit = new JButton("Open EditMode");
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LAST_LINE_START;
		c.fill = GridBagConstraints.NONE;
		pnlAdminView.add(btnOpenEdit, c);

		btnOpenEdit.setActionCommand("openEdit");
		btnOpenEdit.addActionListener(this);

		c.gridx = 2;
		c.gridy = 2;
		c.ipadx = 40;
		c.anchor = GridBagConstraints.LAST_LINE_END;

		JButton btnLogout = new JButton("Logout");
		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlAdminView.add(btnLogout, c);
	}

	private void initElectEdit(){
		setTitle("Election System - Edit Elections");
		setSize(450,300);
		pnlElectEditView.removeAll();
		pnlElectEditView.setBackground(Color.GRAY);
		pnlElectEditView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlElectEditView.setLayout(null);

		JLabel lblTitle = new JLabel("Edit Mode");
		lblTitle.setFont(new Font("Lucida Sans Typewriter", Font.BOLD, 12));
		lblTitle.setBounds(186, 11, 71, 14);
		pnlElectEditView.add(lblTitle);

		JButton btnExitEdit = new JButton("Exit");
		btnExitEdit.setBounds(335, 227, 89, 23);
		pnlElectEditView.add(btnExitEdit);
		btnExitEdit.setActionCommand("exitElectionEdit");
		btnExitEdit.addActionListener(this);

		JButton btnAddElection = new JButton("Add Election");
		btnAddElection.setBounds(311, 93, 111, 23);
		btnAddElection.setActionCommand("addElection");
		btnAddElection.addActionListener(this);

		if(userType.equals("Admin")){
			pnlElectEditView.add(btnAddElection);
		}

		JButton btnRemoveElection = new JButton("Remove Election");
		btnRemoveElection.setBounds(311, 127, 111, 23);
		btnRemoveElection.setActionCommand("removeElection");
		btnRemoveElection.addActionListener(this);

		if(userType.equals("Admin")){
			pnlElectEditView.add(btnRemoveElection);
		}

		JButton btnViewResults = new JButton("View Results");
		btnViewResults.setBounds(10, 93, 111, 23);
		pnlElectEditView.add(btnViewResults);
		btnViewResults.setActionCommand("viewResults");
		btnViewResults.addActionListener(this);

		JButton btnEditBallot = new JButton("Edit Ballot");
		btnEditBallot.setEnabled(false);
		btnEditBallot.setBounds(311, 161, 111, 23);
		btnEditBallot.setActionCommand("editBallot");
		btnEditBallot.addActionListener(this);

		if(userType.equals("Commissioner")){
			pnlElectEditView.add(btnEditBallot);
		}

		lstElections.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedElection = lstElections.getSelectedValue();
					if(selectedElection!= null){
						btnRemoveElection.setEnabled(true);
						btnEditBallot.setEnabled(true);
						btnViewResults.setEnabled(true);
					}
					else{
						System.out.println("No Selection @ electionList");
						btnRemoveElection.setEnabled(false);
						btnEditBallot.setEnabled(false);
						btnViewResults.setEnabled(false);
					}
				}
			}
		});

		btnRemoveElection.setEnabled(false);
		btnEditBallot.setEnabled(false);
		btnViewResults.setEnabled(false);

		updateElectionList(pnlElectEditView);
		System.out.println("Refreshing election list @ ElectView");
		lstElections.setBounds(131, 49, 170, 149);
		pnlElectEditView.add(lstElections);
	}

	private void initBallotEdit(){
		isVoting = true;
		setTitle("Election System - Edit Ballot");
		setSize(450,300);
		pnlBallotEditView.removeAll();
		pnlBallotEditView.setBackground(Color.GRAY);
		pnlBallotEditView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlBallotEditView.setLayout(null);

		JLabel lblEditBallot = new JLabel("Edit Ballot");
		lblEditBallot.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditBallot.setBounds(0, 11, 450, 14);
		pnlBallotEditView.add(lblEditBallot);

		JLabel lblBallotRaces = new JLabel("Races");
		lblBallotRaces.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotRaces.setBounds(64, 36, 99, 14);
		pnlBallotEditView.add(lblBallotRaces);

		JButton btnAddRace = new JButton("Add  Race");
		btnAddRace.setBounds(64, 148, 99, 23);
		pnlBallotEditView.add(btnAddRace);
		btnAddRace.setActionCommand("addRace");
		btnAddRace.addActionListener(this);

		JButton btnRemoveRace = new JButton("Remove Race");
		btnRemoveRace.setBounds(64, 171, 99, 23);
		pnlBallotEditView.add(btnRemoveRace);
		btnRemoveRace.setActionCommand("removeRace");
		btnRemoveRace.addActionListener(this);

		JLabel lblBallotCadidates = new JLabel("Cadidates");
		lblBallotCadidates.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotCadidates.setBounds(263, 36, 123, 14);
		pnlBallotEditView.add(lblBallotCadidates);

		lstCandidates.setBounds(263, 61, 123, 87);
		pnlBallotEditView.add(lstCandidates);

		JButton btnAddCandidate = new JButton("Add Candidate");
		btnAddCandidate.setBounds(263, 148, 123, 23);
		pnlBallotEditView.add(btnAddCandidate);
		btnAddCandidate.setActionCommand("addCand");
		btnAddCandidate.addActionListener(this);

		JButton btnRemoveCandidate = new JButton("Remove Candidate");
		btnRemoveCandidate.setBounds(263, 171, 123, 23);
		pnlBallotEditView.add(btnRemoveCandidate);
		btnRemoveCandidate.setActionCommand("removeCand");
		btnRemoveCandidate.addActionListener(this);

		JRadioButton rdbtnMultSelect = new JRadioButton("Allow multiple selection");
		rdbtnMultSelect.setBounds(64, 201, 135, 23);
		pnlBallotEditView.add(rdbtnMultSelect);

		JButton btnExitEdit = new JButton("Exit");
		btnExitEdit.setBounds(335, 227, 89, 23);
		pnlBallotEditView.add(btnExitEdit);
		btnExitEdit.setActionCommand("exitBallotEdit");
		btnExitEdit.addActionListener(this);

		lstRaces = new JList<String>();
		lstRaces.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedRace = lstRaces.getSelectedValue();
					if(selectedRace != null){
						pwOut.println("<getCands>,"+selectedElection+","+selectedRace);
						btnRemoveRace.setEnabled(true);
						btnAddCandidate.setEnabled(true);
						btnRemoveCandidate.setEnabled(true);
					}
					else{
						btnRemoveRace.setEnabled(false);
						btnAddCandidate.setEnabled(false);
						btnRemoveCandidate.setEnabled(false);
					}
				}
			}
		});

		btnRemoveRace.setEnabled(false);
		btnAddCandidate.setEnabled(false);
		btnRemoveCandidate.setEnabled(false);

		lstRaces.setBounds(64, 61, 99, 87);
		updateRaceList(pnlBallotEditView);
		pnlBallotEditView.add(lstRaces);
	}

	private void initStudentVote(){
		isVoting = true;
		setTitle("Election System - Vote");
		setSize(460,460);
		pnlStudentVoteView.removeAll();
		pnlStudentVoteView.setBackground(Color.GRAY);
		pnlStudentVoteView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlStudentVoteView.setLayout(null);

		JLabel lblBallotTitle = new JLabel(selectedElection + " - BALLOT");
		lblBallotTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotTitle.setBounds(0, 11, 450, 14);
		pnlStudentVoteView.add(lblBallotTitle);

		JButton btnVote = new JButton("VOTE");
		btnVote.setBounds(351, 395, 89, 23);
		pnlStudentVoteView.add(btnVote);
		btnVote.setActionCommand("vote");
		btnVote.addActionListener(this);

		JButton btnNextRace = new JButton("NEXT RACE");
		btnNextRace.setBounds(351, 214, 89, 23);
		pnlStudentVoteView.add(btnNextRace);
		btnNextRace.setActionCommand("nextRace");
		btnNextRace.addActionListener(this);

		JLabel lblRace = new JLabel("Race");
		lblRace.setHorizontalAlignment(SwingConstants.CENTER);
		lblRace.setBounds(10, 67, 122, 14);
		pnlStudentVoteView.add(lblRace);

		JLabel lblCandidates = new JLabel("Candidates");
		lblCandidates.setHorizontalAlignment(SwingConstants.CENTER);
		lblCandidates.setBounds(153, 65, 122, 14);
		pnlStudentVoteView.add(lblCandidates);

		currentCands.clear();
		lstCandidates.setBounds(153, 85, 122, 315);
		pnlStudentVoteView.add(lstCandidates);

		lstRaces = new JList<String>();
		updateRaceList(pnlStudentVoteView);
		lstRaces.setBounds(21, 85, 122, 315);
		pnlStudentVoteView.add(lstRaces);

		btnNextRace.setEnabled(false);

		lstRaces.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedRace = lstRaces.getSelectedValue();
					if(selectedRace != null){
						pwOut.println("<getRandCands>,"+selectedElection+","+selectedRace);
					}
					else{
						btnNextRace.setEnabled(false);
					}
				}
			}
		});

		lstCandidates.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					selectedCand = lstCandidates.getSelectedValue();
					if(selectedCand != null){
						btnNextRace.setEnabled(true);
					}
					else{
						btnNextRace.setEnabled(false);
					}
				}
			}
		});
	}

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

	public void actionPerformed(ActionEvent e){
		if (!sock.isClosed()){
			switch (e.getActionCommand()){
			case "login":
				pwOut.println("<login>," + txtName.getText() + "," + txtPass.getText());
				System.out.println("<login>," + txtName.getText() + "," + txtPass.getText());
				break;
			case "logout":
				System.out.println("Logging out...");
				txtName.setText("");
				txtPass.setText("password");
				getContentPane().removeAll();
				changeView(pnlMain);
				pack();
				setTitle("Election Login");
				setLocationRelativeTo(null);
				break;
			case "openEdit":
				System.out.println("Opening Edit Mode");
				pwOut.println("<initElections>,");
				initElectEdit();
				changeView(pnlElectEditView);
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
				System.out.println("addElection");

				JPanel pnlGetAddElectionInfo = new JPanel();
				JTextField txtAddElectionName = new JTextField(15);
				String addElectionName = "";
				JTextField txtAddElectionCommissioner = new JTextField(15);
				String addElectionCommissionerName = "";
				pnlGetAddElectionInfo.add(new JLabel("Election Name:"));
				pnlGetAddElectionInfo.add(txtAddElectionName);
				pnlGetAddElectionInfo.add(Box.createHorizontalStrut(15)); // a spacer
				pnlGetAddElectionInfo.add(new JLabel("Commissioner Name:"));
				pnlGetAddElectionInfo.add(txtAddElectionCommissioner);

				int addElectionChoice = JOptionPane.showConfirmDialog(null, pnlGetAddElectionInfo, "Add Election", JOptionPane.OK_CANCEL_OPTION);
				if (addElectionChoice == JOptionPane.OK_OPTION) {
					addElectionName = txtAddElectionName.getText();
					addElectionCommissionerName = txtAddElectionCommissioner.getText();
				}
				else{
					break;
				}
				pwOut.println("<addElection>,"+addElectionName+","+addElectionCommissionerName);
				pwOut.println("<getElections>,");
				selectedElection = null;
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
			case "editBallot":
				currentCands.clear();
				updateCandList(pnlBallotEditView);
				System.out.println("Opening Ballot editor");
				if( lstElections.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select election to edit its ballot!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					selectedElection = lstElections.getSelectedValue();
					initBallotEdit();
					changeView(pnlBallotEditView);
					pwOut.println("<getRaces>,"+selectedElection);

				}
				break;
			case "viewElection":

				selectedElection = lstElections.getSelectedValue();
				selectedRace = null;
				selectedCand = null;
				System.out.println("Opening Election View");
				pwOut.println("<getRaces>,"+selectedElection);
				currentCands.clear();
				updateCandList(pnlStudentVoteView);
				initStudentVote();
				changeView(pnlStudentVoteView);				
				break;
			case "addRace":
				String newRaceName = JOptionPane.showInputDialog(this, "Enter Race Name to add:").toString();
				currentCands.clear();
				pwOut.println("<addRace>,"+selectedElection+","+newRaceName);
				pwOut.println("<getRaces>,"+selectedElection);
				updateCandList(pnlBallotEditView);
				break;
			case "removeRace":
				if(lstRaces.getSelectedValue() == null){
					JOptionPane.showMessageDialog(this,"Must select race to remove!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					currentCands.clear();
					pwOut.println("<removeRace>,"+selectedElection+","+selectedRace);
					pwOut.println("<getRaces>,"+selectedElection);
					updateCandList(pnlBallotEditView);
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
			case "exitBallotEdit":
				System.out.println("Exiting Ballot editor");
				pwOut.println("<getElections>,");
				changeView(pnlElectEditView);
				break;
			case "nextRace":
				voteChoices.put(selectedRace,selectedCand);
				System.out.println("Next Race");
				currentRaces.remove(selectedRace);
				updateRaceList(pnlStudentVoteView);
				currentCands.clear();
				updateCandList(pnlStudentVoteView);
				System.out.println(voteChoices.entrySet().toString());
				break;
			case "vote":
				System.out.println("Voted");
				for(String race : voteChoices.keySet()){
					pwOut.println("<vote>,"+userID+","+selectedElection+","+race+","+voteChoices.get(race));
					System.out.println("new Vote for : " + voteChoices.get(race));
				}
				voteChoices.clear();
				pwOut.println("<voteDone>");
				initStudent();
				changeView(pnlStudentView);
				setSize(450,300);
				break;
			case "quit":
				pwOut.println("<die>");
				System.exit(0);
				break;
			}
		}else{
			JOptionPane.showMessageDialog(this,"Socket is Closed","Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"); 
		new Client();

	}
	
	public void exportResults(HashMap<String,HashMap<String,Integer>> results){
		System.out.println("@ExportResults\n"+results.entrySet());
		
	}
	
}
