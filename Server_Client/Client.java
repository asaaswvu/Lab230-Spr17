import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.net.*;
import java.io.*;

class Client extends JFrame implements ActionListener{
    
    BufferedReader brIn;
    PrintWriter pwOut;
    Socket sock;
    JTextField txtName;
    JPasswordField txtPass;
    
    Client(){
        JPanel pnlMain = new JPanel();
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
        
        JButton btnAdd = new JButton("Add User");
        btnAdd.setActionCommand("add");
        btnAdd.addActionListener(this);
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setActionCommand("login");
        btnLogin.addActionListener(this);
        
        JButton btnQuit = new JButton("Quit");
        btnQuit.setActionCommand("quit");
        btnQuit.addActionListener(this);
        
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnLogin);
        pnlButtons.add(btnQuit);
        
        pnlMain.add(pnlName);
        pnlMain.add(pnlPassword);
        pnlMain.add(pnlButtons);
        
        getContentPane().add(pnlMain);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login Client");        
        setSize(300,200);
        
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
                if (strIn.startsWith("<added>")){
                    JOptionPane.showMessageDialog(this,"User added","Successful",JOptionPane.PLAIN_MESSAGE);
                    
                    
                }else if (strIn.startsWith("<logged>")){
                    JOptionPane.showMessageDialog(this,"Successful Login","Successful",JOptionPane.PLAIN_MESSAGE);
                    
                }
                else
                    JOptionPane.showMessageDialog(this,strIn,"???",JOptionPane.PLAIN_MESSAGE);
            }
        }catch(IOException e){
            System.out.println("IOException");
        }catch(NullPointerException npe){
            System.out.println("null");
        }
        
    }
    public void actionPerformed(ActionEvent e){
        if (!sock.isClosed()){
            switch (e.getActionCommand()){
                case "add":
                    System.out.println("<add>," + txtName.getText() + "," + txtPass.getText());
                    pwOut.println("<add>," + txtName.getText() + "," + txtPass.getText()); 
                break;
                case "login":
                    pwOut.println("<login>," + txtName.getText() + "," + txtPass.getText());
                    System.out.println("<login>," + txtName.getText() + "," + txtPass.getText());
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