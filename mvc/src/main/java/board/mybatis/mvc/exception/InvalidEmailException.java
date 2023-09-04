package board.mybatis.mvc.exception;

/**
 * {@code InvalidEmailException}은 이메일이 올바른 형식이 아닐 때 발생하는 예외 클래스입니다.
 */
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super();
    }

     /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public InvalidEmailException(String message) {
        super(message);
    }
}
