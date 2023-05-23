<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	JSONArray noteArray = new JSONArray();	

	StudentDAO dao = new StudentDAO();
	
	ArrayList<ClassNoteVO> list = dao.classNoteGetThree();
	
	for(ClassNoteVO vo : list){
	
		JSONObject note = new JSONObject();
		
		note.put("noteno", vo.getNoteNo());
		note.put("notedate", vo.getNoteDate());
		note.put("notetitle", vo.getNoteTitle());
		note.put("notecontents", vo.getNoteContents());
		note.put("teacherno", vo.getTeacherNo());
	
		noteArray.add(note);
	}
	out.println(noteArray.toJSONString());
%>
