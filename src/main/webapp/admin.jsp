<%@page import="DAO.StudentDAO"%>
<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>admin.jsp</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>

<link rel="stylesheet" href="semi.css">
<link rel="stylesheet" href="mainpage.css">

<style type="text/css">
#container2 {
	margin: auto;
	position: relative;
	bottom: 100px;
	width: 900px;
	height: 700px;
	background: white;
	border-radius: 13px;
	z-index: 10;
	margin-top: 20px;
}

#student1 {
	height: 170px;
}

#student2 {
	margin-top: -105px;
}

table, th, td {
	width: 130px;
}

#data, .delete_btn {
	visibility: hidden;
}

#register_btn, #modify_btn {
	float: right;
}

.sideMenu1 {
	background-color: #7A9BBA;
	color: white;
}

.sideMenu5 {
	background-color: #FFCA2C;
	color: #686868;
}




</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<script type="text/javascript">
	$(function() {

		$("#teacher_or_student")
				.change(
						function() {

							$("#table_result tr:not(:first-child)").remove();

							// 강사 탭을 선택했을 경우
							if ($("#teacher_or_student").val() == "teacher_select") {
								// 테스트 콘솔
								console.log("교사 탭이 선택됨.");
								console.log($("option[value='teacher_select']")
										.text());

								// 안내 메세지 숨기기
								$("#msg1").css("visibility", "hidden");

								// 표 내용 바꾸기
								$("#selectLabel").text("강의명 : ")
								$("#labelName").text("강사명 : ");

								$("#th1").text("강사번호");
								$("#th2").text("강사명");
								$("#th3").text("ID");
								$("#th4").text("PW");
								$("#th5").text("연락처");
								$("#th6").text("담당 강의명");

								$("#op1").val("lectureKor").html("국어");
								$("#op2").val("lectureEng").html("영어");
								$("#op3").val("lectureMath").html("수학");

								$
										.ajax({
											type : "GET",
											async : true,
											url : "searchTeacherAllOK.jsp",
											dataType : "html",
											success : function(response,
													status, request) {
												//	 					console.log("성공");
												//	 					console.log(response);

												var obj = JSON.parse(response);

												console.log(obj);

												// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
												var txt = null;

												for (var i = 0; i < obj.length; i++) {

													txt = "<tr>"
															+ "<th><a href='teacher_detail.jsp?teacher_no="
															+ obj[i].no
															+ "'>"
															+ obj[i].no
															+ "</a></th>"
															+ "<td>"
															+ obj[i].name
															+ "</td>"
															+ "<td>"
															+ obj[i].id
															+ "</td>"
															+ "<td>"
															+ obj[i].pw
															+ "</td>"
															+ "<td>"
															+ obj[i].phone
															+ "</td>"
															+ "<td>"
															+ obj[i].subject
															+ "</td>"
															+ "<td>"
															+ obj[i].lectureStartDate
															+ "</td>"
															+ "<td>"
															+ obj[i].lectureEndDate
															+ "</td>"
															+ "<td><a href='deleteTeacherOk.jsp?teacher_no="
															+ obj[i].no
															+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
															+ "</tr>";

													// 덧붙이기
													$("#table_result").append(
															txt);

												}

											},
											error : function(response, status,
													request) {
												console.log("실패");
											}

										}); // ajax end

								$("#search")
										.on(
												"click",
												function() {

													$(
															"#table_result tr:not(:first-child)")
															.remove();

													// 클릭하면 검색 결과가 나오게

													$
															.ajax({
																type : "GET",
																async : true,
																url : "searchTeacherOK.jsp",
																dataType : "html",
																data : {
																	"nameText" : $(
																			"#nameText")
																			.val()
																},
																success : function(
																		response,
																		status,
																		request) {
																	//		 					console.log("성공");
																	//		 					console.log(response);

																	var obj = JSON
																			.parse(response);

																	console
																			.log(obj);

																	// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
																	var txt = null;

																	for (var i = 0; i < obj.length; i++) {

																		txt = "<tr>"
																				+ "<th><a href='teacher_detail.jsp?teacher_no="
																				+ obj[i].no
																				+ "'>"
																				+ obj[i].no
																				+ "</a></th>"
																				+ "<td>"
																				+ obj[i].name
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].id
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].pw
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].phone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].subject
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureStartDate
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].LectureEndDate
																				+ "</td>"
																				+ "<td><a href='deleteTeacherOk.jsp?teacher_no="
																				+ obj[i].no
																				+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																				+ "</tr>";

																		// 덧붙이기
																		$(
																				"#table_result")
																				.append(
																						txt);
																	}

																},
																error : function(
																		response,
																		status,
																		request) {
																	console
																			.log("실패");
																}

															}); // ajax end

												}); // search 버튼 클릭했을 때

								$("#lectureSelect")
										.change(
												function() {
													// 강의명을 선택한 경우, 그 강의명에 해당하는 행을 출력한다.

													//		 			console.log("과목 선택");
													//		 			console.log($("option[value='lectureEng']").text());

													$(
															"#table_result tr:not(:first-child)")
															.remove();

													// requestData 를 담을 객체를 만들고, 선택하는 조건에 따라 반복문을 만들어보자

													var requestData = {};

													if ($("#lectureSelect")
															.val() == "lectureKor") {
														requestData.lectureSelect = $(
																"option[value='lectureKor']")
																.text();
													} else if ($(
															"#lectureSelect")
															.val() == "lectureEng") {
														requestData.lectureSelect = $(
																"option[value='lectureEng']")
																.text();
													} else if ($(
															"#lectureSelect")
															.val() == "lectureMath") {
														requestData.lectureSelect = $(
																"option[value='lectureMath']")
																.text();
													}

													// 선택하면 검색 결과가 나오게

													$
															.ajax({
																type : "GET",
																async : true,
																url : "SearchTeacherSubjectOk.jsp",
																dataType : "html",
																data : requestData, // requestData를 담은 객체
																success : function(
																		response,
																		status,
																		request) {
																	//			 					console.log("성공");
																	//			 					console.log(response);

																	var obj = JSON
																			.parse(response);

																	console
																			.log(obj);

																	// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
																	var txt = null;

																	for (var i = 0; i < obj.length; i++) {

																		txt = "<tr>"
																				+ "<th><a href='teacher_detail.jsp?teacher_no="
																				+ obj[i].no
																				+ "'>"
																				+ obj[i].no
																				+ "</a></th>"
																				+ "<td>"
																				+ obj[i].name
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].id
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].pw
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].phone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].subject
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureStartDate
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureEndDate
																				+ "</td>"
																				+ "<td><a href='deleteTeacherOk.jsp?teacher_no="
																				+ obj[i].no
																				+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																				+ "</tr>";

																		// 덧붙이기
																		$(
																				"#table_result")
																				.append(
																						txt);

																	}

																	// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
																	var tableHeight = $(
																			'#table_result')
																			.outerHeight();
																	var containerHeight = tableHeight + 320;
																	$(
																			'#container1')
																			.css(
																					'height',
																					containerHeight
																							+ 'px');

																},
																error : function(
																		response,
																		status,
																		request) {
																	console
																			.log("실패");
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
								});
								;

								//		 		var registDateStartV = $("#registDateStart").val();
								//		 		var registDateEnd = $("#registDateEnd").val();

								function checkDate() {

									$("#table_result tr:not(:first-child)")
											.remove();

									if (registDateStart == true
											&& registDateEnd == true) {
										console.log("달력 모두 선택됨.");

										$
												.ajax({
													type : "GET",
													async : true,
													url : "SearchTeacherDateOK.jsp",
													dataType : "html",
													data : {
														"registDateStart" : $(
																"#registDateStart")
																.val(),
														"registDateEnd" : $(
																"#registDateEnd")
																.val()
													},
													success : function(
															response, status,
															request) {
														//		 						console.log("성공");

														var obj = JSON
																.parse(response);

														console.log(obj);

														var txt = null;

														for (var i = 0; i < obj.length; i++) {

															txt = "<tr>"
																	+ "<th><a href='teacher_detail.jsp?teacher_no="
																	+ obj[i].no
																	+ "'>"
																	+ obj[i].no
																	+ "</a></th>"
																	+ "<td>"
																	+ obj[i].name
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].id
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].pw
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].phone
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].subject
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].lectureStartDate
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].lectureEndDate
																	+ "</td>"
																	+ "<td><a href='deleteTeacherOk.jsp?teacher_no="
																	+ obj[i].no
																	+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																	+ "</tr>";

															// 덧붙이기
															$("#table_result")
																	.append(txt);
														}

													},
													error : function(response,
															status, request) {
														console.log("실패");
													}
												}); // ajax end

									}
								}

								// 학생 탭을 선택했다면
							} else if ($("#teacher_or_student").val() == "student_select") {
								// 테스트 콘솔
								console.log("학생 탭이 선택됨.");
								console.log($("option[value='student_select']")
										.text());

								$("#table_result tr:not(:first-child)")
										.remove();

								// 안내 메세지 숨기기
								$("#msg1").css("visibility", "hidden");

								// 표 내용 바꾸기
								$("#selectLabel").text("수강반 : ")
								$("#labelName").text("학생명 : ");

								$("#th1").text("학생번호");
								$("#th2").text("학생명");
								$("#th3").text("연락처");
								$("#th4").text("학부모 연락처");
								$("#th5").text("학교명");
								$("#th6").text("수강반");

								$("#op1").val("lectureClsA").html("A");
								$("#op2").val("lectureClsB").html("B");
								$("#op3").val("lectureClsC").html("C");

								// 학생 목록 전체 출력
								$
										.ajax({
											type : "GET",
											async : true,
											url : "searchStudentAllOK.jsp",
											dataType : "html",
											success : function(response,
													status, request) {
												//	 					console.log("성공");
												//	 					console.log(response);

												var obj = JSON.parse(response);

												console.log(obj);

												// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
												var txt = null;

												for (var i = 0; i < obj.length; i++) {

													txt = "<tr>"
															+ "<th><a href='student_detail.jsp?student_no="
															+ obj[i].no
															+ "'>"
															+ obj[i].no
															+ "</a></th>"
															+ "<td>"
															+ obj[i].name
															+ "</td>"
															+ "<td>"
															+ obj[i].phone
															+ "</td>"
															+ "<td>"
															+ obj[i].parentsPhone
															+ "</td>"
															+ "<td>"
															+ obj[i].schoolName
															+ "</td>"
															+ "<td>"
															+ obj[i].lectureName
															+ "</td>"
															+ "<td>"
															+ obj[i].lectureStartDate
															+ "</td>"
															+ "<td>"
															+ obj[i].lectureEndDate
															+ "</td>"
															+ "<td><a href='deleteStudentOk.jsp?student_no="
															+ obj[i].no
															+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
															+ "</tr>";

													// 덧붙이기
													$("#table_result").append(
															txt);

												}

											},
											error : function(response, status,
													request) {
												console.log("실패");
											}

										}); // ajax end

								$("#search")
										.on(
												"click",
												function() {

													$(
															"#table_result tr:not(:first-child)")
															.remove();

													// 클릭하면 검색 결과가 나오게

													$
															.ajax({
																type : "GET",
																async : true,
																url : "searchStudentOK.jsp",
																dataType : "html",
																data : {
																	"nameText" : $(
																			"#nameText")
																			.val()
																},
																success : function(
																		response,
																		status,
																		request) {
																	//		 					console.log("성공");
																	//		 					console.log(response);

																	var obj = JSON
																			.parse(response);

																	console
																			.log(obj);

																	// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
																	var txt = null;

																	for (var i = 0; i < obj.length; i++) {

																		txt = "<tr>"
																				+ "<th><a href='student_detail.jsp?student_no="
																				+ obj[i].no
																				+ "'>"
																				+ obj[i].no
																				+ "</a></th>"
																				+ "<td>"
																				+ obj[i].name
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].phone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].parentsPhone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].schoolName
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureName
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureStartDate
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureEndDate
																				+ "</td>"
																				+ "<td><a href='deleteStudentOk.jsp?student_no="
																				+ obj[i].no
																				+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																				+ "</tr>";

																		// 덧붙이기
																		$(
																				"#table_result")
																				.append(
																						txt);
																	}

																},
																error : function(
																		response,
																		status,
																		request) {
																	console
																			.log("실패");
																}

															}); // ajax end

												}); // search 버튼 클릭했을 때

								$("#lectureSelect")
										.change(
												function() {
													// 강의명을 선택한 경우, 그 강의명에 해당하는 행을 출력한다.

													//		 			console.log("과목 선택");
													//		 			console.log($("option[value='lectureEng']").text());

													$(
															"#table_result tr:not(:first-child)")
															.remove();

													// requestData 를 담을 객체를 만들고, 선택하는 조건에 따라 반복문을 만들어보자

													var requestData = {};

													if ($("#lectureSelect")
															.val() == "lectureClsA") {
														requestData.lectureSelect = $(
																"option[value='lectureClsA']")
																.text();
													} else if ($(
															"#lectureSelect")
															.val() == "lectureClsB") {
														requestData.lectureSelect = $(
																"option[value='lectureClsB']")
																.text();
													} else if ($(
															"#lectureSelect")
															.val() == "lectureClsC") {
														requestData.lectureSelect = $(
																"option[value='lectureClsC']")
																.text();
													}

													// 선택하면 검색 결과가 나오게

													$
															.ajax({
																type : "GET",
																async : true,
																url : "SearchStudentSubjectOk.jsp",
																dataType : "html",
																data : requestData, // requestData를 담은 객체
																success : function(
																		response,
																		status,
																		request) {
																	//			 					console.log("성공");
																	//			 					console.log(response);

																	var obj = JSON
																			.parse(response);

																	console
																			.log(obj);

																	// 2. 버튼 클릭 시 tr의 태그 자손으로 출력
																	var txt = null;

																	for (var i = 0; i < obj.length; i++) {

																		txt = "<tr>"
																				+ "<th><a href='student_detail.jsp?student_no="
																				+ obj[i].no
																				+ "'>"
																				+ obj[i].no
																				+ "</a></th>"
																				+ "<td>"
																				+ obj[i].name
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].phone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].parentsPhone
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].schoolName
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureName
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureStartDate
																				+ "</td>"
																				+ "<td>"
																				+ obj[i].lectureEndDate
																				+ "</td>"
																				+ "<td><a href='deleteStudentOk.jsp?student_no="
																				+ obj[i].no
																				+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																				+ "</tr>";

																		// 덧붙이기
																		$(
																				"#table_result")
																				.append(
																						txt);
																	}

																},
																error : function(
																		response,
																		status,
																		request) {
																	console
																			.log("실패");
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
								});
								;

								//		 		var registDateStartV = $("#registDateStart").val();
								//		 		var registDateEnd = $("#registDateEnd").val();

								function checkDate() {

									$("#table_result tr:not(:first-child)")
											.remove();

									if (registDateStart == true
											&& registDateEnd == true) {
										console.log("달력 모두 선택됨.");

										$
												.ajax({
													type : "GET",
													async : true,
													url : "SearchStudentDateOK.jsp",
													dataType : "html",
													data : {
														"registDateStart" : $(
																"#registDateStart")
																.val(),
														"registDateEnd" : $(
																"#registDateEnd")
																.val()
													},
													success : function(
															response, status,
															request) {
														//		 						console.log("성공");

														var obj = JSON
																.parse(response);

														console.log(obj);

														var txt = null;

														for (var i = 0; i < obj.length; i++) {

															txt = "<tr>"
																	+ "<th><a href='student_detail.jsp?student_no="
																	+ obj[i].no
																	+ "'>"
																	+ obj[i].no
																	+ "</a></th>"
																	+ "<td>"
																	+ obj[i].name
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].phone
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].parentsPhone
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].schoolName
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].lectureName
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].lectureStartDate
																	+ "</td>"
																	+ "<td>"
																	+ obj[i].lectureEndDate
																	+ "</td>"
																	+ "<td><a href='deleteStudentOk.jsp?student_no="
																	+ obj[i].no
																	+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
																	+ "</tr>";

															// 덧붙이기
															$("#table_result")
																	.append(txt);
														}

													},
													error : function(response,
															status, request) {
														console.log("실패");
													}
												}); // ajax end

									}
								}

							} else {
								// 안내 메세지 표시하기
								$("#msg1").css("visibility", "visible");
							}
						})

		// 등록 버튼을 누르면 빈 페이지가 나오는 걸로
		$("#register_btn").on("click", function() {
			if ($("#teacher_or_student").val() == "teacher_select") {
				window.location.href = "teacher_regist_form.jsp";
			} else if ($("#teacher_or_student").val() == "student_select") {
				window.location.href = "student_regist_form.jsp";
			}
		}) // register 버튼 end

		// 초기화 버튼을 눌렀을 때 table의 tr 태그는 모두 삭제 (첫 행은 제외) + 달력 안에 값도 초기화
		$("#reset").on("click", function() {

			$("#table_result tr:not(:first-child)").remove();
			$("#registDateStart, #registDateEnd").val("");
			$("#nameText").val("");

		})

		// 편집 버튼을 눌렀을 때 삭제 버튼이 눈에 보이게
		$("#modify_btn").on("click", function() {
			$(".delete_btn").css("visibility", "visible");
		});

		// 		-----------------------------------------------------------------------------------------

		// 회계 프로그램

		// 월을 선택했을 경우
		$("#monthSelect")
				.change(
						function() {
							$("#table_accounting_result tr:not(:first-child)")
									.remove();

							$("#account_modify_btn").css("visibility",
									"visible");

							// 안내 메세지 숨기기
							$("#msg2").css("visibility", "hidden");

							// 선택한 달 value 값 가져오기
							var selectedMonth = $(this).val();

							// 선택한 달의 말일을 가져오기(0일 == 말일)
							var lastDay = new Date(2023,
									parseInt(selectedMonth), 0).getDate();

							// 조회할 구간의 처음
							var startDate = "01-" + selectedMonth
									+ "-2023 00:00:00";
							// 조회할 구간의 마지막
							var endDate = lastDay + "-" + selectedMonth
									+ "-2023 23:59:59";

							// 		    console.log(selectedMonth + " : " + lastDay + " : " + startDate + " : " + endDate);

							// container 의 css 값을 표의 마지막 행으로부터 40px 더한 값으로 주기
							var tableHeight = $('#table_accounting_result')
									.outerHeight();
							var containerHeight = tableHeight + 650;
							$('#container2').css('height',
									containerHeight + 'px');

							$
									.ajax({
										type : "GET",
										async : true,
										url : "SearchAccountingDateOK.jsp",
										dataType : "html",
										data : {
											"startDate" : startDate,
											"endDate" : endDate
										},
										success : function(response, status,
												request) {
											// console.log("성공");

											var obj = JSON.parse(response);

											console.log(obj);

											var txt = null;

											for (var i = 0; i < obj.length; i++) {

												txt = "<tr>"
														+ "<th><a href='register_detail.jsp?class_register_no="
														+ obj[i].registerno
														+ "'>"
														+ obj[i].registerno
														+ "</a></th>"
														+ "<td>"
														+ obj[i].lectureClass
														+ "</td>"
														+ "<td>"
														+ obj[i].studentName
														+ "</td>"
														+ "<td>"
														+ obj[i].isPay
														+ "</td>"
														+ "<td>"
														+ obj[i].lectureName
														+ "</td>"
														+ "<td>"
														+ obj[i].lectureTuition
														+ "</td>"
														+ "<td>"
														+ obj[i].payType
														+ "</td>"
														+ "<td>"
														+ obj[i].lectureStartDate
														+ "</td>"
														+ "<td>"
														+ obj[i].lectureEndDate
														+ "</td>"
														+ "<td>"
														+ obj[i].studentDueDate
														+ "</td>"
														+ "<td>"
														+ obj[i].studentParentsPhone
														+ "</td>"
														+ "<td><a href='deleteStudentOk.jsp?student_no="
														+ obj[i].no
														+ "'><input type='button' class='delete_btn' value='삭제' /></a></td>"
														+ "</tr>";

												// 덧붙이기
												$("#table_accounting_result")
														.append(txt);
											}

										},
										error : function(response, status,
												request) {
											console.log("실패");
										}
									}); // ajax end

						});

		// 편집 버튼을 눌렀을 때 삭제 버튼이 눈에 보이게
		$("#account_modify_btn").on("click", function() {
			$(".delete_btn").css("visibility", "visible");
		});

	});
