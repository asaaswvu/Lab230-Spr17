//Christopher Bond
//github account:TheWozard
//email:cdbond@mix.wvu.edu
//Lab 3
//Last Modified:Jan 24th 2017

//Additions
//Email is only modifiable when selected check box
//Image added to load inplace of text for Card type

import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

class PayForm extends JFrame implements ActionListener {

  JTextField txtName;
  JTextField txtCardNumber;
  JTextField txtEmail;
  JTextArea txtOutput;

  ButtonGroup btnCardType;
  ArrayList<JCheckBox> btnModifiers;
  JCheckBox chkEmail;

  PayForm (){

        //Inital Panels
        JPanel panelMain = new JPanel();
        GroupLayout layout = new GroupLayout(panelMain);
        JPanel pnlButtons = new JPanel();
        JPanel pnlRadios = new JPanel();

        //Output Area
        txtOutput = new JTextArea(15,50);
        txtOutput.setEditable(false);

        //Name Field
        txtName = new JTextField(20);
        JLabel nameLabel = new JLabel("Name");

        //Card Number Field
        txtCardNumber = new JTextField(50);
        JLabel cardNumberLabel = new JLabel("Card");

        //Email Field
        txtEmail = new JTextField(10);
        JLabel emailLabel = new JLabel("E-mail");
        txtEmail.setEditable(false);

        //Radio Card Selection
        JRadioButton radAmex = new JRadioButton("AmericanExpress");
        JRadioButton radVisa = new JRadioButton("Visa");
        JRadioButton radMast = new JRadioButton("MasterCard");
        JRadioButton radDisc = new JRadioButton("Discover");

        radAmex.setActionCommand("AmericanExpress");
        radVisa.setActionCommand("Visa");
        radMast.setActionCommand("MasterCard");
        radDisc.setActionCommand("Discover");

        radAmex.setSelected(true);

        //Grouping Radio
        btnCardType = new ButtonGroup();
        btnCardType.add(radAmex);
        btnCardType.add(radVisa);
        btnCardType.add(radMast);
        btnCardType.add(radDisc);

        //Radio Label
        JLabel cardTypeLabel;
        try {
          cardTypeLabel = new JLabel(new ImageIcon(ImageIO.read(new File("creditcards.jpg"))));
        } catch (IOException ex) {
          cardTypeLabel = new JLabel("Card Type");
          cardTypeLabel.setFont(new Font(cardTypeLabel.getFont().getName(), Font.PLAIN, 18));
        }

        //Setting Radio Panel
        pnlRadios.add(cardTypeLabel);
        pnlRadios.add(radAmex);
        pnlRadios.add(radVisa);
        pnlRadios.add(radMast);
        pnlRadios.add(radDisc);

        //Check Boxes
        JCheckBox chkPrint = new JCheckBox("Print");
        chkEmail = new JCheckBox("Email Conformation");

        chkPrint.setActionCommand("print");
        chkEmail.setActionCommand("email");
        chkEmail.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent changeEvent) {
              txtEmail.setEditable(chkEmail.isSelected());
          }
        });

        //Grouping Boxes
        btnModifiers = new ArrayList<JCheckBox>();
        btnModifiers.add(chkPrint);
        btnModifiers.add(chkEmail);

        //Setting Button Panel
        pnlButtons.add(chkPrint);
        pnlButtons.add(chkEmail);

        //Submit Button
        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setActionCommand("submit");
        btnSubmit.addActionListener(this);

        //Layout Formation
        panelMain.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
           layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(nameLabel)
                    .addComponent(txtName)
                    .addComponent(cardNumberLabel)
                    .addComponent(txtCardNumber)
                    .addComponent(pnlRadios)
                    .addComponent(pnlButtons)
                    .addComponent(emailLabel)
                    .addComponent(txtEmail)
                    .addComponent(btnSubmit))
                .addComponent(txtOutput)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                  .addComponent(nameLabel)
                  .addComponent(txtName)
                  .addComponent(cardNumberLabel)
                  .addComponent(txtCardNumber)
                  .addComponent(pnlRadios)
                  .addComponent(pnlButtons)
                  .addComponent(emailLabel)
                  .addComponent(txtEmail)
                  .addComponent(btnSubmit))
              .addComponent(txtOutput)
        );

        setSize(700,400);

        //tells java what to do when the class object closes
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PayForm");


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
         if(e.getActionCommand().equals("submit")){
           StringBuffer strSubm = new StringBuffer();
           strSubm.append(txtName.getText() + "\n");
           strSubm.append(txtCardNumber.getText() + "\n");
           strSubm.append(btnCardType.getSelection().getActionCommand() + "\n");

           Iterator<JCheckBox> iterCheckButtons = btnModifiers.iterator();
           while(iterCheckButtons.hasNext()){
               JCheckBox nextBox = iterCheckButtons.next();
               if(nextBox.isSelected()){
                   strSubm.append(nextBox.getActionCommand() + " ");

               }

           }
           if(chkEmail.isSelected())strSubm.append(txtEmail.getText() + "\n");
           txtOutput.setText(strSubm.toString());
         }
  }

  //Start Location
  public static void main(String args[]){
      new PayForm();
  }

}
