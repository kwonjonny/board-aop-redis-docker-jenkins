package board.mybatis.mvc.reply.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.reply.board.ReplyBoardCreateDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardListDTO;
import board.mybatis.mvc.dto.reply.board.ReplyBoardUpdateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeCreateDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeListDTO;
import board.mybatis.mvc.dto.reply.notice.ReplyNoticeUpdateDTO;
import board.mybatis.mvc.exception.ReplyNumberNotFoundException;
import board.mybatis.mvc.service.ReplyService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Reply Service Test Class
@Log4j2
@SpringBootTest
public class ReplyServiceTests {

        // 의존성 주입
        @Autowired
        private ReplyService replyService;

        // 테스트 시작 시 메모리 선 참조
        private static final String JUNIT_TEST_REPLY = "Junit_Test_Reply";
        private static final String JUNIT_TEST_REPLYER = "thistrik@naver.com";

        private static final String JUNIT_TEST_REPLY_CHILD = "Junit_Test_Reply_Child";
        private static final String JUNIT_TEST_REPLYER_CHILD = "thistrik@naver.com";

        private static final Long JUNIT_TEST_NNO = 101L;
        private static final Long JUNIT_TEST_BNO = 2L;
        private static final Long JUNIT_TEST_BOARD_GNO = 490L;
        private static final Long JUNIT_TEST_BOARD_RNO = 497L;
        private static final Long JUNIT_TEST_BOARD_CHILD_RNO = 497L;

        private static final Long JUNIT_TEST_NOTICE_RNO = 589L;
        private static final Long JUNIT_TEST_NOTICE_GNO = 491L;
        private static final Long JUNIT_TEST_NOTICE_CHILD_RNO = 494L;

        // BeforeEach 사용을 위한 DTO 정의
        private ReplyBoardCreateDTO replyBoardCreateDTO;
        private ReplyBoardUpdateDTO replyBoardUpdateDTO;

        private ReplyBoardCreateDTO replyBoardChildCreateDTO;
        private ReplyBoardUpdateDTO replyBoardChildUpdateDTO;

        private ReplyNoticeCreateDTO replyNoticeCreateDTO;
        private ReplyNoticeUpdateDTO replyNoticeUpdateDTO;

        private ReplyNoticeCreateDTO replyNoticeChildCreateDTO;
        private ReplyNoticeUpdateDTO replyNoticeChildUpdateDTO;

        @BeforeEach
        public void setUp() {
                replyBoardCreateDTO = ReplyBoardCreateDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .reply(JUNIT_TEST_REPLY)
                                .replyer(JUNIT_TEST_REPLYER)
                                .build();

                replyBoardChildCreateDTO = ReplyBoardCreateDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .reply(JUNIT_TEST_REPLY_CHILD)
                                .replyer(JUNIT_TEST_REPLYER_CHILD)
                                .gno(JUNIT_TEST_BOARD_GNO)
                                .build();

                replyBoardUpdateDTO = ReplyBoardUpdateDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .rno(JUNIT_TEST_BOARD_RNO)
                                .reply(JUNIT_TEST_REPLY)
                                .replyer(JUNIT_TEST_REPLYER)
                                .gno(JUNIT_TEST_BOARD_GNO)
                                .build();

                replyBoardChildUpdateDTO = ReplyBoardUpdateDTO.builder()
                                .bno(JUNIT_TEST_BNO)
                                .rno(JUNIT_TEST_BOARD_CHILD_RNO)
                                .reply(JUNIT_TEST_REPLY_CHILD)
                                .replyer(JUNIT_TEST_REPLYER_CHILD)
                                .build();

                replyNoticeCreateDTO = ReplyNoticeCreateDTO.builder()
                                .nno(JUNIT_TEST_NNO)
                                .reply(JUNIT_TEST_REPLY)
                                .replyer(JUNIT_TEST_REPLYER)
                                .build();

                replyNoticeChildCreateDTO = ReplyNoticeCreateDTO.builder()
                                .nno(JUNIT_TEST_NNO)
                                .reply(JUNIT_TEST_REPLY_CHILD)
                                .replyer(JUNIT_TEST_REPLYER_CHILD)
                                .gno(JUNIT_TEST_NOTICE_GNO)
                                .build();

                replyNoticeUpdateDTO = ReplyNoticeUpdateDTO.builder()
                                .rno(JUNIT_TEST_NOTICE_RNO)
                                .nno(JUNIT_TEST_NNO)
                                .reply(JUNIT_TEST_REPLY)
                                .replyer(JUNIT_TEST_REPLYER)
                                .build();

                replyNoticeChildUpdateDTO = ReplyNoticeUpdateDTO.builder()
                                .rno(JUNIT_TEST_NOTICE_CHILD_RNO)
                                .nno(JUNIT_TEST_NNO)
                                .reply(JUNIT_TEST_REPLY_CHILD)
                                .replyer(JUNIT_TEST_REPLYER_CHILD)
                                .build();
        }

