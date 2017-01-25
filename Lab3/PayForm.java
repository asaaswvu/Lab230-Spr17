import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
/**Extra Added
 * Colored panels 
 * Checkbox no on email  - Will clear email text box
 * 						 - prompt user to re-enter email address
 * Checkbox yes on email - Displays confirmed email onto pay form
 * Print Recipt 		 - check yes will print reciept to the output screen**/
public class PayForm extends JFrame implements ActionListener{
		JTextField txtName;
	    JTextField txtCardNum;
	    JTextField txtPhone;
	    JTextField txtEmail;
	    JTextArea txtOrder;

	    ButtonGroup btnGroupRadio;
	    ArrayList<JCheckBox> btnCheck;
	   
	    PayForm(){
	   	
	    /**MY COLORS**/
	    Color seaGreen = new Color(64,224,208);
	    Color deepBlue = new Color(70,130,180);
	    Color beechyBlue = new Color(135,206,235);
	    
	        JPanel panelMain = new JPanel();
	        GroupLayout layout = new GroupLayout(panelMain);
	        JPanel pnlButtons = new JPanel();
	        JPanel pnlButtons_lower = new JPanel();
	        JPanel pnlRadios = new JPanel();
	       
	        /**Setting Jpanel Colors**/
	        pnlButtons.setBackground(deepBlue);  
	        pnlButtons_lower.setBackground(seaGreen);
	        panelMain.setBackground(beechyBlue);
	        
	        txtName = new JTextField(20);
	        txtCardNum = new JTextField(50);
	        txtPhone = new JTextField(10);
	        txtEmail = new JTextField(10);
	        txtOrder = new JTextArea(15,50);
	        txtOrder.setEditable(false);
	        	        
	        JLabel lName = new JLabel("Name");
	        JLabel lCard = new JLabel("Card Number");
	        JLabel lPhone = new JLabel("Phone Number");
	        JLabel lEmail = new JLabel("Email Address");
	        
	        /**JLabel - Setting Foreground colors**/
	        lName.setForeground(Color.white);
	        lCard.setForeground(Color.white);
	        lPhone.setForeground(Color.white);
	        lEmail.setForeground(Color.white);
	       	        
	        JRadioButton radVisa = new JRadioButton("Visa"); 
	        radVisa.setActionCommand("Visa");
	        JRadioButton radMaster = new JRadioButton("MasterCard");
	        radMaster.setActionCommand("MasterCard");
	        radMaster.setSelected(true);
	        JRadioButton radExpress = new JRadioButton("American Express");
	        radExpress.setActionCommand("AmericanExpress");	       

	        btnGroupRadio = new ButtonGroup();
	        btnGroupRadio.add(radVisa);
	        btnGroupRadio.add(radMaster);
	        btnGroupRadio.add(radExpress);
	       
	        pnlRadios.add(radMaster);
	        pnlRadios.add(radVisa);
	        pnlRadios.add(radExpress);
	        
	        JLabel Optional = new JLabel("Print Receipt:  ");
	       
	        JCheckBox print_yes = new JCheckBox("Yes");
	        print_yes.setActionCommand("YesPrint");
	        
	        JCheckBox print_no = new JCheckBox("No");
	        print_no.setActionCommand("NoPrint");
	        
	        JLabel email = new JLabel("Confirm Email:  ");
	        JCheckBox Email_check = new JCheckBox(txtEmail.getText());
	        Email_check.setActionCommand("checkEmail");
	        JCheckBox email_yes = new JCheckBox("Yes");
	        email_yes.setActionCommand("YesEmail");
	        
	        JCheckBox email_no = new JCheckBox("No");
	        email_no.setActionCommand("NoEmail");
	         
	        email.setForeground(Color.white);
	        btnCheck = new ArrayList<JCheckBox>();
	        btnCheck.add(print_yes);	        
	        btnCheck.add(email_yes);
	        btnCheck.add(email_no);
	        
	        pnlButtons.add(Optional);
	        pnlButtons.add(print_yes);
	        pnlButtons.add(print_no);
	        pnlButtons_lower.add(email);
	        pnlButtons_lower.add(email_yes);
	        pnlButtons_lower.add(email_no);
 
	        JButton btnConfirm = new JButton("Confirm Payment Information");
	        btnConfirm.setActionCommand("Confirm");
	        btnConfirm.addActionListener(this);
	        
	        
	        panelMain.setLayout(layout);
	        layout.setAutoCreateGaps(true);
	        layout.setAutoCreateContainerGaps(true);

	        layout.setHorizontalGroup(
	           layout.createSequentialGroup()
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addComponent(lName)
	                    .addComponent(txtName)
	                    .addComponent(lCard)
	                    .addComponent(txtCardNum)
	                    .addComponent(lPhone)
	                    .addComponent(txtPhone)
	                    .addComponent(lEmail)
	                    .addComponent(txtEmail)
	                    .addComponent(pnlRadios)
	                    .addComponent(pnlButtons)
	                    .addComponent(pnlButtons_lower)
	                    .addComponent(btnConfirm))
	                .addComponent(txtOrder)
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup()
	                .addGroup(layout.createSequentialGroup()
	                    .addComponent(lName)
	                    .addComponent(txtName)
	                    .addComponent(lCard)
	                    .addComponent(txtCardNum)
	                    .addComponent(lPhone)
	                    .addComponent(txtPhone)
	                    .addComponent(lEmail)
	                    .addComponent(txtEmail)
	                    .addComponent(pnlRadios)
	                    .addComponent(pnlButtons)
	                    .addComponent(pnlButtons_lower)
	                    .addComponent(btnConfirm))
	                .addComponent(txtOrder)
	        );
	        
