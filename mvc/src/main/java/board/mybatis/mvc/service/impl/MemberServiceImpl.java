package board.mybatis.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.annotation.redis.KwonCacheEvict;
import board.mybatis.mvc.annotation.redis.KwonCacheable;
import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.MemberPhoneIllegalArgumentException;
import board.mybatis.mvc.exception.PasswordIllegalArgumentException;
import board.mybatis.mvc.mappers.MemberMapper;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import board.mybatis.mvc.util.validator.MemberValidator;
import lombok.extern.log4j.Log4j2;

/**
 * 회원 서비스 구현 클래스.
 * 회원 에 대한 CRUD 관련 서비스를 제공합니다.
 * Redis Cache 는 ReadMember, UpdateMember, DeleteMember 에 포함되어 있습니다.
 */
@Log4j2
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * MemberServiceImpl 생성자.
     * memberMapper 의존성 주입을 수행합니다.
     * passwordEncoder 의존성 주입을 수행합니다.
     * 
     * @param memberMapper    회원 관련 데이터 액세스 객체
     * @param passwordEncoder 패스워드 인코딩 관련 데이터 액세스 객체
     */
    @Autowired
    public MemberServiceImpl(final MemberMapper memberMapper, final PasswordEncoder passwordEncoder) {
        log.info("Inject MemberMapper");
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원 가입 서비스.
     * 회원 가입 메서드.
     * 이 메서드는 사용자의 정보를 받아서 회원 가입 절차를 수행합니다.
     * 입력된 정보에 대한 유효성 검사와 중복 체크를 포함합니다.
     * 패스워드는 Spring Security의 PasswordEncoder를 사용하여 인코딩됩니다.
     * 
     * @param memberCreateDTO 사용자로부터 입력받은 회원 가입 정보 DTO
     * @return 등록된 회원의 ID
     * @throws DataNotFoundException               필수 정보가 누락될 경우 발생하는 예외.
     * @throws MemberEmailDuplicateException       중복된 이메일을 사용할 경우 발생하는 예외.
     * @throws InvalidEmailException               이메일 형식에 맞지 않을때 발생하는 예외.
     * @throws PasswordIllegalArgumentException    비밀번호 형식에 맞지 않을때 발생하는 예외.
     * @throws MemberPhoneIllegalArgumentException 전화번호 형식에 맞지 않을때 발생하는 예외.
     */
    @Override
    @Transactional
    public Long joinMember(final MemberCreateDTO memberCreateDTO) {
        log.info("Is Running JoinMember ServiceImpl");
        if (memberCreateDTO.getEmail() == null || memberCreateDTO.getMemberName() == null
                || memberCreateDTO.getMemberPhone() == null || memberCreateDTO.getMemberPw() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호, 패스워드는 필수사항입니다.");
        }
        duplicateMemberEmail(memberCreateDTO.getEmail());
        MemberValidator.validateEmail(memberCreateDTO.getEmail());
        MemberValidator.validatePassword(memberCreateDTO.getMemberPw());
        MemberValidator.validatePhoneNumber(memberCreateDTO.getMemberPhone());
        memberCreateDTO.setMemberPw(passwordEncoder.encode(memberCreateDTO.getMemberPw()));
        String memberRole = "ADMIN";
        memberMapper.createJoinMemberRole(memberCreateDTO.getEmail(), memberRole);
        return memberMapper.joinMember(memberCreateDTO);
    }

    /**
     * 회원 조회 서비스.
     * 회원 조회 메서드.
     * 이메일을 기준으로 회원 정보를 조회합니다.
     * 이메일의 유효성 검사를 포함합니다.
     * 이메일에 대한 회원 정보는 캐시에서 조회하며, 캐시에 없는 경우 DB에서 가져와 캐시에 저장합니다.
     * 
     * @param email 조회하려는 회원의 이메일
     * @return 회원의 정보가 담긴 DTO
     * @throws MemberNotFoundException 해당 이메일의 회원이 없을 경우 발생하는 예외.
     * @throws InvalidEmailException   이메일 형식에 맞지 않을때 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    @KwonCacheable(value = "Member", key = "")
    public MemberConvertDTO readMember(final String email) {
        log.info("Is Running Read Member ServiceImpl");
        notFoundMember(email);
        MemberValidator.validateEmail(email);
        return memberMapper.readMember(email);
    }

    /**
     * 회원 정보 업데이트 서비스.
     * 회원 정보 업데이트 메서드.
     * 제공된 DTO를 사용하여 회원 정보를 업데이트합니다.
     * 이메일, 비밀번호, 전화번호에 대한 유효성 검사를 포함합니다.
     * 회원 정보가 업데이트되면 해당 회원의 캐시된 데이터가 삭제됩니다.
     * 
     * @param memberUpdateDTO 업데이트하려는 회원 정보 DTO
     * @return 업데이트된 회원의 ID
     * @throws DataNotFoundException               필수 정보가 누락될 경우 발생하는 예외.
     * @throws InvalidEmailException               이메일 형식에 맞지 않을때 발생하는 예외.
     * @throws PasswordIllegalArgumentException    비밀번호 형식에 맞지 않을때 발생하는 예외.
     * @throws MemberPhoneIllegalArgumentException 전화번호 형식에 맞지 않을때 발생하는 예외.
     * @throws MemberNotFoundException             해당 이메일의 회원이 없을 경우 발생하는 예외.
     */
    @Override
    @Transactional
    @KwonCacheEvict(value = "Member", key = "")
    public Long updateMember(final MemberUpdateDTO memberUpdateDTO) {
        log.info("Is Running Update Member ServiceImpl");
        if (memberUpdateDTO.getEmail() == null || memberUpdateDTO.getMemberName() == null
                || memberUpdateDTO.getMemberPhone() == null || memberUpdateDTO.getMemberPw() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호, 패스워드는 필수사항입니다.");
        }
        notFoundMember(memberUpdateDTO.getEmail());
        MemberValidator.validateEmail(memberUpdateDTO.getEmail());
        MemberValidator.validatePassword(memberUpdateDTO.getMemberPw());
        MemberValidator.validatePhoneNumber(memberUpdateDTO.getMemberPhone());
        memberUpdateDTO.setMemberPw(passwordEncoder.encode(memberUpdateDTO.getMemberPw()));
        return memberMapper.updateMember(memberUpdateDTO);
    }

    /**
     * 회원 탈퇴 서비스.
     * 회원 탈퇴 메서드.
     * 회원의 이메일을 기준으로 회원 정보를 삭제합니다.
     * 해당 회원이 탈퇴하면, 그 회원의 캐시된 데이터가 삭제됩니다.
     * 
     * @param email 탈퇴하려는 회원의 이메일
     * @return 삭제된 회원의 ID
     * @throws MemberNotFoundException 해당 이메일의 회원이 없을 경우 발생하는 예외.
     * @throws InvalidEmailException   이메일 형식에 맞지 않을때 발생하는 예외.
     */
    @Override
    @Transactional
    @KwonCacheEvict(value = "Member", key = "")
    public Long deleteMember(final String email) {
        log.info("Is Running Delete Member ServiceImpl");
        notFoundMember(email);
        MemberValidator.validateEmail(email);
        memberMapper.deleteMemberRole(email);
        return memberMapper.deleteMember(email);
    }

    /**
     * 회원 조회 서비스.
     * 회원 목록 조회 메서드.
     * 제공된 페이지 요청 정보를 기반으로 회원 목록을 조회합니다.
     * 
     * @param pageRequestDTO 페이지 정보 및 정렬 정보를 포함하는 DTO
     * @return 회원 목록 및 페이지 정보를 포함하는 응답 DTO
     * @throws DataNotFoundException 해당하는 회원 리스트가 없을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<MemberListDTO> listMember(final PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Member ServiceImpl");
        List<MemberListDTO> list = memberMapper.listMember(pageRequestDTO);
        int total = memberMapper.total(pageRequestDTO);
        return PageResponseDTO.<MemberListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /**
     * 주어진 이메일 주소의 회원 인증 상태를 검증하고 업데이트합니다.
     * 이 메서드는 이메일 주소의 유효성을 검사하고 해당 이메일 주소를 가진 회원의 인증 상태를 변경합니다.
     * 
     * @param email 검증할 이메일 주소
     * @throws EmailValidationException 이메일 주소가 유효하지 않은 경우
     * @throws MemberNotFoundException  해당 이메일 주소를 가진 회원이 존재하지 않는 경우
     * 
     */
    @Override
    @Transactional
    public void verifyEmail(final String email) {
        log.info("Is Running VerifiyEmail Member ServiceImpl");
        MemberValidator.validateEmail(email);
        notFoundMember(email);
        memberMapper.verifyEmail(email);
    }

    /**
     * 회원 이메일 중복 체크 서비스.
     * 회원 이메일 중복 체크 메서드.
     * 제공된 이메일을 사용하여 이미 등록된 회원이 있는지 중복 체크를 수행합니다.
     * 이미 등록된 회원이 있으면 예외를 발생시킵니다.
     * 
     * @param email 중복 체크를 수행하려는 이메일
     * @throws MemberEmailDuplicateException 이미 등록된 이메일일 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void duplicateMemberEmail(final String email) {
        log.info("Is Running Duplicate Member Email ServiceImpl");
        MemberValidator.validateEmail(email);
        if (memberMapper.findMemberEmail(email) == 1) {
            throw new MemberEmailDuplicateException("이미 회원가입이 완료된 이메일입니다");
        }
    }

    /**
     * 회원 가입 상태 검증 서비스.
     * 회원 가입 상태 조회 검증 메서드.
     * 제공된 이메일을 사용하여 해당 회원의 가입 상태를 확인합니다.
     * 해당 이메일의 회원이 없으면 예외를 발생시킵니다.
     * 
     * @param email 가입 상태를 조회하려는 이메일
     * @throws MemberNotFoundException 해당 이메일의 회원이 없을 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void notFoundMember(final String email) {
        log.info("Is Running Not Found Member Email ServiceImpl");
        MemberValidator.validateEmail(email);
        if (memberMapper.findMemberEmail(email) == 0 || memberMapper.findMemberEmail(email) == null) {
            throw new MemberNotFoundException("해당하는 이메일의 회원이 없습니다.");
        }
    }

    /**
     * 이메일 중복 체크 서비스.
     * 이메일 중복 체크 메서드.
     * 제공된 이메일을 사용하여 이미 등록된 회원이 있는지 중복 체크를 수행하고, 결과 값을 반환합니다.
     * 
     * @param email 중복 체크를 수행하려는 이메일
     * @return 이메일의 중복 여부를 나타내는 수치 값 (0: 중복 없음, 1: 중복 있음)
     * @throws InvalidEmailException 이메일 형식에 맞지 않을때 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public Long duplicateEmail(final String email) {
        log.info("Is Running Duplicate Email ServiceImpl");
        MemberValidator.validateEmail(email);
        return memberMapper.findMemberEmail(email);
    }
}