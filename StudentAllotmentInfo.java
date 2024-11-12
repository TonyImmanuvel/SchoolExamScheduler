package yoogi.game.examallocator;

public class StudentAllotmentInfo {
	public ClassDetails fromClass;
	public HallDetails toHall;
	int iExamId;
	public int count;
	
	public StudentAllotmentInfo(ClassDetails theClass,HallDetails theHall,int theExamId,int theCount) {
		fromClass = theClass;
		toHall = theHall;
		count = theCount;
		iExamId = theExamId;
	}
}
