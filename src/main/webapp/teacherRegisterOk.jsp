

<%@page import="DAO.TeacherDAOptm"%>
<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	String teacherId = request.getParameter("id");
	String teacherPw = request.getParameter("pw");
	String teacherName = request.getParameter("name");
	String teacherAddress = request.getParameter("addrs");
	String teacherPhone = request.getParameter("phone");
	String teacherEmail = request.getParameter("email");
	String teacherPhoto = request.getParameter("photo");
	String teacherBirth = request.getParameter("birth");
	String teacherGender = request.getParameter("gender");
	
	boolean tg;
	
	System.out.println("tg " +teacherGender);
	
	
	if(teacherGender.trim().equals("여"))
		tg = true;
	else
		tg = false;
	
	System.out.println("tg : " +tg);
	
	ClassNoteVO vo = new ClassNoteVO();
	
	vo.setTeacherId(teacherId);
	vo.setTeacherPw(teacherPw);
	vo.setTeacherName(teacherName);
	vo.setTeacherAddress(teacherAddress);
	vo.setTeacherPhone(teacherPhone);
	vo.setTeacherEmail(teacherEmail);
	vo.setTeacherPhoto(teacherPhoto);
	vo.setTeacherBirth(teacherBirth);
	vo.setTeacherGender(tg);
	
	
	TeacherDAOptm dao = new TeacherDAOptm();
	dao.addOne(vo);
	
	
	%>
	<h4>회원가입을 축하합니다.</h4>
</body>

</html>