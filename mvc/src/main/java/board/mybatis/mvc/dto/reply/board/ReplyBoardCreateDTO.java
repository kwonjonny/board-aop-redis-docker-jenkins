package board.mybatis.mvc.dto.reply.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Reply Board Create DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyBoardCreateDTO {
    // tbl_reply_board
    private Long rno;
    private Long bno;
    private String reply;
    private String replyer;

    @Builder.Default
    private Long gno = 0L;
}
