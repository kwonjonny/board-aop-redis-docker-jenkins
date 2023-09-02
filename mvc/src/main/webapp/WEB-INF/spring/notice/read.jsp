<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Notice Read Page</title>
<style>
	.actionLike {
      color: gray;
      background-color: transparent;
      border: none;
      padding: 0;
      font-size: 40px;
      cursor: pointer;
    }

    .actionLike.liked {
      color: red;
    }
</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container-fluid">
<div class="row">
<div class="col-md-12">
	<h3>공지사항 상세</h3>
	<div class="card">
		<div class="bg-light rounded h-100 p-4">
			<form onsubmit="onSubmitForm(event)" method="get">
				<dl class="detail_content">
					<dt>공지사항 번호</dt>
					<dd class="nno">${list.nno}</dd>
					<dt>제목</dt>
					<dd>${list.title}</dd>
					<dt>작성자</dt>
					<dd>${list.writer}</dd>
					<dt>내용</dt>
					<dd>${list.content}</dd>
					<dt>댓글</dt>
					<dd>${list.replyCount}</dd>
					<dt>좋아요</dt>
					<dd>${list.likeCount}</dd>
					<dt>조회수</dt>
					<dd>${list.viewCount}</dd>
					<c:if test="${not empty list.fileName}">
						<dt>이미지</dt>
						<dd>
							<ul class="image_list"
								style="display: flex; gap: 10px; flex-wrap: wrap;">
								<c:forEach items="${list.fileName}" var="fileName" varStatus="status">
									<li><img src="http://localhost/${fileName}" width="500px" height="500px" style="border-radius: 5px;" /></li>
								</c:forEach>
							</ul>
						</dd>
					</c:if>
				</dl>
			</form>
			<!-- Like Button Start -->
			<button class="actionLike"><i class="fas fa-heart"></i></button>
			<!-- Like Count Start -->
			<span class="likeCount" style="font-size: larger;"></span>
		</div>
	</div>
	<!-- Member Delete & Member Signout & Member Update & Board List Page -->
	<form onsubmit="return false;" action="/spring/notice/delete" method="post">
		<div class="button_wrap mt-4">
			<sec:authentication property="principal.email" var="userEmail"/>
			<c:if test="${userEmail == list.writer}">
				<button type="submit" class="btn btn-primary btn-delete" onclick="confirmDelete(event)">공지사항 삭제</button>
			<a href="/spring/notice/update/${list.nno}" class="btn btn-dark">정보 수정</a>
			</c:if>
			<a href="/spring/notice/list" class="btn btn-outline-dark">목록으로</a>
		</div>
	</form>
	<!-- Update Complete Message Start -->
	<div class="modal alertModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body">${message}</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기
					</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Update Complete Message End -->
	<!-- Delete Confrim Message Modal -->
	<div class="modal deleteModal" tabindex="-1" role="dialog">
		<form method="post" class="actionForm">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-body">정말 삭제 하시겠습니까?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary btnDeleteModal"
							data-bs-dismiss="modal">Confirm
						</button>
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Close
						</button>
					</div>
				</div>
			</div>
		</form>
	</div>
	<!-- Delete Confrim Message Modal -->
</div>
</div>
</div>
<%@ include file="../include/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="/js/NoticeLike.js"></script>
	<script>
	// '삭제' 버튼 클릭 시 모달 띄우기
		document.querySelector('.btn-delete').addEventListener('click', function (event) {
			event.preventDefault();
		// 모달 보이기
		$('.deleteModal').modal('show');
	});
	// '확인' 버튼 클릭 시 폼 제출하기
	document.querySelector('.btnDeleteModal').addEventListener('click', function () {
		var nno = document.querySelector('.nno').textContent;
		var form = document.querySelector('form[action="/spring/notice/delete"]');
		form.action = '/spring/notice/delete/' + encodeURIComponent(nno);
		form.submit();
	});

	const alertModal = new bootstrap.Modal(document.querySelector(".alertModal"))
	let message = "${message}";
	if (message !== "") {
		alertModal.show();
	}
	setTimeout(function () {
		alertModal.hide();
	}, 1500);
	</script>
</body>
</html>