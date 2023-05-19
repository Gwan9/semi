<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin.jsp</title>

<style type="text/css">
#container {
	width: 1000px;
	background: #E2E2E2;
	margin: 0px auto;
	/* 	화면에 보이지 않지만 자리를 차지함. */
	visibility: hidden;
}

#teacher_or_student{
	width: 100px;
	height: 40px;
	margin-left: 90px;
	margin-top: 50px;
}

#lectureSelect {
	width: 200px;
	height: 40px;
	margin-right: 20px;
}

#nameText {
	width: 200px;
	height: 40px;
}

#registDateStart {
	width: 100px;
	height: 40px;
	margin-right: 10px;
	margin-top: 10px;
	margin-bottom: 20px;
}

#registDateEnd {
	width: 100px;
	height: 40px;
	margin-left: 10px;
	margin-top: 10px;
}

.label{
	margin-left: 90px;
}

#msg1 {
	margin-left: 90px;
}

#labelName {
	margin-left: 0px;
}

table {
	width: 900px;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	margin: 0px auto;
}

#search, #reset {
	width: 80px;
	height: 40px;
	margin-left: 20px;
}

#register_or_modify {
	text-align: right;
	margin-right: 90px;
}

#register_btn, #modify_btn {
	width: 80px;
	height: 40px;
	margin-bottom: 30px;
}

#data {
	display: none;
}

/* ------------------------------------------------------- */

#container2 {
	width: 1000px;
	background: #FFE4C4;
	margin: 0px auto;
	visibility: visible;
}


#monthSelect {
	width: 100px;
	height: 40px;
	margin-top: 80px;
	margin-left: 90px;
}


