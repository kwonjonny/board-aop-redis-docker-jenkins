package board.mybatis.mvc.dto.stats.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원 일 가입 통계 DTO 
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDayEntryDTO {
    private Long memberDayEntry; // 회원 일 가입 데이터
    private String createDate; // 회원가입 날짜
}
