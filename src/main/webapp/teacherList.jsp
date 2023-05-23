<%@page import="java.util.ArrayList"%>
<%@page import="VO.studentVO"%>
<%@page import="DAO.TeacherDAOsgh"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	TeacherDAOsgh tdao = new TeacherDAOsgh();
	studentVO vo = new studentVO();
	
	ArrayList<studentVO> list = tdao.teacherSelectAll();
	

%>