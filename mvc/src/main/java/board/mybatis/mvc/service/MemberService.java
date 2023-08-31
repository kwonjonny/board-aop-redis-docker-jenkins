package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Member Service Interface
public interface MemberService {
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
     * 회원 리스트 
     */
    PageResponseDTO<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);
}
