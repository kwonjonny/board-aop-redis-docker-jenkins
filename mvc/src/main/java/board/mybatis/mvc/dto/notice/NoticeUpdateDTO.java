package board.mybatis.mvc.dto.notice;

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

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUpdateDTO {
    // tbl_notice
    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
