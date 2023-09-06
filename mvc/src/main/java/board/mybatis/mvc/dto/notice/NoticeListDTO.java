package board.mybatis.mvc.dto.notice;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code NoticeListDTO} 클래스는 공지사항 목록을 표현하는 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListDTO implements Serializable {
    // tbl_notice
    private Long nno;
    private String title;
    private String content;
    private String writer;
    private LocalDate createDate;
    private LocalDate updateDate;
    private int viewCount;
    private int replyCount;
    private int likeCount;
    private String fileName;
}
