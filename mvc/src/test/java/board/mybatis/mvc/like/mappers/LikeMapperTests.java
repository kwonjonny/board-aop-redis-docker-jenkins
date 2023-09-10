package board.mybatis.mvc.like.mappers;

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
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.LikeMapper;
import lombok.extern.log4j.Log4j2;

// Like Mapper Test Class
@Log4j2
@SpringBootTest
public class LikeMapperTests {

    // 의존성 주입
    @Autowired(required = false)
    private LikeMapper likeMapper;

    // 테스트 시작 시 메모리 선 참조
    private static final Long JUNIT_TEST_BNO = 2L;
    private static final Long JUNIT_TEST_NNO = 101L;
    private static final String JUNIT_TEST_EMAIL = "thistrik@naver.com";

    // BeforeEach 사용을 위한 DTO 정의
    private LikeToggleBoardDTO likeToggleBoardDTO;
    private LikeToggleNoticeDTO likeToggleNoticeDTO;

    // BeforeEach
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

    // Create Board Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 좋아요 생성 테스트")
    public void createLikeTest() {
        // GIVEN
        log.info("=== Start Create Like Mapper Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(likeToggleBoardDTO.getBno()) == null
                || likeMapper.findBoardNumber(likeToggleBoardDTO.getBno()) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }

        if (likeMapper.findMemberEmail(likeToggleBoardDTO.getEmail()) == 0
                || likeMapper.findMemberEmail(likeToggleBoardDTO.getEmail()) == null) {
            throw new MemberNotFoundException("해당하는 회원 이메일이 없습니다.");
        }
        LikeToggleBoardDTO beforeLike = likeMapper.checkToggleLikeBoard(likeToggleBoardDTO.getEmail(),
                likeToggleBoardDTO.getBno());
        if (beforeLike == null) {
            Long craeteLike = likeMapper.createLikeBoard(likeToggleBoardDTO);
            Assertions.assertNotNull(craeteLike, "createLike Should Be Not Null");
        }
        // THEN
        Assertions.assertEquals(likeToggleBoardDTO.getBno(), JUNIT_TEST_BNO);
        Assertions.assertEquals(likeToggleBoardDTO.getEmail(), JUNIT_TEST_EMAIL);
        log.info("=== End Create Like Mapper Test ===");
    }

    // Delete Board Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 좋아요 삭제 테스트")
    public void deleteLikeTest() {
        // GIVEN
        log.info("=== Start Delete Like Mapper Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }

        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0) {
            throw new MemberNotFoundException("해당하는 회원 이메일이 없습니다.");
        }
        Long deleteLike = likeMapper.deleteLikeBoard(likeToggleBoardDTO);
        // THEN
        LikeToggleBoardDTO afterLike = likeMapper.checkToggleLikeBoard(JUNIT_TEST_EMAIL, JUNIT_TEST_BNO);
        Assertions.assertNull(afterLike, "afterLike Should Be Null");
        log.info("=== Start Delete Like Mapper Test ===");
    }

    // Count Board Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 좋아요 카운트 테스트")
    public void countLikeTest() {
        // GIVEN
        log.info("=== Start Count Like Mapper Test ===");
        // WHEN
        Long countLike = likeMapper.countLikeBoard(JUNIT_TEST_BNO);
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        // THEN
        log.info(JUNIT_TEST_BNO + " 번 게시물 좋아요 개수: " + countLike);
        Assertions.assertNotNull(countLike, "countLike Should Be Not Null");
        log.info("=== End Count Like Mapper Test ===");
    }

    // Create Notice Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 좋아요 생성 테스트")
    public void createNoticeLikeTest() {
        // GIVEN
        log.info("=== Start Create Like Notice Mapper Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(likeToggleNoticeDTO.getNno()) == null
                || likeMapper.findNoticeNumber(likeToggleNoticeDTO.getNno()) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }

        if (likeMapper.findMemberEmail(likeToggleNoticeDTO.getEmail()) == 0
                || likeMapper.findMemberEmail(likeToggleNoticeDTO.getEmail()) == null) {
            throw new MemberNotFoundException("해당하는 회원 이메일이 없습니다.");
        }
        LikeToggleNoticeDTO beforeLike = likeMapper.checkToggleLikeNotice(likeToggleNoticeDTO.getEmail(),
                likeToggleNoticeDTO.getNno());
        if (beforeLike == null) {
            Long createLikeNotice = likeMapper.createLikeNotice(likeToggleNoticeDTO);
            Assertions.assertNotNull(createLikeNotice, "createLiketNotice Should Be Not Null");
        }
        // WHEN
        Assertions.assertEquals(likeToggleNoticeDTO.getEmail(), JUNIT_TEST_EMAIL);
        Assertions.assertEquals(likeToggleNoticeDTO.getNno(), JUNIT_TEST_NNO);
        log.info("=== End Create Like Mapper Test ===");
    }

