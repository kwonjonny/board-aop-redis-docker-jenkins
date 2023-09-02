package board.mybatis.mvc.exception;

// 패스워드가 형식에 맞지 않다면 PasswordIllegalArgumentException
public class PasswordIllegalArgumentException extends RuntimeException {
    public PasswordIllegalArgumentException() {
        super();
    }

    public PasswordIllegalArgumentException(String message) {
        super();
    }
}
