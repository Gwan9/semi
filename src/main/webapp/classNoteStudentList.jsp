<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	
	JSONArray JA = new JSONArray();
	StudentDAO dao = new StudentDAO();
	request.setCharacterEncoding("UTF-8");
	String studentName = request.getParameter("studentName");
	
	ArrayList<ClassNoteVO> list = dao.studentNoteSelectStudentName(studentName);
	
	for (ClassNoteVO vo : list) {
		JSONObject student = new JSONObject();

		student.put("noteno", vo.getNoteNo());
		student.put("lecturename", vo.getLectureName());
		student.put("lectureclass",vo.getLectureClass());
		student.put("studentname", vo.getStudentName());
		student.put("notetitle", vo.getNoteTitle());
		student.put("notedate", vo.getNoteDate());
		student.put("teachername",vo.getTeacherName());
		
		JA.add(student);
	}
	
out.println(JA.toJSONString());
%>
