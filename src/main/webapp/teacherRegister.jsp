<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>teacherRegister.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		
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
					$("#msg2").html("<h6 style='color:blue;'>*비밀번호가 일치합니다</h6>");
					
				}else{
					$("#msg2").html("<h6 style='color:red;'>*비밀번호가 불일치합니다</h6>");
					
				}
			}
			
		})
		//공백버튼 있으면 필수정보입니다 출력하고 화면 안넘어가게 구현
		$("input[value='가입하기']").on("click", function(){
			
			if($("#id").val() == ""){
				$("#msg").html("<h6 style='color:red;'>*필수 정보입니다<h6>");
				window.setTimeout(function(){
					document.frm.action = "teacherRegister.jsp";
					document.from.method = "get";
					document.from.submit();
				})
			}
				
		})
		
		
	})
	
</script>
<style type="text/css">
	*{
		margin: 0;
		padding: 0;
	}
	#container{
		width: 250px;
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
	<h1>register</h1>
		<form action="teacherRegisterOk.jsp">
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
						<div id="msg"></div>
					</div>
					<div>
						<h5>비밀번호 재확인</h5>
						<input type="password" name="pw2" class="pw" id="password_2" />
						<div id="msg2"></div>
					</div>
					<div>
						<h5>이름</h5>
						<input type="text" name="name" id="" />
						<div id="msg"></div>
					</div>
					<div>
						<h5>생년월일</h5>
						<input type="text" name="birth" id="" />
						<div id="msg"></div>
					</div>
					<div>
						<h5>성별</h5>
						<h5><input type="radio" name="gender" id="" value="남" />남
						<input type="radio" name="gender" id="" value="여" />여</h5>
						<div id="msg"></div>
					</div>
					<div>
						<h5>거주지</h5>
						<input type="text" name="addrs" id="" />
						<div id="msg"></div>
					</div>
					<div>
						<h5>연락처</h5>
						<input type="text" name="phone" id="" />
						<div id="msg"></div>
					</div>
					<div>
						<h5>EMAIL</h5>
						<input type="text" name="email" id="" />
						<select name="" id="">
							<option value="직접입력">직접입력</option>
						</select>
						<div id="msg"></div>
					</div>
					<div id="sub">
						<form action="" name="frm">
							<input type="button" value="가입하기" />
						</form>	
							<a href="main.jsp">
							<input type="button" value="취소하기" /></a>
					</div>
				</div>
			</div>
		</form>
</body>
</html>