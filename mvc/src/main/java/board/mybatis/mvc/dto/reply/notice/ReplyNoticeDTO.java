package board.mybatis.mvc.dto.reply.notice;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ReplyNoticeDTO} 클래스는 공지사항 댓글 정보를 전달하는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyNoticeDTO implements Serializable {
    // tbl_reply_notice
    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    @NotBlank(message = "rno Should Be Not Blank")
    private Long rno;

    @NotBlank(message = "reply Should Be Not Blank")
    private String reply;

    @NotBlank(message = "replyer Should Be Not Blank")
    private String replyer;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    private Long isDeleted;

    @Builder.Default
    private Long gno = 0L;
}