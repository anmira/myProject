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
			var formObj = $("form[name='writeForm']");
			$(".write_btn").on("click", function(){
				if(fn_valiChk()){
					return false;
				}
				formObj.attr("action", "/board/write");
				formObj.attr("method", "post");
				formObj.submit();
			});
			fn_addFile();
		})
		function fn_valiChk(){
			var regForm = $("form[name='writeForm'] .chk").length;
			for(var i = 0; i<regForm; i++){
				if($(".chk").eq(i).val() == "" || $(".chk").eq(i).val() == null){
					alert($(".chk").eq(i).attr("title"));
					return true;
				}
			}
		}
		function fn_addFile(){
			var fileIndex = 1;
			$(".fileAdd_btn").on("click", function(){
				$("#fileIndex").append("<div><input type='file' style='float:left;' name='file_"+(fileIndex++)+"'>"+"</button>"+"<button type='button' style='float:right;' id='fileDelBtn'>"+"삭제"+"</button></div>");
			});
			$(document).on("click","#fileDelBtn", function(){
				$(this).parent().remove();
			});
		}
	</script>
	<body>
		<div id="root">
			<header>
				<h1> 게시판</h1>
			</header>
			<hr />
			 
			<div>
				<%@include file="nav.jsp" %>
			</div>
			<hr />
			
			<section id="container">
				<form name="writeForm" method="post" action="/board/write" enctype="multipart/form-data">
					<table>
						<tbody>
						<%-- <c:if test="${member.userId != null}"> --%>
							<tr>
								<td>
									<label for="title" class="col-sm-2 control-label">제목</label><input type="text" id="title" name="title" class="form-control" title="제목을 입력하세요." />
								</td>
							</tr>	
							<tr>
								<td>
									<label for="title" class="col-sm-2 control-label">국가</label><input type="text" id="country" name="country" class="form-control" title="국가를 입력하세요." />
								</td>
							</tr>
							<tr>
								<td>
									<label for="title" class="col-sm-2 control-label">지역</label><input type="text" id="area" name="area" class="form-control" title="지역을 입력하세요." />
								</td>
							</tr>
							<tr>
								<td>
									<label for="title" class="col-sm-2 control-label">경비</label><input type="text" id="money" name="money" class="form-control" title="경비를 입력하세요." />
								</td>
							</tr>
							<tr>
								<td>
									<label for="title" class="col-sm-2 control-label">기간</label><input type="text" id="period" name="period" class="form-control" title="기간을 입력하세요." />
								</td>
							</tr>
							<tr>
								<td>
									<label for="content" class="col-sm-2 control-label"v>내용</label><textarea id="content" name="content" class="form-control" title="내용을 입력하세요."></textarea>
								</td>
							</tr>
							<tr>
								<td>
									<label for="writer" class="col-sm-2 control-label">작성자</label><input type="text" id="writer" name="writer" class="form-control" title="작성자를 입력하세요."/>
								</td>
							<tr>
								<td id="fileIndex">
								</td>
							</tr>	
							<tr>
								<td>						
									<button type="submit" class="write_btn">작성</button>
									<button type="button" class="fileAdd_btn">파일추가</button>
								</td>
							</tr>
						<%-- </c:if>		
						<c:if test="${member.userId == null}">
							<p>로그인 후 작성하실 수 있습니다.</p>	
						</c:if>		 --%>
						</tbody>			
					</table>
				</form>
			</section>
			<hr />
		</div>
	</body>
</html>