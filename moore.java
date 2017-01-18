import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.*;

class moore extends JFrame implements ActionListener{
	moore(){
	//create a background panel to put things into, uses BorderLayout & FlowLayout Managers
        JPanel panelMain = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new FlowLayout());
        JPanel panelMiddle = new JPanel(new FlowLayout());
        JPanel panelBottom = new JPanel(new FlowLayout());

	//create a button named btnSample with text Sample Button
	JButton btnName = new JButton("Learn About the Creator!");
	
	//buttons need to say something(ActionCommand) to someone who's listening
	btnName.setActionCommand("name");
	btnName.addActionListener(this);

	panelTop.add(btnName);

        //Add individual panels to panelMain, applies to BORDERLAYOUT only
        //PAGE_START is top of screen
        //PAGE_END is bottom of screen
        //LINE_START is right side of screen
        //LINE_END is left side of screen
        //CENTER is center
	panelMain.add(panelTop,BorderLayout.PAGE_START);
        panelMain.add(panelMiddle,BorderLayout.CENTER);
        panelMain.add(panelBottom,BorderLayout.PAGE_END);

        //created separate method to generate menu, not necessary, but cleaner
        generateMenu();
        
        //set the window size for the app
        setSize(800,600);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Basic GUI for a Beginner");

        
        //get visible container and add panelMain to it
        //EVERYTHING has to be arranged and set before adding to ContentPane
        getContentPane().add(panelMain);

	//this centers the window in the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        
        //make sure you can actually see it, starts off false
        setVisible(true);
	}

	private void generateMenu(){
		//create an empty menu bar
        	JMenuBar menuBar = new JMenuBar();

        	//create a menu  (file, edit, help, etc)
        	JMenu menuFile = new JMenu("File");
        
        	//create a menu item and set up its listeners, similar to buttons
        	JMenuItem miQuit = new JMenuItem("Quit");
        	miQuit.addActionListener(this);
        	miQuit.setActionCommand("quit");
    
       		//put together the pieces
        	menuFile.add(miQuit);
        	menuBar.add(menuFile);

        	//add bar to this JFrame
        	setJMenuBar(menuBar);   
	}

	public void actionPerformed(ActionEvent evt) {
		//this method listens to the JFrame's events and performs appropriately
		switch (evt.getActionCommand()){
			case "quit":
				System.exit(0);
				break;
			case "name": 
				JOptionPane.showMessageDialog(this,"Sarah Lynn Moore","The Creator's Name",JOptionPane.PLAIN_MESSAGE);
				break;		
		}
	}
    public static void main(String [] args){
        new moore();
    }
}

