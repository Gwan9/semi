<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학습 일지 작성</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="semi.css">
<link rel="stylesheet" type="text/css" href="mainpage.css">


<style>

.sideMenu4{
	background:#FFCA2C;
	color:#686868;
}
#tarea {
	border-radius: 13px;
	margin-left: 20px;
	height: 60%;
	width: 90%;
}
</style>
<script>
	function lecturenameselect(){
		$.ajax({
			url:"classNoteLectureList.jsp",
			success:function(data){
				var obj = JSON.parse(data);
				for(var i = 0; i < obj.length; i++){
					var txt = "<option value=" + obj[i].lname + ">" + obj[i].lname +"</option>";
					$("#lectureName").append(txt);
				}
			}
		})
	}
	function teachernameselect(){
		$.ajax({
			url:"classNoteTeacherList.jsp",
			success:function(data){
				var obj = JSON.parse(data);
				for(var i = 0; i < obj.length; i++){
					var txt = "<option value=" + obj[i].tname + ">" + obj[i].tname +"</option>";
					$("#teacherName").append(txt);
				}
			}
		})
	}
	function studentListchange(){
		$.ajax({
			url:"classNoteStudentList.jsp",
			success:function(data){
				var obj = JSON.parse(data);
				for(var i = 0; i < obj.length; i++){
					var txt = "<tr onclick='trclick(this)'> <td>" + obj[i].no + "</td><td>" + obj[i].name + "</td><td>" + obj[i].grade + "</td> </tr>";
					$("#tbody").append(txt);
				}
			}
		})
	}
	function trclick(tr) {
	    $(tr).toggleClass('selected'); // 클릭한 행의 선택 상태 토글
	    $(tr).css("backgroundcolor","#99cc00")
		
	    // 선택된 행의 값을 가져오기
	    var selectedRows = $('#StudentList tbody tr.selected');
	    var values = [];
	    selectedRows.each(function() {
	        values.push($(this).find('td:eq(1)').text());
	    });
	    
	    // 중복 제거
	    var uniqueNames = [...new Set(values)];
	    
	    $("#selectStudentList").val(uniqueNames.join(', '));
	}
		
	$(function(){
		teachernameselect();
		lecturenameselect();
		studentListchange();
		
		$("#lectureClass").change(function() {
			var selectedValue = $(this).val();
			$("#lectureClassText").val(selectedValue);
		});
		$("#lectureName").change(function() {
			var selectedValue = $(this).val();
			$("#lectureNameText").val(selectedValue);
		});
		
		$("#update").click(function() {
			var selectStudentList = $("#selectStudentList").val();
			var date = $("#date").val();
			var lectureNameText = $("#lectureNameText").val();
			var lectureClassText = $("#lectureClassText").val();
		    var teachername = $("#teacherName").val();
			var notetitle = $("#notetitle").val();
			var tarea = $("#tarea").val();

		    $.ajax({
				url: "classNoteInsert.jsp",
				data: {
					selectStudentList: selectStudentList,
					date: date,
		        	lectureNameText: lectureNameText,
		        	lectureClassText: lectureClassText,
		        	teachername: teachername,
		        	notetitle: notetitle,
		        	tarea: tarea
				}
			});
		    location.href = "classNote.jsp";
		});
	})
</script>
</head>
<body>

<jsp:include page="mainheader.jsp"></jsp:include>

<div id="canvas">
	<div class="sideMenu1"><a href="studentList.jsp">학생목록</a></div>
		<div class="sideMenu2"><a href="checkList.jsp"></a>출결관리</div>
		<div class="sideMenu3"><a href="studentcal.jsp">수업관리</a></div>
		<div class="sideMenu4"><a href="classNote.jsp">학습일지</a></div>
		<div class="sideMenu5"><a href="admin.jsp">관리자</a></div>
	<div id="container">
		<div class="sidecontents">
			<div id="student1">
				<label for="">강의명 : </label> 
				<select name="lectureName" id="lectureName">
					<option value="">전체</option>
				</select>
				<br /><br />   
				<label for="">분반 : </label> 
				<select name="lectureClass" id="lectureClass">
					<option value="">전체</option>
					<option value="A">A반</option>
					<option value="B">B반</option>
					<option value="C">C반</option>
				</select>
			</div>
	
			<div id="student2">
				<table class="tableCss">
					<thead>
						<tr>
							<th>학생번호</td>
							<th>학생이름</td>
							<th>학년</td>
						</tr>
					</thead>
					<tbody id="tbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="maincontents">
			<div id="student1">
				<label for="">학생 : </label> 
				<input type="text" name="selectStudentList" id="selectStudentList" readonly /> 
				<label for="">수업일자 : </label> 
				<input type="date" name="date" id="date" /> 
				<br /> <label for="">강의명 : </label> 
				<input type="text" name="lectureNameText" id="lectureNameText" readonly /> 
				<label for="">분반 : </label> 
				<input type="text" name="lectureClassText" id="lectureClassText" readonly />
				<br />
				<label for="">담당강사 : </label> 
				<select name="teachername" id="teacherName">
				</select> <label for="">제목 : </label> 
				<input type="text" name="notetitle" id="notetitle" />
				<br />
				<br />
			</div>
				<br />
				<br />
				<textarea name="" id="tarea" cols="30" rows="10"></textarea>
			<div id="alter_btn" align="right">
				<button type="button" id="update" >등록하기</button> 
				<a href = "classNote.jsp"><button class="btn" type="button" id="cancel" >취소하기</button></a>
			</div>
		</div>
	</div>
</div>
</body>
</html>
