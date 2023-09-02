package board.mybatis.mvc.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberPhoneIllegalArgumentException;
import board.mybatis.mvc.exception.PasswordIllegalArgumentException;

public class MemberValidator {
    /*
     * 회원 이메일 형식 검증
     */
     public static void validateEmail(String email) {
        Pattern pattern = Pattern.compile("/^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$/");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
    }

     /*
     * 회원 비밀번호 형식 유효성 검증
     * 비밀번호 길이: 8 자 이상
     * 최소 하나의 대문자 포함
     * 최소 하나의 소문자 포함
     */
    public static void validatePassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_-]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new PasswordIllegalArgumentException("비밀번호는 8자 이상이며, 소문자, 대문자, 특수문자, 숫자를 포함해야합니다.");
        }
    }

     /*
     * 회원 전화번호 형식 유효성 검증
     * 010-xxxx-xxx 의 형식
     */
    public static void validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^010-\\d{4}-\\d{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new MemberPhoneIllegalArgumentException("전화번호는 010-xxxx-xxxx 형식이어야 합니다.");
        }
    }
}
