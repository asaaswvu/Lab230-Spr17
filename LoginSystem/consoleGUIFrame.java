

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class consoleGUIFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public consoleGUIFrame(Server s) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 425, 300);
		add(new consoleGUI(s));
		pack();
		setMinimumSize(new Dimension(450, 325));
		setTitle("Server Console");
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
