package board.mybatis.mvc.exception;

// 전화번호 형식이 010-xxxx-xxxx 의 형식에 밎지 않으면 MemberPhoneIllegalArgumentException
public class MemberPhoneIllegalArgumentException extends RuntimeException {
    public MemberPhoneIllegalArgumentException() {
        super();
    }

    public MemberPhoneIllegalArgumentException(String message) {
        super(message);
    }
}