package board.mybatis.mvc.exception;

/**
 * {@code PasswordIllegalArgumentException}은 패스워드가 형식에 맞지 않을 경우 발생하는 예외 클래스입니다.
 */
public class PasswordIllegalArgumentException extends RuntimeException {
    public PasswordIllegalArgumentException() {
        super();
    }

    /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public PasswordIllegalArgumentException(String message) {
        super(message);
    }
}
