package board.mybatis.mvc.reply.mapper;

import java.util.List;

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
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.exception.ReplyNumberNotFoundException;
import board.mybatis.mvc.mappers.ReplyMapper;
import board.mybatis.mvc.util.page.PageRequestDTO;
import lombok.extern.log4j.Log4j2;

// Reply Mapper Test Class
@Log4j2
@SpringBootTest
public class ReplyMapperTests {

        // 의존성 주입
        @Autowired(required = false)
        private ReplyMapper replyMapper;

        // 테스트 시작 시 메모리 선 참조
        private static final String JUNIT_TEST_REPLY = "Junit_Test_Reply";
        private static final String JUNIT_TEST_REPLYER = "thistrik@naver.com";

        private static final String JUNIT_TEST_REPLY_CHILD = "Junit_Test_Reply_Child";
        private static final String JUNIT_TEST_REPLYER_CHILD = "thistrik@naver.com";

        private static final Long JUNIT_TEST_NNO = 3L;
        private static final Long JUNIT_TEST_BNO = 2L;
        private static final Long JUNIT_TEST_BOARD_GNO = 490L;
        private static final Long JUNIT_TEST_BOARD_RNO = 490L;
        private static final Long JUNIT_TEST_BOARD_CHILD_RNO = 497L;

        private static final Long JUNIT_TEST_NOTICE_RNO = 491L;
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
                                .rno(JUNIT_TEST_BOARD_RNO)
                                .reply(JUNIT_TEST_REPLY)
                                .replyer(JUNIT_TEST_REPLYER)
                                .gno(JUNIT_TEST_BOARD_GNO)
                                .build();

                replyBoardChildUpdateDTO = ReplyBoardUpdateDTO.builder()
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

