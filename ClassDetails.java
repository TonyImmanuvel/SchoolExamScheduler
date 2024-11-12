package yoogi.game.examallocator;

import java.util.Vector;

public class ClassDetails {
	public String strClassName;
	public int nStudents;
	
	int nStudentsAllotted = 0;
	
	public Vector<StudentAllotmentInfo> vecAllot = new Vector<StudentAllotmentInfo>(); 
	
	ClassDetails(String theName,int theNumStudents){
		strClassName = theName;
		nStudents = theNumStudents;
		nStudentsAllotted = 0;
	}
	
	public void addAllotment(StudentAllotmentInfo theAllotInfo) {
		vecAllot.add(theAllotInfo);
	}
	
	public int getRemainingCount() {
		calculatedAllottedCount();
		return nStudents - nStudentsAllotted;
	}
	
	private int calculatedAllottedCount() {
		nStudentsAllotted = 0;
		for(int i=0;i<vecAllot.size();i++) {
			StudentAllotmentInfo allot = vecAllot.get(i);
			nStudentsAllotted += allot.count;
		}
		return nStudentsAllotted;
	}

	public String toHTMLString() {
		// TODO Auto-generated method stub
		String str = "<tr> <th> "+ strClassName + "</th><th>" + nStudents + "</th></tr>";
		
						
		
		for(int j=0;j<vecAllot.size();j++) {
			StudentAllotmentInfo info = vecAllot.get(j);
			str += "<tr><td>" + info.toHall.strHallName+ "</td><td>" + info.count + "</td></tr>"; 
		}
		//str +="</table>";
		return str;
	}
}
