package board.mybatis.mvc.exception;

/**
 * {@code AuthorizationException}은 인증 정보가 맞지 않을 경우 발생하는 예외 클래스입니다.
 */
public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super();
    }

     /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public AuthorizationException(String message) {
        super(message);
    }
}
