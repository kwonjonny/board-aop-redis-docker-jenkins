package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

// Like Mapper Interface 
@Mapper
public interface LikeMapper {
    /*
     * 게시물의 좋아요 생성
     */
    Long createLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    /*
     * 게시물의 좋아요 삭제
     */
    Long deleteLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    /*
     * 게시물의 좋아요 카운트
     */
    Long countLikeBoard(Long bno);

    /*
     * 게시물의 회원 좋아요 상태 체크
     */
    LikeToggleBoardDTO checkToggleLikeBoard(@Param("email") String email, @Param("bno") Long bno);

    /*
     * 공지사항 좋아요 생성
     */
    Long createLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    /*
     * 공지사항 좋아요 삭제
     */
    Long deleteLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    /*
     * 공지사항 좋아요 카운트
     */
    Long countLikeNotice(Long nno);

    /*
     * 공지사항의 회원 좋아요 상태 체크
     */
    LikeToggleNoticeDTO checkToggleLikeNotice(@Param("email") String email, @Param("nno") Long nno);

    /*
     * 회원 이메일 검증
     */
    Long findMemberEmail(String email);

    /*
     * 게시물 번호 검증
     */
    Long findBoardNumber(Long bno);

    /*
     * 공지사항 번호 검증
     */
    Long findNoticeNumber(Long nno);

    /*
     * 게시물 반정규화 컬럼 좋아요 개수 증가
     */
    Long incrementBoardLike(Long bno);

    /*
     * 게시물 반정규화 컬럼 좋아요 개수 감소
     */
    Long decrementBoardLike(Long bno);

    /*
     * 게시물의 현재 좋아요 개수 반정규화 컬럼 확인
     */
    Long getCurrentBoardLikeCount(Long bno);

    /*
     * 공지사항 반정규화 컬럼 좋아요 개수 증가
     */
    Long incrementNoticeLike(Long nno);

    /*
     * 공지사항 반정규화 컬럼 좋아요 개수 감소
     */
    Long decrementNoticeLike(Long nno);

    /*
     * 공지사항 현재 좋아요 개수 반정규화 컬럼 확인
     */
    Long getCurrentNoticeLikeCount(Long nno);
}