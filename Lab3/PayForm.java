import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

class PayForm extends JFrame implements ActionListener
{
	JTextField txtName;
    	JTextField txtCardNum;
   	JTextField txtEmail;
	JTextArea txtInfo;

	ButtonGroup btnGroupRadio;
	ArrayList<JCheckBox> btnCheck;

	PayForm()
	{
		JPanel panelMain = new JPanel();
        	GroupLayout layout = new GroupLayout(panelMain);
	        JPanel pnlButtons = new JPanel();
        	JPanel pnlRadios = new JPanel();

		JLabel lName = new JLabel("Name");
		JLabel lCardNum = new JLabel("Credit Card Number");
		JLabel lEmail = new JLabel("Email");
		
		txtName = new JTextField(20);
        	txtCardNum = new JTextField(50);
        	txtEmail = new JTextField(10);
        	txtInfo = new JTextArea(15,50);
        	txtInfo.setEditable(false);

		JRadioButton radVisa = new JRadioButton("Visa");
        	radVisa.setActionCommand("Visa");
        	JRadioButton radMasterCard = new JRadioButton("MasterCard");
        	radMasterCard.setActionCommand("MasterCard");
        	JRadioButton radAmex = new JRadioButton("American Express");
        	radAmex.setActionCommand("Amex");
		JRadioButton radDiscover = new JRadioButton("Discover");
		radDiscover.setActionCommand("Discover");

		btnGroupRadio = new ButtonGroup();
        	btnGroupRadio.add(radVisa);
        	btnGroupRadio.add(radMasterCard);
        	btnGroupRadio.add(radAmex);
		btnGroupRadio.add(radDiscover);

		JLabel lblCard = new JLabel("Card type: ");
        	lblCard.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        	pnlRadios.add(lblCard);
        	pnlRadios.add(radVisa);
        	pnlRadios.add(radMasterCard);
        	pnlRadios.add(radAmex);
		pnlRadios.add(radDiscover);

		JLabel lblConfirmation = new JLabel("Check any you would like to recieve: ");
        	JCheckBox chkReceipt = new JCheckBox("Paper receipt with delivery");
        	chkReceipt.setActionCommand("Receipt");
        	JCheckBox chkEmail = new JCheckBox("Email receipt with confirmation");
        	chkEmail.setActionCommand("EmailConf");

		btnCheck = new ArrayList<JCheckBox>();
        	btnCheck.add(chkReceipt);
        	btnCheck.add(chkEmail);

		pnlButtons.add(lblConfirmation);
       		pnlButtons.add(chkReceipt);
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
                    .addComponent(txtName)
                    .addComponent(lCardNum)
                    .addComponent(txtCardNum)
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
                    .addComponent(lCardNum)
                    .addComponent(txtCardNum)
                    .addComponent(lEmail)
                    .addComponent(txtEmail)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder));

		setSize(600,350);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        	setTitle("Pay Form");

		panelMain.setColor(Color.cyan);
		getContentPane().add(panelMain);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        	int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        	int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        	setLocation(x, y);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("order"))
		{
               		StringBuffer strOrder = new StringBuffer();
              		strOrder.append(txtName.getText() + "\n");

			//displays only the last four digits of the entered card number
			String cardNumDisplay = "XXXX-XXXX-XXXX-";
			String realNum = txtCardNum.getText();
			String last4 = realNum.substring(12);
			cardNumDisplay += last4;
               		strOrder.append(cardNumDisplay + "\n");

               		strOrder.append(txtEmail.getText() + "\n");
               		strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
               		Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
               		while(iterCheckButtons.hasNext())
			{
                   		JCheckBox nextBox = iterCheckButtons.next();
                   		if(nextBox.isSelected())
				{
                	       		strOrder.append(nextBox.getActionCommand() + " ");     
				}
			}  
               		txtInfo.setText(strOrder.toString());
		}
	}

	public static void main(String[] args)
	{
		new PayForm();
	}

}









