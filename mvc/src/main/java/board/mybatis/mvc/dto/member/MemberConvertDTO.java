package board.mybatis.mvc.dto.member;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberConvertDTO} 클래스는 회원 정보 변환을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberConvertDTO implements Serializable {
    // tbl_member 
    @NotBlank(message = "email Should Be Not Blank")
    private String email;

    @NotBlank(message = "memberName Should Be Not Blank")
    private String memberName;

    @NotBlank(message = "memberPw Should Be Not Blank")
    private String memberPw;

    @NotBlank(message = "memberPhone Should Be Not Blank")
    private String memberPhone;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;

    @NotBlank(message = "rolename Should Be Not Blank")
    private List<String> rolenames;
}