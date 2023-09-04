package board.mybatis.mvc.exception;

/**
 * {@code MemberPhoneIllegalArgumentException}은 회원 전화번호가 010-xxxx-xxxx 형식에 맞지 않을
 * 경우 발생하는 예외 클래스입니다.
 */
public class MemberPhoneIllegalArgumentException extends RuntimeException {
    public MemberPhoneIllegalArgumentException() {
        super();
    }

    /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public MemberPhoneIllegalArgumentException(String message) {
        super(message);
    }
}