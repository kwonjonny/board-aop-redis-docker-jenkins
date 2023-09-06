package board.mybatis.mvc.dto.board;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 이 클래스는 게시물 번호, 제목, 내용, 작성자, 생성일, 수정일, 댓글 번호,
 * 조회 수, 댓글 수, 좋아요 수, 파일 이름을 포함합니다.
 * 
 * {@code BoardListDTO} 클래스는 게시판 목록을 표현하는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDTO implements Serializable {
    // tbl_board
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Long nno;
    private int viewCount;
    private int replyCount;
    private int likeCount;
    private String fileName;
}
