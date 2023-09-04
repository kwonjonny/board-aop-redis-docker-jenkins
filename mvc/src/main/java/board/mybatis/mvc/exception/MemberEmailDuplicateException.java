package board.mybatis.mvc.exception;

/**
 * {@code MemberEmailDuplicateException}은 회원가입 시 이미 해당 메일이 존재할 경우 발생하는 예외 클래스입니다.
 */
public class MemberEmailDuplicateException extends RuntimeException {
    public MemberEmailDuplicateException() {
        super();
    }

     /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public MemberEmailDuplicateException(String messagae) {
        super(messagae);
    }
}
