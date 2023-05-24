
<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String teacherId = request.getParameter("id");
	if(teacherId != null){
		StudentDAO dao = new StudentDAO();
		ClassNoteVO vo = dao.teacherSelectAllById(teacherId);
		if(vo == null){
			out.println("true");
		}else{
			out.println("false");
		}
	}
%>