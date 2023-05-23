<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="DAO.StudentDAO"%>
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
	// upload 디렉토리의 실제 경로 얻어오기
			String saveDir = request.getRealPath("/images");
			out.println(saveDir);
			
			// 첨부파일의 최대 크기 : 30MB 까지 업로드 가능
			// 1M ==> 1024KB
			int maxFileSize = 1024*1024*30;
			
			// 1KB ==> 1024Byte
					
			MultipartRequest mr = new MultipartRequest(request, saveDir,
					maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
	
	
	String teacherId = mr.getParameter("id");
	String teacherPw = mr.getParameter("pw");
	String teacherName = mr.getParameter("name");
	String teacherAddress = mr.getParameter("addrs");
	String teacherPhone = mr.getParameter("phone");
	String teacherEmail = mr.getParameter("email");
	String teacherPhoto = mr.getOriginalFileName("photo");
	String teacherBirth = mr.getParameter("birth");
	String teacherGender = mr.getParameter("gender");
	
	
	
	
	
	
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
	
	
	StudentDAO dao = new StudentDAO();
	dao.teacherInsertByAll(vo);
	
	//session.setAttribute("vo", vo);
	
	%>
	<script type="text/javascript">
		alert("회원가입을 축하합니다");
		location.href = "main.jsp";
	</script>
</body>

</html>