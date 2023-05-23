<%@page import="DAO.StudentDAO"%>
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
	
		System.out.println(teacherNo);
		
		if( teacherNo != null ) {
			int teacherNoInt = Integer.parseInt(teacherNo);
		
			StudentDAO dao = new StudentDAO();
		
			dao.teacherDeleteByNo(teacherNoInt);
			dao.teacherDeleteBySubject(teacherNoInt);
			dao.teacherDeleteByDate(teacherNoInt);
		
		}
	%>
		<script type="text/javascript">
			
			alert("정상적으로 삭제되었습니다.");
			
			window.location.href="admin.jsp";
			
		</script>
</body>
</html>