package board.mybatis.mvc.exception;

// 이메일이 올바른 형식이 아니면 ? InvalidEmailException
public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException() {
        super();
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
