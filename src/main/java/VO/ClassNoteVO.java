package VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassNoteVO {
	int noteNo;
	String noteDate;
	String noteContents;
	String noteTitle;
	int teacherNo;
	String teacherId;
	String teacherPw;
	String teacherName;
	String teacherAddress;
	int teacherSal;
	String teacherPhone;
	String teacherEmail;
	String teacherSubject;
	String teacherPhoto;
	String teacherWorktype;
	String teacherHiredate;
	String teacherBirth;
	boolean teacherGender;
	int teacherCheckNo;
	String teacherCheckIn;
	String teacherCheckOut;
	String teacherWorkTime;
	String teacherCheckDate;
	int classRegisterNo;
	boolean isPay;
	String payType;
	int studentNo;
	String studentName;
	int studentGrade;
	String studentPhone;
	String studentRegistDate;
	String studentParentsName, studentParentsPhone;
	String studentDueDate;
	String studentPhoto;
	boolean studentGender;
	String studentBirth;
	String studentAddrs;
	String studentEmail;
	String studentSchoolName;
	boolean studentStatus;
	int lectureNo;
	String lectureName, lectureClass, lectureStartDate, lectureEndDate;
	int lectureTuition;
	int studentCheckNo;
	int studentCheck;
	String studentCheckDate;
	String studentCheckStatus;
	int studentCheckType;
	int noticeNo;
	String noticeDate;
	String noticeTitle;
	String noticeContents;
	
	public ClassNoteVO(int teacherNo, String teacherId, String teacherPw, String teacherName,
			String teacherAddress, int teacherSal, String teacherPhone, String teacherEmail, String teacherSubject,
			String teacherPhoto, String teacherWorktype, String teacherHiredate, String teacherBirth, boolean g) {
		
		this.teacherNo = teacherNo;
		this.teacherId = teacherId;
		this.teacherPw = teacherPw;
		this.teacherName = teacherName;
		this.teacherAddress = teacherAddress;
		this.teacherSal = teacherSal;
		this.teacherPhone = teacherPhone;
		this.teacherEmail = teacherEmail;
		this.teacherSubject = teacherSubject;
		this.teacherPhoto = teacherPhoto;
		this.teacherWorktype = teacherWorktype;
		this.teacherHiredate = teacherHiredate;
		this.teacherBirth = teacherBirth;
		this.teacherGender = g;
		
	}
}

