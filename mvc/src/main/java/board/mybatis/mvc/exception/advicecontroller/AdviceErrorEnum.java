package board.mybatis.mvc.exception.advicecontroller;

import java.util.function.Function;

import org.springframework.http.HttpStatus;

/**
 * {@code AdviceErrorEnum}은 에러 코드와 에러 메시지를 관리하는 ENUM 타입입니다.
 * 각 ENUM 값은 특정한 에러 상황을 나타내며, 상태 코드와 에러 메시지를 추출하는 기능을 제공합니다.
 */
public enum AdviceErrorEnum {

    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Data Not Found " + ex.getMessage()),

    MEMBER_EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, ex -> "Duplicate " + ex.getMessage()),

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found " + ex.getMessage()),

    MEMBER_PHONE_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Bad Request " + ex.getMessage()),

    MEMBER_PASSWORD_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Bad Request " + ex.getMessage()),

    MEMBER_EMAIL_INVALIDATE(HttpStatus.BAD_REQUEST, ex -> "Bad Request " + ex.getMessage()),

    BOARD_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found " + ex.getMessage()),

    NOTICE_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found " + ex.getMessage()),

    REPLY_NUMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, ex -> "Not Found " + ex.getMessage()),

    METHOD_ARUGMNET_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, ex -> "Method Argument Type Mismatch " + ex.getMessage()),

    BIND_EXCEPTION(HttpStatus.BAD_REQUEST, ex -> "Bind Exception " + ex.getMessage()),

    DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, ex -> "Data Integrity Violation " + ex.getMessage()),

    NO_SUCH_ELEMENT(HttpStatus.BAD_REQUEST, ex -> "No Such Element " + ex.getMessage()),

    NULL_POINTER(HttpStatus.INTERNAL_SERVER_ERROR, ex -> "Null Pointer " + ex.getMessage()),

    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, ex -> "Illegal Argument " + ex.getMessage()),

    ARRAY_INDEX_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, ex -> "Array Index Out Of Bounds " + ex.getMessage()),

    NUMBER_FORMAT(HttpStatus.BAD_REQUEST, ex -> "Number Format " + ex.getMessage()),

    HTTP_MESSAGE_NOT_READABLE(HttpStatus.BAD_REQUEST, ex -> "HTTP Message Not Readable " + ex.getMessage()),

    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, ex -> "Resource Not Found " + ex.getMessage()),

    ACCESS_DENIED(HttpStatus.FORBIDDEN, ex -> "Access Denied " + ex.getMessage()),

    VERIFY_EMAIL(HttpStatus.BAD_REQUEST, ex -> "No Verify Email " + ex.getMessage()),

    AUTHENTICATION_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ex -> "Authentication Service Error: " + ex.getMessage());


    private final HttpStatus status;
    private final Function<Exception, String> messageExtractor;

    /**
     * AdviceErrorEnum의 생성자입니다.
     *
     * @param status           해당 에러 상황의 HTTP 상태 코드
     * @param messageExtractor 예외 객체로부터 에러 메시지를 추출하는 함수
     */
    AdviceErrorEnum(HttpStatus status, Function<Exception, String> messageExtractor) {
        this.status = status;
        this.messageExtractor = messageExtractor;
    }

    /**
     * HTTP 상태 코드를 반환합니다.
     *
     * @return HTTP 상태 코드
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * 예외 객체로부터 에러 메시지를 추출하여 반환합니다.
     *
     * @param ex 예외 객체
     * @return 추출된 에러 메시지
     */
    public String getMessage(Exception ex) {
        return messageExtractor.apply(ex);
    }
}