</script>

</head>
<body>

<jsp:include page="mainheader.jsp"></jsp:include>

	<div id="canvas">
		<a href="studentList.jsp"><div class="sideMenu1">학생목록</div></a>
		<a href="checkList.jsp"><div class="sideMenu2">출결관리</div></a>
		<a href=""><div class="sideMenu3">수업관리</div></a>
		<a href="classNote.jsp"><div class="sideMenu4">학습일지</div></a>
		<div class="sideMenu5">관리자</div>

		<div id="container">
			<div id="management">
				<div id="student1">
					<h5>학생/강사 관리</h5>
					<hr />
					<div>
						<select id="teacher_or_student">
							<option value="">선택</option>
							<option value="teacher_select">강사</option>
							<option value="student_select">학생</option>
						</select>

						<div id="msg1" style="font-weight: bold">강사 또는 학생을 선택해주세요!</div>



						<span id="lectureSelect_box"> <label class="label" for="" id="selectLabel">강의명 : </label> <!-- 			<input type="text" name="강의명" id="lecture_name" value="" /> --> <select id="lectureSelect">
								<option value="">선택</option>
								<option id="op1" value=""></option>
								<option id="op2" value=""></option>
								<option id="op3" value=""></option>
						</select>
						</span> <span id="nameBox"> <label id="labelName" for=""></label> <input type="text" name="nameText" id="nameText" value="" />
						</span> <br /> <span id="student_regist_search"> <label class="label" for="">강의 일자별 조회 : </label> <input type="date" name="registDateStart" id="registDateStart" />~<input type="date" name="registDateEnd" id="registDateEnd" />
						</span> <input type="button" id="search" value="조회" /> <input type="button" id="reset" value="초기화" /> <input type="button" id="register_btn" value="등록" align="right" /> <input type="button" id="modify_btn" value="편집" align="right" />


					</div>
				</div>
				<!-- 				<label><input type="radio" name="teacher_or_student" -->
				<!-- 					id="teacher_or_student" value="teacher" />교사</label> <label><input -->
				<!-- 					type="radio" name="teacher_or_student" id="teacher_or_student" -->
				<!-- 					value="student" />학생</label> -->

				<div id="student2">
					<div id="table">
						<table class="tableCss" id="table_result">
							<tr>
								<th id="th1"></th>
								<th id="th2"></th>
								<th id="th3"></th>
								<th id="th4"></th>
								<th id="th5"></th>
								<th id="th6"></th>
								<th>강의시작일</th>
								<th>강의종료일</th>
								<th>비고</th>
							</tr>


						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="divider"></div>

		<!-- 			-------------------------------------------------------------------------- -->

		<div id="container2">
			<div id="student1">
				<h5>회계 관리</h5>
				<hr />
				<div>
					<select id="monthSelect">
						<option value="">선택</option>
						<option value="01">1월</option>
						<option value="02">2월</option>
						<option value="03">3월</option>
						<option value="04">4월</option>
						<option value="05">5월</option>
						<option value="06">6월</option>
						<option value="07">7월</option>
						<option value="08">8월</option>
						<option value="09">9월</option>
						<option value="10">10월</option>
						<option value="11">11월</option>
						<option value="12">12월</option>
					</select> <span> <input type="button" id="account_modify_btn" value="편집" />
					</span>

					<div id="msg2" style="font-weight: bold">조회하려는 달을 선택해주세요!</div>

				</div>
				<br /> <br /> <br /> <br /> <br />
				<div id="student2">
					<div>
						<table class="tableCss" id="table_accounting_result">
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
								<th>비고</th>

							</tr>

							<%
							StudentDAO dao = new StudentDAO();

							ArrayList<ClassNoteVO> accountingList = dao.accountingSelectByAll();

							int totalPayment = 0; // 납입금 총액을 저장할 변수 초기화
							int totalOutstanding = 0; // 미납금 총액을 저장할 변수 초기화

							for (ClassNoteVO vo : accountingList) {

								int lectureTuition = vo.getLectureTuition();
								boolean isPay = vo.isPay();

								if (isPay) {
									totalPayment += lectureTuition;
								} else {
									totalOutstanding += lectureTuition;
								}
							%>
							<tr>

								<td><%=vo.getClassRegisterNo()%></td>
								<td><%=vo.getLectureClass()%></td>
								<td><%=vo.getStudentName()%></td>
								<td><%=vo.isPay()%></td>
								<td><%=vo.getLectureName()%></td>
								<td><%=vo.getLectureTuition()%></td>
								<td><%=vo.getPayType()%></td>
								<td><%=vo.getLectureStartDate()%></td>
								<td><%=vo.getLectureEndDate()%></td>
								<td><%=vo.getStudentDueDate()%></td>
								<td><%=vo.getStudentParentsPhone()%></td>
								<td></td>

							</tr>
							<%
							}
							%>
						</table>

						<table class="tableCss">
							<tr class="account_sum">
								<th colspan="4">납입금 총액</th>
								<th colspan="3"><%=totalPayment%>원</th>
								<th rowspan="2" colspan="2">누적 총액</th>
								<th rowspan="2" colspan="2"><%=totalPayment + totalOutstanding%>원</th>
							</tr>

							<tr class="account_sum">
								<th colspan="4">미납금 총액</th>
								<th colspan="3"><%=totalOutstanding%>원</th>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>