#msg2 {
	margin-left: 90px;
	margin-bottom: 40px;
}


}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {
		// 창이 켜지고, 크기가 조정될 때마다 #teacher_or_student의 margin-left 값을 읽어와 #container에 동일한 값으로 설정
		$(window).on("load", function() {
			var marginLeft = $("#container").css("margin-left");
			$("#teacher_or_student, #monthSelect").css("margin-left", marginLeft);
			$("#msg1, #msg2").css("margin-left", marginLeft);
		})
		$(window).on("resize", function() {
			var marginLeft = $("#container").css("margin-left");
			$("#teacher_or_student, #monthSelect").css("margin-left", marginLeft);
			$("#msg1, #msg2").css("margin-left", marginLeft);
		});
		
		
		// 등록 버튼을 누르면 빈 페이지가 나오는 걸로
		$("#register_btn").on("click", function() {
			window.location.href = "teacher_regist_form.jsp";
		}) // register 버튼 end
		
		
		$("#teacher_or_student").change(function() {
			
			// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
			var tableHeight = $('#table_result').outerHeight();
			var containerHeight = tableHeight + 270;
			$('#container').css('height', containerHeight + 'px');
			
			
			
			
			if($("#teacher_or_student").val() == "teacher_select") {
				// 테스트 콘솔
				console.log("교사 탭이 선택됨.");
				console.log($("option[value='teacher_select']").text());
				
				// 안내 메세지 숨기기
				$("#msg1").css("visibility", "hidden");
				// 표 표시하기
				$("#container").css("visibility", "visible");
				
				// 표 내용 바꾸기
				$("#labelName").text("강사명 : ");
				
				$("#th1").text("강사번호");
				$("#th2").text("강사명");
				$("#th3").text("ID");
				$("#th4").text("PW");
				$("#th5").text("연락처");
				$("#th6").text("담당 강의명");
				
				
			} else if ($("#teacher_or_student").val() == "student_select") {
				// 테스트 콘솔
				console.log("학생 탭이 선택됨.");
				console.log($("option[value='student_select']").text());
				
				// 안내 메세지 숨기기
				$("#msg1").css("visibility", "hidden");
				// 표 표시하기
				$("#container").css("visibility", "visible");
				
				// 표 내용 바꾸기
				$("#labelName").text("학생명 : ");
				
				$("#th1").text("학생번호");
				$("#th2").text("학생명");
				$("#th3").text("연락처");
				$("#th4").text("학부모 연락처");
				$("#th5").text("학교명");
				$("#th6").text("수강반");
				
				
			} else {
				// 안내 메세지 표시하기
				$("#msg1").css("visibility", "visible");
				// 표 숨기기
				$("#container").css("visibility", "hidden");
			}
		})
		
		
		$("#search").on("click", function() {
			
			// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
			var tableHeight = $('#table_result').outerHeight();
			var containerHeight = tableHeight + 270;
			$('#container').css('height', containerHeight + 'px');
			
			
			// 클릭하면 검색 결과가 나오게
			
			$.ajax({
				type: "GET",
				async: true,
				url: "SearchTeacherOK.jsp",
				dataType: "html",
				data: {"nameText":$("#nameText").val()},
				success: function(response, status, request) {
// 					console.log("성공");
// 					console.log(response);

					var obj = JSON.parse(response);
					
					console.log(obj);
					
					// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
					var txt = null;
					
					for(var i=0; i<obj.length; i++) {
						
						txt = "<tr>"
					        + "<th><a href='teacher_detail.jsp?teacher_no=" + obj[i].no + "'>" + obj[i].no + "</a></th>"
					        + "<td>" + obj[i].name + "</td>"
					        + "<td>" + obj[i].id + "</td>"
					        + "<td>" + obj[i].pw + "</td>"
					        + "<td>" + obj[i].phone + "</td>"
					        + "<td>" + obj[i].subject + "</td>"
					        + "<td>" + obj[i].lectureStartDate + "</td>"
					        + "<td>" + obj[i].LectureEndDate + "</td>"
					        + "<td><a href='DeleteTeacherOk.jsp?teacher_no=" + obj[i].no + "'><input type='button' id='delete_btn' value='삭제' /></a></td>"
					        + "</tr>";

							// 덧붙이기
							$("#table_result").append(txt);
					}
	
				},
				error: function(response, status, request) {
					console.log("실패");
				}
	
			}); // ajax end
			
		}); // search 버튼 클릭했을 때
		
		
		
		
		// 초기화 버튼을 눌렀을 때 table의 tr 태그는 모두 삭제 (첫 행은 제외) + 달력 안에 값도 초기화
		$("#reset").on("click", function() {
			
			$("#table_result tr:not(:first-child)").remove();
			$("#registDateStart, #registDateEnd").val("");
			$("#labelName").val("");
			
			// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
			var tableHeight = $('#table_result').outerHeight();
			var containerHeight = tableHeight + 270;
			$('#container').css('height', containerHeight + 'px');
			
		})
		
		
		
		
		$("#lectureSelect").change(function() {
			// 강의명을 선택한 경우, 그 강의명에 해당하는 행을 출력한다.
			
// 			console.log("과목 선택");
// 			console.log($("option[value='lectureEng']").text());

			// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
			var tableHeight = $('#table_result').outerHeight();
			var containerHeight = tableHeight + 320;
			$('#container').css('height', containerHeight + 'px');
			
			
			// requestData 를 담을 객체를 만들고, 선택하는 조건에 따라 반복문을 만들어보자
			
			var requestData = {};
			
			
			if ($("#lectureSelect").val() == "lectureKor") {
				requestData.lectureSelect = $("option[value='lectureKor']").text();
			} else if ($("#lectureSelect").val() == "lectureEng") {
				requestData.lectureSelect = $("option[value='lectureEng']").text();
			} else if ($("#lectureSelect").val() == "lectureMath") {
				requestData.lectureSelect = $("option[value='lectureMath']").text();
			}
				
				// 선택하면 검색 결과가 나오게
				
				$.ajax({
					type: "GET",
					async: true,
					url: "SearchTeacherSubjectOk.jsp",
					dataType: "html",
					data: requestData, // requestData를 담은 객체
					success: function(response, status, request) {
//	 					console.log("성공");
//	 					console.log(response);

						var obj = JSON.parse(response);
						
						console.log(obj);
						
						// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
						var txt = null;
						
						for(var i=0; i<obj.length; i++) {
							
							txt = "<tr>"
						        + "<th><a href='teacher_detail.jsp?teacher_no=" + obj[i].no + "'>" + obj[i].no + "</a></th>"
						        + "<td>" + obj[i].name + "</td>"
						        + "<td>" + obj[i].id + "</td>"
						        + "<td>" + obj[i].pw + "</td>"
						        + "<td>" + obj[i].phone + "</td>"
						        + "<td>" + obj[i].subject + "</td>"
						        + "<td>" + obj[i].lectureStartDate + "</td>"
						        + "<td>" + obj[i].lectureEndDate + "</td>"
						        + "<td><a href='DeleteTeacherOk.jsp?teacher_no=" + obj[i].no + "'><input type='button' id='delete_btn' value='삭제' /></a></td>"
						        + "</tr>";

								// 덧붙이기
								$("#table_result").append(txt);
						}
		
					},
					error: function(response, status, request) {
						console.log("실패");
					}
		
				}); // ajax end
			
		}) // end
	
		
		
		// 기간을 넣어서 조회하려면 어떻게?
		// 각 달력에 일자를 선택한 경우, 그 결과 값을 checkDate() 함수에 넘겨, 그리고 결과가 두개가 있어야지만 콘솔에 메세지를 출력할 수 있도록 if 문 전개하자.
		
		var registDateStart = false; // registDateStart 이 눌렸는지, 안 눌렸는 지 boolean  형태로 넣기
		var registDateEnd = false;
		
		$("#registDateStart").change(function() {
			console.log($("#registDateStart").val());
			registDateStart = true;
			checkDate();
		});
		
		$("#registDateEnd").change(function() {
			console.log($("#registDateEnd").val());
			registDateEnd = true;
			checkDate();
		});;
		
// 		var registDateStartV = $("#registDateStart").val();
// 		var registDateEnd = $("#registDateEnd").val();
		
		function checkDate() {

				// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
				var tableHeight = $('#table_result').outerHeight();
				var containerHeight = tableHeight + 650;
				$('#container').css('height', containerHeight + 'px');
				
			if(registDateStart == true && registDateEnd == true) {
				console.log("달력 모두 선택됨.");
				
				$.ajax({
					type: "GET",
					async: true,
					url: "SearchTeacherDateOK.jsp",
					dataType: "html",
					data: {"registDateStart":$("#registDateStart").val(),"registDateEnd":$("#registDateEnd").val()},
					success: function(response, status, request) {
// 						console.log("성공");
						
						var obj = JSON.parse(response);
						
						console.log(obj);
						
						var txt = null;
						
						for(var i=0; i<obj.length; i++) {
							
							txt = "<tr>"
						        + "<th><a href='teacher_detail.jsp?teacher_no=" + obj[i].no + "'>" + obj[i].no + "</a></th>"
						        + "<td>" + obj[i].name + "</td>"
						        + "<td>" + obj[i].id + "</td>"
						        + "<td>" + obj[i].pw + "</td>"
						        + "<td>" + obj[i].phone + "</td>"
						        + "<td>" + obj[i].subject + "</td>"
						        + "<td>" + obj[i].lectureStartDate + "</td>"
						        + "<td>" + obj[i].lectureEndDate + "</td>"
						        + "<td><a href='DeleteTeacherOk.jsp?teacher_no=" + obj[i].no + "'><input type='button' id='delete_btn' value='삭제' /></a></td>"
						        + "</tr>";

								// 덧붙이기
								$("#table_result").append(txt);
						}
						
						
					},
					error: function(response, status, request) {
						console.log("실패");
					}
				}); // ajax end
				
				
			}
		}

		
// 		-----------------------------------------------------------------------------------------
		
		

		
		
	});
