package board.mybatis.mvc.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberPhoneIllegalArgumentException;
import board.mybatis.mvc.exception.PasswordIllegalArgumentException;
import lombok.extern.log4j.Log4j2;

/**
 * 회원 정보 유효성 검증 유틸리티 클래스.
 */
@Log4j2
public class MemberValidator {

    /**
     * 회원 이메일 형식 검증
     * 이메일 주소가 올바른 형식인지 검증합니다.
     *
     * @param email 검증할 이메일 주소. 이메일은 다음과 같은 형식을 따라야 합니다:
     *              - 영문 대소문자, 숫자, 특수문자(+_.-)가 허용됩니다.
     *              - @ 기호를 포함해야 합니다.
     *              - 도메인은 영문 대소문자로만 이루어져야 하며, 최소 2글자에서 최대 4글자까지 허용됩니다.
     * @throws InvalidEmailException 이메일 형식이 올바르지 않을 경우 발생합니다.
     */
    public static void validateEmail(final String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
    }

    /**
     * 회원 비밀번호 형식 유효성 검증
     * 비밀번호의 형식을 검증합니다.
     * 비밀번호는 8자 이상이며, 소문자, 대문자, 특수문자, 숫자를 포함해야합니다.
     *
     * @param password 검증할 비밀번호.
     * 
     * @throws PasswordIllegalArgumentException 비밀번호 형식이 올바르지 않을 경우 발생합니다.
     */
    public static void validatePassword(final String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_-]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new PasswordIllegalArgumentException("비밀번호는 8자 이상이며, 소문자, 대문자, 특수문자, 숫자를 포함해야합니다.");
        }
    }

    /**
     * 회원 전화번호 형식 유효성 검증
     * 전화번호가 010-xxxx-xxxx 형식인지 검증합니다.
     *
     * @param phoneNumber 검증할 전화번호.
     * 
     * @throws MemberPhoneIllegalArgumentException 전화번호 형식이 올바르지 않을 경우 발생합니다.
     */
    public static void validatePhoneNumber(final String phoneNumber) {
        Pattern pattern = Pattern.compile("^010-\\d{4}-\\d{4}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new MemberPhoneIllegalArgumentException("전화번호는 010-xxxx-xxxx 형식이어야 합니다.");
        }
    }
}
