<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%


String today = request.getParameter( "today" );
String selectVal = request.getParameter( "selectVal" );

if( today != null && selectVal != null){

	if( Integer.parseInt(selectVal) == 1 ){
		
		JSONArray j = new JSONArray();
		StudentDAO dao1 = new StudentDAO();
		ArrayList<ClassNoteVO> list1 = dao1.studentCheckSelectAllByDate( today );
		
		
		for( ClassNoteVO vo : list1 ){
			JSONObject student = new JSONObject();
			student.put( "studentNo", vo.getStudentNo() );
			student.put( "studentName", vo.getStudentName() );
			student.put( "studentSchoolName", vo.getStudentSchoolName() );
			student.put( "studentGrade", vo.getStudentGrade() );
			student.put( "lectureClass", vo.getLectureClass() );
			student.put( "studentPhone", vo.getStudentPhone() );
			student.put( "studentParentsPhone", vo.getStudentParentsPhone() );
			student.put( "studentCheckIn", vo.getStudentCheckIn() );
			student.put( "studentCheckLate", vo.getStudentCheckLate() );
			student.put( "studentCheckLeave", vo.getStudentCheckLeave() );
			j.add( student );
	}
		out.println( j.toJSONString() );
		System.out.println( j.toJSONString() );
	}else if( Integer.parseInt(selectVal) == 2 ){
		
		JSONArray teacherArray = new JSONArray();
		StudentDAO dao2 = new StudentDAO();
		ArrayList<ClassNoteVO> list2 = dao2.teacherSelectAllByDate( today );
		
		for( ClassNoteVO vo : list2 ){
			JSONObject teacher = new JSONObject();
			teacher.put( "teacherCheckIn", vo.getTeacherCheckIn() );
			teacher.put( "teacherCheckOut", vo.getTeacherCheckOut() );
			teacher.put( "teacherCheckTime", vo.getTeacherWorkTime() );
			teacherArray.add( teacher );
		}
		out.println( teacherArray.toJSONString() );
		System.out.println("교사");
	}
}
%>
