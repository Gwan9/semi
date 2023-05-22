<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>print.jsp</title>

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
	
<style>

#container{
width: 1080px;
margin: auto;

}
#list{
margin-top:30px;
width: 800px;

}

#printBtn{
position: relative;
left: 680px;
}

#back {
position:relative;
left: 700px;
top: 0px;
	width: 30px;
	height: 30px;
}
</style>
</head>

<body>

<!-- 정렬 맞추지마 >> 맞추니까 동작안해 -->
	<%
	//전달받은 파라미터 값 가져오기
String selectedList = request.getParameter("selectedList");
	//System.out.println(selectedListStr);
 
	if (selectedList != null) {
	%>

<div id="container">

<div id="list">

	<script>
		var selectedList = '<%= selectedList %>';
		// selectedList을 JavaScript 객체로 변환
		var selectedListObj = JSON.parse(selectedList);

		// 선택된 학생 정보를 테이블로 출력
		//thead
		var tableHtml = "<table class='table table-dark table-striped table-hover'>";
		tableHtml += "<tr>";
		tableHtml += "<th>학생명</th>";
		tableHtml += "<th>학교명</th>";
		tableHtml += "<th>학년</th>";
		tableHtml += "<th>반</th>";
		tableHtml += "<th>학생 연락처</th>";
		tableHtml += "<th>등록일</th>";
		tableHtml += "<th>성별</th>";
		tableHtml += "<th>학부모명</th>";
		tableHtml += "<th>학부모 연락처</th>";
		tableHtml += "</tr>"; 
		
		//tbody
		for (var i = 0; i < selectedListObj.length; i++) {
			var student = selectedListObj[i];
			
			tableHtml += "<tr>";
			tableHtml += "<td>" + student.studentName + "</td>";
			tableHtml += "<td>" + student.studentSchoolName + "</td>";
			tableHtml += "<td>" + student.studentGrade + "</td>";
			tableHtml += "<td>" + student.lectureClass + "</td>";
			tableHtml += "<td>" + student.studentPhone + "</td>";
			tableHtml += "<td>" + student.studentRegistDate + "</td>";
			tableHtml += "<td>" + (student.studentGender ? "남" : "여") + "</td>";
			tableHtml += "<td>" + student.studentParentsName + "</td>";
			tableHtml += "<td>" + student.studentParentsPhone + "</td>";
			tableHtml += "</tr>";
		}
		tableHtml += "</table>";

		// 생성한 테이블을 어딘가에 추가하거나 출력
		document.write(tableHtml);
		
	</script>

</div>
<button type="button" class="btn btn-dark custom-btn-xs"
					id="printBtn" onclick="window.print()">인쇄</button>

	<% } %>
<a href="studentList.jsp"><img
			src="https://cdn-icons-png.flaticon.com/512/0/340.png"
			alt="뒤로가기버튼이미지" id="back" /></a>
</div>
	
</body>
</html>