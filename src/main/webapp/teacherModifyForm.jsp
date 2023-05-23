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
<script type="text/javascript">
	$(function(){	
		$('.pw').on("keyup", function(){
			var pass1 = $("#password_1").val();
			var pass2 = $("#password_2").val();
			
			if(pass1 != "" || pass2 !=""){
				if(pass1 == pass2){
					$("#msg").html("<h6 style='color:blue;'>*비밀번호가 일치합니다</h6>");
					
				}else{
					$("#msg").html("<h6 style='color:red;'>*비밀번호가 불일치합니다</h6>");
					
				}
			}
			
		})
	})
</script>
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
	
	#teacherphoto, #sub{
		text-align: center;
	}
	#findimg{
		text-align: center;
	}
</style>
</head>
<body>
	<div id="container">
		<form action="teacherModifyOk.jsp"  method="post" enctype="multipart/form-data" >
	<%
		String n = request.getParameter("no");
		if(n != null){
			int no = Integer.parseInt(n);
			StudentDAO dao = new StudentDAO();
			ClassNoteVO vo = dao.teacherSelectAllByNo(no);
				if(vo != null){
	
	%>			
			
			<input type="hidden" name="no" value="<%= vo.getTeacherNo() %>"/>
			<div>
				<h5>사진</h5>
				<div id="teacherphoto"><img src="./images/<%= vo.getTeacherPhoto() %>" alt="" /></div>	
				<div id="findimg"><input type="file" name="photo" value="파일찾기" /></div>			
			</div>
			<div>
				<h5>아이디</h5>
				<div><%= vo.getTeacherId() %></div>
			</div>
			<div>
				<h5>비밀번호</h5>
				<input type="password" name="pw" class="pw" id="password_1" value="<%= vo.getTeacherPw() %>" />
				<h5>비밀번호 재확인</h5>
				<input type="password" name="pw2" class="pw" id="password_2" />
				<div id="msg"></div>
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
				<input type="text" name="phone" id="" value="<%= vo.getTeacherPhone() %>" />
			</div>
			<div>
				<h5>EMAIL</h5>
				<input type="text" name="email" id="" value="<%= vo.getTeacherEmail() %>" />
			</div>
			<div id="sub">
				<input type="submit" value="수정하기" />
				<a href="main.jsp">
					<input type="button" value="취소하기" />
				</a>
			</div>	
		</form>
	<% 
			}
		}
	%>	
	</div>
</body>
</script>
</html>