<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
JSONArray JA = new JSONArray();
StudentDAO dao = new StudentDAO();

ArrayList<ClassNoteVO> list = dao.teacherSelectAll();

for (ClassNoteVO vo : list) {
	JSONObject teacher = new JSONObject();

	teacher.put("tname", vo.getTeacherName());

	JA.add(teacher);
}

out.println(JA);
%>