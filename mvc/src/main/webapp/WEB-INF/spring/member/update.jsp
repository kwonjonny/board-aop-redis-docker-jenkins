<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Member Update Page</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<h3>회원 정보 수정</h3>
<form action="/spring/member/update" method="post">
	<div class="form_content">
		<div class="bg-light rounded h-100 p-4">
			<label for="memberName" class="form-label">이름</label>
			<input type="text" name="memberName" class="form-control" id="memberName" value="${list.memberName}" readonly>
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="email" class="form-label">Email</label>
			<input type="email" name="email" class="form-control" id="email" value="${list.email}" readonly>
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="memberPhone" class="form-label">전화번호</label>
			<input type="text" name="memberPhone" class="form-control" id="memberPhone" value="${list.memberPhone}">
			<span class="error-message" style="color: red;"></span>
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="memberPw" class="form-label">비밀번호</label>
			<input type="password" name="memberPw" class="form-control" id="memberPw" value="${list.memberPw}">
			<span class="error-message" style="color: red;"></span>
		</div>
	</div>
	<div class="button_wrap p-4">
		<a href="/spring/member/read/${list.email}" class="btn btn-outline-dark">조회 페이지</a>
		<button type="submit" class="btn btn-dark">정보 수정</button>
	</div>
</form>
<%@ include file="../include/footer.jsp" %>
<script src="/js/MemberValidation.js"></script>
</body>
</html>