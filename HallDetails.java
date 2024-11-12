package yoogi.game.examallocator;

import java.util.Vector;

public class HallDetails {
	
	public String strHallName;
	public int nTotCapacity;
	public int nBenches;
	
	int iId = 0;;
	
	public Vector<StudentAllotmentInfo> vecAllot = new Vector<StudentAllotmentInfo>();
	public int nStudentsSeated=0; 
	
	public  HallDetails(String theHallName, int theTotCapacity,int theMaxPerExam,int the_id) {
		strHallName = theHallName;
		nTotCapacity = theTotCapacity;
		nBenches = theMaxPerExam;
		nStudentsSeated = 0;
		iId = the_id;
	};
	
	
	int calculatedAllottedCount() {
		nStudentsSeated = 0;
		for(int i=0;i<vecAllot.size();i++) {
			StudentAllotmentInfo allot = vecAllot.get(i);
			nStudentsSeated += allot.count;
		}
		return nStudentsSeated;
	}


	public boolean checkExamAllotted(int theExamId) {
		// TODO Auto-generated method stub
		for(int i=0;i<vecAllot.size();i++) {
			StudentAllotmentInfo allot = vecAllot.get(i);
			if(theExamId == allot.iExamId) 
				return true;
		}
		return false;
	}

	public int getSeatsAvailableForExamClass(int theExamId,int perBecnchStudentsForThisExam,ClassDetails cd) {
		int toSeatsDoneForExam = 0;
		int toSeatsDoneOverall = 0;
		int toSeatsDoneForClass = 0;
		for(int i=0;i<vecAllot.size();i++) {
			StudentAllotmentInfo allot = vecAllot.get(i);
			if(theExamId == allot.iExamId) { 
				toSeatsDoneForExam += allot.count;
				if(cd.strClassName.equalsIgnoreCase(allot.fromClass.strClassName)) {
					toSeatsDoneForClass+= allot.count;
				}
			}
			toSeatsDoneOverall+= allot.count;
		}
		int retValueForClass = nBenches - toSeatsDoneForClass;
		int maxAvailable = nTotCapacity - toSeatsDoneForClass;
		int retValueForExam = (nBenches * perBecnchStudentsForThisExam) - toSeatsDoneForExam;
		if(retValueForExam > maxAvailable) {
			retValueForExam = maxAvailable;
		}
		
		if(retValueForClass > retValueForExam) {
			retValueForClass = retValueForExam;
		}					
		return retValueForClass;
		
	}

	/*public int getSeatsAvailableForExam(int theExamId,int perBecnchStudentsForThisExam) {
		// TODO Auto-generated method stub
		int totSeatsDoneForExam = 0;
		int totSeatsDoneOverall = 0;
		for(int i=0;i<vecAllot.size();i++) {
			StudentAllotmentInfo allot = vecAllot.get(i);
			if(theExamId == allot.iExamId) { 
				totSeatsDoneForExam+= allot.count;
			}
			totSeatsDoneOverall+= allot.count;
		}
		int retValue = (nBenches * perBecnchStudentsForThisExam) - totSeatsDoneForExam;
		int maxAvailable = nTotCapacity - totSeatsDoneOverall;
		if(maxAvailable < retValue)
			return maxAvailable;				
		return retValue;
	}*/


	public void addAllotment(StudentAllotmentInfo stdAllInfo) {
		// TODO Auto-generated method stub
		vecAllot.add(stdAllInfo);

		
	}


	public String toHTMLString() {
		// TODO Auto-generated method stub
		
		//String str = "<tr> <th> "+ strClassName + "</th><th>" + nStudents + "</th></tr>";
		int nSeatedCount = calculatedAllottedCount();
		String str = "<tr> <th> "+ strHallName + "</th><th>" + nSeatedCount + "/" + nTotCapacity +"</th></tr>";
		
						
		
		for(int j=0;j<vecAllot.size();j++) {
			StudentAllotmentInfo info = vecAllot.get(j);
			str += "<tr><td>" + info.fromClass.strClassName+ "</td><td>" + info.count + "</td></tr>"; 
		}
		str +="";
		
		return str;
	}

}
