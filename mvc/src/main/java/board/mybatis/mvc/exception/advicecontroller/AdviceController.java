package board.mybatis.mvc.exception.advicecontroller;

import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import board.mybatis.mvc.controller.BoardController;
import board.mybatis.mvc.controller.MemberController;
import board.mybatis.mvc.controller.NoticeController;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.MemberPhoneIllegalArgumentException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.exception.PasswordIllegalArgumentException;
import board.mybatis.mvc.exception.ReplyNumberNotFoundException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice(assignableTypes = { BoardController.class, MemberController.class, NoticeController.class })
public class AdviceController {

  /*
   * Time : Error 발생 시점
   * AdviceErrorStatus : Status
   * AdviceErrorMessage : Message
   */
  private Map<String, String> generateErrorMap(AdviceErrorEnum errorCode, Exception ex) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("time", "" + System.currentTimeMillis());
    errorMap.put("status", String.valueOf(errorCode.getStatus().value()));
    errorMap.put("message", errorCode.getMessage(ex));
    return errorMap;
  }

  private ResponseEntity<Map<String, String>> generateErrorEntity(AdviceErrorEnum errorCode, Exception ex) {
    return new ResponseEntity<>(generateErrorMap(errorCode, ex), errorCode.getStatus());
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleDataNotFoundException(DataNotFoundException ex) {
    log.info("Is Running HandleDataNotFoundException");
    return generateErrorEntity(AdviceErrorEnum.DATA_NOT_FOUND, ex);
  }

  @ExceptionHandler(MemberEmailDuplicateException.class)
  public ResponseEntity<Map<String, String>> handleMemberEmailDuplicateException(MemberEmailDuplicateException ex) {
    log.info("Is Running HandleMemberEmailDuplicateException");
    return generateErrorEntity(AdviceErrorEnum.MEMBER_EMAIL_DUPLICATE, ex);
  }

  @ExceptionHandler(MemberNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleMemberNotFoundException(MemberNotFoundException ex) {
    log.info("Is Running HandleMemberNotFoundException");
    return generateErrorEntity(AdviceErrorEnum.MEMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(PasswordIllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handlePasswordIllegalArgumentException(
      PasswordIllegalArgumentException ex) {
    log.info("Is Running HandlePasswordIllegalArgumentException");
    return generateErrorEntity(AdviceErrorEnum.MEMBER_PASSWORD_ARGUMENT, ex);
  }

  @ExceptionHandler(MemberPhoneIllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleMemberPhoneIllegalArgumentException(
      MemberPhoneIllegalArgumentException ex) {
    log.info("Is Running HandleMemberPhoneIllegalArgumentException");
    return generateErrorEntity(AdviceErrorEnum.MEMBER_PHONE_ARGUMENT, ex);
  }

  @ExceptionHandler(InvalidEmailException.class)
  public ResponseEntity<Map<String, String>> handleInvalidEmailException(InvalidEmailException ex) {
    log.info("Is Running HandleInvalidEmailException");
    return generateErrorEntity(AdviceErrorEnum.MEMBER_EMAIL_INVALIDATE, ex);
  }

  @ExceptionHandler(BoardNumberNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleBoardNumberNotFoundException(BoardNumberNotFoundException ex) {
    log.info("Is Running HandleBoardNumberNotFoundException");
    return generateErrorEntity(AdviceErrorEnum.BOARD_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(NoticeNumberNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleNoticeNumberNotFoundException(NoticeNumberNotFoundException ex) {
    log.info("Is Running HandleNoticeNumberNotFoundException");
    return generateErrorEntity(AdviceErrorEnum.NOTICE_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(ReplyNumberNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleReplyNumberNotFoundException(ReplyNumberNotFoundException ex) {
    log.info("Is Running HandleReplyNumberNotFoundException");
    return generateErrorEntity(AdviceErrorEnum.REPLY_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.info("Is Running HandleMethodArgumentTypeMismatchException");
    return generateErrorEntity(AdviceErrorEnum.METHOD_ARUGMNET_TYPE_MISMATCH, ex);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
    log.info("Is Running HandleBindException");
    return generateErrorEntity(AdviceErrorEnum.BIND_EXCEPTION, ex);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    log.info("Is Running HandleDataIntegrityViolationException");
    return generateErrorEntity(AdviceErrorEnum.DATA_INTEGRITY_VIOLATION, ex);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException ex) {
    log.info("Is Running HandleNoSuchElementException");
    return generateErrorEntity(AdviceErrorEnum.NO_SUCH_ELEMENT, ex);
  }

  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<Map<String, String>> handleNullPointerException(NullPointerException ex) {
    log.info("Is Running HandleNullPointerException");
    return generateErrorEntity(AdviceErrorEnum.NULL_POINTER, ex);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.info("Is Running HandleIllegalArgumentException");
    return generateErrorEntity(AdviceErrorEnum.ILLEGAL_ARGUMENT, ex);
  }

  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  public ResponseEntity<Map<String, String>> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
    log.info("Is Running HandleArrayIndexOutOfBoundsException");
    return generateErrorEntity(AdviceErrorEnum.ARRAY_INDEX_OUT_OF_BOUNDS, ex);
  }

  @ExceptionHandler(NumberFormatException.class)
  public ResponseEntity<Map<String, String>> handleNumberFormatException(NumberFormatException ex) {
    log.info("Is Running HandleNumberFormatException");
    return generateErrorEntity(AdviceErrorEnum.NUMBER_FORMAT, ex);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.info("Is Running HandleHttpMessageNotReadableException");
    return generateErrorEntity(AdviceErrorEnum.HTTP_MESSAGE_NOT_READABLE, ex);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException ex) {
    log.info("Is Running HandleAccessDeniedException");
    return generateErrorEntity(AdviceErrorEnum.ACCESS_DENIED, ex);
  }
}
