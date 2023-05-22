<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherRegister.jsp</title>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#subbtn").on("click", function(){
			if($("#id").val().trim() != "" && $("#password_1").val().trim() != "" && $("#name").val().trim() != "" &&
			$("#birth").val().trim() != "" && $("#addrs").val().trim() != "" && $("#female").val().trim() != null &&
			$("#phone").val().trim() != "" && $("#email").val().trim() != "" && $("#male").val().trim() != null){
				document.frm.action = "teacherRegisterOk.jsp";
				document.frm.method = "get";
				document.frm.submit();
			}else{
				document.frm.action = "teacherRegister.jsp";
			};
		})
		
		$("input[value='중복확인']").on("click", function(){
			console.log("버튼눌림");
			$.ajax({
				type: "GET",
				async: true,
				url: "teacherIdCheckup.jsp",
				dataType: "html",
				data: {"id":$("#id").val()},
				success: function(response, status, request){
					if(response.trim() == 'true'){
						$("#msg").html("<h6 style='color:blue;'>*사용가능 합니다</h6>");
					}else {
						$("#msg").html("<h6 style='color:red;'>*아이디가 존재합니다<h6>");
					}
				}
			})
		})
	
		$('.pw').on("keyup", function(){
			var pass1 = $("#password_1").val();
			var pass2 = $("#password_2").val();
			
			if(pass1 != "" || pass2 !=""){
				if(pass1 == pass2){
					$("#msg3").html("<h6 style='color:blue;'>*비밀번호가 일치합니다</h6>");
					
				}else{
					$("#msg3").html("<h6 style='color:red;'>*비밀번호가 불일치합니다</h6>");
					
				}
			}
			
		})
		//공백버튼 있으면 필수정보입니다 출력하고 화면 안넘어가게 구현
		$("input[value='가입하기']").on("click", function(){
			
			if($("#id").val().trim() == "")
				$("#msg").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			
			if($("#password_1").val().trim() == "")
				$("#msg2").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			
			if($("#name").val().trim() == "")
				$("#msg4").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			
			if($("#birth").val() == "")
				$("#msg5").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			
			if($("input[name='gender']").val() == false)
				$("#msg6").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			
			if($("#post1").val() == "")
				$("#msg7").html("<h6 style='color:red;'>*필수 정보입니다<h6>");
			
			if($("#phone").val().trim() == "")
				$("#msg8").html("<h6 style='color:red;'>*필수 정보입니다<h6>");
			
			if($("#email").val().trim() == "")
				$("#msg9").html("<h6 style='color:red;'>*필수 정보입니다<h6>");
		})
		
		$("#id").on("keyup", function(){
			if($("#id").val().trim() == ""){
				$("#msg").html("<h6 style='color:red;'>*필수 정보입니다<h6>");	
			}else
				$("#msg").html("");
		})
		
		$("#password_1").on("keyup", function(){
			if($("#password_1").val().trim() != ""){
				$("#msg2").html("");
			}
		})
		$("#name").on("keyup", function(){
			if($("#name").val().trim() != ""){
				$("#msg4").html("");
			}
		})	
		$("#birth").on("click", function(){
			
				$("#msg5").html("");
			
		})			
		$("#male").on("click", function(){
			
				$("#msg6").html("");
			
		})
		$("#female").on("click", function(){
			
				$("#msg6").html("");
			
		})
		
		$("input[value='우편번호찾기']").on("click", function(){
			
				$("#msg7").html("");
			
		})			
		$("#phone").on("keyup", function(){
			if($("#phone").val().trim() != ""){
				$("#msg8").html("");
			}
		})			
		$("#email").on("keyup", function(){
			if($("#email").val().trim() != ""){
				$("#msg9").html("");
			}
			
		})			
		
	})
	
	window.onload=function(){
		var btn = document.getElementById("btn");
		btn.onclick=openKakaoPostCode;
		
	}
	function openKakaoPostCode(){
		console.log("openKakaoPostCode 함수 호출중");
		new daum.Postcode({
			oncomplete: function(data){
				// 팝업에서 검색 결과 항목을 클릭했을 때
				// 실행할 코드를 작성하는 부분
				//console.log("팝업검색 버튼 누름");
				console.dir(data);
				document.getElementById("post1").value=data.zonecode;
				document.getElementById("addrs").value=data.address;
				document.getElementById("addrs2").value=data.jibunAddress;
				
			}
		}).open();
	}

</script>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}
	#container{
		width: 280px;
		height: 100%;
		margin: auto;
		background: #F4EEDD;
		
	}
	
	#photo, #sub{
		text-align: center;
	}
	#findimg{
		text-align: center;
	}
	
</style>
</head>
<body>
	<form action="" name = "frm">
		<div id="container">
			<div id="center">
				<div>
					<h5>사진</h5>
					<div id="photo"><img src="" alt="증명사진" /></div>
					<div id="findimg"><input type="button" value="파일찾기" /></div>			
				</div>
				<div>
					<h5>아이디</h5>
					<input type="text" name="id" id="id" />
					<input type="button" value="중복확인" />
					<div id="msg"></div>
				</div>
				<div>
					<h5>비밀번호</h5>
					<input type="password" name="pw" class="pw" id="password_1" />
					<div id="msg2"></div>
				</div>
				<div>
					<h5>비밀번호 재확인</h5>
					<input type="password" name="pw2" class="pw" id="password_2" />
					<div id="msg3"></div>
				</div>
				<div>
					<h5>이름</h5>
					<input type="text" name="name" id="name" />
					<div id="msg4"></div>
				</div>
				<div>
					<h5>생년월일</h5>
					<input type="date" name="birth" id="birth" />
					<div id="msg5"></div>
				</div>
				<div>
					<h5>성별</h5>
					<h5><input type="radio" name="gender" id="male" value="남" checked="checked"/>남
					<input type="radio" name="gender" id="female" value="여" />여</h5>
					<div id="msg6"></div>
				</div>
				<div>
					<h5>거주지</h5>
					<input type="text" name="post1" id="post1" />
					<input type="button" value="우편번호찾기" id="btn"/> <br />
					<h5>도로명 : </h5><input type="text" name="addrs" id="addrs" size="35"/> <br />
					<h5>지번 : </h5><input type="text" name="addrs2" id="addrs2" size="35"/> <br />
					<div id="msg7"></div>
				</div>
				<div>
					<h5>연락처</h5>
					<input type="text" name="phone" id="phone" placeholder=" ' - ' 제외  ex) 01022223333 " />
					<div id="msg8"></div>
				</div>
				<div>
					<h5>EMAIL</h5>
					<input type="text" name="email" id="email" placeholder=" ex) teacher@naver.com "/>
					<div id="msg9"></div>
				</div>
				<div id="sub">
					
						<input type="button" value="가입하기" id="subbtn"/>
					
						<a href="main.jsp">
						<input type="button" value="취소하기" /></a>
				</div>
			</div>
		</div>
	</form>
</body>
</html>