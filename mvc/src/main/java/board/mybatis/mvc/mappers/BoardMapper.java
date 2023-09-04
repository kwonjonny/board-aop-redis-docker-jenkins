package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.mybatis.mvc.dto.board.BoardCreateDTO;
import board.mybatis.mvc.dto.board.BoardDTO;
import board.mybatis.mvc.dto.board.BoardListDTO;
import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

/**
 * {@code BoardMapper}는 게시물에 대한 데이터베이스 작업을 처리하기 위한 매퍼 인터페이스입니다.
 * MyBatis의 Mapper 어노테이션을 사용하여 데이터베이스 연동을 수행합니다.
 */
@Mapper
public interface BoardMapper {
    /**
     * 게시물 생성
     *
     * @param boardCreateDTO 게시물 생성에 필요한 정보를 담은 DTO
     * @return 생성된 게시물의 번호
     */
    Long createBoard(BoardCreateDTO boardCreateDTO);

    /**
     * 게시물 조회
     *
     * @param bno 게시물 번호
     * @return 조회된 게시물 정보를 담은 DTO
     */
    BoardDTO readBoard(Long bno);

    /**
     * 게시물 업데이트
     *
     * @param boardUpdateDTO 게시물 업데이트에 필요한 정보를 담은 DTO
     * @return 업데이트된 게시물의 번호
     */
    Long updateBoard(BoardUpdateDTO boardUpdateDTO);

    /**
     * 게시물 삭제
     *
     * @param bno 게시물 번호
     * @return 삭제된 게시물의 번호
     */
    Long deleteBoard(Long bno);

    /**
     * 게시물 리스트 조회
     *
     * @param pageRequestDTO 페이지 요청 DTO
     * @return 게시물 리스트 정보를 담은 리스트
     */
    List<BoardListDTO> listBoard(PageRequestDTO pageRequestDTO);

    /**
     * 게시물 총 개수 조회
     *
     * @param pageRequestDTO 페이지 요청 DTO
     * @return 게시물 총 개수
     */
    int total(PageRequestDTO pageRequestDTO);

    /**
     * 게시물 번호 검증
     *
     * @param bno 게시물 번호
     * @return 해당 게시물 번호
     */
    Long findBoardNumber(Long bno);

    /**
     * 게시물 조회수 증가
     *
     * @param bno 게시물 번호
     * @return 업데이트된 조회수 값
     */
    int countViewBoard(Long bno);

    /**
     * 조회수 테이블에 조회수 생성
     *
     * @param bno 게시물 번호
     * @return 생성된 조회수 테이블의 번호
     */
    Long createViewBoardCount(Long bno);
}