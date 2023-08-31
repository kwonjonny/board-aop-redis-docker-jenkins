package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

// Board Mapper Interface
@Mapper
public interface BoardMapper {
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
    List<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    /*
     * 게시물 총 개수
     */
    int total(PageRequestDTO pageRequestDTO);

    /*
     * 게시물 번호 검증
     */
    Long findBoardNumber(Long bno);

    /*
     * 게시물 조회수 증가
     */
    int countViewBoard(Long bno);
}