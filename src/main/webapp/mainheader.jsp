<%@page import="VO.ClassNoteVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
$("#bannerside").on("click", function(){
	location.href = "main.jsp";
})	

</script>
	<%
		Object obj = session.getAttribute("vo");
		if(obj != null){ //로그인 되어 있는 상태 확인
			ClassNoteVO vo = (ClassNoteVO)obj;
			
			session.setAttribute("vo", vo);
	%>
			<div id="header">
				<div id="bannerside">
					<img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTidgN85-7IXfj0yaEUlldBQ8OCjxO7Jhqbbw&usqp=CAU" alt="banner" />
				</div>
				
				<div id="loginside">
					<form action="teacherModify.jsp" name="frm" method="get">
						<input type="hidden" name="no" value="<%= vo.getTeacherNo() %>" />
						<table>
							<tr>
								<th><%= vo.getTeacherName() %>님 환영합니다</th>
							</tr>
							<tr>
								<td colspan="2">
									<input type="submit" value="개인정보수정" /> </a>
									<a href="logout.jsp">
									<input type="button" value="로그아웃" id="logoutbtn" />	</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<%
			}
			%>