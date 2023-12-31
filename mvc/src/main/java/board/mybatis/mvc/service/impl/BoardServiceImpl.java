package board.mybatis.mvc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.annotation.redis.KwonCacheEvict;
import board.mybatis.mvc.annotation.redis.KwonCacheable;
import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.mappers.BoardMapper;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

/**
 * 게시판 서비스 구현 클래스.
 * 게시판에 대한 CRUD 관련 서비스를 제공합니다.
 */
@Log4j2
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    /**
     * BoardServiceImpl 생성자.
     * boardMapper 의존성 주입을 수행합니다.
     * fileMapper 의존성 주입을 수행합니다.
     * 
     * @param boardMapper 게시물 관련 데이터 엑세스 객체
     * @param fileMapper  파일 업로드 관련 데이터 엑세스 객체
     */
    @Autowired
    public BoardServiceImpl(final BoardMapper boardMapper, final FileMapper fileMapper) {
        log.info("Inject BoardMapper");
        this.boardMapper = boardMapper;
        this.fileMapper = fileMapper;
    }

    /**
     * 게시물 생성 서비스 메서드.
     * 부가 기능: 파일 업로드.
     *
     * @param boardCreateDTO 게시판 생성 정보 DTO
     * @return 생성된 게시물의 번호
     * @throws DataNotFoundException 필수 정보가 누락될 경우 발생
     */
    @Override
    @Transactional
    public Long createBoard(final BoardCreateDTO boardCreateDTO) {
        log.info("Is Running Create Board Serviceimpl");
        if (boardCreateDTO.getTitle() == null || boardCreateDTO.getWriter() == null
                || boardCreateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        boardMapper.createBoard(boardCreateDTO);
        AtomicInteger index = new AtomicInteger(0);
        List<String> fileNames = boardCreateDTO.getFileName();
        Long bno = boardCreateDTO.getBno();

        if (!boardCreateDTO.getFileName().isEmpty() && boardCreateDTO.getFileName() != null) {
            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String[] splitData = str.split("_"); // "_"를 기준으로 문자열을 분리
                String uuid = splitData[0];
                String fileName = splitData[1];
                return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());
            fileMapper.createImage(list);
        }
        return boardCreateDTO.getBno();
    }

    /**
     * 게시물 조회 서비스 메서드.
     * 해당 게시물 정보는 캐시에서 조회하며, 캐시에 없는 경우 DB에서 가져와 캐시에 저장합니다.
     * 캐시 키 생성은 "Key_Generator"를 사용합니다.
     * 
     * @param bno 조회할 게시물 번호
     * @return 조회된 게시물 정보
     * @throws DataNotFoundException        해당하는 게시물 번호가 없을 경우 발생
     * @throws BoardNumberNotFoundException 해당 번호의 게시물이 없을 경우 발생
     */
    @Override
    @Transactional(readOnly = true)
    @KwonCacheable(value = "Board", key = "")
    public BoardDTO readBoard(final Long bno) {
        log.info("Is Running Read Board ServiceImpl");
        validateBoardNumber(bno);
        return boardMapper.readBoard(bno);
    }

    /**
     * 게시물 수정 서비스 메서드.
     * 부가 기능: 파일업로드.
     * 해당 게시물 정보는 캐시에서 수정됩니다. 수정된 정보로 캐시 내용이 갱신되며, "Key_Generator"를 사용하여 캐시 키를
     * 생성합니다.
     *
     * @param boardUpdateDTO 게시판 수정 정보 DTO
     * @return 수정된 게시물의 번호
     * @throws DataNotFoundException        제목, 내용, 작성자가 없을 경우 발생
     * @throws BoardNumberNotFoundException 해당 번호의 게시물이 없을 경우 발생
     */
    @Override
    @Transactional
    @KwonCacheEvict(value = "Board", key = "")
    public Long updateBoard(final BoardUpdateDTO boardUpdateDTO) {
        log.info("Is Running Update Board ServiceImpl");
        if (boardUpdateDTO.getTitle() == null || boardUpdateDTO.getWriter() == null || boardUpdateDTO.getBno() == null
                || boardUpdateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        validateBoardNumber(boardUpdateDTO.getBno());

        boardMapper.updateBoard(boardUpdateDTO);
        AtomicInteger index = new AtomicInteger(0);
        List<String> fileNames = boardUpdateDTO.getFileName();
        Long bno = boardUpdateDTO.getBno();

        fileMapper.deleteImage(bno);
        if (!boardUpdateDTO.getFileName().isEmpty() && boardUpdateDTO.getFileName() != null) {
            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String[] splitData = str.split("_"); // "_"를 기준으로 문자열을 분리
                String uuid = splitData[0];
                String fileName = splitData[1];
                return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());
            fileMapper.updateImage(list);
        }
        return boardUpdateDTO.getBno();
    }

    /**
     * 게시물 삭제 서비스 메서드.
     * 해당 게시물 정보는 캐시에서 제거됩니다. "Key_Generator"를 사용하여 캐시 키를 생성하여 해당 게시물 정보를 캐시에서
     * 제거합니다.
     *
     * @param bno 삭제할 게시물 번호
     * @return 삭제된 게시물의 번호
     * @throws DataNotFoundException        해당하는 게시물 번호가 없을 경우 발생
     * @throws BoardNumberNotFoundException 해당 번호의 게시물이 없을 경우 발생
     */
    @Override
    @Transactional
    @KwonCacheEvict(value = "Board", key = "")
    public Long deleteBoard(final Long bno) {
        log.info("Is Running Delete Board ServiceImpl");
        validateBoardNumber(bno);
        fileMapper.deleteImage(bno);
        return boardMapper.deleteBoard(bno);
    }

    /**
     * 게시물 목록 조회 서비스 메서드.
     *
     * @param pageRequestDTO 페이지 요청 정보 DTO
     * @return 게시물 목록과 페이징 정보
     * @throws DataNotFoundException 해당하는 게시글 리스트가 없을 경우 발생
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<BoardListDTO> listBoard(final PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Board ServiceImpl");
        List<BoardListDTO> list = boardMapper.listBoard(pageRequestDTO);
        int total = boardMapper.total(pageRequestDTO);
        return PageResponseDTO.<BoardListDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .list(list)
                .total(total)
                .build();
    }

    /**
     * 게시물 조회수 증가 서비스 메서드.
     *
     * @param bno 조회수를 증가시킬 게시물 번호
     * @return 증가된 조회수
     * @throws DataNotFoundException        해당하는 게시글 번호가 없을 경우 발생
     * @throws BoardNumberNotFoundException 해당 번호의 게시물이 없을 경우 발생
     */
    @Override
    @Transactional
    public int countViewBoard(final Long bno) {
        log.info("Is Running Board View Count ServiceImpl");
        validateBoardNumber(bno);
        boardMapper.createViewBoardCount(bno);
        return boardMapper.countViewBoard(bno);
    }

    /**
     * 게시물 번호 검증 서비스 메서드.
     *
     * @param bno 검증할 게시물 번호
     * @throws BoardNumberNotFoundException 해당 번호의 게시물이 없을 경우 발생
     */
    @Transactional(readOnly = true)
    private void validateBoardNumber(final Long bno) {
        log.info("Is Running Validate Board Number ServiceImpl");
        if (boardMapper.findBoardNumber(bno) == null || boardMapper.findBoardNumber(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
    }
}