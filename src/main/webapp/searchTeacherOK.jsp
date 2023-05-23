<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String teacherName = request.getParameter("nameText");

	 	System.out.println(teacherName);	
		
	// 	out.println(teacherName);
	
	// 1. 강사 정보를 JSON 형태로 출력
		JSONArray teacher = new JSONArray();
		StudentDAO dao = new StudentDAO();
		
		ArrayList<ClassNoteVO> list = dao.teacherSelectByName(teacherName);
		
		for(ClassNoteVO vo : list) {
		JSONObject teach = new JSONObject();
	
		teach.put("no", vo.getTeacherNo());
		teach.put("name", vo.getTeacherName());
		teach.put("id", vo.getTeacherId());
		teach.put("pw", vo.getTeacherPw());
		teach.put("phone", vo.getTeacherPhone());
		teach.put("subject", vo.getTeacherSubject());
		teach.put("lectureStartDate", vo.getLectureStartDate());
		teach.put("LectureEndDate", vo.getLectureEndDate());
	
		teacher.add(teach);
	
		}
		
		out.println(teacher);
%>