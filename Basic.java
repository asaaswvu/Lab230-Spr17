import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.*;

/**
Basic.java 
Nicholas */
class Basic extends JFrame implements ActionListener {

    public Basic(){
      	super();
	// parameters
        this.setSize(640,480); 
        this.setVisible(true);  //will be invisible otherwise
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CS 230 Lab 2");

	// create panels, main(nested())
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel nestedPanel = new JPanel(new FlowLayout());
	
	// adds menu
	addMenu();

	// create button
	JButton nameButton = new JButton("Clickme");
	nameButton.addActionListener(this);
	nameButton.setActionCommand("clickme");

	// add panels and make visible
	nestedPanel.add(nameButton);
	mainPanel.add(nestedPanel,BorderLayout.CENTER);
	getContentPane().add(mainPanel);

	//this centers the window in the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
    }
	
    // constructs a menu object and adds it to the window
    public void addMenu() {
	JMenuBar menuBar = new JMenuBar();
	JMenu menuFile = new JMenu("File");	
	JMenuItem menuQuit = new JMenuItem("Quit");
	menuQuit.addActionListener(this);
	menuQuit.setActionCommand("quit");
	menuBar.add(menuFile);
	menuFile.add(menuQuit);
	setJMenuBar(menuBar);
	
    }

    // Method to handle events
    public void actionPerformed(ActionEvent evt) {
	switch(evt.getActionCommand() ) {
	    case "quit" :
		System.exit(0);
		break;
	    case "clickme" :
		JOptionPane.showMessageDialog(this,"Nicholas Hill",
					"CS230",JOptionPane.PLAIN_MESSAGE);
		break;	
	}
    }

    // main method to be executed
    public static void main(String args[]){
        new Basic();
    }

}


