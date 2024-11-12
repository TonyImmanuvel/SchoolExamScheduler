package yoogi.game.gui;

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import yoogi.game.examallocator.WHExamAllocator;
import yoogi.game.fileutils.FileHelper;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

public class ExAllocFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblClassesList;
	JList jlistClassDet; 
	JList jlistExamDetails;
	private JList jlistHallDetails;
	
	private int numStudents;
	private DefaultListModel<String> listClassesDetailsModel = new DefaultListModel<String>();
	private DefaultListModel<String> listHallDetailsModel = new DefaultListModel<String>();
	private DefaultListModel<String> listExamDetailsModel = new DefaultListModel<String>();
	
	private JSplitPane jsp = new JSplitPane();
	private JLabel lblHalls;
	
	private JButton btnEditClasses;
	private JButton btnEditHalls;
	
	private DialogHallDetails dlgHall;
	private DialogClassDetails dlgClass;
	private DlgAddExam dlgAddExam;
	
	WHExamAllocator whexamallc = new WHExamAllocator();
	private JButton btnLoadExamDetails;
	private JTextField txtDay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExAllocFrame frame = new ExAllocFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ExAllocFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 626);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		dlgHall = new DialogHallDetails(this);
		dlgClass = new DialogClassDetails(this);
		dlgAddExam = new DlgAddExam(this);
		
		JScrollPane scrollPane = new JScrollPane();
		
		jlistClassDet = new JList();
		scrollPane.setViewportView(jlistClassDet);
		initClassDetails();
		jlistClassDet.setModel(listClassesDetailsModel);
		jlistClassDet.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlistClassDet.setBounds(0, 0, 120, 200);
		scrollPane.setBounds(50,50,140,225);
		//contentPane.add(jlistClassDet);
		contentPane.add(scrollPane);
		
		lblClassesList = new JLabel("Classes");
		lblClassesList.setBounds(31, 20, 83, 14);
		contentPane.add(lblClassesList);
		
		lblHalls = new JLabel("Halls");
		lblHalls.setBounds(483, 24, 77, 14);
		contentPane.add(lblHalls);
		
		
		jlistHallDetails = new JList();
		jlistHallDetails.setModel(listHallDetailsModel);
		jlistHallDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlistHallDetails.setBounds(0, 0, 120, 200);
		//contentPane.add(jlistHallDetails);
		
		JScrollPane scrollPane2 = new JScrollPane(jlistHallDetails);
		scrollPane2.setBounds(440, 50, 140, 225);
		contentPane.add(scrollPane2);
		
		btnEditClasses = new JButton("Edit Classes");
		btnEditClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showClassEditDialog();
			}
		});
		btnEditClasses.setBounds(88, 11, 109, 23);
		contentPane.add(btnEditClasses);
		
		btnEditHalls = new JButton("Edit Halls");
		btnEditHalls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHallEditDialog();
			}
		});
		btnEditHalls.setBounds(554, 20, 89, 23);
		contentPane.add(btnEditHalls);
		
		jlistExamDetails = new JList();
		jlistExamDetails.setModel(listExamDetailsModel);
		jlistExamDetails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		listExamDetailsModel.addElement("XI_TAM:2:XI-A,XI-B,XI-C,XI-D,XI-E,XI-F,XI-G,XI-H,XI-I");
		listExamDetailsModel.addElement("XII_CHE_TAM:1:XII-C,XII-D,XII-E,XII-F");
		listExamDetailsModel.addElement("XII_CHE_ENG:1:XII-A,XII-B");
		listExamDetailsModel.addElement("XII_ACC:1:XII-G,XII-J");
		listExamDetailsModel.addElement("XII_GEO:1:XII-H,XII-I");
				
				
				
		jlistExamDetails.setBounds(52, 362, 602, 125);
		contentPane.add(jlistExamDetails);
		
		JLabel lblExams = new JLabel("Exams");
		lblExams.setBounds(68, 337, 61, 14);
		contentPane.add(lblExams);
		
		JButton btnAddExams = new JButton("Add Exam");
		btnAddExams.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAddExamDialog();
			}
		});
		btnAddExams.setBounds(150, 333, 89, 23);
		contentPane.add(btnAddExams);
		
		JButton btnDeletSelectedExam = new JButton("Delete Exam");
		btnDeletSelectedExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCurrentExam();
			}
		});
		btnDeletSelectedExam.setBounds(249, 333, 115, 23);
		contentPane.add(btnDeletSelectedExam);
		
		JButton btnNewButton = new JButton("Prepare Allocation");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				prepareAllocation();
			}
		});
		btnNewButton.setBounds(267, 553, 171, 23);
		contentPane.add(btnNewButton);
		
		Frame thisParent  = this;
		JButton btnSaveExamDetails = new JButton("SaveExamsAs");
		btnSaveExamDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
						JFileChooser fileChooser = new JFileChooser();
			            int option = fileChooser.showSaveDialog(thisParent);
			            if(option == JFileChooser.APPROVE_OPTION){
			               File file = fileChooser.getSelectedFile();
			               System.out.println("Save File as : " + file.getName());
			               
			               String strAll = modelToStringExamDetails();
			               FileHelper.writeFullStringText(file.getAbsolutePath(), strAll);
			            }else{
			            	System.out.println("Save command canceled");
			            }
					}
				});
			
		btnSaveExamDetails.setBounds(382, 333, 123, 23);
		contentPane.add(btnSaveExamDetails);
		
		btnLoadExamDetails = new JButton("Load Exam Details");
		btnLoadExamDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
	            int option = fileChooser.showOpenDialog(thisParent);
	            if(option == JFileChooser.APPROVE_OPTION){
	               File file = fileChooser.getSelectedFile();
	               String strAll = FileHelper.readFullStringText(file.getAbsolutePath());
	               // Set the strAll to model
	               stringToModelExamDetails(strAll );
	               //label.setText("File Selected: " + file.getName());
	            }else{
	               System.out.println("Open command canceled");
	            }
			}
		});
		btnLoadExamDetails.setBounds(520, 333, 123, 23);
		contentPane.add(btnLoadExamDetails);
		
		txtDay = new JTextField();
		txtDay.setText("day1");
		txtDay.setBounds(299, 510, 86, 20);
		contentPane.add(txtDay);
		txtDay.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Exam Name/DateTime To Save");
		lblNewLabel.setBounds(107, 513, 165, 14);
		contentPane.add(lblNewLabel);
	}
	
	private void stringToModelExamDetails(String strAll) {
		// TODO Auto-generated method stub
		String strArr[] = strAll.split("\n");
		listExamDetailsModel.clear();
		for(int i=0;i<strArr.length;i++) {
			if(strArr[i].length() > 2) {
				listExamDetailsModel.addElement(strArr[i]);
			}
		}
	}
	
	public String modelToStringExamDetails() {
		String strAll = "";
		for(int i=0;i<listExamDetailsModel.size();i++) {
			String str=listExamDetailsModel.get(i);
			strAll+= str + "\n";
		}
		return strAll;
		
	}
	void initClassDetails() {
		listClassesDetailsModel.clear();
		
		//listClassesDetailsModel.addElement("XI-A=28");
	}
	/*
	public boolean assert_Number_entered() {
		try {
			numStudents = Integer.parseInt(txtNumOfStudents.getText());
			return true;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter Number of students in number format,,,,");
			return false;
		}
	}*/
	
	void applyClassDetailsUpdate() {
		listClassesDetailsModel.clear();
		for(int i=0;i<dlgClass.listClassDetailsModel.size();i++) {
			String str = dlgClass.listClassDetailsModel.get(i);
			listClassesDetailsModel.addElement(str);
		}
	}
	void applyHallDetailsUpdate() {
		listHallDetailsModel.clear();
		for(int i=0;i<dlgHall.listHallDetailsModel.size();i++) {
			String str = dlgHall.listHallDetailsModel.get(i);
			listHallDetailsModel.addElement(str);
		}
	};
	
	void applyExamDetailsUpdate() {
		String str = dlgAddExam.strExamName + ":";
		str+= dlgAddExam.numberOfStudentsInEachBench + ":";
		str+= dlgAddExam.strAllSelectedClasses + ":";
		listExamDetailsModel.addElement(str);
		
	};
	
	void showHallEditDialog() {
		dlgHall.setVisible(true);
	};
	
	void showClassEditDialog() {
		dlgClass.setVisible(true);
	}
	
	void showAddExamDialog() {
		if(listClassesDetailsModel.size() > 1) {
			dlgAddExam.listClassDetailsModel.clear();
			for(int i=0;i<listClassesDetailsModel.size();i++) {
				String str = listClassesDetailsModel.get(i);
				dlgAddExam.listClassDetailsModel.addElement(str);
			}
			dlgAddExam.setVisible(true);
		}else {
			JOptionPane.showMessageDialog(null, "Please Add atleast two Class Details first...");
		}
		
	}
	
	void deleteCurrentExam() {
		int k = jlistExamDetails.getSelectedIndex();
		if(k >=0 && k < listExamDetailsModel.size()) {
			listExamDetailsModel.remove(k);
		}
	}
	
	void prepareAllocation() {
		// Check Hall Size, Class Size for each exam, Grand Totall
		whexamallc.clearDetails();
		String strAllClasses = modelToString(listClassesDetailsModel);
		String strAllHalls = modelToString(listHallDetailsModel);
		String strAllExams = modelToString(listExamDetailsModel);
		
		whexamallc.buildClassDetails(strAllClasses);
		whexamallc.buildHallDetails(strAllHalls);
		whexamallc.buildExamDetails(strAllExams);
		
		whexamallc.i_last_iter_hall = 0;
		whexamallc.sortExamsByStrength();
		for(int i=0;i<whexamallc.nExams;i++) {
			whexamallc.fillStudentsSeqMethod(i,whexamallc.i_last_iter_hall);
		}
		
		String strSaveFileNamePath = "D:\\Examprep";
		String strExamsDay = txtDay.getText();
		whexamallc.printViabilityData();
		
		String path = ExAllocFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		String pathDecoded =  path;
		try {
			pathDecoded = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strOutputFileName = pathDecoded + "Exam__"+ strExamsDay + "_output";
		String strCSSFileName = pathDecoded + "style.cssappend.txt";
		whexamallc.writeToHTML(strOutputFileName,strCSSFileName);
		
		
		
	};
	
	public static void sortAlpabetic(DefaultListModel<String> theStringModel) {
		String strAll = "";
		int nStrs = theStringModel.size();
		for(int i=0;i<nStrs-1;i++) {
			for(int j=i+1;j<nStrs;j++) {
				String stri =theStringModel.get(i);
				String strj =theStringModel.get(j);
				if(stri.compareTo(strj) >0) {
					theStringModel.set(i, strj);
					theStringModel.set(j, stri);
				}
			}
		}
		
	}
	
	public static String modelToString(DefaultListModel<String> theStringModel) {
		String strAll = "";
		for(int i=0;i<theStringModel.size();i++) {
			String str=theStringModel.get(i);
			strAll+= str + "\n";
		}
		return strAll;		
	}
}
