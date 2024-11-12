
package yoogi.game.examallocator;

import java.util.Random;

import yoogi.game.fileutils.FileHelper;

public class WHExamAllocator {
    public int nClasses;
    public int nHalls;
    public int nExams;
    
    public int i_last_iter_hall = 0;
    public boolean lastCloseMethodWasOk = false;
    
	ClassDetails arrClasses[] = new ClassDetails[60];
	HallDetails arrHalls[] = new HallDetails[60];
	ExamDetails arrExams[] = new ExamDetails[60];
	
    
	
	void initAll() {
		nClasses =0;
		nHalls = 0;
		nExams = 0;
	}
	void addClassDetails(String theArrClassesDet[]) {
		// [XI-A:74]
		for(int i=0;i<theArrClassesDet.length;i++) {
			String stri = theArrClassesDet[i];
			int end = stri.length()-1;
			String str_i_withoutbrackets = stri.substring(1,end);
			String str2[] = str_i_withoutbrackets.split(":");
			int num = Integer.parseInt(str2[1]);
			addClass(str2[0], num);;
		}	
	}
	
	ClassDetails findClass(String theName) {
		for(int i=0;i<nClasses;i++) {
    		if(arrClasses[i].strClassName.equalsIgnoreCase(theName)) {
    			return arrClasses[i];
    		}
		}
		return null;
	}
	
	void addHallDetails(String theArrHallDet[]) {
		// [XI-A:72:18]
		for(int i=0;i<theArrHallDet.length;i++) {
			String stri = theArrHallDet[i];
			int end = stri.length()-1;
			String str_i_withoutbrackets = stri.substring(1,end);
			String str3[] = str_i_withoutbrackets.split(":");
			int numTot = Integer.parseInt(str3[1]);
			int numBenches = Integer.parseInt(str3[2]);
			addHall(str3[0], numTot, numBenches);
			
		}	
	}
	
	
    void addClass(String strClassName,int num) {
    	for(int i=0;i<nClasses;i++) {
    		if(arrClasses[i].strClassName.equalsIgnoreCase(strClassName)) {
    			System.out.println("Class Name "+ strClassName + " Already Exists");
    		}
    	}
    	arrClasses[nClasses++] = new ClassDetails(strClassName, num);
    }
    
    void addHall(String strHallName,int numTot,int numMaxPerExam) {
    	for(int i=0;i<nHalls;i++) {
    		if(arrHalls[i].strHallName.equalsIgnoreCase(strHallName)) {
    			System.out.println("Hall Name "+ strHallName + " Already Exists");
    		}
    	}
    	arrHalls[nHalls] = new HallDetails(strHallName, numTot,numMaxPerExam,nHalls);
    	nHalls++;
    }
    
    void addExamAA(String theExamName,int theNumStdsPerBench,int theBenchCapacity,String strArr[],int theArrStart) {

    	arrExams[nExams] = new ExamDetails(theExamName,theNumStdsPerBench,theBenchCapacity);
    	for(int i=theArrStart;i<strArr.length;i++) {
    		ClassDetails cd = findClass(strArr[i]);
    		if(cd != null) {
    			arrExams[nExams].addClass(cd);
    		}else {
    			System.out.print("This class "+strArr[i] +" is added to exam " + theExamName + " But not defined in class details..." );
    		}
    	}
    	nExams++;
    }
    
    public void printViabilityData() {
    	System.out.println(" Current Snapshot ");
    	printClassDetails();
    	printHallDetails();
    	for(int i=0;i<nExams;i++) {
    		printExamDetails(i);
    	}
    }
    
    String classDetailsToHTML(int nColsToWrite) {
    	int totStudents = 0;
    	
    	  
    	  
    	String strAll = "<table class=\"table2\">\n";
    	
    	int nColsPerTable = nColsToWrite;
    	int nBigRows = nClasses / nColsPerTable;
    	if(nBigRows *nColsPerTable < nClasses) {
    		nBigRows+=1;
    	}
    	for(int y=0;y<nBigRows;y++) {
    		strAll += "\n<tr>";
    		for(int x=0;x<nColsPerTable;x++) {
    			int k = y * nColsPerTable + x;
    			String str = " ";
    			if(k < nClasses) {
    				ClassDetails cd = arrClasses[k];
    				str = cd.toHTMLString();
    				totStudents+=cd.nStudents;
    			}
    			strAll += "\n<td>  <table class=\"table1\"> "+str+"  </table></td>\n";
    		}
    		strAll += "\n</tr>\n";
    	}
    	
    	strAll += "<tr><td colspan=\""+nColsToWrite+"\">Total Students  "+ totStudents+"</td></tr></table>\n";
    	return strAll;
    }
    
