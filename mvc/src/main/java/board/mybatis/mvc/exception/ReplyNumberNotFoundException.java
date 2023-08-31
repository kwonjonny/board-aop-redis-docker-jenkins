package board.mybatis.mvc.exception;

// 해당하는 댓글 번호가 없다면? ReplyNumberNotFoundException
public class ReplyNumberNotFoundException extends RuntimeException {
    public ReplyNumberNotFoundException() {
        super();
    }

    public ReplyNumberNotFoundException(String message) {
        super(message);
    }
}
