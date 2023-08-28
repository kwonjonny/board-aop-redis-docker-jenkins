package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Member Service Interface
public interface MemberService {
    // Create Member 
    Long joinMember(MemberCreateDTO memberCreateDTO);

    // Read Member 
    MemberConvertDTO readMember(String email);

    // Update Member 
    Long updateMember(MemberUpdateDTO memberUpdateDTO);

    // Delete Member 
    Long deleteMember(String email);

    // List Member 
    PageResponseDTO<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);
}
