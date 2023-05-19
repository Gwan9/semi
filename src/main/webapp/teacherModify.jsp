
<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherModify.jsp</title>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}
	#container{
		width: 250px;
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
	<h1>teacherModify</h1>
		<form action="teacherModifyOk.jsp">
			<%
				String n = request.getParameter("no");
				if(n != null){
					int no = Integer.parseInt(n);
					StudentDAO dao = new StudentDAO();
					ClassNoteVO vo = dao.teacherSelectAllByNo(no);
						if(vo != null){
			%>			
			<div id="container">
			
				<div id="center">
					<div>
						<h5>사진</h5>
						<div id="photo"><img src="" alt="증명사진" /></div>
						<div id="findimg"><input type="button" value="파일찾기" /></div>			
					</div>
					<div>
						<h5>아이디</h5>
						<div><%= vo.getTeacherId() %></div>
						<input type="button" value="중복확인" />
					</div>
					<div>
						<h5>비밀번호</h5>
						<div><%= vo.getTeacherPw() %></div>
						<input type="text" name="pw" id="" />
					</div>
					<div>
						<h5>비밀번호 재확인</h5>
						<input type="text" name="" id="" />
					</div>
					<div>
						<h5>이름</h5>
						<input type="text" name="name" id="" />
					</div>
					<div>
						<h5>성별</h5>
						<h5><input type="radio" name="gender" id="" value="남" />남
						<input type="radio" name="gender" id="" value="여" />여</h5>
					</div>
					<div>
						<h5>생년월일</h5>
						<input type="text" name="birth" id="" />
					</div>
					<div>
						<h5>거주지</h5>
						<input type="text" name="addrs" id="" />
					</div>
					<div>
						<h5>연락처</h5>
						<input type="text" name="phone" id="" />
					</div>
					<div>
						<h5>EMAIL</h5>
						<input type="text" name="email" id="" />
						<select name="" id="">
							<option value="직접입력">직접입력</option>
						</select>
					</div>
					<div id="sub">
						<input type="submit" value="가입하기" />
						<input type="button" value="취소하기" />
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