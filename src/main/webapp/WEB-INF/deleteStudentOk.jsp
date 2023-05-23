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
		String studentNo = request.getParameter("student_no");
	
		System.out.println(studentNo);
		
		if( studentNo != null ) {
			int studentNoInt = Integer.parseInt(studentNo);
		
			StudentDAO dao = new StudentDAO();
		
			dao.studentDeleteByNo(studentNoInt);
// 			dao.studentDeleteBySubject(studentNoInt);
// 			dao.studentDeleteByDate(studentNoInt);
		
		}
	%>
		<script type="text/javascript">
			
			alert("정상적으로 삭제되었습니다.");
			
			window.location.href="admin.jsp";
			
		</script>
</body>
</html>