
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtCreditNumber;
    JTextField txtEmail;
    JTextArea txtPay;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtCreditNumber = new JTextField(50);
        txtEmail = new JTextField(10);
        txtPay = new JTextArea(15,50);
        txtPay.setEditable(false);
        
        JLabel lName = new JLabel("Name");
	lName.setForeground(Color.orange);
        JLabel lCreditNumber = new JLabel("Card Number");
	lCreditNumber.setForeground(Color.green);
        JLabel lEmail = new JLabel("Email");
	lEmail.setForeground(Color.red);
        JRadioButton amex = new JRadioButton("Amex");
	amex.setForeground(Color.red);
        amex.setActionCommand("Amex");
        JRadioButton visa = new JRadioButton("Visa");
	visa.setForeground(Color.blue);
        visa.setActionCommand("Visa");
        amex.setSelected(true);
        JRadioButton masterCard = new JRadioButton("Mastercard");
	masterCard.setForeground(Color.red);
        masterCard.setActionCommand("Mastercard");
	JRadioButton discover = new JRadioButton("Discover");
	discover.setForeground(Color.blue);
	discover.setActionCommand("Discover");
        
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(amex);
        btnGroupRadio.add(visa);
        btnGroupRadio.add(masterCard);
	btnGroupRadio.add(discover);

        JLabel lblCardType = new JLabel("Card Type:  ");
	lblCardType.setForeground(Color.red);
        lblCardType.setFont(new Font(lblCardType.getFont().getName(), Font.BOLD, 20));
        pnlRadios.add(lblCardType);
        pnlRadios.add(amex);
        pnlRadios.add(visa);
        pnlRadios.add(masterCard);
	pnlRadios.add(discover);
        
        JLabel lblReceiptType = new JLabel("Receipt Type:  ");
	lblReceiptType.setForeground(Color.green);
        lblReceiptType.setFont(new Font(lblReceiptType.getFont().getName(), Font.BOLD, 20));
        JCheckBox chkPrintReceipt = new JCheckBox("Printing Receipt");
	chkPrintReceipt.setForeground(Color.orange);
        chkPrintReceipt.setActionCommand("Printing Receipt");
        JCheckBox chkEmailConf = new JCheckBox("Email Confirmation");
	chkEmailConf.setForeground(Color.green);
        chkEmailConf.setActionCommand(" Sending Email Confirmation");
       
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPrintReceipt);
        btnCheck.add(chkEmailConf);
       
        
        pnlButtons.add(lblReceiptType);
        pnlButtons.add(chkPrintReceipt);
        pnlButtons.add(chkEmailConf);
        
        
        JButton btnPay = new JButton("Pay");
        btnPay.setActionCommand("now");
        btnPay.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCreditNumber)
                    .addComponent(txtCreditNumber)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnPay))
                .addComponent(txtPay)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCreditNumber)
                    .addComponent(txtCreditNumber)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnPay))
                .addComponent(txtPay)
        );
        
        setSize(850,350);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PayForm");

        
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
           if(e.getActionCommand().equals("now")){
               StringBuffer strPay = new StringBuffer();
               strPay.append(txtName.getText() + "\n");
               strPay.append(txtCreditNumber.getText() + "\n");
               strPay.append(txtEmail.getText() + "\n");
               strPay.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strPay.append(nextBox.getActionCommand() + " ");
                       
                   }
                   
               }
               
               txtPay.setText(strPay.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}
