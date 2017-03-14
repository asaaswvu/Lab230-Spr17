import javax.swing.*;
import java.awt.event.*;
import java.awt.ToolKit;
import java.awt.Dimension;
import java.net.*;
import java.io.*

class Client extends JFrame implements ActionListener{
	JTextField txtField

	Client(){
		JPanel pnlMain = new JPanel();
        	pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.PAGE_AXIS));	

		JPanel pnlButtons = new JPanel();
		JPanel pnlTxtField = new JPanel();
		
		 
		txtField = new JTextField(200);
		JLabel lblTxtField = new JLabel("Comma Seperated List of Names");
		pnlTxtField.add(lblTxtField);
		pnlTxtField.add(txtField);
		
		JButton btnSend = new JButton("Send to Server");
        	btnSend.setActionCommand("send");
        	btnSend.addActionListener(this);

		JButton btnQuit = new JButton("End Session");
        	btnQuit.setActionCommand("quit");
        	btnQuit.addActionListener(this);

       		pnlButtons.add(btnSend);
        	pnlButtons.add(btn);
        
        	
	}
	
}
