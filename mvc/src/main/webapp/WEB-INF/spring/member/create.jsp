<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Member Create Page</title>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<form action="/spring/member/create" method="post">
	<h3>회원 생성</h3>
	<div class="form_content">
		<div class="bg-light rounded h-100 p-4">
			<label for="memberName" class="form-label">이름</label>
			<input type="text" id="memberName" name="memberName" class="form-control" required
				placeholder="이름을 입력 해주세요">
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="email" class="form-label">Email</label>
			<input type="email" id="email" name="email" class="form-control" required
				placeholder="Email을 입력 해주세요">
			<span class="error-message" style="color: red;"></span>
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="memberPw" class="form-label">비밀번호</label>
			<input type="password" id="memberPw" name="memberPw" class="form-control" required
				placeholder="비밀번호를 입력 해주세요">
			<span class="error-message" style="color: red;"></span>
		</div>
		<div class="bg-light rounded h-100 p-4">
			<label for="memberPhone" class="form-label">전화번호</label>
			<input type="text" id="memberPhone" name="memberPhone" class="form-control" required
				placeholder="010-xxxx-xxxx">
			<span class="error-message" style="color: red;"></span>
		</div>
	</div>
	<div class="button_wrap p-4">
		<a href="/spring/index" class="btn btn-outline-dark btnSignin">목록으로</a>
		<button type="submit" class="btn btn-dark" id="createButton">회원 생성</button>
	</div>
</form>
<div class="modal alertModal" id="alertModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-body" id="modalMessage">${message}</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기
				</button>
			</div>
		</div>
	</div>
</div>
<%@ include file="../include/footer.jsp" %>
<script src="/js/MemberValidation.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
</body>
<script>
function showModal(data) {
    document.getElementById('modalMessage').innerText = data.message;
    $('#alertModal').modal('show');
    setTimeout(function() {
        $('#alertModal').modal('hide');
    }, 3000);
}
document.querySelector('form').addEventListener('submit', async function(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    try {
        const response = await fetch('http://localhost:8084/spring/member/create', {
            method: 'POST',
            body: formData,
        });
        const result = await response.json();
		// 서버 응답은 200 이지만 내부 메시지 응답이 400 ~ 500 일 경우 
        if (result.status.startsWith("400") || result.status.startsWith("500")) {
            showModal(result);
        } else {
        }
    } catch (error) {
        console.error('Error:', error);
    }
});
</script>

</html>