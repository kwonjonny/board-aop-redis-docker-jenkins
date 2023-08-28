package board.mybatis.mvc.notice.mappers;

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
import board.mybatis.mvc.util.PageRequestDTO;
import lombok.extern.log4j.Log4j2;

// Notice Mapper Test Class
@Log4j2
@SpringBootTest
public class NoticeMapperTests {

    // 의존성 주입
    @Autowired(required = false)
    private NoticeMapper noticeMapper;

    @Autowired(required = false)
    private FileMapper fileMapper;

    // 테스트 시작시 메모리 선 참조
    private static final String JUNIT_TEST_TITLE = "Junit_Test_Tilte";
    private static final String JUNIT_TEST_WRITER = "Junit_Test_Writer";
    private static final String JUNIT_TEST_CONTENT = "Junit_Test_Content";
    private static final String JUNIT_TEST_FILE_NAME = "Junit_Test_File_Name.jpg";
    private static final Long JUNIT_TEST_NNO = 3L;

    // Beforeach 사용을 위한 DTO 정의
    private NoticeCreateDTO noticeCreateDTO;
    private NoticeUpdateDTO noticeUpdateDTO;
    private String uuid;

    @BeforeEach
    public void setUp() {
        // File Name 중복 방지를 위한 UUID
        uuid = UUID.randomUUID().toString();

        noticeCreateDTO = NoticeCreateDTO.builder()
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .fileNames(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();

        noticeUpdateDTO = NoticeUpdateDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .title(JUNIT_TEST_TITLE)
                .content(JUNIT_TEST_CONTENT)
                .writer(JUNIT_TEST_WRITER)
                .fileNames(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();
    }

    // Create Noitce Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 생성 테스트")
    public void createNoticeTest() {
        // GIVEN
        log.info("=== Start Create Notice Mapper Test ===");

        // WHEN
        noticeMapper.createNotice(noticeCreateDTO);
        if (noticeCreateDTO.getContent() == null || noticeCreateDTO.getTitle() == null
                || noticeCreateDTO.getWriter() == null) {
            throw new DataNotFoundException("공지사항 게시글, 제목, 작성자는 필수입니다.");
        }
        List<String> fileNames = noticeCreateDTO.getFileNames();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long nno = noticeCreateDTO.getNno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());

        // THEN
        fileMapper.createNoticeImage(list);
        Assertions.assertEquals(JUNIT_TEST_CONTENT, noticeCreateDTO.getContent());
        Assertions.assertEquals(JUNIT_TEST_TITLE, noticeCreateDTO.getTitle());
        Assertions.assertEquals(JUNIT_TEST_WRITER, noticeCreateDTO.getWriter());
        log.info("=== End Create Notice Mapper Test ====");
    }

    // Read Notice Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 상세 조회 테스트")
    public void readNoticeTest() {
        // GIVEN
        log.info("=== Start Read Notice Mapper Test ===");
        // WHEN
        NoticeDTO readNotice = noticeMapper.readNotice(JUNIT_TEST_NNO);
        if (readNotice == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        log.info(readNotice);
        Assertions.assertNotNull(readNotice, "readNotice Should Be Not Null");
        log.info("=== End Read Notice Mapper Test ===");
    }

    // Update Notice Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 업데이트 테스트")
    public void updateNoticeTest() {
        // GIVEN
        log.info("=== Start Update Notice Mapper Test ===");
        // WHEN
        NoticeDTO beforeNotice = noticeMapper.readNotice(JUNIT_TEST_NNO);
        if(beforeNotice == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }

        noticeMapper.updateNotice(noticeUpdateDTO);
        if (noticeUpdateDTO.getNno() == null || noticeUpdateDTO.getNno() == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        if (noticeUpdateDTO.getContent() == null || noticeUpdateDTO.getTitle() == null
                || noticeUpdateDTO.getWriter() == null || noticeUpdateDTO.getNno() == null) {
            throw new DataNotFoundException("해당하는 공지사항 번호, 작성자, 게시글 이 없습니다.");
        }
        fileMapper.deleteNoticeImage(JUNIT_TEST_NNO);
        List<String> fileNames = noticeUpdateDTO.getFileNames();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long nno = noticeUpdateDTO.getNno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        fileMapper.updateNoticeImage(list);
        // THEN
        Assertions.assertEquals(noticeUpdateDTO.getNno(), JUNIT_TEST_NNO);
        Assertions.assertEquals(noticeUpdateDTO.getTitle(), JUNIT_TEST_TITLE);
        Assertions.assertEquals(noticeUpdateDTO.getContent(), JUNIT_TEST_CONTENT);
        Assertions.assertEquals(noticeUpdateDTO.getWriter(), JUNIT_TEST_WRITER);
        NoticeDTO updatedNotice = noticeMapper.readNotice(JUNIT_TEST_NNO);
        Assertions.assertNotNull(updatedNotice);
        log.info("=== End Update Notice Mapper Test ===");
    }

    // Delete Notice Test 
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 삭제 테스트")
    public void deleteNoticeTest() {
        // GIVEN 
        log.info("=== Start Delete Notice Mapper Test ===");
        // WHEN 
        NoticeDTO beforeDelete = noticeMapper.readNotice(JUNIT_TEST_NNO);
        if(beforeDelete == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        // File 먼저 지운다 
        fileMapper.deleteNoticeImage(JUNIT_TEST_NNO);
        Long deleteNotice = noticeMapper.deleteNotice(JUNIT_TEST_NNO);
        // THEN 
        NoticeDTO afterDelete = noticeMapper.readNotice(deleteNotice);
        Assertions.assertNull(afterDelete, "deleteNotice Should Be Null");
        log.info("=== End Delete Notice Mapper Test ===");
    }

    // List Notice Test 
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 리스트 조회 테스트")
    public void listNoticeTest() {
        // GIVEN 
        log.info("=== Start List Notice Mapper Test ===");
        // WHEN 
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        if(pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 공지사항 리스트가 없습니다.");
        }
        List<NoticeListDTO> listNotice = noticeMapper.listNotice(pageRequestDTO);
        // THEN 
        log.info("공지사항 리스트:" + listNotice);
        Assertions.assertNotNull(listNotice, "listNotice Should Be Not Null");
        log.info("=== End List Notice Mapper Test ===");
    }

    // View Count Notce Test 
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 조회수 테스트")
    public void countViewNoticeTest() {
        // GIVEN 
        log.info("=== Start Count View Notice Mapper Test ===");
        // WHEN 
        NoticeDTO readNotice = noticeMapper.readNotice(JUNIT_TEST_NNO);
        if(readNotice == null) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        int viewCount = readNotice.getViewCount();
        noticeMapper.countViewNotice(JUNIT_TEST_NNO);
        // THEN 
        NoticeDTO afterRead = noticeMapper.readNotice(JUNIT_TEST_NNO);
        int updatedViewCount = afterRead.getViewCount();
        Assertions.assertEquals(viewCount + 1, updatedViewCount);
        log.info("=== End Count View Notice Mapper Test ===");
    }
}
