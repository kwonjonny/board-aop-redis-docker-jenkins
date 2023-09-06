package board.mybatis.mvc.dto.stats.view;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ViewsDayEntryDTO} 클래스는 조회수 일 생성 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ViewsDayEntryDTO implements Serializable {
    private Long bno;
    private Long viewCount;
    private String viewDayEntry;
}
