import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PayForm extends JFrame implements ActionListener{

	JTextField txtName;
	JTextField txtCC;
	JTextField txtemail;
	JTextArea txtArea;

	ButtonGroup BtnGroupCC;
	ArrayList<JCheckBox> btnCheck;

	PayForm(){
	
		JPanel mainPan = new JPanel();
		GroupLayout layout = new GroupLayout(mainPan);
		JPanel pnlButtons = new JPanel();
		JPanel pnlCC = new JPanel();

		txtName = new JTextField(1);
		txtCC = new JTextField(1);
		txtemail = new JTextField(1);
		txtArea = new JTextArea(5,10);
        	txtArea.setEditable(false);
		JLabel lName = new JLabel("Name");
		JLabel lCC = new JLabel("Credit Card Number: ");

	
		layout.setHorizontalGroup(
			layout.createSequentialGroup()
			//.addComponent(lName)
			//.addComponent(lCC)
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(txtName)
				.addComponent(txtCC)
				.addComponent(txtemail)
				.addComponent(txtArea))
			
			
		);

		layout.setVerticalGroup(
			layout.createParallelGroup()
			//.addComponent(lName)
			//.addComponent(lCC)
			.addGroup(layout.createSequentialGroup()
				.addComponent(txtName)
				.addComponent(txtCC)
				.addComponent(txtemail)
				.addComponent(txtArea))
			
		);
			
	
		mainPan.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);  
		getContentPane().add(mainPan);

	
	}

	public void actionPerformed(ActionEvent e){

	}

	public static void main(String args[]){
	
		try {
            	// Set cross-platform Java L&F (also called "Metal")
        		UIManager.setLookAndFeel(
            		"com.sun.java.swing.plaf.gtk.GTKLookAndFeel"
		);

		}catch (UnsupportedLookAndFeelException e) {
       		// handle exception
			System.out.println(e.getMessage());
    		}
    		catch (ClassNotFoundException e) {
       		// handle exception
			System.out.println(e.getMessage());
    		}
    		catch (InstantiationException e) {
       		// handle exception
			System.out.println(e.getMessage());
    		}
    		catch (IllegalAccessException e) {
       		// handle exception
			System.out.println(e.getMessage());
    		}

		new PayForm();
		
	}

}
