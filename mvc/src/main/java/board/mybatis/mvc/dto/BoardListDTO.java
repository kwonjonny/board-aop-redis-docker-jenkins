package board.mybatis.mvc.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Board List DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardListDTO {
    // tbl_board
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private int viewCount;
    private LocalDate createDate;
    private LocalDate updateDate;
}
