<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.KHWDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacher_modify_form_OK.jsp</title>

</head>
<body>
	<%

		
		
		// 1. 파라미터 값 가져오기
		String no = request.getParameter("teacher_no");
		String name = request.getParameter("teacher_name");
		String id = request.getParameter("teacher_id");
		String pw = request.getParameter("teacher_pw");
		String phone = request.getParameter("teacher_phone");
		String gender = request.getParameter("teacher_gender");
		String email = request.getParameter("teacher_email");
		String birth = request.getParameter("teacher_birth");
		String hiredate = request.getParameter("teacher_hiredate");
		String worktype = request.getParameter("teacher_worktype");
		String sal = request.getParameter("teacher_sal");
		String subject = request.getParameter("teacher_subject");
		String address = request.getParameter("teacher_address");
		String photo = request.getParameter("teacher_photo");
		
		
		// 2. teacher_no 가 null이 아닌 경우에만 가져오기
		if( no != null ){
			int noInt = Integer.parseInt(no);
			int salInt = Integer.parseInt(sal);
			Boolean genderBoolean = Boolean.valueOf(gender);
			
			
			KHWDAO dao = new KHWDAO();
			
			ClassNoteVO vo = dao.teacherGetOne(noInt);
			
			vo.setTeacherNo(noInt);
			vo.setTeacherName(name);
			vo.setTeacherId(id);
			vo.setTeacherPw(pw);
			vo.setTeacherPhone(phone);
			vo.isTeacherGender();
			vo.setTeacherEmail(email);
			vo.setTeacherBirth(birth);
			vo.setTeacherHiredate(hiredate);
			vo.setTeacherWorktype(worktype);
			vo.setTeacherSal(salInt);
			vo.setTeacherSubject(subject);
			vo.setTeacherAddress(address);
			vo.setTeacherPhoto(photo);
			
			
			dao.updateOne(vo);
			
		}
	%>
	
		<h2>정상적으로 수정되었습니다.</h2>
		<h2>3초 후에 자동으로 이전 페이지로 이동합니다.</h2>
	
		<script type="text/javascript">
		
			window.setTimeout(function() {
				location.href="admin.jsp";
			}, 3000);
		</script>
</body>
</html>