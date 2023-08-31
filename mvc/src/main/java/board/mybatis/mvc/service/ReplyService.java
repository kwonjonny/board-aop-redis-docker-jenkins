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

// ReplyService Interface
public interface ReplyService {
    /*
     * 게시판 댓글 생성 
     */
    Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO);

    /*
     * 게시판 댓글 삭제 
     */
    Long deleteBoardReply(Long rno);

    /*
     * 게시판 댓글 업데이트 
     */
    Long updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO);

    /*
     * 게시판 댓글 조회 
     */
    ReplyBoardDTO readBoardReply(Long rno);

    /*
     * 게시판 댓글 리스트 
     */
    PageResponseDTO<ReplyBoardListDTO> listBoardReply(PageRequestDTO pageRequestDTO, Long bno);

    /*
     * 공지사항 댓글 생성 
     */
    Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO);

    /*
     * 공지사항 댓글 삭제 
     */
    Long deleteNoticeReply(Long rno);
    
    /*
     * 공지사항 댓글 업데이트 
     */
    Long updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO);

    /*
     * 공지사항 댓글 조회 
     */
    ReplyNoticeDTO readNoticeReply(Long rno);

    /*
     * 공지사항 댓글 리스트
     */
    PageResponseDTO<ReplyNoticeListDTO> listNoticeReply(PageRequestDTO pageRequestDTO, Long nno);
}
