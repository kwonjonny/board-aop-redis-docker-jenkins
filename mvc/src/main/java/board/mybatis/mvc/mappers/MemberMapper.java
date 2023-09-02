package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

// Member Mapper Interface 
@Mapper
public interface MemberMapper {
    /*
     * 시큐리티 회원의 자격 조건 까지 확인
     */
    MemberConvertDTO selectOne(String email);

    /*
     * 회원의 자격 조건 생성
     */
    Long createJoinMemberRole(@Param("email") String email, @Param("rolename") String rolename);

    /*
     * 회원 가입
     */
    Long joinMember(MemberCreateDTO memberCreateDTO);

    /*
     * 회원 조회
     */
    MemberConvertDTO readMember(String email);

    /*
     * 회원 업데이트
     */
    Long updateMember(MemberUpdateDTO memberUpdateDTO);

    /*
     * 회원 탈퇴
     */
    Long deleteMember(String email);

    /*
     * 회원의 자격 조건 삭제
     */
    Long deleteMemberRole(String email);

    /*
     * 회원 리스트
     */
    List<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);

    /*
     * 회원 총 개수
     */
    int total(PageRequestDTO pageRequestDTO);

    /*
     * 회원 이메일 검증
     */
    Long findMemberEmail(String email);
}