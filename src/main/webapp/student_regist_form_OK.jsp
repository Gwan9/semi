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
			String grade = request.getParameter("grade");
			int gradeInt = Integer.parseInt(grade);
			String schoolName = request.getParameter("schoolName");
			String phone = request.getParameter("phone");
			String gender = request.getParameter("gender");
			String email = request.getParameter("email");
			String birth = request.getParameter("birth");
			String registDate = request.getParameter("registDate");
			String parentsName = request.getParameter("parentsName");
			String parentsPhone = request.getParameter("parentsPhone");
			String status = request.getParameter("status");
			String address = request.getParameter("address");
			String photo = request.getParameter("photo_addrs");
			
			
			
			// DAO VO 객체
			StudentDAO dao = new StudentDAO();
			ClassNoteVO vo = new ClassNoteVO();
			
			
			// 파라미터 값을 vo 값을 넣는다.
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
			
			System.out.println(vo);
			
			dao.studentAddOne(vo);
	%>
		
		<script type="text/javascript">
			
			alert("정상적으로 등록되었습니다.");
			
			window.location.href="admin.jsp";
			
		</script>
		
</body>
</html>