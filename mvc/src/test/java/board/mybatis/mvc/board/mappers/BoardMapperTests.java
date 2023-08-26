package board.mybatis.mvc.board.mappers;

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

import board.mybatis.mvc.dto.BoardCreateDTO;
import board.mybatis.mvc.dto.BoardDTO;
import board.mybatis.mvc.dto.BoardListDTO;
import board.mybatis.mvc.dto.BoardUpdateDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.mappers.BoardMapper;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.util.PageRequestDTO;
import lombok.extern.log4j.Log4j2;

// Board Mapper Test Class 
@Log4j2
@SpringBootTest
public class BoardMapperTests {

    // 의존성 주입 DI
    @Autowired(required = false)
    private BoardMapper boardMapper;

    // 의존성 주입 DI
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

    // Create Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 글 생성 테스트")
    public void createBoardTest() {
        // GIVEN
        log.info("=== Start Create Board Mapper Test ===");
        // WHEN
        boardMapper.createBoard(boardCreateDTO);

        List<String> fileNames = boardCreateDTO.getFileNames();
        AtomicInteger index = new AtomicInteger(0);
        List<Map<String, String>> list = fileNames.stream().map(str -> {
            Long bno = boardCreateDTO.getBno();
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);
            return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
        }).collect(Collectors.toList());
        fileMapper.createImage(list);
        // THEN
        Assertions.assertEquals("Junit_Test_Title", boardCreateDTO.getTitle());
        Assertions.assertEquals("Junit_Test_Content", boardCreateDTO.getContent());
        Assertions.assertEquals("Junit_Test_Writer", boardCreateDTO.getWriter());
        log.info("=== End Create Board Mapper Test ===");
    }

    // Read Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 글 상세조회 테스트")
    public void readBoardTest() {
        // GIVEN
        log.info("=== Start Read Board Mapper Test ===");
        // WHEN
        BoardDTO readBoard = boardMapper.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        // THEN
        log.info(readBoard);
        Assertions.assertNotNull(readBoard, "readBoard Should Be Not Null");
        Assertions.assertEquals(readBoard.getBno(), JUNIT_TEST_BNO);
        log.info("=== End Read Board Mapper Test ===");
    }

    // Update Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 글 업데이트 테스트")
    public void updateBoardTest() {
        // GIVEN
        log.info("=== Start Update Board Mapper Test ===");
        // WHEN
        BoardDTO readBoard = boardMapper.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        Assertions.assertNotNull(readBoard, "readBoard Should Be Not Null");
        boardMapper.updateBoard(boardUpdateDTO);
        // THEN
        BoardDTO updatedboard = boardMapper.readBoard(JUNIT_TEST_BNO);
        Assertions.assertNotNull(updatedboard, "updatedBoard Should Be Not Null");
        Assertions.assertEquals("Junit_Test_Title", updatedboard.getTitle());
        Assertions.assertEquals("Junit_Test_Content", updatedboard.getContent());
        Assertions.assertEquals("Junit_Test_Writer", updatedboard.getWriter());
        log.info("=== End Update Board Mapper Test ===");
    }

    // Delete Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 글 삭제 테스트")
    public void deleteBoardTest() {
        // GIVEN
        log.info("=== Start Delete Board Mapper Test ===");
        // WHEN
        BoardDTO readBoard = boardMapper.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
        Assertions.assertNotNull(readBoard, "readBoard Should Be Not Null");
        Long deleteBoard = boardMapper.deleteBoard(JUNIT_TEST_BNO);
        // THEN
        BoardDTO deletedBoard = boardMapper.readBoard(JUNIT_TEST_BNO);
        Assertions.assertNull(deletedBoard, "deleteBoard Should Be Null");
        log.info("=== End Delete Board Mapper Test ===");
    }

    // List Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 글 리스트 테스트")
    public void listBoardTest() {
        log.info("=== Start List Board Mapper Test ===");
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        List<BoardListDTO> listBoard = boardMapper.listBoard(pageRequestDTO);
        log.info(listBoard);
        Assertions.assertNotNull(listBoard, "listBoard Should Be Not Null");
        log.info("=== End List Board Mapper Test ===");
    }

    // View Count Board Mapper Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시판 조회수 테스트")
    public void viewCountBoardTest() {
        // GIVEN
        log.info("=== Start View Count Board Mapper Test ===");
        // WHEN
        BoardDTO readBoard = boardMapper.readBoard(JUNIT_TEST_BNO);
        if (readBoard == null) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        int viewCount = readBoard.getViewCount();

        boardMapper.boardViewCount(JUNIT_TEST_BNO);
        // THEN
        BoardDTO afterReading = boardMapper.readBoard(JUNIT_TEST_BNO);
        int updatedViewCount = afterReading.getViewCount();
        Assertions.assertEquals(viewCount + 1, updatedViewCount);
        log.info("=== End View Count Board Mapper Test ===");
    }
}
