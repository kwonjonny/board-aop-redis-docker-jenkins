package board.mybatis.mvc.dto.reply.board;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code ReplyBoardCreateDTO} 클래스는 게시판 댓글 생성을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyBoardCreateDTO implements Serializable {
    // tbl_reply_board
    private Long rno;

    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

    @NotBlank(message = "reply Should Be Not Blank")    
    private String reply;

    @NotBlank(message = "replyer Shold Be Not Blank")
    private String replyer;

    @Builder.Default
    private Long gno = 0L;
}
