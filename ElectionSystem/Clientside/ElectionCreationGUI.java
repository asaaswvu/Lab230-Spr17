import java.awt.Color;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class ElectionCreationGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnlHolder;
	private JButton btnNewButton;
	public JTextField electionName;
	public JTextField commissName;
	public DateTimePicker dateTimePicker_1;
	public DateTimePicker dateTimePicker;

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

		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Election Start Date and Time"));
		panel_1.add(panel_16);
		panel_16.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		dateTimePicker = new DateTimePicker((DatePickerSettings) null, (TimePickerSettings) null);
		panel_16.add(dateTimePicker);

		TimeZone estZone = TimeZone.getTimeZone("EST");
		Calendar est = Calendar.getInstance(estZone);
		Date date = est.getTime();

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(new LineBorder(Color.black, 1), "Election End Date and Time"));

		panel_1.add(panel_5);

		dateTimePicker_1 = new DateTimePicker((DatePickerSettings) null, (TimePickerSettings) null);
		panel_5.add(dateTimePicker_1);

		JPanel panel_4 = new JPanel();
		pnlHolder.add(panel_4);

		btnNewButton = new JButton("Finish");

		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_4.add(btnNewButton);
		add(pnlHolder);
		DatePickerSettings dateSettings = new DatePickerSettings();
		timeSettings = new TimePickerSettings();
		dateSettings.setAllowEmptyDates(false);
		timeSettings.setAllowEmptyTimes(false);

	}

	public void setActnList(ActionListener btnListener) {
		btnNewButton.addActionListener(btnListener);
	}
}
