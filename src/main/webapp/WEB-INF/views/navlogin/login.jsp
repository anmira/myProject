<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<html>
<head>
	<title>로그인</title>
		<!-- 합쳐지고 최소화된 최신 CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<!-- 부가적인 테마 -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	<link rel="stylesheet" href="/resources/assets/css/login.css">
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<script type="text/javascript">
	$(document).ready(function(){
		$("#logoutBtn").on("click", function(){
			location.href="member/logout";
		})		
		$("#registerBtn").on("click", function(){
			location.href="member/register";
		})	
		$("#memberUpdateBtn").on("click", function(){
			location.href="member/memberUpdateView";
		});
	});
</script>

<!-- 네이버아이디로로그인 버튼 노출 영역 -->
<script type="text/javascript">
var naverLogin = new naver.LoginWithNaverId({
	clientId: "EuuYhBHon9C1AidY9swP",
	callbackUrl: "http://localhost:9080/navlogin/callback",
	isPopup: false,
	/* callback 페이지가 분리되었을 경우에 callback 페이지에서는 callback처리를 해줄수 있도록 설정합니다. */
});
/* (3) 네아로 로그인 정보를 초기화하기 위하여 init을 호출 */
naverLogin.init();

/* (4) Callback의 처리. 정상적으로 Callback 처리가 완료될 경우 main page로 redirect(또는 Popup close) */
window.addEventListener('load', function () {
	naverLogin.getLoginStatus(function (status) {
		if (status) {
			/* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
			console.log(naverLogin.accessToken.accessToken)
			var email = naverLogin.user.getEmail();
			var profileImage = naverLogin.user.getProfileImage();
			var name = naverLogin.user.getName();
			var uniqId = naverLogin.user.getId();
			if( email == undefined || email == null) {
				alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
				/* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
				naverLogin.reprompt();
				return;
			}else if( name == undefined || name == null){
				alert("회원이름은 필수정보입니다. 정보제공을 동의해주세요.");
				naverLogin.reprompt();
				return;
			}else{
            	// 성공
			}
		} else {
			console.log("callback 처리에 실패하였습니다.");
		}
});
});
	</script>
	
	<div class="container">
		<a href="/board/main"><img src="/resources/assets/img/TripPick - MarkMaker Logo1.png" height="250" alt="logo"></a>
		<div class="row">
			<div class="panel panel-primary">
				<div class="panel-body">
					<form method="POST" action="/member/login" role="form">
						<div class="form-group">
							<h2>로그인</h2>
						</div>
						<div class="form-group">
							<strong>아이디</strong>
							<input id="userId" type="text" maxlength="50" name="userId" class="form-control">
						</div>
						<div class="form-group">
							<strong>패스워드</strong>
							<span class="right"><a href="#">Forgot your password?</a></span>
							<input id="userPass" type="password" maxlength="25" name="userPass" class="form-control">
						</div>
						<div class="form-group" style="padding-top: 12px;">
							<button type="submit" class="btn btn-success btn-block">로그인</button>
						</div>
						
						<!-- 네이버 로그인 창으로 이동 -->			
						<div id="naver_id_login" style="text-align:center">
						<a href="${url}"><img width="223" src="https://developers.naver.com/doc/review_201802/CK_bEFnWMeEBjXpQ5o8N_20180202_7aot50.png"/></a>
						</div>

						<div class="form-group divider">
							<hr class="left"><small>New to site?</small><hr class="right">
						</div>
						<p class="form-group"><button id="registerBtn" type="button" class="btn btn-info btn-block">회원가입</button></p>
						<p class="form-group">By signing in you are agreeing to our <a href="#">Terms of Use</a> and our <a href="#">Privacy Policy</a>.</p>
						<c:if test="${msg == false}">
							<p style="color: red;">로그인 실패! 아이디와 비밀번호 확인해주세요.</p>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
</html>