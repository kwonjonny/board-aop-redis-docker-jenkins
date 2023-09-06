package board.mybatis.mvc.aop.redis;

import java.lang.reflect.Method;
import java.time.Duration;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.annotation.redis.KwonCacheable;
import board.mybatis.mvc.redis.keygenerator.CustomKeyGenerator;
import lombok.extern.log4j.Log4j2;

/**
 * {@code CacheableAspect} 클래스는 Redis 캐시에 관한 AOP 로직을 제공합니다.
 * {@link KwonCacheable} 어노테이션이 붙은 메서드를 실행할 때,
 * 해당 메서드의 결과를 Redis 캐시에 저장하고, 캐시에 값이 이미 존재하면 그 값을 반환합니다.
 * 
 * <p>
 * 이 로직은 주로 데이터베이스 조회 결과를 캐시하여
 * 데이터베이스의 부하를 줄이고 응답 시간을 개선하기 위해 사용됩니다.
 * </p>
 *
 */
@Aspect
@Log4j2
@Component
public class CacheableAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    /**
     * {@link KwonCacheable} 어노테이션이 붙은 메서드에 대한 캐싱 로직을 수행합니다.
     * 해당 메서드의 실행 결과를 Redis 캐시에 저장하며,
     * 캐시에 값이 이미 존재하면 해당 값을 반환합니다.
     * 
     * @param pjp           ProceedingJoinPoint
     * @param kwonCacheable KwonCacheable 어노테이션
     * @return 메서드의 실행 결과 또는 캐시된 값
     * @throws Throwable 예외 발생시
     */
    @Around("execution(* *(..)) && @annotation(kwonCacheable)")
    public Object kwonCacheable(ProceedingJoinPoint pjp, KwonCacheable kwonCacheable) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        // CustomKeyGenerator를 사용하여 캐시 키 생성
        String cacheKey = (String) customKeyGenerator.generate(pjp.getTarget(), method, pjp.getArgs());
        String cacheName = kwonCacheable.value();

        // Redis에서 데이터 조회
        Object cachedValue = redisTemplate.opsForValue().get(cacheKey);
        if (cachedValue != null) {
            return cachedValue;
        }
        // DB에서 데이터 조회 및 Redis에 저장
        Object result = pjp.proceed();
        redisTemplate.opsForValue().set(cacheKey, result, Duration.ofMinutes(30));
        log.info("Data saved in Redis with key: {} for a duration of 30 minutes", cacheKey );
        return result;
    }
}