import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.net.*;
import java.io.*;

class Client extends JFrame implements ActionListener{
	JTextField txtField;
	JTextArea txtArea;
	Socket sock;
	PrintWriter pwOut;
	BufferedReader brIn;

	Client(){
		JPanel pnlMain = new JPanel();
        	pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.PAGE_AXIS));	

		JPanel pnlButtons = new JPanel();
		JPanel pnlTxtField = new JPanel();
		JPanel pnlOutput = new JPanel();
		
		 
		txtField = new JTextField(15);
		JLabel lblTxtField = new JLabel("Comma Seperated List of Names: ");
		pnlTxtField.add(lblTxtField);
		pnlTxtField.add(txtField);
		
		JButton btnSend = new JButton("Send to Server");
        	btnSend.setActionCommand("send");
        	btnSend.addActionListener(this);

		JButton btnQuit = new JButton("End Session");
        	btnQuit.setActionCommand("quit");
        	btnQuit.addActionListener(this);

       		pnlButtons.add(btnSend);
        	pnlButtons.add(btnQuit);

		txtArea = new JTextArea(50,100);
		txtArea.setEditable(false);
		JLabel lblReturn = new JLabel("Output from Server");
		pnlOutput.add(lblReturn);
		pnlOutput.add(txtArea);

		        
        	pnlMain.add(pnlTxtField);
        	pnlMain.add(pnlButtons);
        	pnlMain.add(pnlOutput);
		
        	getContentPane().add(pnlMain);
        
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	setTitle("Connect to Server Exercise");        
        	setSize(700,200);		
		
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
                	if (strIn.startsWith("<send>;")){
			    txtArea.append(strIn);
                	}else
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
		        case "send":
			    pwOut.println("<send>;"+txtField.getText());//Fill in 
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

