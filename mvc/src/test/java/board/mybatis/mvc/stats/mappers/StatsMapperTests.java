package board.mybatis.mvc.stats.mappers;

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
import board.mybatis.mvc.mappers.StatsMapper;
import lombok.extern.log4j.Log4j2;

// Stats Mapper Test Class
@Log4j2
@SpringBootTest
public class StatsMapperTests {

    // 의존성 주입
    @Autowired(required = false)
    private StatsMapper statsMapper;

    // Member Day Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일별 회원가입 통계 테스트")
    public void memberDayEntryDataTest() {
        log.info("=== Start Stats Member Day Entry Data Mapper Test ===");
        List<MemberDayEntryDTO> list = statsMapper.memberDayEntryData();
        log.info("회원 일별 가입: " + list);
        log.info("=== End Stats Member Day Entry Data Mapper Test ===");
    }

    // Member Month Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 월별 회원가입 통계 테스트")
    public void memberMonthEntryDataTest() {
        log.info("=== Start Stats Member Month  Entry Data Mapper Test ===");
        List<MemberMonthEntryDTO> list = statsMapper.memberMonthEntryData();
        log.info("회원 월별 가입: " + list);
        log.info("=== Start Stats Member Month Entry Data Mapper Test ===");
    }

    // Board Day Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 게시글 통계 테스트")
    public void boardDayEntryDataTest() {
        log.info("=== Start Stats Board Entry Data Mapper Test ===");
        List<BoardDayEntryDTO> list = statsMapper.boardDayEntryData();
        log.info("게시글 일별 생성: " + list);
        log.info("=== Start Stats Board Entry Data Mapper Test ===");
    }

    // Board Month Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 월 게시글 통계 테스트")
    public void boardMonthEntryDataTest() {
        log.info("=== Start Stats Board Month Entry Data Mapper Test ===");
        List<BoardMonthEntryDTO> list = statsMapper.boardMonthEntryData();
        log.info("게시글 월별 생성: " + list);
        log.info("=== Start Stats Board Month Entry Data Mapper Test ===");
    }

    // Like Day Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 게시글 라이크 통계 테스트")
    public void likeDayEntryDataTest() {
        log.info("=== Start Like Day Entry Data Mapper Test ===");
        List<LikeDayEntryDTO> list = statsMapper.likeDayEntryData();
        log.info("게시글 라이크 일별 생성: " + list);
        log.info("=== End Like Day Entry Data Mapper Test ===");
    }

    // Like Month Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 게시글 라이크 통계 테스트")
    public void likeMonthEntryDataTest() {
        log.info("=== Start Like Month Entry Data Mapper Test ===");
        List<LikeMonthEntryDTO> list = statsMapper.likeMonthEntryData();
        log.info("게시글 라이크 월별 생성: " + list);
        log.info("=== End Like Month Entry Data Mapper Test ===");
    }

    // Reply Day Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 댓글 통계 테스트")
    public void replyDayEntryDataTest() {
        log.info("=== Start Reply Day Entry Data Mapper Test ===");
        List<ReplyDayEntryDTO> list = statsMapper.replyDayEntryData();
        log.info("댓글 일별 생성: " + list);
        log.info("=== End Reply Day Entry Data Mapper Test ===");
    }

    // Reply Month Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 댓글 통계 테스트")
    public void replyMonthEntryDataTest() {
        log.info("=== Start Reply Month Entry Data Mapper Test ===");
        List<ReplyMonthEntryDTO> list = statsMapper.replyMonthEntryData();
        log.info("댓글 월별 생성: " + list);
        log.info("=== End Reply Month Entry Data Mapper Test ===");
    }

    // Views Day Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 조회수 통계 테스트")
    public void viewsDayEntryDataTest() {
        log.info("=== Start Views Day Entry Data Mapper Test ===");
        List<ViewsDayEntryDTO> list = statsMapper.viewsDayEntryData();
        log.info("조회수 일별 생성: " + list);
        log.info("=== Start Views Day Entry Data Mapper Test ===");
    }

    // Views Month Entry Test
    @Test
    @Transactional
    @DisplayName("Mapper: 일 조회수 통계 테스트")
    public void viewsMonthEntryDataTest() {
        log.info("=== Start Views Month Entry Data Mapper Test ===");
        List<ViewsMonthEntryDTO> list = statsMapper.viewsMonthEntryData();
        log.info("조회수 월별 생성: " + list);
        log.info("=== Start Views Month Entry Data Mapper Test ===");
    }
}
