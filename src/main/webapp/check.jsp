<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>check.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
	crossorigin="anonymous"></script>

<script type="text/javascript">

<%
Date nowDate  = new Date();
SimpleDateFormat sdf = new SimpleDateFormat( "yy-MM-dd" );
String today = sdf.format( nowDate); 
%>
	$(function() {
		$( "#divTeacher" ).hide();
		$( "#divStudent" ).hide();
		<%
		/* 화면 불러올 때 오늘자 학생 데이터 생성 */
		StudentDAO dao = new StudentDAO();
		
		dao.studentCheckInsertAll();
		System.out.println( "addAllStudent()" );
		
		
		/* 화면 불러올 때 오늘자 교사 데이터 생성 */
		StudentDAO dao2 = new StudentDAO();
		dao2.teacherCheckInsertAll();
		System.out.println( "addAllTeacher()"); 
		%>
			
		// 교사or학생 옵션별 show, hide
		$( "#selectStdTec" ).change(function(e){
			var selectedVal = $(this).val(); // selectedVal = 선택한 옵션의 값
			console.dir(selectedVal);
			if( selectedVal == 1 ){
				$( "#divStudent" ).show();
				$( "#divTeacher" ).hide();
			}else if( selectedVal == 2 ){
				$( "#divTeacher" ).show();
				$( "#divStudent" ).hide();
			}else {
				$( "#divTeacher" ).hide();
				$( "#divStudent" ).hide();
			}
		})
		// 날짜 처음
		$( "#date1" ).change( function(e) {
			var date1 = $("#date1").val();
		})
		// 날짜 끝
		$( "#date2" ).change( function(e) {
			var date2 = $("#date2").val();
		})
		// 조회 클릭 시 날짜부터 날짜까지 값들 출력
		$( "#AtoB" ).on( "click", dateTodate )
		// today버튼 클릭 시 텍스트박스 날짜의 값들 출력
		$( "#checkToday" ).on( "click", dateToday )
		
		// 입력 버튼 누를 시 등교 지각 조퇴 비고 값 입력
		//$( "#btnAllCommit" ).on( "click", allCommit )
		
		
		// 출근버튼 클릭 시 출근시간 update
		
		$( "#btnCheckIn" ).on("click",function(){
			var currentTime = new Date();
		    var hours = currentTime.getHours().toString().padStart(2, '0');
		    var minutes = currentTime.getMinutes().toString().padStart(2, '0');
		    var seconds = currentTime.getSeconds().toString().padStart(2, '0');
		    var formattedTime = hours + ':' + minutes + ':' + seconds;
			$( "#textCheck" ).val(formattedTime);
			
			$.ajax({
				url : "checkIn.jsp",
				data : {
					"teacherName" : $("divTeacher").val()
				},
				success : function(data){
					var obj = JSON.parse( data );
				}
			})
		})
		
		
		// 퇴근버튼 클릭 시 퇴근시간 update
		
		$( "#btnCheckOut" ).on( "click", function(){
			var currentTime = new Date();
		    var hours = currentTime.getHours().toString().padStart(2, '0');
		    var minutes = currentTime.getMinutes().toString().padStart(2, '0');
		    var seconds = currentTime.getSeconds().toString().padStart(2, '0');
		    var formattedTime = hours + ':' + minutes + ':' + seconds;
			$( "#textCheck" ).val(formattedTime);
		})
		
	
		
	})

	

		/* date1 ~ date2 사이 테이블 출력 */
		function dateTodate(){
			$( "tbody" ).empty();
				$.ajax({
					url : "checkAtoB.jsp",
					data : {
						"date1" : $( "#date1" ).val(),
						"date2" : $( "#date2" ).val(),
						"selectVal" : $("#selectStdTec").val()
					}, 
					success : function(data){
						
						if( $("#selectStdTec").val() == 1 ){
							var obj = JSON.parse( data );
							console.log(obj);
							for ( var i=0; i<obj.length; i++ ){
								
								var txt = "<tbody><tr><td>"
									+ obj[i].studentNo
									+ "</td><td> "
									+ obj[i].studentName
									+ "</td><td> "
									+ obj[i].studentSchoolName
									+ "</td><td> "
									+ obj[i].studentGrade
									+ "</td><td> "
									+ obj[i].lectureClass
									+ "</td><td> "
									+ obj[i].studentPhone
									+ "</td><td>"
									+ obj[i].getStudentParentsPhone
									+ "</td><td>"
									+ obj[i].studentCheckIn
									+ "<input type='checkbox' id='studentCheckInCheckbox'/>"
									+ "</td><td>"
									+ obj[i].studentCheckLate
									+ "<input type='checkbox' id='studentCheckLateCheckbox'/>"
									+ "</td><td>"
									+ obj[i].studentCheckLeave
									+ "<input type='checkbox' id='studentCheckLeaveCheckbox'/>"
									+ "</td><td>"
									+ "<input type='text' id='studentText'/>"
									+ "</td><td>"
									+ "<input type='button' id='studentEdit' value='확인'/>"
									+ "</td></tr></tbody>";
									$("#tl1").append(txt);
							}
						}else if( $("#selectStdTec").val() == 2 ){
							var obj = JSON.parse( data );
							console.log(obj);
							for ( var i=0; i<obj.length; i++ ) {
								var txt = "<tbody><tr><td>" 
								+ obj[i].teacherCheckIn
								+ "</td><td>"
								+ obj[i].teacherCheckOut
								+ "</td><td>"
								+ obj[i].teacherWorkTime
								+ "</td></tr></tbody>";	
								$("#tl2").append(txt);
							}
						}
					}
				
				})
		}
		/* 오늘날짜 테이블 출력 */
		function dateToday(){
			$( "tbody" ).empty();
			console.log( "dateToday 호출" );
			$.ajax({
				url : "checkOK.jsp", 
				data : {
					"today" : $( "#today_txt" ).val(),
					"selectVal" : $("#selectStdTec").val()
					
				},
				success : function( data ) {
				
					if( $("#selectStdTec").val() == 1 ){
						var obj = JSON.parse( data );
						console.log( obj );
						for ( var i=0; i<obj.length; i++ ){
							var txt = "<tbody><tr><td>"
							+ obj[i].studentNo
							+ "</td><td> "
							+ obj[i].studentName
							+ "</td><td> "
							+ obj[i].studentSchoolName
							+ "</td><td> "
							+ obj[i].studentGrade
							+ "</td><td> "
							+ obj[i].lectureClass
							+ "</td><td> "
							+ obj[i].studentPhone
							+ "</td><td>"
							+ obj[i].getStudentParentsPhone
							+ "</td><td>"
							+ obj[i].studentCheckIn
							+ "<input type='checkbox' id='studentCheckInCheckbox'/>"
							+ "</td><td>"
							+ obj[i].studentCheckLate
							+ "<input type='checkbox' id='studentCheckLateCheckbox'/>"
							+ "</td><td>"
							+ obj[i].studentCheckLeave
							+ "<input type='checkbox' id='studentCheckLeaveCheckbox'/>"
							+ "</td><td>"
							+ "<input type='text' id='studentText'/>"
							+ "</td><td>"
							+ "<input type='button' id='studentEdit' value='확인'/>"
							+ "</td></tr></tbody>";
							$("#tl1").append(txt);
						}
					}else if( $("#selectStdTec").val() == 2 ){
						var obj = JSON.parse( data );
						console.log(obj);
						for ( var i=0; i<obj.length; i++ ) {
							var txt = "<tbody><tr><td>"
							+ "교사번호"
							+ "</td><td>"
							+ "교사명"
							+ "</td><td>"
							+ "담당반"
							+ "</td><td>"
							+ "전화번호"
							+ "</td><td>"
							+ obj[i].teacherCheckIn
							+  "</td><td>"
							+ obj[i].teacherCheckOut
							+ "</td><td>"
							+ obj[i].teacherWorkTime
							+ "</td></tr></tbody>";	
							$("#tl2").append(txt);
						}
					}
					
					
				}
			})
		}
		
		/* function allCommit(){
			$.ajax({
				url : "studentChackOk.jsp",
				data : {
					"" : 
				}
				
			})
		} */
		
		
		