</script>

</head>
<body>
		<div>
			<select id="teacher_or_student">
				<option value="">선택</option>
				<option value="teacher_select">강사</option>
				<option value="student_select">학생</option>
			</select>
			
			<div id="msg1" style="font-weight: bold">
				강사 또는 학생을 선택해주세요!
			</div>
		</div>
<!-- 				<label><input type="radio" name="teacher_or_student" -->
<!-- 					id="teacher_or_student" value="teacher" />교사</label> <label><input -->
<!-- 					type="radio" name="teacher_or_student" id="teacher_or_student" -->
<!-- 					value="student" />학생</label> -->
			
			
		<div id="container">

			<br /> 
			
			<span id="lectureSelect_box"> 
				<label class="label" for="">강의명 : </label>
				
<!-- 			<input type="text" name="강의명" id="lecture_name" value="" /> -->
				
				<select id="lectureSelect">
					<option value="">선택</option>
					<option value="lectureKor">국어</option>
					<option value="lectureEng">영어</option>
					<option value="lectureMath">수학</option>
				</select>	
			</span> 
			
			<span id="nameBox"> 
				<label id="labelName" for=""></label> 
				<input type="text" name="nameText" id="nameText" value="" />
			</span> 
			
			<br /> 
			
			<span id="student_regist_search"> 
				<label class="label" for="">강의 일자별 조회 : </label>
				<input type="date" name="registDateStart" id="registDateStart" />~<input type="date" name="registDateEnd" id="registDateEnd" />
			</span> 
			
			<input type="button" id="search" value="조회" />
			<input type="button" id="reset" value="초기화" />

			<div id="register_or_modify">
				<input type="button" id="register_btn" value="등록" /> 
				<input type="button" id="modify_btn" value="편집" />
			</div>

			<div id="table">
				<table id="table_result">
					<tr>
						<th id="th1" ></th>
						<th id="th2" ></th>
						<th id="th3" ></th>
						<th id="th4" ></th>
						<th id="th5" ></th>
						<th id="th6" ></th>
						<th>강의시작일</th>
						<th>강의종료일</th>
						<th>비고</th>
					</tr>
		<%
			StudentDAO dao = new StudentDAO();
				
			ArrayList<ClassNoteVO> list = dao.teacherSelectAll();
			
			for(ClassNoteVO vo : list) {
		%>	
				<tr>
					<th><a href="teacher_detail.jsp?teacher_no=<%= vo.getTeacherNo() %>"><%= vo.getTeacherNo() %></a></th>
					<td id="tableName"><%= vo.getTeacherName() %></td>
					<td><%= vo.getTeacherId() %></td>
					<td><%= vo.getTeacherPw() %></td>
					<td><%= vo.getTeacherPhone() %></td>
					<td><%= vo.getTeacherSubject() %></td>
					<td><%= vo.getLectureStartDate() %></td>
					<td><%= vo.getLectureEndDate() %></td>
					<td><a href="DeleteTeacherOk.jsp?teacher_no=<%= vo.getTeacherNo() %>"><input type="button" id="delete_btn" value="삭제" /></a></td>
				</tr>
					
					
		
		<%
			}
			
		%>

					</table>
				</div>
			</div>
			
