package board.mybatis.mvc.dto.board;

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
 * 이 클래스는 게시물 정보를 전달하는 데 사용됩니다.
 * 
 * {@code BoardCreateDTO} 클래스는 게시판 생성을 위한 DTO 클래스입니다.
 * 게시물 번호, 작성자, 내용, 제목 및 첨부 파일 목록을 가지고 있습니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateDTO implements Serializable {
    // tbl_board
    private Long bno;

    @NotBlank(message = "content Should Be Not Blank")
    private String content;

    @NotBlank(message = "writer Should Be Not Blank")
    private String writer;

    @NotBlank(message = "title Should Be Not Blank") 
    private String title;

    @Builder.Default
    private List<String> fileName = new ArrayList<>();
}