        // Create Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 생성 테스트")
        public void createNoticeReplyTest() {
                // GIVEN
                log.info("=== Start Create Notice Reply Mapper Test ===");
                // WHEN
                if (replyNoticeCreateDTO.getNno() == null) {
                        throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
                }
                if (replyNoticeCreateDTO.getReply() == null || replyNoticeCreateDTO.getReplyer() == null
                                || replyNoticeCreateDTO.getNno() == null) {
                        throw new DataNotFoundException("댓글내용, 작성자, 공지사항 번호는 필수입니다.");
                }
                Long createNoticeReply = replyMapper.createNoticeReply(replyNoticeCreateDTO);
                if (replyNoticeCreateDTO.getRno() == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글 번호가 없습니다.");
                }
                Long updateGno = replyMapper.updateNoticeReplyGno(replyNoticeCreateDTO.getRno(),
                                replyNoticeCreateDTO.getRno());
                Long incrementReplyCount = replyMapper.incrementNoticeReplyCount(replyNoticeCreateDTO.getNno());
                // THEN
                Assertions.assertEquals(createNoticeReply, 1, "createNoticeReply Should Be Return 1");
                Assertions.assertEquals(replyNoticeCreateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeCreateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyNoticeCreateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Create Notice Reply Mapper Test ===");
        }

        // Create Notice Reply Child Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 대댓글 생성 테스트")
        public void createNoticeReplyChildTest() {
                // GIVEN
                log.info("=== Start Create Notice Reply Child Mapper Test ===");
                // WHEN
                if (replyNoticeChildCreateDTO.getNno() == null) {
                        throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
                }
                if (replyNoticeChildCreateDTO.getReply() == null || replyNoticeChildCreateDTO.getReplyer() == null
                                || replyNoticeChildCreateDTO.getNno() == null) {
                        throw new DataNotFoundException("댓글내용, 작성자, 공지사항 번호는 필수입니다.");
                }
                replyNoticeChildCreateDTO.setGno(JUNIT_TEST_NOTICE_GNO);
                Long createNoticeReplyChild = replyMapper.createNoticeReplyChild(replyNoticeChildCreateDTO);
                Long incrementReplyCount = replyMapper.incrementNoticeReplyCount(replyNoticeChildCreateDTO.getNno());
                // THEN
                Assertions.assertEquals(createNoticeReplyChild, 1, "createNoticeReplyChild Should Be Return 1");
                Assertions.assertEquals(replyNoticeChildCreateDTO.getNno(), JUNIT_TEST_NNO);
                Assertions.assertEquals(replyNoticeChildCreateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyNoticeChildCreateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Create Notice Reply Child Mapper Test ===");
        }

        // Read Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 조회 테스트")
        public void readNoticeReplyTest() {
                log.info("=== Start Read Notice Reply Mapper Test ===");
                // WHEN
                ReplyNoticeDTO beforeReply = replyMapper.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 번호의 댓글이 없습니다.");
                }
                ReplyNoticeDTO afterReply = replyMapper.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                // THEN
                Assertions.assertEquals(beforeReply, afterReply);
                Assertions.assertNotNull(afterReply, "afterReply Should Be Not Null");
                log.info("=== End Read Notice Reply Mapper Test ===");
        }

        // Update Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 업데이트 테스트")
        public void updateNoticeReplyTest() {
                // GIVEN
                log.info("=== Start Update Notice Reply Mapper Test ===");
                // WHEN
                if (replyNoticeUpdateDTO.getNno() == null) {
                        throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
                }
                if (replyNoticeUpdateDTO.getNno() == null || replyNoticeUpdateDTO.getReply() == null
                                || replyNoticeUpdateDTO.getReplyer() == null) {
                        throw new DataNotFoundException("댓글내용, 작성자, 공지사항 번호는 필수입니다.");
                }
                Long updateNoticeReply = replyMapper.updateNoticeReply(replyNoticeUpdateDTO);
                Assertions.assertEquals(updateNoticeReply, 1, "updateNoticeReply Should Be Return 1");
                Assertions.assertEquals(replyNoticeUpdateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyNoticeUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                Assertions.assertEquals(replyNoticeUpdateDTO.getNno(), JUNIT_TEST_NNO);
                log.info("=== End Update Notice Reply Mapper Test ===");
        }

        // Update Notice Reply Child Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 대댓글 업데이트 테스트")
        public void updateNoticeReplyChildTest() {
                // GIVEN
                log.info("=== Start Update Notice Reply Child Mapper Test ===");
                // WHEN
                if (replyNoticeChildUpdateDTO.getRno() == null || replyNoticeChildUpdateDTO.getReply() == null
                                || replyNoticeChildUpdateDTO.getReplyer() == null) {
                        throw new DataNotFoundException("댓글내용, 작성자, 댓글 번호는 필수입니다.");
                }
                if (replyNoticeChildUpdateDTO.getRno() == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글 번호가 없습니다.");
                }
                Long updateNoticeReplyChild = replyMapper.updateNoticeReply(replyNoticeChildUpdateDTO);
                // THEN
                Assertions.assertEquals(updateNoticeReplyChild, 1, "updateNoticeReplyChild Should Be Return 1");
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                Assertions.assertEquals(replyNoticeChildUpdateDTO.getNno(), JUNIT_TEST_NNO);
                log.info("=== End Update Notice Reply Child Mapper Test ===");
        }

        // Delete Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 삭제 테스트")
        public void deleteNoticeReplyTest() {
                // GIVEN
                log.info("=== Start Delete Notice Reply Mapper Test ===");
                // WHEN
                ReplyNoticeDTO beforeReply = replyMapper.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글 번호가 없습니다.");
                }
                Long deleteReply = replyMapper.deleteNoticeReply(JUNIT_TEST_NOTICE_RNO);
                Long decrementReply = replyMapper.decrementNoticeReplyCount(beforeReply.getRno());
                // THEN
                ReplyNoticeDTO afterReply = replyMapper.readNoticeReply(JUNIT_TEST_NOTICE_RNO);
                Assertions.assertEquals(deleteReply, 1, "deleteReply Should Be Return 1");
                Assertions.assertEquals(afterReply.getReplyer(), "삭제된 게시자 입니다.");
                Assertions.assertEquals(afterReply.getReply(), "삭제된 댓글입니다.");
                log.info("=== End Delete Notice Reply Mapper Test ===");
        }

        // List Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 리스트 테스트")
        public void listNoticeReplyTest() {
                log.info("=== Start List Notice Reply Mapper Test ===");
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                if (pageRequestDTO == null) {
                        throw new DataNotFoundException(JUNIT_TEST_NNO + " 에 해당하는 댓글 리스트가 없습니다.");
                }
                List<ReplyNoticeListDTO> list = replyMapper.listNoticeReply(pageRequestDTO, JUNIT_TEST_NNO);
                log.info("list: " + list);
                log.info("=== End List Notice Reply Mapper Test ===");
        }

        // Total Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 토탈 테스트")
        public void totalNoticeReplyTest() {
                log.info("=== Start Total Notice Reply Mapper Test ===");
                int total = replyMapper.totalNoticeReply(JUNIT_TEST_NNO);
                log.info("total: " + total);
                log.info("=== End Total Notice Reply Mapper Test ===");
        }

        // Count Notice Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 공지사항 댓글 수 카운트")
        public void countNoticeReplyTest() {
                // GIVEN
                log.info("=== Start Count Notice Reply Mapper Test ===");
                // WHEN
                ReplyNoticeDTO beforeReply = replyMapper.readNoticeReply(JUNIT_TEST_NNO);
                if (replyMapper.findNoticeNno(JUNIT_TEST_NNO) == null) {
                        throw new NoticeNumberNotFoundException("해당하는 공지사항의 번호가 없습니다.");
                }
                Long count = replyMapper.countNoticeReply(JUNIT_TEST_NNO);
                // THEN
                log.info("count: " + count);
                Assertions.assertNotNull(count, "count Should Be Not Null");
                log.info("=== End Count Notice Reply Mapper Test ===");
        }

        // Create Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 생성 테스트")
        public void createBoardReplyTest() {
                // GIVEN
                log.info("=== Start Create Board Reply Mapper Test ===");
                // WHEN
                if (replyBoardCreateDTO.getBno() == null) {
                        throw new BoardNumberNotFoundException("해당하는 게시글의 번호가 없습니다.");
                }
                if (replyBoardCreateDTO.getReplyer() == null || replyBoardCreateDTO.getReply() == null) {
                        throw new DataNotFoundException("댓글내용, 작성자는 필수 사항입니다.");
                }
                Long createReply = replyMapper.createBoardReply(replyBoardCreateDTO);
                Long incrementReplyCount = replyMapper.incrementBoardReplyCount(replyBoardCreateDTO.getBno());
                if (replyBoardCreateDTO.getRno() == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글의 RNO 가 없습니다.");
                }
                Long updateGno = replyMapper.updateBoardReplyGno(replyBoardCreateDTO.getRno(),
                                replyBoardCreateDTO.getRno());
                // THEN
                Assertions.assertEquals(createReply, 1, "createReply Should Be Return 1");
                Assertions.assertEquals(replyBoardCreateDTO.getBno(), JUNIT_TEST_BNO);
                Assertions.assertEquals(replyBoardCreateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyBoardCreateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== Start Create Board Reply Mapper Test ===");
        }

        // Create Board Reply Child Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 대댓글 생성 테스트")
        public void createBoardReplyChildTest() {
                // GIVEN
                log.info("=== Start Create Board Reply Child Mapper Test ===");
                if (replyBoardChildCreateDTO.getBno() == null) {
                        throw new BoardNumberNotFoundException("해당하는 게시글의 번호가 없습니다.");
                }
                if (replyBoardChildCreateDTO.getBno() == null || replyBoardChildCreateDTO.getReply() == null
                                || replyBoardChildCreateDTO.getReplyer() == null
                                || replyBoardChildCreateDTO.getGno() == null) {
                        throw new DataNotFoundException("게시글 번호, 작성자, 댓글내용 은 필수사항입니다.");
                }
                // WHEN
                replyBoardChildCreateDTO.setGno(JUNIT_TEST_BOARD_GNO);
                Long createReplyChild = replyMapper.createBoardReplyChild(replyBoardChildCreateDTO);
                Long incrementReplyCount = replyMapper.incrementBoardReplyCount(replyBoardChildCreateDTO.getBno());
                // THEN
                Assertions.assertEquals(createReplyChild, 1, "createReplyChild Should Be Return 1");
                Assertions.assertEquals(replyBoardChildCreateDTO.getGno(), JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(replyBoardChildCreateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyBoardChildCreateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Create Board Reply Child Mapper Test ===");
        }

        // Read Board Reply Child Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 조회 테스트")
        public void readBoardReplyTest() {
                // GIVEN
                log.info("=== Start Read Board Reply Mapper Test ===");
                // WHEN
                ReplyBoardDTO beforeReply = replyMapper.readBoardReply(JUNIT_TEST_BOARD_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 번호의 댓글이 없습니다.");
                }
                ReplyBoardDTO afterReply = replyMapper.readBoardReply(JUNIT_TEST_BOARD_RNO);
                // THEN
                Assertions.assertEquals(beforeReply, afterReply);
                Assertions.assertNotNull(afterReply, "afterReply Should Be Not Null");
                log.info("=== End Read Board Reply Mapper Test ===");
        }

        // Update Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 업데이트 테스트")
        public void updateBoardReplyTest() {
                // GIVEN
                log.info("=== Start Update Board Reply Mapper Test ===");
                // WHEN
                if (replyBoardUpdateDTO.getRno() == null || replyBoardUpdateDTO.getReply() == null
                                || replyBoardUpdateDTO.getReplyer() == null) {
                        throw new DataNotFoundException("게시글 번호, 작성자, 댓글내용 은 필수사항입니다.");
                }
                if (replyBoardUpdateDTO.getRno() == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글의 번호가 없습니다.");
                }
                Long updateReply = replyMapper.updateBoardReply(replyBoardUpdateDTO);
                Assertions.assertEquals(updateReply, 1, "updateReply Should Be Return 1");
                Assertions.assertEquals(replyBoardUpdateDTO.getRno(), JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(replyBoardUpdateDTO.getReply(), JUNIT_TEST_REPLY);
                Assertions.assertEquals(replyBoardUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER);
                log.info("=== End Update Board Reply Mapper Test ===");
        }

        // Update Board Reply Child Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 대댓글 업데이트 테스트")
        public void updateBoardReplyChildTest() {
                // GIVEN
                log.info("=== Start Update Board Reply Child Mapper Test ===");
                // WHEN
                if (replyBoardChildUpdateDTO.getRno() == null || replyBoardChildUpdateDTO.getReply() == null
                                || replyBoardChildUpdateDTO.getReplyer() == null) {
                        throw new DataNotFoundException("게시글 번호, 작성자, 댓글내용 은 필수사항입니다.");
                }
                if (replyBoardChildUpdateDTO.getRno() == null) {
                        throw new ReplyNumberNotFoundException("해당하는 대댓글의 번호가 없습니다.");
                }
                Long updateReply = replyMapper.updateBoardReply(replyBoardChildUpdateDTO);
                Assertions.assertEquals(updateReply, 1, "updateReply Should Be Return 1");
                Assertions.assertEquals(replyBoardChildUpdateDTO.getRno(), JUNIT_TEST_BOARD_CHILD_RNO);
                Assertions.assertEquals(replyBoardChildUpdateDTO.getReply(), JUNIT_TEST_REPLY_CHILD);
                Assertions.assertEquals(replyBoardChildUpdateDTO.getReplyer(), JUNIT_TEST_REPLYER_CHILD);
                log.info("=== End Update Board Reply Child Mapper Test ===");
        }

        // Delete Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 삭제 테스트")
        public void deleteBoardReplyTest() {
                // GIVEN
                log.info("=== Start Delete Board Reply Mapper Test ===");
                // WHEN
                ReplyBoardDTO beforeReply = replyMapper.readBoardReply(JUNIT_TEST_BOARD_RNO);
                if (beforeReply == null) {
                        throw new ReplyNumberNotFoundException("해당하는 댓글 번호가 없습니다.");
                }
                Long deleteReply = replyMapper.deleteBoardReply(JUNIT_TEST_BOARD_RNO);
                Long decrementReply = replyMapper.decrementBoardReplyCount(beforeReply.getBno());
                // THEN
                ReplyBoardDTO afterReply = replyMapper.readBoardReply(JUNIT_TEST_BOARD_RNO);
                Assertions.assertEquals(deleteReply, 1, "deleteReply Should Be Return 1");
                Assertions.assertEquals(afterReply.getReplyer(), "삭제된 게시자 입니다.");
                Assertions.assertEquals(afterReply.getReply(), "삭제된 댓글입니다.");
                log.info("=== End Delete Board Reply Mapper Test ===");
        }

        // List Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 리스트 테스트")
        public void listBoardReplyTest() {
                log.info("=== Start List Board Reply Mapper Test ===");
                PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
                if (pageRequestDTO == null) {
                        throw new DataNotFoundException(JUNIT_TEST_BNO + " 에 해당하는 댓글 리스트가 없습니다.");
                }
                List<ReplyBoardListDTO> list = replyMapper.listBoardReply(pageRequestDTO, JUNIT_TEST_BNO);
                log.info("list: " + list);
                log.info("=== End List Board Reply Mapper Test ===");
        }

        // Total Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 토탈 테스트")
        public void totalBoardReplyTest() {
                log.info("=== Start Total Board Reply Mapper Test ===");
                int total = replyMapper.totalBoardReply(JUNIT_TEST_BNO);
                log.info("total: " + total);
                log.info("=== End Total Board Reply Mapper Test ===");
        }

        // Count Board Reply Test
        @Test
        @Transactional
        @DisplayName("Mapper: 게시판 댓글 수 카운트")
        public void countBoardReplyTest() {
                // GIVEN
                log.info("=== Start Count Board Reply Mapper Test ===");
                // WHEN
                ReplyBoardDTO beforeReply = replyMapper.readBoardReply(JUNIT_TEST_BNO);
                if (replyMapper.findBoardBno(JUNIT_TEST_BNO) == null) {
                        throw new BoardNumberNotFoundException("해당하는 게시물의 번호가 없습니다.");
                }
                Long count = replyMapper.countBoardReply(JUNIT_TEST_BNO);
                // THEN
                log.info("count: " + count);
                Assertions.assertNotNull(count, "count Should Be Not Null");
                log.info("=== End Count Board Reply Mapper Test ===");
        }
}