</script>
</head>
<body>
	<!-- 학생 or 교사 테이블 선택 -->
	<select name="selectStd" id="selectStdTec">
		<option selected>대상선택</option>
		<option value="1">학생</option>
		<option value="2">교사</option>
	</select>
	
	<input type="text" name="textSend" id="textSend">
	<input type="button" name="btnSend" id="btnSend" value="문자발송">

	<input type="date" name="date1" id="date1" value="${dateType4}"> 부터
	<input type="date" name="date2" id="date2" value=""> 까지
	<input type="button" name="AtoB" id="AtoB" value="조회" />

	<div>
		<h5>총 학생 수 :</h5>
	</div>
	
	
	<!-- 오늘 날짜 출석만들기 -->
	<input type="text" name="" id="today_txt" value="<%=today%>" />
	<input type="button" name="checkToday" id="checkToday" value="출석표 불러오기" />

	<ul class="nav justify-content-end">
		<li class="nav-item"><a class="nav-link active"aria-current="page" href="#">10개씩</a></li>
		<li class="nav-item"><a class="nav-link" href="#">20개씩</a></li>
		<li class="nav-item"><a class="nav-link" href="#">30개씩</a></li>
		<li class="nav-item"><a class="nav-link" href="#">50개씩</a></li>
	</ul>


	<div id="divStudent"> <!-- 학생 검색 옵션들 -->
	
		<select id="selectClass">
			<option selected>반 선택</option>
			<option value="1">A 반</option>
			<option value="2">B 반</option>
			<option value="3">C 반</option>
		</select>
		
	<select name="selectLec" id="selectLec">
		<option selected>강의 선택</option>
		<option value="1">A</option>
		<option value="2">B</option>
		<option value="3">C</option>
	</select>
	
	<select name="selectStd" id="selectStd">
		<option selected>학생명 선택</option>
		<option value="1">AAA</option>
		<option value="2">BBB</option>
		<option value="3">CCC</option>
	</select>
	
	<table class="table table-dark table-striped" id="tl1">
	<thead>
		<tr>
			<th>학생번호</th>
			<th>학생명</th>
			<th>학교명</th>
			<th>학년</th>
			<th>강의반</th>
			<th>전화번호</th>
			<th>학부모전화번호</th>
			<th>등교</th>
			<th>지각</th>
			<th>조퇴</th>
			<th>비고</th>
			<th><input type="button" id="btnAllCommit" value="전체입력" /></th>
		</tr>
	</thead>
	</table>
	
	</div> <!-- divStudent end -->
	
	<div id="divTeacher"> <!-- 교사 검색 옵션들 -->
	<select name="selectTec" id="selectTec">
		<option selected>교사명</option>
		<option value="1">A교사</option>
		<option value="2">B교사</option>
		<option value="3">C교사</option>
	</select>
	
	<input type="text" name="textCheckIn" id="textCheck" value="" />
	<input type="button" name="btnCheckOut" id="btnCheckIn" value="출근">
	<input type="button" name="btnCheckOut" id="btnCheckOut" value="퇴근">
	<table class="table table-dark table-striped" id="tl2">
		<thead>
		<tr>
			<th>교사번호</th>
			<th>교사명</th>
			<th>담당반</th>
			<th>전화번호</th>
			<th>출근시간</th>
			<th>퇴근시간</th>
			<th>근무시간</th>
		</tr>
		</thead>
	</table>
	</div> <!-- divTeacher end -->
	
	
	
	

	
</body>
</html>
