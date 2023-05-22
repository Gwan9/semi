<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	*{
	padding : 0;
	margin : auto;
	}
	.container {
		width : 1080px;
		height : 680px;
		background : gray;
		padding-bottom : 0;
		padding-top : 30px;
	}
	.board {
		padding : 20px;
		margin : auto;
		width : 80%;
		height : 80%;
		background : silver;
	}
	.boardlist{
		margin : auto;
		width : 100%;
		height : 70%;
		background : yellow;
	}
	#tarea{
	width : 850px; 
	height:300px;
	}
	.btn{
	width : 60px;
	height : 40px;
	}
</style>
</head>
<body>
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
	<div class="container" >	<!-- 학습일지 큰 div -->
		<div class="board" >
			<div class="maincontents">
				<label for="">수업일자 : </label> 
				<input type="text" name="" id="" value=<%=vo.getNoteDate() %> readonly/> 
				<label for="">학생 : </label> 
				<input type="text" name="selectStudentList" id="selectStudentList" value = "<%= vo.getStudentName() %>" readonly /> 
				<br /><br /> 
				<label for="">강의명 : </label> 
				<input type="text" name="lectureNameText" id="lectureNameText" value = "<%=vo.getLectureName() %>" readonly />
				<label for="">분반 : </label> 
				<input type="text" name="lectureClassText" id="lectureClassText" value = "<%=vo.getLectureClass() %>" readonly />
				<br /> <br />
				<label for="">담당강사 : </label> 
				<input type="text" name="teachername" id="teacherName" value = <%=vo.getTeacherName() %> readonly />
				<label for="">제목 : </label> 
				<input type="text" name="notetitle" id="notetitle" value = <%=vo.getNoteTitle() %>  readonly/>
				<br />
				<br />
				<textarea name="" id="tarea" cols="30" rows="10" readonly><%=vo.getNoteContents() %> </textarea>
			</div>
			<div id="alter_btn" align="right">
				<a href="classNoteModify.jsp?NoteNo=<%=vo.getNoteNo()%>"><input class="btn" type="button" value="수정" id="" /></a>
				<a href="classNoteDeleteOk.jsp?NoteNo=<%=vo.getNoteNo()%>"><input class="btn" type="button" value="삭제" id="" /></a>
				<a href="classNote.jsp"><input type="button" value="뒤로가기" class="btn" /></a>
			</div> <br />
		</div>
	</div>
	
</body>
</html>