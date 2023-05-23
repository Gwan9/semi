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

<style type="text/css">

	#container {
		width: 1000px;
		height: 1000px;
		background: #E2E2E2;
		margin: 0px auto;
	}
	
	table {
		width: 700px;
	}
	
	table, th, td {
		border: 1px solid black;
		border-collapse : collapse;
		margin: 0px auto;
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
			<h1>강의정보[수정]</h1>		
		</div>
		
		<%
			String lectureNo = request.getParameter("lecture_no");
		
			System.out.println("jsp에서의 값 : " + lectureNo);

			if (lectureNo != null) {
				int lectureNoInt = Integer.parseInt(lectureNo);
				StudentDAO dao = new StudentDAO();
				ClassNoteVO vo = dao.lectureSelectByNo(lectureNoInt);
				
		%>
		
	<form action="lecture_modify_OK.jsp?lecture_no=<%=vo.getLectureNo()%>">
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
					<td>
					<input type="text" name="lectureNo" id="" value="<%=vo.getLectureNo()%>" />
					</td>
					<td>
					<input type="text" name="lectureName" id="" value="<%= vo.getLectureName() %>" />
					</td>
					<td>
					<input type="text" name="lectureClass" id="" value="<%= vo.getLectureClass() %>" />
					</td>
					<td>
					<input type="date" name="lectureStartDate" id="" value="<%= vo.getLectureStartDate() %> "/>
					</td>
					<td>
					<input type="date" name="lectureEndDate" id="" value="<%= vo.getLectureEndDate() %> "/>
					</td>
					<td>
					<input type="text" name="lectureTuition" id="" value="<%= vo.getLectureTuition() %>" />
					</td>
				</tr>
			</table>
			
			<div id="modify_or_cancle">
				<input type="submit" id="modify_btn" value="저장" />
				<a href="lecture_detail.jsp?lecture_no=<%= vo.getLectureNo() %>"><input type="button" id="cancle_btn" value="뒤로가기" /></a>
			</div>
		</div>
		
		<%
			}
		%>
		</form>
	</div>
</body>
</html>