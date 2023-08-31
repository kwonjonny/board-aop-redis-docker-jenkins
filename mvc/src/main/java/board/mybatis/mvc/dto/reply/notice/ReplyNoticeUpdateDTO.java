package board.mybatis.mvc.dto.reply.notice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Reply Notice Update DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeUpdateDTO {
    // tbl_reply_notice
    private Long rno;
    private Long nno;
    private String reply;
    private String replyer;

    @Builder.Default
    private Long gno = 0L;

    private LocalDate createDate;
    private LocalDate updateDate;
    private Long isDeleted;
}
