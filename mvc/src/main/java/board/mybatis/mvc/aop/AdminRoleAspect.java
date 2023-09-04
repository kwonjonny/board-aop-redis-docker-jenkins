package board.mybatis.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.exception.AuthorizationException;

/**
 * 이 Aspect는 ADMIN 역할을 가진 사용자만 특정 기능을 수행할 수 있도록 권한 검사를 수행합니다.
 * Notice Controller 의 GET Update, Post Update, Post Create, Post Delete 에 적용 
 */
@Aspect
@Component
public class AdminRoleAspect {
    /**
     * @RoleAdmin 어노테이션이 붙은 메서드를 실행하기 전에 권한 검사를 수행합니다.
     *
     * @param joinPoint 프록시된 메서드에 대한 정보 및 메서드 실행 기능을 제공
     * @return 원래 메서드의 반환 값
     * @throws Throwable 원래 메서드에서 발생하는 예외를 포함하여 다양한 예외 발생 가능
     */
    @Around("@annotation(board.mybatis.mvc.annotation.RoleAdmin)")
    public Object checkAdminRole(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Authentication) {
                boolean isAdmin = false;
                for (GrantedAuthority authority : ((Authentication) arg).getAuthorities()) {
                    if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                        isAdmin = true;
                        break;
                    }
                }
                if (!isAdmin) {
                    throw new AuthorizationException("관리자 권한이 필요합니다.");
                }
            }
        }
        return joinPoint.proceed();
    }
}