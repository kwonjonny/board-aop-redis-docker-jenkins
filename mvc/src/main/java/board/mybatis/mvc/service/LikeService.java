package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

// Like Service Interface
public interface LikeService {
    /*
     * 게시물 토글 좋아요 
     */
    Long toggleLikeBoard(Long bno, String email);

    /*
     * 게시물 좋아요 카운트 
     */
    Long countLikeBoard(Long bno);

    /*
     * 게시물 좋아요 회원 상태 검증 
     */
    LikeToggleBoardDTO checkToggleLikeBoard(Long bno, String email);

    /*
     * 공지사항 토글 좋아요 
     */
    Long toggleLikeNotice(Long nno, String email);

    /*
     * 공지사항 좋아요 카운트 
     */
    Long countLikeNotice(Long nno);

    /*
     * 공지사항 좋아요 회원 상태 검증 
     */
    LikeToggleNoticeDTO checkToggleLikeNotice(Long nno, String email);
}
