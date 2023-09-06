package board.mybatis.mvc.dto.stats.board;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code BoardDayEntryDTO} 클래스는 게시판 일 생성 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDayEntryDTO implements Serializable {
    private Long boardDayEntry;
    private String createDate;
}
