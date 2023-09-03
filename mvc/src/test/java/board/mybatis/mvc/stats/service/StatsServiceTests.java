package board.mybatis.mvc.stats.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import board.mybatis.mvc.service.StatsService;
import lombok.extern.log4j.Log4j2;

// Stats Service Test Class
@Log4j2
@SpringBootTest
public class StatsServiceTests {

    // 의존성 주입
    @Autowired
    private StatsService statsService;

    // Member Day Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일별 회원가입 통계 테스트")
    public void memberDayEntryDataTest() {
        log.info("=== Start Stats Member Day Entry Data Service Test ===");
        List<MemberDayEntryDTO> list = statsService.memberDayEntryData();
        log.info("회원 일별 가입: " + list);
        log.info("=== End Stats Member Day Entry Data Service Test ===");
    }

    // Member Month Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 월별 회원가입 통계 테스트")
    public void memberMonthEntryDataTest() {
        log.info("=== Start Stats Member Month  Entry Data Service Test ===");
        List<MemberMonthEntryDTO> list = statsService.memberMonthEntryData();
        log.info("회원 월별 가입: " + list);
        log.info("=== Start Stats Member Month Entry Data Service Test ===");
    }

    // Board Day Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 게시글 통계 테스트")
    public void boardDayEntryDataTest() {
        log.info("=== Start Stats Board Entry Data Service Test ===");
        List<BoardDayEntryDTO> list = statsService.boardDayEntryData();
        log.info("게시글 일별 생성: " + list);
        log.info("=== Start Stats Board Entry Data Service Test ===");
    }

    // Board Month Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 월 게시글 통계 테스트")
    public void boardMonthEntryDataTest() {
        log.info("=== Start Stats Board Month Entry Data Service Test ===");
        List<BoardMonthEntryDTO> list = statsService.boardMonthEntryData();
        log.info("게시글 월별 생성: " + list);
        log.info("=== Start Stats Board Month Entry Data Service Test ===");
    }

    // Like Day Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 게시글 라이크 통계 테스트")
    public void likeDayEntryDataTest() {
        log.info("=== Start Like Day Entry Data Service Test ===");
        List<LikeDayEntryDTO> list = statsService.likeDayEntryData();
        log.info("게시글 라이크 일별 생성: " + list);
        log.info("=== End Like Day Entry Data Service Test ===");
    }

    // Like Month Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 게시글 라이크 통계 테스트")
    public void likeMonthEntryDataTest() {
        log.info("=== Start Like Month Entry Data Service Test ===");
        List<LikeMonthEntryDTO> list = statsService.likeMonthEntryData();
        log.info("게시글 라이크 월별 생성: " + list);
        log.info("=== End Like Month Entry Data Service Test ===");
    }

    // Reply Day Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 댓글 통계 테스트")
    public void replyDayEntryDataTest() {
        log.info("=== Start Reply Day Entry Data Service Test ===");
        List<ReplyDayEntryDTO> list = statsService.replyDayEntryData();
        log.info("댓글 일별 생성: " + list);
        log.info("=== End Reply Day Entry Data Service Test ===");
    }

    // Reply Month Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 댓글 통계 테스트")
    public void replyMonthEntryDataTest() {
        log.info("=== Start Reply Month Entry Data Service Test ===");
        List<ReplyMonthEntryDTO> list = statsService.replyMonthEntryData();
        log.info("댓글 월별 생성: " + list);
        log.info("=== End Reply Month Entry Data Service Test ===");
    }

    // Views Day Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 조회수 통계 테스트")
    public void viewsDayEntryDataTest() {
        log.info("=== Start Views Day Entry Data Service Test ===");
        List<ViewsDayEntryDTO> list = statsService.viewsDayEntryData();
        log.info("조회수 일별 생성: " + list);
        log.info("=== Start Views Day Entry Data Service Test ===");
    }

    // Views Month Entry Test
    @Test
    @Transactional
    @DisplayName("Service: 일 조회수 통계 테스트")
    public void viewsMonthEntryDataTest() {
        log.info("=== Start Views Month Entry Data Service Test ===");
        List<ViewsMonthEntryDTO> list = statsService.viewsMonthEntryData();
        log.info("조회수 월별 생성: " + list);
        log.info("=== Start Views Month Entry Data Service Test ===");
    }
}
