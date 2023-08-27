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
public class NoticeUpdateDTO {
    private Long nno;
    private String writer;
    private String title;
    private String content;
    private LocalDate createDate;
    private LocalDate updateDate;
}
