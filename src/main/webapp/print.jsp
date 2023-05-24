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
#printList{
margin-top:30px;
width: 800px;
}


/* 테이블 전체 */
table.tableCss {
	width: 800px;
	font-size: 70%;
	border-collapse: collapse;
	text-align: left;
	margin-left: 1px;
}

/* 테이블 헤드 */
table.tableCss th {
	/* width: 130px; */
	padding: 7px;
	font-weight: bold;
	color: white;
	/* vertical-align: top; */
	background: #D8D9DB;
}

/* 테이블 내용 부분 */
table.tableCss td {
	width: 20px;
	padding: 7px;
	border-bottom: 1px solid white;
	background-color: #F3F6F7;
	color: #686868;
}


#printBtn {
	position: relative;
	left: 690px;
	bottom: -20px;
	background-color: #A6A9AE;
	border: 1px solid white;
}

#back {
position:relative;
left: 710px;
top: 20px;
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

<div id="printList">

	<script>
		var selectedList = '<%= selectedList %>';
		// selectedList을 JavaScript 객체로 변환
		var selectedListObj = JSON.parse(selectedList);

		// 선택된 학생 정보를 테이블로 출력
		//thead
		var tableHtml = "<table class='tableCss'>";
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
			src="https://media.istockphoto.com/id/1452220399/ko/%EB%B2%A1%ED%84%B0/%EB%92%A4%EB%A1%9C-%ED%99%94%EC%82%B4%ED%91%9C-%EC%95%84%EC%9D%B4%EC%BD%98%EC%9E%85%EB%8B%88%EB%8B%A4-%EC%99%BC%EC%AA%BD%EC%9C%BC%EB%A1%9C-%EB%8F%8C%EC%95%84%EA%B0%80%EA%B8%B0-%EB%B2%A1%ED%84%B0-%EA%B7%B8%EB%A6%BC.jpg?s=612x612&w=0&k=20&c=OIiES0-dasbonq_cxTn0oGuycE4b_sLr63HrY3yglVw="
			alt="뒤로가기버튼이미지" id="back" /></a>
</div>
	
</body>
</html>