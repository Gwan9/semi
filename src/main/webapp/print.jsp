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
</head>
<body>
	<h2>인쇄 페이지</h2>


<!-- 정렬 맞추지마 >> 맞추니까 동작안해 -->
	<%
	//전달받은 파라미터 값 가져오기
String selectedListStr = request.getParameter("selectedList");
	//System.out.println(selectedListStr);
 if (selectedListStr != null) {


	%>

	<script>
		var selectedListStr = '<%= selectedListStr %>';
		// selectedListStr을 JavaScript 객체로 변환
		var selectedListObj = JSON.parse(selectedListStr);

		// 선택된 학생 정보를 테이블로 출력
		var tableHtml = "<table>";
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

<input type="button" value="인쇄" onclick="window.print()" />
	<% } %>


	
</body>
</html>