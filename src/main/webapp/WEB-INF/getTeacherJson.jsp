<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%


	// 1. 강사 정보를 JSON 형태로 출력
	JSONArray teacher = new JSONArray();

	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.teacherSelectAll();
	
	for(ClassNoteVO vo : list) {
		JSONObject teach = new JSONObject();
		
		teach.put("id", vo.getTeacherId());
		teach.put("pw", vo.getTeacherPw());
		
		teacher.add(teach);
		
	}
	
	out.println(teacher);
%>