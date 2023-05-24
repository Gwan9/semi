<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%

			// 한글처리
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
		
			// 요청객체로부터 파라미터 값을 가져온다.
			String no = request.getParameter("no");
			int noInt = Integer.parseInt(no);
			String name = request.getParameter("name");
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String birth = request.getParameter("birth");
			String hiredate = request.getParameter("hiredate");
			String worktype = request.getParameter("worktype");
			String sal = request.getParameter("sal");
			int salInt = Integer.parseInt(sal);
			String subject = request.getParameter("subject");
			String address = request.getParameter("address");
			String photo = request.getParameter("photo_addrs");
			
			
			
			// DAO VO 객체
			StudentDAO dao = new StudentDAO();
			ClassNoteVO vo = new ClassNoteVO();
			
			
			// 파라미터 값을 vo 값을 넣는다.
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
			
			System.out.println(vo);
			
			dao.teacherAddOne(vo);
	%>
		
		<script type="text/javascript">
			
			alert("정상적으로 등록되었습니다.");
			
			window.location.href="admin.jsp";
			
		</script>
		
</body>
</html>