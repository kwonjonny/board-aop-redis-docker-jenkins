package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

// Like Mapper Interface 
@Mapper
public interface LikeMapper {
    // Create Like Board
    Long createLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    // Delete Like Board
    Long deleteLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    // Count Like Board
    Long countLikeBoard(Long bno);

    // Toggle Check Like Board
    LikeToggleBoardDTO checkToggleLikeBoard(@Param("email") String email, @Param("bno") Long bno);

    // Create Like Notice
    Long createLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    // Delete Like Notice
    Long deleteLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    // Count Like Notice
    Long countLikeNotice(Long nno);

    // Toggle Check Like Notice
    LikeToggleNoticeDTO checkToggleLikeNotice(@Param("email") String email, @Param("nno") Long nno);

    // Find Member Email
    Long findMemberEmail(String email);

    // Find Board Number
    Long findBoardNumber(Long bno);

    // Find Notice Number 
    Long findNoticeNumber(Long nno);

    // Increment Board Like 
    Long incrementBoardLike(Long bno);

    // Decrement Board Like 
    Long decrementBoardLike(Long bno);

    // 게시판 반 정규화 좋아요 수 확인 
    Long getCurrentBoardLikeCount(Long bno);

    // Increment Notice Like 
    Long incrementNoticeLike(Long nno);

    // Decrement Notice Like 
    Long decrementNoticeLike(Long nno);

    // 공지사항 반 정규화 좋아요 수 확인 
    Long getCurrentNoticeLikeCount(Long nno);
}
