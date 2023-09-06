package board.mybatis.mvc.aop.rolevalidation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

/**
 * 이 Aspect는 애플리케이션의 권한 관련 문제를 처리합니다.
 * AOP (관점 지향 프로그래밍)를 활용하여 @CurrentMember 어노테이션이 붙은 메서드 호출을 가로챕니다.
 * 유저 정보를 서비스 레이어에서 받아 Docker의 Redis에 데이터를 인메모리 캐싱하는데 사용합니다.
 */
@Aspect
@Log4j2
@Component
public class CurrentMemberAspect {

    /**
     * @CurrentUser 어노테이션이 붙은 메서드를 실행하기 전에 현재 인증된 사용자의 정보를 가져옵니다.
     * 
     * @param joinPoint 프록시된 메서드에 대한 정보 및 메서드 실행 기능을 제공
     * @return 원래 메서드의 반환 값
     * @throws Throwable 원래 메서드에서 발생하는 예외를 포함하여 다양한 예외 발생 가능
     */
    @Around("@annotation(board.mybatis.mvc.annotation.CurrentMember)")
    public Object handleCurrentMember(ProceedingJoinPoint joinPoint) throws Throwable {
        // 현재 인증 컨텍스트에서 Authentication 객체를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Authentication 객체에서 UserDetails 객체를 가져와 현재 사용자의 정보를 얻습니다.
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 현재 사용자의 이메일(사용자명)을 가져옵니다.
        String email = userDetails.getUsername();
        log.info("Current Member: " + email);
        Object result;
        try {
            // 메서드의 원래 실행을 proceed()를 사용하여 계속하고, 현재 사용자의 이메일을 인수로 전달합니다.
            result = joinPoint.proceed(new Object[] { email });
        } catch (Exception e) {
            throw e;
        }
        // 원래 메서드의 반환 값을 반환합니다.
        return result;
    }
}
