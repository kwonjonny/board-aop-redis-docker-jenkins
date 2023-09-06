package board.mybatis.mvc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.service.ReplyService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;

/**
 * {@code ReplyController}는 댓글 관련 기능을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@RestController
@RequestMapping("/reply/")
@Tag(name = "Reply API", description = "댓글과 대댓글 관련된 모든 API")
public class ReplyController {

    private final ReplyService replyService;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param replyService 댓글 서비스
     */
    @Autowired
    public ReplyController(ReplyService replyService) {
        log.info("Inject ReplyService");
        this.replyService = replyService;
    }

    // GET | List Board Reply
    @GetMapping("board/list/{bno}")
    @Operation(summary = "게시판 댓글 목록 조회", description = "게시판의 댓글 목록을 조회하는 API")
    public ResponseEntity<Map<String, Object>> listBoardReply(PageRequestDTO pageRequestDTO,
            @Parameter(description = "게시판 번호", required = true) @PathVariable("bno") final Long bno) {
        log.info("GET | Reply Board List RestController");
        PageResponseDTO<ReplyBoardListDTO> result = replyService.listBoardReply(pageRequestDTO, bno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // POST | Create Board Reply
    @PostMapping("board/create")
    @Operation(summary = "게시판 댓글 생성", description = "게시판에 댓글을 추가하는 API")
    public ResponseEntity<Map<String, Object>> createBoardReply(
            @Parameter(description = "댓글 생성 정보", required = true) @RequestBody ReplyBoardCreateDTO replyBoardCreateDTO,
            Authentication authentication) {
        log.info("POST | Reply Board Create RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        replyBoardCreateDTO.setReplyer(userDetails.getUsername());
        Long result = replyService.createBoardReply(replyBoardCreateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // PUT | Update Board Reply
    @PutMapping("board/update")
    @Operation(summary = "게시판 댓글 업데이트", description = "특정 게시판의 댓글을 업데이트합니다.")
    public ResponseEntity<Map<String, Object>> updateBoardReply(
            @Parameter(description = "업데이트할 댓글 정보", required = true) @RequestBody ReplyBoardUpdateDTO replyBoardUpdateDTO,
            @Parameter(description = "사용자 인증 정보", required = true) Authentication authentication) {
        log.info("PUT | Reply Board Update RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        replyBoardUpdateDTO.setReplyer(userDetails.getUsername());
        Long result = replyService.updateBoardReply(replyBoardUpdateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // DELETE | Delete Board Reply
    @DeleteMapping("board/delete/{rno}")
    @Operation(summary = "게시판 댓글 삭제", description = "특정 게시판의 댓글을 삭제합니다.")
    public ResponseEntity<Map<String, Object>> deleteBoardReply(
            @Parameter(description = "댓글 번호", required = true) @PathVariable("rno") final Long rno) {
        log.info("DELTE | Reply Board Delete RestController");
        Long result = replyService.deleteBoardReply(rno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Count Board Reply
    @GetMapping("board/count/{bno}")
    @Operation(summary = "게시판 댓글 수 조회", description = "특정 게시판의 댓글 수를 조회합니다.")
    public ResponseEntity<Map<String, Object>> countBoardReply(
            @Parameter(description = "게시판 번호", required = true) @PathVariable("bno") final Long bno) {
        log.info("GET | Reply Board Count RestController");
        Long result = replyService.countBoardReply(bno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Get User Details
    @GetMapping("board/user/email")
    @Operation(summary = "사용자 이메일 조회", description = "인증된 사용자의 이메일을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getBoardUserDetails(Authentication authentication) {
        log.info("GET | Reply Board User Details RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return new ResponseEntity<>(Map.of("email", email), HttpStatus.OK);
    }

    // GET | List Notice Reply
    @GetMapping("notice/list/{nno}")
    @Operation(summary = "공지사항 댓글 목록 조회", description = "특정 공지사항의 댓글 목록을 조회합니다.")
    public ResponseEntity<Map<String, Object>> listNoticeReply(
            @Parameter(description = "페이지 정보") PageRequestDTO pageRequestDTO,
            @Parameter(description = "공지사항 번호", required = true) @PathVariable("nno") final Long nno) {
        log.info("GET | Reply Notice List RestController");
        PageResponseDTO<ReplyNoticeListDTO> result = replyService.listNoticeReply(pageRequestDTO, nno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // POST | Create Notice Reply
    @PostMapping("notice/create")
    @Operation(summary = "공지사항 댓글 생성", description = "새로운 공지사항 댓글을 생성합니다.")
    public ResponseEntity<Map<String, Object>> createNoticeReply(
            @RequestBody ReplyNoticeCreateDTO replyNoticeCreateDTO, Authentication authentication) {
        log.info("POST | Reply Notice Create RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        replyNoticeCreateDTO.setReplyer(userDetails.getUsername());
        Long result = replyService.createNoticeReply(replyNoticeCreateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // PUT | Update Notice Reply
    @PutMapping("notice/update")
    @Operation(summary = "공지사항 댓글 수정", description = "기존 공지사항 댓글을 수정합니다.")
    public ResponseEntity<Map<String, Object>> updateNoticeReply(
            @RequestBody ReplyNoticeUpdateDTO replyNoticeUpdateDTO, Authentication authentication) {
        log.info("PUT | Reply Notice Update RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        replyNoticeUpdateDTO.setReplyer(userDetails.getUsername());
        Long result = replyService.updateNoticeReply(replyNoticeUpdateDTO);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // DELETE | Delete Notice Reply
    @DeleteMapping("notice/delete/{rno}")
    @Operation(summary = "공지사항 댓글 삭제", description = "특정 공지사항 댓글을 삭제합니다.")
    public ResponseEntity<Map<String, Object>> deleteNoticeReply(
            @Parameter(description = "댓글 번호", required = true) @PathVariable("rno") final Long rno) {
        log.info("DELETE | Reply Notice Delete RestController");
        Long result = replyService.deleteNoticeReply(rno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Count Notice Reply
    @GetMapping("notice/count/{nno}")
    @Operation(summary = "공지사항 댓글 수 조회", description = "특정 공지사항의 댓글 수를 조회합니다.")
    public ResponseEntity<Map<String, Object>> countNoticeReply(
            @Parameter(description = "공지사항 번호", required = true) @PathVariable("nno") final Long nno) {
        log.info("GET | Reply Notice Count RestController");
        Long result = replyService.countNoticeReply(nno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Get Notice User Details
    @GetMapping("notice/user/email")
    @Operation(summary = "사용자 이메일 조회", description = "인증된 사용자의 이메일을 조회합니다.")
    public ResponseEntity<Map<String, Object>> getNoticeUserDetails(Authentication authentication) {
        log.info("GET | Reply Notice User Details RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        return new ResponseEntity<>(Map.of("email", email), HttpStatus.OK);
    }
}