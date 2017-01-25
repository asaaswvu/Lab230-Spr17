// Edgar Villarreal

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;



public class PayForm extends JFrame implements ActionListener{

	JTextField txtName;
	JTextField txtCredit;
	JTextField txtEmail;
	JTextArea txtPayment;
	JLabel lName;
	JLabel lCredit;
	JLabel lEmail;
	JLabel lblType;
	JLabel lblOptions;
	JCheckBox chkEmail;
	JCheckBox chkReceipt;
	
	ButtonGroup btnGroupRadio;
	ArrayList<JCheckBox> btnCheck;
	
	PayForm(){
		JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();
        
        txtName = new JTextField(20);
        txtCredit = new JTextField(15);
        txtEmail = new JTextField(30);
        txtPayment = new JTextArea(15,25);
        txtPayment.setEditable(false);
        
        lName = new JLabel("Name");
        lName.setFont(new Font(lName.getFont().getName(), Font.PLAIN, 20));
        lCredit = new JLabel("Credit Card Number");
        lCredit.setFont(new Font(lCredit.getFont().getName(), Font.PLAIN, 20));
        lEmail = new JLabel("Email Address");
        lEmail.setFont(new Font(lEmail.getFont().getName(), Font.PLAIN, 20));
        JRadioButton radAmex = new JRadioButton("American Express");
        radAmex.setActionCommand("American Express");
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        JRadioButton radMaster = new JRadioButton("Mastercard");
        radMaster.setActionCommand("Mastercard");
        JRadioButton radDiscover = new JRadioButton("Discovercard");
        radDiscover.setActionCommand("Discovercard");
        radAmex.setSelected(true);
        
		btnGroupRadio = new ButtonGroup();
		btnGroupRadio.add(radAmex);
		btnGroupRadio.add(radVisa);
		btnGroupRadio.add(radMaster);
		btnGroupRadio.add(radDiscover);
		
		lblType = new JLabel("Card Type:  ");
        lblType.setFont(new Font(lblType.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblType);
        pnlRadios.add(radAmex);
        pnlRadios.add(radVisa);
        pnlRadios.add(radMaster);
        pnlRadios.add(radDiscover);
        
        lblOptions = new JLabel("Options:  ");
        lblOptions.setFont(new Font(lblOptions.getFont().getName(), Font.PLAIN, 20));
        chkEmail = new JCheckBox("Email Confirmation");
        chkEmail.setActionCommand("Email");
        chkReceipt = new JCheckBox("Print Receipt");
        chkReceipt.setActionCommand("Receipt");
        
        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkEmail);
        btnCheck.add(chkReceipt);
        
        pnlButtons.add(lblOptions);
        pnlButtons.add(chkEmail);
        pnlButtons.add(chkReceipt);
        
        JButton btnPayment = new JButton("Place Payment");
        btnPayment.setActionCommand("Pay");
        btnPayment.addActionListener(this);
        
        JButton btnSpanish = new JButton("Spanish");
        btnSpanish.setActionCommand("Spanish");
        btnSpanish.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(lName)
                         .addComponent(txtName)
                         .addComponent(lCredit)
                         .addComponent(txtCredit)
                         .addComponent(lEmail)
                         .addComponent(txtEmail)
                         .addComponent(pnlRadios)
                         .addComponent(pnlButtons)
                         .addComponent(btnPayment)
                         .addComponent(btnSpanish))
                     .addComponent(txtPayment)
             );
        layout.setVerticalGroup(
                 layout.createParallelGroup()
                     .addGroup(layout.createSequentialGroup()
                    		 .addComponent(lName)
                             .addComponent(txtName)
                             .addComponent(lCredit)
                             .addComponent(txtCredit)
                             .addComponent(lEmail)
                             .addComponent(txtEmail)
                             .addComponent(pnlRadios)
                             .addComponent(pnlButtons)
                             .addComponent(btnPayment)
                             .addComponent(btnSpanish))
                     .addComponent(txtPayment)
             );
             
             setSize(800,350);
             
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Credit Card Payment");
        
        getContentPane().add(panelMain);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
        
        setVisible(true);  
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Pay")){
            StringBuffer strPayment = new StringBuffer();
            strPayment.append(txtName.getText() + "\n");
            strPayment.append(txtCredit.getText() + "\n");
            strPayment.append(txtEmail.getText() + "\n");
            strPayment.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
            
            Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
            while(iterCheckButtons.hasNext()){
                JCheckBox nextBox = iterCheckButtons.next();
                if(nextBox.isSelected()){
                    strPayment.append(nextBox.getActionCommand() + " ");    
                }
            }
            txtPayment.setText(strPayment.toString());
        }
		if(e.getActionCommand().equals("Spanish")){
			lName.setText("Nombre");
			lCredit.setText("Número de Tarjeta de Crédito");
			lEmail.setText("Correo Electrónico");
			lblType.setText("Tipo de Tarjeta");
            lblOptions.setText("Opciones: ");
            chkEmail.setText("Confirmación de Correo Electrónico");
            chkReceipt.setText("Recibo");
            chkEmail.setActionCommand("Correo Electrónico");
            chkReceipt.setActionCommand("Recibo");
		}
	}

	
	public static void main(String args[]){
		new PayForm();
	}
}
