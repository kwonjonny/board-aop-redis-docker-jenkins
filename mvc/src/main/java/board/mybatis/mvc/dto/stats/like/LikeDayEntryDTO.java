package board.mybatis.mvc.dto.stats.like;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code LikeDayEntryDTO} 클래스는 좋아요 일 생성 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeDayEntryDTO implements Serializable {
    private Long bno;
    private String likeEntryDay;
    private Long likeCount;
}
