<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String teacherName = request.getParameter("teacherName").trim();
	StudentDAO dao = new StudentDAO();
	ClassNoteVO vo = new ClassNoteVO(); // vo 객체 생성
	vo.setTeacherName(teacherName); // vo 객체에 teacherName 값 설정
	dao.teacherCheckInUpdateByName(vo);
	dao.teacherCheckOutUpdateByName(vo);
	dao.teacherWorkTimeUpdateByName(vo);
	System.out.println(vo); 
%>
