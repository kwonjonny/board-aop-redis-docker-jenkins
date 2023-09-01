package board.mybatis.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
import board.mybatis.mvc.service.ReplyService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Reply ServiceImpl Class
@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {

    // 의존성 주입
    private final ReplyMapper replyMapper;

    /*
     * Autowired 명시적 표시
     */
    @Autowired
    public ReplyServiceImpl(ReplyMapper replyMapper) {
        log.info("Inject ReplyMapper");
        this.replyMapper = replyMapper;
    }

    /*
     * 게시글 댓글 생성 서비스
     * Gno == null || Gno == 0 이면 댓글 생성 아닐 시 대댓글 생성
     * 후 댓글 수 증가
     */
    @Override
    @Transactional
    public Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO) {
        log.info("Is Running Create Board Reply ServiceImpl");
        if (replyBoardCreateDTO.getReply() == null || replyBoardCreateDTO.getReplyer() == null
                || replyBoardCreateDTO.getBno() == null) {
            throw new DataNotFoundException("게시물 번호, 작성자, 댓글 내용 은 필수사항입니다.");
        }
        validateBoardNumber(replyBoardCreateDTO.getBno()); // Check Board Number
        if (replyBoardCreateDTO.getGno() == null || replyBoardCreateDTO.getGno() == 0) {
            Long createBoardReply = replyMapper.createBoardReply(replyBoardCreateDTO);

            validateBoardReplyNumber(replyBoardCreateDTO.getRno()); // Check Board Reply Number
            replyMapper.updateBoardReplyGno(replyBoardCreateDTO.getRno(), replyBoardCreateDTO.getRno());
            return replyMapper.incrementBoardReplyCount(replyBoardCreateDTO.getBno());
        } else {
            Long createBoardReplyChild = replyMapper.createBoardReplyChild(replyBoardCreateDTO);

            validateBoardReplyNumber(replyBoardCreateDTO.getRno()); // Check Board Reply Number
            return replyMapper.incrementBoardReplyCount(replyBoardCreateDTO.getBno());
        }
    }

    /*
     * 게시글 댓글 삭제 서비스
     * 및 댓글 수 감소
     */
    @Override
    @Transactional
    public Long deleteBoardReply(Long rno) {
        log.info("Is Running Delete Board Reply ServiceImpl");
        validateBoardReplyNumber(rno); // Check Board Reply Number
        ReplyBoardDTO replyBoardDTO = replyMapper.readBoardReply(rno);
        replyMapper.deleteBoardReply(rno);
        return replyMapper.decrementBoardReplyCount(replyBoardDTO.getBno());
    }

    /*
     * 게시글 댓글 업데이트 서비스
     */
    @Override
    @Transactional
    public Long updateBoardReply(ReplyBoardUpdateDTO replyBoardUpdateDTO) {
        log.info("Is Running Update Board Reply ServiceImpl");
        if (replyBoardUpdateDTO.getReply() == null || replyBoardUpdateDTO.getReplyer() == null
                || replyBoardUpdateDTO.getBno() == null) {
            throw new DataNotFoundException("게시물 번호, 작성자, 댓글 내용 은 필수사항입니다.");
        }
        return replyMapper.updateBoardReply(replyBoardUpdateDTO);
    }

    /*
     * 게시글 댓글 조회 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public ReplyBoardDTO readBoardReply(Long rno) {
        log.info("Is Running Read Board Reply ServiceImpl");
        validateBoardReplyNumber(rno); // Check Board Reply Number
        return replyMapper.readBoardReply(rno);
    }

    /*
     * 게시글 댓글 리스트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ReplyBoardListDTO> listBoardReply(PageRequestDTO pageRequestDTO, Long bno) {
        log.info("Is Running List Board Reply ServiceImpl");
        validateBoardNumber(bno); // Check Board Number
        pageRequestDTO.setSize(10);
        int total = replyMapper.totalBoardReply(bno);
        // page 번호
        int pageNum = pageRequestDTO.getPage();
        // 끝 페이지 계산
        if (!pageRequestDTO.isReplyLast()) {
            // pageNum 에 넣어주기
            pageNum = (int) (Math.ceil(total / (double) pageRequestDTO.getSize()));
            // page 번호가 0 보다 작거나 같으면 1
            pageNum = pageNum <= 0 ? 1 : pageNum;
        }
        // 끝페이지 번호로 설정
        pageRequestDTO.setPage(pageNum);
        List<ReplyBoardListDTO> list = replyMapper.listBoardReply(pageRequestDTO, bno);
        return PageResponseDTO.<ReplyBoardListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /*
     * 게시글 댓글 수 카운트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public Long countBoardReply(Long bno) {
        log.info("Is Running Count Board Reply ServiceImpl");
        validateBoardNumber(bno); // Check Board Number
        return replyMapper.countBoardReply(bno);
    }

    /*
     * 공지사항 댓글 생성 서비스
     * Gno == null || Gno == 0 이면 댓글 생성 아닐 시 대댓글 생성
     * 후 댓글 수 증가
     */
    @Override
    @Transactional
    public Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO) {
        log.info("Is Runing Create Notice Reply ServiceImpl");
        if (replyNoticeCreateDTO.getNno() == null || replyNoticeCreateDTO.getReply() == null
                || replyNoticeCreateDTO.getReplyer() == null) {
            throw new DataNotFoundException("공지사항 번호, 작성자, 댓글 내용은 필수입니다.");
        }
        validateNoticeNumber(replyNoticeCreateDTO.getNno()); // Check Notice Number
        if (replyNoticeCreateDTO.getGno() == null || replyNoticeCreateDTO.getGno() == 0) {
            Long createNoticeReply = replyMapper.createNoticeReply(replyNoticeCreateDTO);

            validateNoticeReplyNumber(replyNoticeCreateDTO.getRno()); // Check Notice Reply Number
            replyMapper.updateNoticeReplyGno(replyNoticeCreateDTO.getRno(), replyNoticeCreateDTO.getRno());
            return replyMapper.incrementNoticeReplyCount(replyNoticeCreateDTO.getNno());
        } else {
            Long createNoticeReplyChild = replyMapper.createNoticeReplyChild(replyNoticeCreateDTO);

            validateNoticeReplyNumber(replyNoticeCreateDTO.getRno()); // Check Notice Reply Number
            return replyMapper.incrementNoticeReplyCount(replyNoticeCreateDTO.getNno());
        }
    }

    /*
     * 공지사항 댓글 삭제 서비스
     * 및 댓글 수 감소
     */
    @Override
    @Transactional
    public Long deleteNoticeReply(Long rno) {
        log.info("Is Running Delete Notice Reply ServiceImpl");
        validateNoticeReplyNumber(rno); // Check Notice Reply Number
        ReplyNoticeDTO replyNoticeDTO = replyMapper.readNoticeReply(rno);
        replyMapper.deleteNoticeReply(rno);
        return replyMapper.decrementNoticeReplyCount(replyNoticeDTO.getNno());
    }

    /*
     * 공지사항 댓글 업데이트 서비스
     */
    @Override
    @Transactional
    public Long updateNoticeReply(ReplyNoticeUpdateDTO replyNoticeUpdateDTO) {
        log.info("Is Running Update Notice Reply ServiceImpl");
        if (replyNoticeUpdateDTO.getNno() == null || replyNoticeUpdateDTO.getReply() == null
                || replyNoticeUpdateDTO.getReplyer() == null) {
            throw new DataNotFoundException("공지사항 번호, 작성자, 댓글 내용은 필수입니다.");
        }
        return replyMapper.updateNoticeReply(replyNoticeUpdateDTO);
    }

    /*
     * 공지사항 댓글 조회 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public ReplyNoticeDTO readNoticeReply(Long rno) {
        log.info("Is Running Read Notice Reply ServiceImpl");
        validateNoticeReplyNumber(rno); // Check Notice Reply Number
        return replyMapper.readNoticeReply(rno);
    }

    /*
     * 공지사항 댓글 리스트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ReplyNoticeListDTO> listNoticeReply(PageRequestDTO pageRequestDTO, Long nno) {
        if (pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 공지사항 댓글 리스트가 없습니다.");
        }
        validateNoticeNumber(nno); // Check Notice Number
        pageRequestDTO.setSize(10);
        int total = replyMapper.totalNoticeReply(nno);
        // page 번호
        int pageNum = pageRequestDTO.getPage();
        // 끝 페이지 계산
        if (!pageRequestDTO.isReplyLast()) {
            // pageNum 에 넣어주기
            pageNum = (int) (Math.ceil(total / (double) pageRequestDTO.getSize()));
            // page 번호가 0 보다 작거나 같으면 1
            pageNum = pageNum <= 0 ? 1 : pageNum;
        }
        // 끝페이지 번호로 설정
        pageRequestDTO.setPage(pageNum);
        List<ReplyNoticeListDTO> list = replyMapper.listNoticeReply(pageRequestDTO, nno);
        return PageResponseDTO.<ReplyNoticeListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /*
     * 공지사항 댓글 수 카운트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public Long countNoticeReply(Long nno) {
        log.info("Is Running Count Notice Reply ServiceImpl");
        validateNoticeNumber(nno); // Check Notice Number
        return replyMapper.countNoticeReply(nno);
    }

    /*
     * 공지사항 번호 검증 서비스
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateNoticeNumber(Long nno) {
        log.info("Is Running Validate Notice Number ServiceImpl");
        if (replyMapper.findNoticeNno(nno) == null || replyMapper.findNoticeNno(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }

    /*
     * 게시판 번호 검증 서비스
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateBoardNumber(Long bno) {
        log.info("Is Running Validate Board Number ServiceImpl");
        if (replyMapper.findBoardBno(bno) == null || replyMapper.findBoardBno(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
    }

    /*
     * 공지사항 댓글 번호 검증 서비스
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateNoticeReplyNumber(Long rno) {
        log.info("Is Running Validate Notice Reply Number ServiceImpl");
        if (replyMapper.findNoticeRno(rno) == null || replyMapper.findNoticeRno(rno) == 0) {
            throw new ReplyNumberNotFoundException("해당하는 공지사항 댓글 번호가 없습니다.");
        }
    }

    /*
     * 게시판 댓글 번호 검증 서비스
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateBoardReplyNumber(Long rno) {
        log.info("Is Running Validate Board Reply Number ServiceImpl");
        if (replyMapper.findBoardRno(rno) == null || replyMapper.findBoardRno(rno) == 0) {
            throw new ReplyNumberNotFoundException("해당하는 게시글 댓글 번호가 없습니다.");
        }
    }
}
