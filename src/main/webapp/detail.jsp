<%@page import="VO.ClassNoteVOjsb"%>
<%@page import="DAO.StudentDAOjsb"%>
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
#container {
	width: 1080px;
	/* background-color: gray; */
}

table,td {
	width: 500px;
	border: 1px solid black;
	border-collapse: collapse;
}

img {
	width: 150px;
	height: 180px;
}
</style>
</head>
<body>


	<%
	//전달받은 파라미터 값 가져오기
		String studentName = request.getParameter("studentName");
		//out.println(studentName);

		if (studentName != null) {
			
			StudentDAOjsb dao = new StudentDAOjsb();

			ArrayList<ClassNoteVOjsb> list = dao.selectAll(studentName);

			//for (ClassNoteVO vo : list) {
		
			//out.println(list.get(0));
	%>

	<div id="container">

		<h2>상세 개인정보</h2>
		
		  <div id="photo">
			<img src="<%=list.get(0).getStudentPhoto()%>" alt="" />
		</div>  

		<table>
			<!--  학생의 개인 인적사항이 나오면 좋지 않을까 -->
			<tr>
				<td>이름</td>
				<td><%=list.get(0).getStudentName()%></td>
			</tr>
			<tr>
				<td>성별</td>
				<td><%=list.get(0).isStudentGender() == true ? "남" : "여"%></td>
			</tr>
			<tr>
				<td>생일</td>
				<%-- <% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
				<td><%=sdf.format(list.get(0).getStudentBirth())%></td> --%>
				<td><%=list.get(0).getStudentBirth().substring(0,10)%></td>
				
			</tr>
			<tr>
				<td>주소</td>
				<td><%=list.get(0).getStudentAddrs()%></td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><%=list.get(0).getStudentEmail()%></td>
			</tr>
			<tr>
				<td>학교</td>
				<td><%=list.get(0).getStudentSchoolName()%></td>
			</tr>
			<tr>
				<td>학년</td>
				<td><%=list.get(0).getStudentGrade()%></td>
			</tr>
			<tr>
				<td>학생 연락처</td>
				<td><%=list.get(0).getStudentPhone()%></td>
			</tr>
			<tr>
				<td>학부모명</td>
				<td><%=list.get(0).getStudentParentsName()%></td>
			</tr>
			<tr>
				<td>학부모 연락처</td>
				<td><%=list.get(0).getStudentParentsPhone()%></td>
			</tr>
			<tr>
				<td>재원 상태</td>
			 	<td><%=list.get(0).isStudentStatus() ? "재원" : "퇴원" %></td> 
			</tr> 

		</table>

		<%
		}
		%>
	</div>
</body>
</html>