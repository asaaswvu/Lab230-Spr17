import javax.swing.*; 
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
	
	JTextField txtName;
    JTextField txtCreditNum;
    JTextField txtEmail;
    JTextArea txtCardType;
    
ButtonGroup btnGroupRadio;
ArrayList<JCheckBox> btnCheck;


 PayForm() {
	 JPanel panelMain = new JPanel();
     GroupLayout layout = new GroupLayout(panelMain);
     
     JPanel pnlButtons = new JPanel();
     JPanel pnlRadios = new JPanel();
     
     
     txtName = new JTextField(20);
     txtCreditNum = new JTextField(50);
     txtEmail = new JTextField(10);
     txtCardType = new JTextArea(10,20);
     txtCardType.setEditable(false);
     
     JLabel lName = new JLabel("Name");
     lName.setForeground(Color.WHITE);
     JLabel lCreditNum = new JLabel("Credit Card Number");
     lCreditNum.setForeground(Color.WHITE);
     JLabel lEmail = new JLabel("Email");
     lEmail.setForeground(Color.WHITE);
     
     
     JRadioButton radAmex = new JRadioButton("Amex");
     radAmex.setActionCommand("Amex");
     radAmex.setForeground(Color.RED);
     JRadioButton radVisa = new JRadioButton("Visa");
     radVisa.setActionCommand("Visa");
     radVisa.setForeground(Color.ORANGE);
     radAmex.setSelected(true); //default?
     JRadioButton radMasterCard = new JRadioButton("Master Card");
     radMasterCard.setActionCommand("Master Card");
     radMasterCard.setForeground(Color.green);
     JRadioButton radDiscover = new JRadioButton("Discover");
     radDiscover.setActionCommand("Discover");
     radDiscover.setForeground(Color.BLUE);
     
     btnGroupRadio = new ButtonGroup();
     btnGroupRadio.add(radAmex);
     btnGroupRadio.add(radVisa);
     btnGroupRadio.add(radMasterCard);
     btnGroupRadio.add(radDiscover);
     
     JLabel lblCard = new JLabel("Card:  ");
     lblCard.setFont(new Font(lblCard.getFont().getName(), Font.ROMAN_BASELINE, 20));
     pnlRadios.add(lblCard);
     pnlRadios.add(radAmex);
     pnlRadios.add(radVisa);
     pnlRadios.add(radMasterCard);
     pnlRadios.add(radDiscover);
     
     //Printing the Receipt and Email Confirm
     JLabel lblConfirmation = new JLabel("Confirmation:  ");
     lblConfirmation.setFont(new Font(lblConfirmation.getFont().getName(), Font.BOLD, 20));
     JCheckBox chkEmail = new JCheckBox("Email");
     chkEmail.setActionCommand("Email");
     chkEmail.setForeground(Color.MAGENTA);
     JCheckBox chkReceipt = new JCheckBox("Receipt");
     chkReceipt.setActionCommand("Receipt");
     chkReceipt.setForeground(Color.MAGENTA);
    

     btnCheck = new ArrayList<JCheckBox>();
     btnCheck.add(chkEmail);
     btnCheck.add(chkReceipt);
     
     pnlButtons.add(lblConfirmation);
     pnlButtons.add(chkEmail);
     pnlButtons.add(chkReceipt);
     
     JButton btnOrder = new JButton("Confirm Purchase");
     btnOrder.setForeground(Color.GREEN);
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
                      .addComponent(lCreditNum)
                      .addComponent(txtCreditNum)
                      .addComponent(lEmail)
                      .addComponent(txtEmail)
                      .addComponent(pnlRadios)
                      .addComponent(pnlButtons)
                      .addComponent(btnOrder))
                  .addComponent(txtCardType)
          );
     layout.setVerticalGroup(
             layout.createParallelGroup()
                 .addGroup(layout.createSequentialGroup()
                     .addComponent(lName)
                     .addComponent(txtName)
                     .addComponent(lCreditNum)
                     .addComponent(txtCreditNum)
                     .addComponent(lEmail)
                     .addComponent(txtEmail)
                     .addComponent(pnlRadios)
                     .addComponent(pnlButtons)
                     .addComponent(btnOrder))
                 .addComponent(txtCardType)
         );
     
     setSize(700,450);
     
     //tells java what to do when the class object closes
     setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
     setTitle("Pay Form");
     
     panelMain.setBackground(Color.BLACK);
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
         strOrder.append(txtCreditNum.getText() + "\n");
         strOrder.append(txtEmail.getText() + "\n");
         strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
         
         Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
         while(iterCheckButtons.hasNext()){
             JCheckBox nextBox = iterCheckButtons.next();
             if(nextBox.isSelected()){
                 strOrder.append(nextBox.getActionCommand() + " ");
                 
             }
             
         }
         
         txtCardType.setText(strOrder.toString());
     }
}














public static void main(String args[]){
  new PayForm();
}
}
