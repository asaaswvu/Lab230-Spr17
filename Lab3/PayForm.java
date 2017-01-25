
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtCard;
    JTextField txtEmail;
    JTextArea txtInfo;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtCard = new JTextField(16);
        txtEmail = new JTextField(30);
        txtInfo = new JTextArea(15,50);
        txtInfo.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lCard = new JLabel("Credit Card Number");
        JLabel lEmail = new JLabel("Email Address");
        JRadioButton radAmex = new JRadioButton("Amex");
        radAmex.setActionCommand("Amex");
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        radVisa.setSelected(true);
        JRadioButton radMastercard = new JRadioButton("Mastercard");
        radMastercard.setActionCommand("Mastercard");
	JRadioButton radDiscover = new JRadioButton("Discover");
        radDiscover.setActionCommand("Discover");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(radAmex);
        btnGroupRadio.add(radDiscover);
        btnGroupRadio.add(radMastercard);
	btnGroupRadio.add(radVisa);

        JLabel lblCardType = new JLabel("Card Type:  ");
        lblCardType.setFont(new Font(lblCardType.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblCardType);
        pnlRadios.add(radAmex);
        pnlRadios.add(radDiscover);
        pnlRadios.add(radMastercard);
	pnlRadios.add(radVisa);
        
        JLabel lblReceipt = new JLabel("Receipt Type(s):  ");
        lblReceipt.setFont(new Font(lblReceipt.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkEmail = new JCheckBox("Email Confirmation");
        chkEmail.setActionCommand("Email Confirmation");
	JCheckBox chkPrinted = new JCheckBox("Printed Receipt");
        chkPrinted.setActionCommand("Printed Receipt");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkEmail);
        btnCheck.add(chkPrinted);
        
        pnlButtons.add(lblReceipt);
        pnlButtons.add(chkEmail);
        pnlButtons.add(chkPrinted);

        
        JButton btnProcess = new JButton("Process Payment");
        btnProcess.setActionCommand("Process Payment");
        btnProcess.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCard)
                    .addComponent(txtCard)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnProcess))
                .addComponent(txtInfo)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCard)
                    .addComponent(txtCard)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnProcess))
                .addComponent(txtInfo)
        );
        
        setSize(800,400);

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
           if(e.getActionCommand().equals("Process Payment")){
               StringBuffer strInfo = new StringBuffer();
               strInfo.append("Payment Information\n\n");
               strInfo.append("Name:\n     " + txtName.getText() + "\n");
               strInfo.append("Credit Card Number:\n     " + txtCard.getText() + "\n");
               strInfo.append("Email Address:\n     " + txtEmail.getText() + "\n");
               strInfo.append("Card Type:\n     " + btnGroupRadio.getSelection().getActionCommand() + "\nDesired Receipts:\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strInfo.append("     " + nextBox.getActionCommand() + "\n");
                       
                   }
                   
               }
               strInfo.append("\n\n\nThank you, " + txtName.getText() + ".\nYour payment has been processed.");
               txtInfo.setText(strInfo.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}
