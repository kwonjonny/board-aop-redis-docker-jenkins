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

// ReplyMapper Interface
@Mapper
public interface ReplyMapper {

    /*
     * 게시판 댓글 생성
     */
    Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO);

    /*
     * 게시판 대댓글 생성
     */
    Long createBoardReplyChild(ReplyBoardCreateDTO replyBoardCreateDTO);

    /*
     * 게시판 댓글 대댓글 구분을 위한 Gno 업데이트
     */
    Long updateBoardReplyGno(@Param("gno") Long gno, @Param("rno") Long rno);

    /*
     * 게시판 댓글 업데이트
     */
    Long updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO);

    /*
     * 게시판 댓글 삭제
     */
    Long deleteBoardReply(Long rno);

    /*
     * 게시판 댓글 조회
     */
    ReplyBoardDTO readBoardReply(Long rno);

    /*
     * 게시판 댓글 리스트
     */
    List<ReplyBoardListDTO> listBoardReply(@Param("pr") PageRequestDTO pageRequestDTO, @Param("bno") Long bno);

    /*
     * 게시판 댓글 총 개수
     */
    int totalBoardReply(Long bno);

    /*
     * 게시판 댓글 수 증가
     */
    Long incrementBoardReplyCount(Long bno);

    /*
     * 게시판 댓글 수 감소
     */
    Long decrementBoardReplyCount(Long bno);

    /*
     * 게시판 댓글 Rno 검증
     */
    Long findBoardRno(Long rno);

    /*
     * 게시판 번호 검증
     */
    Long findBoardBno(Long bno);

    /*
     * 공지사항 댓글 생성
     */
    Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /*
     * 공지사항 대댓글 생성
     */
    Long createNoticeReplyChild(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /*
     * 공지사항 댓글 대댓글 구분을 위한 Gno 업데이트
     */
    Long updateNoticeReplyGno(@Param("gno") Long gno, @Param("rno") Long rno);

    /*
     * 공지사항 댓글 업데이트 
     */
    Long updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO);

    /*
     * 공지사항 댓글 삭제
     */
    Long deleteNoticeReply(Long rno);

    /*
     * 공지사항 댓글 조회
     */
    ReplyNoticeDTO readNoticeReply(Long rno);

    /*
     * 공지사항 댓글 리스트
     */
    List<ReplyNoticeListDTO> listNoticeReply(@Param("pr") PageRequestDTO pageRequestDTO, @Param("nno") Long nno);

    /*
     * 공지사항 댓글 총 개수
     */
    int totalNoticeReply(Long nno);

    /*
     * 공지사항 댓글 수 증가
     */
    Long incrementNoticeReplyCount(Long nno);

    /*
     * 공지사항 댓글 수 감소
     */
    Long decrementNoticeReplyCount(Long nno);

    /*
     * 게시판 댓글 Rno 검증
     */
    Long findNoticeRno(Long rno);

    /*
     * 게시판 번호 검증
     */
    Long findNoticeNno(Long nno);
}
