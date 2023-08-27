package board.mybatis.mvc.dto.notice;

import java.util.ArrayList;
import java.util.List;

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
    private String writer;
    private String title;
    private String content;

    @Builder.Default
    private List<String> fileNames = new ArrayList<>();
}
