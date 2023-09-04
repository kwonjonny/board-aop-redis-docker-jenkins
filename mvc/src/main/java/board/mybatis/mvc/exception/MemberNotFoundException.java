package board.mybatis.mvc.exception;

/**
 * {@code MemberNotFoundException}은 회원의 이메일이 없을 경우 발생하는 예외 클래스입니다.
 */
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super();
    }

    /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public MemberNotFoundException(String message) {
        super(message);
    }
}
