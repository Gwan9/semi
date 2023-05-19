
<%@page import="VO.ClassNoteVO"%>
<%@page import="DAO.StudentDAO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>studentList.jsp</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 화면 크기를 디바이스 크기로 / 시작은 1배크기로  -->

<!-- CDN -->
<!-- css -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
	crossorigin="anonymous"></script>

<style>
* {
	margin: auto;
	padding: 0;
}

#container {
	width: 1080px;
	margin: auto;
}

#studentList {
	width: 900px;
	height: 600px;
	background: gray;
}

#student1 {
	padding: 10px;
	position: relative;
	top: 40px;
	width: 800px;
	height: 150px;
	background-color: #212529;
	color: white;
	font-size: 90%;
	background-color: black;
}

#student2 {
	position: relative;
	top: 60px;
	width: 800px;
	background: skyblue;
}

table {
	width: 750px;
	font-size: 80%;
}

#searchBtn {
	position: relative;
	left: 300px;
}

#btns {
	position: absolute;
	left: 810px;
	bottom: 590px;
}

.custom-btn-xs{
font-size: 10px;
padding: 5px 10px;
}
</style>


<!-- ajax CDN -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {

		// <조회>
		//분반, 강의명, 학년은 값을 선택하면 조회
		//change 말고 selected 도 고려
		$("#lectureClass").on("change", search);
		$("#lectureName").on("change", search);
		$("#studentGrade").on("change", search);
		//이름은 조회버튼을 눌러야 조회
		$("#searchBtn").on("click", search);
		
		// <체크>
		//checkAll을 누르면 모든 체크박스가 선택됨
		$("#checkAll").on("change", function(){ //체크박스가 변경될때마다 실행
			var isChecked = $(this).prop("checked"); 
							// $(this) : 현재 이벤트가 발생한 요소
							// prop("checked") : 해당 요소의 checked 속성값을 가져오기 
							// $(this).prop("checked") : boolean 타입
								// 체크박스가 선택되면 isChecked에 true가 담기고, 선택해제되면 false가 담김
			$("input[name='studentNo']").prop("checked", isChecked); 
									// checkAll을 누르면 그 체크상태의 값을 studentNo의 checked 속성값에 담아줌
									//#로 id 값을 가져오면 첫번째 값만 가져오므로 name으로 가져온다
		});
	});
	
	//<인쇄>
	//$("#printBtn").on()
	//------------------------------------------------

	function search() {
		//기존 테이블 행 삭제 (비워주기)
		$("#sl tbody").empty();
		
		//체크 된 거 언체크로
		if($("#checkAll").prop("checked") == true) //만약 체크되었다면
			//console.log("checked");
			$("#checkAll").prop("checked", false); //해제

		//Ajax 함수 호출
		$
				.ajax({
					url : "search.jsp",
					//넘겨줄 데이터 sdsds
					data : {
						"studentName" : $("#studentName").val(),
						"studentGrade" : $("#studentGrade").val(),
						"lectureClass" : $("#lectureClass").val(),
						"lectureName" : $("#lectureName").val(),
					//날짜

					},

					//성공했다면
					success : function(data) {
						var obj = JSON.parse(data);
						//보통 웹 애플리케이션에서 서버로부터 받은 데이터는 JSON 형식으로 전송 
						// 그러나 JavaScript에서는 JSON 형식의 문자열을 직접 다루기보다 객체로 변환하여 사용하는 것이 더 편리 
						//JSON.parse()는 이러한 변환을 수행해주는 메서드

						for (var i = 0; i < obj.length; i++) {
							//오브젝트의 값들을 가져와서 
							var txt = "<tbody><tr><td><input type='checkbox' name='studentNo' id='studentNo' />"
								//+ " </td><td> "+ obj[i].studentName
								+ " </td><td><a href='detail.jsp?studentName="
								+ obj[i].studentName
								+ "'> "
								+ obj[i].studentName //'' ""때문에 겁나 애먹었네
								+ " </a> </td><td> "
								+ obj[i].studentSchoolName
								+ " </td><td> "
								+ obj[i].studentGrade
								+ " </td><td> "
								+ obj[i].lectureClass
								+ " </td><td> "
								+ obj[i].studentPhone
								+ " </td><td> "
								+ obj[i].studentRegistDate.substring(0,10)
								+ " </td><td> "
								+ (obj[i].studentGender ? "남" : "여")
								+ " </td><td> "
								+ obj[i].studentParentsName
								+ "</td><td>" + obj[i].studentParentsPhone

								+ "</td></tr></tbody>";
						//테이블에 붙여줘
						$("#sl").append(txt);
						}
					},
					//에러났다면
					error : function() {
						alert("실패");
					} 
				});
	}
