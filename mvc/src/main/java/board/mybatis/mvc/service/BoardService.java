package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Board Service Interface 
public interface BoardService {
    // Create Board Service 
    Long createBoard(BoardCreateDTO boardCreateDTO);

    // Read Board Service 
    BoardDTO readBoard(Long bno);

    // Update Board Service 
    Long updateBoard(BoardUpdateDTO boardUpdateDTO);

    // Delete Board Service 
    Long deleteBoard(Long bno);

    // List Board Service 
    PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    // Find Board Number Service 
    void findBoardNumber(Long bno);

    // Board View Count 
    int countviewBoard(Long bno);
}
