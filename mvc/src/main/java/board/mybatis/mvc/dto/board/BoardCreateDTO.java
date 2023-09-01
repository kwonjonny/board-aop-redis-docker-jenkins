package board.mybatis.mvc.dto.board;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Board Create DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateDTO {
    // tbl_board
    private Long bno;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
