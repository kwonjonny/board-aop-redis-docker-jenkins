document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('title').addEventListener('blur', function() {
        // 제목 길이 검사
        if (this.value.length < 5 || this.value.length > 100) {
            this.nextElementSibling.textContent = "제목은 5자 이상 100자 이하로 입력해주세요.";
        } else {
            this.nextElementSibling.textContent = "";
        }
    });

    document.getElementById('content').addEventListener('blur', function() {
        // 내용 길이 검사
        if (this.value.length < 10 || this.value.length > 2000) {
            this.nextElementSibling.textContent = "내용은 10자 이상 2000자 이하로 입력해주세요.";
        } else {
            this.nextElementSibling.textContent = "";
        }
    });
});
