package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;

/**
 * {@code LikeMapper}는 게시물 및 공지사항의 좋아요에 대한 데이터베이스 작업을 처리하기 위한 매퍼 인터페이스입니다.
 * MyBatis의 Mapper 어노테이션을 사용하여 데이터베이스 연동을 수행합니다.
 */
@Mapper
public interface LikeMapper {
    /**
     * 게시물의 좋아요 생성
     *
     * @param likeToggleBoardDTO 좋아요 정보를 담은 DTO
     * @return 생성된 좋아요 정보의 ID
     */
    Long createLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    /**
     * 게시물의 좋아요 삭제
     *
     * @param likeToggleBoardDTO 좋아요 정보를 담은 DTO
     * @return 삭제된 좋아요 정보의 ID
     */
    Long deleteLikeBoard(LikeToggleBoardDTO likeToggleBoardDTO);

    /**
     * 게시물의 좋아요 카운트
     *
     * @param bno 게시물 번호
     * @return 게시물의 좋아요 개수
     */
    Long countLikeBoard(Long bno);

    /**
     * 게시물의 회원 좋아요 상태 체크
     *
     * @param email 회원 이메일
     * @param bno   게시물 번호
     * @return 회원의 게시물 좋아요 상태
     */
    LikeToggleBoardDTO checkToggleLikeBoard(@Param("email") String email, @Param("bno") Long bno);

    /**
     * 공지사항 좋아요 생성
     *
     * @param likeToggleNoticeDTO 좋아요 정보를 담은 DTO
     * @return 생성된 좋아요 정보의 ID
     */
    Long createLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    /**
     * 공지사항 좋아요 삭제
     *
     * @param likeToggleNoticeDTO 좋아요 정보를 담은 DTO
     * @return 삭제된 좋아요 정보의 ID
     */
    Long deleteLikeNotice(LikeToggleNoticeDTO likeToggleNoticeDTO);

    /**
     * 공지사항 좋아요 카운트
     *
     * @param nno 공지사항 번호
     * @return 공지사항의 좋아요 개수
     */
    Long countLikeNotice(Long nno);

    /**
     * 공지사항의 회원 좋아요 상태 체크
     *
     * @param email 회원 이메일
     * @param nno   공지사항 번호
     * @return 회원의 공지사항 좋아요 상태
     */
    LikeToggleNoticeDTO checkToggleLikeNotice(@Param("email") String email, @Param("nno") Long nno);

    /**
     * 회원 이메일 검증
     *
     * @param email 회원 이메일
     * @return 회원의 이메일 정보
     */
    Long findMemberEmail(String email);

    /**
     * 게시물 번호 검증
     *
     * @param bno 게시물 번호
     * @return 검증된 게시물 번호
     */
    Long findBoardNumber(Long bno);

    /**
     * 공지사항 번호 검증
     *
     * @param nno 공지사항 번호
     * @return 검증된 공지사항 번호
     */
    Long findNoticeNumber(Long nno);

    /**
     * 게시물 반정규화 컬럼 좋아요 개수 증가
     *
     * @param bno 게시물 번호
     * @return 증가된 좋아요 개수
     */
    Long incrementBoardLike(Long bno);

    /**
     * 게시물 반정규화 컬럼 좋아요 개수 감소
     *
     * @param bno 게시물 번호
     * @return 감소된 좋아요 개수
     */
    Long decrementBoardLike(Long bno);

    /**
     * 게시물의 현재 좋아요 개수 반정규화 컬럼 확인
     *
     * @param bno 게시물 번호
     * @return 현재 게시물의 좋아요 개수
     */
    Long getCurrentBoardLikeCount(Long bno);

    /**
     * 공지사항 반정규화 컬럼 좋아요 개수 증가
     *
     * @param nno 공지사항 번호
     * @return 증가된 좋아요 개수
     */
    Long incrementNoticeLike(Long nno);

    /**
     * 공지사항 반정규화 컬럼 좋아요 개수 감소
     *
     * @param nno 공지사항 번호
     * @return 감소된 좋아요 개수
     */
    Long decrementNoticeLike(Long nno);

    /**
     * 공지사항 현재 좋아요 개수 반정규화 컬럼 확인
     *
     * @param nno 공지사항 번호
     * @return 현재 공지사항의 좋아요 개수
     */
    Long getCurrentNoticeLikeCount(Long nno);
}