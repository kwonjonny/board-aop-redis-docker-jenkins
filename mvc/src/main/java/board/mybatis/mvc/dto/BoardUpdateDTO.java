package board.mybatis.mvc.dto;

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

// Board Update DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDTO {
    // tbl_board

    @NotBlank(message = "bno Should Be Not Null")
    private Long bno;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotBlank(message = "updateDate Should Be Not Null")
    private LocalDate updateDate;

    @Builder.Default
    private List<String> fileNames = new ArrayList<>();
}