        // Create Board Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 생성 테스트")
        public void createBoardReplyTest() {
                log.info("=== Start Create Board Reply Service Test ===");
                Long replyCreate = replyService.createBoardReply(replyBoardCreateDTO);
                Assertions.assertEquals(replyCreate, 1, "replyCreate Should Be Return 1");
                Assertions.assertEquals(replyBoardCreateDTO.getBno(), JUNIT_TEST_BNO);
                Assertions.assertEquals(replyBoardCreateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyBoardCreateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Create Board Reply Service Test ===");
        }

        // Create Board Reply Child Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 대 댓글 생성 테스트")
        public void createBoardReplyChildTest() {
                log.info("=== Start Create Board Reply Child Service Test ===");
                Long replyChildCreate = replyService.createBoardReply(replyBoardChildCreateDTO);
                Assertions.assertEquals(replyChildCreate, 1, "replyChildCreate Should Be Return 1");
                Assertions.assertEquals(replyBoardChildCreateDTO.getBno(), JUNIT_TEST_BNO);
                Assertions.assertEquals(replyBoardChildCreateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyBoardChildCreateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Create Board Reply Child Service Test ===");
        }

        // Create Notice Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 생성 테스트")
        public void createNoticeReplyTest() {
                log.info("=== Start Create Notice Reply Service Test ===");
                Long replyCreateNotice = replyService.createNoticeReply(replyNoticeCreateDTO);
                Assertions.assertEquals(replyCreateNotice, 1, "replyCraetNotice Should Be Return 1");
                Assertions.assertEquals(replyNoticeChildCreateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeCreateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyNoticeCreateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Create Notice Reply Service Test ===");
        }

        // Craete Notice Reply Child Service Test
        @Test
        @Transactional
        @DisplayName("Serivce: 공지사항 대 댓글 생성 테스트")
        public void craeteNoticeReplyChildTest() {
                log.info("=== Start Create Notice Reply Child Service Test ===");
                Long replyCreateNoticeChild = replyService.createNoticeReply(replyNoticeChildCreateDTO);
                Assertions.assertEquals(replyCreateNoticeChild, 1, "replyCreateNoticeChild Should Be Return 1");
                Assertions.assertEquals(replyNoticeChildCreateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeChildCreateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyNoticeChildCreateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Create Notice Reply Child Service Test ===");
        }

        // Read Board Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 조회 테스트")
        public void readBoardReplyTest() {
                log.info("=== Start Read Board Reply Service Test ===");
                ReplyBoardDTO beforeReply = replyService.readBoardReply(JUNIT_TEST_BOARD_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 게시물 댓글이 없습니다.");
                }
                ReplyBoardDTO afterReply = replyService.readBoardReply(JUNIT_TEST_BOARD_RNO);
                log.info("afterReply: " + afterReply);
                Assertions.assertEquals(beforeReply, afterReply);
                Assertions.assertNotNull(afterReply, "afterReply Should Be Not Null");
                log.info("=== End Read Board Reply Service Test ===");
        }

        // Read Notice Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 조회 테스트")
        public void readNoticeReplyTest() {
                log.info("=== Start Read Notice Reply Service Test ===");
                ReplyNoticeDTO beforeReply = replyService.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 공지사항 댓글이 없습니다.");
                }
                ReplyNoticeDTO afterReply = replyService.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                log.info("afterReply: " + afterReply);
                Assertions.assertEquals(beforeReply, afterReply);
                Assertions.assertNotNull(afterReply, "afterReply Should Be Not Null");
                log.info("=== End Read Notice Reply Service Test ===");
        }

        // Update Board Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 업데이트 테스트")
        public void updateBoardReplyTest() {
                log.info("=== Start Update Board Reply Service Test ===");
                Long updateReply = replyService.updateBoardReply(replyBoardUpdateDTO);
                Assertions.assertEquals(updateReply, 1, "updateReply Should Be Return 1");
                Assertions.assertEquals(replyBoardUpdateDTO.getBno(), JUNIT_TEST_BNO);
                Assertions.assertEquals(replyBoardUpdateDTO.getRno(), JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(replyBoardUpdateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyBoardUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Update Board Reply Service Test ===");
        }

        // Update Notice Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 업데이트 테스트")
        public void updateNoticeReplyTest() {
                log.info("=== Start Update Notice Reply Servcie Test ===");
                Long updateReplyNotice = replyService.updateNoticeReply(replyNoticeUpdateDTO);
                Assertions.assertEquals(updateReplyNotice, 0, "updateReplyNotice Should Be Return 0");
                Assertions.assertEquals(replyNoticeUpdateDTO.getRno(), JUNIT_TEST_NOTICE_RNO);
                Assertions.assertEquals(replyNoticeUpdateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeUpdateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyNoticeUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Update Notice Reply Service Test ===");
        }

        // Update Board Reply Child Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 대 댓글 업데이트 테스트")
        public void updateBoardReplyChildTest() {
                log.info("=== Start Update Board Reply Child Service Test ===");
                Long updateReplyChild = replyService.updateBoardReply(replyBoardChildUpdateDTO);
                Assertions.assertEquals(updateReplyChild, 1, "updateReplyChild Should Be Return 1");
                Assertions.assertEquals(replyBoardChildUpdateDTO.getBno(), JUNIT_TEST_BNO);
                Assertions.assertEquals(replyBoardChildUpdateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyBoardChildUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Update Board Reply Child Service Test ===");
        }

        // Update Notice Reply Child Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 대 댓글 업데이트 테스트")
        public void updateNoticeReplyChildTest() {
                log.info("=== Start Update Notice Reply Child Service Test ===");
                Long updateReplyChildNotice = replyService.updateNoticeReply(replyNoticeChildUpdateDTO);
                Assertions.assertEquals(updateReplyChildNotice, 0, "updateReplyChildNotice Should Be Return 0");
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Update Notice Reply Child Service Test ===");
        }

        // Delete Board Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 삭제 테스트")
        public void deleteBoardReplyTest() {
                log.info("=== Start Delete Board Reply Service Test ===");
                Long deleteReply = replyService.deleteBoardReply(JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(deleteReply, 1, "deleteReply Should Be Return 1");
                ReplyBoardDTO afterReply = replyService.readBoardReply(JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(afterReply.getReply(), "삭제된 댓글입니다.");
                Assertions.assertEquals(afterReply.getReplyer(), "삭제된 게시자 입니다.");
                log.info("=== End Delete Board Reply Service Test ===");
        }

        // Delete Notice Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 삭제 테스트")
        public void deleteNoticeReplyTest() {
                log.info("=== Start Delete Notice Reply Service Test ===");
                Long deleteReplyNotice = replyService.deleteNoticeReply(JUNIT_TEST_NOTICE_RNO);
                Assertions.assertEquals(deleteReplyNotice, 1, "deleteReplyNotice Should Be Return 1");
                ReplyNoticeDTO afterReply = replyService.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                Assertions.assertEquals(afterReply.getReply(), "삭제된 댓글입니다.");
                Assertions.assertEquals(afterReply.getReplyer(), "삭제된 게시자 입니다.");
                log.info("=== End Delete Notice Reply Service Test ===");
        }

        // List Board Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 리스트 테스트")
        public void listBoardReplyTest() {
                log.info("=== Start List Board Reply Service Test ===");
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                PageResponseDTO<ReplyBoardListDTO> list = replyService.listBoardReply(pageRequestDTO, JUNIT_TEST_BNO);
                log.info("list: " + list.getList());
                Assertions.assertNotNull(list, "list Should Be Not Null");
                log.info("=== End List Board Reply Service Test ===");
        }

        // List Notice Reply Service Test
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 리스트 테스트")
        public void listNoticeReplyTest() {
                log.info("=== Start List Notice Reply Service Test ===");
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                PageResponseDTO<ReplyNoticeListDTO> list = replyService.listNoticeReply(pageRequestDTO, JUNIT_TEST_NNO);
                log.info("list: " +list.getList());
                Assertions.assertNotNull(list, "list Should Be Not Null");
                log.info("=== Start List Notice Reply Service Test ===");
        }

        // Count Board Reply Service Test 
        @Test
        @Transactional
        @DisplayName("Service: 게시물 댓글 카운트 테스트")
        public void countBoardReplyTest() {
                log.info("=== Start Counct Board Reply Service Test ===");
                Long count = replyService.countBoardReply(JUNIT_TEST_BNO);
                Assertions.assertNotNull(count, "count Should Be Not Null");
                log.info("=== End Count Board Reply Service Test ===");
        }

        // Count Notice Reply Service Test 
        @Test
        @Transactional
        @DisplayName("Service: 공지사항 댓글 카운트 테스트")
        public void countNoticeReplyTest() {
                log.info("=== Start Count Notice Reply Service Test ===");
                Long count = replyService.countNoticeReply(JUNIT_TEST_NNO);
                Assertions.assertNotNull(count, "count Should Be Not Null");
                log.info("=== End Count Notice Reply Service Test ===");
        }
}