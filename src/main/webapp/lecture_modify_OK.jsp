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
	String no = request.getParameter("lectureNo");
	String name = request.getParameter("lectureName");
	String divideClass = request.getParameter("lectureClass");
	String startDate = request.getParameter("lectureStartDate");
	String endDate = request.getParameter("lectureEndDate");
	String tuition = request.getParameter("lectureTuition");
	

	// 2. teacher_no 가 null이 아닌 경우에만 가져오기
	if (no != null) {
		int noInt = Integer.parseInt(no);
		int tuitionInt = Integer.parseInt(tuition);		

		StudentDAO dao = new StudentDAO();

		ClassNoteVO vo = dao.lectureSelectByNo(noInt);

		vo.setLectureName(name);
		vo.setLectureClass(divideClass);
		vo.setLectureStartDate(startDate);
		vo.setLectureEndDate(endDate);
		vo.setLectureTuition(tuitionInt);

		dao.lectureUpdateAllByNo(vo);

	}
	%>

	<script type="text/javascript">
		alert("정상적으로 수정되었습니다.");

		window.location.href = "admin.jsp";
	</script>
</body>
</html>