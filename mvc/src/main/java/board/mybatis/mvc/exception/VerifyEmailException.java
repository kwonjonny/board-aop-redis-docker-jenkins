package board.mybatis.mvc.exception;

/**
 * {@code VerifyEmailException}은 회원이 이메일 인증을 하지 않았을시에 발생하는 예외 클래스입니다.
 */
public class VerifyEmailException extends RuntimeException {
    public VerifyEmailException() {
        super();
    }

    public VerifyEmailException(String message) {
        super(message);
    }
}
