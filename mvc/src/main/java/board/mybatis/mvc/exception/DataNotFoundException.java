package board.mybatis.mvc.exception;

/**
 * {@code DataNotFoundException}은 데이터가 없을 때 발생하는 예외 클래스입니다.
 */
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super();
    }

     /**
     * 메시지를 포함하는 생성자입니다.
     *
     * @param message 예외 메시지
     */
    public DataNotFoundException(String mesage) {
        super(mesage);
    }
}