    String classDetailsToString() {
    	int totStudents = 0;
    	String strAll = " ++ CLASS DETAILS ++ \n";
    	for(int i=0;i<nClasses;i++) {
    		ClassDetails cd = arrClasses[i];
    		strAll += "Class "+ cd.strClassName + " =  "+ cd.nStudents + "\n";
    		totStudents+= cd.nStudents;
    		int nAllot = cd.vecAllot.size();
    		String strAllot = "";
    		for(int j=0;j<nAllot;j++) {
    			StudentAllotmentInfo info = cd.vecAllot.get(j);
    			strAllot += "    " + info.toHall.strHallName+ "-" + info.count + "\n"; 
    			totStudents+= cd.nStudents;
    		}
    		if(nAllot > 0) {
    			strAll += strAllot;
    		}
    	}
    	strAll += "Total Students = "+ totStudents+"\n---------------------------\n";
    	return strAll;
    }
    void printClassDetails() {
    	/*int totStudents = 0;
    	for(int i=0;i<nClasses;i++) {
    		ClassDetails cd = arrClasses[i];
    		System.out.println("    Class "+ cd.strClassName + " =  "+ cd.nStudents);
    		totStudents+= cd.nStudents;
    		int nAllot = cd.vecAllot.size();
    		String strAllot = " ( ";
    		for(int j=0;j<nAllot;j++) {
    			StudentAllotmentInfo info = cd.vecAllot.get(j);
    			strAllot += " " + info.toHall.strHallName+ "-" + info.count + "  "; 
    		}
    		strAllot += " )\n";
    		if(nAllot > 0) {
    			System.out.println(strAllot);
    		}
    	}
    	System.out.println("Total Students = "+ totStudents+"\n");
    	*/
    	System.out.print(classDetailsToString());
    	
    	
    }
    
    String hallDetailsToHTMLString(int nColsToWrite) {
    	String strAll = "<table class=\"table2\">";
    	int nColsPerTable = nColsToWrite;
    	int nBigRows = nHalls / nColsPerTable;
    	if(nBigRows *nColsPerTable < nHalls) {
    		nBigRows+=1;
    	}
    	int totCapacity= 0;
    	int totSeated = 0;
    	for(int y=0;y<nBigRows;y++) {
    		strAll += "\n<tr>";
    		for(int x=0;x<nColsPerTable;x++) {
    			
	    		int k = y * nColsPerTable + x;
				String str = " ";
				if(k < nHalls) {
					HallDetails hd = arrHalls[k];
					str = hd.toHTMLString();
					totCapacity+= hd.nTotCapacity;
					totSeated += hd.calculatedAllottedCount();
				}
				strAll += "\n<td>   <table class=\"table1\">"+str+" </table></td>\n";
    		}
    		strAll += "</tr>\n";
    	}
	
    	strAll += "\n<tr><td colspan=\""+nColsToWrite+"\"> Total Seated --  Max Capacity"+ totSeated+" -- "+ totCapacity+"</td></tr></table>\\n";
    	return strAll;
	
    		
    }
    
