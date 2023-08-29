package board.mybatis.mvc.board.service;

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

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Board Service Test Class
@Log4j2
@SpringBootTest
public class BoardServiceTests {

    // 의존성 주입
    @Autowired
    private BoardService boardService;

    @Autowired(required = false)
    private FileMapper fileMapper;

    // 테스트 시작시 메모리에 static 으로 올려놓고 동작
    private static final String JUNIT_TEST_TITLE = "Junit_Test_Title";
    private static final String JUNIT_TEST_CONTENT = "Junit_Test_Content";
    private static final String JUNIT_TEST_WRITER = "Junit_Test_Writer";
    private static final Long JUNIT_TEST_BNO = 2L;
    private static final String JUNIT_TEST_FILE_NAME = "Junit_Test_File_Name.jpg";

    // Beforeach 사용을 위한 DTO 정의
    private BoardCreateDTO boardCreateDTO;
    private BoardUpdateDTO boardUpdateDTO;
    private String uuid;

    // Beforeach Set Up
    @BeforeEach
    public void setUp() {
        uuid = UUID.randomUUID().toString();

        boardCreateDTO = BoardCreateDTO.builder()
                .content(JUNIT_TEST_CONTENT)
                .title(JUNIT_TEST_TITLE)
                .writer(JUNIT_TEST_WRITER)
                .fileNames(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();

        boardUpdateDTO = BoardUpdateDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .content(JUNIT_TEST_CONTENT)
                .title(JUNIT_TEST_TITLE)
                .writer(JUNIT_TEST_WRITER)
                .fileNames(Arrays.asList(uuid + "_" + JUNIT_TEST_FILE_NAME))
                .build();
    }

    // Create Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 생성 테스트")
    public void createBoardTest() {
        // GIVEN
        log.info("=== Start Create Board Service Test ===");
        // WHEN
        boardService.createBoard(boardCreateDTO);
        if (boardCreateDTO.getContent() == null || boardCreateDTO.getTitle() == null
                || boardCreateDTO.getWriter() == null) {
            throw new DataNotFoundException("작성자, 제목, 내용은 필수 사항입니다.");
        }
        List<String> fileNames = boardCreateDTO.getFileNames();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long bno = boardCreateDTO.getBno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        // THEN
        Assertions.assertEquals(JUNIT_TEST_CONTENT, boardCreateDTO.getContent());
        Assertions.assertEquals(JUNIT_TEST_TITLE, boardCreateDTO.getTitle());
        Assertions.assertEquals(JUNIT_TEST_WRITER, boardCreateDTO.getWriter());
        log.info("=== End Create Board Service Test ===");
    }

    // Read Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 상세 조회 테스트")
    public void readBoardTest() {
        // GIVEN
        log.info("=== Start Read Board Service Test ===");
        // WHEN
        BoardDTO readBoard = boardService.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        // THEN
        log.info(readBoard);
        Assertions.assertNotNull(readBoard, "readedBoard Should Be Not Null");
        Assertions.assertEquals(readBoard.getBno(), 2L);
        log.info("=== End Read Board Service Test ===");
    }

    // Update Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 업데이트 테스트")
    public void updateBoardTest() {
        // GIVEN
        log.info("=== Start Update Board Service Test ===");
        BoardDTO readBoard = boardService.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        // WHEN
        boardService.updateBoard(boardUpdateDTO);
        if (boardUpdateDTO.getBno() == null || boardUpdateDTO.getContent() == null || boardUpdateDTO.getWriter() == null
                || boardUpdateDTO.getTitle() == null) {
            throw new DataNotFoundException("작성자, 제목 내용은 필수 사항입니다.");
        }
        fileMapper.deleteImage(boardUpdateDTO.getBno());
        List<String> fileNames = boardCreateDTO.getFileNames();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long bno = boardUpdateDTO.getBno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        fileMapper.updateImage(list);
        // THEN
        Assertions.assertEquals(JUNIT_TEST_CONTENT, boardUpdateDTO.getContent());
        Assertions.assertEquals(JUNIT_TEST_BNO, boardUpdateDTO.getBno());
        Assertions.assertEquals(JUNIT_TEST_WRITER, boardUpdateDTO.getWriter());
        Assertions.assertEquals(JUNIT_TEST_TITLE, boardUpdateDTO.getTitle());
        Assertions.assertNotNull(readBoard, "readBoard Should Be Not Null");
    }

    // Delete Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 삭제 테스트")
    public void deleteBoardTest() {
        // GIVEN
        log.info("=== Start Delete Board Service Test ===");
        // WHEN
        BoardDTO readBoard = boardService.readBoard(JUNIT_TEST_BNO);
        log.info("게시물: "+readBoard);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        Long deleteBoard = boardService.deleteBoard(JUNIT_TEST_BNO);
        // THEN
        Assertions.assertEquals(deleteBoard, 1, "deleteBoard Should Be Return 1");
        log.info("=== End Delete Board Service Test ===");
    }

    // List Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 리스트 테스트")
    public void listBoardTest() {
        // GIVEN
        log.info("=== Start List Board Service Test ===");
        // WHEN
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<BoardListDTO> listBoard = boardService.listBoard(pageRequestDTO);
        if (pageRequestDTO == null || listBoard == null) {
            throw new DataNotFoundException("해당하는 게시글 리스트가 없습니다.");
        }
        // THEN
        log.info(listBoard.getList());
        Assertions.assertNotNull(listBoard.getList(), "listBoard.getList() Should Be Not Null");
        log.info("=== End List Board Service Test ===");
    }

    // View Count Board Service Test
    @Test
    @Transactional
    @DisplayName("Service: 게시판 조회수 테스트")
    public void viewCountBoardTest() {
        // GIVEN
        log.info("=== Start View Count Board Service Test ===");
        // WHEN
        BoardDTO readBoard = boardService.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        int viewCount = readBoard.getViewCount();

        boardService.countviewBoard(JUNIT_TEST_BNO);
        // THEN
        BoardDTO afterReading = boardService.readBoard(JUNIT_TEST_BNO);
        int updatedViewCount = afterReading.getViewCount();
        Assertions.assertEquals(viewCount + 1, updatedViewCount);
        log.info("=== End View Count Board Service Test ===");
    }
}
