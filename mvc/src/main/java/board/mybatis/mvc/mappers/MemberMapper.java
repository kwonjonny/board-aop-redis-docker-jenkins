package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.util.page.PageRequestDTO;

/**
 * {@code MemberMapper}는 회원 정보에 접근하는 매퍼 인터페이스입니다.
 */
@Mapper
public interface MemberMapper {
    /**
     * 시큐리티 회원의 자격 조건까지 확인하여 회원 정보를 조회합니다.
     *
     * @param email 회원 이메일
     * @return 회원 정보 및 자격 조건을 포함한 DTO
     */
    MemberConvertDTO selectOne(String email);

    /**
     * 회원의 자격 조건을 생성합니다.
     *
     * @param email    회원 이메일
     * @param rolename 자격 조건 이름
     * @return 생성된 자격 조건의 ID
     */
    Long createJoinMemberRole(@Param("email") String email, @Param("rolename") String rolename);

    /**
     * 회원을 가입시킵니다.
     *
     * @param memberCreateDTO 생성할 회원 정보를 담은 DTO
     * @return 생성된 회원의 ID
     */
    Long joinMember(MemberCreateDTO memberCreateDTO);

    /**
     * 특정 회원의 정보를 조회합니다.
     *
     * @param email 조회할 회원의 이메일
     * @return 조회된 회원 정보를 포함한 DTO
     */
    MemberConvertDTO readMember(String email);

    /**
     * 회원 정보를 업데이트합니다.
     *
     * @param memberUpdateDTO 업데이트할 회원 정보를 담은 DTO
     * @return 업데이트된 회원의 ID
     */
    Long updateMember(MemberUpdateDTO memberUpdateDTO);

    /**
     * 회원을 탈퇴시킵니다.
     *
     * @param email 탈퇴할 회원의 이메일
     * @return 탈퇴된 회원의 ID
     */
    Long deleteMember(String email);

    /**
     * 회원의 자격 조건을 삭제합니다.
     *
     * @param email 삭제할 회원의 이메일
     * @return 삭제된 자격 조건의 ID
     */
    Long deleteMemberRole(String email);

    /**
     * 회원 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 요청 정보를 담은 DTO
     * @return 회원 목록 정보를 포함한 DTO 리스트
     */
    List<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);

    /**
     * 회원의 총 개수를 조회합니다.
     *
     * @param pageRequestDTO 페이지 요청 정보를 담은 DTO
     * @return 회원의 총 개수
     */
    int total(PageRequestDTO pageRequestDTO);

    /**
     * 회원 이메일의 존재 여부를 검증합니다.
     *
     * @param email 검증할 회원의 이메일
     * @return 검증 결과를 나타내는 값 (존재하면 해당 회원의 ID, 없으면 null)
     */
    Long findMemberEmail(String email);

    /**
     * 회원가입 이메일 인증을 완료합니다.
     * 
     * @param email 인증할 회원의 이메일
     */
    void verifyEmail(String email);
}