<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
<title>Board Read Page</title>
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

	.reply-margin {
    margin-left: 60px;
	}
</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="container-fluid">
<div class="row">
<div class="col-md-12">
<h3>게시물 상세</h3>
<div class="card">
	<div class="bg-light rounded h-100 p-4">
		<form onsubmit="onSubmitForm(event)" method="get">
			<dl class="detail_content">
				<dt>게시물 번호</dt>
				<dd class="bno">${list.bno}</dd>
				<dt>제목</dt>
				<dd>${list.title}</dd>
				<dt>작성자</dt>
				<dd class="writer">${list.writer}</dd>
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
							<c:forEach items="${list.fileName}" var="fileName"
								varStatus="status">
								<li><img src="http://localhost/${fileName}" width="500px"
										height="500px" style="border-radius: 5px;" /></li>
							</c:forEach>
						</ul>
					</dd>
				</c:if>
			</dl>
			<!-- Like Button Start -->
			<button class="actionLike"><i class="fas fa-heart"></i></button>
			<!-- Like Count Start -->
			<span class="likeCount" style="font-size: larger;"></span>
		</form>
	</div>
</div>
<!-- Member Delete & Member Signout & Member Update & Board List Page -->
<form onsubmit="return false;" action="/spring/board/delete" method="post">
    <div class="button_wrap mt-4">
		<sec:authentication property="principal.email" var="userEmail"/>
		<c:if test="${userEmail == list.writer}">
            <button type="submit" class="btn btn-primary btn-delete"
                onclick="confirmDelete(event)">게시글 삭제</button>
            <a href="/spring/board/update/${list.bno}" class="btn btn-dark">정보 수정</a>
        </c:if>
        <a href="/spring/board/list" class="btn btn-outline-dark">목록으로</a>
    </div>
</form>
<!-- Reply Form Start -->
<div class="reply-form">
	<form class="ActionCreateReply">
	<div class="mb-3">
	<h6 class="mb-4">댓글 작성</h6>
	<label for="replyer" class="form-label">이름</label>
	<input type="text" class="form-control actionReplyer" name="replyer" id="replyer"
	value="<sec:authentication property='principal.email'/>" readonly>
	</div>
	<div class="mb-3">
	<label for="reply" class="form-label">댓글</label>
	<textarea class="form-control actionReply" id="reply" name="reply" rows="3"
		placeholder="Write your comment"></textarea>
	</div>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>
</div>
<!-- Reply Start -->
<div class="col-sm-12 col-md-12 col-xl-12">
	<div class="h-100 bg-light rounded p-4">
		<div class="d-flex align-items-center justify-content-between mb-3">
		<h6 class="mb-7">Reply</h6>
		</div>
		<div class="replyWrap"></div>
		<div class="btn-toolbar" role="toolbar" style="justify-content: center;">
		<!-- Reply Paging -->
		<ul class="btn-group me-2 paging replyPaging" role="group" aria-label="First group"></ul>
		</div>
	</div>
</div>
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
<script src="/js/BoardLike.js"></script>
<script src="/js/BoardReply.js"></script>

<sec:authentication property="principal.email" var="userEmail"/>
<c:if test="${userEmail == list.writer}">
<script>
	// '삭제' 버튼 클릭 시 모달 띄우기
	document.querySelector('.btn-delete').addEventListener('click', function (event) {
	event.preventDefault();
	// 모달 보이기
	$('.deleteModal').modal('show');
	});
	// '확인' 버튼 클릭 시 폼 제출하기
	document.querySelector('.btnDeleteModal').addEventListener('click', function () {
		var bno = document.querySelector('.bno').textContent;
		var form = document.querySelector('form[action="/spring/board/delete"]');
		form.action = '/spring/board/delete/' + encodeURIComponent(bno);
		form.submit();
	});
</script>
</c:if>

<script>
	const alertModal = new bootstrap.Modal(document.querySelector(".alertModal"))
	let message = "${message}";
	if (message !== "") {
		alertModal.show();
	}
	setTimeout(function () {
		alertModal.hide();
	}, 1500);
</script>

<script>
	// 댓글 페이징 처리 
	// Reply and pagination containers
	const replyWrap = document.querySelector(".replyWrap");
    const replyPaging = document.querySelector(".replyPaging");

    //댓글 페이징 처리
    replyPaging.addEventListener("click", (e) => {
      e.preventDefault()
      e.stopPropagation()
      //target 찾기
      const target = e.target
      //paging 번호 찾기 click 시 
      const pageNum = target.getAttribute("data-page")
      //페이징 변경해주기
      listReplyAxios(true, pageNum)
    }, false)

    const replyForm = document.querySelector('.ActionCreateReply');
    const replyerInput = document.querySelector('.actionReplyer');
    const replyInput = document.querySelector('.actionReply');

    replyForm.addEventListener('submit', async (e) => {
      e.preventDefault();
      e.stopPropagation();
      const replyer = replyerInput.value;
      const reply = replyInput.value;
      const list = {
        replyer: replyer,
        reply: reply,
        bno: bno,
      };
      const response = await createReplyBoard(list);
      // Clear the form
      replyInput.value = '';
      // 답글 등록 후 댓글 목록을 다시 로드
      listReplyAxios(false, 1);
    }, false);

    // Reply Axios List Show 
    listReplyAxios()
</script>

</body>
</html>