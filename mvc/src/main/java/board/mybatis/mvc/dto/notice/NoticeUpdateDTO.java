package board.mybatis.mvc.dto.notice;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code NoticeUpdateDTO} 클래스는 공지사항 정보 업데이트를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeUpdateDTO implements Serializable {
    // tbl_notice
    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    @NotBlank(message = "writer Should Be Not NotBlank")
    private String writer;

    @NotBlank(message = "title Should Be Not NotBlank")
    private String title;

    @NotBlank(message = "content Should Be Not NotBlank")
    private String content;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
