import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	private JPanel pnlStudentView = new JPanel(new BorderLayout());
	private JPanel pnlAdminView = new JPanel(new GridBagLayout());
	private JPanel pnlElectEditView = new JPanel(new GridBagLayout());
	private JPanel pnlBallotEditView = new JPanel(new GridBagLayout());
	private JButton btnQuit = new JButton("Quit");
	private Set<String> elections = new HashSet<String>();
	private Set<String> currentRaces = new HashSet<String>();
	private JList<String> lstElections = new JList<String>();
	private JList<String> lstEditBallotCands = new JList<String>();
	private JList<String> lstEditBallotRaces = new JList<String>();
	private JButton btnEditBallot;

	private String userName = "";
	private String userType = "";
	private String selectedElection = null;

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
		JLabel lblPass = new JLabel("Password: ");
		pnlPassword.add(lblPass);
		pnlPassword.add(txtPass);

		JButton btnLogin = new JButton("Login");
		btnLogin.setActionCommand("login");
		btnLogin.addActionListener(this);

		btnQuit.setActionCommand("quit");
		btnQuit.addActionListener(this);

		pnlButtons.add(btnLogin);
		pnlButtons.add(btnQuit);

		pnlMain.add(pnlName);
		pnlMain.add(pnlPassword);
		pnlMain.add(pnlButtons);

		getContentPane().add(pnlMain);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login Client");
		pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		setLocation(x, y);

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
				System.out.println("STRIN IS : " + strIn);
				if (strIn.startsWith("<loggedS>")){
					System.out.println("Student has logged in.");
					userType = "Student"; 
					userName = data[1];
					initStudent();
					changeView(pnlStudentView);
					continue;
				}
				else if (strIn.startsWith("<loggedA>")){
					System.out.println("Admin has logged in.");
					userType = "Admin"; 
					userName = data[1];
					initAdmin();
					changeView(pnlAdminView);
					setSize(400,300);
				}
				else if (strIn.startsWith("<loggedC>")){
					System.out.println("Commissioner has logged in.");
					userType = "Commissioner"; 
					userName = data[1];
					initAdmin();
					changeView(pnlAdminView);
					setSize(400,300);
				}
				else if (strIn.startsWith("<loggedF>")){
					System.out.println("Commissioner has logged in.");
					JOptionPane.showMessageDialog(this,"Login Failed!","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
				}
				else if (strIn.startsWith("<AddedElection>")){
					String message = "The " + data[1] + " election has been created.\n"+data[2] + " is the Election Commissioner.";
					JOptionPane.showMessageDialog(this,message,"Election Created",JOptionPane.PLAIN_MESSAGE);
					System.out.println("Election created: " + data[1] + " : Commissioner: " + data[2]);
				}
				else if (strIn.startsWith("<removedElection>")){
					String message = "";
					if(data.length == 2){
						if(data[1].equals("noSelection")){
							message = "No selection made!\nCould not remove.";
							System.out.println("Could not remove, no slection made");
						}
						else{
							message = "The " + data[1] + " election has been removed.";
							System.out.println("Election removed: " + data[1]);
						}
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
					System.out.println("@Client: elctions here are " + elections.toString());
					updateElectionList();
				}
				else if(strIn.startsWith("<initElections>")){
					elections.clear();
					for(int i = 1; i < data.length;i++){
						elections.add(data[i]);
					}
					System.out.println("@Client: INITIAL elctions here are " + elections.toString());
				}
				else if(strIn.startsWith("<getRaces>")){
					currentRaces.clear();
					for(int i = 1; i < data.length;i++){
						currentRaces.add(data[i]);
					}
					System.out.println("@Client: races here are " + currentRaces.toString());
					updateRaceList();
				}
				else if(strIn.startsWith("<addedRace>")){
					System.out.println("Added Race: " + data[1] + " to election: " + data[2]);
				}
				else if(strIn.startsWith("<removedRace>")){
					if(data[1].equals("noSelection")){
						JOptionPane.showMessageDialog(this,"Could not remove, no slection made","Race Removed",JOptionPane.PLAIN_MESSAGE);

					}
					else{
						System.out.println("removed Race: " + data[1] + " from election: " + data[2]);
					}
				}
				else
					JOptionPane.showMessageDialog(this,strIn,"Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			}
		}catch(IOException e){
			System.out.println("Server offline. Exiting...");
			JOptionPane.showMessageDialog(this,"Server is offline\nNow exiting...","Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}catch(NullPointerException npe){
			System.out.println("User Quit");
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
		pnlStudentView.removeAll();
		JLabel greeting = new JLabel("Welcome Student", SwingConstants.CENTER);
		pnlStudentView.add(greeting, BorderLayout.PAGE_START);
		JButton btnLogout = new JButton("Logout");
		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlStudentView.add(btnLogout,BorderLayout.PAGE_END);
	}

	private void initAdmin(){
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

		btnEditBallot = new JButton("Edit Ballot");
		btnEditBallot.setEnabled(false);
		btnEditBallot.setBounds(311, 161, 111, 23);
		btnEditBallot.setActionCommand("editBallot");
		btnEditBallot.addActionListener(this);

		if(userType.equals("Commissioner")){
			pnlElectEditView.add(btnEditBallot);
		}

		updateElectionList();
		lstElections.setBounds(131, 49, 170, 149);
		pnlElectEditView.add(lstElections);
	}

	private void initBallotEdit(){
		setSize(450,300);
		pnlBallotEditView.removeAll();
		pnlBallotEditView.setBackground(Color.GRAY);
		pnlBallotEditView.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnlBallotEditView.setLayout(null);

		lstEditBallotRaces.setBounds(64, 61, 99, 87);
		pnlBallotEditView.add(lstEditBallotRaces);

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

		lstEditBallotCands.setBounds(263, 61, 123, 87);
		pnlBallotEditView.add(lstEditBallotCands);

		JButton btnAddCandidate = new JButton("Add Candidate");
		btnAddCandidate.setBounds(263, 148, 123, 23);
		pnlBallotEditView.add(btnAddCandidate);

		JButton btnRemoveCandidate = new JButton("Remove Candidate");
		btnRemoveCandidate.setBounds(263, 171, 123, 23);
		pnlBallotEditView.add(btnRemoveCandidate);

		JRadioButton rdbtnMultSelect = new JRadioButton("Allow multiple selection");
		rdbtnMultSelect.setBounds(64, 201, 135, 23);
		pnlBallotEditView.add(rdbtnMultSelect);

		JButton btnExitEdit = new JButton("Exit");
		btnExitEdit.setBounds(335, 227, 89, 23);
		pnlBallotEditView.add(btnExitEdit);
		btnExitEdit.setActionCommand("exitBallotEdit");
		btnExitEdit.addActionListener(this);
	}

	private void updateElectionList(){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String electName : elections){
			model.addElement(electName);
		}
		lstElections.setModel(model);
		if(lstElections.getModel().getSize() == 0){
			btnEditBallot.setEnabled(false);
		}
		else{
			btnEditBallot.setEnabled(true);
		}
		pnlElectEditView.repaint();
		pnlElectEditView.revalidate();
	}

	private void updateRaceList(){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String raceName : currentRaces){
			model.addElement(raceName);
		}
		lstEditBallotRaces.setModel(model);
		pnlBallotEditView.repaint();
		pnlBallotEditView.revalidate();
	}

	public void actionPerformed(ActionEvent e){
		if (!sock.isClosed()){
			switch (e.getActionCommand()){
			case "login":
				//pwOut.println("<getElections>,");
				pwOut.println("<login>," + txtName.getText() + "," + txtPass.getText());
				System.out.println("<login>," + txtName.getText() + "," + txtPass.getText());
				pwOut.println("<initElections>,");
				break;
			case "logout":
				System.out.println("Logging out...");
				txtName.setText("");
				txtPass.setText("");
				getContentPane().removeAll();
				getContentPane().add(pnlMain);
				pack();
				setLocationRelativeTo(null);
				break;
			case "openEdit":
				System.out.println("Opening Edit Mode");
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
				//initResults();
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
				selectedElection = (lstElections.getSelectedValue() != null) ? lstElections.getSelectedValue() : "noSelection";

				pwOut.println("<removeElection>,"+ selectedElection);
				pwOut.println("<getElections>,");
				selectedElection = null;
				break;
			case "editBallot":
				System.out.println("Opening Ballot editor");
				selectedElection = (lstElections.getSelectedValue() != null) ? lstElections.getSelectedValue() : "noSelection";
				if (selectedElection != null){
					initBallotEdit();
					changeView(pnlBallotEditView);
				}
				else{
					JOptionPane.showMessageDialog(this,"Must select election to edit!","Error Occurred",JOptionPane.PLAIN_MESSAGE);
				}
				break;
			case "addRace":
				String newRaceName = JOptionPane.showInputDialog(this, "Enter Race Name ot add:").toString();
				pwOut.println("<addRace>,"+selectedElection+","+newRaceName);
				pwOut.println("<getRaces>,"+selectedElection);
				break;
			case "removeRace":
				String removeRaceName = (lstEditBallotRaces.getSelectedValue() != null) ? lstEditBallotRaces.getSelectedValue(): "noSelection";
				pwOut.println("<removeRace>,"+selectedElection+","+removeRaceName);
				pwOut.println("<getRaces>,"+selectedElection);
				break;
			case "exitBallotEdit":
				System.out.println("Exiting Ballot editor");
				changeView(pnlElectEditView);
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

	public static void main(String args[]){
		new Client();
	}
}
