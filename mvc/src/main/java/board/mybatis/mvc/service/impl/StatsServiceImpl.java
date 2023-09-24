package board.mybatis.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import board.mybatis.mvc.mappers.StatsMapper;
import board.mybatis.mvc.service.StatsService;
import lombok.extern.log4j.Log4j2;

/**
 * 통계 서비스의 구현체.
 * 회원 가입, 게시물 생성, 좋아요, 조회수, 댓글 생성에 대한 일일 및 월간 통계 정보를 제공합니다.
 */
@Log4j2
@Service
public class StatsServiceImpl implements StatsService {

    private final StatsMapper statsMapper;

    /**
     * StatsServiceImpl 생성자.
     * statsMapper 의존성 주입을 수행합니다.
     * 
     * @param statsMapper 통계 관련 데이터 엑세스 객체
     */
    @Autowired
    public StatsServiceImpl(final StatsMapper statsMapper) {
        log.info("Inject StatsMapper");
        this.statsMapper = statsMapper;
    }

    /**
     * 회원 가입에 대한 일일 통계 정보를 반환합니다.
     * 
     * @return 일별 회원 가입 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberDayEntryDTO> memberDayEntryData() {
        log.info("Is Rrunning MemberDayEntryData ServiceImpl");
        return statsMapper.memberDayEntryData();
    }

    /**
     * 회원 가입에 대한 월간 통계 정보를 반환합니다.
     * 
     * @return 월별 회원 가입 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberMonthEntryDTO> memberMonthEntryData() {
        log.info("Is Running memberMonthEntryData ServiceImpl");
        return statsMapper.memberMonthEntryData();
    }

    /**
     * 게시물 생성에 대한 일일 통계 정보를 반환합니다.
     * 
     * @return 일별 게시물 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardDayEntryDTO> boardDayEntryData() {
        log.info("Is Running boardDayEntryData ServiceImpl");
        return statsMapper.boardDayEntryData();
    }

    /**
     * 게시물 생성에 대한 월간 통계 정보를 반환합니다.
     * 
     * @return 월별 게시물 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardMonthEntryDTO> boardMonthEntryData() {
        log.info("Is Running BoardMonthEntryData ServiceImpl");
        return statsMapper.boardMonthEntryData();
    }

    /**
     * 좋아요 생성에 대한 일일 통계 정보를 반환합니다.
     * 
     * @return 일별 좋아요 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeDayEntryDTO> likeDayEntryData() {
        log.info("Is Running LikeDayEntryData ServiceImpl");
        return statsMapper.likeDayEntryData();
    }

    /**
     * 좋아요 생성에 대한 월간 통계 정보를 반환합니다.
     * 
     * @return 월별 좋아요 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeMonthEntryDTO> likeMonthEntryData() {
        log.info("Is Running LikeMonthEntryData ServiceImpl");
        return statsMapper.likeMonthEntryData();
    }

    /**
     * 댓글 생성에 대한 일일 통계 정보를 반환합니다.
     * 
     * @return 일별 댓글 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReplyDayEntryDTO> replyDayEntryData() {
        log.info("Is Running ReplyDayEntryData ServiceImpl");
        return statsMapper.replyDayEntryData();
    }

    /**
     * 댓글 생성에 대한 월간 통계 정보를 반환합니다.
     * 
     * @return 월별 댓글 생성 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReplyMonthEntryDTO> replyMonthEntryData() {
        log.info("Is Running ReplyMonthEntryData ServiceImpl");
        return statsMapper.replyMonthEntryData();
    }

    /**
     * 조회수에 대한 일일 통계 정보를 반환합니다.
     * 
     * @return 일별 조회수 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ViewsDayEntryDTO> viewsDayEntryData() {
        log.info("Is Running ViewsDayEntryData ServiceImpl");
        return statsMapper.viewsDayEntryData();
    }

    /**
     * 조회수에 대한 월간 통계 정보를 반환합니다.
     * 
     * @return 월별 조회수 통계 목록.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ViewsMonthEntryDTO> viewsMonthEntryData() {
        log.info("Is Running ViewsMonthEntryData ServiceImpl");
        return statsMapper.viewsMonthEntryData();
    }
}