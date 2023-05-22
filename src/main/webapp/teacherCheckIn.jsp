<%@page import="org.json.simple.JSONObject"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String teacherName = request.getParameter("teacherName").trim();
	StudentDAO dao = new StudentDAO();
	ClassNoteVO vo = new ClassNoteVO();
if ( teacherName != null ){
	vo.setTeacherCheckIn(teacherName);
	}
System.out.println(vo); 
%>