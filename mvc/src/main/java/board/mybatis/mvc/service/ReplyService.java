package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

/**
 * 게시글 및 공지사항 댓글 관련 서비스의 인터페이스입니다.
 */
public interface ReplyService {

    /**
     * 게시글에 댓글을 생성합니다.
     *
     * @param replyBoardCreateDTO 생성할 게시글 댓글 정보.
     * @return 생성된 댓글의 고유 번호.
     */
    Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO);

    /**
     * 게시글 댓글을 삭제합니다.
     *
     * @param rno 삭제할 게시글 댓글의 고유 번호.
     * @return 삭제된 댓글의 고유 번호.
     */
    Long deleteBoardReply(Long rno);

    /**
     * 게시글 댓글을 업데이트합니다.
     *
     * @param replyBoardUpdateDTO 업데이트할 게시글 댓글 정보.
     * @return 업데이트된 댓글의 고유 번호.
     */
    Long updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO);

    /**
     * 게시글 댓글을 조회합니다.
     *
     * @param rno 조회할 게시글 댓글의 고유 번호.
     * @return 조회된 게시글 댓글 정보.
     */
    ReplyBoardDTO readBoardReply(Long rno);

    /**
     * 게시글의 댓글 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보와 게시글 번호.
     * @param bno 조회할 게시글 번호.
     * @return 댓글 리스트와 페이지 정보.
     */
    PageResponseDTO<ReplyBoardListDTO> listBoardReply(PageRequestDTO pageRequestDTO, Long bno);

    /**
     * 게시글의 댓글 수를 카운트합니다.
     *
     * @param bno 게시글 번호.
     * @return 게시글의 댓글 수.
     */
    Long countBoardReply(Long bno);

    /**
     * 공지사항에 댓글을 생성합니다.
     *
     * @param replyNoticeCreateDTO 생성할 공지사항 댓글 정보.
     * @return 생성된 댓글의 고유 번호.
     */
    Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /**
     * 공지사항 댓글을 삭제합니다.
     *
     * @param rno 삭제할 공지사항 댓글의 고유 번호.
     * @return 삭제된 댓글의 고유 번호.
     */
    Long deleteNoticeReply(Long rno);
    
    /**
     * 공지사항 댓글을 업데이트합니다.
     *
     * @param replyNoticeUpdateDTO 업데이트할 공지사항 댓글 정보.
     * @return 업데이트된 댓글의 고유 번호.
     */
    Long updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO);

    /**
     * 공지사항 댓글을 조회합니다.
     *
     * @param rno 조회할 공지사항 댓글의 고유 번호.
     * @return 조회된 공지사항 댓글 정보.
     */
    ReplyNoticeDTO readNoticeReply(Long rno);

    /**
     * 공지사항의 댓글 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보와 공지사항 번호.
     * @param nno 조회할 공지사항 번호.
     * @return 댓글 리스트와 페이지 정보.
     */
    PageResponseDTO<ReplyNoticeListDTO> listNoticeReply(PageRequestDTO pageRequestDTO, Long nno);

    /**
     * 공지사항의 댓글 수를 카운트합니다.
     *
     * @param nno 공지사항 번호.
     * @return 공지사항의 댓글 수.
     */
    Long countNoticeReply(Long nno);
}