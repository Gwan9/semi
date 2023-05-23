
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
// 	window.onload = function(){
// 		var btn = document.getElementById("loginbtn");
	$(function(){
		$("#loginbtn").on("click", function(){
				console.log("test");
				var frm = document.frm;
				//입력값 유효성 검사
				
				frm.action = "teacherLoginOk.jsp";
				frm.method = "get";
				frm.submit();
			
		})
		
		$("#bannerside").on("click", function(){
			location.href = "main.jsp";
		})
	})
// 	}
</script>
</head>
<body>
	<%
		Object obj = session.getAttribute("vo");
		if(obj != null){ //로그인 되어 있는 상태 확인
			ClassNoteVO vo = (ClassNoteVO)obj;
			
			session.setAttribute("vo", vo);
	%>
			<div id="container">
			
				<div id="bannerside">
					<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTidgN85-7IXfj0yaEUlldBQ8OCjxO7Jhqbbw&usqp=CAU" alt="banner" />
				</div>
				
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
				
				<div id="leftside">
					<div id="notice">
						<ul>
							<li>공지1</li>
							<li>공지2</li>
							<li>공지3</li>
						</ul>
					</div>
					
					<div id="classnote">
						<ul>
							<li>학습일지1</li>
							<li>학습일지2</li>
							<li>학습일지3</li>
						</ul>
					</div>
				</div>
			
				<div id="rightside">
					<div id="todayclass">
						<ul>
							<li>오늘의강의1</li>
							<li>오늘의강의2</li>
							<li>오늘의강의3</li>
						</ul>
					</div>
					<div id="birthday">
						<ul>
							<li>생일1</li>
							<li>생일2</li>
							<li>생일3</li>
						</ul>
					</div>
				</div>
				
			</div>		
	<% 
			}else{	
	%>			
			<div id="container">
			
				<div id="bannerside">
					<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTidgN85-7IXfj0yaEUlldBQ8OCjxO7Jhqbbw&usqp=CAU" alt="banner" />
				</div>
				
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
				
				<div id="leftside">
					<div id="notice">
						<ul>
							<li>공지1</li>
							<li>공지2</li>
							<li>공지3</li>
						</ul>
					</div>
					
					<div id="classnote">
						<ul>
							<li>학습일지1</li>
							<li>학습일지2</li>
							<li>학습일지3</li>
						</ul>
					</div>
				</div>
			
				<div id="rightside">
					<div id="todayclass">
						<ul>
							<li>오늘의강의1</li>
							<li>오늘의강의2</li>
							<li>오늘의강의3</li>
						</ul>
					</div>
					
					<div id="birthday">
						<ul>
							<li>생일1</li>
							<li>생일2</li>
							<li>생일3</li>
						</ul>
					</div>
				</div>
				
			</div>
				
		<% } %>			
</body>
</html>