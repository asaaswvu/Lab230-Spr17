import java.awt.BorderLayout;
import java.awt.Color;
import org.jdesktop.swingx.JXDatePicker;
import com.github.lgooddatepicker.components.TimePicker;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class ElectionCreationGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private JPanel pnlHolder;
	static final int rowMultiplier = 4;
	private JTextField textField;
	private JTextField textField_1;

	public ElectionCreationGUI() {
		setBounds(100, 100, 448, 240);
		pnlHolder = new JPanel();
		pnlHolder.setBorder(new EmptyBorder(5, 5, 5, 5));

		TimePickerSettings timeSettings = new TimePickerSettings();
		timeSettings = new TimePickerSettings();
		timeSettings.initialTime = LocalTime.of(15, 30);
		timeSettings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, null, null);
		timeSettings.setAllowEmptyTimes(false);

		pnlHolder.setLayout(new BorderLayout(0, 0));


		JPanel panel_1 = new JPanel();
		pnlHolder.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_16 = new JPanel();
		panel_1.add(panel_16);

		JPanel panel_11 = new JPanel();
		panel_16.add(panel_11);
		panel_11.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[]{189, 0};
		gbl_panel_11.rowHeights = new int[]{20, 20, 0};
		gbl_panel_11.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_11.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_11.setLayout(gbl_panel_11);

		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(new LineBorder(Color.black, 1),"Start Date"));
		GridBagConstraints gbc_panel_12 = new GridBagConstraints();
		gbc_panel_12.weighty = 1.0;
		gbc_panel_12.weightx = 1.0;
		gbc_panel_12.fill = GridBagConstraints.BOTH;
		gbc_panel_12.insets = new Insets(0, 0, 5, 0);
		gbc_panel_12.gridx = 0;
		gbc_panel_12.gridy = 0;
		panel_11.add(panel_12, gbc_panel_12);
		GridBagLayout gbl_panel_12 = new GridBagLayout();
		gbl_panel_12.columnWidths = new int[]{189, 0};
		gbl_panel_12.rowHeights = new int[]{20, 0};
		gbl_panel_12.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_12.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_12.setLayout(gbl_panel_12);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new EmptyBorder(0, 2, 4, 0));
		GridBagConstraints gbc_panel_13 = new GridBagConstraints();
		gbc_panel_13.fill = GridBagConstraints.BOTH;
		gbc_panel_13.gridx = 0;
		gbc_panel_13.gridy = 0;
		panel_12.add(panel_13, gbc_panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
	    TimeZone estZone = TimeZone.getTimeZone("EST");
	    Calendar est = Calendar.getInstance(estZone);
		Date date = est.getTime();
		JXDatePicker datePicker_1 = new JXDatePicker(date);
		datePicker_1.setFormats("yyyy-MM-dd");
		panel_13.add(datePicker_1, BorderLayout.CENTER);

		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(new LineBorder(Color.black, 1),"Start Time"));
		GridBagConstraints gbc_panel_14 = new GridBagConstraints();
		gbc_panel_14.weighty = 1.0;
		gbc_panel_14.weightx = 1.0;
		gbc_panel_14.fill = GridBagConstraints.BOTH;
		gbc_panel_14.gridx = 0;
		gbc_panel_14.gridy = 1;
		panel_11.add(panel_14, gbc_panel_14);
		GridBagLayout gbl_panel_14 = new GridBagLayout();
		gbl_panel_14.columnWidths = new int[]{189, 0};
		gbl_panel_14.rowHeights = new int[]{20, 0, 0};
		gbl_panel_14.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_14.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_14.setLayout(gbl_panel_14);

		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new EmptyBorder(0, 2, 2, 0));
		GridBagConstraints gbc_panel_15 = new GridBagConstraints();
		gbc_panel_15.weighty = 1.0;
		gbc_panel_15.weightx = 1.0;
		gbc_panel_15.fill = GridBagConstraints.BOTH;
		gbc_panel_15.insets = new Insets(0, 0, 5, 0);
		gbc_panel_15.gridx = 0;
		gbc_panel_15.gridy = 0;
		panel_14.add(panel_15, gbc_panel_15);
		panel_15.setLayout(new GridLayout(0, 1, 0, 0));

		TimePicker timePicker_1 = new TimePicker((TimePickerSettings) null);
		panel_15.add(timePicker_1);

		JPanel panel_5 = new JPanel();
		panel_1.add(panel_5);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_5.add(panel_6);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{189, 0};
		gbl_panel_6.rowHeights = new int[]{20, 20, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);

		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(new LineBorder(Color.black, 1),"End Date"));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.weighty = 1.0;
		gbc_panel_8.weightx = 1.0;
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 0;
		panel_6.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[]{189, 0};
		gbl_panel_8.rowHeights = new int[]{20, 0};
		gbl_panel_8.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel_8.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_8.setLayout(gbl_panel_8);

		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new EmptyBorder(0, 2, 4, 0));
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 0;
		panel_8.add(panel_9, gbc_panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JXDatePicker datePicker = new JXDatePicker(date);
		datePicker.setFormats("yyyy-MM-dd");
		panel_9.add(datePicker, BorderLayout.NORTH);

		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(new LineBorder(Color.black, 1),"End Time"));
		GridBagConstraints gbc_panel_10 = new GridBagConstraints();
		gbc_panel_10.weighty = 1.0;
		gbc_panel_10.weightx = 1.0;
		gbc_panel_10.fill = GridBagConstraints.BOTH;
		gbc_panel_10.gridx = 0;
		gbc_panel_10.gridy = 1;
		panel_6.add(panel_10, gbc_panel_10);
		GridBagLayout gbl_panel_10 = new GridBagLayout();
		gbl_panel_10.columnWidths = new int[]{189, 0};
		gbl_panel_10.rowHeights = new int[]{20, 0, 0};
		gbl_panel_10.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_10.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_10.setLayout(gbl_panel_10);

		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new EmptyBorder(0, 2, 2, 0));
		GridBagConstraints gbc_panel_17 = new GridBagConstraints();
		gbc_panel_17.weighty = 1.0;
		gbc_panel_17.weightx = 1.0;
		gbc_panel_17.fill = GridBagConstraints.BOTH;
		gbc_panel_17.insets = new Insets(0, 0, 5, 0);
		gbc_panel_17.gridx = 0;
		gbc_panel_17.gridy = 0;
		panel_10.add(panel_17, gbc_panel_17);
		panel_17.setLayout(new GridLayout(0, 1, 0, 0));

		TimePicker timePicker = new TimePicker((TimePickerSettings) null);
		panel_17.add(timePicker);

		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 13, 0, 13));
		pnlHolder.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 0, 12));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel label = new JLabel("Election Name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label);

		textField = new JTextField();
		textField.setColumns(10);
		panel_2.add(textField);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(0, 13, 0, 0));
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel label_1 = new JLabel("Commissioner Name");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(label_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		panel_3.add(textField_1);

		JPanel panel_4 = new JPanel();
		pnlHolder.add(panel_4, BorderLayout.SOUTH);

		JButton btnNewButton = new JButton("Finish");
		panel_4.add(btnNewButton);
		add(pnlHolder);

	}
}
