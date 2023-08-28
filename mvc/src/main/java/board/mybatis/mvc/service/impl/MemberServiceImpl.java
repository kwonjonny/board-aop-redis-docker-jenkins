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
        String email = memberCreateDTO.getEmail();
        if (memberMapper.findMemberEmail(email) == 1) {
            throw new MemberEmailDuplicateException("이미 회원가입 된 이메일입니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        if (memberCreateDTO.getEmail() == null || memberCreateDTO.getMemberName() == null
                || memberCreateDTO.getMemberPhone() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호, 비밀번호는 필수사항 입니다.");
        }
        String password = memberCreateDTO.getMemberPw();
        memberCreateDTO.setMemberPw(passwordEncoder.encode(password));
        String memberRole = "USER";
        memberMapper.createJoinMemberRole(email, memberRole);
        return memberMapper.joinMember(memberCreateDTO);
    }

    // Read Member ServiceImpl
    @Override
    @Transactional(readOnly = true)
    public MemberConvertDTO readMember(String email) {
        log.info("Is Running Read Member ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 0) {
            throw new MemberNotFoundException("해당하는 이메일이 없습니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        if (email == null) {
            throw new DataNotFoundException("해당하는 이메일이 없습니다.");
        }
        return memberMapper.readMember(email);
    }

    // Update MemberServiceImpl
    @Override
    @Transactional
    public Long updateMember(MemberUpdateDTO memberUpdateDTO) {
        log.info("Is Running Update Member ServiceImpl");
        if (memberMapper.findMemberEmail(memberUpdateDTO.getEmail()) == 0) {
            throw new MemberNotFoundException("해당하는 이메일이 없습니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(memberUpdateDTO.getEmail());
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        return memberMapper.updateMember(memberUpdateDTO);
    }

    // Delete MemberServiceImpl
    @Override
    @Transactional
    public Long deleteMember(String email) {
        log.info("Is Running Delete Member ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 0) {
            throw new MemberNotFoundException("해당하는 이메일이 없습니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        memberMapper.deleteMemberRole(email);
        return memberMapper.deleteMember(email);
    }

    @Override
    @Transactional(readOnly = true)
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
}
