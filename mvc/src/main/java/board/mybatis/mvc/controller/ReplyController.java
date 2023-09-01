package board.mybatis.mvc.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.service.ReplyService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Reply Controller Class 
@Log4j2
@RestController
@RequestMapping("/reply/")
public class ReplyController {

    // 의존성 주입
    private final ReplyService replyService;

    // Autowired 명시적 표시
    public ReplyController(ReplyService replyService) {
        log.info("Inject ReplyService");
        this.replyService = replyService;
    }

    // GET | List Board Reply
    @GetMapping("board/list/{bno}")
    public ResponseEntity<Map<String, Object>> listBoardReply(PageRequestDTO pageRequestDTO,
            @PathVariable("bno") final Long bno) {
        log.info("Reply Board List RestController");
        PageResponseDTO<ReplyBoardListDTO> result = replyService.listBoardReply(pageRequestDTO, bno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // POST | Create Board Reply
    @PostMapping("board/create")
    public ResponseEntity<Map<String, Object>> createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO) {
        log.info("Reply Board Create RestController");
        Long result = replyService.createBoardReply(replyBoardCreateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // PUT | Update Board Reply
    @PutMapping("board/update")
    public ResponseEntity<Map<String, Object>> updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO) {
        log.info("Reply Board Update RestController");
        Long result = replyService.updateBoardReply(replyBoardUpdateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // DELETE | Delete Board Reply
    @DeleteMapping("board/delete/{rno}")
    public ResponseEntity<Map<String, Object>> deleteBoardReply(@PathVariable("rno") final Long rno) {
        log.info("Reply Board Delete RestController");
        Long result = replyService.deleteBoardReply(rno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Count Board Reply
    @GetMapping("board/count/{bno}")
    public ResponseEntity<Map<String, Object>> countBoardReply(@PathVariable("bno") final Long bno) {
        log.info("Reply Board Count RestController");
        Long result = replyService.countBoardReply(bno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | List NoticeReply
    @GetMapping("notice/list/{nno}")
    public ResponseEntity<Map<String, Object>> listNoticeReply(PageRequestDTO pageRequestDTO,
            @PathVariable("nno") final Long nno) {
        log.info("Reply Notice List RestController");
        PageResponseDTO<ReplyNoticeListDTO> result = replyService.listNoticeReply(pageRequestDTO, nno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // POST | Create Notice Reply
    @PostMapping("notice/create")
    public ResponseEntity<Map<String, Object>> createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO) {
        log.info("Reply Notice Create RestController");
        Long result = replyService.createNoticeReply(replyNoticeCreateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // PUT | Update Notice Reply
    @PutMapping("notice/update")
    public ResponseEntity<Map<String, Object>> updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO) {
        log.info("Reply Notice Update RestController");
        Long result = replyService.updateNoticeReply(replyNoticeUpdateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // DELETE | Delete Notice Reply
    @DeleteMapping("notice/delete/{rno}")
    public ResponseEntity<Map<String, Object>> deleteNoticeReply(@PathVariable("rno") final Long rno) {
        log.info("Reply Notice Delete RestController");
        Long result = replyService.deleteNoticeReply(rno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Count Notice Reply
    @GetMapping("notice/count/{nno}")
    public ResponseEntity<Map<String, Object>> countNoticeReply(@PathVariable("nno") final Long nno) {
        log.info("Reply Notice Count RestsController");
        Long result = replyService.countNoticeReply(nno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }
}