package board.mybatis.mvc.member.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.controller.MemberController;
import board.mybatis.mvc.dto.member.MemberConvertDTO;
import board.mybatis.mvc.dto.member.MemberCreateDTO;
import board.mybatis.mvc.dto.member.MemberListDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.service.MemberService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Member Controller Test Class
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc // Mock MVC 자동 설정
public class MemberControllerTets {

        // MockMVC 의존성 주입
        @Autowired
        private MockMvc mockMvc;

        // Mockito에 MemberController에 Mock 객체 주입 지시
        @InjectMocks
        private MemberController memberController;

        // MemberService의 mock을 생성하도록 Mockito에 지시
        @Mock
        private MemberService memberService;

        // 테스트 시작 전 메모리 선 참조
        private static final Long JUNIT_CONTROLLER_RETURN_NUMBER = 2L;
        private static final String JUNIT_TEST_EMAIL = "rnjstjdwns@naver.com";
        private static final String JUNIT_TEST_EMAIL_V2 = "thistrik@naver.com";
        private static final String JUNIT_TEST_PASSWORD = "1111";
        private static final String JUNIT_TEST_MEMBER_NAME = "권성준";
        private static final String JUNIT_TEST_MEMBER_ROLE = "ADMIN";
        private static final String JUNIT_TEST_MEMBER_PHONE = "010-3099-0648";
        private static final LocalDate JUNIT_TEST_CEATE_DATE = LocalDate.now();

        // 클래스 수준에서 closeable 선언
        private AutoCloseable closeable;

        @BeforeEach
        public void setUp() throws Exception {
                closeable = MockitoAnnotations.openMocks(this);
                Mockito.reset(memberService);
        }

        @AfterEach
        public void tearDown() throws Exception {
                closeable.close();
        }

        // Junit Controller Member GET Read Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 회원 조회 테스트")
        public void getReadMemberTest() throws Exception {
                log.info("=== Start GET Read Member Controller Test ===");
                // GIVEN
                MemberConvertDTO list = MemberConvertDTO.builder()
                                .email(JUNIT_TEST_EMAIL)
                                .createDate(JUNIT_TEST_CEATE_DATE)
                                .memberPhone(JUNIT_TEST_MEMBER_PHONE)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .build();
                // WHEN
                // 테스트 중 호출될 때 mock MemberService가 변환할 내용을 지정
                given(memberService.readMember(JUNIT_TEST_EMAIL_V2)).willReturn(list);
                // THEN
                // GET | 요청을 수행 후 응답 검증
                mockMvc.perform(get("/spring/member/read/{email}", JUNIT_TEST_EMAIL_V2))
                                .andExpect(status().isOk())
                                .andExpect(view().name("spring/member/read"))
                                .andExpect(model().attributeExists("list"));
                log.info("=== End GET Read Member Controller Test ===");
        }

        // Junit Controller Member GET Update Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 회원 업데이트 조회 테스트")
        public void getUpdateMemberTest() throws Exception {
                log.info("=== Start GET Update Member Controller Test ===");
                // GIVEN
                MemberConvertDTO list = MemberConvertDTO.builder()
                                .email(JUNIT_TEST_EMAIL_V2)
                                .createDate(JUNIT_TEST_CEATE_DATE)
                                .memberPhone(JUNIT_TEST_EMAIL)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .build();
                // WHEN
                // 테스트 중 호출될 때 mock MemberService 변환할 내용을 지정
                given(memberService.readMember(JUNIT_TEST_EMAIL_V2)).willReturn(list);
                // THEN
                // GET | 요청을 수행 후 응답 검증
                mockMvc.perform(get("/spring/member/update/{email}", JUNIT_TEST_EMAIL_V2))
                                .andExpect(status().isOk())
                                .andExpect(view().name("spring/member/update"))
                                .andExpect(model().attributeExists("list"));
                log.info("=== End GET Update Member Controller Test ===");
        }

