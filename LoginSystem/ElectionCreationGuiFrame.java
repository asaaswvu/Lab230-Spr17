import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ElectionCreationGuiFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public ElectionCreationGuiFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		add(new ElectionCreationGUI());
		setMinimumSize(new Dimension(475, 275));
		pack();
		setTitle("Election Creation");
		setLocationRelativeTo(null);
		setVisible(true);
	}
}