package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

/**
 * 좋아요 관련 서비스의 인터페이스입니다.
 */
public interface LikeService {

    /**
     * 게시물에 대한 좋아요 토글을 처리합니다.
     *
     * @param bno   게시물 번호.
     * @param email 회원 이메일 주소.
     * @return 좋아요 토글 후의 처리 결과를 반환합니다.
     */
    Long toggleLikeBoard(Long bno, String email);

    /**
     * 게시물에 대한 좋아요 수를 조회합니다.
     *
     * @param bno 게시물 번호.
     * @return 게시물에 대한 좋아요 수.
     */
    Long countLikeBoard(Long bno);

    /**
     * 게시물의 좋아요 상태와 회원 상태를 검증합니다.
     *
     * @param bno   게시물 번호.
     * @param email 회원 이메일 주소.
     * @return 게시물의 좋아요 상태와 회원 상태를 담은 DTO.
     */
    LikeToggleBoardDTO checkToggleLikeBoard(Long bno, String email);

    /**
     * 공지사항에 대한 좋아요 토글을 처리합니다.
     *
     * @param nno   공지사항 번호.
     * @param email 회원 이메일 주소.
     * @return 좋아요 토글 후의 처리 결과를 반환합니다.
     */
    Long toggleLikeNotice(Long nno, String email);

    /**
     * 공지사항에 대한 좋아요 수를 조회합니다.
     *
     * @param nno 공지사항 번호.
     * @return 공지사항에 대한 좋아요 수.
     */
    Long countLikeNotice(Long nno);

    /**
     * 공지사항의 좋아요 상태와 회원 상태를 검증합니다.
     *
     * @param nno   공지사항 번호.
     * @param email 회원 이메일 주소.
     * @return 공지사항의 좋아요 상태와 회원 상태를 담은 DTO.
     */
    LikeToggleNoticeDTO checkToggleLikeNotice(Long nno, String email);
}