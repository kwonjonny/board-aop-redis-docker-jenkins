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
 * 이 클래스는 게시판 데이터 전송 객체(DTO)를 정의합니다.
 *
 * {@code BoardDTO} 클래스는 게시물 정보를 전달하는 데 사용됩니다.
 * 이 클래스는 게시물 번호, 작성자, 내용 등의 속성을 가지고 있습니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO implements Serializable {
    // tbl_board 
    @NotBlank(message = "bno Should Be Not Blank")
    private Long bno;

    @NotBlank(message = "writer Should Be Not Blank")
    private String writer;

    @NotBlank(message = "content Should Be Not Blank")
    private String content;

    @NotBlank(message = "title Should Be Not Blank")
    private String title;

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
