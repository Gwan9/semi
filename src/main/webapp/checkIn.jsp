
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.TeacherDAOsgh"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String teacherName = request.getParameter( "teacherName" );

if( teacherName != null ){
	ClassNoteVO vo = new ClassNoteVO();
	
	vo.setTeacherCheckIn(teacherName);
	TeacherDAOsgh dao = new TeacherDAOsgh();
	//dao.checkInTeacher(vo);
	
}System.out.println();
%>
