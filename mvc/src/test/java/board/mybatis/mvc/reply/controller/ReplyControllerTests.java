package board.mybatis.mvc.reply.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import board.mybatis.mvc.controller.ReplyController;
import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.service.ReplyService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Reply Controller Test Class
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class ReplyControllerTests {

    // 의존성 주입
    @Autowired
    private MockMvc mockMvc;

    // ReplyController 에 ReplyService 주입
    @InjectMocks
    private ReplyController replyController;

    // Mock 객체 ReplyService
    @Mock
    private ReplyService replyService;

    // Database AutoCloseable Using
    private AutoCloseable closeable;

    // 테스트 시작 시 메모리 선 참조
    private static final String JUNIT_TEST_REPLY = "Junit_Test_Reply";
    private static final String JUNIT_TEST_REPLYER = "thistrik@naver.com";

    private static final String JUNIT_TEST_REPLY_CHILD = "Junit_Test_Reply_Child";
    private static final String JUNIT_TEST_REPLYER_CHILD = "thistrik@naver.com";

    private static final Long JUNIT_TEST_NNO = 3L;
    private static final Long JUNIT_TEST_BNO = 2L;
    private static final Long JUNIT_TEST_BOARD_GNO = 490L;
    private static final Long JUNIT_TEST_BOARD_RNO = 490L;
    private static final Long JUNIT_TEST_BOARD_CHILD_RNO = 497L;

    private static final Long JUNIT_TEST_NOTICE_RNO = 491L;
    private static final Long JUNIT_TEST_NOTICE_GNO = 491L;
    private static final Long JUNIT_TEST_NOTICE_CHILD_RNO = 494L;

    @BeforeEach
    public void setUp() throws Exception {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Create Board Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 댓글 생성 테스트")
    public void createBoardReplyTest() throws Exception {
        log.info("=== Start Create Board Reply Controller Test ===");
        // GIVEN
        ReplyBoardCreateDTO replyBoardCreateDTO = ReplyBoardCreateDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        // WHEN
        when(replyService.createBoardReply(replyBoardCreateDTO)).thenReturn(JUNIT_TEST_BNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/board/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("reply", JUNIT_TEST_REPLY)
                        .param("replyer", JUNIT_TEST_REPLYER)
                        .param("bno", String.valueOf(JUNIT_TEST_BNO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Create Board Reply Controller Test ===");
    }

    // Create Board Reply Child Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 대 댓글 생성 테스트")
    public void createBoardReplyChildTest() throws Exception {
        log.info("=== Start Create Board Reply Child Controller Test ===");
        // GIVEN
        ReplyBoardCreateDTO replyBoardCreateDTO = ReplyBoardCreateDTO.builder()
                .gno(JUNIT_TEST_BOARD_GNO)
                .bno(JUNIT_TEST_BNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        // WHEN
        when(replyService.createBoardReply(replyBoardCreateDTO)).thenReturn(JUNIT_TEST_BNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/board/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("reply", JUNIT_TEST_REPLY)
                        .param("replyer", JUNIT_TEST_REPLYER)
                        .param("gno", String.valueOf(JUNIT_TEST_BOARD_GNO))
                        .param("bno", String.valueOf(JUNIT_TEST_BNO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Create Board Reply Child Controller Test ===");
    }

    // Create Notice Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 댓글 생성 테스트")
    public void createNoticeReplyTest() throws Exception {
        log.info("=== Start Create Notice Reply Controller Test ===");
        // GIVEN
        ReplyNoticeCreateDTO replyNoticeCreateDTO = ReplyNoticeCreateDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        // WHEN
        when(replyService.createNoticeReply(replyNoticeCreateDTO)).thenReturn(JUNIT_TEST_NNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/notice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nno", String.valueOf(JUNIT_TEST_NNO))
                        .param("reply", JUNIT_TEST_REPLY)
                        .param("replyer", JUNIT_TEST_REPLYER))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Create Notice Reply Controller Test ===");
    }

    // Create Notice Reply Child Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 대 댓글 생성 테스트")
    public void createNoticeReplyChildTest() throws Exception {
        log.info("=== Start Create Notice Reply Child Controller Test ===");
        // GIVEN
        ReplyNoticeCreateDTO replyNoticeCreateDTO = ReplyNoticeCreateDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .reply(JUNIT_TEST_REPLY_CHILD)
                .replyer(JUNIT_TEST_REPLYER_CHILD)
                .gno(JUNIT_TEST_NOTICE_GNO)
                .build();
        // WHEN
        when(replyService.createNoticeReply(replyNoticeCreateDTO)).thenReturn(JUNIT_TEST_NNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                post("/reply/notice/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("nno", String.valueOf(JUNIT_TEST_NNO))
                        .param("gno", String.valueOf(JUNIT_TEST_NOTICE_GNO))
                        .param("reply", JUNIT_TEST_REPLY_CHILD)
                        .param("replyer", JUNIT_TEST_REPLYER_CHILD))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
    }

    // Update Board Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 댓글 업데이트 테스트")
    public void updateBoardReplyTest() throws Exception {
        log.info("=== Start Update Board Reply Controller Test ===");
        // GIVEN
        ReplyBoardUpdateDTO replyBoardUpdateDTO = ReplyBoardUpdateDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .gno(JUNIT_TEST_BOARD_GNO)
                .rno(JUNIT_TEST_BOARD_RNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        // WHEN
        when(replyService.updateBoardReply(replyBoardUpdateDTO)).thenReturn(JUNIT_TEST_BNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                put("/reply/board/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("bno", String.valueOf(JUNIT_TEST_BNO))
                        .param("gno", String.valueOf(JUNIT_TEST_BOARD_GNO))
                        .param("rno", String.valueOf(JUNIT_TEST_BOARD_RNO))
                        .param("reply", JUNIT_TEST_REPLY)
                        .param("replyer", JUNIT_TEST_REPLYER))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Update Board Reply Controller Test ===");
    }

    // Update Notice Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 댓글 업데이트 테스트")
    public void updateNoticeReplyTest() throws Exception {
        log.info("=== Start Update Notice Reply Controller Test ===");
        // GIVEN
        ReplyNoticeUpdateDTO replyNoticeUpdateDTO = ReplyNoticeUpdateDTO.builder()
                .rno(JUNIT_TEST_NOTICE_RNO)
                .nno(JUNIT_TEST_NNO)
                .gno(JUNIT_TEST_NOTICE_GNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        // WHEN
        when(replyService.updateNoticeReply(replyNoticeUpdateDTO)).thenReturn(JUNIT_TEST_NNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                put("/reply/notice/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("rno", String.valueOf(JUNIT_TEST_NOTICE_RNO))
                        .param("nno", String.valueOf(JUNIT_TEST_NNO))
                        .param("gno", String.valueOf(JUNIT_TEST_NOTICE_GNO))
                        .param("reply", JUNIT_TEST_REPLY)
                        .param("replyer", JUNIT_TEST_REPLYER))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Update Notice Reply Controller Test ===");
    }

    // Delete Board Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 댓글 삭제 테스트")
    public void deleteBoardReplyTest() throws Exception {
        // GIVEN
        log.info("=== Start Delete Board Controller Reply Test ===");
        // WHEN
        when(replyService.deleteBoardReply(JUNIT_TEST_BOARD_CHILD_RNO)).thenReturn(JUNIT_TEST_BOARD_CHILD_RNO);
        /// Request
        MockHttpServletResponse response = mockMvc.perform(
                delete("/reply/board/delete/" + JUNIT_TEST_BOARD_CHILD_RNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Delete Board Reply Controller Test ===");
    }

    // Delete Notice Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 댓글 삭제 테스트")
    public void deleteNoticeReplyTest() throws Exception {
        // GIVEN
        log.info("=== Start Delete Notice Controller Reply Test ===");
        // WHEN
        when(replyService.deleteNoticeReply(JUNIT_TEST_NOTICE_RNO)).thenReturn(JUNIT_TEST_NOTICE_RNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                delete("/reply/notice/delete/" + JUNIT_TEST_NOTICE_RNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Delete Notice Reply Controller Test ===");
    }

    // List Board Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 댓글 리스트 테스트")
    public void listBoardReplyTest() throws Exception {
        log.info("=== Start List Board Reply Controller Test ===");
        // GIVEN
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        ReplyBoardListDTO listGetReady = ReplyBoardListDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        int total = 10;
        PageResponseDTO<ReplyBoardListDTO> mockListReponse = new PageResponseDTO<>(
                List.of(listGetReady),
                total,
                pageRequestDTO);
        // WHEN
        when(replyService.listBoardReply(pageRequestDTO, JUNIT_TEST_BNO)).thenReturn(mockListReponse);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/board/list/" + JUNIT_TEST_BNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End List Board Reply Controller Test ===");
    }

    // List Notice Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 댓글 리스트 테스트")
    public void listNoticeReplyTest() throws Exception {
        log.info("=== Start List Notice Reply Controller Test ===");
        // GIVEN
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        ReplyNoticeListDTO listGetReady = ReplyNoticeListDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .reply(JUNIT_TEST_REPLY)
                .replyer(JUNIT_TEST_REPLYER)
                .build();
        int total = 10;
        PageResponseDTO<ReplyNoticeListDTO> mockListReponse = new PageResponseDTO<>(
                List.of(listGetReady),
                total,
                pageRequestDTO);
        // WHEN
        when(replyService.listNoticeReply(pageRequestDTO, JUNIT_TEST_NNO)).thenReturn(mockListReponse);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/notice/list/" + JUNIT_TEST_NNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End List Notice Reply Controller Test ===");
    }

    // Count Board Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 게시물 댓글 카운트 테스트")
    public void countBoardReplyTest() throws Exception {
        log.info("=== Start Count Board Reply Controller Test ===");
        // WHEN
        when(replyService.countBoardReply(JUNIT_TEST_BNO)).thenReturn(JUNIT_TEST_BNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/board/count/" + JUNIT_TEST_BNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Count Board Reply Controller Test ===");
    }

    // Count Notice Reply Controller Test
    @Test
    @Transactional
    @DisplayName("Controller: 공지사항 댓글 카운트 테스트")
    public void countNoticeReplyTest() throws Exception {
        log.info("=== Start Count Notice Reply Controller Test ===");
        // WHEN
        when(replyService.countNoticeReply(JUNIT_TEST_NNO)).thenReturn(JUNIT_TEST_NNO);
        // Request
        MockHttpServletResponse response = mockMvc.perform(
                get("/reply/notice/count/" + JUNIT_TEST_NNO)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        // Check Response
        String responseBody = response.getContentAsString();
        log.info("Response content: {}", responseBody);
        log.info("=== End Count Notice Reply Controller Test ===");
    }
}