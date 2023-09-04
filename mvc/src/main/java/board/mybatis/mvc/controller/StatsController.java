package board.mybatis.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
import board.mybatis.mvc.util.PageRequestDTO;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("spring/stats/")
public class StatsController {

    // 의존성 주입
    private final StatsService statsService;

    // Autowired 명시적 표시
    @Autowired
    public StatsController(StatsService statsService) {
        log.info("Inject StatsService");
        this.statsService = statsService;
    }

    // GET | Stats List 
    @GetMapping("list")
    public String getListStats(PageRequestDTO pageRequestDTO, Model model) {
        log.info("GET | List Stats Controller");
        List<BoardDayEntryDTO> boardDayStats = statsService.boardDayEntryData();
        List<BoardMonthEntryDTO> boardMonthStats = statsService.boardMonthEntryData();
        List<MemberDayEntryDTO> memberDayStats = statsService.memberDayEntryData();
        List<MemberMonthEntryDTO> memberMonthStats = statsService.memberMonthEntryData();
        List<LikeDayEntryDTO> likeDayStats = statsService.likeDayEntryData();
        List<LikeMonthEntryDTO> likeMonthStats = statsService.likeMonthEntryData();
        List<ReplyDayEntryDTO> replyDayStats = statsService.replyDayEntryData();
        List<ReplyMonthEntryDTO> replyMonthStats = statsService.replyMonthEntryData();
        List<ViewsDayEntryDTO> viewsDayStats = statsService.viewsDayEntryData();
        List<ViewsMonthEntryDTO> viewsMonthStast = statsService.viewsMonthEntryData();
        model.addAttribute("boardDayStats", boardDayStats);
        model.addAttribute("boardMonthStats", boardMonthStats);
        model.addAttribute("memberDayStats", memberDayStats);
        model.addAttribute("memberMonthStats", memberMonthStats);
        model.addAttribute("likeDayStats", likeDayStats);
        model.addAttribute("likeMonthStats", likeMonthStats);
        model.addAttribute("replyDayStats", replyDayStats);
        model.addAttribute("replyMonthStats", replyMonthStats);
        model.addAttribute("viewsDayStats", viewsDayStats);
        model.addAttribute("viewsMonthStats", viewsMonthStast);
        return "spring/stats/list";
    }
}