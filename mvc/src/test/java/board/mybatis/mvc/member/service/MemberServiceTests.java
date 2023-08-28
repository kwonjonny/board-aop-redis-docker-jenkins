package board.mybatis.mvc.member.service;

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
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Member Service Test Class
@Log4j2
@SpringBootTest
public class MemberServiceTests {

    // 의존성 주입
    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 테스트 시작 시 메모리 선 참조
    private static final String JUNIT_TEST_EMAIL = "wndyd0110@naver.com";
    private static final String JUNIT_TEST_ADMIN_EMAIL = "thistrik@naver.com";
    private static final String JUNIT_TEST_PHONE_NUMBER = "010-3099-0648";
    private static final String JUNIT_TEST_PASSWORD = "1111";
    private static final String JUNIT_TEST_MEMBER_NAME = "이주용";

    // BeforEach 사용을 위한 DTO 정의
    private MemberCreateDTO memberCreateDTO;
    private MemberUpdateDTO memberUpdateDTO;

    @BeforeEach
    public void setUp() {
        memberCreateDTO = MemberCreateDTO.builder()
                .email(JUNIT_TEST_EMAIL)
                .memberName(JUNIT_TEST_MEMBER_NAME)
                .memberPhone(JUNIT_TEST_PHONE_NUMBER)
                .memberPw(passwordEncoder.encode(JUNIT_TEST_PASSWORD))
                .build();

        memberUpdateDTO = MemberUpdateDTO.builder()
                .email(JUNIT_TEST_ADMIN_EMAIL)
                .memberName(JUNIT_TEST_MEMBER_NAME)
                .memberPhone(JUNIT_TEST_PHONE_NUMBER)
                .memberPw(passwordEncoder.encode(JUNIT_TEST_PASSWORD))
                .build();
    }

    // Join Member Test 
    @Test
    @Transactional
    @DisplayName("Service: 회원 가입 테스트")
    public void createMemberTest() {
        log.info("=== Start Join Member Service Test ===");
        memberService.joinMember(memberCreateDTO);
        Assertions.assertEquals(memberCreateDTO.getEmail(), JUNIT_TEST_EMAIL);
        Assertions.assertEquals(memberCreateDTO.getMemberName(), JUNIT_TEST_MEMBER_NAME);
        Assertions.assertEquals(memberCreateDTO.getMemberPhone(), JUNIT_TEST_PHONE_NUMBER);
        log.info("=== End Join Member Service Test ===");
    }

    // Read Member Test 
    @Test
    @Transactional
    @DisplayName("Service: 회원 상세 조회 테스트")
    public void readMemberTest() {
        // GIVEN 
        log.info("=== Start Read member Service Test ===");
        // WHEN 
        MemberConvertDTO readMember = memberService.readMember(JUNIT_TEST_ADMIN_EMAIL);
        // THEN 
        log.info(readMember);
        Assertions.assertNotNull(readMember, "beforeRead Should Be Not Null");
        Assertions.assertEquals(readMember.getEmail(), JUNIT_TEST_ADMIN_EMAIL);
        log.info("=== End Read Member Service Test ===");
    }

    // Update Member Test 
    @Test
    @Transactional
    @DisplayName("Service: 회원 정보 업데이트 테스트")
    public void updateMemberTest() {
        // GIVEN 
        log.info("=== Start Update Member Service Test ===");
        // WHEN 
        memberService.updateMember(memberUpdateDTO);
        // THEN 
        Assertions.assertEquals(memberUpdateDTO.getEmail(), JUNIT_TEST_ADMIN_EMAIL);
        Assertions.assertEquals(memberUpdateDTO.getMemberPhone(), JUNIT_TEST_PHONE_NUMBER);
        Assertions.assertEquals(memberUpdateDTO.getMemberName(), JUNIT_TEST_MEMBER_NAME);
        log.info("=== End Update Member Service Test ===");
    }

    // Delete Member Test 
    @Test
    @Transactional
    @DisplayName("Service: 회원 탈퇴 테스트")
    public void deleteMemberTest() {
        // GIVEN 
        log.info("=== Start Delete Member Service Test ===");
        // WHEN 
        MemberConvertDTO beforeDelete = memberService.readMember(JUNIT_TEST_ADMIN_EMAIL);
        Assertions.assertNotNull(beforeDelete, "beforeDelete Should Be Not Null");
        Long deleteMember = memberService.deleteMember(JUNIT_TEST_ADMIN_EMAIL);
    }

    // List Member Test 
    @Test
    @Transactional
    @DisplayName("Service: 회원 리스트 테스트")
    public void listMemberTest() {
        log.info("=== Start List Member Service Test ===");
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<MemberListDTO> listMember = memberService.listMember(pageRequestDTO);
        log.info(listMember.getList());
        Assertions.assertNotNull(listMember.getList(), "listMember.getList() Should Be Not Null");
        log.info("=== End List Member Service Test ===");
    }
}
