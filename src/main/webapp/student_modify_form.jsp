<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
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
			<h1>학생정보[수정]</h1>		
		</div>
		
		<%
				String studentNo = request.getParameter("student_no");

				if (studentNo != null) {
					int studentNoInt = Integer.parseInt(studentNo);
					StudentDAO dao = new StudentDAO();
					ArrayList<ClassNoteVO> list = dao.studentSearchSelectAllByNo(studentNoInt);
				
					for(ClassNoteVO vo : list) {
				
			%>
		
	<form action="student_modify_form_OK.jsp?student_no=<%= vo.getStudentNo() %>">
		
		<div id="img">
			<img src="<%= vo.getStudentPhoto() %>" alt="" name="photo" id="photo"/>
			<input type="text" id="photo_addrs" name="student_photo" value="<%= vo.getStudentPhoto() %>" />
			<input type="file" name="photo_insert" id="photo_insert" />
		</div>
	
	
		<div id="table">
			<table>
				<tr>
					<th>학생번호</th>
					<td colspan="2">
						<input type="text" name="student_no" id="" value="<%= vo.getStudentNo() %>" />
					</td>
					<th>이름</th>
					<td colspan="2">
						<input type="text" name="student_name" id="" value="<%= vo.getStudentName() %>" />
					</td>
				</tr>
				<tr>
					<th>학년</th>
					<td>
					<input type="text" name="student_grade" id="" value="<%= vo.getStudentGrade() %>" />
					</td>
					<th>학교명</th>
					<td>
					<input type="text" name="student_school_name" id="" value="<%= vo.getStudentSchoolName() %>" />
					</td>
					<th>연락처</th>
					<td>
					<input type="text" name="student_phone" id="" value="<%= vo.getStudentPhone() %>" />
					</td>
				</tr>
				<tr>
					<th>성별</th>
					<td>
						<label><input type="radio" name="student_gender" id="gender" value="1" />남</label> 
						<label><input type="radio" name="student_gender" id="gender" value="2" />여</label>
					</td>
					<th>이메일</th>
					<td>
					<input type="text" name="student_email" id="" value="<%= vo.getStudentEmail() %>" />
					</td>
					<th>생년월일</th>
					<td>
					<input type="date" name="student_birth" id="" value="<%= vo.getStudentBirth() %>" />
					</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>
					<input type="date" name="student_regist_date" id="" value="<%= vo.getStudentRegistDate() %> "/>
					</td>
					<th>학부모 이름</th>
					<td>
					<input type="text" name="student_parents_name" id="" value="<%= vo.getStudentParentsName() %>"/>
					</td>
					<th>학부모 연락처</th>
					<td>
					<input type="text" name="student_parents_phone" id="" value="<%= vo.getStudentParentsPhone() %>" />
					</td>
				</tr>
				<tr>
					<th>등록상태</th>
					<td>
						<label><input type="radio" name="student_status" id="student_status" value="1" />재원</label> 
						<label><input type="radio" name="student_status" id="student_status" value="2" />퇴원</label>
					</td>
					<th>주소지</th>
					<td colspan="3">
					<input type="text" name="student_address" id="" value="<%= vo.getStudentAddrs() %>"/>
					</td>
				</tr>
			</table>
			
			<div id="modify_or_cancle">
				<input type="submit" id="modify_btn" value="저장" />
				<a href="student_detail.jsp?student_no=<%= vo.getStudentNo() %>"><input type="button" id="cancle_btn" value="뒤로가기" /></a>
			</div>
		</div>
		
		<%
				}
			}
		%>
		</form>
	</div>
</body>
</html>