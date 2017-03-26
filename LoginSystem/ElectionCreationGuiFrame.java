import java.awt.Dimension;
import javax.swing.JFrame;

public class ElectionCreationGuiFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	public ElectionCreationGuiFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new ElectionCreationGUI());
		setMinimumSize(new Dimension(475, 275));
		pack();
		setTitle("Election Creation");
		setLocationRelativeTo(null);
		setVisible(true);
	}
}