package board.mybatis.mvc.exception;

/**
 * {@code ReplyNumberNotFoundException}은 해당하는 댓글 번호가 없을 경우 발생하는 예외 클래스입니다.
 */
public class ReplyNumberNotFoundException extends RuntimeException {
    public ReplyNumberNotFoundException() {
        super();
    }

    /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public ReplyNumberNotFoundException(String message) {
        super(message);
    }
}
