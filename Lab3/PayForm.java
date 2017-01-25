import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class PayForm extends JFrame implements ActionListener
{

	JTextField txtName;
	JTextField txtCreditCard;
	JTextField txtEmail;
	JTextArea txtPay;
	
	ButtonGroup btnGroupRadio;
	ArrayList<JCheckBox> btnCheck;
	
	PayForm()
	{
		JPanel panelMain = new JPanel();
		GroupLayout layout = new GroupLayout(panelMain);
		JPanel pnlButtons = new JPanel();
		JPanel pnlRadios = new JPanel();
		
		txtName = new JTextField(20);
		txtCreditCard = new JTextField(50);
		txtEmail = new JTextField(10);
		txtPay = new JTextArea(15,50);
		txtPay.setEditable(false);
		
		JLabel lName = new JLabel("Name");
		JLabel lCreditCard = new JLabel("Credit Card");
		JLabel lEmail = new JLabel("Email");
		
		JRadioButton radAmex = new JRadioButton("American Express");
		radAmex.setActionCommand("American Express");
		JRadioButton radVisa = new JRadioButton("Visa");
		radVisa.setActionCommand("Visa");
		radVisa.setSelected(true);
		JRadioButton radMasterCard = new JRadioButton("MasterCard");
		radMasterCard.setActionCommand("MasterCard");
		JRadioButton radDiscover = new JRadioButton("Discover");
		radDiscover.setActionCommand("Discover");
		
		btnGroupRadio = new ButtonGroup();
		btnGroupRadio.add(radAmex);
		btnGroupRadio.add(radVisa);
		btnGroupRadio.add(radMasterCard);
		btnGroupRadio.add(radDiscover);
		
		JLabel lblCard = new JLabel("Type of Card:  ");
		lblCard.setFont(new Font(lblCard.getFont().getName(), Font.PLAIN, 20));
		pnlRadios.add(lblCard);
		pnlRadios.add(radAmex);
		pnlRadios.add(radVisa);
		pnlRadios.add(radMasterCard);
		pnlRadios.add(radDiscover);

		JLabel lblReceipt = new JLabel("Receipt:   ");
		lblReceipt.setFont(new Font(lblReceipt.getFont().getName(), Font.PLAIN, 20));

		JCheckBox chkPrintedReceipt = new JCheckBox("Printed Receipt");
		chkPrintedReceipt.setActionCommand("Printed Receipt");
		JCheckBox chkEmailConf = new JCheckBox("Email Confirmation");
		chkEmailConf.setActionCommand("Email Confirmation");

		btnCheck = new ArrayList<JCheckBox>();
		btnCheck.add(chkPrintedReceipt);
		btnCheck.add(chkEmailConf);


		pnlButtons.add(lblReceipt);
		pnlButtons.add(chkPrintedReceipt);
		pnlButtons.add(chkEmailConf);


		JButton btnPay = new JButton("Pay");
		btnPay.setActionCommand("pay");
		btnPay.addActionListener(this);

		panelMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		            .addComponent(lName)
		            .addComponent(txtName)
		            .addComponent(lCreditCard)
		            .addComponent(txtCreditCard)
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
		            .addComponent(lCreditCard)
		            .addComponent(txtCreditCard)
		            .addComponent(lEmail)
		            .addComponent(txtEmail)
		            .addComponent(pnlRadios)
		            .addComponent(pnlButtons)
		            .addComponent(btnPay))
		        .addComponent(txtPay)
		);


		setSize(800,350);

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
           if(e.getActionCommand().equals("pay")){
               StringBuffer strPay= new StringBuffer();
               strPay.append(txtName.getText() + "\n");
               strPay.append(txtCreditCard.getText() + "\n");
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
