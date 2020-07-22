<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../resources/cssjs/head.jsp" %>
<style type="text/css">
	.dto:hover{
		background-color: lime;
		cursor: pointer;
	}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$("#insert").on("click",function(){
		window.location="insert.ws";
	});
	$(".dto").on("click",function(){
// 		alert($(this).find("td").eq(0).text());
		var idx = $(this).find("input").val();
// 		alert(idx);
		window.location="update.ws?idx="+idx;
	});
	$(".td_ck").on("click",function(e){
// 		e.preventDefault();	 checkbox 는 기본 이벤트
		e.stopPropagation(); // 부모태그에 이벤트는
		$(this).find("input").attr("checked",true);
	});
	$("#delete").on("click",function(e){
		$('#frm').submit();
	});
});
</script>
</head>
<body>
	<div class="container">
		<div class="row" style="height:430px;">
			<form action="delete.ws" id="frm">
				<table class="table">
					<tr>
						<th></th>
						<th>순번</th>
						<th>제목</th>
						<th>내용</th>
						<th>작성일자</th>
						<th>수정일자</th>
					</tr>
					<c:forEach items="${list}" var="dto">
						<tr class="dto">
							<td class="td_ck"><input type="checkbox" name="idx" value="${dto.idx}"/></td>
							<td>${dto.idx}</td>					
							<td>${dto.title}</td>
							<td>${dto.content}</td>
							<td>${dto.reg_date}</td>
							<td>${dto.mod_date}</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</div>
		<div class="row" style="text-align: center">
			<c:forEach begin="1" end="${pagecount}" var="i">
				<a href="index.ws?page=${i}">[ ${i} ]</a>
			</c:forEach>
		</div>
		<div class="row">
			<button type="button" id="insert" class="btn-primary">글쓰기</button>
			<button id="delete" class="btn-primary">삭제</button>
		</div>
	</div>
</body>
</html>














