<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="semi.css">
<link rel="stylesheet" href="mainpage.css">
<style>
#tarea{
	margin-top : 50px;
	margin-left : 50px;
	padding:5px;
	border-radius: 13px;
	width : 90%; 
	height : 55%;
}

.sideMenu4{
	background:#FFCA2C;
	color:#686868;
}
</style>
</head>
<body>
<jsp:include page="mainheader.jsp"></jsp:include>
	<%
	StudentDAO dao = new StudentDAO();
	ClassNoteVO vo = new ClassNoteVO();

	String n = request.getParameter("NoteNo");

	if (n == null || n.equals("")) {
		response.sendRedirect("ClassNote.jsp");
	} else {
		int noteno = Integer.parseInt(n);

		vo = dao.studentNoteSelectOne(noteno);
	}
	%>
<div id="canvas">
		<a href="studentList.jsp"><div class="sideMenu1">학생목록</div></a>
		<a href="checkList.jsp"><div class="sideMenu2">출결관리</div></a>
		<a href="studentcal.jsp"><div class="sideMenu3">수업관리</div></a>
		<a href="classNote.jsp"><div class="sideMenu4">학습일지</div></a>
		<a href="admin.jsp"><div class="sideMenu5">관리자</div></a>
	<div id="container" > <!-- 학습일지 큰 div -->
		<div id="student1">
			<form action="classNoteModifyOk.jsp">
				<input type="hidden" name="NoteNo" value="<%=vo.getNoteNo()%>" />
				<label for="">수업일자 : </label> 
				<input type="text" name="" id="" value=<%=vo.getNoteDate() %> readonly /> 
				<label for="">학생 : </label> 
				<input type="text" name="" id="selectStudentList" value = "<%= vo.getStudentName() %>" readonly /> 
				<br /><br /> 
				<label for="">강의명 : </label> 
				<input type="text" name="" id="lectureNameText" value = "<%=vo.getLectureName() %>" readonly />
				<label for="">분반 : </label> 
				<input type="text" name="" id="lectureClassText" value = "<%=vo.getLectureClass() %>" readonly />
				<br /> <br />
				<label for="">담당강사 : </label> 
				<input type="text" name="" id="teacherName" value = <%=vo.getTeacherName() %> readonly />
				<label for="">제목 : </label> 
				<input type="text" name="NoteTitle" id="notetitle" value = <%=vo.getNoteTitle() %>  />
				<br />
				<br />
				<textarea name="TArea" id="tarea" cols="30" rows="10" ><%=vo.getNoteContents() %> </textarea>
				<br /><br />
				<div id="alter_btn" align="right">
					<button type="submit" class="btn btn-warning btn-xs" >수정완료</button>
					<a href="classNote.jsp"><button type="button" class="btn btn-warning btn-xs" >뒤로가기</button></a>
				</div> <br />
			</form>
		</div>
	</div>
</div>
	
</body>
</html>
