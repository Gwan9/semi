<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String n = request.getParameter("no").trim();
	String pw = request.getParameter("pw");
	String phone = request.getParameter("phone");
	String email = request.getParameter("email");
	
	if(n != null){
		
		StudentDAO dao = new StudentDAO();
		
		ClassNoteVO vo = new ClassNoteVO();
		
		int no = Integer.parseInt(n);
		
		vo.setTeacherNo(no);
		vo.setTeacherPw(pw);
		vo.setTeacherPhone(phone);
		vo.setTeacherEmail(email);
		
		dao.updateOne(vo);
	}
	
		
		
%>
<script type="text/javascript">
window.setTimeout(function(){
	alert("회원정보가 수정되었습니다");
	location.href = "main.jsp";
})
</script>		
