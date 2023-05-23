<%@page import="VO.ClassNoteVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DAO.StudentDAO"%>
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
		$("#lectureClass, #lectureName").on("change", function() {
		    var lectureClass = $("#lectureClass").val();
		    var lectureName = $("#lectureName").val();
		    
		    $.ajax({
		      url: "classNoteList.jsp",
		      data: {
		        "lectureClass": lectureClass,
		        "lectureName": lectureName
		      },
		      success: function(data) {
		        var obj = JSON.parse(data);
		        $("table").empty(); // 기존 테이블 내용 비우기
		        
		        for (var i = 0; i < obj.length; i++) {
		          var txt = "<tr><td>" + obj[i].noteno + 
		          			"</td><td>" + obj[i].lecturename+ 
		          			"</td><td>" + obj[i].lectureclass + 
		          			"</td><td>" + obj[i].studentname+ 
		          			"</td><td>" + "<a href='classNoteDetail.jsp?NoteNo="+obj[i].noteno + "'>" + obj[i].notetitle + "</a>"+ 
		          			"</td></tr>";
		          $("table").append(txt);
		        }
		      }
		    });
		  });
		$.ajax({
			url:"classNoteLectureList.jsp",
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
<%
// 	로그인 확인 절차
// 	Object obj = session.getAttribute("vo");
// 	if(obj == null)
// 		response.sendRedirect("../day10/login.jsp");

	StudentDAO dao = new StudentDAO();
	
	int totalCount = dao.getTotalCount();
	
// 	한 페이지당 게시물 건수 : 20
	int recordPerPage = 20;
	int totalPage = (totalCount % recordPerPage == 0) ? totalCount / recordPerPage : totalCount / recordPerPage + 1;
	
	
	String cp = request.getParameter("cp");
	
	int currentPage = 0;
	
	if(cp!=null)
		 currentPage = Integer.parseInt(cp);
	else
		currentPage = 1;

	int startPage = 1;
	int endPage = totalPage;
	
	if(currentPage <= 5)
		startPage = 1;
	else if(currentPage >= 6)
		startPage = currentPage-4;
	
	if(totalPage - currentPage <= 5)
		endPage = totalPage;
	else if(totalPage - currentPage > 5)
		if(currentPage <= 5){
			if(totalPage > 10){
				endPage =10;
			}else{
				endPage = totalPage;
			}
		}
		else
			endPage = currentPage+4;
	

%>
	<div class="container" >	<!-- 학습일지 큰 div -->
		<div class="board" >
			<select name="lectureClass" id="lectureClass">	<!-- 분반선택 콤보박스 -->
			    <option value="전체">분반</option>
			    <option value="A">A반</option>
			    <option value="B">B반</option>
			    <option value="C">C반</option>
			</select>
			<select name="lectureName" id="lectureName">
				<option value="전체">강의명</option>
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
			</div> <br />
			<div class="boardlist">
				<table>
				<%
					int startno = (currentPage-1) * recordPerPage +1;
					int endno = currentPage * recordPerPage;
					
					ArrayList<ClassNoteVO> list = dao.studentNoteSelectAll(startno, endno);
					for(ClassNoteVO vo : list){
						
						%>
						<tr>
							<td><%=vo.getNoteNo() %></td>
							<td><%=vo.getNoteDate() %></td>
							<td><a href="classNoteDetail.jsp?NoteNo=<%= vo.getNoteNo() %>"> <%= vo.getNoteTitle() %> </a></td>
							<td><%=vo.getNoteContents() %></td>
							<td><%=vo.getClassRegisterNo() %></td>
						</tr>
						
						<%
					}
				%>
				</table>
			</div>
		</div>
	</div>
</body>
</html>