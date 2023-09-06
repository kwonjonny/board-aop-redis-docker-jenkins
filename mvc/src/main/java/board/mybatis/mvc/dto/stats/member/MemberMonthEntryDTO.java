package board.mybatis.mvc.dto.stats.member;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * {@code MemberMonthEntryDTO} 클래스는 회원 월 가입 통계를 위한 DTO 클래스입니다.
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberMonthEntryDTO implements Serializable {
    private Long memberMonthEntry; // 회원 월 가입 데이터
    private String createDate; // 회원가입 날짜
}
