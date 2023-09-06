package board.mybatis.mvc.dto.like.boardlike;

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
 * {@code LikeToggleBoardDTO} 클래스는 게시물 좋아요 토글을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleBoardDTO implements Serializable {
    // tbl_board_like 
    private Long likeId;

    @NotBlank(message = "email Should Be Not Blank")
    private String email;

    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;
    
    private LocalDate createDate;
}
