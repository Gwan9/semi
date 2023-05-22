
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

<!-- bootstrap CDN -->
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
	position: relative;
	width: 900px;
	height: 600px;
	background: #C7C7CA;
	border-radius: 13px;
	z-index: 5; /*  z-index 적용하려면 position을 줘야 함 
	 				 숫자가 작을수록 아래고 높을수록 위 */
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
	/* border-radius: 13px;
	border: 2px solid #FFCA2C; */
}

#student2 {
	position: relative;
	top: 60px;
	width: 800px;
	height: 330px;
	background: #212529;
	overflow: auto; /* 영역을 넘어갈 때 스크롤바 생성 */
	overflow-x: white-space; /* 가로 스크롤은 없애기 */
	/* border-radius: 13px; */
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
	position: relative;
	left: 710px;
	bottom: -75px;
}

.custom-btn-xs {
	font-size: 10px;
	padding: 5px 10px;
}

.sideMenu1 {
	position: relative;
	left: -480px;
	bottom: -130px;
	border-radius: 10px;
	background-color: #FFCA2C;
	width: 110px;
	height: 40px;
	z-index: 1;
	padding-left: 12px;
	padding-top: 8px;
}

.sideMenu2 {
	position: relative;
	left: -480px;
	bottom: -135px;
	border-radius: 10px;
	background-color: #212529;
	color: white;
	width: 110px;
	height: 40px;
	z-index: 2;
	padding-left: 12px;
	padding-top: 8px;
}
</style>


