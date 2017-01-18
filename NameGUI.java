import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import java.awt.*;

class NameGUI extends JFrame implements ActionListener{
    
    JTextField txtWord1;
    JTextField txtWord2;
    
    NameGUI(){
        
        //create a background panel to put things into, uses BorderLayout & FlowLayout Managers
        JPanel panelMain = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new FlowLayout()); //adds layers "straight into it"
        JPanel panelMiddle = new JPanel(new FlowLayout());
        JPanel panelBottom = new JPanel(new FlowLayout());

        //create a button named btnSample with text Sample Button
        // JButton - creates button that allows you to do something on press
        JButton btnSample = new JButton("Sample Button");
        JButton btnLstName = new JButton("Last Name");
        JButton btnQuit = new JButton("Quit");
        JButton btnSwap = new JButton("Swap");
        
        //create sample textboxes
        txtWord1 = new JTextField(15);
        txtWord2 = new JTextField(15);
        
        //create a label for display
        JLabel lblSwap = new JLabel("Swap Words!");
        
        //buttons need to say something(ActionCommand) to someone who's listening
        // this - refers to current class
        // ActionListener - indicates which program to listen from (this)
        btnSample.setActionCommand("sample");  //yours will have to be unique
        btnSample.addActionListener(this);
        btnQuit.setActionCommand("quit");  //your command will have to be unique
        btnQuit.addActionListener(this);
        btnSwap.setActionCommand("swap");  
        btnSwap.addActionListener(this);
        btnLstName.setActionCommand("Lname");
        btnLstName.addActionListener(this);
        
        //Add components to proper panels
        panelTop.add(btnSample);
        panelTop.add(btnQuit);
        panelTop.add(btnLstName);
        
        //panelMiddle.add(btnYourButton);

        panelBottom.add(lblSwap);
        panelBottom.add(btnSwap);
        panelBottom.add(txtWord1);
        panelBottom.add(txtWord2);
        
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
        setTitle("NameGui Title (how original)");

        
        //get visible container and add panelMain to it
        //EVERYTHING has to be arranged and set before adding to ContentPane
        //ContentPane - anything that you want to show up
        getContentPane().add(panelMain);
      

        //this centers the window in the screen
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        
        //make sure you can actually see it, starts off false
        setVisible(true);
        
    }
    /**Top Menu, Only has help in beginning**/
    private void generateMenu(){
        //create an empty menu bar
        JMenuBar menuBar = new JMenuBar();

        //create a menu  (file, edit, help, etc)
        JMenu menuHelp = new JMenu("Help");
        JMenu menuFile = new JMenu("File");
        
        //create a menu item and set up its listeners, similar to buttons
        JMenuItem miHelp = new JMenuItem("Help me");
        miHelp.addActionListener(this);
        miHelp.setActionCommand("help");
        
        JMenuItem ficlk = new JMenuItem("Quit");
        ficlk.setActionCommand("file");
        ficlk.addActionListener(this);
        //put together the pieces
        menuHelp.add(miHelp);
        menuBar.add(menuHelp);
        menuFile.add(ficlk);
        menuBar.add(menuFile);
        
        
       menuFile.setMnemonic('F');
      

        ficlk.setAccelerator(KeyStroke.getKeyStroke(
        		java.awt.event.KeyEvent.VK_X, 
        		java.awt.Event.CTRL_MASK));

        //add bar to this JFrame
        setJMenuBar(menuBar);    
    
    }
    public void actionPerformed(ActionEvent evt) {
        //this method listens to the JFrame's events and performs appropriately
    	//evt.getActionCommand - retrieves commands which have been "said"
        switch (evt.getActionCommand()){
            case "sample":
                JOptionPane.showMessageDialog(this,"A Sample message dialog box","A plain message",JOptionPane.PLAIN_MESSAGE);
                break;
            case "quit" :
                System.exit(0);
                break;
            case "swap" :
                String tempString;
                tempString = txtWord1.getText();
                txtWord1.setText(txtWord2.getText());
                txtWord2.setText(tempString);
                break;
            case "help" :
                JOptionPane.showMessageDialog(this,"There is no help for you.","Sorry",JOptionPane.WARNING_MESSAGE);
                break;
            case "Lname" : JOptionPane.showMessageDialog(this, "Kambara"); 
            	break;
            case "file" : System.exit(0);
            break;
        }
        System.out.println("F3 IS PRESSED");
    }
    
    public static void main(String [] args){
        new NameGUI();
    }
    
}
