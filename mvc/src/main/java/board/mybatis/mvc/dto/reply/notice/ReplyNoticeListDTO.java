package board.mybatis.mvc.dto.reply.notice;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ReplyNoticeListDTO} 클래스는 공지사항 댓글 목록을 표현하는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeListDTO implements Serializable {
     // tbl_reply_notice
    private Long nno;
    private Long rno;
    private String reply;
    private String replyer;
    private String step;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Long isDeleted;

    @Builder.Default
    private Long gno = 0L;
}
