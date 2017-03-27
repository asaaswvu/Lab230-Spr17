import java.awt.Dimension;
import javax.swing.JFrame;

public class consoleGUIFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public static consoleGUI gui;
	public consoleGUIFrame(Server s) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui = new consoleGUI(s);
		add(gui);
		pack();
		setMinimumSize(new Dimension(450, 325));
		setTitle("Server Console");
		setLocationRelativeTo(null);
		setVisible(true);
	}
}