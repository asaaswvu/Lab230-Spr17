
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class Pizza extends JFrame implements ActionListener{

    JTextField txtName;
    JTextField txtAddress;
    JTextField txtPhone;
    JTextArea txtOrder;

    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;

    Pizza(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();

        txtName = new JTextField(20);
        txtAddress = new JTextField(50);
        txtPhone = new JTextField(10);
        txtOrder = new JTextArea(15,50);
        txtOrder.setEditable(false);

        JLabel lName = new JLabel("Name");
        JLabel lAddress = new JLabel("Address");
        JLabel lPhone = new JLabel("Phone");
        JRadioButton rad12 = new JRadioButton("12 inch");
        rad12.setActionCommand("12inch");
        JRadioButton rad10 = new JRadioButton("10 inch");
        rad10.setActionCommand("10inch");
        rad10.setSelected(true);
        JRadioButton rad16 = new JRadioButton("16 inch");
        rad16.setActionCommand("16inch");

        btnGroupRadio = new ButtonGroup();
        btnGroupRadio.add(rad12);
        btnGroupRadio.add(rad10);
        btnGroupRadio.add(rad16);

        JLabel lblSize = new JLabel("Size:  ");
        lblSize.setFont(new Font(lblSize.getFont().getName(), Font.PLAIN, 20));
        pnlRadios.add(lblSize);
        pnlRadios.add(rad10);
        pnlRadios.add(rad12);
        pnlRadios.add(rad16);

        JLabel lblToppings = new JLabel("Toppings:  ");
        lblToppings.setFont(new Font(lblToppings.getFont().getName(), Font.PLAIN, 20));
        JCheckBox chkPepperoni = new JCheckBox("Pepperoni");
        chkPepperoni.setActionCommand("Pepperoni");
        JCheckBox chkPeppers = new JCheckBox("Peppers");
        chkPeppers.setActionCommand("Peppers");
        JCheckBox chkSausage = new JCheckBox("Sausage");
        chkSausage.setActionCommand("Sausage");

        btnCheck = new ArrayList<JCheckBox>();
        btnCheck.add(chkPepperoni);
        btnCheck.add(chkPeppers);
        btnCheck.add(chkSausage);

        pnlButtons.add(lblToppings);
        pnlButtons.add(chkPepperoni);
        pnlButtons.add(chkPeppers);
        pnlButtons.add(chkSausage);

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
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
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
                    .addComponent(lAddress)
                    .addComponent(txtAddress)
                    .addComponent(lPhone)
                    .addComponent(txtPhone)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(btnOrder))
                .addComponent(txtOrder)
        );

        setSize(600,350);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Pizza Order");


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
           if(e.getActionCommand().equals("order")){
               StringBuffer strOrder = new StringBuffer();
               strOrder.append(txtName.getText() + "\n");
               strOrder.append(txtAddress.getText() + "\n");
               strOrder.append(txtPhone.getText() + "\n");
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
        new Pizza();
    }
}
