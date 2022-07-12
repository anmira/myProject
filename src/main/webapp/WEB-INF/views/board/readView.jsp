<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<!-- 합쳐지고 최소화된 최신 CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- 부가적인 테마 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
		
		<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	 	<title>게시판</title>
	</head>
	
	<script type="text/javascript">
		$(document).ready(function(){
			var formObj = $("form[name='readForm']");
		
			// 수정 
			$(".update_btn").on("click", function(){
				formObj.attr("action", "/board/updateView");
				formObj.attr("method", "get");
				formObj.submit();				
			})
			
			// 삭제
			$(".delete_btn").on("click", function(){
				var deleteYN = confirm("삭제하시겠습니까?");
				
				if(deleteYN == true){
				formObj.attr("action", "/board/delete");
				formObj.attr("method", "post");
				formObj.submit();
				}
			})
			
			// 목록
			$(".list_btn").on("click", function(){
				
				location.href = "/board/list?page=${scri.page}"
							  +"&perPageNum=${scri.perPageNum}"
							  +"&searchType=${scri.searchType}"
							  +"&keyword=${scri.keyword}";
			
			})
			
			// 댓글 작성
			$(".replyWriteBtn").on("click", function(){
				var formObj = $("form[name='replyForm']");
				formObj.attr("action", "/board/replyWrite");
				formObj.submit();
			});
			
			// 댓글 수정 뷰
			$(".replyUpdateBtn").on("click", function(){
				location.href = "/board/replyUpdateView?bno=${read.bno}"
								+ "&page=${scri.page}"
								+ "&perPageNum=${scri.perPageNum}"
								+ "&searchType=${scri.searchType}"
								+ "&keyword=${scri.keyword}"
								+ "&rno="+$(this).attr("data-rno");
			});
			
			// 댓글 삭제 뷰
			$(".replyDeleteBtn").on("click", function(){
				location.href = "/board/replyDeleteView?bno=${read.bno}"
								+ "&page=${scri.page}"
								+ "&perPageNum=${scri.perPageNum}"
								+ "&searchType=${scri.searchType}"
								+ "&keyword=${scri.keyword}"
								+ "&rno="+$(this).attr("data-rno");
			});			
			// $(this).attr("data-rno")는 클릭 이벤트가 발생한 수정 버튼의 data-rno값을 가져옴.
		})
	</script>
	
	<body>
		<div class="container">
			<header>
				<h1> 게시판</h1>
			</header>
			<hr />			 
			<div>
				<%@include file="nav.jsp" %>
			</div>
			<hr />			
			<section id="container">
				<form name="readForm" role="form" method="post">
					<input type="hidden" id="bno" name="bno" value="${read.bno}" >
					<input type="hidden" id="page" name="page" value="${scri.page}" >	
					<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}" >	
					<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}" >	
					<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}" >		
				</form>			
				
							<div class="form-group">							
									<label for="title" class="col-sm-2 control-label">제목</label>
									<input type="text" id="title" name="title" class="form-control" value="${read.title}" readonly="readonly"/>								
							</div>	
							<div class="form-group">							
									<label for="country" class="col-sm-2 control-label">국가</label>
									<input type="text" id="country" name="country" class="form-control" value="${read.country}" readonly="readonly"/>								
							</div>	
							<div class="form-group">							
									<label for="area" class="col-sm-2 control-label">지역</label>
									<input type="text" id="area" name="area" class="form-control" value="${read.area}" readonly="readonly"/>								
							</div>	
							<div class="form-group">
									<label for="money" class="col-sm-2 control-label">경비</label>
									<textarea id="text" name="money" class="form-control" readonly="readonly"><c:out value="${read.content}" /></textarea>
							</div>
							<div class="form-group">	
									<label for="period" class="col-sm-2 control-label">기간</label>
									<input type="text" id="period" name="period" class="form-control" value="${read.period}" readonly="readonly"/>
							</div>
							
							<div class="result-images">
							    <c:forEach var="imageFileName" items="${model.fileList}">
							        <img src="${pageContext.request.contextPath}/download?imageFileName=${imageFileName}">
							        <br><br>
							    </c:forEach>
							</div>

							<div class="form-group">	
									<label for="regdate"class="col-sm-2 control-label">작성날짜</label>
									<fmt:formatDate value="${read.regdate}" pattern="yyyy-MM-dd"/>					
							</div>		
							<hr>
							<span>파일 목록</span>
							<div class="form-group" style="boarder: 1px solid #dbdbdb;">
								<c:forEach var="file" items="${file}">
									<a href="#" onclick="fn_fileDown('${file.FILE_NO}'); return false;">${file.ORG_FILE_NAME}</a>(${file.FILE_SIZE}kb)<br>
								</c:forEach>
							</div>
							<hr>
							<div>
								<button type="button" class="update_btn btn btn-warning">수정</button>
								<button type="button" class="delete_btn btn btn-danger">삭제</button>
								<button type="button" class="list_btn btn btn-primary">목록</button>
							</div>
					
							<!-- 댓글 -->
							<div id="reply">
								<ol class="replyList">
									<c:forEach items="${replyList}" var="replyList">
										<li>
											<p>
											작성자 : ${replyList.writer}<br />
											작성날짜 : <fmt:formatDate value="${replyList.regdate}" pattern="yyyy-MM-dd"/>
											</p>
											<p>${replyList.content}</p>
											<div>
												<button type="button" class="replyUpdateBtn btn btn-warning" data-rno="${replyList.rno}">수정</button>
												<button type="button" class="replyDeleteBtn btn btn-danger" data-rno="${replyList.rno}">삭제</button>
											</div>
										</li>
									</c:forEach>
								</ol>
							</div>
							<!--  댓글 작성 폼-->
					<form name="replyForm" method="post" class="form-horizontal">
						<input type="hidden" id="bno" name="bno" value="${read.bno}"/>
						<input type="hidden" id="page" name="page" value="${scri.page}"/>
						<input type="hidden" id="perPageNum" name="perPageNum" value="${scri.perPageNum}"/>
						<input type="hidden" id="searchType" name="searchType" value="${scri.searchType}"/>
						<input type="hidden" id="keyword" name="keyword" value="${scri.keyword}"/>
						
						<div class="form-group">
							<label for="writer" class="col-sm-2 control-label">댓글 작성자</label>
							<div class="col-sm-5">
								<input type="text" id="writer" name="writer" class="form-control"/>
							</div>	
						</div>	
						
						<div class="form-group">
							<label for="content" class="col-sm-2 control-label">댓글 내용</label>
							<div class="col-sm-5">
								<input type="text" id="content" name="content" class="form-control"/>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="button" class="replyWriteBtn btn btn-success">댓글 작성</button>
							</div>
						</div>
					</form>
			</section>
			<hr />
		</div>
	</body>
</html>