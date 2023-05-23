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
	String no = request.getParameter("student_no");
	String name = request.getParameter("student_name");
	String grade = request.getParameter("student_grade");
	String schoolName = request.getParameter("student_school_name");
	String phone = request.getParameter("student_phone");
	String gender = request.getParameter("student_gender");
	String email = request.getParameter("student_email");
	String birth = request.getParameter("student_birth");
	String registDate = request.getParameter("student_regist_date");
	String parentsName = request.getParameter("student_parents_name");
	String parentsPhone = request.getParameter("student_parents_phone");
	String status = request.getParameter("student_status");
	String address = request.getParameter("student_address");
	String photo = request.getParameter("student_photo");

	// 2. teacher_no 가 null이 아닌 경우에만 가져오기
	if (no != null) {
		int noInt = Integer.parseInt(no);
		int gradeInt = Integer.parseInt(grade);
		Boolean genderBoolean = Boolean.valueOf(gender);
		Boolean statusBoolean = Boolean.valueOf(status);
		

		StudentDAO dao = new StudentDAO();

		ClassNoteVO vo = dao.studentSearchSelectByNo(noInt);

		vo.setStudentNo(noInt);
		vo.setStudentName(name);
		vo.setStudentGrade(gradeInt);
		vo.setStudentSchoolName(schoolName);
		vo.setStudentPhone(phone);
		vo.isStudentGender();
		vo.setStudentEmail(email);
		vo.setStudentBirth(birth);
		vo.setStudentRegistDate(registDate);
		vo.setStudentParentsName(parentsName);
		vo.setStudentParentsPhone(parentsPhone);
		vo.isStudentStatus();
		vo.setStudentAddrs(address);
		vo.setStudentPhoto(photo);

		dao.studentUpdateAllByNo(vo);

	}
	%>

	<script type="text/javascript">
		alert("정상적으로 수정되었습니다.");

		window.location.href = "admin.jsp";
	</script>
</body>
</html>