package board.mybatis.mvc.dto.like.boardlike;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Like Toggle Board DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleBoardDTO {
    // tbl_board_like 
    private Long likeId;

    @NotNull(message = "email Should Be Not Null")
    private String email;

    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;
    
    private LocalDate createDate;
}
