package board.mybatis.mvc.exception;

// 회원가입 시 이미 메일이 존재한다면? MemberEmailDuplicateException
public class MemberEmailDuplicateException extends RuntimeException {
    public MemberEmailDuplicateException() {
        super();
    }

    public MemberEmailDuplicateException(String messagae) {
        super(messagae);
    }
}
