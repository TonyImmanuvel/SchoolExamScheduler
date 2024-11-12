package yoogi.game.examallocator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ExamAllocationManager {

	

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		//cps.createRandomPuzzles(6, 7, 4, 10);
		//

		Properties propsRead = new Properties();
		Properties propsSave = new Properties();
		
		String strPath = System.getProperty("user.dir");

		String strPropFileName = strPath + "\\exam_allocation_init.txt"; 
		
		
		
		try (FileInputStream in = new FileInputStream(strPropFileName)) {
			propsRead.load(in);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String strClassDetails = propsRead.getProperty("CLASS_DETAILS");
		String strHallDetails = propsRead.getProperty("HALL_DETAILS");
		String strExamDetails = propsRead.getProperty("EXAM_DETAILS");
		String strExamsList = propsRead.getProperty("EXAM_LIST");
		String strExamsDay= propsRead.getProperty("DAY_OF_EXAM");
		
		String strSaveFileNamePath = propsRead.getProperty("SAVEPATH");
		
		WHExamAllocator whexamallc = new WHExamAllocator();
		
		
		String strArrClassDet[] = strClassDetails.split(",");
		String strArrHallDet[] = strHallDetails.split(",");
		
		int nExams = 0;
		
		whexamallc.addClassDetails(strArrClassDet);
		whexamallc.addHallDetails(strArrHallDet);
		
		String strArrExams[] = strExamsList.split(","); 
			nExams = strArrExams.length;
		
		for(int i=0;i<nExams;i++) {
			String strQuery = "EXAM_"+strArrExams[i];
			String strexamDet = propsRead.getProperty(strQuery);
			if(strexamDet == null || strexamDet.length() < 3) {
				System.out.print("Exam details for " + strQuery + " Missing...");
			}
			String strArrExDet[] =  strexamDet.split(",");
			int nStudentsPerBenchPerExam = Integer.parseInt(strArrExDet[0]);
			int nStudentsPerBench = Integer.parseInt(strArrExDet[1]);
			if(nStudentsPerBenchPerExam > nStudentsPerBench) {
				int temp = nStudentsPerBench;
				nStudentsPerBench = nStudentsPerBenchPerExam; 
				nStudentsPerBenchPerExam=temp;
			}
			whexamallc.addExamAA(strArrExams[i], nStudentsPerBenchPerExam,nStudentsPerBench,strArrExDet,2);
			//whexamallc.addExamAA(,x1,x2, ,3);
		}
		whexamallc.printViabilityData();
		
		whexamallc.i_last_iter_hall = 0;
		for(int i=0;i<nExams;i++) {
			whexamallc.fillStudentsSeqMethod(i,whexamallc.i_last_iter_hall);
		}
		
		whexamallc.printViabilityData();
		String strOutputFileName = strSaveFileNamePath + strExamsDay + "_output.txt";
		whexamallc.writeToFile(strOutputFileName);
		
		
		
	}
	
	
	 static void saveProperties(Properties p, String strPropFN) throws IOException
	    {
	        File file = new File(strPropFN);

		 	FileOutputStream fr = new FileOutputStream(file);
	        p.store(fr, "Properties");
	        fr.close();
	        System.out.println("After saving properties: " + p);
	    }

		
	
		
}
