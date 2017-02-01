
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    JTextField name;
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
        
        name = new JTextField(20);
        cardNumber = new JTextField(50);
        email = new JTextField(10);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
		ImageIcon amex = new ImageIcon("amex.jpg");
		ImageIcon visa = new ImageIcon("visa.png");
		ImageIcon master = new ImageIcon("master.png");
		ImageIcon discover = new ImageIcon("discover.png");
		
        JLabel lName = new JLabel("Name:");
        JLabel lCardNumber = new JLabel("Credit Card:");
        JLabel lEmail = new JLabel("eMail:");
        JRadioButton rad12 = new JRadioButton(amex);
        rad12.setActionCommand("Amex");
        JRadioButton rad10 = new JRadioButton(visa);
        rad10.setActionCommand("Visa");
        JRadioButton rad16 = new JRadioButton(master);
        rad16.setActionCommand("Mastercard");
		JRadioButton rad18 = new JRadioButton(discover);
        rad18.setActionCommand("Discover");
		rad10.setSelected(true);
		
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(rad12);
        btnGroupRadio.add(rad10);
        btnGroupRadio.add(rad16);
		btnGroupRadio.add(rad18);

        JLabel lblSize = new JLabel("Card: ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(rad10);
        pnlRadios.add(rad12);
        pnlRadios.add(rad16);
		pnlRadios.add(rad18);
        
        JLabel lblreceiptOpt = new JLabel("Receipt Options:");
        lblreceiptOpt.setFont(new Font(lblreceiptOpt.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkPrint = new JCheckBox("Printed Receipt");
        chkPrint.setActionCommand("Printed Receipt");
        JCheckBox chkEmail = new JCheckBox("eMail Confirmation");
        chkEmail.setActionCommand("eMail");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPrint);
        btnCheck.add(chkEmail);
        
        pnlButtons.add(lblreceiptOpt);
        pnlButtons.add(chkPrint);
        pnlButtons.add(chkEmail);
        
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
                    .addComponent(name)
                    .addComponent(lCardNumber)
                    .addComponent(cardNumber)
                    .addComponent(lEmail)
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
                    .addComponent(name)
                    .addComponent(lCardNumber)
                    .addComponent(cardNumber)
                    .addComponent(lEmail)
                    .addComponent(email)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
        
        setSize(600,350);

        //tells java what to do when the class object closes
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
           if(e.getActionCommand().equals("order")){
			   if(name.getText().equals("") || cardNumber.getText().equals("") || email.getText().equals(""))
				   JOptionPane.showMessageDialog(this,"All fields must be filled, or we cannot complete your order. Please try again.","Error",JOptionPane.WARNING_MESSAGE);
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(name.getText() + "\n");
               strOrder.append(cardNumber.getText() + "\n");
               strOrder.append(email.getText() + "\n");
               strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               while(iterCheckButtons.hasNext()){
                   JCheckBox nextBox = iterCheckButtons.next();
                   if(nextBox.isSelected()){
                       strOrder.append(nextBox.getActionCommand() + ", ");
                       
                   }
                   
               }
               
               txtOrder.setText(strOrder.toString());
           }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String args[]){
        new PayForm();
    }
}