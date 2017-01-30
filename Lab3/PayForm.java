import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class PayForm extends JFrame implements ActionListener{
  private JTextField firstName;
  private JLabel lFirstName;
  private JTextField lastName;
  private JLabel lLastName;
  private JTextField email;
  private JLabel lEmail;
  private JTextField creditCardNumber;
  private JLabel lCreditCardNumber;
  private JComboBox creditCardCompany;
  private JLabel lCreditCardCompany;
  private JCheckBox printReceipt;
  private JCheckBox emailConfirmation;
  private JTextArea displayInfo;
  private JButton submit;
  public PayForm(){
    JPanel mainPanel = new JPanel();
    GroupLayout layout = new GroupLayout(mainPanel);
    firstName = new JTextField(20);
    lastName = new JTextField(20);
    email = new JTextField(20);
    creditCardNumber = new JTextField(20);
    String[] companies = {"Visa","Amex","Mastercard","Discover"};
    creditCardCompany = new JComboBox(companies);
    creditCardCompany.setSelectedIndex(0);
    printReceipt = new JCheckBox("Print Receipt?", true);
    printReceipt.setActionCommand("Receipt");
    emailConfirmation = new JCheckBox("Send Email Confirmation?",true);
    emailConfirmation.setActionCommand("Email");
    displayInfo = new JTextArea(15,50);
    displayInfo.setEditable(false);
    submit = new JButton("Submit");
    submit.setActionCommand("submit");
    submit.addActionListener(this);
    lFirstName = new JLabel("First Name:");
    lLastName = new JLabel("Last Name:");
    lEmail = new JLabel("Email:");
    lCreditCardNumber = new JLabel("Credit Card #:");
    lCreditCardCompany = new JLabel("Credit Card Company:");

    mainPanel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(
      layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addComponent(lFirstName)
          .addComponent(firstName)
          .addComponent(lLastName)
          .addComponent(lastName)
          .addComponent(lEmail)
          .addComponent(email)
          .addComponent(lCreditCardNumber)
          .addComponent(creditCardNumber)
          .addComponent(lCreditCardCompany)
          .addComponent(creditCardCompany)
          .addComponent(printReceipt)
          .addComponent(emailConfirmation)
          .addComponent(submit)
          .addComponent(displayInfo)
        )

    );
    layout.setVerticalGroup(
      layout.createSequentialGroup()
        .addGroup(layout.createSequentialGroup()
          .addComponent(lFirstName)
          .addComponent(firstName)
          .addComponent(lLastName)
          .addComponent(lastName)
          .addComponent(lEmail)
          .addComponent(email)
          .addComponent(lCreditCardNumber)
          .addComponent(creditCardNumber)
          .addComponent(lCreditCardCompany)
          .addComponent(creditCardCompany)
          .addComponent(printReceipt)
          .addComponent(emailConfirmation)
          .addComponent(submit)
          .addComponent(displayInfo)
        )
    );

    setSize(600,1000);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Payment Form");
    getContentPane().add(mainPanel);
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
    setLocation(x, y);
    setVisible(true);
  }
  public String isMatch(String text, String regex){
    if(text.equals("")){
      return "**Required Field";
    }
    if(Pattern.matches(regex,text))
      return text;
    return "**Information Entered Was Incorrect";
  }
  public void actionPerformed(ActionEvent e){
    if(e.getActionCommand().equals("submit")){

      StringBuffer info = new StringBuffer();
      info.append("First - "+firstName.getText() + "\n");
      info.append("Last - "+lastName.getText() + "\n");
      info.append("Email - "+isMatch(email.getText(),"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$") + "\n");
      info.append("Credit Card Number - "+isMatch(creditCardNumber.getText(),"[0-9]{16}") + "\n");
      info.append("Credit Card Company - "+creditCardCompany.getSelectedItem().toString() + "\n");
      info.append("Print Receipt - "+printReceipt.isSelected() +" \n");
      info.append("Send Email - "+emailConfirmation.isSelected() +" \n");
      displayInfo.setText(info.toString());
    }
  }
  public static void main(String[] args){
      new PayForm();
  }
}
