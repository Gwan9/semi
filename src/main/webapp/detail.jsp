
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detail.jsp</title>


<style>
* {
	margin: auto;
	padding: 0;
}

#container {
	width: 500px;
	height: 800px;
	/* background-color: #C7C7CA;  */
	border-radius: 13px;
	border: 5px solid #FFCA2C;
}

h3{
color: #212529;
padding-left: 13px;
padding-top: 10px;
}
/* table, td {
	width: 500px;
	border: 1px solid black;
	border-collapse: collapse;
} */

table.info{
border-collapse: collapse;
text-align: left;
line-height: 1.5;
margin-left:15px;
}

table.info th{
width: 130px;
padding:10px;
font-weight: bold;
/* vertical-align: top; */
background: #f3f6f7;
 border-bottom: 1px solid #ccc;
}

table.info td{
width:280px;
padding:10px;
border-bottom: 1px solid  #ccc;

}

#profile{
/*  position:relative;
left:150px;  */
margin-left:150px;
width: 150px;
height: 150px;
border-radius: 70%; /* 프로필 사진 원모양으로 */
overflow:hidden; /* 넘치는 부분은 숨기기 */
}

#photo {
	width: 100%;
	height:100%;
	object-fit:cover; /* 이미지를 div에 꽉채우기! */
}

#back {
position:relative;
left: 450px;
top: 15px;
	width: 30px;
	height: 30px;
}
</style>


<!-- 팝업창을 띄우기 위한 제이쿼리 cdn -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">
/* 팝업창 */
$(function () {
	$("a").on("click", function () {
		window.close();
	})
})
</script>
</head>
<body>


	<%
	//전달받은 파라미터 값 가져오기
	String studentName = request.getParameter("studentName");
	//out.println(studentName);

	if (studentName != null) {

		StudentDAO dao = new StudentDAO();

		ArrayList<ClassNoteVO> list = dao.studentSearchSelectAllByNameToDetail(studentName);

		//for (ClassNoteVO vo : list) {

		//out.println(list.get(0));
	%>

	<div id="container">

		<h3>원생 상세정보</h3>
		<br />
		<hr />
		<br />

		<div id="profile">
			<img id="photo" src="<%=list.get(0).getStudentPhoto()%>" alt="" />
		</div>
<br />
		<table class="info">
			<!--  학생의 개인 인적사항이 나오면 좋지 않을까 -->
			<tr>
				<th>이름</th>
				<td><%=list.get(0).getStudentName()%></td>
			</tr>
			<tr>
				<th>성별</th>
				<td><%=list.get(0).isStudentGender() == true ? "남" : "여"%></td>
			</tr>
			<tr>
				<th>생일</th>
				<%-- <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
				<td><%=sdf.format(list.get(0).getStudentBirth())%></td> --%>
				<td><%=list.get(0).getStudentBirth().substring(0, 8)%></td>

			</tr>
			<tr>
				<th>주소</th>
				<td><%=list.get(0).getStudentAddrs()%></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><%=list.get(0).getStudentEmail()%></td>
			</tr>
			<tr>
				<th>학교</th>
				<td><%=list.get(0).getStudentSchoolName()%></td>
			</tr>
			<tr>
				<th>학년</th>
				<td><%=list.get(0).getStudentGrade()%></td>
			</tr>
			<tr>
				<th>학생 연락처</th>
				<td><%=list.get(0).getStudentPhone()%></td>
			</tr>
			<tr>
				<th>학부모명</th>
				<td><%=list.get(0).getStudentParentsName()%></td>
			</tr>
			<tr>
				<th>학부모 연락처</th>
				<td><%=list.get(0).getStudentParentsPhone()%></td>
			</tr>
			<tr>
				<th>재원 상태</th>
				<td><%=list.get(0).isStudentStatus() ? "재원" : "퇴원"%></td>
			</tr>
		</table>
		
		<a href="studentList.jsp"><img
			src="https://cdn-icons-png.flaticon.com/512/0/340.png"
			alt="뒤로가기버튼이미지" id="back" /></a>

		<%
		}
		%>
	</div>
</body>
</html>
