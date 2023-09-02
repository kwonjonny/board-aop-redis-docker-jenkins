package board.mybatis.mvc.exception.advicecontroller;

import java.util.function.Function;

import org.springframework.http.HttpStatus;


// ENUM 타입으로 Advice Controller 의 JSP 화면에 Error Message JSON 데이터 전달 
public enum AdviceErrorEnum {
    
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, ex-> "Data Not Found:" + ex.getMessage()),

    MEMBER_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, ex-> "Duplicate: " + ex.getMessage()),

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found: " + ex.getMessage()),

    MEMBER_PHONE_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Bad Request: " + ex.getMessage()),

    MEMBER_PASSWORD_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Bad Request: " + ex.getMessage()),

    MEMBER_EMAIL_INVALIDATE(HttpStatus.BAD_REQUEST, ex -> "Bad Request: " + ex.getMessage()),

    BOARD_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found: " + ex.getMessage()),

    NOTICE_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found: " + ex.getMessage()), 

    REPLY_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found: " + ex.getMessage()),

    METHOD_ARUGMNET_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, ex -> "Method Argument Type Mismatch: " + ex.getMessage()),

    BIND_EXCEPTION(HttpStatus.BAD_REQUEST, ex -> "Bind Exception: " + ex.getMessage()),

    DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, ex -> "Data Integrity Violation: " + ex.getMessage()),

    NO_SUCH_ELEMENT(HttpStatus.BAD_REQUEST, ex -> "No Such Element: " + ex.getMessage()),

    NULL_POINTER(HttpStatus.INTERNAL_SERVER_ERROR, ex -> "Null Pointer: " + ex.getMessage()),

    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Illegal Argument: " + ex.getMessage()),

    ARRAY_INDEX_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, ex -> "Array Index Out Of Bounds: " + ex.getMessage()),

    NUMBER_FORMAT(HttpStatus.BAD_REQUEST, ex -> "Number Format: " + ex.getMessage()),

    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, ex -> "HTTP Message Not Readable: " + ex.getMessage()),

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ex -> "Resource Not Found: " + ex.getMessage()),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, ex -> "Access Denied: " + ex.getMessage());

    private final HttpStatus status;
    private final Function<Exception, String> messageExtractor;

    AdviceErrorEnum(HttpStatus status, Function<Exception, String> messageExtractor) {
        this.status = status;
        this.messageExtractor = messageExtractor;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage(Exception ex) {
        return messageExtractor.apply(ex);
    }
}
