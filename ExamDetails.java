package yoogi.game.examallocator;

import java.util.Vector;

public class ExamDetails {
	public int num;
	public int iExamStdEachBench;
	public int iTotStdsEachBench;
	public int totStdnInExam = 0;
	
	public String strName;
	public Vector<ClassDetails> vecClasses = new Vector<ClassDetails>();
	
	
	public ExamDetails(String strTheName,int theNumExamStdPerBench,int theTotStdnsPerBench){
		strName = strTheName;
		iExamStdEachBench = theNumExamStdPerBench;
		iTotStdsEachBench = theTotStdnsPerBench;
		
	}
	
	public void addClass(ClassDetails theCD) {
		for(int i=0;i<vecClasses.size();i++) {
			ClassDetails cd = vecClasses.get(i);
			if(theCD.strClassName.equals(cd.strClassName)) {
				//do not add
				return;
			}
		}
		vecClasses.add(theCD);
	}
	
	public int getTotNumStudents() {
		int sum=0;
		for(int i=0;i<vecClasses.size();i++) {
			ClassDetails cd = vecClasses.get(i);
			sum += cd.nStudents;
		}
		return sum;
	}
}
