
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField cardNumber;
    JTextField email;
    JTextArea txtOrder;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        cardNumber = new JTextField(50);
        email = new JTextField(10);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lAddress = new JLabel("Credit Card Number");
        JLabel lPhone = new JLabel("Email");
        JRadioButton amex = new JRadioButton("Amex");
        amex.setActionCommand("Amex");
        amex.setSelected(true);
        JRadioButton visa = new JRadioButton("Visa");
        visa.setActionCommand("Visa");
        JRadioButton masterc = new JRadioButton("Mastercard");
        masterc.setActionCommand("Mastercard");
        JRadioButton discov = new JRadioButton("Discover");
        discov.setActionCommand("Discover");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(amex);
        btnGroupRadio.add(visa);
        btnGroupRadio.add(masterc);
        btnGroupRadio.add(discov);

        JLabel lblSize = new JLabel("Card Type:  ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(amex);
        pnlRadios.add(visa);
        pnlRadios.add(masterc);
        pnlRadios.add(discov);
        
        JLabel receiptOpt = new JLabel("How would you like your receipt?:  ");
        receiptOpt.setFont(new Font(receiptOpt.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkPepperoni = new JCheckBox("Print");
        chkPepperoni.setActionCommand("print");
        JCheckBox chkPeppers = new JCheckBox("Email");
        chkPeppers.setActionCommand("email");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPepperoni);
        btnCheck.add(chkPeppers);
        
        pnlButtons.add(receiptOpt);
        pnlButtons.add(chkPepperoni);
        pnlButtons.add(chkPeppers);
        
        JButton btnOrder = new JButton("Place Order");
        btnOrder.setActionCommand("order");
        btnOrder.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lAddress)
                    .addComponent(cardNumber)
                    .addComponent(lPhone)
                    .addComponent(email)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lAddress)
                    .addComponent(cardNumber)
                    .addComponent(lPhone)
                    .addComponent(email)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        
        setSize(700,350);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pay Form");

        
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
    	boolean formsValid = false;
    	StringBuffer strOrder = new StringBuffer();
    	if(e.getActionCommand().equals("order")){
    	while(!formsValid){
    		boolean nameValid = false;
    		boolean emailValid = false;
    		boolean cardValid = false;
                if(txtName.getText().equals("")){
             	   txtName.setBackground(Color.red);
             	   txtName.setText("Please enter a name!");
             	   txtName.addFocusListener(new FocusListener(){
                    	@Override
                        public void focusGained(FocusEvent e) {
                            txtName.setBackground(Color.white);
                            txtName.setText("");
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            //Your code here
                        }
                    });
             	   
                }
                else{
                	nameValid = true;
                }
                if(cardNumber.getText().equals("")){
             	   cardNumber.setBackground(Color.red);
             	   cardNumber.setText("Please enter a valid card number!");
             	   cardNumber.addFocusListener(new FocusListener(){
                    	@Override
                        public void focusGained(FocusEvent e) {
                            cardNumber.setBackground(Color.white);
                            cardNumber.setText("");
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            //Your code here
                        }
                    });
                }
                else{
                	cardValid = true;
                }
                if(email.getText().equals("")){
             	   email.setBackground(Color.red);
             	   email.setText("Please enter a valid email!");
             	   email.addFocusListener(new FocusListener(){
                    	@Override
                        public void focusGained(FocusEvent e) {
                            email.setBackground(Color.white);
                            email.setText("");
                        }

                        @Override
                        public void focusLost(FocusEvent e) {
                            //Your code here
                        }
                    });
                }
                else{
                	emailValid = true;
                }
                if(nameValid && cardValid && emailValid){
                	formsValid = true;
                }	
    	
    	}
    	
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(cardNumber.getText() + "\n");
               strOrder.append(email.getText() + "\n");
               strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                	   if(nextBox.getActionCommand().equals("email")){
                		   strOrder.append("Receipt emailed!");
                	   }
                	   else{
                		   strOrder.append(nextBox.getActionCommand() + "\n");
                	   }
                       
                       
                   }
                   
               }
               
               txtOrder.setText(strOrder.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}