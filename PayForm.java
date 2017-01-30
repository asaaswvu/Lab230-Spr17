import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PayForm extends JFrame implements ActionListener {

	JTextField txtName;
	JTextField txtCC;
	JTextField txtemail;
	JTextArea txtArea;

	ButtonGroup btnGroupCC;
	ArrayList<JCheckBox> btnCheck;

	PayForm(){
	
		JPanel mainPan = new JPanel();
		GroupLayout layout = new GroupLayout(mainPan);
		JPanel pnlButtons = new JPanel();
		JPanel pnlCC = new JPanel();

		txtName = new JTextField(1);
		txtCC = new JTextField(1);
		txtemail = new JTextField(1);
		txtArea = new JTextArea(5,10);
        txtArea.setEditable(false);
        
        
		JLabel lName = new JLabel("<html><font color='white' size = '4'><b>Name on card:</b></font></html>");
		JLabel lType = new JLabel("<html><font color='white' size = '4'><b>Issuer:</b></font></html>");
		JLabel lCC = new JLabel("<html><font color='white' size = '4'><b>Credit card number:</b></font></html>");
		JLabel lEmail = new JLabel("<html><font color='white' size = '4'><b>E-mail address:</b></font></html>");
		JLabel lReceipt = new JLabel("Receipt delivery options:");
		JLabel lArea = new JLabel("Receipt:");
		
		JRadioButton radV = new JRadioButton("Visa");
		radV.setSelected(true);
		JRadioButton radM = new JRadioButton("Mastercard");
		JRadioButton radA = new JRadioButton("American Express");
		JRadioButton radD = new JRadioButton("Discover");
		
		JCheckBox chkPrint = new JCheckBox("Print");
		JCheckBox chkEmail = new JCheckBox("Email");

        chkPrint.setActionCommand("Print");
        chkEmail.setActionCommand("Email");

        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPrint);
        btnCheck.add(chkEmail);

        pnlButtons.add(lReceipt);
        pnlButtons.add(chkPrint);
        pnlButtons.add(chkEmail);
		
		btnGroupCC = new ButtonGroup();
		btnGroupCC.add(radV);
		btnGroupCC.add(radM);
		btnGroupCC.add(radA);
		btnGroupCC.add(radD);
		
		pnlCC.add(radV);
		pnlCC.add(radM);
		pnlCC.add(radA);
		pnlCC.add(radD);
		
		radV.setActionCommand("Visa");
		radM.setActionCommand("Mastercard");
		radA.setActionCommand("American Express");
		radD.setActionCommand("Discover");
		
		JButton btnSubmit = new JButton("<html><font color='white' size = '4'><b>submit</b></font></html>");
		btnSubmit.setActionCommand("submit");
		btnSubmit.addActionListener(this);
		
		JButton btnCancel = new JButton("<html><font color='white' size = '4'><b>cancel</b></font></html>");
		btnCancel.setActionCommand("cancel");
		btnCancel.addActionListener(this);
		JPanel pGroupsc = new JPanel();
		pGroupsc.add(btnSubmit);
		pGroupsc.add(btnCancel);
		
		txtName.setBorder(BorderFactory.createLineBorder(Color.decode("#C9E6FF"), 2, false));
		txtCC.setBorder(BorderFactory.createLineBorder(Color.decode("#C9E6FF"), 2, false));
		txtemail.setBorder(BorderFactory.createLineBorder(Color.decode("#C9E6FF"), 2, false));
		radV.setBackground(Color.decode("#C9E6FF"));
		radM.setBackground(Color.decode("#C9E6FF"));
		radA.setBackground(Color.decode("#C9E6FF"));
		radD.setBackground(Color.decode("#C9E6FF"));
		chkPrint.setBackground(Color.decode("#C9E6FF"));
		chkEmail.setBackground(Color.decode("#C9E6FF"));
		pGroupsc.setBackground(Color.decode("#C9E6FF"));
		pnlCC.setBackground(Color.decode("#C9E6FF"));
		pnlButtons.setBackground(Color.decode("#C9E6FF"));
		
		btnSubmit.setBackground(Color.decode("#2190FF"));
		btnCancel.setBackground(Color.decode("#2190FF"));

        layout.setHorizontalGroup(

                layout.createSequentialGroup()
                	
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)

                         .addComponent(lName)
                         .addComponent(lType)
                     	 .addComponent(lCC)
                     	 .addComponent(lEmail))
                         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(txtName)
                         .addComponent(pnlButtons)
                         .addComponent(pnlCC)
                         .addComponent(txtCC)
                         .addComponent(txtemail)
                         .addComponent(pGroupsc)
                         .addGroup(layout.createParallelGroup()
                                 .addComponent(txtArea))
                         )
                         

             );

             layout.setVerticalGroup(

                 layout.createSequentialGroup()
                 	 
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                         .addComponent(lName)
                         .addComponent(txtName))
                     .addComponent(pnlButtons)
                     .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                             .addComponent(lType)
                             .addComponent(pnlCC))
                     .addGroup(layout.createParallelGroup()
                         .addComponent(lCC)
                         .addComponent(txtCC))
                      .addGroup(layout.createParallelGroup()
                         .addComponent(lEmail)
                         .addComponent(txtemail))
                      .addComponent(pGroupsc)
                      .addGroup(layout.createParallelGroup()
                      .addComponent(txtArea))
                      
                     
             );
						
		
        mainPan.setBackground(Color.decode("#2190FF"));
		mainPan.setBorder(BorderFactory.createLineBorder(Color.white, 2, false));
		mainPan.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		
		setSize(625, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		setTitle("Payment Details");
		getContentPane().add(mainPan);

	
	}

	public void actionPerformed(ActionEvent e) {
		JPanel jp = new JPanel();
		if (e.getActionCommand().equals("submit")) {

			if (txtCC.getText().equals("")) {
				JOptionPane.showMessageDialog(jp, "The credit card field is empty.", "Payment not processed",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (btnGroupCC.getSelection().getActionCommand().equals("Visa")
					&& !txtCC.getText().substring(0, 1).equals("4")) {
				JOptionPane.showMessageDialog(jp, "Visa card number must begin with a 4. Please edit and resubmit.",
						"Payment not processed", JOptionPane.ERROR_MESSAGE);
			} else if (btnGroupCC.getSelection().getActionCommand().equals("Mastercard")
					&& !txtCC.getText().substring(0, 1).equals("5")) {
				JOptionPane.showMessageDialog(jp, "Mastercard number must begin with a 5. Please edit and resubmit.",
						"Payment not processed", JOptionPane.ERROR_MESSAGE);
			} else if (btnGroupCC.getSelection().getActionCommand().equals("American Express")
					&& !txtCC.getText().substring(0, 1).equals("3")) {
				JOptionPane.showMessageDialog(jp,
						"American Express card number must begin with a 3. Please edit and resubmit.",
						"Payment not processed", JOptionPane.ERROR_MESSAGE);
			} else if (btnGroupCC.getSelection().getActionCommand().equals("Discover")
					&& !txtCC.getText().substring(0, 1).equals("6")) {
				JOptionPane.showMessageDialog(jp, "Discover card number must begin with a 6. Please edit and resubmit.",
						"Payment not processed", JOptionPane.ERROR_MESSAGE);
			} else {

				StringBuffer strOrder = new StringBuffer();
				strOrder.append("Name: \n" + txtName.getText() + "\n");
				strOrder.append("Credit card number: \n" + txtCC.getText() + "\n");
				strOrder.append("Email: \n" + txtemail.getText() + "\n");
				strOrder.append("Credit card type: \n " + btnGroupCC.getSelection().getActionCommand() + "\n"
						+ "Method of receipt delivery: ");

				Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();

				while (iterCheckButtons.hasNext()) {

					JCheckBox nextBox = iterCheckButtons.next();

					if (nextBox.isSelected()) {

						strOrder.append(nextBox.getActionCommand() + " ");

					}

				}

				txtArea.setText(strOrder.toString());

			}

		}

		if (e.getActionCommand().equals("cancel"))
			System.exit(1);
	}

	public static void main(String args[]) {

		try {
			// Set cross-platform Java L&F
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// handle exception
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			// handle exception
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			// handle exception
			System.out.println(e.getMessage());
		}

		new PayForm();

	}

}
