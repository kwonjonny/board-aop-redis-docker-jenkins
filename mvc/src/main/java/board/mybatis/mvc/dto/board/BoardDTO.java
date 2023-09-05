package board.mybatis.mvc.dto.board;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Board DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO implements Serializable {
    // tbl_board
    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    private int viewCount;

    private int replyCount;

    private int likeCount;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
