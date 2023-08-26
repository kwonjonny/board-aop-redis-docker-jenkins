package board.mybatis.mvc.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Board DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    // tbl_board 
    private Long bno;
    private String writer;
    private String content;
    private String title;
    private LocalDate createDate;
    private LocalDate updateDate;
}