<!-- ajax CDN -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {

		// <조회>
		//분반, 강의명, 학년은 select타입 값을 선택하면 조회
		//* change 말고 selected 도 고려
		$("#lectureClass").on("change", search);
		$("#lectureName").on("change", search);
		$("#studentGrade").on("change", search);
		
		//이름은 조회버튼을 눌러야 조회
		$("#searchBtn").on("click", search);
		
		//날짜
		/* $("#date1").on("change", function () {
			var date1 = $("#date1").val();
		});
		$("#date2").on("change", function () {
			var date2 = $("#date2").val();
		}); */
		
		// <전체 체크>
		//checkAll을 누르면 모든 체크박스가 선택됨
		$("#checkAll").on("change", function() { //체크박스가 변경될때마다 실행
			var isChecked = $(this).prop("checked");
			// $(this).prop("checked") : boolean 타입
			// 체크박스가 선택되면 isChecked에 true가 담기고, 선택해제되면 false가 담김
			// $(this) : 현재 이벤트가 발생한 요소 - #checkAll
			// prop("checked") : 해당 요소의 checked 속성값을 가져오기 
			$("input[name='studentNo']").prop("checked", isChecked);
			// checkAll의 체크상태의 값을 studentNo의 checked 속성값에 담아줌
			// #로 id 값을 가져오면 첫번째 값만 가져오므로 name으로 가져오기!
		});

		// <인쇄>
		// 체크박스가 선택된 학생의 정보를 selectedList 배열에 담아서 그걸 print.jsp에 전달
		$("#printBtn").on(
				"click",
				function() {
					var selectedList = [];
					$("input[name='studentNo']:checked").each(
							//체크박스가 선택된 아이을 반복
							function() {
								var studentInfo = { //그 학생의 다른 정보를 담을 변수 studentInfo

									studentName : $(this).closest("tr").find( //2번째행 - 이름
									"td:nth-child(2)").text(),
									studentSchoolName : $(this).closest("tr")
											.find("td:nth-child(3)").text(),
									studentGrade : $(this).closest("tr").find(
											"td:nth-child(4)").text(),
									lectureClass : $(this).closest("tr").find(
											"td:nth-child(5)").text(),
									studentPhone : $(this).closest("tr").find(
											"td:nth-child(6)").text(),
									studentRegistDate : $(this).closest("tr")
											.find("td:nth-child(7)").text(),
									studentGender : $(this).closest("tr").find(
											"td:nth-child(8)").text(),
									studentParentsName : $(this).closest("tr")
											.find("td:nth-child(9)").text(),
									studentParentsPhone : $(this).closest("tr")
											.find("td:nth-child(10)").text(),
								}

								selectedList.push(studentInfo); // 체크된 학생의 정보를 selectedList 배열에 추가
							});
					//console.log(selectedList);

					// 선택된 체크박스 값을 print.jsp로 이동
					var url = "print.jsp?selectedList="
							+ encodeURIComponent(JSON.stringify(selectedList));

					window.location.href = url;

					// encodeURIComponent(JSON.stringify(selectedList))
					// selectedList 객체를 JSON 문자열로 변환한 후, 이를 URL에 안전한 형식으로 인코딩
					// 이렇게 인코딩된 문자열은 URL에 포함될 수 있으며, 서버 측에서 파라미터로 사용될 수 있음

					// selectedList 객체가 { name: "John Doe", age: 25 }와 같은 값을 가지고 있다면, 
					// JSON.stringify(selectedList)는 {"name":"John Doe","age":25}와 같은 JSON 형식의 문자열을 생성
					// 그리고 encodeURIComponent() 함수는 이 JSON 문자열을 URL에 안전한 형식으로 인코딩하여 사용
					//이렇게 함으로써 URL의 파라미터에 객체 데이터를 안전하게 전달
				});

		//<팝업>
		//$("#").on("click", show); //show() 함수는 파라미터값을 vo. 으로 전달을 해야하므로 하단에 
	});

	//----------------------------------------------------------------------------------------
	// 팝업 - #을 누르면 해당 studentName이 디테일 팝업으로 전달됨
	function show(studentName) {
		var w = 500;
		var h = 670;
		var x = 500;
		var y = 100;

		var spec = "width=" + w + ", height=" + h + ", left=" + x + ", top="
				+ y + ", menubar=0,resizeable=0,scrollbar=0,status=0,toolbar=0";

		//새창으로 .jsp를 열어주기
		window.open("detail.jsp?studentName=" + studentName, "_blank", spec);
		//(열려질 페이지, 새 창에서 페이지를 열라는 의미의 타겟(target) 속성 값, spec)
	}
	
	//셀렉트 옵션을 바꿨을 때 작동
	function search() {
		//기존 테이블 행 삭제 (비워주기)
		$("#sl tbody").empty();

		//체크 된 거 해제
		if ($("#checkAll").prop("checked") == true) //만약 체크되었다면
			//console.log("checked");
			$("#checkAll").prop("checked", false); //해제

		//Ajax 함수 호출
		$
				.ajax({
					url : "search.jsp",
					//넘겨줄 데이터 
					data : {
						"studentName" : $("#studentName").val(),
						"studentGrade" : $("#studentGrade").val(),
						"lectureClass" : $("#lectureClass").val(),
						"lectureName" : $("#lectureName").val(),
					
					/* 	"date1" : $("#date1").val(),
						"date2" : $("#date2").val() */
					},

					//성공했다면
					success : function(data) {
						var obj = JSON.parse(data);
						//보통 웹 애플리케이션에서 서버로부터 받은 데이터는 JSON 형식으로 전송 
						//그러나 JavaScript에서는 JSON 형식의 문자열을 직접 다루기보다 객체로 변환하여 사용하는 것이 더 편리 
						//JSON.parse()는 이러한 변환을 수행해주는 메서드

						//오브젝트의 값들을 가져와서 
						for (var i = 0; i < obj.length; i++) {
							var txt = "<tbody><tr><td><input type='checkbox' name='studentNo' id='studentNo' />"
									//이거는 이름을 누르면 페이지로 이동하는 방법	
									/* 	+ " </td><td><a href='detail.jsp?studentName="
										+ obj[i].studentName
										+ "'> "
										+ obj[i].studentName //'' ""때문에 애먹음
										+ " </a> </td><td> " */

									//이거는 이름을 누르면 팝업을 띄우는 방법
									//인자로 전달되는 obj[i].studentName이 문자열로 감싸줘야 함
									+ " </td><td><a href='#' onclick='show(\""
									+ obj[i].studentName
									+ "\");'>"
									+ obj[i].studentName
									+ " </a> </td><td> "

									+ obj[i].studentSchoolName
									+ " </td><td> "
									+ obj[i].studentGrade
									+ " </td><td> "
									+ obj[i].lectureClass
									+ " </td><td> "
									+ obj[i].studentPhone
									+ " </td><td> "
									+ obj[i].studentRegistDate.substring(0, 8)
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
		<!-- 사이드 메뉴바 -->
		<div class="sideMenu1">학생목록</div>
		<div class="sideMenu2">출결관리</div>

		<div id="studentList">
			<div id="student1">
				<h5>학생 정보 조회</h5>
				<hr style="border: 1.2px solid" />
				분반 <select name="lectureClass" id="lectureClass">
					<option value="전체">전체</option>
					<option value="A">A반</option>
					<option value="B">B반</option>
					<option value="C">C반</option>
				</select> 강의명 <select name="lectureName" id="lectureName">
					<option value="전체">전체</option>
					<option value="국어">국어</option>
					<option value="수리">수리</option>
					<option value="영어">영어</option>
				</select> 학년 <select name="studentGrade" id="studentGrade">
					<option value="전체">전체</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
				</select> 이름 <input type="text" name="studentName" id="studentName" /> <br />
				등록일 <input type="date" name="date1" id="date1" /> 부터 <input
					type="date" name="date2" id="date2" /> 까지

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
						<td><a href="#" onclick="show('<%=vo.getStudentName()%>');"><%=vo.getStudentName()%></a></td>
						<!-- 여기서 #은 실제로 사용되는 것이 아니라, 클릭 이벤트가 발생했을 때 페이지 이동을 방지하기 위해 빈 앵커 링크를 사용!
						이렇게 하면 클릭 이벤트가 발생해도 페이지가 새로고침되거나 이동하지 않고, 지정된 JavaScript 함수가 실행! -->
						<td><%=vo.getStudentSchoolName()%></td>
						<td><%=vo.getStudentGrade()%></td>
						<td><%=vo.getLectureClass()%></td>
						<td><%=vo.getStudentPhone()%></td>
						<td><%=vo.getStudentRegistDate().substring(0, 8)%></td>
						<td><%=vo.isStudentGender() == true ? "남" : "여"%></td>
						<td><%=vo.getStudentParentsName()%></td>
						<td><%=vo.getStudentParentsPhone()%></td>
					</tr>
					
					<%
					}
					%>
				</table>
			</div> <!-- student2 끝 -->

			<div id="btns">
				<!-- 	<input type="button" value="PDF" id="PDF" /> <input type="button"
						value="EXCEL" id="EXCEL" /> <input type="button" value="인쇄"
						id="인쇄" /> -->
				<!-- 부트스트랩 이용한 버튼 -->
				<button type="button" class="btn btn-dark custom-btn-xs">PDF</button>
				<button type="button" class="btn btn-dark custom-btn-xs">EXCEL</button>
				<button type="button" class="btn btn-dark custom-btn-xs"
					id="printBtn">인쇄</button>
			</div>
		</div> <!-- studentList 끝 -->
	</div> <!-- container끝  -->
</body>
</html>

