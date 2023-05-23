
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String teacherName = request.getParameter( "teacherName" );

if( teacherName != null ){
	ClassNoteVO vo = new ClassNoteVO();
	
	vo.setTeacherCheckIn(teacherName);
	StudentDAO dao = new StudentDAO();
	//dao.checkInTeacher(vo);
	
}System.out.println(); 
%>
