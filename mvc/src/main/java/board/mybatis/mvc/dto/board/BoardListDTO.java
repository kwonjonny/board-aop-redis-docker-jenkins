package board.mybatis.mvc.dto.board;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Board List DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDTO {
    // tbl_board
    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    private int viewCount;

    private String fileName;
}
