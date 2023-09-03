package board.mybatis.mvc.dto.stats.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 게시판 일 생성 통계 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDayEntryDTO {
    private Long boardDayEntry;
    private String createDate;
}
