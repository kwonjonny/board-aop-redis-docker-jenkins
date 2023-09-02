function validateEmail(email) {
    const pattern = /^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\.)+[A-Za-z]{2,4}$/;
    return pattern.test(email);
}

function validatePassword(password) {
    const pattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_-]).{8,}$/;
    return pattern.test(password);
}

function validatePhoneNumber(phoneNumber) {
    const pattern = /^010-\d{4}-\d{4}$/;
    return pattern.test(phoneNumber);
}

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('email').addEventListener('blur', function () {
        if (!validateEmail(this.value)) {
            this.nextElementSibling.textContent = "이메일 형식이 올바르지 않습니다.";
        } else {
            this.nextElementSibling.textContent = "";
        }
    });

    document.getElementById('memberPw').addEventListener('blur', function () {
        if (!validatePassword(this.value)) {
            this.nextElementSibling.textContent = "비밀번호는 8자 이상이며, 소문자, 대문자, 특수문자, 숫자를 포함해야합니다.";
        } else {
            this.nextElementSibling.textContent = "";
        }
    });

    document.getElementById('memberPhone').addEventListener('blur', function () {
        if (!validatePhoneNumber(this.value)) {
            this.nextElementSibling.textContent = "전화번호는 010-xxxx-xxxx 형식이어야 합니다.";
        } else {
            this.nextElementSibling.textContent = "";
        }
    });
});
