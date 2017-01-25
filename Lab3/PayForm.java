
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener
{
    	JTextField txtName;
    	JTextField txtCreditCardNumber;
    	JTextField txtEmail;
    	JTextArea txtOrder;
	
	ButtonGroup btnGroupRadio;
    	ArrayList<JCheckBox> btnCheck;

	PayForm()
	{

		setSize(650, 600);
		JPanel panelMain = new JPanel();
        	GroupLayout layout = new GroupLayout(panelMain);
        	JPanel pnlButtons = new JPanel();
        	JPanel pnlRadios = new JPanel();
		pnlButtons.setBackground(Color.BLUE);
		pnlRadios.setBackground(Color.RED);		

	
		txtName = new JTextField(20);
        	txtCreditCardNumber = new JTextField(50);
        	txtEmail = new JTextField(10);
        	txtOrder = new JTextArea(15,50);
        	txtOrder.setEditable(false);

		JLabel lName = new JLabel("Name:");
        	JLabel lCredit = new JLabel("Credit Card Number:");
        	JLabel lEmail = new JLabel("E-Mail:");


		JLabel lblType = new JLabel("Type:  ");
		lblType.setFont(new Font(lblType.getFont().getName(), Font.BOLD, 18));
		JRadioButton radAmex = new JRadioButton("Amex");
        	radAmex.setActionCommand("Amex");
		radAmex.setBackground(Color.RED);
        	JRadioButton radVisa = new JRadioButton("Visa");
		radVisa.setBackground(Color.RED);
        	radVisa.setActionCommand("Visa");
        	radVisa.setSelected(true);
        	JRadioButton radMastercard = new JRadioButton("Mastercard");
        	radMastercard.setActionCommand("Mastercard");
		radMastercard.setBackground(Color.RED);
		JRadioButton radDiscover = new JRadioButton("Discover");
        	radDiscover.setActionCommand("Discover");
		radDiscover.setBackground(Color.RED);

		btnGroupRadio = new ButtonGroup();
        	btnGroupRadio.add(radAmex);
       		btnGroupRadio.add(radVisa);
        	btnGroupRadio.add(radMastercard);
        	btnGroupRadio.add(radDiscover);
		pnlRadios.add(lblType);
		pnlRadios.add(radAmex);
		pnlRadios.add(radVisa);
		pnlRadios.add(radMastercard);
		pnlRadios.add(radDiscover);
		

		JLabel lblReturn = new JLabel("Return");
		lblReturn.setFont(new Font(lblReturn.getFont().getName(), Font.BOLD, 18));
		JCheckBox chkReceipt = new JCheckBox("Print Receipt");
        	chkReceipt.setActionCommand("Receipt");
       		JCheckBox chkEmail = new JCheckBox("E-Mail Confirmation");
        	chkEmail.setActionCommand("Email");
		btnCheck = new ArrayList<JCheckBox>();
		btnCheck.add(chkReceipt);
		btnCheck.add(chkEmail);
		for(JCheckBox i: btnCheck)
		{
			i.setBackground(Color.BLUE);
		}
		pnlButtons.add(lblReturn);
       		pnlButtons.add(chkReceipt);
		pnlButtons.add(chkEmail);


		JButton btnOrder = new JButton("Place Order");
        	btnOrder.setActionCommand("Order");
        	btnOrder.addActionListener(this);
        
        	panelMain.setLayout(layout);
        	layout.setAutoCreateGaps(true);
        	layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(
           	layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCredit)
                    .addComponent(txtCreditCardNumber)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder));
        	layout.setVerticalGroup(
           	layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lName)
                    .addComponent(txtName)
                    .addComponent(lCredit)
                    .addComponent(txtCreditCardNumber)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );
	 //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pay Form");

        
        //get visible container and add panelMain to it
        //EVERYTHING has to be arranged and set before adding to ContentPane
	panelMain.setBackground(Color.YELLOW);        
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
           if(e.getActionCommand().equals("Order")){
		
	       if(txtName.getText().length()==0 || txtCreditCardNumber.getText().length()==0 || 
		  !txtCreditCardNumber.getText().matches("[0-9]*") || 
		  !txtEmail.getText().matches(".[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+") || txtEmail.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "One or more Fields is invalid", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtCreditCardNumber.getText() + "\n");
               strOrder.append(txtEmail.getText() + "\n");
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




