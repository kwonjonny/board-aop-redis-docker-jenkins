<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<h3>게시물 정보 수정</h3>
				<form action="/spring/board/update" method="post">
					<div class="form_content">
						<div class="bg-light rounded h-100 p-4">
							<label for="bno" class="form-label">게시물 번호</label>
							<input type="text" name="bno" class="form-control" id="bno" value="${list.bno}">
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="writer" class="form-label">작성자</label>
							<input type="text" name="writer" class="form-control" id="writer" value="${list.writer}"
								readonly>
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="title" class="form-label">게시물 제목</label>
							<input type="text" name="title" class="form-control" id="title" value="${list.title}">
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="createDate" class="form-label">게시물 작성 날짜</label>
							<input type="text" name="createDate" class="form-control" id="createDate"
								value="${list.createDate}">
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="updateDate" class="form-label">게시물 수정 날짜</label>
							<input type="text" name="updateDate" class="form-control" id="updateDate"
								value="${list.updateDate}">
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="content" class="form-label">게시글 내용</label>
							<textarea id="content" name="content" class="form-control" rows="5"
								required>${list.content}</textarea>
						</div>
						<div class="bg-light rounded h-100 p-4">
							<label for="fileName" class="form-label">이미지</label>
							<input type="file" name="upload" multiple class="form-control uploadInput" id="fileName">
						</div>
						<div class="uploadHidden"></div>
					</div>
					<div class="uploadWrap p-4">
						<ul class="uploadUL">
							<c:forEach items="${list.fileName}" var="fileName" varStatus="status">
								<li data-originName="${fileName}">
									<a href="http://localhost/${fileName}" target="_blank">
										<img src="http://localhost/s_${fileName}" />
									</a>
									<p>${fn:substring(product,37,fn:length(product))}</p>
									<button class="btn btn-danger"
										onclick="javascript:removeFile(event, '${product}')">X</button>
								</li>
							</c:forEach>
						</ul>
					</div>
					<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
					<script src="/js/file.js"></script>
					<div class="button_wrap p-4">
						<a href="/spring/board/read/${list.bno}" class="btn btn-outline-dark">조회 페이지</a>
						<button type="submit" class="btn btn-dark btnAdd">정보 수정</button>
					</div>
				</form>
				<%@ include file="../include/footer.jsp" %>
					<script>
						const bno = ${ list.bno }
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
						//수정버튼 클릭
						btnAdd.addEventListener("click", e => {
							const isRecommend = document.querySelector("input[name=isRecommend]")
							//li 전부 셀렉트
							const liArr = uploadUL.querySelectorAll("li")
							//li개수만큼 for문 돌려서 input hidden 추가
							let str = ""
							for (let liObj of liArr) {
								const originName = liObj.getAttribute("data-originname")
								str += '<input type="hidden" name="fileName" value="' + originName + '">'
							}
							uploadHidden.innerHTML += str
							if (isRecommend.checked === false) {
								uploadHidden.innerHTML += '<input type="hidden" name="isRecommend" value="0">'
							}
							frm.submit()
						}, false)
						//삭제 modal show
						btnDelete.addEventListener("click", e => {
							deleteModal.show()
						}, false)
					</script>
		</body>

		</html>