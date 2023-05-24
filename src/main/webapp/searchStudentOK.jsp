<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String studentName = request.getParameter("nameText");

	 	System.out.println(studentName);	
		
	// 	out.println(teacherName);
	
	// 1. 강사 정보를 JSON 형태로 출력
		JSONArray student = new JSONArray();
		StudentDAO dao = new StudentDAO();
		
		ArrayList<ClassNoteVO> list = dao.studentSelectByName(studentName);
		
		for(ClassNoteVO vo : list) {
		JSONObject study = new JSONObject();
	
		study.put("no", vo.getStudentNo());
		study.put("name", vo.getStudentName());
		study.put("phone", vo.getStudentPhone());
		study.put("parentsPhone", vo.getStudentParentsPhone());
		study.put("schoolName", vo.getStudentSchoolName());
		study.put("lectureName", vo.getLectureClass());
		study.put("lectureStartDate", vo.getLectureStartDate());
		study.put("lectureEndDate", vo.getLectureEndDate());
	
		student.add(study);
	
		}
		
		out.println(student);
%>