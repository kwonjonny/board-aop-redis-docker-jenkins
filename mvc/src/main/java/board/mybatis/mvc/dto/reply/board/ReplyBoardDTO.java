package board.mybatis.mvc.dto.reply.board;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Reply Board DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReplyBoardDTO {
    // tbl_reply_board 
    private Long rno;
    private Long bno;
    private String reply;
    private String replyer;

    @Builder.Default
    private Long gno = 0L;

    private LocalDate createDate;
    private LocalDate updateDate;
    private Long isDeleted;
}
