
<%@page import="DAO.TeacherDAOptm"%>
<%@page import="VO.ClassNoteVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String teacherId = request.getParameter("id");
	if(teacherId != null){
		TeacherDAOptm dao = new TeacherDAOptm();
		ClassNoteVO vo = dao.getOne(teacherId);
		if(vo == null){
			out.println("true");
		}else{
			out.println("false");
		}
	}
%>