	        setSize(600,400);

	        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Pay Form");

	        getContentPane().add(panelMain);

	        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	        setLocation(x, y);
	        
	        setVisible(true);       
    
	    }
	    public void actionPerformed(ActionEvent e){
	    	String radio = "";
	           if(e.getActionCommand().equals("Confirm")){
	               StringBuffer strOrder = new StringBuffer();
	               strOrder.append(txtName.getText() + "\n");
	               strOrder.append(txtCardNum.getText() + "\n");
	               strOrder.append(txtPhone.getText() + "\n");
	               strOrder.append(txtEmail.getText() + "\n");
	               
	               radio = btnGroupRadio.getSelection().getActionCommand() + "\n";
	               strOrder.append(radio);
	               
	               
	               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
	               boolean email = false, print = false;
	               while(iterCheckButtons.hasNext()){
	                   JCheckBox nextBox = iterCheckButtons.next();
	                   if(nextBox.isSelected()){
	                	   
	                	  if(nextBox.getActionCommand().equals("YesPrint"))
	                	  {
	                		  strOrder.append("Recipt Printing...\n");
	                		  System.out.println("Reciept\n" +  
	                          "Payment to: "   +  txtName.getText() + "\n"+
	                          "Card Type:  "   +  radio +
	                          "Card Number: "  +  txtCardNum.getText() + "\n"+
	                          "Phone Number: " +  txtPhone.getText() + "\n"+
	                          "Email Address: "+  txtEmail.getText() + "\n");
	                		   strOrder.append("Recipt Successfully printed!\n");
	                		  print = true;
	                	  }
	                	  /*{Checks if email were confirmed}*/
	                	  if(nextBox.getActionCommand().equals("YesEmail"))
	                	  {
	                		  strOrder.append("Confirm Email: " + txtEmail.getText()+ "\n");
	                		  email = true;
	                	  }
	                	  
	                	  if(nextBox.getActionCommand().equals("NoEmail"))
	                	  {
	                		  strOrder.append("Please Re-input Email and Re-Confirm Payment...");
	                		  txtEmail.setText("");
	                	  }
	                	  
	                		 
	                	  if(email || print){}
	                	  else
	                	  strOrder.append(nextBox.getActionCommand() + "\n ");
    
	                   }
	               }
	               
	               txtOrder.setText(strOrder.toString());
	           }
	    }

	    public static void main(String args[]){
	        new PayForm();
	    }	
	
}
