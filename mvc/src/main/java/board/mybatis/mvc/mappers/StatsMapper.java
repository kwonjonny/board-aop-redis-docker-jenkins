package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
 * {@code StatsMapper}는 통계 정보에 접근하는 매퍼 인터페이스입니다.
 */
@Mapper
public interface StatsMapper {

    /**
     * 사용자의 일별 회원가입 통계 데이터를 조회합니다.
     *
     * @return 일별 회원가입 통계 데이터 리스트
     */
    List<MemberDayEntryDTO> memberDayEntryData();

    /**
     * 사용자의 월별 회원가입 통계 데이터를 조회합니다.
     *
     * @return 월별 회원가입 통계 데이터 리스트
     */
    List<MemberMonthEntryDTO> memberMonthEntryData();

    /**
     * 게시글의 일별 생성 통계 데이터를 조회합니다.
     *
     * @return 일별 게시글 생성 통계 데이터 리스트
     */
    List<BoardDayEntryDTO> boardDayEntryData();

    /**
     * 게시글의 월별 생성 통계 데이터를 조회합니다.
     *
     * @return 월별 게시글 생성 통계 데이터 리스트
     */
    List<BoardMonthEntryDTO> boardMonthEntryData();

    /**
     * 게시글의 일별 좋아요 생성 통계 데이터를 조회합니다.
     *
     * @return 일별 게시글 좋아요 생성 통계 데이터 리스트
     */
    List<LikeDayEntryDTO> likeDayEntryData();

    /**
     * 게시글의 월별 좋아요 생성 통계 데이터를 조회합니다.
     *
     * @return 월별 게시글 좋아요 생성 통계 데이터 리스트
     */
    List<LikeMonthEntryDTO> likeMonthEntryData();

    /**
     * 댓글의 일별 생성 통계 데이터를 조회합니다.
     *
     * @return 일별 댓글 생성 통계 데이터 리스트
     */
    List<ReplyDayEntryDTO> replyDayEntryData();

    /**
     * 댓글의 월별 생성 통계 데이터를 조회합니다.
     *
     * @return 월별 댓글 생성 통계 데이터 리스트
     */
    List<ReplyMonthEntryDTO> replyMonthEntryData();

    /**
     * 조회수의 일별 생성 통계 데이터를 조회합니다.
     *
     * @return 일별 조회수 생성 통계 데이터 리스트
     */
    List<ViewsDayEntryDTO> viewsDayEntryData();

    /**
     * 조회수의 월별 생성 통계 데이터를 조회합니다.
     *
     * @return 월별 조회수 생성 통계 데이터 리스트
     */
    List<ViewsMonthEntryDTO> viewsMonthEntryData();
}