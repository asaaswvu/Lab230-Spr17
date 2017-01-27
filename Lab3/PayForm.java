
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtCardNum;
    JTextField txtEMail;
    JTextArea txtInfo;
   
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
    	Color bg = new Color(173,216,230);
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        JPanel pnlRadios2 = new JPanel();
	        
        txtName = new JTextField(20);
        txtCardNum = new JTextField(20);
        txtEMail = new JTextField(40);
        txtInfo = new JTextArea(75, 25);
        txtInfo.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lCardNum = new JLabel("Credit Card Number (no spaces)");
        JLabel lEMail = new JLabel("eMail address");

        JRadioButton radAmex = new JRadioButton("AMEX");
        radAmex.setActionCommand("AMEX");
        radAmex.setSelected(true);
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        JRadioButton radDisc = new JRadioButton("Discover");
        radDisc.setActionCommand("Discover");
		JRadioButton radMCard = new JRadioButton("MasterCard");
        radMCard.setActionCommand("MasterCard");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(radAmex);
        btnGroupRadio.add(radDisc);
        btnGroupRadio.add(radVisa);
		btnGroupRadio.add(radMCard);

        JLabel lblCard = new JLabel("Credit Card Type: ");
        lblCard.setFont(new Font(lblCard.getFont().getName(), Font.PLAIN, 16));
        
        pnlRadios.add(radAmex);
        pnlRadios.add(radDisc);
        
        pnlRadios2.add(radVisa);
        pnlRadios2.add(radMCard);
        
        JLabel lblRcpt = new JLabel("Receipt Type: ");
        lblRcpt.setFont(new Font(lblRcpt.getFont().getName(), Font.PLAIN, 16));
        JCheckBox chkPrinted = new JCheckBox("Printed");
        chkPrinted.setActionCommand("Printed");
        JCheckBox chkEMail = new JCheckBox("eMail");
        chkEMail.setActionCommand("eMail");
                
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPrinted);
        btnCheck.add(chkEMail);
        
        
        pnlButtons.add(chkPrinted);
        pnlButtons.add(chkEMail);
        
        JButton btnPayment = new JButton("Make Payment");
        btnPayment.setActionCommand("payment");
        btnPayment.addActionListener(this);
        
        JButton btnClear = new JButton("Clear Form");
        btnClear.setActionCommand("clear");
        btnClear.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                	.addGroup(layout.createSequentialGroup()
                		.addGroup(layout.createParallelGroup()
				            .addComponent(lName)
				            .addComponent(txtName)
				            .addComponent(lCardNum)
				            .addComponent(txtCardNum)
				            .addComponent(lEMail)
				            .addComponent(txtEMail))
                    	.addGroup(layout.createParallelGroup()
                    		.addComponent(lblCard)
                    	    .addComponent(pnlRadios)
                    		.addComponent(pnlRadios2)
                    		.addComponent(lblRcpt)
                    		.addComponent(pnlButtons)
                    		.addGroup(layout.createSequentialGroup()
                    			.addComponent(btnPayment)
                    			.addComponent(btnClear))))
       				.addComponent(txtInfo))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                	.addGroup(layout.createParallelGroup()
                		.addGroup(layout.createSequentialGroup()
				            .addComponent(lName)
				            .addComponent(txtName)
				            .addComponent(lCardNum)
				            .addComponent(txtCardNum)
				            .addComponent(lEMail)
				            .addComponent(txtEMail))
                    	.addGroup(layout.createSequentialGroup()
                    		.addComponent(lblCard)
                    	    .addComponent(pnlRadios)
                    		.addComponent(pnlRadios2)
                    		.addComponent(lblRcpt)
                    		.addComponent(pnlButtons)
                    		.addGroup(layout.createParallelGroup()
                    			.addComponent(btnPayment)
                    			.addComponent(btnClear))))
       				.addComponent(txtInfo))
        );
        
        setSize(700,400);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Credit Card Payment");

        panelMain.setBackground(bg);
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
    
    public void actionPerformed(ActionEvent e){
           if(e.getActionCommand().equals("payment")){
               StringBuffer strInfo = new StringBuffer();
               strInfo.append("Payment Information:\n\n");
               strInfo.append("Name:                   " + txtName.getText() + "\n");
               strInfo.append("Credit Card Type:  " + btnGroupRadio.getSelection().getActionCommand() + "\n");
               strInfo.append("Card Number:       " + txtCardNum.getText() + "\n");
               strInfo.append("eMail:                    " + txtEMail.getText() + "\n\n");
               strInfo.append("Receipt Type(s):\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strInfo.append(nextBox.getActionCommand() + " receipt\n");
                       
                   }
                   
               }
               
               txtInfo.setText(strInfo.toString());
           }
           if(e.getActionCommand().equals("clear")){
           		txtName.setText("");
           		txtCardNum.setText("");
           		txtEMail.setText("");
           		txtInfo.setText("");
           		
       	   }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}
