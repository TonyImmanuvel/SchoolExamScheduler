package yoogi.game.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class DlgAddExam extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtExamName;
	private JList jlistClasses;
	private JScrollPane scrollPane;
	private ExAllocFrame frameParent;
	JLabel lblNoOfStudents; 
	public DefaultListModel<String> listClassDetailsModel = new DefaultListModel<String>();
	
	public String strAllSelectedClasses;
	public String strExamName;
	public int numberOfStudentsInEachBench;
	private JTextField txtStudentsPerBench;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			DlgAddExam dialog = new DlgAddExam();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public DlgAddExam(ExAllocFrame theParentFrame) {
		frameParent = theParentFrame;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtExamName = new JTextField();
		txtExamName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtExamName.setBounds(188, 11, 86, 20);
		contentPanel.add(txtExamName);
		txtExamName.setColumns(10);
		
		
		JLabel lblNewLabel = new JLabel("Exam Name");
		lblNewLabel.setLabelFor(txtExamName);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(97, 14, 81, 14);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("<html>Select Classes <br> (1 or more)</html>l>");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(47, 90, 120, 70);
		contentPanel.add(lblNewLabel_1);
		
		lblNoOfStudents = new JLabel("(0) Students ");
		lblNoOfStudents.setBounds(125, 203, 130, 14);
		contentPanel.add(lblNoOfStudents);
		
		JLabel lblStudentsPerBech = new JLabel("<html>Students <br>Per Bench</html>");
		lblStudentsPerBech.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStudentsPerBech.setBounds(97, 36, 81, 40);
		contentPanel.add(lblStudentsPerBech);
		
		txtStudentsPerBench = new JTextField();
		txtStudentsPerBench.setText("1");
		txtStudentsPerBench.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtStudentsPerBench.setColumns(10);
		txtStudentsPerBench.setBounds(188, 39, 24, 20);
		contentPanel.add(txtStudentsPerBench);
		jlistClasses = new JList();
		//contentPanel.add(jlistClasses);
		jlistClasses.setModel(listClassDetailsModel);
		
		
		jlistClasses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				 //ListSelectionModel lsm = (ListSelectionModel)e.getSource();
				int totSelStudents = calcNoOfStudents();
				lblNoOfStudents.setText("("+ totSelStudents+") Students" );
				
			}
		});
		
		jlistClasses.setFont(new Font("Tahoma", Font.PLAIN, 12));
		jlistClasses.setBounds(188, 81, 118, 98);
		
		scrollPane = new JScrollPane(jlistClasses);
		scrollPane.setBounds(188, 79, 120, 100);
		//contentPanel.add(jlistClasses);
		contentPanel.add(scrollPane);
		
		lblNewLabel_1.setLabelFor(jlistClasses);
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Add Exam");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						processApplyExamDetailsUpdate();						
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	
		protected int calcNoOfStudents() {
		// TODO Auto-generated method stub
			int totSelStudents = 0;
			String strClassesSelected = "";
			for(int i=0;i<listClassDetailsModel.size();i++) {
				
				if(jlistClasses.isSelectedIndex(i)) {
					String str = listClassDetailsModel.get(i);
					String str2[] = str.split("=");
					int classCount = Integer.parseInt(str2[1]);
					totSelStudents+= classCount;
					System.out.println("Selected Class is"+str );
					strClassesSelected+= str2[0]+",";
					
				}
			}
			if(totSelStudents > 0)
				strAllSelectedClasses = strClassesSelected.substring(0,strClassesSelected.length()-1);
			return totSelStudents;
	}

		protected boolean  processApplyExamDetailsUpdate() {
		// TODO Auto-generated method stub
			assert_Number_entered();
			String exName = txtExamName.getText();
			if(exName.length() > 2) {
				strExamName = exName;
			}else {
				JOptionPane.showMessageDialog(null, "Please enter Exam Name (atleast two chars");
				return false;
			}
			// make sure atleast one class is selected..
			int totSelStudents = calcNoOfStudents();
			if(totSelStudents <= 0) {
				JOptionPane.showMessageDialog(null, "Please Select one or more classes...");
				return false;
			}
			frameParent.applyExamDetailsUpdate();
			return true;
		
		}

		public boolean assert_Number_entered() {
			try {
				numberOfStudentsInEachBench = Integer.parseInt(txtStudentsPerBench.getText());
				return true;
			}catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Please enter Hall Capacity in number format,,,1 - 999");
			
			}			
			return false;
		}
}
