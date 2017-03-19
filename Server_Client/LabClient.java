import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;

class LabClient extends JFrame implements ActionListener{

    BufferedReader brIn;
    PrintWriter pwOut;
    Socket sock;
    JTextField txtName;
    JLabel lblSent;

    LabClient(){
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BorderLayout());
        JPanel pnlName = new JPanel();
        pnlName.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1,2));


        txtName = new JTextField(30);
        JLabel lblName = new JLabel("Send Values");
        lblSent = new JLabel("");
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,30);
        gbc.gridx = 0;
        gbc.gridy = 0;
        pnlName.add(lblName,gbc);
        gbc.insets = new Insets(2,5,5,2);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        pnlName.add(txtName,gbc);
        gbc.insets = new Insets(8,5,5,2);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlName.add(lblSent,gbc);

        JButton btnSend = new JButton("Send");
        btnSend.setActionCommand("send");
        btnSend.addActionListener(this);

        JButton btnQuit = new JButton("Quit");
        btnQuit.setActionCommand("quit");
        btnQuit.addActionListener(this);

        pnlButtons.add(btnSend);
        pnlButtons.add(btnQuit);

        pnlMain.add(pnlName, BorderLayout.NORTH);
        pnlMain.add(pnlButtons, BorderLayout.SOUTH);

        getContentPane().add(pnlMain);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Server Client Lab");
        setSize(350,165);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);

        setVisible(true);
        run();


    }
    private void run(){
        try{
            sock = new Socket("127.0.0.1",50000);
            brIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            pwOut = new PrintWriter(sock.getOutputStream(),true);

            while (true){
                String strIn = brIn.readLine();
                if (strIn.startsWith("<val>")){
                  System.out.println("Client Received: " + strIn.substring(5));
                    //JOptionPane.showMessageDialog(this,"text sent","Successful",JOptionPane.PLAIN_MESSAGE);
                    String current = lblSent.getText();
                    String prior = (current.equals("")) ? "" : (current + "+");
                    lblSent.setText(prior+strIn.substring(5));
                }
                else if (strIn.startsWith("<EOL>")){
                    JOptionPane.showMessageDialog(this,"The send process has been completed!","Send Function",JOptionPane.PLAIN_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(this,strIn,"Error Encountered",JOptionPane.PLAIN_MESSAGE);
            }
        }catch(IOException e){
            System.out.println("IOException");
        }catch(NullPointerException npe){
            System.out.println("null");
        }

    }
    public void actionPerformed(ActionEvent e){
        if (!sock.isClosed()){
            switch (e.getActionCommand()){
                case "send":
                    System.out.println("<send>," + txtName.getText());
                    pwOut.println("<send>," + txtName.getText()+",<EOL>");
                break;
                case "quit":
                    pwOut.println("<die>");
                    System.exit(0);
                break;
            }
        }else{
            JOptionPane.showMessageDialog(this,"Socket is Closed","Error",JOptionPane.ERROR_MESSAGE);
        }


    }

    public static void main(String args[]){
        new LabClient();
    }
}
