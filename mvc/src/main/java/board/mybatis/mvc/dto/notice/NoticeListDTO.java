package board.mybatis.mvc.dto.notice;

import java.time.LocalDate;

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
public class NoticeListDTO {
    // tbl_notice
    private Long nno;
    private String title;
    private String content;
    private String writer;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String fileName;
}
