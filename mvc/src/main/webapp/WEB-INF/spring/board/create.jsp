<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
		<!DOCTYPE html>
		<html lang="kr">

		<head>
			<meta charset="UTF-8">
			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1.0">
			<title>FastPickup</title>
		</head>

		<body>
			<%@ include file="../include/header.jsp" %>
				<h3>게시물 생성</h3>
				<form action="/spring/board/create" method="post">
					<div class="form_content">
						<div class="bg-light rounded h-100 p-4">
							<label for="writer" class="form-label">게시글 작성자</label>
							<input type="text" id="writer" name="writer" class="form-control"
								value="<sec:authentication property='principal.email'/>" required readonly>
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="title" class="form-label">게시글 제목</label>
							<input type="text" id="title" name="title" class="form-control" required>
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="content" class="form-label">게시글 내용</label>
							<textarea id="content" name="content" class="form-control" rows="5" required></textarea>
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="fileName" class="form-label">이미지</label>
							<input type="file" name="uploadingFiles" multiple class="form-control uploadInput"
								id="fileName">
						</div>
						<div class="uploadHidden"></div>
					</div>
					<div class="uploadWrap p-4">
						<ul class="uploadUL"></ul>
					</div>
					<div class="button_wrap p-4">
						<a href="/spring/board/list" class="btn btn-outline-dark btnSignin">목록으로</a>
						<button type="submit" class="btn btn-dark btnAdd">게시물 생성</button>
					</div>
				</form>
				<%@ include file="../include/footer.jsp" %>
					<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
					<script src="/js/file.js"></script>
					<!-- Script Start -->
					<script>
						const uploadInput = document.querySelector(".uploadInput")
						const uploadUL = document.querySelector(".uploadUL")
						const uploadHidden = document.querySelector(".uploadHidden")
						const btnAdd = document.querySelector(".btnAdd")

						//파일 업로드
						uploadInput.addEventListener("change", e => {
							//file 없으면 리턴
							if (!uploadInput.files || uploadInput.files.length === 0) {
								return
							}
							//form data
							const formData = new FormData()
							//formData에 파일 넣어주기
							for (let i = 0; i < uploadInput.files.length; i++) {
								formData.append("files", uploadInput.files[i])
							}
							//http header 타입 지정
							const header = { headers: { "Content-Type": "multipart/form-data" } }
							//파일 업로드 axios 호출
							axios.post("http://localhost:8084/upload", formData, header).then(res => {
								const result = res.data
								console.log(result)
								showProducts(result)
							})
						})
						//등록버튼 클릭
						btnAdd.addEventListener("click", e => {
							//li 전부 셀렉트
							const liArr = uploadUL.querySelectorAll("li")
							//li개수만큼 for문 돌려서 input hidden 추가
							let str = ""
							for (let liObj of liArr) {
								//console.log(liObj)
								const originName = liObj.getAttribute("data-originname")
								console.log('오리지널네임: ', originName)
								//console.log("---------------------------------")
								str += '<input type="hidden" name="fileName" value="' + originName + '">'
							}
							uploadHidden.innerHTML += str
							frm.submit()
						}, false)

					</script>
		</body>

		</html>