</script>

</head>
<body>

	<%
	// 조회 전 전체목록 다 나오게 하기
	//DAO 생성
	StudentDAO dao = new StudentDAO();
	%>
	<div id="container">

		<div id="studentList">

			<div id="student1">
				<h5>학생 정보 조회</h5>
				 분반 <select name="lectureClass" id="lectureClass">
					<option value="전체">전체</option>
					<option value="A">A반</option>
					<option value="B">B반</option>
					<option value="C">C반</option> 
				</select> 
				강의명 <select name="lectureName" id="lectureName">
					<option value="전체">전체</option>
					<option value="국어">국어</option>
					<option value="수리">수리</option>
					<option value="영어">영어</option>
				</select> 
			학년	<select name="studentGrade" id="studentGrade">
					<option value="전체">전체</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
				</select> 
				이름 <input type="text" name="studentName" id="studentName" /> <br />
				<br />
				등록일 <input type="date" name="startDate" id="" /> 부터 <input
					type="date" name="" id="" /> 까지

				<!-- <input type="button" value="조회" id="searchBtn" /> <br /> <br /> -->
				<button type="button" id="searchBtn" class="btn btn-warning btn-xs">조회</button>
				<br /> <br />
			</div>


			<div id="student2">
				<table id="sl" class="table table-dark table-striped table-hover">
					<thead>
						<tr>
							<th><input type="checkbox" name="checkAll" id="checkAll" /></th>
							<th>학생명</th>
							<th>학교명</th>
							<th>학년</th>
							<th>반</th>
							<th>학생 연락처</th>
							<th>등록일</th>
							<th>성별</th>
							<th>학부모명</th>
							<th>학부모 연락처</th>
						</tr>

					</thead>

					<%
						ArrayList<ClassNoteVO> list = dao.studentSearchSelectAll();
						for (ClassNoteVO vo : list) {
						%>
					
					<tr>
						<td><input type='checkbox' name='studentNo' id='studentNo' /></td>
						
						<!--  이름 누르면 학생 상세 페이지로 !!!!-->
						<td><a href="detail.jsp?studentName=<%=vo.getStudentName()%>"><%=vo.getStudentName()%></a></td>
						
						<td><%=vo.getStudentSchoolName()%></td>
						<td><%=vo.getStudentGrade()%></td>
						<td><%=vo.getLectureClass()%></td>
						<td><%=vo.getStudentPhone()%></td>
						<td><%=vo.getStudentRegistDate().substring(0,10)%></td>
						<td><%=vo.isStudentGender() == true ? "남" : "여"%></td>
						<td><%=vo.getStudentParentsName()%></td>
						<td><%=vo.getStudentParentsPhone()%></td>

					</tr>
					<%
					}
					%> 

				</table>
			</div>

			<div id="btns">
				<!-- 	<input type="button" value="PDF" id="PDF" /> <input type="button"
						value="EXCEL" id="EXCEL" /> <input type="button" value="인쇄"
						id="인쇄" /> -->
				<!-- 부트스트랩 이용한 버튼 -->
				<button type="button" class="btn btn-dark custom-btn-xs">PDF</button>
				<button type="button" class="btn btn-dark custom-btn-xs">EXCEL</button>
				<button type="button" class="btn btn-dark custom-btn-xs" id="printBtn">인쇄</button>

			</div>
		</div>
	</div>
</body>
</html>

