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

/* background-color: #C7C7CA; */

border-radius: 13px;

border: 5px solid #FFCA2C;

}

/* 원생 상세 정보 텍스트 */

h3 {

color: #FFCA2C;

padding-left: 13px;

padding-top: 10px;

font-size: 130%;

}

hr {

border: 1.7px solid #D8D9DB;

width: 95%;

}

table.info {

border-collapse: collapse;

text-align: left;

line-height: 1.5;

margin-left: 15px;

}

table.info th {

width: 130px;

padding: 10px;

font-weight: bold;

/* vertical-align: top; */

background: #F3F6F7;

color: #7A9BBA;

border-bottom: 1px solid #ccc;

}

table.info td {

width: 280px;

padding: 10px;

border-bottom: 1px solid #ccc;

color: #686868;

}

#profile {

/* position:relative;

left:150px; */

margin-left: 150px;

width: 150px;

height: 150px;

border-radius: 70%; /* 프로필 사진 원모양으로 */

overflow: hidden; /* 넘치는 부분은 숨기기 */

}

#photo {

width: 100%;

height: 100%;

object-fit: cover; /* 이미지를 div에 꽉채우기! */

}

#back {

position: relative;

left: 450px;

top: 12px;

width: 30px;

height: 30px;

}

</style>

<!-- 팝업창을 띄우기 위한 제이쿼리 cdn -->

<script

src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">

/* 팝업창 */

$(function() {

$("a").on("click", function() {

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

<!-- 학생의 개인 인적사항이 나오면 좋지 않을까 -->

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

<%-- <td style="color:blue"><%=list.get(0).isStudentStatus() ? "재원" : "퇴원"%></td> --%>

<%

if (list.get(0).isStudentStatus()) { //이 값 자체가 true라서 ==true를 써줄 필요x

%>

<td style="color: blue ; font-weight: bold">재원</td>

<%

} else {

%>

<td style="color: red ; font-weight: bold">퇴원</td>

<%

}

%>

</tr>

</table>

<a href="studentList.jsp"><img

src="https://media.istockphoto.com/id/1452220399/ko/%EB%B2%A1%ED%84%B0/%EB%92%A4%EB%A1%9C-%ED%99%94%EC%82%B4%ED%91%9C-%EC%95%84%EC%9D%B4%EC%BD%98%EC%9E%85%EB%8B%88%EB%8B%A4-%EC%99%BC%EC%AA%BD%EC%9C%BC%EB%A1%9C-%EB%8F%8C%EC%95%84%EA%B0%80%EA%B8%B0-%EB%B2%A1%ED%84%B0-%EA%B7%B8%EB%A6%BC.jpg?s=612x612&w=0&k=20&c=OIiES0-dasbonq_cxTn0oGuycE4b_sLr63HrY3yglVw="

alt="뒤로가기버튼이미지" id="back" /></a>

<%

}

%>

</div>

</body>

</html>
