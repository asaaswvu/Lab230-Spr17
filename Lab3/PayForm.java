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
import java.awt.event.*;
import java.util.ArrayList;


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

        setSize(800, 600);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pay Form");




    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String args[]) {new PayForm();}
}
