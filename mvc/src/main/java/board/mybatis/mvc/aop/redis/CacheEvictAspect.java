package board.mybatis.mvc.aop.redis;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.annotation.redis.KwonCacheEvict;
import board.mybatis.mvc.redis.keygenerator.CustomKeyGenerator;
import lombok.extern.log4j.Log4j2;

/**
 * {@link KwonCacheEvict} 어노테이션이 붙은 메서드를 대상으로 하여
 * 해당 메서드가 실행될 때 Redis 캐시를 제거하는 AOP Aspect 클래스입니다.
 */
@Aspect
@Log4j2
@Component
public class CacheEvictAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    /**
     * {@link KwonCacheEvict} 어노테이션과 함께 실행되는 Around 어드바이스입니다.
     * 해당 어드바이스는 Redis에서 캐시 데이터를 제거하고 원래의 메서드 로직을 실행합니다.
     * 
     * @param pjp            메서드의 실행 정보를 담고 있는 객체
     * @param kwonCacheEvict 캐시 제거 어노테이션
     * @return 메서드의 원래 결과
     * @throws Throwable 예외 발생 시
     */
    @Around("execution(* *(..)) && @annotation(kwonCacheEvict)")
    public Object kwonCacheEvict(final ProceedingJoinPoint pjp, final KwonCacheEvict kwonCacheEvict) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        // CustomKeyGenerator를 사용하여 캐시 키 생성
        String cacheKey = (String) customKeyGenerator.generate(pjp.getTarget(), method, pjp.getArgs());

        // Redis에서 해당 키의 데이터 삭제
        redisTemplate.delete(cacheKey);
        log.info("Data removed from Redis with key: {}", cacheKey);

        return pjp.proceed();
    }
}
