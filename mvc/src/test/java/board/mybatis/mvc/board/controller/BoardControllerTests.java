package board.mybatis.mvc.board.controller;

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

import board.mybatis.mvc.controller.BoardController;
import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.ManagementCookie;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// Board Controller Test Class 
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc // Mock MVC 자동 설정
public class BoardControllerTests {

        // MockMvc 의존성 주입
        @Autowired
        private MockMvc mockMvc;

        // Mockito에 boardController에 mock 객체 (boardService & managementCookie)를 주입하도록 지시
        @InjectMocks
        private BoardController boardController;

        // BoardService의 mock을 생성하도록 Mockito에 지시
        @Mock
        private BoardService boardService;

        // ManagementCookie의 mock을 생성하도록 Mockito에 지시
        @Mock
        private ManagementCookie managementCookie;

        // 테스트 시작 전 메모리 선 참조
        private static final Long JUNIT_TEST_BNO = 2L;
        private static final String JUNIT_TEST_TITLE = "Junit_Test_Title";
        private static final String JUNIT_TEST_WRITER = "Junit_Test_Writer";
        private static final String JUNIT_TEST_CONTENT = "Junit_Test_Content";
        private static final LocalDate JUNIT_TEST_NOW = LocalDate.now();
        private static final String JUNIT_TEST_FILE_NAME = "Junit_Test_File.jpg";

        // 클래스 수준에서 closeable 선언
        private AutoCloseable closeable;

        // FileNames 전달을 위한 UUID 선언
        private String uuid;

        @BeforeEach
        public void setUp() throws Exception {
                closeable = MockitoAnnotations.openMocks(this);
                Mockito.reset(boardService, managementCookie);
                uuid = UUID.randomUUID().toString();
        }

        @AfterEach
        public void tearDown() throws Exception {
                closeable.close();
        }

        // Junit Controller Board GET Read Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 조회 테스트")
        public void getReadBoardTest() throws Exception {
                log.info("=== Start GET Read Board Controller Test ===");
                // GIVEN
                BoardDTO list = BoardDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .createDate(JUNIT_TEST_NOW)
                                .updateDate(JUNIT_TEST_NOW)
                                .build();
                // WHEN
                // 테스트 중에 호출될 때 mock BoardService가 반환할 내용을 지정
                given(boardService.readBoard(JUNIT_TEST_BNO)).willReturn(list);
                // 테스트 중에 createCookie() 메서드가 호출될 때 항상 true를 반환하도록 mock ManagementCookie를 설정
                given(managementCookie.createCookie(any(HttpServletRequest.class), any(HttpServletResponse.class),
                                anyLong()))
                                .willReturn(true);
                // THEN
                // GET | 요청 수행 후 응답 검증
                mockMvc.perform(get("/spring/board/read/{bno}", JUNIT_TEST_BNO))
                                .andExpect(status().isOk())
                                .andExpect(view().name("spring/board/read"))
                                .andExpect(model().attributeExists("list"));
                log.info("=== End GET Read Board Controller Test ===");
        }

        // Junit Controller Board GET Update Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 업데이트 조회 테스트")
        public void getUpdateBoardTest() throws Exception {
                log.info("=== Start GET Update Board Controller Test ===");
                // GIVEN
                BoardDTO list = BoardDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .createDate(JUNIT_TEST_NOW)
                                .updateDate(JUNIT_TEST_NOW)
                                .build();
                // WHEN
                // 테스트 중에 호출될 때 mock BoardService가 반환될 내용을 지정
                given(boardService.readBoard(JUNIT_TEST_BNO)).willReturn(list);
                // THEN
                // GET | 요청 수행 후 응답 검증
                mockMvc.perform(get("/spring/board/update/{bno}", JUNIT_TEST_BNO))
                                .andExpect(status().isOk())
                                .andExpect(view().name("spring/board/update"))
                                .andExpect(model().attributeExists("list"));
                log.info("=== End GET Update Board Controller Test ===");
        }

