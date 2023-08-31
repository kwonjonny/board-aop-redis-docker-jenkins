package board.mybatis.mvc.dto.reply.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Reply Notice Create DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeCreateDTO {
    // tbl_reply_notice
    private Long nno;
    private Long rno;
    private String reply;
    private String replyer;

    @Builder.Default
    private Long gno = 0L;
}
