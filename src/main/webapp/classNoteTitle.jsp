<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONArray JA = new JSONArray();
	
	StudentDAO dao = new StudentDAO();
	
	
	
	ArrayList<ClassNoteVO> list = dao.classNoteGetThree();
	
	for(ClassNoteVO vo : list){
		JSONObject jb = new JSONObject();
		jb.put("NoteTitle", vo.getNoteTitle());
		
		JA.add(jb);
	}
%>
out.println(JA);