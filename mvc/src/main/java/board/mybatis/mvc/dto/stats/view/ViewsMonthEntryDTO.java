package board.mybatis.mvc.dto.stats.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 조회수 월 생성 통계 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ViewsMonthEntryDTO {
    private Long bno;
    private Long viewCount;
    private String viewMonthEntry;
}
