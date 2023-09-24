package board.mybatis.mvc.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.extern.log4j.Log4j2;

/**
 * RedisCacheConfig는 Redis 캐싱 설정을 담당하는 스프링 구성 클래스입니다.
 * 
 * 이 구성 클래스는 Redis와의 연결 및 데이터 상호 작용을 위한 설정을 제공합니다.
 * {@link EnableCaching} 애너테이션을 사용하여 스프링의 캐싱 기능을 활성화하고 있습니다.
 * 
 * 설정 값은 application.properties 또는 application.yml 파일에서 가져옵니다.
 */
@Log4j2
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private String port;

    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;

    @Value("${spring.redis.sentinel.nodes}")
    private String sentinelNodes;

    /**
     * Redis 연결 팩토리를 생성하는 메서드입니다.
     * Redis Sentinel을 활용한 구성으로, 여러 Sentinel 노드를 구성에 추가할 수 있습니다.
     * 
     * @return LettuceConnectionFactory Redis 서버와의 연결을 위한 Lettuce 연결 팩토리 객체
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
    // Redis Sentinel 구성 설정
    RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
    .master(sentinelMaster); // Sentinel의 마스터 이름 설정
    // 각 센티널 노드를 구성에 추가
    String[] nodes = sentinelNodes.split(","); // 콤마를 기준으로 Sentinel 노드들 분리
    for (String node : nodes) {
    String[] hostAndPort = node.split(":"); // 각 노드의 호스트와 포트 정보 분리
    sentinelConfig.sentinel(new RedisNode(hostAndPort[0],
    Integer.valueOf(hostAndPort[1]))); // Sentinel 구성에 해당
    // 노드 추가
    }
    // LettuceConnectionFactory에 Sentinel 구성 적용
    LettuceConnectionFactory lettuceConnectionFactory = new
    LettuceConnectionFactory(sentinelConfig); // Sentinel
    // 팩토리 생성
    return lettuceConnectionFactory; // 연결 팩토리 반환
    }

    // @Bean
    // public RedisConnectionFactory redisConnectionFactory() {
    //     RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
    //             .master("mymaster")
    //             .sentinel("localhost", 26379); // Redis Sentinel 노드의 호스트 및 포트 설정
    //     return new LettuceConnectionFactory(sentinelConfig);
    // }

    /**
     * `template` 메서드는 Redis와의 상호 작용을 위한 핵심 구성요소인 `RedisTemplate` 객체를 생성 및 설정합니다.
     * 생성된 템플릿은 String 형태의 키와 Object 형태의 값 사이의 상호 작용을 위해 구성됩니다.
     * 키는 문자열 형식으로 직렬화되며, 값은 JDK 제공 직렬화 도구를 사용하여 직렬화됩니다.
     * 본 템플릿은 Redis 트랜잭션 지원도 활성화합니다.
     *
     * @return RedisTemplate<String, Object> Redis와의 상호 작용을 위한 설정된 템플릿 객체
     */
    @Bean
    public RedisTemplate<String, Object> template() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        template.setEnableTransactionSupport(true);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * `cacheManager` 메서드는 스프링의 캐싱 추상화에서 사용되는 `RedisCacheManager` 객체를 생성합니다.
     * 이 메서드를 통해 생성된 `RedisCacheManager`는 레디스에서 캐시 항목에 대한 기본 TTL(Time-To-Live)을
     * 30분으로 설정합니다.
     * 또한, null 값의 캐싱은 비활성화됩니다.
     * 
     * `RedisCacheManager`는 스프링의 `@Cacheable`, `@CacheEvict` 등의 캐싱 어노테이션과 함께 사용될 때,
     * 레디스를 캐시 저장소로 사용하기 위한 캐시 관리자 역할을 합니다.
     *
     * @return RedisCacheManager 레디스를 캐시 저장소로 사용하기 위한 캐시 관리자
     */
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues();
        return RedisCacheManager
                .builder(redisConnectionFactory())
                .cacheDefaults(cacheConfig)
                .build();
    }
}