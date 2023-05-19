<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="loginside">
	<form action="teacherLoginOk.jsp" name="frm" method="get">
		<table>
			<tr>
				<th>ID</th>
				<td><input type="text" name="id" id="id" /></td>
			</tr>
			<tr>
				<th>PW</th>
				<td><input type="password" name="pw" id="" /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="로그인" id="btn1" />
					<a href="teacherRegister.jsp">
					<input type="button" value="회원가입" id="btn2" />	</a>
				</td>
			</tr>
		</table>
	</form>
</div>