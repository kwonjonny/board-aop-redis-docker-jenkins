package board.mybatis.mvc.exception;

// 해당하는 Board Number 가 없다면? BoardNumberNotFoundException
public class BoardNumberNotFoundException extends RuntimeException {
    public BoardNumberNotFoundException() {
        super();
    }

    public BoardNumberNotFoundException(String message) {
        super(message);
    }
}
