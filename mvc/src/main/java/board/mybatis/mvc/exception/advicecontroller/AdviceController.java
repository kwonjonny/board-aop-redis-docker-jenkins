package board.mybatis.mvc.exception.advicecontroller;

import java.net.BindException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
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
import board.mybatis.mvc.controller.StatsController;
import board.mybatis.mvc.exception.AuthorizationException;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.MemberPhoneIllegalArgumentException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.exception.PasswordIllegalArgumentException;
import board.mybatis.mvc.exception.ReplyNumberNotFoundException;
import board.mybatis.mvc.exception.VerifyEmailException;
import lombok.extern.log4j.Log4j2;

/**
 * Controller 및 RestController의 예외 처리를 담당하는 Advice 클래스입니다.
 * 에러가 발생할 경우 상태, 메시지 및 발생 시점을 error.jsp에 전달합니다.
 */
@Log4j2
@ControllerAdvice(assignableTypes = { BoardController.class, MemberController.class, NoticeController.class,
    StatsController.class })
@RestControllerAdvice(assignableTypes = { ReplyController.class, FileUploadController.class, LikeController.class })
public class AdviceController {

  /**
   * 에러 페이지를 생성하는 메서드입니다.
   *
   * @param errorCode AdviceErrorEnum에서 정의한 에러 코드
   * @param ex        발생한 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
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

  /**
   * 예외 처리 메서드: DataNotFoundException 처리
   * 
   * @param ex DataNotFoundException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(DataNotFoundException.class)
  public ModelAndView handleDataNotFoundException(DataNotFoundException ex) {
    log.info("Is Running HandleDataNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.DATA_NOT_FOUND, ex);
  }

  /**
   * 예외 처리 메서드: MemberEmailDuplicateException 처리
   * 
   * @param ex MemberEmailDuplicateException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(MemberEmailDuplicateException.class)
  public ModelAndView handleMemberEmailDuplicateException(MemberEmailDuplicateException ex) {
    log.info("Is Running HandleMemberEmailDuplicateException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_EMAIL_DUPLICATE, ex);
  }

  /**
   * 예외 처리 메서드: MemberNotFoundException 처리
   *
   * 이 메서드는 {@link MemberNotFoundException} 예외가 발생했을 때 실행됩니다. 회원을 찾지 못하는 예외를
   * 처리합니다.
   *
   * @param ex MemberNotFoundException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(MemberNotFoundException.class)
  public ModelAndView handleMemberNotFoundException(MemberNotFoundException ex) {
    log.info("Is Running HandleMemberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_NOT_FOUND, ex);
  }

  /**
   * 예외 처리 메서드: PasswordIllegalArgumentException 처리
   *
   * 이 메서드는 {@link PasswordIllegalArgumentException} 예외가 발생했을 때 실행됩니다. 회원 비밀번호 유효성
   * 검사 실패 예외를 처리합니다.
   *
   * @param ex PasswordIllegalArgumentException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(PasswordIllegalArgumentException.class)
  public ModelAndView handlePasswordIllegalArgumentException(
      PasswordIllegalArgumentException ex) {
    log.info("Is Running HandlePasswordIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_PASSWORD_ARGUMENT, ex);
  }

  /**
   * 예외 처리 메서드: MemberPhoneIllegalArgumentException 처리
   *
   * 이 메서드는 {@link MemberPhoneIllegalArgumentException} 예외가 발생했을 때 실행됩니다. 회원 전화번호
   * 유효성 검사 실패 예외를 처리합니다.
   *
   * @param ex MemberPhoneIllegalArgumentException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(MemberPhoneIllegalArgumentException.class)
  public ModelAndView handleMemberPhoneIllegalArgumentException(
      MemberPhoneIllegalArgumentException ex) {
    log.info("Is Running HandleMemberPhoneIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_PHONE_ARGUMENT, ex);
  }

  /**
   * 예외 처리 메서드: InvalidEmailException 처리
   *
   * 이 메서드는 {@link InvalidEmailException} 예외가 발생했을 때 실행됩니다. 올바르지 않은 이메일 형식 예외를
   * 처리합니다.
   *
   * @param ex InvalidEmailException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(InvalidEmailException.class)
  public ModelAndView handleInvalidEmailException(InvalidEmailException ex) {
    log.info("Is Running HandleInvalidEmailException");
    return generateErrorModelAndView(AdviceErrorEnum.MEMBER_EMAIL_INVALIDATE, ex);
  }

  /**
   * 예외 처리 메서드: BoardNumberNotFoundException 처리
   *
   * 이 메서드는 {@link BoardNumberNotFoundException} 예외가 발생했을 때 실행됩니다. 게시물 번호를 찾지 못하는
   * 예외를 처리합니다.
   *
   * @param ex BoardNumberNotFoundException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(BoardNumberNotFoundException.class)
  public ModelAndView handleBoardNumberNotFoundException(BoardNumberNotFoundException ex) {
    log.info("Is Running HandleBoardNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.BOARD_NUMBER_NOT_FOUND, ex);
  }

  /**
   * 예외 처리 메서드: NoticeNumberNotFoundException 처리
   *
   * 이 메서드는 {@link NoticeNumberNotFoundException} 예외가 발생했을 때 실행됩니다. 공지사항 번호를 찾지
   * 못하는 예외를 처리합니다.
   *
   * @param ex NoticeNumberNotFoundException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(NoticeNumberNotFoundException.class)
  public ModelAndView handleNoticeNumberNotFoundException(NoticeNumberNotFoundException ex) {
    log.info("Is Running HandleNoticeNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.NOTICE_NUMBER_NOT_FOUND, ex);
  }

  /**
   * 예외 처리 메서드: ReplyNumberNotFoundException 처리
   *
   * 이 메서드는 {@link ReplyNumberNotFoundException} 예외가 발생했을 때 실행됩니다. 댓글 번호를 찾지 못하는
   * 예외를 처리합니다.
   *
   * @param ex ReplyNumberNotFoundException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(ReplyNumberNotFoundException.class)
  public ModelAndView handleReplyNumberNotFoundException(ReplyNumberNotFoundException ex) {
    log.info("Is Running HandleReplyNumberNotFoundException");
    return generateErrorModelAndView(AdviceErrorEnum.REPLY_NUMBER_NOT_FOUND, ex);
  }

  /**
   * 예외 처리 메서드: MethodArgumentTypeMismatchException 처리
   *
   * 이 메서드는 {@link MethodArgumentTypeMismatchException} 예외가 발생했을 때 실행됩니다.
   * 메서드의 인자 타입 불일치 예외를 처리합니다.
   *
   * @param ex MethodArgumentTypeMismatchException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ModelAndView handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException ex) {
    log.info("Is Running HandleMethodArgumentTypeMismatchException");
    return generateErrorModelAndView(AdviceErrorEnum.METHOD_ARUGMNET_TYPE_MISMATCH, ex);
  }

  /**
   * 예외 처리 메서드: BindException 처리
   *
   * 이 메서드는 {@link BindException} 예외가 발생했을 때 실행됩니다. 데이터 바인딩 실패 예외를 처리합니다.
   *
   * @param ex BindException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(BindException.class)
  public ModelAndView handleBindException(BindException ex) {
    log.info("Is Running HandleBindException");
    return generateErrorModelAndView(AdviceErrorEnum.BIND_EXCEPTION, ex);
  }

  /**
   * 예외 처리 메서드: DataIntegrityViolationException 처리
   *
   * 이 메서드는 {@link DataIntegrityViolationException} 예외가 발생했을 때 실행됩니다. 데이터 무결성 위반
   * 예외를 처리합니다.
   *
   * @param ex DataIntegrityViolationException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ModelAndView handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    log.info("Is Running HandleDataIntegrityViolationException");
    return generateErrorModelAndView(AdviceErrorEnum.DATA_INTEGRITY_VIOLATION, ex);
  }

  /**
   * 예외 처리 메서드: NoSuchElementException 처리
   *
   * 이 메서드는 {@link NoSuchElementException} 예외가 발생했을 때 실행됩니다. 요소를 찾을 수 없는 예외를
   * 처리합니다.
   *
   * @param ex NoSuchElementException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(NoSuchElementException.class)
  public ModelAndView handleNoSuchElementException(NoSuchElementException ex) {
    log.info("Is Running HandleNoSuchElementException");
    return generateErrorModelAndView(AdviceErrorEnum.NO_SUCH_ELEMENT, ex);
  }

  /**
   * 예외 처리 메서드: NullPointerException 처리
   *
   * 이 메서드는 {@link NullPointerException} 예외가 발생했을 때 실행됩니다. null 참조 예외를 처리합니다.
   *
   * @param ex NullPointerException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(NullPointerException.class)
  public ModelAndView handleNullPointerException(NullPointerException ex) {
    log.info("Is Running HandleNullPointerException");
    return generateErrorModelAndView(AdviceErrorEnum.NULL_POINTER, ex);
  }

  /**
   * 예외 처리 메서드: IllegalArgumentException 처리
   *
   * 이 메서드는 {@link IllegalArgumentException} 예외가 발생했을 때 실행됩니다. 잘못된 인자 예외를 처리합니다.
   *
   * @param ex IllegalArgumentException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ModelAndView handleIllegalArgumentException(IllegalArgumentException ex) {
    log.info("Is Running HandleIllegalArgumentException");
    return generateErrorModelAndView(AdviceErrorEnum.ILLEGAL_ARGUMENT, ex);
  }

  /**
   * 예외 처리 메서드: ArrayIndexOutOfBoundsException 처리
   *
   * 이 메서드는 {@link ArrayIndexOutOfBoundsException} 예외가 발생했을 때 실행됩니다. 배열 인덱스 범위 예외를
   * 처리합니다.
   *
   * @param ex ArrayIndexOutOfBoundsException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
  public ModelAndView handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException ex) {
    log.info("Is Running HandleArrayIndexOutOfBoundsException");
    return generateErrorModelAndView(AdviceErrorEnum.ARRAY_INDEX_OUT_OF_BOUNDS, ex);
  }

  /**
   * 예외 처리 메서드: NumberFormatException 처리
   *
   * 이 메서드는 {@link NumberFormatException} 예외가 발생했을 때 실행됩니다. 숫자 형식 예외를 처리합니다.
   *
   * @param ex NumberFormatException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(NumberFormatException.class)
  public ModelAndView handleNumberFormatException(NumberFormatException ex) {
    log.info("Is Running HandleNumberFormatException");
    return generateErrorModelAndView(AdviceErrorEnum.NUMBER_FORMAT, ex);
  }

  /**
   * 예외 처리 메서드: HttpMessageNotReadableException 처리
   *
   * 이 메서드는 {@link HttpMessageNotReadableException} 예외가 발생했을 때 실행됩니다. HTTP 메시지 읽기
   * 실패 예외를 처리합니다.
   *
   * @param ex HttpMessageNotReadableException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ModelAndView handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.info("Is Running HandleHttpMessageNotReadableException");
    return generateErrorModelAndView(AdviceErrorEnum.HTTP_MESSAGE_NOT_READABLE, ex);
  }

  /**
   * 예외 처리 메서드: AuthorizationException 처리
   *
   * 이 메서드는 {@link AuthorizationException} 예외가 발생했을 때 실행됩니다. 권한 부여 예외를 처리합니다.
   *
   * @param ex AuthorizationException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(AuthorizationException.class)
  public ModelAndView handleAccessDeniedException(AuthorizationException ex) {
    log.info("Is Running HandleAccessDeniedException");
    return generateErrorModelAndView(AdviceErrorEnum.ACCESS_DENIED, ex);
  }

  /**
   * 예외 처리 메서드: VerifyEmailException 처리
   *
   * 이 메서드는 {@link VerifyEmailException} 예외가 발생했을 때 실행됩니다. 이메일 인증 예외를 처리합니다.
   *
   * @param ex VerifyEmailException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(VerifyEmailException.class)
  public ModelAndView handleVerifyEmailException(VerifyEmailException ex) {
    log.info("Is Running HandleVerifyEmailException");
    return generateErrorModelAndView(AdviceErrorEnum.VERIFY_EMAIL, ex);
  }

  /**
   * 예외 처리 메서드: VerifyEmailException 처리
   *
   * 이 메서드는 {@link InternalAuthenticationServiceException} 예외가 발생했을 때 실행됩니다. 시큐리티
   * 인증 예외를 처리합니다.
   *
   * @param ex InternalAuthenticationServiceException 예외 객체
   * @return ModelAndView 객체를 반환하여 에러 페이지를 생성합니다.
   */
  @ExceptionHandler(InternalAuthenticationServiceException.class)
  public ModelAndView handleInternalAuthenticationServiceException(InternalAuthenticationServiceException ex) {
    log.info("Is Running HandleInternalAuthenticationServiceException");
    return generateErrorModelAndView(AdviceErrorEnum.AUTHENTICATION_SERVICE_ERROR, ex);
  }
}