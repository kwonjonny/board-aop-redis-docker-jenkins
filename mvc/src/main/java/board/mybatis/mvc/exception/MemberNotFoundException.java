package board.mybatis.mvc.exception;

// 회원의 이메일이 없으면? MemberNotFoundException
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException() {
        super();
    }

    public MemberNotFoundException(String message) {
        super(message);
    }
}
