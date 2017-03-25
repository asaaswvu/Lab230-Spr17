

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Dimension;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Set;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class consoleGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	public static JList<String> currentUserJList;
	public static JList<String> currentElectionJList;
	public static JPanel currentUserPanel;
	public static JScrollPane currentElectionPanel;
	public consoleGUI(Server s) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{175, 114, 124, 0};
		gridBagLayout.rowHeights = new int[]{14, 140, 122, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(Color.black, 1),
				"Admin Controls"));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.ipadx = 5;
		gbc_panel.ipady = 5;
		gbc_panel.weighty = 1.0;
		gbc_panel.weightx = 1.0;
		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		panel.setLayout(new GridLayout(4, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JButton btnBackup = new JButton("Backup");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("BACKUP");
				for(String electName : s.elections.keySet()){
					System.out.println("TERMINAL @ BACKING UP : " + electName);
					s.backup(electName);
				}
			}
		});
		panel_1.add(btnBackup);
		btnBackup.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JButton btnRestore = new JButton("Restore");
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("RSETORE");
				s.restore();
			}
		});
		panel_1.add(btnRestore);
		btnRestore.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		JButton btnDisconnectAllUsers = new JButton("Disconnect all Users");
		btnDisconnectAllUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Disconnecting Connected Clients");
				try {
					s.forceDisconnectAllClients();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDisconnectAllUsers.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(btnDisconnectAllUsers);

		JButton btnBroadcastMessage = new JButton("Broadcast Message");
		btnBroadcastMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = JOptionPane.showInputDialog("Enter Broadcast message:", "Message").toString();
				System.out.println("Broadcasting to Connected Clients: " + msg);
				for(ClientHandler c : s.currentClients){
					c.sendmsg(msg);
				}

			}
		});
		btnBroadcastMessage.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(btnBroadcastMessage);

		JButton btnShutServerDown = new JButton("Shut Server Down");
		btnShutServerDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Shutting down...");
				s.die();
			}
		});
		btnShutServerDown.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(btnShutServerDown);

		currentElectionPanel = new JScrollPane();
		currentElectionPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 1),
				"Current Elections"));
		GridBagConstraints gbc_currentElectionPanel = new GridBagConstraints();
		gbc_currentElectionPanel.ipadx = 5;
		gbc_currentElectionPanel.ipady = 5;
		gbc_currentElectionPanel.weighty = 1.0;
		gbc_currentElectionPanel.weightx = 1.0;
		gbc_currentElectionPanel.insets = new Insets(0, 3, 0, 3);
		gbc_currentElectionPanel.fill = GridBagConstraints.BOTH;
		gbc_currentElectionPanel.gridx = 1;
		gbc_currentElectionPanel.gridy = 1;
		add(currentElectionPanel, gbc_currentElectionPanel);
		//currentElectionPanel.setLayout(new GridLayout(1, 0, 0, 0));

		currentElectionJList = new JList<String>();
		currentElectionJList.setVisibleRowCount(5);
		currentElectionPanel.setViewportView(currentElectionJList);

		currentUserPanel = new JPanel();
		currentUserPanel.setBorder(new TitledBorder(new LineBorder(Color.black, 1),
				"Current Users"));
		GridBagConstraints gbc_currentUserPanel = new GridBagConstraints();
		gbc_currentUserPanel.ipady = 5;
		gbc_currentUserPanel.ipadx = 5;
		gbc_currentUserPanel.weighty = 1.0;
		gbc_currentUserPanel.weightx = 1.0;
		gbc_currentUserPanel.anchor = GridBagConstraints.NORTHEAST;
		gbc_currentUserPanel.insets = new Insets(0, 3, 0, 3);
		gbc_currentUserPanel.fill = GridBagConstraints.BOTH;
		gbc_currentUserPanel.gridx = 2;
		gbc_currentUserPanel.gridy = 1;
		add(currentUserPanel, gbc_currentUserPanel);
		currentUserPanel.setLayout(new GridLayout(1, 0, 0, 0));

		currentUserJList = new JList<String>();
		currentUserPanel.add(currentUserJList);

		setPreferredSize(new Dimension(450, 325));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(new LineBorder(Color.black, 1),
				"Server Logs"));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.ipady = 5;
		gbc_scrollPane.ipadx = 5;
		gbc_scrollPane.weighty = 1.0;
		gbc_scrollPane.weightx = 1.0;
		gbc_scrollPane.anchor = GridBagConstraints.SOUTH;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		add(scrollPane, gbc_scrollPane);
	}

	public static void updateOnlineUsers(Vector<String> users){
		DefaultListModel<String> model = new DefaultListModel<String>();
		for(String user : users){
			model.addElement(user);
		}
		currentUserJList.setModel(model);
		currentUserPanel.repaint();
		currentUserPanel.revalidate();
	}

	public static void updateCurrentElections(Set<String> elections){
		DefaultListModel<String> lstmodel = new DefaultListModel<String>();
		for(String elect : elections){
			lstmodel.addElement(elect);
		}
		currentElectionJList.setModel(lstmodel);
		currentElectionPanel.repaint();
		currentElectionPanel.revalidate();
	}
}
