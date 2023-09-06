package board.mybatis.mvc.notice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.mappers.NoticeMapper;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Notice Service Test Class
@Log4j2
@SpringBootTest
public class NoticeServiceTests {

    // 의존성 주입
    @Autowired
    private NoticeService noticeService;

    // 의존성 주입 
    @Autowired
    private FileMapper fileMapper;

    // 의존성 주입 
    @Autowired
    private NoticeMapper noticeMapper;

    // 테스트 시작시 메모리 선 참조
    private static final String JUNIT_TEST_WRITER = "Junit_Test_Writer";
    private static final String JUNIT_TEST_CONTENT = "Junit_Test_Content";
    private static final String JUNIT_TEST_TITLE = "Junit_Test_Title";
    private static final String JUNIT_TEST_FILE_NAME = "Junit_Test_File_Name.jpg";
    private static final Long JUNIT_TEST_NNO = 3L;

    // Beforeach 를 위한 DTO 정의
    private NoticeCreateDTO noticeCreateDTO;
    private NoticeUpdateDTO noticeUpdateDTO;
    private String uuid;

    // BeforeEach 사용
    @BeforeEach
    public void setUp() {
        // File Name 중복 방지를 위한 UUID
        uuid = UUID.randomUUID().toString();

        noticeCreateDTO = NoticeCreateDTO.builder()
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();

        noticeUpdateDTO = NoticeUpdateDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .fileName(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();
    }

    // Create Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 생성 테스트")
    public void createNoticeTest() {
        // GIVEN
        log.info("=== Start Create Notice Service Test ===");
        // WHEN
        noticeService.createNotice(noticeCreateDTO);
        if (noticeCreateDTO.getContent() == null || noticeCreateDTO.getWriter() == null
                || noticeCreateDTO.getTitle() == null) {
            throw new DataNotFoundException("공지사항 작성자, 내용, 제목은 필수입니다.");
        }
        List<String> fileNames = noticeCreateDTO.getFileName();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long nno = noticeCreateDTO.getNno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        // THEN
        Assertions.assertEquals(noticeCreateDTO.getContent(), JUNIT_TEST_CONTENT);
        Assertions.assertEquals(noticeCreateDTO.getWriter(), JUNIT_TEST_WRITER);
        Assertions.assertEquals(noticeCreateDTO.getTitle(), JUNIT_TEST_TITLE);
        log.info("=== End Create Notice Service Test ===");
    }

    // Read Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 상세 조회 테스트")
    public void readNoticeTest() {
        // GIVEN
        log.info("=== Start Read Notice Service Test ===");
        // WHEN
        NoticeDTO beforeRead = noticeService.readNotice(JUNIT_TEST_NNO);

        if (beforeRead == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        NoticeDTO afterRead = noticeService.readNotice(JUNIT_TEST_NNO);
        // THEN
        Assertions.assertNotNull(afterRead, "afterRead Should Be Not Null");
        Assertions.assertEquals(beforeRead, afterRead);
        log.info("=== End Read Notice Service Test ===");
    }

    // Update Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 업데이트 테스트")
    public void updateNoticeTest() {
        // GIVEN
        log.info("=== Start Update Notice Service Test ===");
        // WHEN
        NoticeDTO beforeRead = noticeService.readNotice(JUNIT_TEST_NNO);

        if (beforeRead == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        noticeService.updateNotice(noticeUpdateDTO);
        if (noticeUpdateDTO.getNno() == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        if (noticeUpdateDTO.getTitle() == null || noticeUpdateDTO.getWriter() == null
                || noticeUpdateDTO.getContent() == null || noticeUpdateDTO.getNno() == null) {
            throw new DataNotFoundException("해당하는 공지사항 번호, 작성자, 내용, 제목 이 없습니다.");
        }
        fileMapper.deleteNoticeImage(JUNIT_TEST_NNO);
        List<String> fileNames = noticeUpdateDTO.getFileName();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long nno = noticeUpdateDTO.getNno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        // THEN
        NoticeDTO afterRead = noticeService.readNotice(JUNIT_TEST_NNO);
        if (afterRead == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        Assertions.assertNotNull(afterRead, "afterRead Should Be Not Null");
        Assertions.assertEquals(noticeUpdateDTO.getContent(), JUNIT_TEST_CONTENT);
        Assertions.assertEquals(noticeUpdateDTO.getWriter(), JUNIT_TEST_WRITER);
        Assertions.assertEquals(noticeUpdateDTO.getTitle(), JUNIT_TEST_TITLE);
        Assertions.assertEquals(noticeUpdateDTO.getNno(), JUNIT_TEST_NNO);
        log.info("=== End Update Notice Service Test ===");
    }

    // Delete Notice Test 
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 삭제 테스트")
    public void deleteNoticeTest() {
        // GIVEN 
        log.info("=== Start Delete Notice Service Test ===");
        // WHEN 
        NoticeDTO beforeRead = noticeService.readNotice(JUNIT_TEST_NNO);
        if(beforeRead == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        int deleteFile = fileMapper.deleteNoticeImage(JUNIT_TEST_NNO);
        Long deleteNotice = noticeService.deleteNotice(JUNIT_TEST_NNO);
        Assertions.assertEquals(deleteNotice, 1, "deleteNotice Should Be Return 1");
        log.info("=== End Delete Notice Service Test ===");
    }

    // List Notice Test 
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 리스트 테스트")
    public void listNoticeService() {
        // GIVEN 
        log.info("=== Start List Notice Service Test ===");
        // WHEN 
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        if(pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 공지사항 리스트가 없습니다.");
        }
        PageResponseDTO<NoticeListDTO> listNotice = noticeService.listNotice(pageRequestDTO);
        if(listNotice == null) {
            throw new DataNotFoundException("해당하는 공지사항 리스트가 없습니다.");
        }
        // THEN 
        Assertions.assertNotNull(listNotice, "listNotice Should Be Not Null");
        log.info(listNotice.getList());
        log.info("=== End List Notice Service Test ===");
    }

    // View Count Notice Test 
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 조회수 테스트")
    public void countViewNoticeTest() {
        // GIVEN 
        log.info("=== Start Count View Notice Service Test ===");
        // WHEN 
        NoticeDTO readNotice = noticeService.readNotice(JUNIT_TEST_NNO);
        if(readNotice == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        int viewCount = readNotice.getViewCount();
        noticeMapper.countViewNotice(JUNIT_TEST_NNO);
        // THEN 
        NoticeDTO afterRead = noticeService.readNotice(JUNIT_TEST_NNO);
        int updatedViewCount = afterRead.getViewCount();
        Assertions.assertEquals(viewCount + 1, updatedViewCount);
    }
}
