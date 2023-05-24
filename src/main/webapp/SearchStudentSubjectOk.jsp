<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String studentClass = request.getParameter("lectureSelect");

	System.out.println(studentClass);

	JSONArray student = new JSONArray();
	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.studentSelectBySubject(studentClass);
	
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