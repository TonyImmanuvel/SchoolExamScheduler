package yoogi.game.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import yoogi.game.fileutils.FileHelper;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

public class DialogClassDetails extends JDialog {

	private static final long serialVersionUID = 1L;
	private int nStudentsCount= 0;
	private JTextField txtfClassName;
	private JTextField txtfNoOfStudents;
	
	ExAllocFrame frameParent;
	
	private JList jListClassDetails;
	private JScrollPane scrollPane;

	public DefaultListModel<String> listClassDetailsModel = new DefaultListModel<String>();
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			DialogClassDetails dialog = new DialogClassDetails();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public DialogClassDetails(ExAllocFrame theParentFrame) {
		frameParent = theParentFrame;
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setBounds(100, 100, 656, 515);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 443, 640, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						frameParent.applyClassDetailsUpdate();
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
		
		txtfClassName = new JTextField();
		txtfClassName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtfClassName.setBounds(472, 167, 86, 20);
		getContentPane().add(txtfClassName);
		txtfClassName.setColumns(10);
		
		txtfNoOfStudents = new JTextField();
		txtfNoOfStudents.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtfNoOfStudents.setColumns(10);
		txtfNoOfStudents.setBounds(472, 198, 86, 20);
		getContentPane().add(txtfNoOfStudents);
		
		JLabel lblNewLabel = new JLabel("Class Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(360, 167, 102, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("No Of Students");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(360, 198, 102, 14);
		getContentPane().add(lblNewLabel_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addClassDetails();
			}
		});
		btnAdd.setBounds(360, 251, 89, 23);
		getContentPane().add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateClassDetails();
			}
		});
		btnUpdate.setBounds(472, 251, 89, 23);
		getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCurrentClass();
			}
		});
		btnDelete.setBounds(360, 300, 89, 23);
		getContentPane().add(btnDelete);
		
		
		
		JLabel lblNewLabel_2 = new JLabel("Class Details");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(28, 85, 126, 14);
		getContentPane().add(lblNewLabel_2);
		
		
		
		jListClassDetails = new JList();
		jListClassDetails.setModel(listClassDetailsModel);
		jListClassDetails.setBounds(0, 0, 100, 200);
		
		scrollPane = new JScrollPane(jListClassDetails);
		
		//getContentPane().add(jListClassDetails);
		getContentPane().add(scrollPane);
		scrollPane.setBounds(50,150,150, 260);
		
		JButton btnNewButton = new JButton("Load Saved Class Details");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            int option = fileChooser.showOpenDialog(frameParent);
	            if(option == JFileChooser.APPROVE_OPTION){
	               File file = fileChooser.getSelectedFile();
	               String strAll = FileHelper.readFullStringText(file.getAbsolutePath());
	               // Set the strAll to model
	               stringToModel(strAll );
	               ExAllocFrame.sortAlpabetic(listClassDetailsModel);
	               //label.setText("File Selected: " + file.getName());
	            }else{
	               System.out.println("Open command canceled");
	            }
			}
		});
		btnNewButton.setBounds(126, 33, 185, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnSaveThisDetails = new JButton("Save This details");
		btnSaveThisDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            int option = fileChooser.showSaveDialog(frameParent);
	            if(option == JFileChooser.APPROVE_OPTION){
	               File file = fileChooser.getSelectedFile();
	               System.out.println("Save File as : " + file.getName());
	               String strAll = ExAllocFrame.modelToString(listClassDetailsModel);
	               FileHelper.writeFullStringText(file.getAbsolutePath(), strAll);
	            }else{
	            	System.out.println("Save command canceled");
	            }
			}
		});
		btnSaveThisDetails.setBounds(351, 33, 185, 23);
		getContentPane().add(btnSaveThisDetails);
	}
	
	public boolean assert_Number_entered() {
		try {
			nStudentsCount = Integer.parseInt(txtfNoOfStudents.getText());
			return true;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter Number of students in number format,,,,");
			return false;
		}
	}
	
	public void addClassDetails() {
		assert_Number_entered();
		String str = txtfClassName.getText();
		String strData = str+"="+nStudentsCount;
		listClassDetailsModel.addElement(str+"="+nStudentsCount);
		ExAllocFrame.sortAlpabetic(listClassDetailsModel);
	};
	
	public void updateClassDetails() {
		assert_Number_entered();
		int selectionIndex = jListClassDetails.getSelectedIndex();
		if((listClassDetailsModel.size() > selectionIndex)) {
			String str = txtfClassName.getText();
			String strData = str+"="+nStudentsCount;
			listClassDetailsModel.set(selectionIndex,strData);			
		}
		ExAllocFrame.sortAlpabetic(listClassDetailsModel);
	};
	
	
	public void deleteCurrentClass() {
		int selectionIndex = jListClassDetails.getSelectedIndex();
		if((listClassDetailsModel.size() > selectionIndex)) {
			listClassDetailsModel.remove(selectionIndex);
		}
	}
	
	private void stringToModel(String strAll) {
		// TODO Auto-generated method stub
		String strArr[] = strAll.split("\n");
		listClassDetailsModel.clear();
		for(int i=0;i<strArr.length;i++) {
			if(strArr[i].length() > 2) {
				listClassDetailsModel.addElement(strArr[i]);
			}
		}
	}
}
