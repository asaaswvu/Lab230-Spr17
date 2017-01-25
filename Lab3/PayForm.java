import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class PayForm extends JFrame implements ActionListener{
    private String[] stateList = {"AK","AL","AR","AZ","CA","CO","CT","DC","DE","FL","GA","GU","HI","IA","ID",
            "IL","IN", "KS","KY","LA","MA","MD","ME","MH","MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ",
            "NM","NV","NY", "OH","OK","OR","PA","PR","PW","RI","SC","SD","TN","TX","UT","VA","VI","VT","WA",
            "WI","WV","WY"};

    private JTextField nameOnCard, emailAddress, streetAddress, city, phone, zipCode, cardNumber, securityCode;
    private JComboBox<String> states, expMonth, expYear;
    private JTextArea collectedData;

    private ButtonGroup radioGroup;
    private ArrayList<JCheckBox> checkboxes = new ArrayList<>();

    private PayForm(){
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        Font font = new Font("Open Sans", Font.PLAIN, 20);

        JLabel nameLabel = new JLabel("Name On Card");
        nameOnCard = new JTextField(20);
        JLabel addressLabel = new JLabel("Street Address");
        streetAddress = new JTextField(50);

        JPanel zipStatePanel = new JPanel();
        city = new JTextField(15);
        states = new JComboBox<>(stateList);
        zipCode = new JTextField(5);
        zipStatePanel.add(new JLabel("City"));
        zipStatePanel.add(city);
        zipStatePanel.add(states);
        zipStatePanel.add(new JLabel("Zip Code"));
        zipStatePanel.add(zipCode);

        JLabel emailLabel = new JLabel("Email Address");
        emailAddress = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number");
        phone = new JTextField(10);

        collectedData = new JTextArea(15,25);
        collectedData.setEditable(false);

        // radio buttons
        String[] cards = {"Visa", "American Express", "Master Card", "Discover"};
        radioGroup = new ButtonGroup();
        JPanel radioPanel = new JPanel();

        for (String s: cards) {
            JRadioButton radioButton = new JRadioButton(s);
            radioButton.setActionCommand(s);
            radioGroup.add(radioButton);
            radioPanel.add(radioButton);
        }

        JPanel cardInfo = new JPanel();
        JLabel cardNumLabel = new JLabel("Card Number");
        cardNumber = new JTextField(16);
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        expMonth = new JComboBox<>(months);
        String[] years = {"2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027"};
        expYear = new JComboBox<>(years);
        securityCode = new JTextField(3);
        cardInfo.add(new JLabel("Expiration Date"));
        cardInfo.add(expMonth);
        cardInfo.add(expYear);
        cardInfo.add(new JLabel("Security Code"));
        cardInfo.add(securityCode);

        JCheckBox receipt = new JCheckBox("Print Receipt");
        receipt.setActionCommand("Print Receipt");
        JCheckBox emailConfirm = new JCheckBox("Email Confirmation");
        emailConfirm.setActionCommand("Email Confirmation");
        checkboxes.add(receipt);
        checkboxes.add(emailConfirm);

        JButton submitPayment = new JButton("Submit Payment");
        submitPayment.setActionCommand("pay");
        submitPayment.addActionListener(this);
        
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameLabel)
                        .addComponent(nameOnCard)
                        .addComponent(addressLabel)
                        .addComponent(streetAddress)
                        .addComponent(zipStatePanel)
                        .addComponent(phoneLabel)
                        .addComponent(phone)
                        .addComponent(emailLabel)
                        .addComponent(emailAddress)
                        .addComponent(radioPanel)
                        .addComponent(cardNumLabel)
                        .addComponent(cardNumber)
                        .addComponent(cardInfo)
                        .addComponent(receipt)
                        .addComponent(emailConfirm)
                        .addComponent(submitPayment))
                   .addComponent(collectedData)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(nameLabel)
                        .addComponent(nameOnCard)
                        .addComponent(addressLabel)
                        .addComponent(streetAddress)
                        .addComponent(zipStatePanel)
                        .addComponent(phoneLabel)
                        .addComponent(phone)
                        .addComponent(emailLabel)
                        .addComponent(emailAddress)
                        .addComponent(radioPanel)
                        .addComponent(cardNumLabel)
                        .addComponent(cardNumber)
                        .addComponent(cardInfo)
                        .addComponent(receipt)
                        .addComponent(emailConfirm)
                        .addComponent(submitPayment))
                    .addComponent(collectedData)
        );
        
        setSize(800,550);

        //tells java what to do when the class object closes
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
    
    public void actionPerformed(ActionEvent e){
       if(e.getActionCommand().equals("pay")) {
           StringBuilder builder = new StringBuilder();
           builder.append("Name: " + nameOnCard.getText() + "\n\n");
           builder.append("Address:\n" + streetAddress.getText() + "\n");
           builder.append(city.getText() + ", " + states.getSelectedItem() + " " + zipCode.getText() + "\n");
           builder.append("Phone Number: " + phone.getText() + "\n");
           builder.append("Email Address: " + emailAddress.getText() + "\n");
           if (radioGroup.getSelection() != null)
            builder.append("\nCard Type: " + radioGroup.getSelection().getActionCommand() + "\n");
           else
               builder.append("\nCard Type: null\n");

           builder.append("Card Number: " + cardNumber.getText() + "\n");
           builder.append("Expiration: " + expMonth.getSelectedItem() + "/" + expYear.getSelectedItem() + "\n");
           builder.append("Security Code: " + securityCode.getText() + "\n\n");

            for (JCheckBox box:checkboxes) {
                builder.append(box.getActionCommand() + ": " + box.isSelected() + "\n");
            }

           collectedData.setText(builder.toString());
       }
    }
    
    public static void main(String args[]){
        new PayForm();
    }
}