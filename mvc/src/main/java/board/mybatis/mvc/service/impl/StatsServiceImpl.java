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

// Stats ServiceImpl Class 
@Log4j2
@Service
public class StatsServiceImpl implements StatsService {

    // 의존성 주입
    private final StatsMapper statsMapper;

    /*
     * Autowired 명시적 표시
     */
    @Autowired
    public StatsServiceImpl(StatsMapper statsMapper) {
        log.info("Inject StatsMapper");
        this.statsMapper = statsMapper;
    }

    /*
     * 회원 일 가입 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberDayEntryDTO> memberDayEntryData() {
        log.info("Is Rrunning MemberDayEntryData ServiceImpl");
        return statsMapper.memberDayEntryData();
    }

    /*
     * 회원 월 가입 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberMonthEntryDTO> memberMonthEntryData() {
        log.info("Is Running memberMonthEntryData ServiceImpl");
        return statsMapper.memberMonthEntryData();
    }

    /*
     * 게시물 일 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardDayEntryDTO> boardDayEntryData() {
        log.info("Is Running boardDayEntryData ServiceImpl");
        return statsMapper.boardDayEntryData();
    }

    /*
     * 게시물 월 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<BoardMonthEntryDTO> boardMonthEntryData() {
        log.info("Is Running BoardMonthEntryData ServiceImpl");
        return statsMapper.boardMonthEntryData();
    }

    /*
     * 라이크 일 생성 통계 서비스
     * 트랜잭션 ReadOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeDayEntryDTO> likeDayEntryData() {
        log.info("Is Running LikeDayEntryData ServiceImpl");
        return statsMapper.likeDayEntryData();
    }

    /*
     * 라이크 월 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<LikeMonthEntryDTO> likeMonthEntryData() {
        log.info("Is Running LikeMonthEntryData ServiceImpl");
        return statsMapper.likeMonthEntryData();
    }

    /*
     * 댓글 일 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReplyDayEntryDTO> replyDayEntryData() {
        log.info("Is Running ReplyDayEntryData ServiceImpl");
        return statsMapper.replyDayEntryData();
    }

    /*
     * 댓글 월 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReplyMonthEntryDTO> replyMonthEntryData() {
        log.info("Is Running ReplyMonthEntryData ServiceImpl");
        return statsMapper.replyMonthEntryData();
    }

    /*
     * 조회수 일 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<ViewsDayEntryDTO> viewsDayEntryData() {
        log.info("Is Running ViewsDayEntryData ServiceImpl");
        return statsMapper.viewsDayEntryData();
    }

    /*
     * 조회수 월 생성 통계 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public List<ViewsMonthEntryDTO> viewsMonthEntryData() {
        log.info("Is Running ViewsMonthEntryData ServiceImpl");
        return statsMapper.viewsMonthEntryData();
    }
}