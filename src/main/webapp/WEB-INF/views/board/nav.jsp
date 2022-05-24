<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	li {list-style: none; display:inline; padding: 6px;}
	
</style>
<head>
	 <link href="/resources/assets/css/theme.nav.css" rel="stylesheet" />
</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-light fixed-top py-5 d-block" data-navbar-on-scroll="data-navbar-on-scroll">
        <div class="container"><a class="navbar-brand" href="/board/main"><img src="/resources/assets/img/TripPick - MarkMaker Logo1.png" height="200" alt="logo" /></a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"> </span></button>
          <div class="collapse navbar-collapse border-top border-lg-0 mt-4 mt-lg-0" id="navbarSupportedContent">
            <ul class="navbar-nav ms-auto pt-2 pt-lg-0 font-base align-items-lg-center align-items-start">
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="#service">여행보기</a></li>
              <li class="nav-item px-3 px-xl-4"><a class="nav-link fw-medium" aria-current="page" href="/board/list">여행톡</a></li>
              <li class="nav-item px-3 px-xl-4"><c:if test="${member != null}"><a class="nav-link fw-medium" aria-current="page" href="/member/logout">로그아웃</a></c:if>
              									<c:if test="${member == null}"><a href="/">로그인</a></c:if></li>
               <li class="nav-item px-3 px-xl-4"><c:if test="${member != null}"><p>${member.userId}님 안녕하세요.</p></c:if></li>									
              <li class="nav-item px-3 px-xl-4"><a class="btn btn-outline-dark order-1 order-lg-0 fw-medium" href="/member/register">회원가입</a></li>
            </ul>
          </div>
        </div>
      </nav>
<%-- <ul>
	
	<li>
		<c:if test="${member != null}"><a href="/member/logout">로그아웃</a></c:if>
		<c:if test="${member == null}"><a href="/">로그인</a></c:if>
	</li>
	<li>
		<c:if test="${member != null}">
			<p>${member.userId}님 안녕하세요.</p>
		</c:if>
	</li>
</ul> --%>

</body>