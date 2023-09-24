package board.mybatis.mvc.aop.rolevalidation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.exception.AuthorizationException;
import lombok.extern.log4j.Log4j2;

/**
 * 이 Aspect는 관리자 권한을 가진 사용자만 특정 기능을 수행할 수 있도록 검사합니다.
 * AOP (관점 지향 프로그래밍)를 활용하여 @RoleAdmin 어노테이션이 붙은 메서드 호출을 가로챕니다.
 * NoticeController의 GET Update, POST Update, POST Create, POST Delete에 적용되었습니다.
 */
@Aspect
@Log4j2
@Component
public class AdminRoleAspect {

    /**
     * @RoleAdmin 어노테이션이 붙은 메서드를 실행하기 전에 권한 검사를 수행합니다.
     * 
     * @param joinPoint 프록시된 메서드에 대한 정보 및 메서드 실행 기능을 제공합니다.
     * @return 원래 메서드의 반환 값을 돌려줍니다.
     * @throws Throwable 원래 메서드에서 발생하는 예외 및 권한 관련 예외를 포함하여 다양한 예외가 발생할 수 있습니다.
     */
    @Around("@annotation(board.mybatis.mvc.annotation.role.RoleAdmin)")
    public Object handleCheckAdminRole(final ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint에서 메서드 인수를 가져옵니다.
        Object[] args = joinPoint.getArgs();
        // 인수 목록을 순회하면서 Authentication 타입의 인수를 찾습니다.
        for (Object arg : args) {
            if (arg instanceof Authentication) {
                boolean isAdmin = false;
                // 사용자의 권한 목록을 순회하면서 "ROLE_ADMIN" 역할을 가진지 검사합니다.
                for (GrantedAuthority authority : ((Authentication) arg).getAuthorities()) {
                    if ("ROLE_ADMIN".equals(authority.getAuthority())) {
                        isAdmin = true;
                        log.info("authority: " + authority.getAuthority());
                        break;
                    }
                }  
                // 사용자가 "ROLE_ADMIN" 역할을 가지고 있지 않다면 예외를 발생시킵니다.
                if (!isAdmin) {
                    throw new AuthorizationException("관리자 권한이 필요합니다.");
                }
            }
        }
        // 원래의 메서드를 계속 실행합니다.
        return joinPoint.proceed();
    }
}