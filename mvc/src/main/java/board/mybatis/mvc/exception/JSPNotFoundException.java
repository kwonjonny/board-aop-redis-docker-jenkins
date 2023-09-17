package board.mybatis.mvc.exception;

/**
 * {@code JSPNotFoundException}은 회원가입 및 회원탈퇴 JSP페이지가 없을 때 때 발생하는 예외 클래스입니다.
 */
public class JSPNotFoundException extends RuntimeException {
    public JSPNotFoundException() {
        super();
    }

    public JSPNotFoundException(String message, Exception e) {
        super(message, e);
    }
}