        // Junit Controller Board GET List Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 리스트 조회 테스트")
        public void getListBoardTest() throws Exception {
                log.info("=== Start GET List Board Controller Test ===");
                // GIVEN
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                BoardListDTO listGetReady = BoardListDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .createDate(JUNIT_TEST_NOW)
                                .updateDate(JUNIT_TEST_NOW)
                                .build();
                BoardListDTO listGetReadyV2 = BoardListDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .createDate(JUNIT_TEST_NOW)
                                .updateDate(JUNIT_TEST_NOW)
                                .build();
                int total = 10;
                PageResponseDTO<BoardListDTO> mockListResponse = new PageResponseDTO<>(
                                List.of(listGetReady, listGetReadyV2),
                                total,
                                pageRequestDTO);
                log.info("mockListResponse 의 정보:" + mockListResponse.getList());
                // 테스트 중에 호출될 때 mock BoardService가 반환할 내용을 지정
                given(boardService.listBoard(pageRequestDTO)).willReturn(mockListResponse);
                // WHEN & THEN
                mockMvc.perform(get("/spring/board/list"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("spring/board/list"))
                                .andExpect(model().attribute("list", instanceOf(PageResponseDTO.class)))
                                .andExpect(model().attribute("list", hasProperty("list", hasSize(12))))
                                .andExpect(model().attribute("list", hasProperty("list", hasItem(
                                                allOf(
                                                                hasProperty("title", is(listGetReady.getTitle())),
                                                                hasProperty("content",
                                                                                is(listGetReady.getContent())))))))
                                .andExpect(model().attribute("list", hasProperty("list", hasItem(
                                                allOf(
                                                                hasProperty("title", is(listGetReadyV2.getTitle())),
                                                                hasProperty("content",
                                                                                is(listGetReadyV2.getContent())))))))
                                .andExpect(status().isOk());
                log.info("=== End GET List Board Controller Test ===");
        }

        // Junit Controller Board POST Delete Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 삭제 테스트")
        public void postDeleteBoardTest() throws Exception {
                // GIVEN
                log.info("=== Start POST Delete Board Controller Test ===");
                // 테스트 중에 호출될 때 mock BoardService가 반환할 내용을 지정
                given(boardService.deleteBoard(JUNIT_TEST_BNO)).willReturn(JUNIT_TEST_BNO);
                // WHEN & THEN
                mockMvc.perform(post("/spring/board/delete/" + JUNIT_TEST_BNO))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/board/list"))
                                .andExpect(flash().attributeExists("message"));
                log.info("=== End POST Delete Board Controller Test ===");
        }

        // Junit Controller Board POST Create Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 생성 테스트")
        public void postCreateBoardTest() throws Exception {
                log.info("=== Start POST Create Board Controller Test ===");
                // GIVEN
                BoardCreateDTO boardCreateDTO = BoardCreateDTO.builder()
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                                .build();
                // 테스트 중에 호출될 때 mock BoardService가 반환할 내용을 지정
                given(boardService.createBoard(any(BoardCreateDTO.class))).willReturn(JUNIT_TEST_BNO);
                // WHEN & THEN
                mockMvc.perform(post("/spring/board/create")
                                .param("title", JUNIT_TEST_TITLE)
                                .param("content", JUNIT_TEST_CONTENT)
                                .param("writer", JUNIT_TEST_WRITER)
                                .param("fileNames", uuid + "_" + JUNIT_TEST_FILE_NAME))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/board/list"))
                                .andExpect(flash().attributeExists("message"));
                log.info("=== End POST Create Board Controller Test ===");
        }

        // Junit Controller Board POST Update Test
        @Test
        @Transactional
        @DisplayName("Controller: Board 컨트롤러 업데이트 테스트")
        public void postUpdateBoardTest() throws Exception {
                log.info("=== Start POST Update Board Controller Test ===");
                // GIVEN
                BoardUpdateDTO boardUpdateDTO = BoardUpdateDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .title(JUNIT_TEST_TITLE)
                                .content(JUNIT_TEST_CONTENT)
                                .writer(JUNIT_TEST_WRITER)
                                .updateDate(JUNIT_TEST_NOW)
                                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                                .build();
                // 테스트 중에 호출될 때 mock BoardService 반환할 내용을 지정
                given(boardService.updateBoard(any(BoardUpdateDTO.class))).willReturn(JUNIT_TEST_BNO);
                // WHEN & THEN
                mockMvc.perform(post("/spring/board/update")
                                .param("bno", String.valueOf(JUNIT_TEST_BNO))
                                .param("title", JUNIT_TEST_TITLE)
                                .param("content", JUNIT_TEST_CONTENT)
                                .param("writer", JUNIT_TEST_WRITER)
                                .param("fileNames", uuid + "_" + JUNIT_TEST_FILE_NAME))
                                .andExpect(status().is3xxRedirection())
                                .andExpect(redirectedUrl("/spring/board/read/" + JUNIT_TEST_BNO))
                                .andExpect(flash().attributeExists("message"));
                log.info("=== End POST Update Board Controller Test ===");
        }
}
