
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtEmail;
    JTextField txtCardNumber;
    JTextField txtSecurityCode;
    JTextArea txtOrder;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtEmail = new JTextField(50);
        txtCardNumber = new JTextField(10);
	txtSecurityCode = new JTextField(3);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
        JLabel lName = new JLabel("Name");
	lName.setForeground(Color.BLUE);
        JLabel lEmail = new JLabel("Email");
	lEmail.setForeground(Color.BLUE);
        JLabel lCardNumber = new JLabel("Credit Card #");
	lCardNumber.setForeground(Color.BLUE);
	JLabel lSecurityCode = new JLabel("Security Code #");
	lSecurityCode.setForeground(Color.BLUE);
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        radVisa.setSelected(true);
        JRadioButton radMasterCard = new JRadioButton("MasterCard");
        radMasterCard.setActionCommand("MasterCard");
        JRadioButton radChase = new JRadioButton("Chase");
        radChase.setActionCommand("Chase");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(radVisa);
        btnGroupRadio.add(radMasterCard);
        btnGroupRadio.add(radChase);

        JLabel lblCardType = new JLabel("Card Type:  ");
	lblCardType.setForeground(Color.BLACK);
        lblCardType.setFont(new Font(lblCardType.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblCardType);
        pnlRadios.add(radVisa);
        pnlRadios.add(radMasterCard);
        pnlRadios.add(radChase);
        
        JLabel lblOptions = new JLabel("Options:  ");
        lblOptions.setFont(new Font(lblOptions.getFont().getName(), Font.PLAIN, 20));
	lblOptions.setForeground(Color.BLACK);
        JCheckBox chkReceipt = new JCheckBox("Print Receipt");
        chkReceipt.setActionCommand("Print Receipt Selected");
        JCheckBox chkConfirmation = new JCheckBox("Email Confirmation");
        chkConfirmation.setActionCommand("Email Confirmation Selected");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkReceipt);
        btnCheck.add(chkConfirmation);
        
        pnlButtons.add(lblOptions);
        pnlButtons.add(chkReceipt);
        pnlButtons.add(chkConfirmation);
        
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
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(lCardNumber)
                    .addComponent(txtCardNumber)
		    .addComponent(lSecurityCode)
		    .addComponent(txtSecurityCode)
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
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(lCardNumber)
                    .addComponent(txtCardNumber)
		    .addComponent(lSecurityCode)
		    .addComponent(txtSecurityCode)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        
        setSize(600,350);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Payment Form");

        
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
           if(e.getActionCommand().equals("order")){
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtEmail.getText() + "\n");
               strOrder.append(txtCardNumber.getText() + "\n");
	       strOrder.append(txtSecurityCode.getText() + "\n");
               strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strOrder.append(nextBox.getActionCommand() + "\n");
                       
                   }
                   
               }
               
               txtOrder.setText(strOrder.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}
