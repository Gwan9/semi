<%@page import="DAO.KHWDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
	String teacherNo = request.getParameter("teacher_no");
		
			if( teacherNo != null ) {
		int teacherNoInt = Integer.parseInt(teacherNo);
		
		KHWDAO dao = new KHWDAO();
		
		dao.deleteTeacher(teacherNoInt);
		
		response.sendRedirect("admin.jsp");
		
			}
	%>
</body>
</html>