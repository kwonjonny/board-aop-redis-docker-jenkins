package board.mybatis.mvc.aop.rolevalidation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.exception.AuthorizationException;
import lombok.extern.log4j.Log4j2;

/**
 * 이 Aspect는 애플리케이션의 권한 관련 문제를 처리합니다.
 * AOP (관점 지향 프로그래밍)를 활용하여 @CheckUserMatch 어노테이션이 붙은 메서드 호출을 가로챕니다.
 * MemberController 의 GET Update, Post Update, Post Delete 에 적용
 */
@Aspect
@Log4j2
@Component
public class AuthorizationAspect {

    /**
     * @CheckUserMatch 어노테이션이 붙은 메서드를 실행하기 전에 권한 검사를 수행합니다.
     * 
     * @param joinPoint 프록시된 메서드에 대한 정보 및 메서드 실행 기능을 제공
     * @return 원래 메서드의 반환 값
     * @throws Throwable 원래 메서드에서 발생하는 예외를 포함하여 다양한 예외 발생 가능
     */
    @Around("@annotation(board.mybatis.mvc.annotation.role.CheckMemberMatch)")
    public Object handleCheckMemberMatch(final ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint에서 메서드 인수를 가져옵니다.
        Object[] args = joinPoint.getArgs();
        // 인수 목록을 순회하면서 Authentication 타입의 인수를 찾습니다.
        for (Object arg : args) {
            if (arg instanceof Authentication) {
                UserDetails userDetails = (UserDetails) ((Authentication) arg).getPrincipal();
                // 인수 목록을 순회하면서 String 타입의 인수 (이메일)를 찾습니다.
                for (Object otherArg : args) {
                    if (otherArg instanceof String) {
                        log.info("Current User Check: " + userDetails.getUsername());
                        String email = (String) otherArg;
                        // 현재 인증된 사용자의 이름(이메일)과 메서드의 이메일 인수를 비교합니다.
                        if (!userDetails.getUsername().equals(email)) {
                            throw new AuthorizationException("현재 접속해있는 유저의 접속 정보와 일치하지 않습니다.");
                        }
                    }
                }
            }
        }
        // 원래의 메서드를 계속 실행합니다.
        return joinPoint.proceed();
    }
}