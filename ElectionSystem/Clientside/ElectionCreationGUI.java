import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import java.time.LocalTime;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;

public class ElectionCreationGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnlHolder;
	private JButton btnNewButton;
	public JTextField electionName;
	public JTextField commissName;

	public ElectionCreationGUI() {
		setBounds(100, 100, 280, 200);
		pnlHolder = new JPanel();
		pnlHolder.setBorder(new EmptyBorder(5, 5, 5, 5));

		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings = new TimePickerSettings();
		timeSettings.initialTime = LocalTime.of(15, 30);
		timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
		timeSettings.setAllowEmptyTimes(false);
		pnlHolder.setLayout(new BoxLayout(pnlHolder, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		pnlHolder.add(panel);
		panel.setBorder(new EmptyBorder(0, 13, 0, 13));
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 12));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel label = new JLabel("Election Name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label);

		electionName = new JTextField();
		electionName.setColumns(10);
		panel_2.add(electionName);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 13, 0, 0));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel label_1 = new JLabel("Commissioner Name");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(label_1);

		commissName = new JTextField();
		commissName.setColumns(10);
		panel_3.add(commissName);

		JPanel panel_1 = new JPanel();
		pnlHolder.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		pnlHolder.add(panel_4);

		btnNewButton = new JButton("Finish");

		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.add(btnNewButton);
		add(pnlHolder);

	}

	public void setActnList(ActionListener btnListener) {
		btnNewButton.addActionListener(btnListener);
	}
}
