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
			<h1>수강납부정보</h1>

			<%
			String lectureNo = request.getParameter("lecture_no");
			
			System.out.println("jsp에서의 값 : " + lectureNo);

			if (lectureNo != null) {
				int lectureNoInt = Integer.parseInt(lectureNo);
				StudentDAO dao = new StudentDAO();
				ClassNoteVO vo = dao.lectureSelectByNo(lectureNoInt);
				
				System.out.println("jsp에서의 값 : " +vo);
			%>
			
			<div id="table">
				<table>
					<tr>
						<th>강의번호</th>
						<th>강의명</th>
						<th>분반</th>
						<th>수강시작일</th>
						<th>수강종료일</th>
						<th>수강료</th>
					</tr>
					<tr>
						<td><%=vo.getLectureNo()%></td>
						<td><%=vo.getLectureName()%></td>
						<td><%=vo.getLectureClass()%></td>
						<td><%=vo.getLectureStartDate()%></td>
						<td><%=vo.getLectureEndDate()%></td>
						<td><%=vo.getLectureTuition()%></td>
					</tr>
				</table>

				<div id="modify_or_cancle">
					<a
						href="lecture_modify.jsp?lecture_no=<%=vo.getLectureNo()%>"><input
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