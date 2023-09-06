package board.mybatis.mvc.dto.reply.notice;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ReplyNoticeCreateDTO} 클래스는 공지사항 댓글 생성을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeCreateDTO implements Serializable {
    // tbl_reply_notice
    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    @NotBlank(message = "rno Should Be Not Blank")
    private Long rno;

    @NotBlank(message = "reply Should Be Not Blank")
    private String reply;

    @NotBlank(message = "replyer Should Be Not Blank")
    private String replyer;

    @Builder.Default
    private Long gno = 0L;
}
