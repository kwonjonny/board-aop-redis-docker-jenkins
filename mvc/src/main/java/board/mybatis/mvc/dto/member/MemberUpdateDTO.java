package board.mybatis.mvc.dto.member;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Member Update DTO Class
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateDTO {
    // tbl_member
    @NotNull(message = "email Should Be Not Null")
    private String email;

    @NotNull(message = "memberName Should Be Not Null")
    private String memberName;

    @NotNull(message = "memberPw Should Be Not Null")
    private String memberPw;

    @NotNull(message = "memberPhone Should Be Not Null")
    private String memberPhone;

    @NotBlank(message = "createDate Should Be Not Blank")
    private LocalDate createDate;
}
