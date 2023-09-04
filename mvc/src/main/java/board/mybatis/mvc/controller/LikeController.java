package board.mybatis.mvc.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;
import board.mybatis.mvc.service.LikeService;
import lombok.extern.log4j.Log4j2;

/**
 * {@code LikeController}는 게시물 및 공지사항 좋아요 관련 요청을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@RestController
@RequestMapping("/like/")
public class LikeController {

    private final LikeService likeService;

    /**
     * 생성자를 통한 의존성 주입
     *
     * @param likeService 좋아요 서비스
     */
    @Autowired
    public LikeController(LikeService likeService) {
        log.info("Inject LikeService");
        this.likeService = likeService;
    }

    // POST | Toggle Like Board
    @PostMapping("toggle/board/{bno}")
    public ResponseEntity<Map<String, Object>> toggleLikeBoard(@PathVariable("bno") final Long bno,
            Authentication authentication) {
        log.info("Toggle Like Board RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Long result = likeService.toggleLikeBoard(bno, email);
        Long likeCount = likeService.countLikeBoard(bno);
        return new ResponseEntity<>(Map.of("result", result, "likeCount", likeCount), HttpStatus.OK);
    }

    // GET | Count Like Board
    @GetMapping("count/board/{bno}")
    public ResponseEntity<Map<String, Object>> countLikeBoard(@PathVariable("bno") final Long bno) {
        log.info("Count Like Board RestController");
        Long result = likeService.countLikeBoard(bno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Check Like Board For Member
    @GetMapping("check/board/{bno}")
    public ResponseEntity<Map<String, Object>> checkLikeBoardMember(@PathVariable("bno") final Long bno,
            Authentication authentication) {
        log.info("Check Like Board Member RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        LikeToggleBoardDTO result = likeService.checkToggleLikeBoard(bno, email);
        return new ResponseEntity<>(Map.of("result", result != null), HttpStatus.OK);
    }

    // POST | Toggle Like Notice
    @PostMapping("toggle/notice/{nno}")
    public ResponseEntity<Map<String, Object>> toggleLikeNotice(@PathVariable("nno") final Long nno,
            Authentication authentication) {
        log.info("Toggle Like Notice RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Long result = likeService.toggleLikeNotice(nno, email);
        Long likeCount = likeService.countLikeNotice(nno);
        return new ResponseEntity<>(Map.of("result", result, "likeCount", likeCount), HttpStatus.OK);
    }

    // GET | Count Like Notice
    @GetMapping("count/notice/{nno}")
    public ResponseEntity<Map<String, Object>> countLikeNotice(@PathVariable("nno") final Long nno) {
        log.info("Count Like Notice RestController");
        Long result = likeService.countLikeNotice(nno);
        return new ResponseEntity<>(Map.of("result", result), HttpStatus.OK);
    }

    // GET | Check Like Notice For Member
    @GetMapping("check/notice/{nno}")
    public ResponseEntity<Map<String, Object>> checkLikeNoticeMember(@PathVariable("nno") final Long nno,
            Authentication authentication) {
        log.info("Check Like Notice Member RestController");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        LikeToggleNoticeDTO result = likeService.checkToggleLikeNotice(nno, email);
        return new ResponseEntity<>(Map.of("result", result != null), HttpStatus.OK);
    }
}
