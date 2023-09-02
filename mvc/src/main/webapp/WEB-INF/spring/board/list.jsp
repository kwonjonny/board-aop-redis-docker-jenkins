<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FastPickup</title>
<style>
.notice-row {
background-color: #EEEEEE;
}
</style>
</head>
<body>
<%@ include file="../include/header.jsp" %>
<div class="col-12">
<h3>자유 게시판</h3>
<!-- Search Start -->
<div class="my-4 search_wrap bg-body">
    <form action="/spring/board/list" method="get" class="actionForm">
        <div class="search_box">
            <input type="hidden" name="page" value="${pageRequestDTO.page}">
            <input type="hidden" name="size" value="${pageRequestDTO.size}">
            <div class="input-group mb-3">
                <select name="type" class="form-select search-condition">
                    <option value="">선택해주세요</option>
                    <option value="t" ${pageRequestDTO.type=='t' ? 'selected="selected"' : '' }>제목</option>
                    <option value="c" ${pageRequestDTO.type=='c' ? 'selected="selected"' : '' }>내용</option>
                    <option value="w" ${pageRequestDTO.type=='w' ? 'selected="selected"' : '' }>작성자</option>
                    <option value="tcw" ${pageRequestDTO.type=='tcw' ? 'selected="selected"' : '' }>통합 검색</option>
                </select>
                <input type="text" name="keyword" class="form-control search-input" placeholder="검색어를 입력 해주세요." value="${pageRequestDTO.keyword}">
            </div>
            <div class="input-group input-date-group">
                <!-- 기간 검색 -->
                <input type="date" name="startDate" class="form-control search-input"
                    value="${pageRequestDTO.startDate}">
                <input type="date" name="endDate" class="form-control search-input"
                    value="${pageRequestDTO.endDate}">
                <button type="submit" class="btn btn-primary btnSearch">검색</button>
            </div>
        </div>
    </form>
</div>
<!-- Search End -->
<div class="bg-light rounded h-100 p-4">
<div class="table-responsive">
<table class="table">
<thead>
    <tr>
        <th scope="col">게시물 번호</th>
        <th scope="col">게시물 제목</th>
        <th scope="col">작성자</th>
        <th scope="col">댓글, 좋아요, 조회수</th>
        <th scope="col">게시물 생성 날짜</th>
        <th scope="col">게시물 썸네일 이미지</th>
    </tr>
</thead>
<tbody>
    <c:forEach items="${list.list}" var="board" varStatus="status">
        <c:set var="isNoticeRow" value="${board.nno != null}" />
        <tr class="${isNoticeRow ? 'notice-row' : ''}">
            <td>
                <c:choose>
                    <c:when test="${isNoticeRow}">
                        <a href="/spring/notice/read/${board.nno}?${pageRequestDTO.link}"
                            style="font-size: large;">공지사항</a>
                    </c:when>
                    <c:otherwise>
                        <a
                            href="/spring/board/read/${board.bno}?${pageRequestDTO.link}">${board.bno}</a>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>${board.title}</td>
            <td>${board.writer}</td>
            <td>
                <span class="badge bg-info" style="font-size: 1.2rem; padding: 5px 10px;">댓글:${board.replyCount}</span>
                <span class="badge bg-primary" style="font-size: 1.2rem; padding: 5px 10px;">좋아요:${board.likeCount}</span>
                <span class="badge bg-success" style="font-size: 1.2rem; padding: 5px 10px;">조회:${board.viewCount}</span>
            </td>
            <td>${board.createDate}</td>
            <td><c:if test="${not empty board.fileName}"><img src="http://localhost/s_${board.fileName}"></c:if>
                <c:if test="${empty board.fileName}"><img src="http://localhost/Default.jpg" width="105px" height="105px"/></c:if>
            </td>
        </tr>
    </c:forEach>
</tbody>
</table>
</div>
<div class="button_wrap">
<a href="/spring/board/create" class="btn btn-dark">게시물 생성</a>
</div>
</div>
<!-- Paging Start -->
<div class="btn-toolbar" role="toolbar" style="justify-content: center;">
    <ul class="btn-group me-2 paging" role="group" aria-label="First group">
        <c:if test="${list.prevBtn}">
            <li><a href="${list.startNum - 1}" class="btn btn-group btn-prev">이전</a></li>
        </c:if>
        <c:forEach var="i" begin="${list.startNum}" end="${list.endNum}">
            <li class="${list.page == i ? 'active' : ''}">
                <a href="${i}" class="btn btn-group">${i}</a>
            </li>
        </c:forEach>
        <c:if test="${list.nextBtn}">
            <li><a href="${list.endNum + 1}" class="btn btn-group btn-next">다음</a></li>
        </c:if>
    </ul>
</div>
<!-- Paging End -->
<!-- Modal Start -->
<div class="modal alertModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">${message}</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            </div>
        </div>
    </div>
</div>
<!-- Modal End -->
</div>
<%@ include file="../include/footer.jsp" %>
<!-- JavaScript Start -->
<script>
    const paging = document.querySelector(".paging")
    const actionForm = document.querySelector(".actionForm")
    const pageInput = actionForm.querySelector("input[name=page]")
    const typeObj = actionForm.querySelector("select[name=type]")
    const keywordObj = actionForm.querySelector("input[name=keyword]")
    const btnSearch = document.querySelector(".btnSearch")
    paging.addEventListener("click", (e) => {
        //이벤트 막기
        e.preventDefault()
        e.stopPropagation()
        //target 찾기
        const target = e.target
        //A태그가 아니면 return
        if (target.tagName !== "A") {
            return
        }
        //page번호 설정
        const pageNum = target.getAttribute("href")
        //input에 page변경 넘겨주기
        pageInput.value = pageNum
        //actionForm action 변경
        actionForm.setAttribute("action", "/spring/board/list")
        //submit
        actionForm.submit()
    })
    //검색 버튼
    btnSearch.addEventListener("click", e => {
        //이벤트 막기
        e.preventDefault()
        e.stopPropagation()
        //검색타입, 키워드 입력 안되었을 시 return
        if (typeObj.options[typeObj.selectedIndex].value === "") {
            alert("검색 조건을 선택해주세요")
            typeObj.focus()
            return
        }
        if (keywordObj.value === "") {
            alert("검색어를 입력해주세요")
            keywordObj.focus()
            return
        }
        //검색 하고나면 page는 무조건 1페이지
        pageInput.value = 1
        actionForm.submit()
    }, false)
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