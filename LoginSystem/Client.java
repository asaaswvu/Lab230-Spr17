import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.net.*;
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
	private JPanel pnlEditView = new JPanel(new GridBagLayout());
	private JButton btnLogout = new JButton("Logout");
	private JButton btnQuit = new JButton("Quit");

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
				if (strIn.startsWith("<loggedS>")){
					System.out.println("Student has logged in.");
					initStudent();
					changeView(pnlStudentView);
					continue;
				}
				else if (strIn.startsWith("<loggedA>")){
					System.out.println("Admin has logged in.");
					initAdmin();
					changeView(pnlAdminView);
					setSize(400,300);
				}
				else if (strIn.startsWith("<AddedElection>")){
					String [] data = strIn.split(",");
					String message = "The " + data[1] + " election has been created.\n"+data[2] + " is the Election Commissioner.";
					JOptionPane.showMessageDialog(this,message,"Election Created",JOptionPane.PLAIN_MESSAGE);
					System.out.println("Election created: " + data[1] + " : Commissioner: " + data[2]);
				}
				else if (strIn.startsWith("<removedElection>")){
					String [] data = strIn.split(",");
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
				else
					JOptionPane.showMessageDialog(this,strIn,"Error Occurred!",JOptionPane.PLAIN_MESSAGE);
			}
		}catch(IOException e){
			System.out.println("Server not started. Start Server First.");
		}catch(NullPointerException npe){
			System.out.println("User Quit");
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
		JLabel greeting = new JLabel("Welcome Student", SwingConstants.CENTER);
		pnlStudentView.add(greeting, BorderLayout.PAGE_START);
		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlStudentView.add(btnLogout,BorderLayout.PAGE_END);
	}

	private void initAdmin(){
		pnlAdminView.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel greeting = new JLabel("Welcome Admin");
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

		btnLogout.setActionCommand("logout");
		btnLogout.addActionListener(this);
		pnlAdminView.add(btnLogout, c);
	}

	private void initEdit(){
		setSize(400,300);
		pnlEditView.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JLabel greeting = new JLabel("Edit Mode");
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10,10,10,10);  //top padding
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 1;
		c.gridy = 0;
		pnlEditView.add(greeting, c);

		JButton btnAddElection = new JButton("Add Election");
		c.weighty = 1;
		c.weightx = 1;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.insets = new Insets(10,10,10,10);  //top padding
		pnlEditView.add(btnAddElection,c);
		btnAddElection.setActionCommand("addElection");
		btnAddElection.addActionListener(this);

		JButton btnRemoveElection = new JButton("Remove Election");
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(10,10,10,10);  //top padding
		pnlEditView.add(btnRemoveElection,c);
		btnRemoveElection.setActionCommand("removeElection");
		btnRemoveElection.addActionListener(this);

		JButton btnExitEdit = new JButton("Exit");
		c.weightx = 1;
		c.weighty = 1;
		c.gridx = 2;
		c.gridy = 2;
		c.anchor = GridBagConstraints.LAST_LINE_END;
		pnlEditView.add(btnExitEdit,c);
		btnExitEdit.setActionCommand("exitEdit");
		btnExitEdit.addActionListener(this);
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
				txtPass.setText("");
				changeView(pnlMain);
				pack();
				setLocationRelativeTo(null);
				break;
			case "openEdit":
				System.out.println("Opening Edit Mode");
				initEdit();
				changeView(pnlEditView);
				break;
			case "exitEdit":
				System.out.println("Exit Edit Mode");
				getContentPane().setLayout(new BorderLayout());
				changeView(pnlAdminView);
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
				break;
			case "removeElection":
				System.out.println("removeElection");
				JPanel pnlGetRemoveElectionInfo = new JPanel();
				JTextField txtRemoveElectionName = new JTextField(15);
				String removeElectionName = "";

				pnlGetRemoveElectionInfo.add(new JLabel("Election to be removed:"));
				pnlGetRemoveElectionInfo.add(txtRemoveElectionName);

				int removeElectionChoice = JOptionPane.showConfirmDialog(null, pnlGetRemoveElectionInfo, "Remove Election", JOptionPane.OK_CANCEL_OPTION);
				if (removeElectionChoice == JOptionPane.OK_OPTION) {
					removeElectionName = txtRemoveElectionName.getText();
				}
				else{
					break;
				}
				pwOut.println("<removeElection>,"+removeElectionName);
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
