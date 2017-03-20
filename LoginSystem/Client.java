import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.BorderLayout;
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
  private JPanel pnlAdminView = new JPanel(new BorderLayout());
	private JButton btnLogout = new JButton("Logout");
	private JButton btnQuit = new JButton("Quit");

	Client(){
    System.setProperty("awt.useSystemAAFontSettings", "off");
    System.setProperty("swing.aatext", "false");
		this.setResizable(false);
		pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.PAGE_AXIS));

		JPanel pnlName = new JPanel();
		JPanel pnlPassword = new JPanel();
		JPanel pnlButtons = new JPanel();

		txtName = new JTextField(15);
		JLabel lblName = new JLabel("Name");
		pnlName.add(lblName);
		pnlName.add(txtName);

		txtPass = new JPasswordField(15);
		JLabel lblPass = new JLabel("Pass");
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
		setSize(400,300);

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
				if (strIn.startsWith("<loggedA>")){
          System.out.println("Admin has logged in.");
					initAdmin();
					changeView(pnlAdminView);
				}
				else
					JOptionPane.showMessageDialog(this,strIn,"???",JOptionPane.PLAIN_MESSAGE);
			}
		}catch(IOException e){
			System.out.println("IOException");
		}catch(NullPointerException npe){
			System.out.println("null");
			pwOut.println("<die>");
			System.exit(0);
		}

	}

	private void changeView(JPanel currentView){
		getContentPane().removeAll();
		getContentPane().add(currentView, BorderLayout.CENTER);
		getContentPane().doLayout();
		validate();
		repaint();
	}

	private void initStudent(){
		JLabel greeting = new JLabel("Welcome Student", SwingConstants.CENTER);
		pnlStudentView.add(greeting, BorderLayout.PAGE_START);
    btnLogout.setActionCommand("logout");
    btnLogout.addActionListener(this);
		pnlStudentView.add(btnLogout,BorderLayout.PAGE_END);
	}

  private void initAdmin(){
    JLabel greeting = new JLabel("Welcome Admin", SwingConstants.CENTER);
    pnlAdminView.add(greeting, BorderLayout.PAGE_START);
    btnLogout.setActionCommand("logout");
    btnLogout.addActionListener(this);
    pnlAdminView.add(btnLogout,BorderLayout.PAGE_END);
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
