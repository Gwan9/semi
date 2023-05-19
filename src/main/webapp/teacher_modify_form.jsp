<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.KHWDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacher_detail.jsp</title>

<style type="text/css">

	#container {
		width: 1000px;
		height: 1000px;
		background: #E2E2E2;
		margin: 0px auto;
	}
	
	table {
		width: 800px;
		height: 300px;
	}
	
	table, th, td {
		border: 1px solid black;
		border-collapse : collapse;
		margin: 0px auto;
	}
	
	
	#img {
		width: 200px;
		height: 230px;
		border: 1px solid black;
		margin-left: 40%;
		margin-bottom: 60px;
	}
	
	#photo {
		width: 200px;
		height: 230px;
	}
	
	#title {
		text-align: center;
	}
	
	#modify_or_cancle {
		text-align: right;
		margin-right: 90px;
		margin-top: 20px;
	}
	
	
	#modify_btn, #cancle_btn {
		width: 80px;
		height: 40px;
		margin-bottom: 30px;
		
	}

}

</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">

	$(function() {
		$("#cancle_btn").on("click", function() {
			window.location.href = "admin.jsp";
		})
		
		
		// 파일을 넣었다! 의 의미==e
		// 파일을 넣었다면 아래 익명함수 수행하기
		$("input[name='photo_insert']").change(function (e) {
			// file의 엘리먼트 이름 가져오기
			var fileInput = document.getElementsByName("photo_insert");
			console.log(fileInput);
			
			var filename = null;
			
			// 업로드한 이미지는 배열 형식으로 저장되는 것으로 보임.
			for( var i=0; i<fileInput.length; i++ ){
				// 만약, 파일을 추가했다면
				if( fileInput[i].files.length > 0 ){
					for( var j=0; j<fileInput[i].files.length; j++ ){
						console.log(fileInput[i].files[j].name); // 파일명 출력
					
						
						filename = fileInput[i].files[j].name;
						
						// 이미지의 경로를 첨부한 파일의 경로로 적용한다.
						$("#photo").attr("src", "../team2portress/images/"+filename);
						// DB 저장을 위해서 파일의 경로를 text 박스에 넣었고, 그 value 값에 파일의 경로를 넣는다. 
						$("#photo_addrs").attr("value", "../team2portress/images/"+filename);
						
					}
				}
			}
		})
	})	

</script>

</head>
<body>
	<div id="container">
		<div id="title">
			<h1>교사정보[수정]</h1>		
		</div>
		
		<%

					String teacher_no = request.getParameter("teacher_no");
						
					if( teacher_no != null ) {
						int teacherNoInt = Integer.parseInt(teacher_no);
						KHWDAO dao = new KHWDAO();
						ClassNoteVO vo = dao.teacherGetOne(teacherNoInt);
				%>
		
	<form action="teacher_modify_form_OK.jsp?teacher_no=<%= vo.getTeacherNo() %>">
	
<!-- 		<div id="img"> -->
<%-- 			<img src="<%= vo.getTEACHER_PHOTO() %>" alt="" name="photo" id="photo"/> --%>
<%-- 			<input type="hidden" name="teacher_photo" value="<%= vo.getTEACHER_PHOTO() %>" /> --%>
<!-- 		</div> -->
		
		<div id="img">
			<img src="<%= vo.getTeacherPhoto() %>" alt="" name="photo" id="photo"/>
			<input type="text" id="photo_addrs" name="teacher_photo" value="<%= vo.getTeacherPhoto() %>" />
			<input type="file" name="photo_insert" id="photo_insert" />
		</div>
	
	
		<div id="table">
			<table>
				<tr>
					<th>교사번호</th>
					<td colspan="2">
						<input type="text" name="teacher_no" id="" value="<%= vo.getTeacherNo() %>" />
					</td>
					<th>이름</th>
					<td colspan="2">
						<input type="text" name="teacher_name" id="" value="<%= vo.getTeacherName() %>" />
					</td>
				</tr>
				<tr>
					<th>아이디(ID)</th>
					<td>
					<input type="text" name="teacher_id" id="" value="<%= vo.getTeacherId() %>" />
					</td>
					<th>패스워드(PW)</th>
					<td>
					<input type="text" name="teacher_pw" id="" value="<%= vo.getTeacherPw() %>" />
					</td>
					<th>휴대전화</th>
					<td>
					<input type="text" name="teacher_phone" id="" value="<%= vo.getTeacherPhone() %>" />
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<label><input type="radio" name="teacher_gender" id="gender" value="1" />남</label> 
						<label><input type="radio" name="teacher_gender" id="gender" value="2" />여</label>
					</td>
					<th>이메일</th>
					<td>
					<input type="text" name="teacher_email" id="" value="<%= vo.getTeacherEmail() %>" />
					</td>
					<th>생년월일</th>
					<td>
					<input type="date" name="teacher_birth" id="" value="<%= vo.getTeacherBirth() %>" />
					</td>
				</tr>
				<tr>
					<th>입사일</th>
					<td>
					<input type="date" name="teacher_hiredate" id="" value="<%= vo.getTeacherHiredate()%> "/>
					</td>
					<th>근무형태</th>
					<td>
					<input type="text" name="teacher_worktype" id="" value="<%= vo.getTeacherWorktype()%>"/>
					</td>
					<th>급여</th>
					<td>
					<input type="text" name="teacher_sal" id="" value="<%= vo.getTeacherSal() %>" />
					</td>
				</tr>
				<tr>
					<th>담당과목</th>
					<td>
					<input type="text" name="teacher_subject" id="" value="<%= vo.getTeacherSubject() %>"/>
					</td>
					<th>주소지</th>
					<td colspan="3">
					<input type="text" name="teacher_address" id="" value="<%= vo.getTeacherAddress() %>"/>
					</td>
				</tr>
			</table>
			
			<div id="modify_or_cancle">
				<input type="submit" id="modify_btn" value="저장" />
				<a href="teacher_detail.jsp?teacher_no=<%= vo.getTeacherNo()%>"><input type="button" id="cancle_btn" value="뒤로가기" /></a>
			</div>
		</div>
		
		<%
			}
		%>
		</form>
	</div>
</body>
</html>