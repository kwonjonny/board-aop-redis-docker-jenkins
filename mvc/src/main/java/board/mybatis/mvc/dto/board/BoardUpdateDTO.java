package board.mybatis.mvc.dto.board;

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
 * {@code BoardUpdateDTO} 클래스는 게시판 업데이트를 위한 DTO 클래스입니다.
 *
 * 이 클래스는 게시물 번호, 내용, 작성자, 제목, 업데이트 일자, 첨부 파일 목록을 가지고 있습니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDTO implements Serializable {
    // tbl_board
    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

    @NotBlank(message = "content Should Be Not Blank")
    private String content;

    @NotBlank(message = "writer Should Be Not Blank")
    private String writer;

    @NotBlank(message = "title Should Be Not Blank")
    private String title;

    @NotBlank(message = "updateDate Should Be Not Blank")
    private LocalDate updateDate;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}