    String hallDetailsToString() {
    	String strAll = " ++ HALL  DETAILS ++ \n";
    	int totCapacity= 0;
    	for(int i=0;i<nHalls;i++) {
    		HallDetails hd = arrHalls[i];
    		strAll+= "Hall "+ hd.strHallName + " =  "+ hd.nTotCapacity + "\n";
    		totCapacity+= hd.nTotCapacity;
    		
    		int nAllot = hd.vecAllot.size();
    		String strAllot = "";
    		for(int j=0;j<nAllot;j++) {
    			StudentAllotmentInfo info = hd.vecAllot.get(j);
    			strAllot += "   " + info.fromClass.strClassName+ "=" + info.count + "\n"; 
    		}
    		strAllot += ""; 
    		if(nAllot > 0) {
    			strAll+= strAllot;
    		}
    		
    	}
    	strAll+= "Total Capacity = "+ totCapacity + "\n-----------------------------\n";
    	return strAll;
    	
    }
    void printHallDetails() {
    	/*
    	int totCapacity= 0;
    	for(int i=0;i<nHalls;i++) {
    		HallDetails hd = arrHalls[i];
    		System.out.println("  Hall "+ hd.strHallName + " =  "+ hd.nTotCapacity);
    		totCapacity+= hd.nTotCapacity;
    		
    		int nAllot = hd.vecAllot.size();
    		String strAllot = " ( ";
    		for(int j=0;j<nAllot;j++) {
    			StudentAllotmentInfo info = hd.vecAllot.get(j);
    			strAllot += " " + info.fromClass.strClassName+ "=" + info.count + "    "; 
    		}
    		strAllot += ")\n"; 
    		if(nAllot > 0) {
    			System.out.println(strAllot);
    		}
    		
    	}
    	System.out.println("Total Capacity = "+ totCapacity + "\n");
    	*/
    	System.out.print(hallDetailsToString());
    }
    
    void printExamDetails(int examNum) {
    	ExamDetails ed = arrExams[examNum];
    	System.out.println("Exam = "+ ed.strName);
    	String strClassesList = "";
    	int totStudents = 0;
    	for(int i=0;i<ed.vecClasses.size();i++) {
    		ClassDetails cd = ed.vecClasses.get(i);
    		System.out.println("  "+ cd.strClassName + " =  "+ cd.nStudents);
    		strClassesList+=cd.strClassName + " =  "+ cd.nStudents + "  ";
    		totStudents+= cd.nStudents;
    		
    	}
    	System.out.println("Total Exam Students = "+ totStudents);
    	
    }
    
    ClassDetails getFullyUnAllottedClass(int theExamId) {
    	ExamDetails examd = arrExams[theExamId];
    	int nC = examd.vecClasses.size();
    	for(int i=0;i<nC;i++) {
    		ClassDetails cd  = examd.vecClasses.get(i);
    		if(cd.nStudentsAllotted == 0)
    			return cd;
    	}
    	return null;
    }
    
    HallDetails getExamNotAllottedHall(int theExamId,int perBenchExamNumOfStudents,int iter_from,ClassDetails cd,int minReq) {
    	
    	
    	for(int i=0;i<nHalls;i++) {
    		int ii = (i + iter_from) % nHalls;
    		HallDetails hd  = arrHalls[ii];
    		int res = hd.getSeatsAvailableForExamClass(theExamId,perBenchExamNumOfStudents,cd); 
    		if(res < 1) {
    			continue;
    		}
    		if(res < minReq )
    			continue;
    		
    		return hd;
    	}
    	return null;
    }
    
    public void fillStudentsSeqMethod(int theExamId,int kLastDoneClassId) {
    	ExamDetails ed = arrExams[theExamId];
    	ClassDetails cd = getFullyUnAllottedClass(theExamId);
    	while(cd != null) {
    		cd = getFullyUnAllottedClass(theExamId);	
    		if(cd == null) {
    			System.out.println("All Exam " + ed.strName   + " were allotted..");
	    		return;
	    	}
    		System.out.println("Working on  " + ed.strName  +  "  On Class "+ cd.strClassName +" ....");
	    	int nStudentsRemian = cd.getRemainingCount();
	    	while(nStudentsRemian > 0) {
	    		HallDetails hd =  getExamNotAllottedHall(theExamId,ed.iExamStdEachBench,i_last_iter_hall,cd,3);
	    		if(hd == null) {
	    			System.out.println("Suitable Hall for Exam " + ed.strName+ " and Class " + cd.strClassName  +"cannot be found..");
	    			return;
	    		}
	    		i_last_iter_hall = hd.iId;
	    		int hSeatsAvailable =  hd.getSeatsAvailableForExamClass(theExamId,ed.iExamStdEachBench,cd);
	    		//create a fragment of less than 3, then place less students so that in next class there will be atleast 3+
	    		if((nStudentsRemian > hSeatsAvailable) && ((nStudentsRemian-hSeatsAvailable) <= 3)) {
	    			hSeatsAvailable = nStudentsRemian - 3; 
	    		}
	    		if(hSeatsAvailable > nStudentsRemian) {
	    			StudentAllotmentInfo stdAllInfo = new StudentAllotmentInfo(cd, hd, theExamId,nStudentsRemian);
	    			cd.addAllotment(stdAllInfo);
	    			hd.addAllotment(stdAllInfo);
	    		}else {
	    			StudentAllotmentInfo stdAllInfo = new StudentAllotmentInfo(cd, hd, theExamId,hSeatsAvailable);
	    			cd.addAllotment(stdAllInfo);
	    			hd.addAllotment(stdAllInfo);
	    		}
	    		printViabilityData();
	    		nStudentsRemian = cd.getRemainingCount();
	    	}
	    }
    }
	public void writeToFile(String strOutputFileName) {
		String strFullData = "";
		strFullData += classDetailsToString();
		strFullData += hallDetailsToString();
		FileHelper.writeFullStringText(strOutputFileName,strFullData);
		// TODO Auto-generated method stub
		
	}
	public void clearDetails() {
		// TODO Auto-generated method stub
		nClasses = 0;
		nHalls = 0;
		nExams = 0;
	}
	public void buildClassDetails(String theStrAllClasses) {
		// TODO Auto-generated method stub
		String theArrClassesDef[] = theStrAllClasses.split("\n");
		for(int i=0;i<theArrClassesDef.length;i++) {
			String stri = theArrClassesDef[i];
			String str2[] = stri.split("=");
			int num = Integer.parseInt(str2[1]);
			addClass(str2[0], num);;
		}
	}
	
