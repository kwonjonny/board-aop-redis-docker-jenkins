package board.mybatis.mvc.dto.notice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Notice DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    // tbl_notice
    private Long nno;
    private String title;
    private String content;
    private String writer;
    private LocalDate createDate;
    private LocalDate updateDate;

    @Builder.Default
    private List<String> fileName= new ArrayList<>();
}
