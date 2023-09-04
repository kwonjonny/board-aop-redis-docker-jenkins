
// Reply Axios & JavaScript code 

// Reply Path 
const replyLink = "http://localhost:8084/reply/"

// List Board Reply
const listReplyBoard = async (replyLast = false, page = 1) => {
    console.log("댓글 마지막 여부: ", replyLast, "페이지: ", page)
    const {data } = await axios.get(`${replyLink}board/list/${bno}?page=${page}&replyLast=${replyLast}`)
    return data.result;
}

// Create Board Reply 
const createReplyBoard = async (list) => {
    console.log("댓글 생성: ", list)
    const { data } = await axios.post(`${replyLink}board/create`, list)
    return data.result;
}

// Update Board Reply 
const updateReplyBoard = async (list) => {
    console.log("댓글 수정: " ,list)
    const { data } = await axios.put(`${replyLink}board/update`, list)
    return data.result;
}

// Delete Board Reply 
const deleteReplyBoard = async (bno) => {
    console.log("댓글 삭제: ", bno)
    const { data } = await axios.delete(`${replyLink}board/delete/${bno}`)
    return data.result;
}

// Count Board Reply 
const countReplyBoard = async (bno) => {
    console.log("댓글 수", bno)
    const { data } = await axios.get(`${replyLink}board/count/${bno}`)
    return data.result;
}

// Get Board User Details 
const getUserDetailsBoard = async () => {
    const { data } = await axios.get(`${replyLink}board/user/email`)
    console.log('유저정보',data.email)
    return data.email;
}

// 동적 댓글 리스트 
const listReplyAxios = (replyLast, page) => {
   getUserDetailsBoard().then(currentUserEmail => {

    listReplyBoard(replyLast, page).then(arr => {
        let replyStr = ""
        let replyPagingStr = ""
        for(let i = 0; i< arr.list.length; i++) {
            const { reply, replyer, createDate, updateDate, step, gno, rno, isDeleted, bno} = arr.list[i]
            
            const showEditDeleteButtons = replyer === currentUserEmail;

            replyStr += 
            `
            <div class="card mb-3 ${step > 0 ? "reply-margin" : ""}" data-gno="${gno}">
            <div class="card-body">
            <div class="row">
            <div class="col">
            <div class="reply-content">
            <h6 class="mb-0">${reply}</h6>
            <span>작성자: ${replyer}</span>
            <div class="d-felx justify-content-between">
            <small>작성 날짜: ${createDate}</small>  
            <small>수정 날짜: ${updateDate}</small>
            </div>
            </div>
            </div>
            <div class="col-auto d-flex align-items-center">
            ${showEditDeleteButtons ? `
            <button class="btn btn-outline-secondary reply-delete" data-reply="replyDelete" data-rno="${rno}">삭제</button>
            <button class="btn btn-outline-secondary reply-update" data-reply="replyUpdate"  data-gno="${gno}" data-bno="${bno}" data-rno="${rno}" data-replyer="${replyer}" data-reply="${reply}">수정</button>
            ` : ''}

            <button class="btn btn-outline-secondary reply-replyChild" data-reply="replyChild" data-rno="${rno}">답글달기</button>
            </div>
            </div>
            </div>
            </div>
            `
        }
        const { page, size, startNum, endNum, prevBtn, nextBtn, replyLast, total } = arr

        prevBtn === true ? replyPagingStr += `<li><button data-page="${startNum - 1}" class="btn btn-primary">이전</button></li>` : ""

        for (let i = startNum; i <= endNum; i++) {
            replyPagingStr += `
        <li${page === i ? " class='active'" : ''}> <button data-page="${i}" class="btn btn-primary">${i}</button> </li>`
        }
        nextBtn === true ? replyPagingStr += `<li><button data-page="${endNum + 1}" class="btn btn-primary">다음</button></li>` : ""

        replyWrap.innerHTML = replyStr
        replyPaging.innerHTML = replyPagingStr
        })
    })
}

