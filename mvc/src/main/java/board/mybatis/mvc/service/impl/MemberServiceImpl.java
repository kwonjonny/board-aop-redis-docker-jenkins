package board.mybatis.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.mappers.MemberMapper;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import board.mybatis.mvc.util.validator.MemberValidator;
import lombok.extern.log4j.Log4j2;

// Member ServiceImpl Class
@Log4j2
@Service
public class MemberServiceImpl implements MemberService {

    // 의존성 주입
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /*
     * Autowired 명시적 표시
     */
    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        log.info("Inject MemberMapper");
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * 회원 가입 서비스
     * Spring Security 의 PasswordEncoder
     * 패스워드 인코딩
     */
    @Override
    @Transactional
    public Long joinMember(MemberCreateDTO memberCreateDTO) {
        log.info("Is Running JoinMember ServiceImpl");
        if (memberCreateDTO.getEmail() == null || memberCreateDTO.getMemberName() == null
                || memberCreateDTO.getMemberPhone() == null || memberCreateDTO.getMemberPw() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호, 패스워드는 필수사항입니다.");
        }
        duplicateMemberEmail(memberCreateDTO.getEmail()); // Member Duplicate Check
        MemberValidator.validateEmail(memberCreateDTO.getEmail()); // Member Email Validation Check
        MemberValidator.validatePassword(memberCreateDTO.getMemberPw()); // Member Password Validation Check
        MemberValidator.validatePhoneNumber(memberCreateDTO.getMemberPhone()); // Member Phone Number Validation Check
        memberCreateDTO.setMemberPw(passwordEncoder.encode(memberCreateDTO.getMemberPw()));
        String memberRole = "USER";
        memberMapper.createJoinMemberRole(memberCreateDTO.getEmail(), memberRole);
        return memberMapper.joinMember(memberCreateDTO);
    }

    /*
     * 회원 조회 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public MemberConvertDTO readMember(String email) {
        log.info("Is Running Read Member ServiceImpl");
        notFoundMember(email); // Member Find Email Check
        MemberValidator.validateEmail(email); // Member Email Validation Check
        return memberMapper.readMember(email);
    }

    /*
     * 회원 업데이트 서비스
     * Spring Security 의 PasswordEncoder
     * 패스워드 인코딩
     */
    @Override
    @Transactional
    public Long updateMember(MemberUpdateDTO memberUpdateDTO) {
        log.info("Is Running Update Member ServiceImpl");
        if (memberUpdateDTO.getEmail() == null || memberUpdateDTO.getMemberName() == null
                || memberUpdateDTO.getMemberPhone() == null || memberUpdateDTO.getMemberPw() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호, 패스워드는 필수사항입니다.");
        }
        notFoundMember(memberUpdateDTO.getEmail()); // Member Find Email Check
        MemberValidator.validateEmail(memberUpdateDTO.getEmail()); // Member Email Validation Check
        MemberValidator.validatePassword(memberUpdateDTO.getMemberPw()); // Member Pasword Validation Check
        MemberValidator.validatePhoneNumber(memberUpdateDTO.getMemberPhone()); // Member Phone Number Validation Check
        memberUpdateDTO.setMemberPw(passwordEncoder.encode(memberUpdateDTO.getMemberPw()));
        return memberMapper.updateMember(memberUpdateDTO);
    }

    /*
     * 회원 탈퇴 서비스
     */
    @Override
    @Transactional
    public Long deleteMember(String email) {
        log.info("Is Running Delete Member ServiceImpl");
        notFoundMember(email); // Member Find Email Check
        MemberValidator.validateEmail(email); // Member Email Validation Check
        memberMapper.deleteMemberRole(email);
        return memberMapper.deleteMember(email);
    }

    /*
     * 회원 리스트 서비스
     * 트랜잭션 readOnly
     */
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

    /*
     * 회원 이메일 중복 체크
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void duplicateMemberEmail(String email) {
        log.info("Is Running Duplicate Member Email ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 1) {
            throw new MemberEmailDuplicateException("이미 회원가입이 완료된 이메일입니다");
        }
    }

    /*
     * 회원 가입 상태 조회 검증
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void notFoundMember(String email) {
        log.info("Is Running Not Found Member Email ServiceImpl");
        if (memberMapper.findMemberEmail(email) == 0 || memberMapper.findMemberEmail(email) == null) {
            throw new MemberNotFoundException("해당하는 이메일의 회원이 없습니다.");
        }
    }

    /*
     * 이메일 중복 체크 서비스
     */
    @Override
    @Transactional(readOnly = true)
    public Long duplicateEmail(String email) {
      log.info("Is Running Duplicate Email ServiceImpl");
      return memberMapper.findMemberEmail(email);
    }
}