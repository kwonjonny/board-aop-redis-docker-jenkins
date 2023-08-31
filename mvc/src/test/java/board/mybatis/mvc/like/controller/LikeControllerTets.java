package board.mybatis.mvc.like.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import board.mybatis.mvc.controller.LikeController;
import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;
import board.mybatis.mvc.service.LikeService;
import lombok.extern.log4j.Log4j2;

// Like Controller Test Class
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class LikeControllerTets {

    // 의존성 주입
    @Autowired
    private MockMvc mockMvc;

    // LikeController 에 LikeService 주입
    @InjectMocks
    private LikeController likeController;

    // Mock 객체 LikeService
    @Mock
    private LikeService likeService;

    // Database AutoCloseable Using
    private AutoCloseable closeable;

    // 테스트 시작 시 메모리 선 참조
    private static final Long JUNIT_TEST_BNO = 2L;
    private static final Long JUNIT_TEST_NNO = 3L;
    private static final Long JUNIT_TEST_RETURN_COUNT = 10L;
    private static final String JUNIT_TEST_USER_EMAIL = "thistrik@naver.com";
    private static final String JUNIT_TEST_AUTHORITIES = "ROLE_USER";

    @BeforeEach
    public void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Toggle Like Controller Notice Test
    @Test
    @Transactional
    @WithMockUser(username = JUNIT_TEST_USER_EMAIL, authorities = { JUNIT_TEST_AUTHORITIES })
    @DisplayName("Controller: 토글 좋아요 기능 공지사항 테스트")
    public void toggleLikeNoticeTest() throws Exception {
        // GIVEN
        log.info("=== Start Toggle Like Notice Controller Test ===");
        // WHEN
        when(likeService.toggleLikeNotice(JUNIT_TEST_NNO, JUNIT_TEST_USER_EMAIL)).thenReturn(1L);
        when(likeService.countLikeNotice(JUNIT_TEST_NNO)).thenReturn(JUNIT_TEST_RETURN_COUNT);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/toggle/notice/" + JUNIT_TEST_NNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Toggle Like Notice Controller Test ===");
    }

    // Count Like Controller Notice Test
    @Test
    @Transactional
    @DisplayName("Controller: 좋아요 카운트 공지사항 테스트")
    public void countLikeNoticeTest() throws Exception {
        // GIVEN
        log.info("=== Start Count Like Notice Controller Test ===");
        // WHEN
        when(likeService.countLikeNotice(JUNIT_TEST_NNO)).thenReturn(JUNIT_TEST_RETURN_COUNT);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/count/notice/" + JUNIT_TEST_NNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Count Like Notice Controller Test ===");
    }

    // Toggle Like Check Notice Controller Test
    @Test
    @Transactional
    @WithMockUser(username = JUNIT_TEST_USER_EMAIL, authorities = { JUNIT_TEST_AUTHORITIES })
    @DisplayName("Controller: 사용자별 공지사항 좋아요 상태 확인")
    public void checkToggleLikeNoticeTest() throws Exception {
        // GIVEN
        LikeToggleNoticeDTO likeToggleNoticeDTO = LikeToggleNoticeDTO.builder()
                .email(JUNIT_TEST_USER_EMAIL)
                .nno(JUNIT_TEST_NNO)
                .build();
        log.info("=== Start Check Toggle Like For Member Notice Test ===");
        // WHEN
        when(likeService.checkToggleLikeNotice(JUNIT_TEST_NNO, JUNIT_TEST_USER_EMAIL))
                .thenReturn(likeToggleNoticeDTO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/check/notice/" + JUNIT_TEST_NNO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result")
                        .value(true))
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Check Toggle Like For Member Notice Test ===");

    }

    // Toggle Like Controller Test
    @Test
    @Transactional
    @WithMockUser(username = JUNIT_TEST_USER_EMAIL, authorities = { JUNIT_TEST_AUTHORITIES })
    @DisplayName("Controller: 토글 좋아요 기능 게시판 테스트")
    public void toggleLikeBoardTest() throws Exception {
        // GIVEN
        log.info("=== Start Toggle Like Board Controller Test ===");
        // WHEN
        when(likeService.toggleLikeBoard(JUNIT_TEST_BNO, JUNIT_TEST_USER_EMAIL)).thenReturn(1L);
        when(likeService.countLikeBoard(JUNIT_TEST_BNO)).thenReturn(JUNIT_TEST_RETURN_COUNT);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/toggle/board/" + JUNIT_TEST_BNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Toggle Like Board Controller Test ===");
    }

    // Count Like Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 좋아요 카운트 게시판 테스트")
    public void countLikeBoardTest() throws Exception {
        // GIVEN
        log.info("=== Start Count Like Board Controller Test ===");
        // WHEN
        when(likeService.countLikeBoard(JUNIT_TEST_BNO)).thenReturn(JUNIT_TEST_RETURN_COUNT);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/count/board/" + JUNIT_TEST_BNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Count Like Board Controller Test ===");
    }

    // Toggle Like Check Controller Test
    @Test
    @Transactional
    @WithMockUser(username = JUNIT_TEST_USER_EMAIL, authorities = { JUNIT_TEST_AUTHORITIES })
    @DisplayName("Controller: 사용자별 게시판 좋아요 상태 확인")
    public void checkToggleLikeBoardTest() throws Exception {
        // GIVEN
        LikeToggleBoardDTO likeToggleBoardDTO = LikeToggleBoardDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .email(JUNIT_TEST_USER_EMAIL)
                .build();
        log.info("=== Start Check Toggle Like For Member Board Test ===");
        // WHEN
        when(likeService
                .checkToggleLikeBoard(JUNIT_TEST_BNO, JUNIT_TEST_USER_EMAIL))
                .thenReturn(likeToggleBoardDTO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/check/board/" + JUNIT_TEST_BNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result")
                        .value(true))
                .andReturn()
                .getResponse();
        log.info("Response content: {}", response.getContentAsString());
        log.info("=== End Check Toggle Like For Member Boadr Test ===");
    }
}
