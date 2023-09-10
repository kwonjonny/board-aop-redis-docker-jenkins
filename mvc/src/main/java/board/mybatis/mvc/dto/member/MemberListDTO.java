package board.mybatis.mvc.dto.member;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberListDTO} 클래스는 회원 목록을 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberListDTO implements Serializable {
   // tbl_member
   private String email;
   private String memberName;
   private String memberPw;
   private String memberPhone;
   private LocalDate createDate;
}
