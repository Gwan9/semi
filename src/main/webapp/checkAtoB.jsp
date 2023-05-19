<%@page import="VO.ClassNoteVOjsb"%>
<%@page import="DAO.StudentDAOjsb"%>
<%@page import="DAO.TeacherDAOsgh"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVOsgh"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%


String date1 = request.getParameter( "date1" );
String date2 = request.getParameter( "date2" );
String selectVal = request.getParameter( "selectVal" );

if( date1 != null && date2 != null && selectVal != null){
	
	if( Integer.parseInt(selectVal) == 1 ){
		JSONArray j = new JSONArray();
		StudentDAOjsb dao1 = new StudentDAOjsb();
		ArrayList<ClassNoteVOjsb> list1 = dao1.studentSelectAll( date1, date2 );
		for( ClassNoteVOjsb vo : list1){
			JSONObject student = new JSONObject();
			student.put( "studentNo", vo.getStudentNo() );
			student.put( "studentName", vo.getStudentName() );
			student.put( "studentSchoolName", vo.getStudentSchoolName() );
			student.put( "studentGrade", vo.getStudentGrade() );
			//student.put( "lectureClass", vo.getLectureClass() );
			student.put( "studentPhone", vo.getStudentPhone() );
			student.put( "studentParentsPhone", vo.getStudentParentsPhone() );
			student.put( "studentCheckIn", vo.getStudentCheckIn() );
			student.put( "studentCheckLate", vo.getStudentCheckLate() );
			student.put( "studentCheckLeave", vo.getStudentCheckLeave() );
			j.add( student );
		}
		out.println( j.toJSONString() );
		System.out.println("학생");
	}else if ( Integer.parseInt(selectVal) == 2 ){
		JSONArray teacherArray = new JSONArray();
		TeacherDAOsgh dao2 = new TeacherDAOsgh();
		ArrayList<ClassNoteVOsgh> list2 = dao2.teacherSelectAll( date1, date2 );
		for( ClassNoteVOsgh vo : list2 ){
			JSONObject teacher = new JSONObject();
			teacher.put( "teacherCheckIn", vo.getTeacherCheckIn() );
			teacher.put( "teacherCheckOut", vo.getTeacherCheckOut() );
			teacher.put( "teacherWorkTime", vo.getTeacherWorkTime() );
			teacherArray.add( teacher );
		}
		out.println( teacherArray.toJSONString() );
		System.out.println("교사");
	}
}
%>