package board.mybatis.mvc.dto.stats.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원 월 가입 통계 DTO 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberMonthEntryDTO {
    private Long memberMonthEntry; // 회원 월 가입 데이터
    private String createDate; // 회원가입 날짜
}
