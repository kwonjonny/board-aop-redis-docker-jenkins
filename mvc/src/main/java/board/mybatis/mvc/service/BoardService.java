package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Board Service Interface 
public interface BoardService {
    /*
     * 게시물 생성 
     */
    Long createBoard(BoardCreateDTO boardCreateDTO);

    /*
     * 게시물 조회 
     */
    BoardDTO readBoard(Long bno);

    /*
     * 게시물 업데이트 
     */
    Long updateBoard(BoardUpdateDTO boardUpdateDTO);

    /*
     * 게시물 삭제 
     */ 
    Long deleteBoard(Long bno);

    /*
     * 게시물 리스트 
     */
    PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    /*
     * 게시물 조회수 증가 
     */
    int countviewBoard(Long bno);
}
