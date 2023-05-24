<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String teacherSubject = request.getParameter("lectureSelect");

	System.out.println(teacherSubject);

	JSONArray teacher = new JSONArray();
	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.teacherSelectBySubject(teacherSubject);
	
	for(ClassNoteVO vo : list) {
		JSONObject teach = new JSONObject();
		
		teach.put("no", vo.getTeacherNo());
		teach.put("name", vo.getTeacherName());
		teach.put("id", vo.getTeacherId());
		teach.put("pw", vo.getTeacherPw());
		teach.put("phone", vo.getTeacherPhone());
		teach.put("subject", vo.getTeacherSubject());
		teach.put("lectureStartDate", vo.getLectureStartDate());
		teach.put("lectureEndDate", vo.getLectureEndDate());
	
		teacher.add(teach);
		
	}
	
		out.println(teacher);

%>