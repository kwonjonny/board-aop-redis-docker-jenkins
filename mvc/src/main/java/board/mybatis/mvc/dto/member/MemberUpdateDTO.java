package board.mybatis.mvc.dto.member;

import java.io.Serializable;
import java.time.LocalDate;

import board.mybatis.mvc.redis.provider.CacheKeyProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberUpdateDTO} 클래스는 회원 정보 업데이트를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO implements Serializable {
    // tbl_member
    @NotBlank(message = "email Should Be Not NotBlank")
    private String email;

    @NotBlank(message = "memberName Should Be Not NotBlank")
    private String memberName;

    @NotBlank(message = "memberPw Should Be Not NotBlank")
    private String memberPw;

    @NotNull(message = "memberPhone Should Be Not NotBlank")
    private String memberPhone;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;
}
