package yoogi.game.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import yoogi.game.fileutils.FileHelper;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Dialog.ModalityType;

public class DialogHallDetails extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtHallName;
	private JTextField txtHallCapacity;
	private JTextField txtBenchCapacity;
	private JList listHallDetails;
	private JScrollPane scrollPane;
	public DefaultListModel<String> listHallDetailsModel = new DefaultListModel<String>();
	private int nHallCapacity;
	private int nBenchCapacity;
	public ExAllocFrame frameParent;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		try {
			DialogHallDetails dialog = new DialogHallDetails();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/

	/**
	 * Create the dialog.
	 */
	public DialogHallDetails(ExAllocFrame theParentFrame) {
		frameParent = theParentFrame;
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setBounds(100, 100, 596, 484);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			listHallDetails = new JList();
			listHallDetails.setModel(listHallDetailsModel);
			listHallDetails.setBounds(0, 0, 140, 140);
			//contentPanel.add(listHallDetails);
			
			scrollPane = new JScrollPane(listHallDetails);
			scrollPane.setBounds(30,100,160,160);
			contentPanel.add(scrollPane);
		}
		{
			JLabel lblHallCaption = new JLabel("Hall Details");
			lblHallCaption.setBounds(36, 105, 138, 14);
			contentPanel.add(lblHallCaption);
		}
		{
			txtHallName = new JTextField();
			txtHallName.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtHallName.setBounds(371, 147, 86, 20);
			contentPanel.add(txtHallName);
			txtHallName.setColumns(10);
		}
		{
			txtHallCapacity = new JTextField();
			txtHallCapacity.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtHallCapacity.setBounds(371, 178, 86, 20);
			contentPanel.add(txtHallCapacity);
			txtHallCapacity.setColumns(10);
		}
		{
			txtBenchCapacity = new JTextField();
			txtBenchCapacity.setFont(new Font("Tahoma", Font.PLAIN, 12));
			txtBenchCapacity.setBounds(371, 209, 86, 20);
			contentPanel.add(txtBenchCapacity);
			txtBenchCapacity.setColumns(10);
		}
		
		JLabel lblHallName = new JLabel("Hall Name");
		lblHallName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHallName.setBounds(240, 147, 78, 14);
		contentPanel.add(lblHallName);
		
		JLabel lblHallCapacity = new JLabel("Capacity");
		lblHallCapacity.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblHallCapacity.setBounds(240, 178, 78, 14);
		contentPanel.add(lblHallCapacity);
		
		JLabel lblBenchSize = new JLabel("Bench Capacity");
		lblBenchSize.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBenchSize.setBounds(241, 209, 97, 14);
		contentPanel.add(lblBenchSize);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addHallDetails();
			}
		});
		btnAdd.setBounds(249, 264, 89, 23);
		contentPanel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateHallDetails();
			}
		});
		btnUpdate.setBounds(393, 264, 89, 23);
		contentPanel.add(btnUpdate);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCurrentHall();
			}
		});
		btnRemove.setBounds(249, 318, 89, 23);
		contentPanel.add(btnRemove);
		{
			JButton btnNewButton = new JButton("Load Saved Details");
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
		            int option = fileChooser.showOpenDialog(frameParent);
		            if(option == JFileChooser.APPROVE_OPTION){
		               File file = fileChooser.getSelectedFile();
		               String strAll = FileHelper.readFullStringText(file.getAbsolutePath());
		               // Set the strAll to model
		               stringToModel(strAll );
		               //label.setText("File Selected: " + file.getName());
		            }else{
		               System.out.println("Open command canceled");
		            }
				}

				
			});
			btnNewButton.setBounds(107, 45, 138, 23);
			contentPanel.add(btnNewButton);
		}
		{
			JButton btnSaveThisDetails = new JButton("Save this details");
			btnSaveThisDetails.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
		            int option = fileChooser.showSaveDialog(frameParent);
		            if(option == JFileChooser.APPROVE_OPTION){
		               File file = fileChooser.getSelectedFile();
		               System.out.println("Save File as : " + file.getName());
		               
		               String strAll = modelToString();
		               FileHelper.writeFullStringText(file.getAbsolutePath(), strAll);
		            }else{
		            	System.out.println("Save command canceled");
		            }
				}
			});
			btnSaveThisDetails.setFont(new Font("Tahoma", Font.PLAIN, 12));
			btnSaveThisDetails.setBounds(319, 46, 138, 23);
			contentPanel.add(btnSaveThisDetails);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton dialog_ok_btn = new JButton("OK");
				dialog_ok_btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// Pass the details to parent..or Member variables.. close..
						// apply changes
						frameParent.applyHallDetailsUpdate();
						setVisible(false);
					}
				});
				dialog_ok_btn.setActionCommand("OK");
				buttonPane.add(dialog_ok_btn);
				getRootPane().setDefaultButton(dialog_ok_btn);
			}
			{
				JButton dialog_cancelButton = new JButton("Cancel");
				dialog_cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				dialog_cancelButton.setActionCommand("Cancel");
				buttonPane.add(dialog_cancelButton);
			}
		}
	}
	
	private void stringToModel(String strAll) {
		// TODO Auto-generated method stub
		String strArr[] = strAll.split("\n");
		listHallDetailsModel.clear();
		for(int i=0;i<strArr.length;i++) {
			if(strArr[i].length() > 2) {
				listHallDetailsModel.addElement(strArr[i]);
			}
		}
	}
	
	public String modelToString() {
		String strAll = "";
		for(int i=0;i<listHallDetailsModel.size();i++) {
			String str=listHallDetailsModel.get(i);
			strAll+= str + "\n";
		}
		return strAll;
		
	}
	
	public boolean assert_Number_entered() {
		boolean bSuccess = false;
		try {
			nHallCapacity = Integer.parseInt(txtHallCapacity.getText());
			bSuccess = true;
			//return true;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter Hall Capacity in number format,,,1 - 999");
			//return false;
		}
		if(!bSuccess) {
			return false;
		}
		try {
			nBenchCapacity = Integer.parseInt(txtBenchCapacity.getText());
			if(nBenchCapacity < 1 ||  nBenchCapacity > 10) {
				JOptionPane.showMessageDialog(null, "Please enter Bench Capacity in number format,,, 1 - 10,");
				return false;
			}
			bSuccess = true;
			//return true;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter Bench Capacity in number format,,,1 - 10");
			return false;
		}
		return true;
	}
	
	public void addHallDetails() {
		assert_Number_entered();
		String str = txtHallName.getText();
		String strData = str+":"+nHallCapacity+":"+nBenchCapacity;
		listHallDetailsModel.addElement(strData);
		ExAllocFrame.sortAlpabetic(listHallDetailsModel);
	};
	
	public void updateHallDetails() {
		assert_Number_entered();
		int selectionIndex = listHallDetails.getSelectedIndex();
		if((listHallDetailsModel.size() > selectionIndex)) {
			String str = txtHallName.getText();
			String strData = str+":"+nHallCapacity +  ":"+nBenchCapacity;
			listHallDetailsModel.set(selectionIndex,strData);
		}
		
	};
	
	public void deleteCurrentHall() {
		int selectionIndex = listHallDetails.getSelectedIndex();
		if((listHallDetailsModel.size() > selectionIndex)) {
			listHallDetailsModel.remove(selectionIndex);
		}
	}
}
