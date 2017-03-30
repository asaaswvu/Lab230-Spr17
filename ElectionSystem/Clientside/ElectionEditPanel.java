import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

public class ElectionEditPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public DateTimePicker dateTimePicker;
	public DateTimePicker dateTimePicker_1;
	private JTextField textField;

	
	public ElectionEditPanel(JList<String> lstRaces, JList<String> lstCandidates) {
		setBorder(new EmptyBorder(10,10,10,10));
		setLayout(null);

		JLabel lblBallotEdit = new JLabel("Create Ballot");
		lblBallotEdit.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblBallotEdit.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotEdit.setBounds(10, 11, 480, 14);
		add(lblBallotEdit);
		
		textField = new JTextField();
		textField.setBounds(259, 173, 231, 23);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblElectionPassword = new JLabel("Election Password");
		lblElectionPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblElectionPassword.setBounds(259, 147, 231, 30);
		add(lblElectionPassword);
		
		JPanel datesAndOptions = new JPanel();
		datesAndOptions.setBounds(259, 25, 241, 152);
		add(datesAndOptions);
		datesAndOptions.setLayout(new GridLayout(5, 1, 0, 0));
		
		JLabel lblBallotRaces = new JLabel("Races");
		lblBallotRaces.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotRaces.setBounds(10, 36, 99, 14);
		add(lblBallotRaces);
		
		lstRaces.setBounds(10, 61, 99, 135);
		add(lstRaces);
		
		JButton btnAddRace = new JButton("Add  Race");
		btnAddRace.setBounds(10, 207, 99, 23);
		add(btnAddRace);
		btnAddRace.setActionCommand("addRace");
		btnAddRace.addActionListener(Client.parentClient);

		JButton btnRemoveRace = new JButton("Remove Race");
		btnRemoveRace.setBounds(10, 241, 99, 23);
		add(btnRemoveRace);
		btnRemoveRace.setActionCommand("removeRace");
		btnRemoveRace.addActionListener(Client.parentClient);
		
		lstCandidates.setBounds(127, 61, 122, 135);
		add(lstCandidates);
		
		JLabel lblBallotCadidates = new JLabel("Cadidates");
		lblBallotCadidates.setHorizontalAlignment(SwingConstants.CENTER);
		lblBallotCadidates.setBounds(136, 36, 99, 14);
		add(lblBallotCadidates);
		
		JButton btnAddCandidate = new JButton("Add Candidate");
		btnAddCandidate.setBounds(127, 207, 129, 23);
		add(btnAddCandidate);
		btnAddCandidate.setActionCommand("addCand");
		btnAddCandidate.addActionListener(Client.parentClient);


		JButton btnRemoveCandidate = new JButton("Remove Candidate");
		btnRemoveCandidate.setBounds(127, 241, 129, 23);
		add(btnRemoveCandidate);
		btnRemoveCandidate.setActionCommand("removeCand");
		btnRemoveCandidate.addActionListener(Client.parentClient);

		
		JLabel lblStartDate = new JLabel("Election Start Date and Time");
		lblStartDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartDate.setVerticalAlignment(SwingConstants.BOTTOM);
		datesAndOptions.add(lblStartDate);

		JPanel pnlStartDate = new JPanel();
		pnlStartDate.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		dateTimePicker = new DateTimePicker((DatePickerSettings) null, (TimePickerSettings) null);
		pnlStartDate.add(dateTimePicker);
		datesAndOptions.add(pnlStartDate);
		
		JLabel lblEndDate = new JLabel("Election End Date and Time");
		lblEndDate.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndDate.setVerticalAlignment(SwingConstants.BOTTOM);
		datesAndOptions.add(lblEndDate);

		JPanel pnlEndDate = new JPanel();
		dateTimePicker_1 = new DateTimePicker((DatePickerSettings) null, (TimePickerSettings) null);
		pnlEndDate.add(dateTimePicker_1);
		datesAndOptions.add(pnlEndDate);
		
		JLabel label = new JLabel("");
		datesAndOptions.add(label);
		
		JButton btnExitEdit = new JButton("Cancel");
		btnExitEdit.setBounds(10, 316, 86, 23);
		add(btnExitEdit);
		btnExitEdit.setActionCommand("exitBallotEdit");
		btnExitEdit.addActionListener(Client.parentClient);
		
		JButton btnFinalize = new JButton("Finalize");
		btnFinalize.setBounds(404, 316, 86, 23);
		add(btnFinalize);
		btnFinalize.setActionCommand("finalizeBallot");
		btnFinalize.addActionListener(Client.parentClient);
		
		lstRaces.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lE) {
				if (!lE.getValueIsAdjusting()) {
					Client.selectedRace = lstRaces.getSelectedValue();
					if(Client.selectedRace != null){
						Client.raceSelected();
						btnRemoveRace.setEnabled(true);
						btnAddCandidate.setEnabled(true);
						btnRemoveCandidate.setEnabled(true);
					}
					else{
						btnRemoveRace.setEnabled(false);
						btnAddCandidate.setEnabled(false);
						btnRemoveCandidate.setEnabled(false);
					}
				}
			}
		});
		
	}
}
