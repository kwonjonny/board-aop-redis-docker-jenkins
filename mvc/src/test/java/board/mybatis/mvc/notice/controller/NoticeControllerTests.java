package board.mybatis.mvc.notice.controller;

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

import board.mybatis.mvc.controller.NoticeController;
import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.cookie.ManagementCookie;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

// Notice Controller Test Class
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc // Mcok MVC 자동 설정
public class NoticeControllerTests {

    // MockMvc 의존성 주입
    @Autowired
    private MockMvc mockMvc;

    // Mockito에 noticeController에 mock 객체 (noticeservice & managementCookie) 를 주입하도록
    // 지시
    @InjectMocks
    private NoticeController noticeController;

    // NoticeService의 mock을 생성하도록 Mockito에 지시
    @Mock
    private NoticeService noticeService;

    // ManagementCookie의 mock을 생성하도록 Mockito에 지시
    @Mock
    private ManagementCookie managementCookie;

    // 테스트 시작 전 메모리 선 참조
    private static final Long JUNIT_TEST_NNO = 101L;
    private static final Long JUNIT_TEST_NNO_FOR_MOCK = 4L;
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
        Mockito.reset(noticeService, managementCookie);
        uuid = UUID.randomUUID().toString();
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    // Junit Controller Notice GET Read Test
    @Test
    @Transactional
    @DisplayName("Controller: Notice 컨트롤러 조회 테스트")
    public void getReadNoticeTest() throws Exception {
        log.info("=== Start GET Read Notice Controller Test ===");
        // GIVEN
        NoticeDTO list = NoticeDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .createDate(JUNIT_TEST_NOW)
                .updateDate(JUNIT_TEST_NOW)
                .build();
        // WHEN
        // 테스트 중에 호출될 때 mock NoticeService가 반환할 내용을 지정
        given(noticeService.readNotice(JUNIT_TEST_NNO)).willReturn(list);
        // 테스트 중에 createCookie() 메서드가 호출될 때 항상 true를 반환하도록 mock ManagementCookie를 설정
        given(managementCookie.createCookie(any(HttpServletRequest.class), any(HttpServletResponse.class), anyLong()))
                .willReturn(true);
        // THEN
        // GET | 요청을 수행 후 응답 검증
        mockMvc.perform(get("/spring/notice/read/{nno}", JUNIT_TEST_NNO))
                .andExpect(status().isOk())
                .andExpect(view().name("spring/notice/read"))
                .andExpect(model().attributeExists("list"));
        log.info("=== End GET Read Notice Controller Test ===");
    }

    // Junit Controller Notice GET Update Test
    @Test
    @Transactional
    @DisplayName("Controller: Notice 컨트롤러 업데이트 조회 테스트")
    public void getUpdateNoticeTest() throws Exception {
        log.info("=== Start GET Update Notice Controller Test ===");
        // GIVEN
        NoticeDTO list = NoticeDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .createDate(JUNIT_TEST_NOW)
                .updateDate(JUNIT_TEST_NOW)
                .build();
        // WHEN
        // 테스트 중 호출될 때 mock NoticeService가 반환될 내용을 지정
        given(noticeService.readNotice(JUNIT_TEST_NNO)).willReturn(list);

        mockMvc.perform(get("/spring/notice/update/{nno}", JUNIT_TEST_NNO))
                .andExpect(status().isOk())
                .andExpect(view().name("spring/notice/update"))
                .andExpect(model().attributeExists("list"));
        log.info("=== End GET Update Notice Controller Test ===");
    }

//     // Junit Controller Notice GET List Test
//     @Test
//     @Transactional
//     @DisplayName("Controller: Notice 컨트롤러 리스트 조회 테스트")
//     public void getListNoticeTest() throws Exception {
//         log.info("=== Start GET List Notice Controller Test ===");
//         // GIVEN
//         PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
//         NoticeListDTO listGetReady = NoticeListDTO.builder()
//                 .nno(JUNIT_TEST_NNO)
//                 .title(JUNIT_TEST_TITLE)
//                 .content(JUNIT_TEST_CONTENT)
//                 .writer(JUNIT_TEST_WRITER)
//                 .createDate(JUNIT_TEST_NOW)
//                 .updateDate(JUNIT_TEST_NOW)
//                 .build();

//         NoticeListDTO listGetReadyV2 = NoticeListDTO.builder()
//                 .nno(JUNIT_TEST_NNO_FOR_MOCK)
//                 .title(JUNIT_TEST_TITLE)
//                 .content(JUNIT_TEST_CONTENT)
//                 .writer(JUNIT_TEST_WRITER)
//                 .createDate(JUNIT_TEST_NOW)
//                 .updateDate(JUNIT_TEST_NOW)
//                 .build();
//         int total = 10;
//         PageResponseDTO<NoticeListDTO> mockListResponse = new PageResponseDTO<>(
//                 List.of(listGetReady, listGetReadyV2),
//                 total,
//                 pageRequestDTO);
//         assertEquals(2, mockListResponse.getList().size());
//         log.info("mockListReponse 의 정보: " + mockListResponse.getList());
//         // mock NoticeService 설정
//         given(noticeService.listNotice(pageRequestDTO)).willReturn(mockListResponse);
//         // WHEN & THEN
//         mockMvc.perform(get("/spring/notice/list"))
//                 .andExpect(status().isOk())
//                 .andExpect(view().name("spring/notice/list"))
//                 .andExpect(model().attribute("list", instanceOf(PageResponseDTO.class)))
//                 .andExpect(model().attribute("list", hasProperty("list", hasSize(2))))
//                 .andExpect(model().attribute("list", hasProperty("list", hasItem(
//                         allOf(
//                                 hasProperty("title", is(listGetReady.getTitle())),
//                                 hasProperty("content", is(listGetReady.getContent())))))))
//                 .andExpect(model().attribute("list", hasProperty("list", hasItem(
//                         allOf(
//                                 hasProperty("title", is(listGetReadyV2.getTitle())),
//                                 hasProperty("content", is(listGetReadyV2.getContent())))))))
//                 .andExpect(status().isOk());
//         log.info("=== End GET List Notice Controller Test ===");
//     }

