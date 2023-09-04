package board.mybatis.mvc.exception;

// 인증 정보가 맞지 않다면 AuthorizationException
public class AuthorizationException extends RuntimeException {
    public AuthorizationException() {
        super();
    }

    public AuthorizationException(String message) {
        super(message);
    }
}
