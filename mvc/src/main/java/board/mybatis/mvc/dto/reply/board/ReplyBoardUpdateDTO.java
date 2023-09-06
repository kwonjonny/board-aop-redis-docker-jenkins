package board.mybatis.mvc.dto.reply.board;

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
 * {@code ReplyBoardListDTO} 클래스는 게시판 댓글 수정을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyBoardUpdateDTO implements Serializable {
    // tbl_reply_board
    @NotBlank(message = "rno Should Be Not Blank")
    private Long rno;

    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

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
