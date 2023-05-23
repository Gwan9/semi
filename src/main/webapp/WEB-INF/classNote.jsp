<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>

<style>
	*{
	padding : 0;
	margin : auto;
	}
	.container {
		width : 1080px;
		height : 680px;
		background : gray;
		padding-bottom : 0;
		padding-top : 30px;
	}
	.board {
		padding : 20px;
		margin : auto;
		width : 80%;
		height : 80%;
		background : silver;
	}
	.boardlist{
		margin : auto;
		width : 100%;
		height : 70%;
		background : yellow;
	}
	#lectureClass{
		width : 100px;
		height : 30px;
	}
	#lectureName{
		width : 150px;
		height : 30px;
	}
	#studentName{
		width : 150px;
		height : 30px;
	}
	.btn{
		width : 50px;
		height : 30px;
	}
	#date{
		width : 150px;
		height : 30px;
	}
	#alter_btn{
		padding:right;
	}
</style>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:"lectureList.jsp",
			success:function(data){
				var obj = JSON.parse(data);
				for(var i = 0; i < obj.length; i++){
					var txt = "<option value='" + obj[i].lname + "'>" + obj[i].lname +"</option>";
					$("#lectureName").append(txt);
				}
			}
		})
		$("#add").on("click",function(){
			location.href = "classNoteWriter.jsp"
		})
		
		$("#stdSelect").on("click",function(){
			console.log("클릭");
			$.ajax({
				url:"studentList.jsp",
				data:{
					"studentName" : $("#studentName").val()
				},
				success:function(data){
					var obj = JSON.parse(data);
					for(var i = 0; i < obj.length; i++){
						var txt = "<tr><td>" + obj[i].no + "</td><td>" + obj[i].name + "</td><td>" + obj[i].grade + "</td></tr>";
						$("table").append(txt);
					}
				}
			})
		})
	})
</script>
</head>
<body>
	<div class="container" >	<!-- 학습일지 큰 div -->
		<div class="board" >
			<select name="lectureClass" id="lectureClass">	<!-- 분반선택 콤보박스 -->
			    <option value="">분반</option>
			    <option value="A반">A반</option>
			    <option value="B반">B반</option>
			    <option value="C반">C반</option>
			</select>
			<select name="lectureName" id="lectureName">
				<option value="">강의명</option>
			</select>
			
			<label for="">이름 : </label>
			<input type="text" name="studentName" id="studentName" />
			
			<input class="btn" type="button" value="검색" id="stdSelect"/><br><br>
			
			<input type="date" name="" id="date" />
			
			<label for="">부터 </label>
			<input type="date" name="" id="date" />
			
			<label for="">까지 </label> <br /><br />
			
			<div id="alter_btn" align="right">
				
				<input class="btn" type="button" value="등록" id="add" />
				
				
				<input class="btn" type="button" value="수정" id="" />
				<input class="btn" type="button" value="삭제" id="" />
			</div> <br />
			<div class="boardlist">
				<table>
				</table>
			</div>
		</div>
	</div>
</body>
</html>