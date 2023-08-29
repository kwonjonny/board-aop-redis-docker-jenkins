package board.mybatis.mvc.dto.like.noticelike;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Like Toggle Notice DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleNoticeDTO {
    // tbl_notice_like 
    private Long likeId;
    private String email;
    private Long nno;
    private LocalDate createDate;
}
