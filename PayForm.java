// Chad Milburn
// gitHub Account Name: crmskins
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
    
    JTextField txtName;
    JTextField txtCCN;
    JTextField txtEmail;
    JTextArea txtOrder;
    JTextField txtYear;
    JTextField txtMonth;
    
    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
    
    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtCCN = new JTextField(50);
        txtMonth = new JTextField(20);
        txtYear = new JTextField(20);
        txtEmail = new JTextField(10);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);
        
        JLabel lName = new JLabel("Name");
        JLabel lCCN = new JLabel("Credit Card Number");
        JLabel lmonth= new JLabel("Expiration Month");
        String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
        @SuppressWarnings("unchecked")
        JLabel lyear= new JLabel("Expiration Year");
        JLabel lEmail = new JLabel("Email Address");
        JRadioButton radAmex = new JRadioButton("Amex");
        radAmex.setActionCommand("Amex");
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        radVisa.setSelected(true);
        JRadioButton radMC = new JRadioButton("MasterCard");
        radMC.setActionCommand("MasterCard");
        JRadioButton radDiscover = new JRadioButton("Discover");
        radDiscover.setActionCommand("Discover");

        radVisa.setBackground(Color.yellow);
        radAmex.setBackground(Color.cyan);
        radMC.setBackground(Color.red);
        radDiscover.setBackground(Color.orange);
        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(radAmex);
        btnGroupRadio.add(radVisa);
        btnGroupRadio.add(radMC);
        btnGroupRadio.add(radDiscover);

        JLabel lblSize = new JLabel("Credit Card Brand: ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(radVisa);
        pnlRadios.add(radAmex);
        pnlRadios.add(radMC);
        pnlRadios.add(radDiscover);

        JLabel lblFeatures = new JLabel("Additional Features:  ");
        lblFeatures.setFont(new Font(lblFeatures.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkReceipt = new JCheckBox("Print Receipt");
        chkReceipt.setActionCommand("Print Receipt");
        JCheckBox chkEmail = new JCheckBox("Email Confirmation");
        chkEmail.setActionCommand("Email Confirmation");

        chkReceipt.setBackground(Color.magenta);
        chkEmail.setBackground(Color.green);
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkReceipt);
        btnCheck.add(chkEmail);
        

        
        pnlButtons.add(lblFeatures);
        pnlButtons.add(chkReceipt);
        pnlButtons.add(chkEmail);

        
        JButton btnPay = new JButton("Pay Now");
        btnPay.setActionCommand("paid");
        btnPay.addActionListener(this);
        btnPay.setBackground(Color.pink);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCCN)
                    .addComponent(txtCCN)
                    .addComponent(lmonth)
                    .addComponent(txtMonth)
                    .addComponent(lyear)
                    .addComponent(txtYear)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnPay))
                .addComponent(txtOrder)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCCN)
                    .addComponent(txtCCN)
                    .addComponent(lmonth)
                    .addComponent(txtMonth)
                    .addComponent(lyear)
                    .addComponent(txtYear)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnPay))
                .addComponent(txtOrder)
        );
        
        setSize(700,450);
        
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
           if(e.getActionCommand().equals("paid")){
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtCCN.getText() + "\n");
               strOrder.append(txtMonth.getText() + "\n");
               strOrder.append(txtYear.getText() + "\n");
               strOrder.append(txtEmail.getText() + "\n");
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
