package board.mybatis.mvc.dto.notice;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Notice Create DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateDTO {
    // tbl_notice
    private Long nno;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
