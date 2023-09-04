package board.mybatis.mvc.exception;

/**
 * {@code BoardNumberNotFoundException}은 게시물 번호를 찾지 못했을 때 발생하는 예외 클래스입니다.
 */
public class BoardNumberNotFoundException extends RuntimeException {
    public BoardNumberNotFoundException() {
        super();
    }

    /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public BoardNumberNotFoundException(String message) {
        super(message);
    }
}
