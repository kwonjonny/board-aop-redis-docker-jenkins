package board.mybatis.mvc.dto.stats.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 좋아요 일 생성 통계 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeDayEntryDTO {
    private Long bno;
    private String likeEntryDay;
    private Long likeCount;
}
