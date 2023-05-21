<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String studentCheckType = request.getParameter("studentCheckType");
String studentNo = request.getParameter("studentNo");

if( studentCheckType != null && studentNo != null ){
	
	int sct = Integer.parseInt(studentCheckType);
	int no = Integer.parseInt(studentNo);
	
		JSONArray j = new JSONArray();
		StudentDAO dao = new StudentDAO();
		ArrayList<ClassNoteVO> list = dao.studentCheckUpdateByTypeByNo( sct, no ); 
		
		for( ClassNoteVO vo : list){
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
			j.add( student );
		}
		out.println( j.toJSONString() );
}
%>