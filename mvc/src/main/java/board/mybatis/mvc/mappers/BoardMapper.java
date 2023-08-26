package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.mybatis.mvc.dto.BoardCreateDTO;
import board.mybatis.mvc.dto.BoardDTO;
import board.mybatis.mvc.dto.BoardListDTO;
import board.mybatis.mvc.dto.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

// Board Mapper Interface
@Mapper
public interface BoardMapper {
    // Create Board 
    Long createBoard(BoardCreateDTO boardCreateDTO);

    // Read Board 
    BoardDTO readBoard(Long bno);

    // Update Board 
    Long updateBoard(BoardUpdateDTO boardUpdateDTO);

    // Delete Board 
    Long deleteBoard(Long bno);

    // List Board 
    List<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    // Total Board 
    int total(PageRequestDTO pageRequestDTO);

    // Find Board Number 
    Long findBoardNumber(Long bno);

    // Board View Count 
    int boardViewCount(Long bno);
}