document.addEventListener('DOMContentLoaded', (event) => {
    getUserDetailsBoard().then(currentUserEmail => {
    const replyWrap = document.querySelector('.replyWrap');
    ///////////////
    // 답글 입니다 //
    //////////////
    // 답글 버튼 클릭 이벤트 리스너 추가
    replyWrap.addEventListener("click", (e) => {
        if (e.target && e.target.classList.contains("reply-replyChild")) {
            e.preventDefault();
            e.stopPropagation();
            const target = e.target;

            if (target.dataset.reply === "replyChild") {
                const rno = target.dataset.rno;
                const gno = target.closest(".card").dataset.gno; // 부모 댓글의 gno 값 가져오기
                const replyContentElement = target.closest(".card-body").querySelector(".reply-content");

                // 답글 작성 폼 생성
                const replyForm = document.createElement("div");
                replyForm.innerHTML = `
                <div class="reply-form-wrapper">
                    <input type="text" class="form-control replyer-input" placeholder="작성자" value="${currentUserEmail}" readOnly/>
                    <textarea class="form-control reply-input" rows="3" placeholder="답글 내용을 입력하세요" data-gno="${gno}" data-bno="${bno}"></textarea>
                        <div class="reply-buttons">
                        <button class="btn btn-primary reply-submit" data-rno="${rno}">작성 완료</button>
                        <button class="btn btn-secondary reply-cancel">취소</button>
                    </div>
                </div>
                `;

                // 기존 답글 내용 밑에 답글 작성 폼 추가
                const replyContent = replyContentElement.innerHTML;
                replyContentElement.innerHTML += replyForm.innerHTML;
                // 작성 완료 버튼 클릭 이벤트 리스너 추가

                const replySubmitBtn = replyContentElement.querySelector(".reply-submit");
                replySubmitBtn.addEventListener("click", () => {
                    const replyerInput = replyContentElement.querySelector(".replyer-input");
                    const replyInput = replyContentElement.querySelector(".reply-input");
                    const replyer = replyerInput.value;
                    const reply = replyInput.value;
                    const gno = target.closest(".card").dataset.gno;
                    const bno = replyInput.dataset.bno;
                    const rno = target.dataset.rno;

                    // 답글 등록 처리 (Ajax 등)
                    const list = {
                        replyer: replyer,
                        reply: reply,
                        gno: gno,
                        bno: bno,
                        rno:rno,
                    };
                    // createReplyBoard 함수 호출
                    createReplyBoard(list)
                        .then((response) => {
                            // 답글 등록 성공 시 처리할 로직
                            console.log(response);

                            // 답글 작성 폼 제거
                            replyContentElement.innerHTML = replyContent;

                            // 답글 등록 후 댓글 목록을 다시 로드
                            listReplyAxios(false, 1);
                        })
                });

                // 취소 버튼 클릭 이벤트 리스너 추가
                const replyCancelBtn = replyContentElement.querySelector(".reply-cancel");
                replyCancelBtn.addEventListener("click", () => {
                    // 답글 작성 폼 제거
                    replyContentElement.innerHTML = replyContent;
                });
            }
        }
    })
});


    //////////////
    // 삭제 입니다 //
    //////////////
    // 삭제 버튼에 대한 이벤트 리스너
    replyWrap.addEventListener("click", (e) => {
        if (e.target && e.target.classList.contains("reply-delete")) {
            const rno = e.target.dataset.rno;
            deleteReplyBoard(rno).then((response) => {
                // 삭제 성공 시 처리할 로직
                console.log("응답 메시지", response);
                // 댓글 목록 다시 로드
                listReplyAxios(false, 1);
            });
        }
    });


    //////////////
    // 수정 입니다 //
    //////////////

    // 수정 이벤트 리스너
    replyWrap.addEventListener("click", (e) => {
        const target = e.target;

        if (target && target.classList.contains("reply-update")) {
            const replyContentElement = target.closest(".card-body").querySelector(".reply-content");
            const replyText = replyContentElement.querySelector("h6.mb-0").innerText;

            // 기존 댓글 내용을 가져오기
            const rno = target.dataset.rno;
            const replyer = target.dataset.replyer;
            const bno = target.dataset.bno;
            const gno = target.dataset.gno;

            // 수정 폼 생성
            const replyForm = document.createElement("div");
            replyForm.innerHTML = `
            <div class="reply-form-wrapper">
                <input type="text" class="form-control replyer-input" placeholder="작성자" value="${replyer}" readOnly />
                <textarea class="form-control reply-input" rows="3" placeholder="댓글 내용을 입력하세요">${replyText}</textarea>
                <div class="reply-buttons">
                    <button class="btn btn-primary reply-update-confirm" data-rno="${rno}">확인</button>
                    <button class="btn btn-secondary reply-update-cancel">취소</button>
                </div>
            </div>
        `;

            // 기존 댓글 내용을 수정 폼으로 바꾸기
            replyContentElement.innerHTML = '';
            replyContentElement.appendChild(replyForm);

            // 확인 버튼 클릭 이벤트 리스너
            const confirmButton = replyContentElement.querySelector(".reply-update-confirm");
            confirmButton.addEventListener("click", () => {
                const newReply = replyContentElement.querySelector(".reply-input").value;

                const list = {
                    replyer: replyer,
                    reply: newReply,
                    rno: rno,
                    bno: bno,
                    gno: gno
                };

                // 수정된 댓글을 서버로 보냄
                updateReplyBoard(list).then((response) => {
                    console.log(response);
                    // 댓글 목록 다시 로드
                    listReplyAxios(false, 1);
                });
            });

            // 취소 버튼 클릭 이벤트 리스너
            const cancelButton = replyContentElement.querySelector(".reply-update-cancel");
            cancelButton.addEventListener("click", () => {
                // 댓글 목록 다시 로드
                listReplyAxios(false, 1);
            });
        }
    });
});

