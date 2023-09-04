package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

/**
 * {@code ReplyMapper}는 게시물 및 공지사항 댓글 관련 데이터베이스 작업을 위한 매퍼 인터페이스입니다.
 */
@Mapper
public interface ReplyMapper {
    /**
     * 게시물 댓글 생성
     *
     * @param replyBoardCreateDTO 생성할 댓글 정보
     * @return 생성된 댓글의 번호
     */
    Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO);

    /**
     * 게시물 대댓글 생성
     *
     * @param replyBoardCreateDTO 생성할 대댓글 정보
     * @return 생성된 대댓글의 번호
     */
    Long createBoardReplyChild(ReplyBoardCreateDTO replyBoardCreateDTO);

    /**
     * 게시물 댓글 대댓글 구분을 위한 Gno 업데이트
     *
     * @param gno 대댓글 그룹 번호
     * @param rno 댓글 번호
     * @return 업데이트된 행 수
     */
    Long updateBoardReplyGno(@Param("gno") Long gno, @Param("rno") Long rno);

    /**
     * 게시물 댓글 업데이트
     *
     * @param replyBoardUpdateDTO 업데이트할 댓글 정보
     * @return 업데이트된 행 수
     */
    Long updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO);

    /**
     * 게시물 댓글 삭제
     *
     * @param rno 댓글 번호
     * @return 삭제된 행 수
     */
    Long deleteBoardReply(Long rno);

    /**
     * 게시물 댓글 조회
     *
     * @param rno 댓글 번호
     * @return 댓글 정보
     */
    ReplyBoardDTO readBoardReply(Long rno);

    /**
     * 게시물 댓글 리스트
     *
     * @param pageRequestDTO 페이지 요청 정보
     * @param bno            게시물 번호
     * @return 댓글 리스트
     */
    List<ReplyBoardListDTO> listBoardReply(@Param("pr") PageRequestDTO pageRequestDTO, @Param("bno") Long bno);

    /**
     * 게시물 댓글 총 개수
     *
     * @param bno 게시물 번호
     * @return 댓글 총 개수
     */
    int totalBoardReply(Long bno);

    /**
     * 게시물 댓글 수 증가
     *
     * @param bno 게시물 번호
     * @return 증가된 댓글 수
     */
    Long incrementBoardReplyCount(Long bno);

    /**
     * 게시물 댓글 수 감소
     *
     * @param bno 게시물 번호
     * @return 감소된 댓글 수
     */
    Long decrementBoardReplyCount(Long bno);

    /**
     * 게시물 댓글 Rno 검증
     *
     * @param rno 댓글 번호
     * @return 검증된 댓글 번호
     */
    Long findBoardRno(Long rno);

    /**
     * 게시물 번호 검증
     *
     * @param bno 게시물 번호
     * @return 검증된 게시물 번호
     */
    Long findBoardBno(Long bno);

    /**
     * 게시물 댓글 수 카운트 조회
     *
     * @param bno 게시물 번호
     * @return 댓글 수 카운트
     */
    Long countBoardReply(Long bno);

    /**
     * 공지사항 댓글 생성
     *
     * @param replyNoticeCreateDTO 생성할 댓글 정보
     * @return 생성된 댓글의 번호
     */
    Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /**
     * 공지사항 대댓글 생성
     *
     * @param replyNoticeCreateDTO 생성할 대댓글 정보
     * @return 생성된 대댓글의 번호
     */
    Long createNoticeReplyChild(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /**
     * 공지사항 댓글 대댓글 구분을 위한 Gno 업데이트
     *
     * @param gno 대댓글 그룹 번호
     * @param rno 댓글 번호
     * @return 업데이트된 행 수
     */
    Long updateNoticeReplyGno(@Param("gno") Long gno, @Param("rno") Long rno);

    /**
     * 공지사항 댓글 업데이트
     *
     * @param replyNoticeUpdateDTO 업데이트할 댓글 정보
     * @return 업데이트된 행 수
     */
    Long updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO);

    /**
     * 공지사항 댓글 삭제
     *
     * @param rno 댓글 번호
     * @return 삭제된 행 수
     */
    Long deleteNoticeReply(Long rno);

    /**
     * 공지사항 댓글 조회
     *
     * @param rno 댓글 번호
     * @return 댓글 정보
     */
    ReplyNoticeDTO readNoticeReply(Long rno);

    /**
     * 공지사항 댓글 리스트
     *
     * @param pageRequestDTO 페이지 요청 정보
     * @param nno            공지사항 번호
     * @return 댓글 리스트
     */
    List<ReplyNoticeListDTO> listNoticeReply(@Param("pr") PageRequestDTO pageRequestDTO, @Param("nno") Long nno);

    /**
     * 공지사항 댓글 총 개수
     *
     * @param nno 공지사항 번호
     * @return 댓글 총 개수
     */
    int totalNoticeReply(Long nno);

    /**
     * 공지사항 댓글 수 증가
     *
     * @param nno 공지사항 번호
     * @return 증가된 댓글 수
     */
    Long incrementNoticeReplyCount(Long nno);

    /**
     * 공지사항 댓글 수 감소
     *
     * @param nno 공지사항 번호
     * @return 감소된 댓글 수
     */
    Long decrementNoticeReplyCount(Long nno);

    /**
     * 게시판 댓글 Rno 검증
     *
     * @param rno 댓글 번호
     * @return 검증된 댓글 번호
     */
    Long findNoticeRno(Long rno);

    /**
     * 게시판 번호 검증
     *
     * @param nno 게시판 번호
     * @return 검증된 게시판 번호
     */
    Long findNoticeNno(Long nno);

    /**
     * 공지사항 댓글 수 카운트 조회
     *
     * @param nno 공지사항 번호
     * @return 댓글 수 카운트
     */
    Long countNoticeReply(Long nno);
}