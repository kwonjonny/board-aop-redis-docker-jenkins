package board.mybatis.mvc.exception.advicecontroller;

import java.net.BindException;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import board.mybatis.mvc.controller.BoardController;
import board.mybatis.mvc.controller.FileUploadController;
import board.mybatis.mvc.controller.LikeController;
import board.mybatis.mvc.controller.MemberController;
import board.mybatis.mvc.controller.NoticeController;
import board.mybatis.mvc.controller.ReplyController;
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

/*
 * Controller, RestController 의 담당 Advice Error Controller Class 
 * Error 발생시
 * 상태, 메시지, 발생 시점 
 * error.jsp 에 전달 
 */
@Log4j2
@ControllerAdvice(assignableTypes = { BoardController.class, MemberController.class, NoticeController.class })
@RestControllerAdvice(assignableTypes = { ReplyController.class, FileUploadController.class, LikeController.class })
public class AdviceController {

  /*
   * time : Error 발생 시점
   * status : 상태
   * message : 메시지
   */
  private ModelAndView generateErrorModelAndView(AdviceErrorEnum errorCode, Exception ex) {
    log.error("Exception received: " + ex.toString(), ex);
    Map<String, Object> model = new HashMap<>();
    String readableTime = new SimpleDateFormat("yyyy/MM/dd일 HH시:mm분").format(new Date());
    model.put("time", readableTime);
    HttpStatus status = errorCode.getStatus();
    model.put("status", status.value());
    model.put("message", errorCode.getMessage(ex));
    ModelAndView modelAndView = new ModelAndView("spring/error", model);
    modelAndView.setStatus(status);
    return modelAndView;
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ModelAndView handleDataNotFoundException(DataNotFoundException ex) {
    log.info("Is Running HandleDataNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.DATA_NOT_FOUND, ex);
  }

  @ExceptionHandler(MemberEmailDuplicateException.class)
  public ModelAndView handleMemberEmailDuplicateException(MemberEmailDuplicateException ex) {
    log.info("Is Running HandleMemberEmailDuplicateException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_EMAIL_DUPLICATE, ex);
  }

  @ExceptionHandler(MemberNotFoundException.class)
  public ModelAndView handleMemberNotFoundException(MemberNotFoundException ex) {
    log.info("Is Running HandleMemberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(PasswordIllegalArgumentException.class)
  public ModelAndView handlePasswordIllegalArgumentException(
      PasswordIllegalArgumentException ex) {
    log.info("Is Running HandlePasswordIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_PASSWORD_ARGUMENT, ex);
  }

  @ExceptionHandler(MemberPhoneIllegalArgumentException.class)
  public ModelAndView handleMemberPhoneIllegalArgumentException(
      MemberPhoneIllegalArgumentException ex) {
    log.info("Is Running HandleMemberPhoneIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_PHONE_ARGUMENT, ex);
  }

  @ExceptionHandler(InvalidEmailException.class)
  public ModelAndView handleInvalidEmailException(InvalidEmailException ex) {
    log.info("Is Running HandleInvalidEmailException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_EMAIL_INVALIDATE, ex);
  }

  @ExceptionHandler(BoardNumberNotFoundException.class)
  public ModelAndView handleBoardNumberNotFoundException(BoardNumberNotFoundException ex) {
    log.info("Is Running HandleBoardNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.BOARD_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(NoticeNumberNotFoundException.class)
  public ModelAndView handleNoticeNumberNotFoundException(NoticeNumberNotFoundException ex) {
    log.info("Is Running HandleNoticeNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.NOTICE_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(ReplyNumberNotFoundException.class)
  public ModelAndView handleReplyNumberNotFoundException(ReplyNumberNotFoundException ex) {
    log.info("Is Running HandleReplyNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.REPLY_NUMBER_NOT_FOUND, ex);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ModelAndView handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.info("Is Running HandleMethodArgumentTypeMismatchException");
    return generateErrorModelAndView(AdviceErrorEnum.METHOD_ARUGMNET_TYPE_MISMATCH, ex);
  }

  @ExceptionHandler(BindException.class)
  public ModelAndView handleBindException(BindException ex) {
    log.info("Is Running HandleBindException");
    return generateErrorModelAndView(AdviceErrorEnum.BIND_EXCEPTION, ex);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    log.info("Is Running HandleDataIntegrityViolationException");
    return generateErrorModelAndView(AdviceErrorEnum.DATA_INTEGRITY_VIOLATION, ex);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ModelAndView handleNoSuchElementException(NoSuchElementException ex) {
    log.info("Is Running HandleNoSuchElementException");
    return generateErrorModelAndView(AdviceErrorEnum.NO_SUCH_ELEMENT, ex);
  }

  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNullPointerException(NullPointerException ex) {
    log.info("Is Running HandleNullPointerException");
    return generateErrorModelAndView(AdviceErrorEnum.NULL_POINTER, ex);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
    log.info("Is Running HandleIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.ILLEGAL_ARGUMENT, ex);
  }

  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  public ModelAndView handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
    log.info("Is Running HandleArrayIndexOutOfBoundsException");
    return generateErrorModelAndView(AdviceErrorEnum.ARRAY_INDEX_OUT_OF_BOUNDS, ex);
  }

  @ExceptionHandler(NumberFormatException.class)
  public ModelAndView handleNumberFormatException(NumberFormatException ex) {
    log.info("Is Running HandleNumberFormatException");
    return generateErrorModelAndView(AdviceErrorEnum.NUMBER_FORMAT, ex);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ModelAndView handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.info("Is Running HandleHttpMessageNotReadableException");
    return generateErrorModelAndView(AdviceErrorEnum.HTTP_MESSAGE_NOT_READABLE, ex);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ModelAndView handleAccessDeniedException(AccessDeniedException ex) {
    log.info("Is Running HandleAccessDeniedException");
    return generateErrorModelAndView(AdviceErrorEnum.ACCESS_DENIED, ex);
  }
}