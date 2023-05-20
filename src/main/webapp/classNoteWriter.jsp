<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<style>
* {
	padding: 0;
	margin: auto;
}

.container {
	padding: 20px;
	width: 1280px;
	height: 720px;
	background: #34a8cb;
}

.sidecontents {
	padding: 20px;
	float: left;
	width: 30%;
	height: 90%;
	background: green;
}

.maincontents {
	padding: 20px;
	float: right;
	width: 60%;
	height: 90%;
	background: red;
}

.classNoteWriterStudentList {
	margin-top: 20px;
	width: 80%;
	height: 80%;
	background: white;
}

select {
	height: 20px;
}

input {
	height: 20px;
}

#tarea {
	margin-left: 20px;
	height: 400px;
	width: 700px;
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
					$("#StudentList tbody").append(txt);
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
				url: "insertnote.jsp",
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
		});
	})
</script>
</head>
<body>
	<div class="container">
		<div class="sidecontents">
			<label for="">강의명 : </label> 
			<select name="lectureName" id="lectureName">
				<option value="">전체</option>
			</select>
			<br /> <br /> 
			<label for="">분반 : </label> 
			<select name="lectureClass" id="lectureClass">
				<option value="">전체</option>
				<option value="A">A반</option>
				<option value="B">B반</option>
				<option value="C">C반</option>
			</select>

			<div class="classNoteWriterStudentList">
				<table id="StudentList">
					<thead>
						<tr>
							<td>학생번호</td>
							<td>학생이름</td>
							<td>학년</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
		<div class="maincontents">
			<label for="">학생 : </label> 
			<input type="text" name="selectStudentList" id="selectStudentList" readonly /> 
			<br /> <br /> w
			<label for="">수업일자 : </label> 
			<input type="date" name="date" id="date" /> 
			<br /> <br /> <label for="">강의명 : </label> 
			<input type="text" name="lectureNameText" id="lectureNameText" readonly /> 
			<label for="">분반 : </label> 
			<input type="text" name="lectureClassText" id="lectureClassText" readonly />
			<br /> <br /> 
			<label for="">담당강사 : </label> 
			<select name="teachername" id="teacherName">
			</select> <label for="">제목 : </label> 
			<input type="text" name="notetitle" id="notetitle" />
			<br /> <br /> <br />
			<textarea name="" id="tarea" cols="30" rows="10"></textarea>
			<br />
			<div id="alter_btn" align="right">
				<input type="button" value="등록하기" id="update" /> 
				<a href = "classNote.jsp"><input class="btn" type="button" value="취소하기" id="cancel" /></a>
			</div>
		</div>
	</div>
</body>
</html>