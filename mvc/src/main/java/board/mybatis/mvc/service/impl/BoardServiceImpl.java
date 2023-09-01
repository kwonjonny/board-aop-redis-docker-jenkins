package board.mybatis.mvc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.mappers.BoardMapper;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.service.BoardService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Board ServiceImpl Class
@Log4j2
@Service
public class BoardServiceImpl implements BoardService {

    // 의존성 주입
    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    /*
     * Autowired 명시적 표시 
     */
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, FileMapper fileMapper) {
        log.info("Inject BoardMapper");
        this.boardMapper = boardMapper;
        this.fileMapper = fileMapper;
    }

    /*
     * 게시물 생성 서비스 
     * 부가기능: 파일 업로드 
     */
    @Override
    @Transactional
    public Long createBoard(BoardCreateDTO boardCreateDTO) {
        log.info("Is Running Create Board Serviceimpl");
        if (boardCreateDTO.getTitle() == null || boardCreateDTO.getWriter() == null
                || boardCreateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        Long count = boardMapper.createBoard(boardCreateDTO);
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

    /*
     * 게시물 조회 서비스 
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public BoardDTO readBoard(Long bno) {
        log.info("Is Running Read Board ServiceImpl");
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        validateBoardNumber(bno); // Board Number Check
        return boardMapper.readBoard(bno);
    }

    /*
     * 게시물 업데이트 서비스 
     * 부가기능: 파일 업로드 
     */
    @Override
    @Transactional
    public Long updateBoard(BoardUpdateDTO boardUpdateDTO) {
        log.info("Is Running Update Board ServiceImpl");
        if (boardUpdateDTO.getTitle() == null || boardUpdateDTO.getWriter() == null || boardUpdateDTO.getBno() == null
                || boardUpdateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        validateBoardNumber(boardUpdateDTO.getBno()); // Check Board Number

        Long count = boardMapper.updateBoard(boardUpdateDTO);
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

    /*
     * 게시물 삭제 서비스 
     */
    @Override
    @Transactional
    public Long deleteBoard(Long bno) {
        log.info("Is Running Delete Board ServiceImpl");
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        validateBoardNumber(bno); // Board Number Check
        fileMapper.deleteImage(bno);
        return boardMapper.deleteBoard(bno);
    }

    /*
     * 게시물 리스트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Board ServiceImpl");
        List<BoardListDTO> list = boardMapper.listBoard(pageRequestDTO);
        int total = boardMapper.total(pageRequestDTO);
        if (list == null || pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 게시글 리스트가 없습니다.");
        }
        return PageResponseDTO.<BoardListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /*
     * 게시물 조회수 증가 서비스 
     */
    @Override
    @Transactional
    public int countviewBoard(Long bno) {
        log.info("Is Running Board View Count ServiceImpl");
        validateBoardNumber(bno); // Check Board Number
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시글 번호가 없습니다.");
        }
        return boardMapper.countViewBoard(bno);
    }

    /*
     * 게시물 번호 검증 서비스 
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateBoardNumber(Long bno) {
        log.info("Is Running Validate Board Number ServiceImpl");
        if (boardMapper.findBoardNumber(bno) == null || boardMapper.findBoardNumber(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
    }
}
