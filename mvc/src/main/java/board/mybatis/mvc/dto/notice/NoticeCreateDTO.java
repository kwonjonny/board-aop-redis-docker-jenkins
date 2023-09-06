package board.mybatis.mvc.dto.notice;

import java.io.Serializable;
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
 * {@code NoticeCreateDTO} 클래스는 공지사항 생성을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeCreateDTO implements Serializable{
    // tbl_notice
    private Long nno;

    @NotBlank(message = "writer Should Be Not NotBlank")
    private String writer;

    @NotBlank(message = "title Should Be Not NotBlank")
    private String title;

    @NotBlank(message = "content Should Be Not NotBlank")
    private String content;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