<!-- 			-------------------------------------------------------------------------- -->
			
			<div>
				<select id="monthSelect">
					<option value="">선택</option>
					<option value="jan">1월</option>
					<option value="feb">2월</option>
					<option value="mar">3월</option>
					<option value="apr">4월</option>
					<option value="may">5월</option>
					<option value="jun">6월</option>
					<option value="jul">7월</option>
					<option value="aug">8월</option>
					<option value="sep">9월</option>
					<option value="oct">10월</option>
					<option value="nov">11월</option>
					<option value="dec">12월</option>
				</select>
				
				<div id="msg2" style="font-weight: bold">
					조회하려는 달을 선택해주세요!
				</div>
			</div>
			
			
			<div id="container2">
				<div>
					<table>
						<tr>
							<th>수강번호</th>
							<th>수강반</th>
							<th>학생명</th>
							<th>납부여부</th>
							<th>강의명</th>
							<th>수강료</th>
							<th>납부방법</th>
							<th>수강시작일</th>
							<th>수강종료일</th>
							<th>납부일</th>
							<th>학부모 전화번호</th>
						
						</tr>
						
					<%
					dao = new StudentDAO();
					
					ArrayList<ClassNoteVO> accountingList = dao.teacherSelectAllAccounting();
					
					for(ClassNoteVO vo : accountingList) {
					%>	
						<tr>
							
							<td><%= vo.getClass_registerNo() %></td>
							<td><%= vo.getLectureClass() %></td>
							<td><%= vo.getStudentName() %></td>
							<td><%= vo.isPay() %></td>
							<td><%= vo.getLectureName() %></td>
							<td><%= vo.getLectureTuition() %></td>
							<td><%= vo.getPayType() %></td>
							<td><%= vo.getLectureStartDate() %></td>
							<td><%= vo.getLectureEndDate() %></td>
							<td><%= vo.getStudentDueDate() %></td>
							<td><%= vo.getStudentParentsPhone() %></td>
						
							
						</tr>
					<%
					}
					%>
					
					<tr>
						<td colspan="4">납입금 총액</td>
						<td colspan="3">2</td>
						<td rowspan="2" colspan="2">누적 총액</td>
						<td rowspan="2" colspan="2">5</td>		
					</tr>
					
					<tr>
						<td colspan="4">미납금 총액</td>
						<td colspan="3">4</td>
					</tr>
					
					
					</table>
				
				</div>
			</div>	
		
</body>
</html>