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

@Aspect
@Log4j2
@Component
public class CacheEvictAspect {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CustomKeyGenerator customKeyGenerator;

    @Around("execution(* *(..)) && @annotation(kwonCacheEvict)")
    public Object kwonCacheEvict(ProceedingJoinPoint pjp, KwonCacheEvict kwonCacheEvict) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        // CustomKeyGenerator를 사용하여 캐시 키 생성
        String cacheKey = (String) customKeyGenerator.generate(pjp.getTarget(), method, pjp.getArgs());

        // Redis에서 해당 키의 데이터 삭제
        redisTemplate.delete(cacheKey);
        log.info("Data removed from Redis with key: {}", cacheKey);

        return pjp.proceed();  // 메서드 원래 로직 실행
    }
}
