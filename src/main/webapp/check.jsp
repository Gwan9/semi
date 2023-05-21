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
		/* 화면 불러올 때 오늘자 학생 데이터가 존재하지 않는다면 오늘자 데이터 생성 */
		StudentDAO dao = new StudentDAO();
		if (!dao.studentCheckIsExist()) {
   			 dao.studentCheckInsertAll();
   			 System.out.println("addAllStudent()");
		}
		
		
		
		/* 화면 불러올 때 오늘자 교사 데이터가 존재하지 않는다면 오늘자 데이터 생성 */
			StudentDAO dao2 = new StudentDAO();
		if (!dao.teacherCheckIsExist()){
			dao2.teacherCheckInsertAll();
			System.out.println( "addAllTeacher()"); 
		}
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
		
		/** SET student_check_status =
		CASE
		WHEN student_check_type = 1 THEN '등교'
		WHEN student_check_type = 2 THEN '지각'
		WHEN student_check_type = 3 THEN '조퇴'
		WHEN student_check_type = 4 THEN '결석' */
		
		$("#checkAll").on("change", function() { //체크박스가 변경될때마다 실행
			var isChecked = $(this).prop("checked");
			$("input[name='studentNo']").prop("checked", isChecked);
		});
		
		// 입력 버튼 누를 시 등교 지각 조퇴 비고 값 입력
		//$( "#btnAllCommit" ).on( "click", allCommit )
		
		// btnStudentCheck 클릭 시 출석정보 전달
		
		$("#btnStudentCheckIn").on("click", function(){
			var studentCheckType = 1
			
			$( "tbody" ).empty(); 
			 
			$.ajax({
				url : "studentCheck.jsp",
				data : {
					"studentCheckType" : studentCheckType
				},
				success : function(data){
					var obj = JSON.parse( data );
					console.log(obj);
					for ( var i=0; i<obj.length; i++ ) {
						var txt = "<tbody><tr><td><input type='checkbox' name='studentNo' id='studentNo' />"
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
							+ obj[i].studentStatus
							+ "</td></tr></tbody>";
							$("#tl1").append(txt);
					}
				}
			})
		})
		
		
		
		
		
		// 출근버튼 클릭 시 출근시간 update
		$( "#btnCheckIn" ).on("click",function(){
			$( "tbody" ).empty();
			var currentTime = new Date();
		    var hours = currentTime.getHours().toString().padStart(2, '0');
		    var minutes = currentTime.getMinutes().toString().padStart(2, '0');
		    var seconds = currentTime.getSeconds().toString().padStart(2, '0');
		    var formattedTime = hours + ':' + minutes + ':' + seconds;
			$( "#textCheck" ).val(formattedTime);
			
			
			$.ajax({
				url : "teacherCheckIn.jsp",
				data : {
					"teacherName" : $( "#textCheck" ).val()
				},
				success : function(data){
					var obj = JSON.parse( data );
					console.log(obj);
					for ( var i=0; i<obj.length; i++ ) {
						var txt = "<tbody><tr><td>"
						+ obj[i].teacherNo
						+ "</td><td>"
						+ obj[i].teacherCheckIn
						+ "</td><td>"
						+ obj[i].teacherCheckOut
						+ "</td><td>"
						+ obj[i].teacherWorkTime
						+ "</td></tr></tbody>";	
						$("#tl2").append(txt);
					}
				}
			})
		})
		
		
		// 퇴근버튼 클릭 시 퇴근시간 update
		
		$( "#btnCheckOut" ).on( "click", function(){
			$( "tbody" ).empty();
			var currentTime = new Date();
		    var hours = currentTime.getHours().toString().padStart(2, '0');
		    var minutes = currentTime.getMinutes().toString().padStart(2, '0');
		    var seconds = currentTime.getSeconds().toString().padStart(2, '0');
		    var formattedTime = hours + ':' + minutes + ':' + seconds;
			$( "#textCheck" ).val(formattedTime);
			$.ajax({
				url : "teacherCheckOut.jsp",
				data : {
					"teacherName" : $("#textCheck").val()
				},
				success : function(data){
					var obj = JSON.parse( data );
					console.log(obj);
					for ( var i=0; i<obj.length; i++ ) {
						var txt = "<tbody><tr><td>" 
						+ obj[i].teacherNo
						+ "</td><td>"
						+ obj[i].teacherCheckIn
						+ "</td><td>"
						+ obj[i].teacherCheckOut
						+ "</td><td>"
						+ obj[i].teacherWorkTime
						+ "</td></tr></tbody>";	
						$("#tl2").append(txt);
					}
				}
			})
		})
		
	
		
	})

	

		/* date1 ~ date2 사이 테이블 출력 */
		function dateTodate(){
		
			$( "tbody" ).empty(); // table 내용 지우
			
			
				$.ajax({
					url : "checkAtoB.jsp",
					data : {
						// 넘겨주는 데이터 :
						"selectVal" : $( "#selectStdTec" ).val() // 학생인지 교사인지
						"date1" : $( "#date1" ).val(), // 날짜 1 ~ 날짜 2	
						"date2" : $( "#date2" ).val(),
						"studentName" : $( "#studentName" ).val(), // 학생이름 
						"lectureClass" : $( "#lectureClass" ).val(), // 강의명 
						"lectureName" : $( "#lectureName" ).val() // 반이름
					}, 
					success : function(data){
						
						if( $("#selectStdTec").val() == 1 ){
							var obj = JSON.parse( data );
							console.log(obj);
							for ( var i=0; i<obj.length; i++ ){
								
								var txt = "<tbody><tr><td><input type='checkbox' name='studentNo' id='studentNo' />"
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
									+ obj[i].studentStatus
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
				url : "CheckOk.jsp", 
				data : {
					"today" : $( "#today_txt" ).val(),
					"selectVal" : $("#selectStdTec").val()
					"studentName" : $( "#studentName" ).val(), // 학생이름 
					"lectureClass" : $( "#lectureClass" ).val(), // 강의명 
					"lectureName" : $( "#lectureName" ).val() // 반이름
					
				},
				success : function( data ) {
				
					if( $("#selectStdTec").val() == 1 ){
						var obj = JSON.parse( data );
						console.log( obj );
						for ( var i=0; i<obj.length; i++ ){
							var txt = "<tbody><tr><td><input type='checkbox' name='studentNo' id='studentNo' />"
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
							+ obj[i].studentStatus
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
		
		
		function studentCheck(){
			$("#sl tbody").empty();

			//체크 된 거 언체크로
			if ($("#checkAll").prop("checked") == true) //만약 체크되었다면
				//console.log("checked");
				$("#checkAll").prop("checked", false); //해제
			
				
			$.ajax({
				url : "studentCheck.jsp",
				data : {
					"today" : $( "#today_txt" ).val(),
					"checkStatus" : 
					
					

			})
		}
		
		
		
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
	
	<input type="button" name="btnStudentStatus" id="btnStudentCheckIn" value="등교">
	<input type="button" name="btnStudentStatus" id="btnStudentCheckLate" value="지각">
	<input type="button" name="btnStudentStatus" id="btnStudentCheckLeave" value="조퇴">
	<input type="button" name="btnStudentStatus" id="btnStudentCheckAbsence" value="결석">
	
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
			<th>출결상태</th>
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