	public void buildHallDetails(String theStrAllHalls) {
		// TODO Auto-generated method stub
		String theArrHallsDef[] = theStrAllHalls.split("\n");
		for(int i=0;i<theArrHallsDef.length;i++) {
			String stri = theArrHallsDef[i];
			String str3[] = stri.split(":");
			int num1 = Integer.parseInt(str3[1]);
			int num2 = Integer.parseInt(str3[2]);
			if(num1 % num2 > 0)
				num2 = 1 + (num1 / num2);
			else
				num2 = (num1 / num2);
			addHall(str3[0], num1, num2);;
		}
	}
	public void buildExamDetails(String theStrAllExams) {
		// TODO Auto-generated method stub
		//"XI_TAM:2:XI-A,XI-B,XI-C,XI-D,XI-E,XI-F,XI-G,XI-H,XI-I"
		String theArrAllExamDef[] = theStrAllExams.split("\n");
		for(int i=0;i<theArrAllExamDef.length;i++) {
			String strArrExamParts[] = theArrAllExamDef[i].split(":");
			
			String strExamName = strArrExamParts[0];
			String strExamStdnPerBench = strArrExamParts[1];
			String strExamClassesList = strArrExamParts[2];
			String strArrClasses[] = strExamClassesList.split(",");
			int nStdsPerBench = Integer.parseInt(strExamStdnPerBench);
			addExamAA(strExamName, nStdsPerBench, 4, strArrClasses, 0);
		}
		
	}
	
	public void sortExamsByStrength(){
		for(int i=0;i<nExams;i++) {
			arrExams[i].totStdnInExam = arrExams[i].getTotNumStudents();
		}
		for(int i=0;i<nExams-1;i++) {
			for(int j=i+1;j<nExams;j++) {
				if(arrExams[i].totStdnInExam < arrExams[j].totStdnInExam) {
					ExamDetails exd_temp= arrExams[i];
					arrExams[i] = arrExams[j];
					arrExams[j] = exd_temp;
				}
			}
		}
	}
	
	public void writeToHTML(String strOutputFileName,String stylecss_filename) {
		
		String strFullData = "<HTML>";
		stylecss_filename = stylecss_filename.substring(1,stylecss_filename.length());
		String strStyleData = FileHelper.readFullStringText(stylecss_filename);
		
		strFullData +="<head>\n" + strStyleData + "</head><body>";
		
		strFullData += classDetailsToHTML(10);
		strFullData += "<br><br>";
		
		FileHelper.writeFullStringText(strOutputFileName+"_Class.html",strFullData);
		
		
		strFullData = "<HTML>";
		
		stylecss_filename = stylecss_filename.substring(1,stylecss_filename.length());
		strFullData +="<head>\n" + strStyleData + "</head><body>";
		strFullData += hallDetailsToHTMLString(10);
		strFullData += "<br><br>";
		strFullData += "</body></html>";
		
		FileHelper.writeFullStringText(strOutputFileName+"_Halls.html",strFullData);	
		
	};
}
