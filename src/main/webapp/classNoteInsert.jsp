<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%

// 	전달된 데이터 가져오기
	String selectStudentList = request.getParameter("selectStudentList");
	String date = request.getParameter("date");
	String lectureNameText = request.getParameter("lectureNameText");
	String lectureClassText = request.getParameter("lectureClassText");
	String teachername = request.getParameter("teachername");
	String notetitle = request.getParameter("notetitle");
	String tarea = request.getParameter("tarea");

	StudentDAO dao = new StudentDAO();
	
	dao.studentNoteInsert(notetitle, tarea, teachername);

    response.sendRedirect("classNote.jsp");
%>