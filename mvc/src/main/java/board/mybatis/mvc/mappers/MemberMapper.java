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
    // Security Member & With Roles
    MemberConvertDTO selectOne(String email);

    // Create Member Role 
    Long createJoinMemberRole(@Param("email") String email, @Param("rolename") String rolename);

    // Create Member 
    Long joinMember(MemberCreateDTO memberCreateDTO);

    // Read Member 
    MemberConvertDTO readMember(String email);

    // Update Member 
    Long updateMember(MemberUpdateDTO memberUpdateDTO);
    
    // Delete Member 
    Long deleteMember(String email);

    // Delete Member Role 
    Long deleteMemberRole(String email);

    // List Member 
    List<MemberListDTO> listMember(PageRequestDTO pageRequestDTO);

    // Total 
    int total(PageRequestDTO pageRequestDTO);

    // Find Member Email 
    Long findMemberEmail(String email);
}
