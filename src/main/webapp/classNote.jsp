<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학습일지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="semi.css">

<style>
#add {
	position: relative;
	font-size: 15px;
	left: 400px;
	padding: 5px 10px;
}
.sideMenu4{
	background:#FFCA2C;
	color:#686868;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#lectureClass, #lectureName").on("change",function() {
			var lectureClass = $("#lectureClass").val();
			var lectureName = $("#lectureName").val();
			$.ajax({
				url : "classNoteList.jsp",
				data : {
					"lectureClass" : lectureClass,
					"lectureName" : lectureName
					},
					success : function(data) {
						var obj = JSON.parse(data);
						$("table").empty(); // 기존 테이블 내용 비우기
						for (var i = 0; i < obj.length; i++) {
							var txt = "<tr><td>"
							+ obj[i].noteno
							+ "</td><td>"
							+ obj[i].lecturename
							+ "</td><td>"
							+ obj[i].lectureclass
							+ "</td><td>"
							+ obj[i].studentname
							+ "</td><td>"
							+ "<a href='classNoteDetail.jsp?NoteNo="+ obj[i].noteno + "'>"
							+ obj[i].notetitle
							+ "</a>" + "</td><td>"
							+ obj[i].teachername
							+ "</td><td>"
							+ obj[i].notedate
							+ "</td></tr>";
							$("table").append(txt);
						}
					}
				});
			});
		$.ajax({
			url : "classNoteLectureList.jsp",
			success : function(data) {
				var obj = JSON.parse(data);
				for (var i = 0; i < obj.length; i++) {
					var txt = "<option value='" + obj[i].lname + "'>"+ obj[i].lname + "</option>";
					$("#lectureName").append(txt);
				}
			}
		})
		$("#add").on("click", function() {
			location.href = "classNoteWriter.jsp"
		})

		$("#stdSelect").on("click",function() {
			$("table").empty();
			$.ajax({
				url : "classNoteStudentList.jsp",
				data : {
					"studentName" : $("#studentName").val()
				},
				success : function(data) {
					var obj = JSON.parse(data);
					for (var i = 0; i < obj.length; i++) {
						var txt = "<tr><td>"
						+ obj[i].noteno
						+ "</td><td>"
						+ obj[i].lecturename
						+ "</td><td>"
						+ obj[i].lectureclass
						+ "</td><td>"
						+ obj[i].studentname
						+ "</td><td>"
						+ "<a href='classNoteDetail.jsp?NoteNo="+ obj[i].noteno + "'>"
						+ obj[i].notetitle
						+ "</td><td>"
						+ obj[i].teachername
						+ "</td><td>"
						+ obj[i].notedate
						+ "</td></tr>";
						$("table").append(txt);
					}
				}
			})
		})
	})
</script>
</head>
<body>
	<%
	// 	로그인 확인 절차
	// 	Object obj = session.getAttribute("vo");
	// 	if(obj == null)
	// 		response.sendRedirect("../main.jsp");

	StudentDAO dao = new StudentDAO();

	int totalCount = dao.getTotalCount();

	// 	한 페이지당 게시물 건수 : 20
	int recordPerPage = 20;
	int totalPage = (totalCount % recordPerPage == 0) ? totalCount / recordPerPage : totalCount / recordPerPage + 1;

	String cp = request.getParameter("cp");

	int currentPage = 0;

	if (cp != null)
		currentPage = Integer.parseInt(cp);
	else
		currentPage = 1;

	int startPage = 1;
	int endPage = totalPage;

	if (currentPage <= 5)
		startPage = 1;
	else if (currentPage >= 6)
		startPage = currentPage - 4;

	if (totalPage - currentPage <= 5)
		endPage = totalPage;
	else if (totalPage - currentPage > 5)
		if (currentPage <= 5) {
			if (totalPage > 10) {
		endPage = 10;
			} else {
		endPage = totalPage;
			}
		} else
			endPage = currentPage + 4;
	%>
	<div id="canvas">
		<div class="sideMenu1">학생목록</div>
		<div class="sideMenu2">출결관리</div>
		<div class="sideMenu3">수업관리</div>
		<div class="sideMenu4">학습일지</div>
		<div class="sideMenu5">관리자</div>

		<div id="container">
			<!-- 학습일지 큰 div -->
			<div id="student1">
				<h5>학생 정보 조회</h5>
				<hr />
				<select name="lectureClass" id="lectureClass">
					<!-- 분반선택 콤보박스 -->
					<option value="전체">분반</option>
					<option value="A">A반</option>
					<option value="B">B반</option>
					<option value="C">C반</option>
				</select> <select name="lectureName" id="lectureName">
					<option value="전체">강의명</option>
				</select> <label for="">이름 : </label> <input type="text" name="studentName" id="studentName" />

				<button class="btn btn-warning btn-xs" type="button" id="stdSelect">조회</button>
				<br> 
				<input type="date" name="" id="date" /> 
				<label for="">부터 </label> 
				<input type="date" name="" id="date" /> 
				<label for="">까지 </label>
				<button type="button" id="add" class="btn btn-warning btn-xs">등록</button>
			</div>
			<div id="student2">
				<table class="tableCss">
					<thead>
						<tr>
							<th>일지번호</th>
							<th>강의명</th>
							<th>분반</th>
							<th>학생명</th>
							<th>일지제목</th>
							<th>강사명</th>
							<th>작성일시</th>
						</tr>
					</thead>
					<tbody>
						<%
						int startno = (currentPage - 1) * recordPerPage + 1;
						int endno = currentPage * recordPerPage;

						ArrayList<ClassNoteVO> list = dao.studentNoteSelectAll(startno, endno);
						for (ClassNoteVO vo : list) {
						%>
						<tr>
							<td><%=vo.getNoteNo()%></td>
							<td><%=vo.getLectureName()%></td>
							<td><%=vo.getLectureClass()%></td>
							<td><%=vo.getStudentName()%></td>
							<td><a href="classNoteDetail.jsp?NoteNo=<%=vo.getNoteNo()%>"> <%=vo.getNoteTitle()%>
							</a></td>
							<td><%=vo.getTeacherName()%></td>
							<td><%=vo.getNoteDate()%></td>
						</tr>

						<%
						}
						%>
					</tbody>
				</table>
			</div>

		</div>
	</div>
</body>
</html>