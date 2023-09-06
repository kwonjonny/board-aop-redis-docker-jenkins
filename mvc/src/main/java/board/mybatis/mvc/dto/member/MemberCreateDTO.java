package board.mybatis.mvc.dto.member;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberCreateDTO} 클래스는 회원 생성을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateDTO implements Serializable {
    // tbl_member 
    @NotBlank(message = "email Should Be Not Null")
    private String email;

    @NotBlank(message = "memberName Should Be Not Null")
    private String memberName;

    @NotBlank(message = "memberPw Should Be Not Null")
    private String memberPw;

    @NotBlank(message = "memberPhone Should Be Not Null")
    private String memberPhone;
}
