package board.mybatis.mvc.dto.like.noticelike;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "email Should Be Not Null")
    private String email;

    @NotBlank(message = "nno Should Be Not Blank")
    private Long nno;

    private LocalDate createDate;
}
