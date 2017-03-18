import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.net.*;
import java.io.*;

class LabClient extends JFrame implements ActionListener{

    BufferedReader brIn;
    PrintWriter pwOut;
    Socket sock;
    JTextField txtName;
    JLabel lblSent;

    LabClient(){
        JPanel pnlMain = new JPanel(new SpringLayout());

        JPanel pnlName = new JPanel();
        JPanel pnlButtons = new JPanel();

        txtName = new JTextField(15);
        JLabel lblName = new JLabel("Name");
	      JLabel lblValue = new JLabel("");
        lblSent = new JLabel("");
        pnlName.add(lblName);
        pnlName.add(txtName);
	      pnlName.add(lblValue);
        pnlName.add(lblSent);

        JButton btnSend = new JButton("Send");
        btnSend.setActionCommand("send");
        btnSend.addActionListener(this);

        JButton btnQuit = new JButton("Quit");
        btnQuit.setActionCommand("quit");
        btnQuit.addActionListener(this);

        pnlButtons.add(btnSend);
        pnlButtons.add(btnQuit);

        pnlMain.add(pnlName);
        pnlMain.add(pnlButtons);

        SpringUtilities.makeCompactGrid(pnlMain,3, 2, 6, 6,6, 6);
        getContentPane().add(pnlMain);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Login Client");
        setSize(300,200);

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
                if (strIn.startsWith("<send_received>")){
                    JOptionPane.showMessageDialog(this,"text sent","Successful",JOptionPane.PLAIN_MESSAGE);
                    lblSent.setText(lblSent.getText()+strIn);
                }
                else
                    JOptionPane.showMessageDialog(this,strIn,"???",JOptionPane.PLAIN_MESSAGE);
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
                    pwOut.println("<send>," + txtName.getText());
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
