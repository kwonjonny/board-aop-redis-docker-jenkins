package board.mybatis.mvc.dto.notice;

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

/**
 * {@code NoticeDTO} 클래스는 공지사항 정보를 전달하는 데 사용되는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO implements Serializable{
    // tbl_notice
    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    @NotNull(message = "title Should Be Not Null")
    private String title;

    @NotNull(message = "content Should Be Not Null")
    private String content;

    @NotNull(message = "writer Should Be Not Null")
    private String writer;

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
