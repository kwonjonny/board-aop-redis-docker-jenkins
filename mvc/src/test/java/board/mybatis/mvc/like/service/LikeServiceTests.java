package board.mybatis.mvc.like.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.LikeMapper;
import board.mybatis.mvc.service.LikeService;
import lombok.extern.log4j.Log4j2;

// Like Service Test Class 
@Log4j2
@SpringBootTest
public class LikeServiceTests {

    // 의존성 주입
    @Autowired
    private LikeService likeService;

    @Autowired
    private LikeMapper likeMapper;

    // 테스트 시작시 메모리 선 참조
    private static final String JUNIT_TEST_EMAIL = "thistrik@naver.com";
    private static final Long JUNIT_TEST_BNO = 2L;
    private static final Long JUNIT_TEST_NNO = 101L;

    // BeforeEach 사용을 위한 DTO 정의
    private LikeToggleBoardDTO likeToggleBoardDTO;
    private LikeToggleNoticeDTO likeToggleNoticeDTO;

    // BeforeEach 사용
    @BeforeEach
    public void setUp() {
        likeToggleBoardDTO = LikeToggleBoardDTO.builder()
                .bno(JUNIT_TEST_BNO)
                .email(JUNIT_TEST_EMAIL)
                .build();

        likeToggleNoticeDTO = LikeToggleNoticeDTO.builder()
                .nno(JUNIT_TEST_NNO)
                .email(JUNIT_TEST_EMAIL)
                .build();
    }

    // Toggle Like Board Test
    @Test
    @Transactional
    @DisplayName("Service: 게시물 토글 좋아요 삭제, 좋아요 생성 테스트")
    public void toggleLikeBoardTest() {
        // GIVEN
        log.info("=== Start Toggle Like Board Service Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0) {
            throw new BoardNumberNotFoundException("요청하신 게시물 번호는 없는 번호입니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("요청하신 이메일의 형식이 올바르지 않습니다.");
        }
        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 이메일입니다.");
        }
        Long togglelike = likeService.toggleLikeBoard(JUNIT_TEST_BNO, JUNIT_TEST_EMAIL);
        // THEN
        Assertions.assertEquals(togglelike, 1, "toggleLike Should Be Return 1");
        log.info("=== End Toggle Like Board Service Test ===");
    }

    // Count Like Board Test
    @Test
    @Transactional
    @DisplayName("Service: 게시물 좋아요 카운트 테스트")
    public void countLikeBoardTest() {
        // GIVEN
        log.info("=== Start Count Like Board Service Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0 || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null) {
            throw new BoardNumberNotFoundException("요청하신 게시물 번호는 없는 번호입니다.");
        }
        Long countLike = likeService.countLikeBoard(JUNIT_TEST_BNO);
        // THEN
        log.info(JUNIT_TEST_BNO + " 게시물 의 좋아요 개수: " + countLike);
        Assertions.assertNotNull(countLike, "countLike Should Be Not Null");
        log.info("=== End COunt Like Board Service Test ===");
    }

    // Check Toggle Like Board Test
    @Test
    @Transactional
    @DisplayName("Service: 게시물 좋아요 토글 체크 테스트")
    public void checkToggleLikeBoardTest() {
        // GIVEN
        log.info("=== Start Check Toggle Like Board Service Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0 || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0) {
            throw new BoardNumberNotFoundException("요청하신 게시물 번호는 없는 번호입니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("요청하신 이메일의 형식이 올바르지 않습니다.");
        }
        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0 || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 이메일입니다.");
        }
        LikeToggleBoardDTO likeToggleCheck = likeService.checkToggleLikeBoard(JUNIT_TEST_BNO, JUNIT_TEST_EMAIL);
        // THEN
        log.info("Like InforMation: " + likeToggleCheck);
        Assertions.assertNotNull(likeToggleCheck, "likeToggleCheck Should Be Not Null");
        log.info("=== End Check Toggle Like Board Service Test ===");
    }

    // Toggle Like Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 게시글 토글 좋아요 삭제, 좋아요 생성 테스트")
    public void toggleLikeNoticeTest() {
        // GIVEN
        log.info("=== Start Toggle Like Notice Service Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0) {
            throw new NoticeNumberNotFoundException("요청하신 공지사항 번호은 없는 번호입니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("요청하신 이메일의 형식이 올바르지 않습니다.");
        }
        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 이메일입니다.");
        }
        Long toggleLike = likeService.toggleLikeNotice(JUNIT_TEST_NNO, JUNIT_TEST_EMAIL);
        // THEN
        Assertions.assertEquals(toggleLike, 1, "toggleLike Should Be Return 1");
        log.info("=== End Toggle Like Notice Service Test ===");
    }

    // Count Like Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 좋아요 카운트 테스트")
    public void countLikeNoticeTest() {
        // GIVEN
        log.info("=== Start Count Like Notice Service Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0 || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null) {
            throw new NoticeNumberNotFoundException("요청하신 공지사항 번호는 없는 번호입니다.");
        }
        Long countLikeNotice = likeService.countLikeNotice(JUNIT_TEST_NNO);
        // THEN
        log.info(JUNIT_TEST_NNO + " 게시물의 좋아요 개수: " + countLikeNotice);
        Assertions.assertNotNull(countLikeNotice, "countLikeNotice Should Be Not Null");
        log.info("=== End Count Like Notice Service Test ===");
    }

    // Check Toggle Like Notice Test
    @Test
    @Transactional
    @DisplayName("Service: 공지사항 토글 좋아요 체크 테스트")
    public void checkToggleLikeNoticeTest() {
        // GIVEN
        log.info("=== Start Toggle Like Notice Check Service Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0 || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null) {
            throw new NoticeNumberNotFoundException("요청하신 공지사항 번호는 없는 번호입니다.");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(JUNIT_TEST_EMAIL);
        if (!matcher.matches()) {
            throw new InvalidEmailException("요청하신 이메일의 형식이 올바르지 않습니다.");
        }
        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0 || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 이메일 입니다.");
        }
        LikeToggleNoticeDTO likeToggleNoticeCheck = likeService.checkToggleLikeNotice(JUNIT_TEST_NNO, JUNIT_TEST_EMAIL);
        log.info("Notice Like InforMation: " + likeToggleNoticeCheck);
        Assertions.assertNotNull(likeToggleNoticeCheck, "likeToggleNoticeCheck Should Be Not Null");
        log.info("=== End Toggle Like Notice Check Service Test ===");
    }
}
