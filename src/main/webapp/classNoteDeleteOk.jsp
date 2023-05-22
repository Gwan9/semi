<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String no = request.getParameter("NoteNo");

	if(no != null){
		int NoteNo = Integer.parseInt(no);
		
		StudentDAO dao = new StudentDAO();
		
		dao.studentNoteDeleteOne(NoteNo);
		
		response.sendRedirect("classNote.jsp");
	}

%>