    // Delete Notice Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 좋아요 삭제 테스트")
    public void deleteNoticeLikeTest() {
        // GIVEN
        log.info("=== Start Delete Notice Like Mapper Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }

        if (likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == null || likeMapper.findMemberEmail(JUNIT_TEST_EMAIL) == 0) {
            throw new MemberNotFoundException("해당하는 회원 이메일이 없습니다.");
        }
        Long deleteNoticeLike = likeMapper.deleteLikeNotice(likeToggleNoticeDTO);
        LikeToggleNoticeDTO afterRead = likeMapper.checkToggleLikeNotice(JUNIT_TEST_EMAIL, JUNIT_TEST_NNO);
        Assertions.assertNull(afterRead, "afterRead Should Be Null");
        log.info("=== End Delete Notice Like Mapper Test ===");
    }

    // Count Notice Like Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 좋아요 카운트 테스트")
    public void countNoticeLikeTest() {
        // GIVEN
        log.info("=== Start Count Like Notice Mapper Test ===");
        // WHEN
        Long countNoticeLike = likeMapper.countLikeNotice(JUNIT_TEST_NNO);
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        log.info(JUNIT_TEST_BNO + " 번 공지사항 좋아요 개수: " + countNoticeLike);
        Assertions.assertNotNull(countNoticeLike, "countNoticeLike Should Be Not Null");
        log.info("=== End Count Like Notice Mapper Test === ");
    }

    // Increment Like Count Board Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시물 좋아요 수 증가 테스트")
    public void incrementLikeTest() {
        // GIVEN
        log.info("=== Start Increment Like Mapper Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0 || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        Long beforeCount = likeMapper.getCurrentBoardLikeCount(JUNIT_TEST_BNO);

        likeMapper.incrementBoardLike(JUNIT_TEST_BNO);

        Long afterCount = likeMapper.getCurrentBoardLikeCount(JUNIT_TEST_BNO);
        // THEN
        Assertions.assertEquals(beforeCount + 1, afterCount);
        log.info("=== End Increment Like Mapper Test ===");
    }

    // Decrement Like Count Board Test
    @Test
    @Transactional
    @DisplayName("Mapper: 게시물 좋아요 수 감소 테스트")
    public void decrementLikeTest() {
        // GIVEN
        log.info("=== Start Decrement Like Count Mapper Test ===");
        // WHEN
        if (likeMapper.findBoardNumber(JUNIT_TEST_BNO) == 0 || likeMapper.findBoardNumber(JUNIT_TEST_BNO) == null) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        Long beforeCount = likeMapper.getCurrentBoardLikeCount(JUNIT_TEST_BNO);

        likeMapper.decrementBoardLike(JUNIT_TEST_BNO);

        Long afterCount = likeMapper.getCurrentBoardLikeCount(JUNIT_TEST_BNO);
        // THEN
        Assertions.assertEquals(beforeCount, afterCount);
        log.info("=== End Decrement Like Count Mapper Test ===");
    }

    // Increment Like Count Notice Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 좋아요 수 증가 테스트")
    public void incrementNoticeLikeTest() {
        log.info("=== Start Increment Like Count Mapper Test ====");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0 || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
        Long beforeCount = likeMapper.getCurrentNoticeLikeCount(JUNIT_TEST_NNO);

        likeMapper.incrementNoticeLike(JUNIT_TEST_NNO);

        Long afterCount = likeMapper.getCurrentNoticeLikeCount(JUNIT_TEST_NNO);
        // THEN
        Assertions.assertEquals(beforeCount + 1, afterCount);
        log.info("=== End Increment Like Count Mapper Test ===");
    }

    // Decrement Like Count Notice Test
    @Test
    @Transactional
    @DisplayName("Mapper: 공지사항 좋아요 수 감소 테스트")
    public void decrementNoticeLikeTest() {
        // GIVEN
        log.info("=== Start Decrement Like Count Mapper Test ===");
        // WHEN
        if (likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == null || likeMapper.findNoticeNumber(JUNIT_TEST_NNO) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        Long beforeCound = likeMapper.getCurrentNoticeLikeCount(JUNIT_TEST_NNO);

        likeMapper.decrementNoticeLike(JUNIT_TEST_NNO);

        Long afterCount = likeMapper.getCurrentNoticeLikeCount(JUNIT_TEST_NNO);
        // THEN
        log.info("=== End Decrement Like Count Mapper Test ===");
    }
}
