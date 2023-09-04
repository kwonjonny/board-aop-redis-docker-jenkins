package board.mybatis.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.exception.AuthorizationException;

@Aspect
@Component
public class AuthorizationAspect {

    @Around("@annotation(board.mybatis.mvc.annotation.CheckUserMatch)")
    public Object checkUserMatch(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Authentication) {
                UserDetails userDetails = (UserDetails) ((Authentication) arg).getPrincipal();
                for (Object otherArg : args) {
                    if (otherArg instanceof String) {
                        String email = (String) otherArg;
                        if (!userDetails.getUsername().equals(email)) {
                            throw new AuthorizationException("현재 접속해있는 유저의 접속 정보와 일치하지 않습니다.");
                        }
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}