<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="VO.ClassNoteVO"%>
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


<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

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
			<h1>학생정보</h1>

			<%
			String studentNo = request.getParameter("student_no");
			
			System.out.println("jsp에서의 값 : " + studentNo);

			if (studentNo != null) {
				int studentNoInt = Integer.parseInt(studentNo);
				StudentDAO dao = new StudentDAO();
				ClassNoteVO vo = dao.studentSearchSelectByNo(studentNoInt);
				
				System.out.println("jsp에서의 값 : " +vo);
			%>

			<div id="img">
				<img src="<%=vo.getStudentPhoto() %>" alt="" name="photo" id="photo" />
			</div>


			<div id="table">
				<table>
					<tr>
						<th>학생번호</th>
						<td colspan="2"><%=vo.getStudentNo()%></td>
						<th>이름</th>
						<td colspan="2"><%=vo.getStudentName()%></td>
					</tr>
					<tr>
						<th>학년</th>
						<td><%=vo.getStudentGrade()%></td>
						<th>학교명</th>
						<td><%=vo.getStudentSchoolName()%></td>
						<th>연락처</th>
						<td><%=vo.getStudentPhone()%></td>
					</tr>
					<tr>
						<th>성별</th>
						<td><%=vo.isStudentGender()%></td>
						<th>이메일</th>
						<td><%=vo.getStudentEmail()%></td>
						<th>생년월일</th>
						<td><%=vo.getStudentBirth()%></td>
					</tr>
					<tr>
						<th>등록일</th>
						<td><%=vo.getStudentRegistDate()%></td>
						<th>학부모 이름</th>
						<td><%=vo.getStudentParentsName()%></td>
						<th>학부모 연락처</th>
						<td><%=vo.getStudentParentsPhone()%></td>
					</tr>
					<tr>
						<th>등록상태</th>
						<td><%=vo.isStudentStatus()%></td>
						<th>주소지</th>
						<td colspan="3"><%=vo.getStudentAddrs()%></td>
					</tr>
				</table>

				<div id="modify_or_cancle">
					<a
						href="student_modify_form.jsp?student_no=<%=vo.getStudentNo()%>"><input
						type="button" id="modify_btn" value="수정" /></a> <input type="button"
						id="cancle_btn" value="뒤로가기" />
				</div>
			</div>
			<%
			}
			%>

		</div>
	</div>
</body>
</html>