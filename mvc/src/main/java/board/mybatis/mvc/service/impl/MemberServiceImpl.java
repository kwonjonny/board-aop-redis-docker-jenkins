package board.mybatis.mvc.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.mappers.MemberMapper;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Member ServiceImpl Class
@Log4j2
@Service
public class MemberServiceImpl implements MemberService {

    // 의존성 주입
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    // Autowired 명시적 표시
    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        log.info("Inject MemberMapper");
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Join Member ServiceImpl
    @Override
    @Transactional
    public Long joinMember(MemberCreateDTO memberCreateDTO) {
        log.info("Is Running JoinMember ServiceImpl");
        duplicateMemberEmail(memberCreateDTO.getEmail()); // Member Duplicate Check
        validationUserEmail(memberCreateDTO.getEmail()); // Member Email Validation Check
        String password = memberCreateDTO.getMemberPw();
        memberCreateDTO.setMemberPw(passwordEncoder.encode(password));
        String memberRole = "USER";
        memberMapper.createJoinMemberRole(memberCreateDTO.getEmail(), memberRole);
        return memberMapper.joinMember(memberCreateDTO);
    }

    // Read Member ServiceImpl
    @Override
    @Transactional(readOnly = true)
    public MemberConvertDTO readMember(String email) {
        log.info("Is Running Read Member ServiceImpl");
        notFoundMember(email); // Member Find Email Check
        validationUserEmail(email); // Member Email Validation Check
        return memberMapper.readMember(email);
    }

    // Update Member ServiceImpl
    @Override
    @Transactional
    public Long updateMember(MemberUpdateDTO memberUpdateDTO) {
        log.info("Is Running Update Member ServiceImpl");
        notFoundMember(memberUpdateDTO.getEmail()); // Member Find Email Check
        validationUserEmail(memberUpdateDTO.getEmail()); // Member Email Validation Check
        String passwrod = memberUpdateDTO.getMemberPw();
        memberUpdateDTO.setMemberPw(passwordEncoder.encode(passwrod));
        return memberMapper.updateMember(memberUpdateDTO);
    }

    // Delete Member ServiceImpl
    @Override
    @Transactional
    public Long deleteMember(String email) {
        log.info("Is Running Delete Member ServiceImpl");
        notFoundMember(email); // Member Find Email Check
        validationUserEmail(email); // Member Email Validation Check
        memberMapper.deleteMemberRole(email);
        return memberMapper.deleteMember(email);
    }

    // List Member ServceImpl
    @Override
    @Transactional
    public PageResponseDTO<MemberListDTO> listMember(PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Member ServiceImpl");
        if (pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 회원 리스트가 없습니다.");
        }
        List<MemberListDTO> list = memberMapper.listMember(pageRequestDTO);
        int total = memberMapper.total(pageRequestDTO);
        return PageResponseDTO.<MemberListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    // Find Member Email ServiceImpl
    @Transactional(readOnly = true)
    private void duplicateMemberEmail(String email) {
        log.info("Is Running Find Member Email ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 1) {
            throw new MemberEmailDuplicateException("이미 회원가입이 완료된 이메일입니다");
        }
    }

    // Not Found Member ServiceImpl
    @Transactional(readOnly = true)
    private void notFoundMember(String email) {
        log.info("Is Running Not Found Member Email ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 0 || memberMapper.findMemberEmail(email) == null) {
            throw new MemberNotFoundException("해당하는 이메일의 회원이 없습니다.");
        }
    }

    // Validation Use Email ServiceImpl
    @Transactional(readOnly = true)
    private void validationUserEmail(String email) {
        log.info("Is Running Validation User Email");
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지않습니다.");
        }
    }
}
