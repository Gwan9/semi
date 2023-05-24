<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacher_detail.jsp</title>
<link rel="stylesheet" href="semi.css">

<style type="text/css">

table {
	width: 800px;
	font-size: 70%;
	border-collapse: collapse;
	text-align: left;
	margin-left: 1px;
}

th {
	/* width: 130px; */
	padding: 7px;
	font-weight: bold;
	color: white;
	/* vertical-align: top; */
	background: #D8D9DB;
}

td {
	width: 20px;
	padding: 7px;
	border-bottom: 1px solid white;
	background-color: #F3F6F7;
	color: #686868;

}

#img {
	width: 200px;
	height: 230px;
	border: 1px solid black;
	margin-left: 40%;
	margin-bottom: 60px;
}

#photo {
	width: 200px;
	height: 230px;
}

#title {
	margin-top: 130px;
	text-align: center;
}

#modify_or_cancle {
	text-align: right;
	margin-right: 90px;
	margin-top: 20px;
}

#modify_btn, #cancle_btn {
	width: 80px;
	height: 40px;
	margin-bottom: 30px;
}
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">

	$(function() {
		$("#cancle_btn").on("click", function() {
			window.location.href = "admin.jsp";
		})
	})	

</script>

</head>
<body>
	<div id="container">
		<div id="title">
			<h1>납부정보[수정]</h1>		
		</div>
		
		<%
			String classRegisterNo = request.getParameter("class_register_no");
		
			System.out.println("jsp에서의 값 : " + classRegisterNo);

			if (classRegisterNo != null) {
				int classRegisterNoInt = Integer.parseInt(classRegisterNo);
				StudentDAO dao = new StudentDAO();
				ClassNoteVO vo = dao.lectureSelectByNo(classRegisterNoInt);
				
		%>
		
	<form action="register_modify_OK.jsp?class_register_no=<%=vo.getClass_registerNo()%>">
		<div id="table">
			<table>
				<tr>
					<th>수강번호</th>
					<th>강사번호</th>
					<th>강의번호</th>
					<th>학생번호</th>
					<th>납부여부</th>
				</tr>
				<tr>
					<td>
					<input type="text" name="classRegisterNo" id="" value="<%=vo.getClass_registerNo()%>" />
					</td>
					<td>
					<input type="text" name="teacherNo" id="" value="<%=vo.getTeacherNo()%>" />
					</td>
					<td>
					<input type="text" name="lectureNo" id="" value="<%=vo.getLectureNo()%>" />
					</td>
					<td>
					<input type="text" name="studentNo" id="" value="<%=vo.getStudentNo()%>" />
					</td>
					<td>
						<label><input type="radio" name="isPay" id="isPay" value="<%=vo.isPay()%>" />납부</label> 
						<label><input type="radio" name="isPay" id="isPay" value="<%=vo.isPay()%>" />미납</label> 					
					</td>
				</tr>
			</table>
			
			<div id="modify_or_cancle">
				<input type="submit" id="modify_btn" value="저장" />
				<a href="register_detail.jsp?class_register_no=<%= vo.getClass_registerNo() %>"><input type="button" id="cancle_btn" value="뒤로가기" /></a>
			</div>
		</div>
		
		<%
			}
		%>
		</form>
	</div>
</body>
</html>