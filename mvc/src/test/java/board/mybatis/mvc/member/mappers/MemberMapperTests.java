package board.mybatis.mvc.member.mappers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberEmailDuplicateException;
import board.mybatis.mvc.mappers.MemberMapper;
import board.mybatis.mvc.util.page.PageRequestDTO;
import lombok.extern.log4j.Log4j2;

// Member Mapper Test Class
@Log4j2
@SpringBootTest
public class MemberMapperTests {

    // 의존성 주입
    @Autowired(required = false)
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 테스트 시작시 메모리 선 참조
    private static final String JUNIT_TEST_EMAIL = "thistrik@naver.com";
    private static final String JUNIT_TEST_EMAIL_VERSION_2 = "wndyd0110@naver.com";
    private static final String JUNIT_TEST_MEMBER_PASSWROD = "1111";
    private static final String JUNIT_TEST_MEMBER_NAME = "권성준";
    private static final String JUNIT_TEST_MEMBER_ROLE = "ADMIN";
    private static final String JUNIT_TEST_MEMBER_PHONE = "010-3099-0648";
    private static final String JUNIT_TEST_MEMBER_ROLE_FOR_USER = "USER";

    // BeforeEach 사용을 위한 DTO 정의
    private MemberCreateDTO memberCreateDTO;
    private MemberUpdateDTO memberUpdateDTO;

    @BeforeEach
    public void setUp() {
        memberCreateDTO = MemberCreateDTO.builder()
                .email(JUNIT_TEST_EMAIL_VERSION_2)
                .memberPw(passwordEncoder.encode(JUNIT_TEST_MEMBER_PASSWROD))
                .memberName(JUNIT_TEST_MEMBER_NAME)
                .memberPhone(JUNIT_TEST_MEMBER_PHONE)
                .build();

        memberUpdateDTO = MemberUpdateDTO.builder()
                .email(JUNIT_TEST_EMAIL)
                .memberPw(passwordEncoder.encode(JUNIT_TEST_MEMBER_PASSWROD))
                .memberName(JUNIT_TEST_MEMBER_NAME)
                .memberPhone(JUNIT_TEST_MEMBER_PHONE)
                .memberPw(JUNIT_TEST_MEMBER_NAME)
                .build();
    }

    // Create Member Test
    @Test
    @Transactional
    @DisplayName("Mapper: 회원 가입 테스트")
    public void createMemberTest() {
        // GIVEN
        log.info("=== Start Create Member Mapper Test ===");
        // WHEN
        String email = memberCreateDTO.getEmail();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        if (memberCreateDTO.getEmail() == null || memberCreateDTO.getMemberName() == null
                || memberCreateDTO.getMemberPhone() == null || memberCreateDTO.getMemberPw() == null) {
            throw new DataNotFoundException("회원 가입 시 이메일, 이름, 전화번호, 비밀번호는 필수입니다.");
        }
        if (memberMapper.findMemberEmail(JUNIT_TEST_EMAIL_VERSION_2) == 1) {
            throw new MemberEmailDuplicateException("이미 회원가입 되어 있는 이메일입니다.");
        }
        memberMapper.joinMember(memberCreateDTO);
        memberMapper.createJoinMemberRole(JUNIT_TEST_EMAIL_VERSION_2, JUNIT_TEST_MEMBER_ROLE);
        // THEN
        Assertions.assertEquals(memberCreateDTO.getEmail(), JUNIT_TEST_EMAIL_VERSION_2);
        Assertions.assertEquals(memberCreateDTO.getMemberName(), JUNIT_TEST_MEMBER_NAME);
        Assertions.assertEquals(memberCreateDTO.getMemberPhone(), JUNIT_TEST_MEMBER_PHONE);
        log.info("=== End Create Member Mapper Test ===");
    }

    // Read Member Test
    @Test
    @Transactional
    @DisplayName("Mapper: 회원 상세 조회 테스트")
    public void readMemberTest() {
        // GIVEN
        log.info("=== Start Read Member Mapper Test ===");
        // WHEN
        MemberConvertDTO beforeRead = memberMapper.readMember(JUNIT_TEST_EMAIL);
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        MemberConvertDTO afterRead = memberMapper.readMember(JUNIT_TEST_EMAIL);
        // THEN
        log.info(afterRead);
        Assertions.assertNotNull(afterRead, "afterRead Should Be Not Null");
        log.info("=== End Read Member Mapper Test ===");
    }

    // Update Member Test
    @Test
    @Transactional
    @DisplayName("Mapper: 회원 정보 업데이트 테스트")
    public void updateMemberTest() {
        // GIVEN
        log.info("=== Start Update Member Mapper Test ===");
        // WHEN
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        MemberConvertDTO beforeRead = memberMapper.readMember(JUNIT_TEST_EMAIL);
        Assertions.assertNotNull(beforeRead, "beforeRead Should Be Not Null");
        if (memberUpdateDTO.getMemberName() == null || memberUpdateDTO.getEmail() == null
                || memberUpdateDTO.getMemberPhone() == null) {
            throw new DataNotFoundException("이메일, 이름, 전화번호 는 필수사항입니다.");
        }
        memberMapper.updateMember(memberUpdateDTO);
        // THEN
        Assertions.assertEquals(memberUpdateDTO.getEmail(), JUNIT_TEST_EMAIL);
        Assertions.assertEquals(memberUpdateDTO.getMemberName(), JUNIT_TEST_MEMBER_NAME);
        Assertions.assertEquals(memberUpdateDTO.getMemberPhone(), JUNIT_TEST_MEMBER_PHONE);
    }

    // Delete Member Test 
    @Test
    @Transactional
    @DisplayName("Mapper: 회원 탈퇴 테스트")
    public void deleteMemberTest() {
        // GIVEN 
        log.info("=== Start Delete Member Mapper Test ===");
        // WHEN 
        MemberConvertDTO beforeRead = memberMapper.readMember(JUNIT_TEST_EMAIL);
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if(!matcher.matches()) {
            throw new InvalidEmailException("이메일 형식이 올바르지 않습니다.");
        }
        Long deleteMember = memberMapper.deleteMember(JUNIT_TEST_EMAIL);
        Long deleteMemberRole = memberMapper.deleteMemberRole(JUNIT_TEST_EMAIL);
        // THEN 
        MemberConvertDTO afterRead = memberMapper.readMember(JUNIT_TEST_EMAIL);
        Assertions.assertNull(afterRead, "afterRead Should Be Null");
        log.info("=== End Delete Member Mapper Test ===");
    }

    // List Member Test 
    @Test
    @Transactional
    @DisplayName("Mapper: 회원 리스트 테스트")
    public void listMemberTest() {
        // GIVEN 
        log.info("=== Start List Member Mapper Test ===");
        // WHEN 
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        if(pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 회원 리스트가 없습니다.");
        }
        List<MemberListDTO> listMember = memberMapper.listMember(pageRequestDTO);
        // THEN 
        log.info(listMember);
        Assertions.assertNotNull(listMember, "listMember SHould Be Not Null");
    }
}
