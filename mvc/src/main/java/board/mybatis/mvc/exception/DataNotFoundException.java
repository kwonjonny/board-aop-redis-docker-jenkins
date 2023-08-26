package board.mybatis.mvc.exception;

// 데이터가 없으면? DataNotFoundException
public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String mesage) {
        super(mesage);
    }
}
