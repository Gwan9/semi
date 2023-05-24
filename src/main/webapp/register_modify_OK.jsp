<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="VO.ClassNoteVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>student_modify_form_OK.jsp</title>

</head>
<body>
	<%
	// 1. 파라미터 값 가져오기
	String classRegisterNo = request.getParameter("classRegisterNo");
	String teacherNo = request.getParameter("teacherNo");
	String lectureNo = request.getParameter("lectureNo");
	String studentNo = request.getParameter("studentNo");
	String isPay = request.getParameter("isPay");
	

	// 2. teacher_no 가 null이 아닌 경우에만 가져오기
	if (classRegisterNo != null) {
		int classRegisterNoInt = Integer.parseInt(classRegisterNo);
		int teacherNoInt = Integer.parseInt(teacherNo);
		int lectureNoInt = Integer.parseInt(lectureNo);
		int studentNoInt = Integer.parseInt(studentNo);
		

		StudentDAO dao = new StudentDAO();

		ClassNoteVO vo = dao.registerSelectByNo(classRegisterNoInt);

		vo.setClass_registerNo(classRegisterNoInt);
		vo.setTeacherNo(teacherNoInt);
		vo.setLectureNo(lectureNoInt);
		vo.setStudentNo(studentNoInt);
		vo.isPay();

		dao.lectureUpdateAllByNo(vo);

	}
	%>

	<script type="text/javascript">
		alert("정상적으로 수정되었습니다.");

		window.location.href = "admin.jsp";
	</script>
</body>
</html>