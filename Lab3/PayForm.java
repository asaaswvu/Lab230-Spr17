
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{
	JTextField txtName;
	JTextField txtCCNumber;
	JTextField txtEmail;
	JTextArea txtOrder;
	boolean emailNotification = false, paperNotification = false;
	ButtonGroup btnGroupRadio;
    	ArrayList<JCheckBox> btnCheck;
	ArrayList<JTextArea> cardInfo;

	PayForm(){
		JPanel panelMain = new JPanel();
        	GroupLayout layout = new GroupLayout(panelMain);
        	JPanel pnlButtons = new JPanel();
        	JPanel pnlRadios = new JPanel();

		txtName = new JTextField(30);
        	txtCCNumber = new JTextField(16);
        	txtEmail = new JTextField(30);
        	txtOrder = new JTextArea(30,50);
        	txtOrder.setEditable(false);

		JLabel lName = new JLabel("Name");
		lName.setForeground(Color.blue);
		JLabel lCCNumber = new JLabel("Credit Card Number");
		lCCNumber.setForeground(Color.blue);
		JLabel lEmail = new JLabel("Email Address");
		lEmail.setForeground(Color.blue);
		JRadioButton radAmex = new JRadioButton("American Express");
		radAmex.setActionCommand("American Express");
		JRadioButton radVisa = new JRadioButton("Visa");
		radVisa.setActionCommand("Visa");
		radVisa.setSelected(true);
		JRadioButton radMC = new JRadioButton("Mastercard");
		radMC.setActionCommand("Mastercard");
		JRadioButton radDis = new JRadioButton("Discover");
		radDis.setActionCommand("Discover");

		btnGroupRadio = new ButtonGroup();
		btnGroupRadio.add(radAmex);
		btnGroupRadio.add(radVisa);
		btnGroupRadio.add(radMC);
		btnGroupRadio.add(radDis);
		
		JLabel lblType = new JLabel("Type:  ");
        	lblType.setFont(new Font(lblType.getFont().getName(), Font.PLAIN, 20));
		lblType.setForeground(Color.blue);
        	pnlRadios.add(lblType);
        	pnlRadios.add(radAmex);
        	pnlRadios.add(radVisa);
        	pnlRadios.add(radMC);
		pnlRadios.add(radDis);
		
		JLabel lblRecipt = new JLabel("Recipt:  ");
        	lblRecipt.setFont(new Font(lblRecipt.getFont().getName(), Font.PLAIN, 20));
		lblRecipt.setForeground(Color.blue);
        	JCheckBox chkEmail = new JCheckBox("Email");
        	chkEmail.setActionCommand("Email");
		chkEmail.addActionListener(this);
        	JCheckBox chkPaper = new JCheckBox("Paper");
        	chkPaper.setActionCommand("Paper");
		chkPaper.addActionListerner(this);

		btnCheck = new ArrayList<JCheckBox>();
        	btnCheck.add(chkEmail);
        	btnCheck.add(chkPaper);

		pnlButtons.add(lblRecipt);
        	pnlButtons.add(chkEmail);
        	pnlButtons.add(chkPaper);

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
		            .addComponent(lCCNumber)
		            .addComponent(txtCCNumber)
			    
		            .addComponent(lEmail)
		            .addComponent(txtEmail)
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
		            .addComponent(lCCNumber)
		            .addComponent(txtCCNumber)
		            .addComponent(lEmail)
		            .addComponent(txtEmail)
		            .addComponent(pnlRadios)
		            .addComponent(pnlButtons)
		            .addComponent(btnOrder))
		        .addComponent(txtOrder)
		);

		setSize(700,350);
		

		panelMain.setBackground(Color.yellow);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        	setTitle("Place Order with Card");
		getContentPane().add(panelMain);
       		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        	int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        	int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        	setLocation(x, y);
        	setVisible(true); 
	}

	public void actionPerformed(ActionEvent e){
           if(e.getActionCommand().equals("order")){
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtCCNumber.getText() + "\n");
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
	  if(e.getActionCommand().equals("Email"){
	  	emailNotification = !emailNotification;
	  }
	  if(e.getActionCommand().equals("Paper"){
	  	paperNotification = !paperNotification;
	  }
	}
	
	public static void main(String args[]){
		new PayForm();
	}	
}
