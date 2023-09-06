package board.mybatis.mvc.dto.stats.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberDayEntryDTO} 클래스는 회원 일 가입 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDayEntryDTO implements Serializable {
    private Long memberDayEntry; 
    private String createDate; 
}