    // Junit Controller Notice POST Delete Test
    @Test
    @Transactional
    @DisplayName("Controller: Notice 컨트롤러 삭제 테스트")
    public void postDeleteNoticeTest() throws Exception {
        // GIVEN
        log.info("=== Start POST Delete Notice Controller Test ===");
        // 테스트 중에 호출될 때 mock NoticeService가 반환할 내용을 지정
        given(noticeService.deleteNotice(JUNIT_TEST_NNO)).willReturn(JUNIT_TEST_NNO);
        // WHEN & THEN
        mockMvc.perform(post("/spring/notice/delete/" + JUNIT_TEST_NNO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/spring/notice/list"))
                .andExpect(flash().attributeExists("message"));
        log.info("=== End POST Delete Notice Controller Test ===");
    }

    // Junit Controller Notice Create Test
    @Test
    @Transactional
    @DisplayName("Controller: Notice 컨트롤러 생성 테스트")
    public void postCreateNoticeTest() throws Exception {
        log.info("=== Start POST Create Notice Controller Test ===");
        // GIVEN
        NoticeCreateDTO noticeCreateDTO = NoticeCreateDTO.builder()
                .title(JUNIT_TEST_TITLE)
                .writer(JUNIT_TEST_WRITER)
                .content(JUNIT_TEST_CONTENT)
                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();
        // 테스트 중에 호출될 때 mock NoticeService가 반환할 내용을 지정
        given(noticeService.createNotice(any(NoticeCreateDTO.class))).willReturn(JUNIT_TEST_NNO);
        // WHEN & THEN
        mockMvc.perform(post("/spring/notice/create")
                .param("content", JUNIT_TEST_CONTENT)
                .param("writer", JUNIT_TEST_WRITER)
                .param("title", JUNIT_TEST_TITLE)
                .param("fileName", uuid + "_" + JUNIT_TEST_FILE_NAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/spring/notice/list"))
                .andExpect(flash().attributeExists("message"));
        log.info("=== End POST Create Notice Controller Test ===");
    }

    // Junit Controller Notice POST Update Test
    @Test
    @Transactional
    @DisplayName("Controller: Notice 컨트롤러 업데이트 테스트")
    public void postUpdateBoardTest() throws Exception {
        log.info("=== Start POST Update Notice Controller Test ===");
        // GIVEN
        NoticeUpdateDTO noticeUpdateDTO = NoticeUpdateDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .updateDate(JUNIT_TEST_NOW)
                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();
        // 테스트 중에 호출될 때 mock NoticeService가 반환할 내용을 지정
        given(noticeService.updateNotice(any(NoticeUpdateDTO.class))).willReturn(JUNIT_TEST_NNO);
        // WHEN & THEN
        mockMvc.perform(post("/spring/notice/update")
                .param("nno", String.valueOf(JUNIT_TEST_NNO))
                .param("title", JUNIT_TEST_TITLE)
                .param("content", JUNIT_TEST_CONTENT)
                .param("writer", JUNIT_TEST_WRITER)
                .param("fileName", uuid + "_" + JUNIT_TEST_FILE_NAME))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/spring/notice/read/" + JUNIT_TEST_NNO))
                .andExpect(flash().attributeExists("message"));
        log.info("=== End POST Update Notice Controller Test ===");
    }
}
