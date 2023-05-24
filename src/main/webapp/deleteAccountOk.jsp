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
		String classregisterNo = request.getParameter("classregisterDeleteByNo");
	
		System.out.println(classregisterNo);
		
		if( classregisterNo != null ) {
			int classregisterNoInt = Integer.parseInt(classregisterNo);
		
			StudentDAO dao = new StudentDAO();
		
			dao.classregisterDeleteByNo(classregisterNoInt);
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