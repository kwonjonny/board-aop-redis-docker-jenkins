package board.mybatis.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.BoardCreateDTO;
import board.mybatis.mvc.dto.BoardDTO;
import board.mybatis.mvc.dto.BoardListDTO;
import board.mybatis.mvc.dto.BoardUpdateDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.mappers.BoardMapper;
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

    // Autowired 명시적 표시
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        log.info("Inject BoardMapper");
        this.boardMapper = boardMapper;
    }

    // Create Board Serviceimpl
    @Override
    @Transactional
    public Long createBoard(BoardCreateDTO boardCreateDTO) {
        log.info("Is Running Create Board Serviceimpl");
        if (boardCreateDTO.getTitle() == null || boardCreateDTO.getWriter() == null
                || boardCreateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        return boardMapper.createBoard(boardCreateDTO);
    }

    // Read Board Serviceimpl
    @Override
    @Transactional(readOnly = true)
    public BoardDTO readBoard(Long bno) {
        log.info("Is Running Read Board ServiceImpl");
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        findBoardNumber(bno); // Board Number Check
        return boardMapper.readBoard(bno);
    }

    // Update Board ServiceImpl
    @Override
    @Transactional
    public Long updateBoard(BoardUpdateDTO boardUpdateDTO) {
        log.info("Is Running Update Board ServiceImpl");
        if (boardUpdateDTO.getTitle() == null || boardUpdateDTO.getWriter() == null || boardUpdateDTO.getBno() == null
                || boardUpdateDTO.getContent() == null) {
            throw new DataNotFoundException("제목, 내용, 작성자는 필수입니다.");
        }
        findBoardNumber(boardUpdateDTO.getBno());
        return boardMapper.updateBoard(boardUpdateDTO);
    }

    // Delete Board ServiceImpl
    @Override
    @Transactional
    public Long deleteBoard(Long bno) {
        log.info("Is Running Delete Board ServiceImpl");
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        findBoardNumber(bno); // Board Number Check
        return boardMapper.deleteBoard(bno);
    }

    // List Board ServiceImpl
    @Override
    @Transactional
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

    // Find Board Number ServiceImpl
    @Override
    @Transactional(readOnly = true)
    public void findBoardNumber(Long bno) {
        log.info("Is Running Find Board Number ServiceImpl");
        if (boardMapper.findBoardNumber(bno) == null) {
            throw new BoardNumberNotFoundException("해당하는 게시글이 없습니다.");
        }
    }

    // Board View Count
    @Override
    @Transactional
    public int boardViewCount(Long bno) {
        log.info("Is Running Board View Count ServiceImpl");
        findBoardNumber(bno); // Check Board Number
        if (bno == null) {
            throw new DataNotFoundException("해당하는 게시글 번호가 없습니다.");
        }
        return boardMapper.boardViewCount(bno);
    }
}
