<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

//파라미터 값
String today = request.getParameter("today");
String selectVal = request.getParameter("selectVal");
String studentName = request.getParameter("studentName");
String lectureName = request.getParameter("lectureName");
String lectureClass = request.getParameter("lectureClass");
String teacherName = request.getParameter("teacherName");
//Null 체크
if (today != null && selectVal != null && studentName != null && lectureName != null && lectureClass != null && teacherName != null) {
	 today = today.trim();
	 selectVal = selectVal.trim();
	 studentName = studentName.trim();
	 lectureName = lectureName.trim();
	 lectureClass = lectureClass.trim();
	 teacherName = teacherName.trim();
 // ... 이하 코드 생략 ...
	if( Integer.parseInt(selectVal) == 1 ){
		
		JSONArray studentArray = new JSONArray();
		StudentDAO dao1 = new StudentDAO();
		ArrayList<ClassNoteVO> list1;
		list1 = dao1.studentCheckSelectAllByDate(today);
		if (studentName != null && !studentName.isEmpty()) {
			list1 = dao1.studentSearchSelectAllByName(studentName);
		}
		// 날짜 + 반 선택
		else if ("학생명선택".equals(studentName)&& !"반선택".equals(lectureClass) && "강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByLectureClassByDate(today, lectureClass);
		}
		// 날짜 + 학생명 선택
		else if (!"학생명선택".equals(studentName)&& "반선택".equals(lectureClass) && "강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByStudentNameByDate(today, studentName);
		}
		// 날짜 + 강의 선택
		else if ("학생명선택".equals(studentName)&& "반선택".equals(lectureClass) && !"강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByLectureNameByDate(today, lectureName);
		}
		// 날짜 + 반 선택 + 학생명 선택
		else if (!"학생명선택".equals(studentName)&& !"반선택".equals(lectureClass) && "강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByStudentNameByLectureClassByDate(today, studentName, lectureClass);
		}
		// 날짜 + 반 선택 + 강의 선택
		else if ("학생명선택".equals(studentName)&& !"반선택".equals(lectureClass) && !"강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByLectureNameByLectureClassByDate(today, lectureName, lectureClass);
		}
		// 날짜 + 반 선택 + 강의 선택 + 학생명 선택
		else if (!"학생명선택".equals(studentName)&& !"반선택".equals(lectureClass) && !"강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByLectureNameByLectureClassByStudentNameByDate(today, lectureName, lectureClass, studentName);
		}
		// 날짜 + 강의 선택 + 학생명 선택
		else if (!"학생명선택".equals(studentName)&& "반선택".equals(lectureClass) && !"강의선택".equals(lectureName)) {
			list1 = dao1.studentCheckSelectByLectureNameByStudentNameByDate(today, lectureName, studentName );
		}
		
		else {
			list1 = new ArrayList<>();
			System.out.println(list1);
		}
		
		for( ClassNoteVO vo : list1 ){
			JSONObject student = new JSONObject();
			student.put( "studentNo", vo.getStudentNo() );
			student.put( "studentName", vo.getStudentName() );
			student.put( "studentSchoolName", vo.getStudentSchoolName() );
			student.put( "studentGrade", vo.getStudentGrade() );
			student.put( "lectureClass", vo.getLectureClass() );
			student.put( "studentPhone", vo.getStudentPhone() );
			student.put( "studentParentsPhone", vo.getStudentParentsPhone() );
			student.put( "studentCheckNo", vo.getStudentCheckNo() );
			student.put( "studentCheckLate", vo.getStudentCheckStatus() );
			student.put( "studentCheckDate", vo.getStudentCheckDate() );
			
			studentArray.add( student );
		}
		out.println( studentArray.toJSONString() );
		System.out.println( studentArray.toJSONString() );
	
	}
	

		else if( Integer.parseInt(selectVal) == 2 ){
			
			JSONArray teacherArray = new JSONArray();
			StudentDAO dao2 = new StudentDAO();
			ArrayList<ClassNoteVO> list2 = dao2.teacherSelectAllByDate( today, teacherName );
			
			for( ClassNoteVO vo : list2 ){
				JSONObject teacher = new JSONObject();
				teacher.put( "teacherNo", vo.getTeacherNo() );
				teacher.put( "teacherCheckIn", vo.getTeacherCheckIn() );
				teacher.put( "teacherCheckOut", vo.getTeacherCheckOut() );
				teacher.put( "teacherCheckWorkTime", vo.getTeacherWorkTime() );
				
				teacherArray.add( teacher );
			}
			out.println( teacherArray.toJSONString() );
			System.out.println( teacherArray.toJSONString() );
		
		}

	}
%>
