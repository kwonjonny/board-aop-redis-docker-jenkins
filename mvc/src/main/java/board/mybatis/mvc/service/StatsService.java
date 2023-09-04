package board.mybatis.mvc.service;

import java.util.List;

import board.mybatis.mvc.dto.stats.board.BoardDayEntryDTO;
import board.mybatis.mvc.dto.stats.board.BoardMonthEntryDTO;
import board.mybatis.mvc.dto.stats.like.LikeDayEntryDTO;
import board.mybatis.mvc.dto.stats.like.LikeMonthEntryDTO;
import board.mybatis.mvc.dto.stats.member.MemberDayEntryDTO;
import board.mybatis.mvc.dto.stats.member.MemberMonthEntryDTO;
import board.mybatis.mvc.dto.stats.reply.ReplyDayEntryDTO;
import board.mybatis.mvc.dto.stats.reply.ReplyMonthEntryDTO;
import board.mybatis.mvc.dto.stats.view.ViewsDayEntryDTO;
import board.mybatis.mvc.dto.stats.view.ViewsMonthEntryDTO;

/**
 * 통계 정보 관련 서비스의 인터페이스입니다.
 */
public interface StatsService {

    /**
     * 사용자 일별 회원가입 통계를 조회합니다.
     *
     * @return 사용자 일별 회원가입 통계 리스트.
     */
    List<MemberDayEntryDTO> memberDayEntryData();

    /**
     * 사용자 월별 회원가입 통계를 조회합니다.
     *
     * @return 사용자 월별 회원가입 통계 리스트.
     */
    List<MemberMonthEntryDTO> memberMonthEntryData();

    /**
     * 게시글 일별 생성 통계를 조회합니다.
     *
     * @return 게시글 일별 생성 통계 리스트.
     */
    List<BoardDayEntryDTO> boardDayEntryData();

    /**
     * 게시글 월별 생성 통계를 조회합니다.
     *
     * @return 게시글 월별 생성 통계 리스트.
     */
    List<BoardMonthEntryDTO> boardMonthEntryData();

    /**
     * 게시글 일별 좋아요 생성 통계를 조회합니다.
     *
     * @return 게시글 일별 좋아요 생성 통계 리스트.
     */
    List<LikeDayEntryDTO> likeDayEntryData();

    /**
     * 게시글 월별 좋아요 생성 통계를 조회합니다.
     *
     * @return 게시글 월별 좋아요 생성 통계 리스트.
     */
    List<LikeMonthEntryDTO> likeMonthEntryData();

    /**
     * 댓글 일별 생성 통계를 조회합니다.
     *
     * @return 댓글 일별 생성 통계 리스트.
     */
    List<ReplyDayEntryDTO> replyDayEntryData();

    /**
     * 댓글 월별 생성 통계를 조회합니다.
     *
     * @return 댓글 월별 생성 통계 리스트.
     */
    List<ReplyMonthEntryDTO> replyMonthEntryData();

    /**
     * 조회수 일별 생성 통계를 조회합니다.
     *
     * @return 조회수 일별 생성 통계 리스트.
     */
    List<ViewsDayEntryDTO> viewsDayEntryData();

    /**
     * 조회수 월별 생성 통계를 조회합니다.
     *
     * @return 조회수 월별 생성 통계 리스트.
     */
    List<ViewsMonthEntryDTO> viewsMonthEntryData();
}