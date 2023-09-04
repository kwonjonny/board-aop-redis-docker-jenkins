package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

/**
 * 게시물 관련 서비스의 인터페이스입니다.
 */
public interface BoardService {

    /**
     * 게시물을 생성합니다.
     *
     * @param boardCreateDTO 생성할 게시물 정보를 담고 있는 DTO.
     * @return 생성된 게시물의 번호.
     */
    Long createBoard(BoardCreateDTO boardCreateDTO);

    /**
     * 게시물을 조회합니다.
     *
     * @param bno 조회할 게시물의 번호.
     * @return 조회된 게시물 정보를 담고 있는 DTO.
     */
    BoardDTO readBoard(Long bno);

    /**
     * 게시물을 업데이트합니다.
     *
     * @param boardUpdateDTO 업데이트할 게시물 정보를 담고 있는 DTO.
     * @return 업데이트된 게시물의 번호.
     */
    Long updateBoard(BoardUpdateDTO boardUpdateDTO);

    /**
     * 게시물을 삭제합니다.
     *
     * @param bno 삭제할 게시물의 번호.
     * @return 삭제된 게시물의 번호.
     */
    Long deleteBoard(Long bno);

    /**
     * 게시물 리스트를 페이지별로 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보 및 정렬 기준을 담고 있는 DTO.
     * @return 페이지별 게시물 리스트와 페이징 정보를 담고 있는 DTO.
     */
    PageResponseDTO<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    /**
     * 게시물의 조회수를 증가시킵니다.
     *
     * @param bno 조회수를 증가시킬 게시물의 번호.
     * @return 증가된 조회수 값.
     */
    int countViewBoard(Long bno);
}