        // Junit Controller Member GET List Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 리스트 조회 테스트")
        public void getListMemberTest() throws Exception {
                log.info("=== Start GET List Member Controller Test ===");
                // GIVEN
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                MemberListDTO listGetReady = MemberListDTO.builder()
                                .email(JUNIT_TEST_EMAIL_V2)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .memberPhone(JUNIT_TEST_MEMBER_PHONE)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .build();
                int total = 10;
                PageResponseDTO<MemberListDTO> mockListResponse = new PageResponseDTO<>(
                                List.of(listGetReady),
                                total,
                                pageRequestDTO);
                log.info("mockListResponse 의 정보: " + mockListResponse);
                // mock MemberService 설정
                given(memberService.listMember(pageRequestDTO)).willReturn(mockListResponse);
                // WHEN & THEN
                mockMvc.perform(get("/spring/member/list"))
                                .andExpect(view().name("spring/member/list"))
                                .andExpect(model().attribute("list", instanceOf(PageResponseDTO.class)))
                                .andExpect(model().attribute("list", hasProperty("list", hasSize(1))))
                                .andExpect(model().attribute("list", hasProperty("list", hasItem(
                                                allOf(
                                                                hasProperty("email", is(listGetReady.getEmail())),
                                                                hasProperty("memberName",
                                                                                is(listGetReady.getMemberName())))))))
                                .andExpect(status().isOk());
                log.info("=== End GET List member Controller Test ===");
        }

        // Junit Controller Member POST Create Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 회원 가입 테스트")
        public void postCreateMemberTest() throws Exception {
                log.info("=== Start POST Create Member Controller Test ===");
                // GIVEN
                MemberCreateDTO memberCreateDTO = MemberCreateDTO.builder()
                                .email(JUNIT_TEST_EMAIL)
                                .memberPhone(JUNIT_TEST_EMAIL)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .memberPw(JUNIT_TEST_MEMBER_NAME)
                                .build();
                // 테스트 중에 호출 될 때 mock MemberService 설정
                given(memberService.joinMember(memberCreateDTO)).willReturn(JUNIT_CONTROLLER_RETURN_NUMBER);
                // WHEN & THEN
                mockMvc.perform(post("/spring/member/create")
                                .param("email", JUNIT_TEST_EMAIL)
                                .param("memberPhone", JUNIT_TEST_MEMBER_PHONE)
                                .param("memberName", JUNIT_TEST_MEMBER_NAME)
                                .param("memberPw", JUNIT_TEST_PASSWORD))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/member/index"))
                                .andExpect(flash().attributeExists("message"));
                log.info("=== End POST Create Member Controller Test ===");
        }

        // Junit Controller Member POST Delete Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 회원 탈퇴 테스트")
        public void postDeleteMemberTest() throws Exception {
                // GIVEN
                log.info("=== Start POST Delete Member Controller Test ===");
                // 테스트 중에 호출 될 때 mock MemberService 설정
                given(memberService.deleteMember(JUNIT_TEST_EMAIL)).willReturn(JUNIT_CONTROLLER_RETURN_NUMBER);
                // WHEN & THEN
                mockMvc.perform(post("/spring/member/delete/" + JUNIT_TEST_EMAIL_V2))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/member/index"))
                                .andExpect(flash().attributeExists("message"));
                log.info("=== End POST Delete Member Controller Test ===");
        }

        // Junit Controller Member POST Update Test
        @Test
        @Transactional
        @DisplayName("Controller: Member 컨트롤러 회원 정보 업데이트 테스트")
        public void postUpdateMemberTest() throws Exception {
                // GIVEN
                MemberUpdateDTO memberUpdateDTO = MemberUpdateDTO.builder()
                                .email(JUNIT_TEST_EMAIL_V2)
                                .memberName(JUNIT_TEST_MEMBER_NAME)
                                .memberPhone(JUNIT_TEST_EMAIL)
                                .memberPw(JUNIT_TEST_MEMBER_NAME)
                                .createDate(JUNIT_TEST_CEATE_DATE)
                                .build();
                // 테스트 중에 호출 될 때 mock MemberService 설정
                given(memberService.updateMember(memberUpdateDTO)).willReturn(JUNIT_CONTROLLER_RETURN_NUMBER);
                // WHEN & THEN
                mockMvc.perform(post("/spring/member/update")
                                .param("email", JUNIT_TEST_EMAIL_V2)
                                .param("memberPhone", JUNIT_TEST_MEMBER_PHONE)
                                .param("memberPw", JUNIT_TEST_PASSWORD)
                                .param("memberName", JUNIT_TEST_MEMBER_NAME))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/member/read/" + memberUpdateDTO.getEmail()))
                                .andExpect(flash().attributeExists("message"));
        }
}
