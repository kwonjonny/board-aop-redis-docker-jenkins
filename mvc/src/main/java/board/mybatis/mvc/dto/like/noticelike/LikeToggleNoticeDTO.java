package board.mybatis.mvc.dto.like.noticelike;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code LikeToggleNoticeDTO} 클래스는 공지사항 좋아요 토글을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LikeToggleNoticeDTO implements Serializable {
    // tbl_notice_like
    private Long likeId;

    @NotBlank(message = "email Should Be Not Blank")
    private String email;

    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    private LocalDate createDate;
}
