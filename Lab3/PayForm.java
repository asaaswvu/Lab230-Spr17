/*
 * Daniel Jones
 * CS 230 Lab
 * dpjones@mix.wvu.edu
 *
 * Stealing my code will result in a group of angry cats meowing loudly,
 * at your door,
 * for the rest of your life.
 * If you like that sound you won't after hearing it 24/7 365 ^_^
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;


class PayForm extends JFrame implements ActionListener {
    JTextField txtName;
    JTextField txtAddress;
    JTextField txtEmail;
    JTextField txtCreditCardNum;
    JTextArea txtPayForm;

    ButtonGroup myRadioButtons;
    ArrayList<JCheckBox> btnCheck;

    PayForm() {
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();

        txtName = new JTextField(10);
        txtAddress = new JTextField(50);
        txtEmail = new JTextField(10);
        txtCreditCardNum = new JTextField(10);
        txtPayForm = new JTextArea(20,50);

        JLabel lName = new JLabel("Name");
        JLabel lAddress = new JLabel("Address");
        JLabel lEmail = new JLabel("Email");
        JLabel lCreditCardNum = new JLabel("Credit Card Number");
        JRadioButton radVisa = new JRadioButton("Visa");
        radVisa.setActionCommand("Visa");
        JRadioButton radMastercard = new JRadioButton("Mastercard");
        radVisa.setActionCommand("Mastercard");
        JRadioButton radDiscover = new JRadioButton("Discover");
        radVisa.setActionCommand("Discover");
        JRadioButton radAmex = new JRadioButton("Amex");
        radVisa.setActionCommand("Amex");

        myRadioButtons = new ButtonGroup();
        myRadioButtons.add(radAmex);
        myRadioButtons.add(radVisa);
        myRadioButtons.add(radDiscover);
        myRadioButtons.add(radMastercard);

        pnlRadios.add(radAmex);
        pnlRadios.add(radDiscover);
        pnlRadios.add(radMastercard);
        pnlRadios.add(radVisa);

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
                                .addComponent(lAddress)
                                .addComponent(txtAddress)
                                .addComponent(lCreditCardNum)
                                .addComponent(txtEmail)
                                .addComponent(pnlRadios)
                                .addComponent(pnlButtons)
                                .addComponent(btnOrder))
                        .addComponent(txtPayForm)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(lName)
                                .addComponent(txtName)
                                .addComponent(lAddress)
                                .addComponent(txtAddress)
                                .addComponent(lCreditCardNum)
                                .addComponent(txtEmail)
                                .addComponent(pnlRadios)
                                .addComponent(pnlButtons)
                                .addComponent(btnOrder))
                        .addComponent(txtPayForm)
        );

        setSize(800, 600);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pay Form");

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

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("")) {
            StringBuffer strOrder = new StringBuffer();
            strOrder.append(txtName.getText() + "\n");
            strOrder.append(txtAddress.getText() + "\n");
            strOrder.append(txtCreditCardNum.getText() + "\n");
            strOrder.append(myRadioButtons.getSelection().getActionCommand() + "\n");

            Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
            while(iterCheckButtons.hasNext()){
                JCheckBox nextBox = iterCheckButtons.next();
                if(nextBox.isSelected()){
                    strOrder.append(nextBox.getActionCommand() + " ");

                }

            }

            txtPayForm.setText(strOrder.toString());
        }
    }

    public static void main(String args[]) {new PayForm();}
}
