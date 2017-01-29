import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtCCExpiration;
    JTextField txtCVCNumber;
    JTextField txtPhone;
    JTextField txtTip;
    JTextArea  txtOrder;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtAddress = new JTextField(16);
        txtPhone = new JTextField(30);
	txtTip = new JTextField(10);
	txtCCExpiration = new JTextField(8);
	txtCVCNumber = new JTextField(3);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lAddress = new JLabel("Credit Card #");
	JLabel lCCExpiration = new JLabel("Expiration Date");
	JLabel lCVCNumber = new JLabel("CVC");
        JLabel lPhone = new JLabel("Email Address");
	JLabel lTip = new JLabel("Tip Amount (leave blank if paying tip with cash)");
        JRadioButton rad12 = new JRadioButton("American Express");
        rad12.setActionCommand("American Express");
        JRadioButton rad10 = new JRadioButton("Visa");
        rad10.setActionCommand("Visa");
        rad10.setSelected(true);
        JRadioButton rad16 = new JRadioButton("Mastercard");
        rad16.setActionCommand("Mastercard");
	JRadioButton rad18 = new JRadioButton("Discover");
	rad18.setActionCommand("Discover");

        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(rad12);
        btnGroupRadio.add(rad10);
        btnGroupRadio.add(rad16);
	btnGroupRadio.add(rad18);
	
        JLabel lblSize = new JLabel("Credit Card:  ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(rad10);
        pnlRadios.add(rad12);
        pnlRadios.add(rad16);
	pnlRadios.add(rad18);

    
        JLabel lblToppings = new JLabel("Receipt Options:  ");
        lblToppings.setFont(new Font(lblToppings.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkPrintReceipt = new JCheckBox("Print Receipt");
        chkPrintReceipt.setActionCommand("Print Receipt");
        JCheckBox chkEmailReceipt = new JCheckBox("Email Receipt");
        chkEmailReceipt.setActionCommand("Email Receipt");
       
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPrintReceipt);
        btnCheck.add(chkEmailReceipt);
       
        
        pnlButtons.add(lblToppings);
        pnlButtons.add(chkPrintReceipt);
        pnlButtons.add(chkEmailReceipt);
        
        
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
		    .addComponent(lCCExpiration)
		    .addComponent(txtCCExpiration)
		    .addComponent(lCVCNumber)
		    .addComponent(txtCVCNumber)  
                    .addComponent(lAddress)
                    .addComponent(txtAddress)
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
		    .addComponent(lTip)  
		    .addComponent(txtTip)  
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
                    .addComponent(txtAddress)
		    .addComponent(lCCExpiration)
		    .addComponent(txtCCExpiration)
		    .addComponent(lCVCNumber)
		    .addComponent(txtCVCNumber)  
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
		    .addComponent(lTip)
		    .addComponent(txtTip)  
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        
        setSize(800,550);

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
           if(e.getActionCommand().equals("order")){
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtAddress.getText() + "\n");
               strOrder.append(txtPhone.getText() + "\n");
	       strOrder.append(txtTip.getText() + "\n");
               strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strOrder.append(nextBox.getActionCommand() + " ");
                       
                   }
                   
               }
               
               txtOrder.setText(strOrder.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}
