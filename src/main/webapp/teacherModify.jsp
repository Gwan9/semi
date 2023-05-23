<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherModify.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}
	#container{
		width: 280px;
		height: 100%;
		margin: auto;
		background: #F4EEDD;
		
	}
	#photo, #sub{
		text-align: center;
	}
	#findimg{
		text-align: center;
	}
</style>
</head>
<body>
	<%
		String n = request.getParameter("no");
		if(n != null){
			
			int no = Integer.parseInt(n);
			
			StudentDAO dao = new StudentDAO();
			
			ClassNoteVO vo = dao.teacherSelectAllByNo(no);
				
		if(vo != null){
	
	%>			
			
				
	<form action="teacherModifyOk.jsp" method="post" enctype="multipart/form-data">
		<div id="container">
			<div id="center">
				<div>
					<h5>사진</h5>
					<div id="photo"><img src="./images/<%= vo.getTeacherPhoto() %>" alt="" /></div>			
				</div>
				<div>
					<h5>아이디</h5>
					<div><%= vo.getTeacherId() %></div>
				</div>
				<div>
					<h5>이름</h5>
					<div><%= vo.getTeacherName() %></div>
				</div>
				<div>
					<h5>성별</h5>
					<div><% if(vo.isTeacherGender() == false) out.println("남");
							else out.println("여"); %>
					</div>
				</div>
				<div>
					<h5>생년월일</h5>
					<div><%= vo.getTeacherBirth() %></div>
				</div>
				<div>
					<h5>거주지</h5>
					<div><%= vo.getTeacherAddress() %></div>
				</div>
				<div>
					<h5>연락처</h5>
					<div><%= vo.getTeacherPhone() %></div>
				</div>
				<div>
					<h5>EMAIL</h5>
					<div><%= vo.getTeacherEmail() %></div>
				</div>
				<div id = "sub">
					<a href="teacherModifyForm.jsp?no=<%= vo.getTeacherNo() %>">
						<input type="button" value="수정하기" />
					</a>
					<a href="main.jsp">
						<input type="button" value="취소하기" />
					</a>
				</div>	
			</div>
		</div>
	</form>
	<% 
			}
		}
	%>	
</body>
</html>