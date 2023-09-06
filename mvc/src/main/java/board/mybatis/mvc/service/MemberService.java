package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;

/**
 * 회원 관련 서비스의 인터페이스입니다.
 */
public interface MemberService {

    /**
     * 회원 가입을 처리합니다.
     *
     * @param memberCreateDTO 가입할 회원 정보가 담긴 DTO.
     * @return 생성된 회원의 고유 번호를 반환합니다.
     */
    Long joinMember(MemberCreateDTO memberCreateDTO);

    /**
     * 회원 정보를 조회합니다.
     *
     * @param email 조회할 회원의 이메일 주소.
     * @return 조회된 회원 정보를 담은 DTO.
     */
    MemberConvertDTO readMember(String email);

    /**
     * 회원 정보를 업데이트합니다.
     *
     * @param memberUpdateDTO 업데이트할 회원 정보가 담긴 DTO.
     * @return 업데이트된 회원의 고유 번호를 반환합니다.
     */
    Long updateMember(MemberUpdateDTO memberUpdateDTO);

    /**
     * 회원 탈퇴를 처리합니다.
     *
     * @param email 탈퇴할 회원의 이메일 주소.
     * @return 탈퇴된 회원의 고유 번호를 반환합니다.
     */
    Long deleteMember(String email);

    /**
     * 회원 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보가 담긴 DTO.
     * @return 페이지에 해당하는 회원 리스트와 페이징 정보를 담은 DTO.
     */
    PageResponseDTO<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);

    /**
     * 이메일 중복을 체크합니다.
     *
     * @param email 체크할 이메일 주소.
     * @return 중복되지 않는 경우 1, 중복되는 경우 0을 반환합니다.
     */
    Long duplicateEmail(String email);
}