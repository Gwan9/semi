

<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.TeacherDAOptm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherLoginOk.jsp</title>
</head>
<body>
	<%
		String teacherId = request.getParameter("id");
		String teacherPw = request.getParameter("pw");
		TeacherDAOptm dao = new TeacherDAOptm();
		// 로그인 성공했다면
		ClassNoteVO vo = dao.getTwo(teacherId, teacherPw);
		if(vo != null)
			session.setAttribute("vo", vo);
		/* 
		else
			session.setAttribute("fail", 1); */
		
		response.sendRedirect("main.jsp");
		
	 %>
</body>
</html>