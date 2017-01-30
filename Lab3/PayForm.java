import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Brandon Phillips
 * bpdev97
 * 800196315
 * 1/29/30
 * Assignment 3 - Payform
 */
public class PayForm extends JFrame implements ActionListener{
    JTextField txtName;
    JTextField txtCardNum;
    JTextField txtCardCVV;
    JTextField txtEmail;
    JTextArea txtOrder;

    JLabel lEmail;

    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;

    PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();

        txtName = new JTextField(20);
        txtCardNum = new JTextField(50);
        txtCardCVV = new JTextField(50);
        txtEmail = new JTextField(10);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);

        JLabel lName = new JLabel("Name");
        JLabel lCardNum = new JLabel("Card Number");
        lEmail = new JLabel("Email");
        JRadioButton radAmex = new JRadioButton("American Express");
        radAmex.setActionCommand("amex");
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("visa");
        radVisa.setSelected(true);
        JRadioButton radMCard = new JRadioButton("Mastercard");
        radMCard.setActionCommand("mcard");
        JRadioButton radDiscover = new JRadioButton("Discover");
        radMCard.setActionCommand("discover");

        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(radAmex);
        btnGroupRadio.add(radVisa);
        btnGroupRadio.add(radMCard);
        btnGroupRadio.add(radDiscover);

        JLabel lblCVV = new JLabel("CVV:  ");

        JLabel lblType = new JLabel("Card Type:  ");
        lblType.setFont(new Font(lblType.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblType);
        pnlRadios.add(radAmex);
        pnlRadios.add(radVisa);
        pnlRadios.add(radMCard);
        pnlRadios.add(radDiscover);

        JLabel lblConfirmation = new JLabel("Confirmation:  ");
        lblConfirmation.setFont(new Font(lblConfirmation.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkEmail = new JCheckBox("Email");
        chkEmail.setActionCommand("Email");
        JCheckBox chkPaper = new JCheckBox("Paper");
        chkPaper.setActionCommand("Paper");

        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkEmail);
        btnCheck.add(chkPaper);

        pnlButtons.add(lblConfirmation);
        pnlButtons.add(chkEmail);
        pnlButtons.add(chkPaper);

        JButton btnOrder = new JButton("Submit");
        btnOrder.setActionCommand("Submit");
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
                                .addComponent(lblCVV)
                                .addComponent(txtCardCVV)
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
                                .addComponent(lCardNum)
                                .addComponent(txtCardNum)
                                .addComponent(lblCVV)
                                .addComponent(txtCardCVV)
                                .addComponent(lEmail)
                                .addComponent(txtEmail)
                                .addComponent(pnlRadios)
                                .addComponent(pnlButtons)
                                .addComponent(btnOrder))
                        .addComponent(txtOrder)
        );

        setSize(700,400);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Billing Information");

        panelMain.setBackground(Color.cyan);
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
        if(e.getActionCommand().equals("Submit")) {
            //Validation (credit card numbers are 13 or 19 chars)
            StringBuffer strOrder = new StringBuffer();
            boolean valid = false;
            if(txtCardNum.getText().matches("^[0-9]{13}$") || txtCardNum.getText().matches("^[0-9]{19}$"))
                if(txtName.getText().matches("^[A-Z]'?[- a-zA-Z]+$"))
                    if(!txtEmail.getText().trim().equals(""))
                        if(txtCardCVV.getText().matches("([0-9]{3})+"))
                            valid = true;
            if (valid) {
                strOrder.append(txtName.getText() + "\n");
                strOrder.append(txtCardNum.getText() + "\n");
                strOrder.append(txtCardCVV.getText() + "\n");
                strOrder.append(txtEmail.getText() + "\n");
                strOrder.append(btnGroupRadio.getSelection().getActionCommand() + "\n");

                Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
                while (iterCheckButtons.hasNext()) {
                    JCheckBox nextBox = iterCheckButtons.next();
                    if (nextBox.isSelected()) {
                        strOrder.append(nextBox.getActionCommand() + " ");

                    }

                }
            }
            else {
                strOrder.append("Invalid information");
            }

            txtOrder.setText(strOrder.toString());
        }

    }

    public static void main(String args[]){
        new PayForm();
    }
}

