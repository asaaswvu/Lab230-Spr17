import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener{

    JTextField txtName;
    JTextField txtCCNum;
    JTextField txtEmail;
    JTextArea txtInfo;

    ButtonGroup btnGroupRadio;
    ArrayList<JCheckBox> btnCheck;
	
    PayForm(){

    JPanel panelMain = new JPanel();
    GroupLayout layout = new GroupLayout(panelMain);
    JPanel pnlButtons = new JPanel();
    JPanel pnlRadios = new JPanel();

    txtName = new JTextField(20);
    txtCCNum = new JTextField(16);
    txtEmail = new JTextField(50);
    txtInfo = new JTextArea(15,50);
    txtInfo.setEditable(false);

    JLabel lName = new JLabel("Name");
    lName.setForeground(Color.yellow);
    JLabel lCCNum = new JLabel("Card Number");
    lCCNum.setForeground(Color.yellow);
    JLabel lEmail = new JLabel("Email");
    lEmail.setForeground(Color.yellow);

    JRadioButton radVisa = new JRadioButton("Visa");
    radVisa.setActionCommand("Visa");
    radVisa.setSelected(true);
    JRadioButton radAmEx = new JRadioButton("American Express");
    radAmEx.setActionCommand("American Express");
    JRadioButton radMC = new JRadioButton("MasterCard");
    radMC.setActionCommand("Master Card");
    JRadioButton radDiscover = new JRadioButton("Discover");
    radDiscover.setActionCommand("Discover");

    btnGroupRadio = new ButtonGroup();
    btnGroupRadio.add(radVisa);
    btnGroupRadio.add(radMC);
    btnGroupRadio.add(radDiscover);
    btnGroupRadio.add(radAmEx);

    JLabel lblType = new JLabel("Card Type:  ");
    lblType.setFont(new Font(lblType.getFont().getName(), Font.PLAIN, 20));
    pnlRadios.add(lblType);
    pnlRadios.add(radVisa);
    pnlRadios.add(radMC);
    pnlRadios.add(radDiscover);
    pnlRadios.add(radAmEx);

	
    JLabel lblOptions = new JLabel("Options:  ");
    lblOptions.setForeground(Color.white);
    lblOptions.setFont(new Font(lblOptions.getFont().getName(), Font.PLAIN, 20));
    JCheckBox chkPrint = new JCheckBox("Print Receipt");
    chkPrint.setActionCommand("Print");
    JCheckBox chkEmail = new JCheckBox("Send Email Confirmation");
    chkEmail.setActionCommand("Email");
        
    btnCheck = new ArrayList<JCheckBox>();
    btnCheck.add(chkPrint);
    btnCheck.add(chkEmail);

    pnlButtons.add(lblOptions);
    pnlButtons.add(chkPrint);
    pnlButtons.add(chkEmail);

    JButton btnAccept = new JButton("Acccept");
    btnAccept.setForeground(Color.blue);
    btnAccept.setBackground(Color.yellow);
    btnAccept.setActionCommand("accept");
    btnAccept.addActionListener(this);

    panelMain.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    layout.setHorizontalGroup(
       layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lName)
                .addComponent(txtName)
                .addComponent(lCCNum)
                .addComponent(txtCCNum)
                .addComponent(lEmail)
                .addComponent(txtEmail)
                .addComponent(pnlRadios)
                .addComponent(pnlButtons)
                .addComponent(btnAccept))
            .addComponent(txtInfo)
    );

    layout.setVerticalGroup(
        layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addComponent(lName)
                .addComponent(txtName)
                .addComponent(lCCNum)
                .addComponent(txtCCNum)
                .addComponent(lEmail)
                .addComponent(txtEmail)
                .addComponent(pnlRadios)
                .addComponent(pnlButtons)
                .addComponent(btnAccept))
            .addComponent(txtInfo)
    );

    setSize(800,350);

    //tells java what to do when the class object closes
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Pay Form");

        
    //get visible container and add panelMain to it
    //EVERYTHING has to be arranged and set before adding to ContentPane
    panelMain.setBackground(Color.blue);
    getContentPane().add(panelMain);

    //this centers the window in the screen
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
    setLocation(x, y);
        
    //make sure you can actually see it, starts off false
    setVisible(true);

    } // end PayForm


    public void actionPerformed(ActionEvent e){
       if(e.getActionCommand().equals("accept")){
           StringBuffer strAccept = new StringBuffer();
           strAccept.append(txtName.getText() + "\n");
           strAccept.append(txtCCNum.getText() + "\n");
           strAccept.append(txtEmail.getText() + "\n");
           strAccept.append(btnGroupRadio.getSelection().getActionCommand() + "\n");
               
           Iterator<JCheckBox> iterCheckButtons = btnCheck.iterator();
           while(iterCheckButtons.hasNext()){
               JCheckBox nextBox = iterCheckButtons.next();
               if(nextBox.isSelected()){
                   strAccept.append(nextBox.getActionCommand() + " ");
                       
               } //end if
                   
           } //end while
               
               txtInfo.setText(strAccept.toString());
       }//end if
    }

    public static void main(String args[]){
        new PayForm();
    }// end main

}// end class

