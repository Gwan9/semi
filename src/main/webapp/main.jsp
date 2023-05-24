<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MainPage</title>
<link rel="stylesheet" href="semi.css">
<link rel="stylesheet" href="mainpage.css">
<!-- bootstrap CDN -->
<!-- css -->
<link
   href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
   rel="stylesheet"
   integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
   crossorigin="anonymous">
<script
   src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
   integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
   crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
// 			$("#test").on("click", function(){
// 				console.log("출력 버튼 눌림");
// 				$.ajax({
// 					url:"classNoteTitle.jsp",
// 					success:function(data){
// 		 				var data2 = JSON.parse(data);
// 		 				console.log(data2);
// 		 				for(i = 0; i < data2.length; i++){
// 		 					var txt = data2[i].NoteTitle+"</br>";
// 		 					$("#todayclassul").append(txt);
// 		 				}
// 					}
// 				});
// 			})
		$("#loginbtn").on("click", function(){
				console.log("test");
				var frm = document.frm;
				//입력값 유효성 검사
				
				frm.action = "teacherLoginOk.jsp";
				frm.method = "get";
				frm.submit();
		})
		
	})
</script>
<style>
	.contents{
		margin-top:35px;
		margin-left:25px;
		width: 400px;
		height: 250px;
		background: #F4EEDD;
	}
	
	#leftside{
		width: 450px;
		height: 700px;
		float: left;
	}
	#rightside{
		width: 450px;
		height: 700px;
		float: right;
	}
	ul{
		list-style-type: none;
	}
</style>
</head>
<body>
<script>
$(function(){
	$("#bannerside").on("click", function(){
		location.href = "main.jsp";
	})	
})

</script>
	<%
		Object obj = session.getAttribute("vo");
		if(obj != null){ //로그인 되어 있는 상태 확인
			ClassNoteVO vo = (ClassNoteVO)obj;
			
			session.setAttribute("vo", vo);
	%>
			<div id="header">
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
									<input type="submit" value="개인정보수정" />
									<a href="logout.jsp">
									<input type="button" value="로그아웃" id="logoutbtn" /></a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
				
		<div id="canvas">
			<a href="studentlist.jsp"><div class="sideMenu1">학생목록</div></a>
			<a href=""><div class="sideMenu2">출결관리</div></a>
			<a href="studentCal.jsp"><div class="sideMenu3">수업관리</div></a>
			<a href="classNote.jsp"><div class="sideMenu4">학습일지</div></a>
			<a href="admin.jsp"><div class="sideMenu5">관리자</div></a>
			<div id="container">
				<div id="leftside">
					<div class="contents" id="notice">
						<ul>
							<li>공지1</li>
							<li>공지2</li>
							<li>공지3</li>
						</ul>
					</div>
					
					<div class="contents" id="classnote">
						<ul>
							<li>학습일지1</li>
							<li>학습일지2</li>
							<li>학습일지3</li>
						</ul>
					</div>
				</div>
			
				<div id="rightside">
					<div class="contents" id="todayclass">
						<ul>
							<li>오늘의강의1</li>
							<li>오늘의강의2</li>
							<li>오늘의강의3</li>
						</ul>
					</div>
					<div class="contents" id="birthday">
						<ul>
							<li>생일1</li>
							<li>생일2</li>
							<li>생일3</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
					
	<% 
			}else{	
	%>			
			<div id="header">
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
									<input type="button" value="회원가입" id="registerbtn" /></a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		<div id="canvas">
			<a href="studentlist.jsp"><div class="sideMenu1">학생목록</div></a>
			<a href=""><div class="sideMenu2">출결관리</div></a>
			<a href="studentCal.jsp"><div class="sideMenu3">수업관리</div></a>
			<a href="classNote.jsp"><div class="sideMenu4">학습일지</div></a>
			<a href="admin.jsp"><div class="sideMenu5">관리자</div></a>
			<div id="container">
				<div id="leftside">
					<div class="contents" id="notice">
						<ul>
							<li>공지1</li>
							<li>공지2</li>
							<li>공지3</li>
						</ul>
					</div>
					
					<div class="contents" id="classnote">
						<ul>
							<li>학습일지1</li>
							<li>학습일지2</li>
							<li>학습일지3</li>
						</ul>
					</div>
				</div>
			
				<div id="rightside">
					<div class="contents" id="todayclass">
						<ul >
							<li>오늘의강의1</li>
							<li>오늘의강의2</li>
							<li>오늘의강의3</li>
						</ul>
					</div>
					
					<div class="contents" id="birthday">
						<ul>
							<li>생일1</li>
							<li>생일2</li>
							<li>생일3</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
				
		<% } %>			
</body>
</html>