package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

// Like Service Interface
public interface LikeService {
    // Toggle Like Board
    Long toggleLikeBoard(Long bno, String email);

    // Count Like Board
    Long countLikeBoard(Long bno);

    // Check Toggle Like Board
    LikeToggleBoardDTO checkToggleLikeBoard(Long bno, String email);

    // Toggle Like Notice
    Long toggleLikeNotice(Long nno, String email);

    // Count Like Notice
    Long countLikeNotice(Long nno);

    // Check Toggle Like Notice
    LikeToggleNoticeDTO checkToggleLikeNotice(Long nno, String email);
}
