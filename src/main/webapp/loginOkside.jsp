<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="loginside">
	<form action="teacherLoginOk.jsp" name="frm" method="get">
		<table>
			<tr>
				<th>~~님 환영합니다</th>
				<td><input type="text" name="id" id="id" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="개인정보수정" id="btn1" />
					<a href="logout.jsp">
					<input type="button" value="로그아웃" id="btn2" />	</a>
				</td>
			</tr>
		</table>
	</form>
</div>