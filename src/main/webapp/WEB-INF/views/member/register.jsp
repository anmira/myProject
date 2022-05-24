<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
	form { margin: 0px 10px; }
	h2 {
	  margin-top: 2px;
	  margin-bottom: 2px;
	}
	.container { max-width: 360px; }
	.divider {
	  text-align: center;
	  margin-top: 20px;
	  margin-bottom: 5px;
	}
	.divider hr {
	  margin: 7px 0px;
	  width: 35%;
	}
	.left { float: left; }
	.right { float: right; }
	.img{ display: block; margin: 10px auto; left: 50px;}
</style>
<html>
	<head>
		<!-- 합쳐지고 최소화된 최신 CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- 부가적인 테마 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	 	
	 	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

		<title>회원가입</title>
	</head>
	<script type="text/javascript">
		$(document).ready(function(){
			// 취소
			$(".cencle").on("click", function(){			
				location.href = "/login";				    
			})
		
			$("#submit").on("click", function(){
				if($("#userId").val()==""){
					alert("아이디를 입력해주세요.");
					$("#userId").focus();
					return false;
				}
				
				if($("#userPass").val()==""){
					alert("비밀번호를 입력해주세요.");
					$("#userPass").focus();
					return false;
				}
				
				if($("#userName").val()==""){
					alert("성명을 입력해주세요.");
					$("#userName").focus();
					return false;
				}
				
				if($("#userMail").val()==""){
					alert("이메일을 입력해주세요.");
					$("#userMail").focus();
					return false;
				}
				
				var idChkVal = $("#idChk").val();
				if(idChkVal == "N"){
					alert("아이디 중복확인을 해주세요.");
				}else if(idChkVal == "Y"){
					$("#regForm").submit();
				}
			});		
		})
		
		function fn_idChk(){
			$.ajax({
				url : "/member/idChk",
				type : "post",
				dataType : "json",
				data : {"userId" : $("#userId").val()},
				success : function(data){
					if(data == 1){
						alert("중복된 아이디입니다.");
					}else if(data == 0){
						$("#idChk").attr("value", "Y");
						alert("사용가능한 아이디입니다.");
					}
				}
			})
		}
		
	</script>
	<body>
		<div class="container">
		<div class="row">
		<img src="/resources/assets/img/TripPick - MarkMaker Logo1.png" height="250" alt="logo">
			<div class="panel panel-primary">
				<div class="panel-body">
					
					<form action="/member/register" method="post" id="regForm">
						<div class="form-group">
							
							<h2>회원가입</h2>
						</div>
						<div class="form-group">
							<label class="control-label" for="userId">아이디</label>
							<input id="userId" type="text" maxlength="50" class="form-control" name="userId">
							<button class="idChk" type="button" id="idChk" onclick="fn_idChk()" value="N">중복확인</button>
						</div>
						<div class="form-group">
							<label class="control-label" for="userPass">비밀번호</label>
							<input id="userPass" type="password" maxlength="50" class="form-control" name="userPass">
						</div>
						<div class="form-group">
							<label class="control-label" for="userName">성명</label>
							<input id="userName" type="text" maxlength="50" class="form-control" name="userName">
						</div>
						<div class="form-group">
							<label class="control-label" for="userMail">이메일</label>
							<input id="userMail" type="email" maxlength="50" class="form-control" name="userMail">
						</div>
						<div class="form-group">
							<button id="submit" type="submit" class="btn btn-info btn-block">회원가입</button>
							<button class="cencle btn btn-danger" type="button">취소</button>
						</div>
						<p>Already have an account? <a href="/">Sign in</a></p>
					</form>
				</div>
			</div>
		</div>
	</div>
	</body>	
</html>