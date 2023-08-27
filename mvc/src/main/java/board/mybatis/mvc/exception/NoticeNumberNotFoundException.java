package board.mybatis.mvc.exception;

// 해당하는 Notice Number 가 없다면? NoticeNumberNotFoundException
public class NoticeNumberNotFoundException extends RuntimeException {
    public NoticeNumberNotFoundException() {
        super();
    }

    public NoticeNumberNotFoundException(String message) {
        super(message);
    }
}
