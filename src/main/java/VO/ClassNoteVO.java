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
	int class_registerNo;
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
	String studentCheckIn;
	String studentCheckLate;
	String studentCheckLeave;
	String studentCheckDate;
	int noticeNo;
	String noticeDate;
	String noticeTitle;
	String noticeContents;
}

