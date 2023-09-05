package board.mybatis.mvc.redis.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 짧게 만들어진 URL에 대한 응답용 데이터 전송 객체(DTO) 표현.
 * 이 DTO는 Redis에서 해시로 저장되도록 설계되었으며,
 * 기본적으로 생존 시간(TTL)은 60초입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "shortUrl", timeToLive = 60)
public class ShortUrlResponseDTO implements Serializable {

    /** 직렬화에 필요한 버전 ID */
    private static final Long serialVersionUID = -214490344996507077L;

    /** 원래의 URL을 나타내는 필드. Redis에서 이 값을 기반으로 고유 ID로 사용됩니다. */
    @Id
    private String orgUrl;

    /** 짧게 만들어진 URL을 나타내는 필드 */
    private String shortUrl;

    private String email;
}
