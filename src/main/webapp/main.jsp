
<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MainPage</title>

<link rel="stylesheet" href="./cssteacher/main.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		var btn = document.getElementById("loginbtn");
		btn.onclick = function(){
			console.log("test");
			var frm = document.frm;
			//입력값 유효성 검사
			
			frm.action = "teacherLoginOk.jsp";
			frm.method = "get";
			frm.submit();
		}
	}
</script>
</head>
<body>
	<%
		Object obj = session.getAttribute("vo");
		if(obj != null){ //로그인 되어 있는 상태
			ClassNoteVO vo = (ClassNoteVO)obj;
	%>
			<div id="container">
			
				<jsp:include page="bannerside.jsp" />
				
				<div id="loginside">
					<form action="teacherModify.jsp" name="frm" method="get">
						<input type="hidden" name="no" value="<%= vo.getTeacherNo() %>" />
						<table>
							<tr>
								<th><%= vo.getTeacherName() %>님 환영합니다</th>
							</tr>
							<tr>
								<td colspan="2">
									<input type="submit" value="개인정보수정" /> </a>
									<a href="logout.jsp">
									<input type="button" value="로그아웃" id="logoutbtn" />	</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<jsp:include page="leftside.jsp" />
			
				<jsp:include page="rightside.jsp" />
				
			</div>		
	<% 
			}else{	
	%>			
			<div id="container">
			
				<jsp:include page="bannerside.jsp" />
				
				<div id="loginside">
					<form action="teacherLoginOk.jsp" name="frm" method="get">
						<table>
							<tr>
								<th>ID</th>
								<td><input type="text" name="id" id="id" /></td>
							</tr>
							<tr>
								<th>PW</th>
								<td><input type="password" name="pw" id="" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<input type="button" value="로그인" id="loginbtn" />
									<a href="teacherRegister.jsp">
									<input type="button" value="회원가입" id="registerbtn" />	</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				
				<jsp:include page="leftside.jsp" />
			
				<jsp:include page="rightside.jsp" />
				
			</div>
				
		<% } %>			
</body>
</html>