<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//upload 디렉토리의 실제 경로 얻어오기
	String saveDir = request.getRealPath("/images");
	
	System.out.println(saveDir);
	
	// 첨부파일의 최대 크기 : 30MB 까지 업로드 가능
	// 1M ==> 1024KB
	int maxFileSize = 1024*1024*30;
	
	// 1KB ==> 1024Byte
			
	MultipartRequest mr = new MultipartRequest(request, saveDir,
			maxFileSize, "UTF-8", new DefaultFileRenamePolicy());





	String teacherNo = mr.getParameter("no");
	String teacherPhoto = mr.getOriginalFileName("photo");
	String teacherPw = mr.getParameter("pw");
	String teacherPhone = mr.getParameter("phone");
	String teacherEmail = mr.getParameter("email");
	
	if(teacherNo != null){
		
		StudentDAO dao = new StudentDAO();
		
		ClassNoteVO vo = new ClassNoteVO();
		
		int no = Integer.parseInt(teacherNo);
		
		vo.setTeacherNo(no);
		vo.setTeacherPhoto(teacherPhoto);
		vo.setTeacherPw(teacherPw);
		vo.setTeacherPhone(teacherPhone);
		vo.setTeacherEmail(teacherEmail);
		
		dao.updateOne(vo);
	}
	
		
		
%>
<script type="text/javascript">
window.setTimeout(function(){
	alert("회원정보가 수정되었습니다");
	location.href = "main.jsp";
})
</script>		
