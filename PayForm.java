import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


public class PayForm extends JFrame implements ActionListener{
	private JTextField name;
	private JTextField creditCardNumber;
	private JTextField email;
	private JTextArea textArea;
	private ButtonGroup btnGroup;
	private List<JCheckBox> btnChecks;
	
	
	PayForm(){
		JPanel panelMain = new Jpanel();
		GroupLayout layout = new GroupLayout(panelMain);
		JPanel radioButtonPanel = new JPanel();
		JPanel checkboxPanel = new JPanel();
		
		this.name = new JTextField(20);
		this.creditCardNumber = new JTextField(50);
		this.email = new JTextField(30);
		this.textArea = new JTextArea(15,50);
		this.textArea.setEditable(false);

		//Initialize Lables
		JLabel labelForName = new JLabel("Name");
		JLabel labelForCCNumber = new JLabel("Credit Card Number");
		JLabel labelForEmail = new JLabel("Email");
		
		//Initialize Buttons
		JRadioButton discoverRadio = new JRadioButton("Discover");
		discoverRadio.setActionCommand("discoverSelected");
		
		JRadioButton amexRadio = new JRadioButton("Amex");
		amexRadio.setActionCommand("amexRadioSelected");
		
		JRadioButton visaRadio = new JRadioButton("Visa");
		visaRadio.setActionCommand("visaSelected");
		
		this.btnGroup = new ButtonGroup();
		this.btnGroup.add(visaRadio);
		this.btnGroup.add(amexRadio);
		this.btnGroup.add(discoverRadio);
		
		//Add Radios to Panel
		JLabel buttonGroupLabel = new JLabel("Card Provider: ");
		buttonGroupLabel.setFont(new Font(buttonGroupLabel.getFont().getName(), Font.PLAIN, 20));
		radioButtonPanel.add(buttonGroupLabel);
		radioButtonPanel.add(discoverRadio);
		radioButtonPanel.add(amexRadio);
		radioButtonPanel.add(visaRadio);

		//Initialize Checkbox Lables
		JLabel printReceiptLabel = new JLabel("Proof of Purchase Preference: ");
		printReceiptLabel.setFont(new Font(printReceiptLabel.getFont().getName(), Font.PLAIN, 20));
		
		//Initialize Checkboxes
		JCheckBox emailCheckbox = new JCheckBox("Email");
		emailCheckbox.setActionCommand("emailSelected");
		
		JCheckBox printReceiptCheckbox = new JCheckBox("Print");
		printReceiptCheckbox.setActionCommand("printSelected");
		
		btnChecks = Arrays.asList(emailCheckbox, printReceiptCheckbox);
		
		checkboxPanel.add(printReceiptLabel);
		checkboxPanel.add(emailCheckbox);
		checkboxPanel.add(printReceiptCheckbox);
		
		JButton payButton = new JButton("Pay");
		payButton.setActionCommand("paySelected");
		payButton.addActionListener(this);
		
		panelMain.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		//Create horizontal group
		SequentialGroup horizontalGroup = layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(labelForName)
				.addComponent(name)
				.addComponent(labelForEmail)
				.addComponent(email)
				.addComponent(labelForCCNumber)
				.addComponent(creditCardNumber)
				.addComponent(radioButtonPanel)
				.addComponent(checkboxPanel)
				.addComponent(payButton))
				.addComponent(textArea);
		
		layout.setHorizontalGroup(horizontalGroup);
		
		
		
		
		
		
		this.initializePane(panelMain);
	}
	
	private initializePane(JPanel mainPanel){
		//Initialization Boilerplate
		setSize(600, 350);
		setTitle("Pay Form");
		setDefaultCloseOperation(WindowsConstants.EXIT_ON_CLOSE);
		getContentPane().add(mainPanel);
	    
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
	    setLocation(x, y);
	    
	    setVisible(true); 
	}
	
	
	public static void main(String [] args){
		new PayForm();
	}
}