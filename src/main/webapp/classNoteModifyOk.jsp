<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String No = request.getParameter("NoteNo");
	String NoteTitle = request.getParameter("NoteTitle");
	String tarea = request.getParameter("TArea");
	
	if(No != null){
		StudentDAO dao = new StudentDAO();
		ClassNoteVO vo = new ClassNoteVO();
		
		int NoteNo = Integer.parseInt(No);
		
		vo.setNoteNo(NoteNo);
		vo.setNoteTitle(NoteTitle);
		vo.setNoteContents(tarea);
		
		dao.studentNoteUpdateOne(vo);

		response.sendRedirect("classNote.jsp");
	}
%>