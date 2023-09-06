package board.mybatis.mvc.dto.stats.reply;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ReplyMonthEntryDTO} 클래스는 댓글 월 생성 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyMonthEntryDTO implements Serializable {
    private Long bno;
    private Long replyCount;
    private String replyMonthEntry;
}
