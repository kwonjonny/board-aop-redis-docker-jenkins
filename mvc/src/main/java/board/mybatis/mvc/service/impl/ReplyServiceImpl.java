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
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

/**
 * 댓글 서비스 구현 클래스.
 * 게시판 및 공지사항에 대한 댓글 CRUD 관련 서비스를 제공합니다.
 */
@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    /**
     * ReplyServiceImpl 생성자.
     * replyMapper 의존성 주입을 수행합니다.
     * 
     * @param replyMapper 댓글 관련 데이터 엑세스 객체
     */
    @Autowired
    public ReplyServiceImpl(ReplyMapper replyMapper) {
        log.info("Inject ReplyMapper");
        this.replyMapper = replyMapper;
    }

    /**
     * 댓글 과 대댓글 생성 서비스.
     * 게시글에 대한 댓글을 생성합니다.
     * Gno 값이 null 또는 0이면 댓글을 생성하며, 그렇지 않으면 대댓글을 생성합니다.
     * 
     * @param replyBoardCreateDTO 게시글 댓글 생성에 필요한 데이터를 담고 있는 DTO.
     * @return 생성된 댓글의 번호.
     * @throws DataNotFoundException        필수 데이터가 없을 경우 발생하는 예외.
     * @throws BoardNumberNotFoundException 게시글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional
    public Long createBoardReply(ReplyBoardCreateDTO replyBoardCreateDTO) {
        log.info("Is Running Create Board Reply ServiceImpl");
        if (replyBoardCreateDTO.getReply() == null || replyBoardCreateDTO.getReplyer() == null
                || replyBoardCreateDTO.getBno() == null) {
            throw new DataNotFoundException("게시물 번호, 작성자, 댓글 내용 은 필수사항입니다.");
        }
        validateBoardNumber(replyBoardCreateDTO.getBno()); 
        if (replyBoardCreateDTO.getGno() == null || replyBoardCreateDTO.getGno() == 0) {
            Long createBoardReply = replyMapper.createBoardReply(replyBoardCreateDTO);

            validateBoardReplyNumber(replyBoardCreateDTO.getRno()); 
            replyMapper.updateBoardReplyGno(replyBoardCreateDTO.getRno(), replyBoardCreateDTO.getRno());
            return replyMapper.incrementBoardReplyCount(replyBoardCreateDTO.getBno());
        } else {
            Long createBoardReplyChild = replyMapper.createBoardReplyChild(replyBoardCreateDTO);

            validateBoardReplyNumber(replyBoardCreateDTO.getRno()); 
            return replyMapper.incrementBoardReplyCount(replyBoardCreateDTO.getBno());
        }
    }

    /**
     * 댓글 삭제 서비스.
     * 게시글의 댓글을 삭제하며, 댓글 수를 감소시킵니다.
     *
     * @param rno 삭제할 댓글의 번호.
     * @return 감소된 댓글의 총 수.
     * @throws ReplyNumberNotFoundException 게시글 댓글 번호가 유효하지 않을 경우 발생하는 예외..
     */
    @Override
    @Transactional
    public Long deleteBoardReply(Long rno) {
        log.info("Is Running Delete Board Reply ServiceImpl");
        validateBoardReplyNumber(rno); 
        ReplyBoardDTO replyBoardDTO = replyMapper.readBoardReply(rno);
        replyMapper.deleteBoardReply(rno);
        return replyMapper.decrementBoardReplyCount(replyBoardDTO.getBno());
    }

    /**
     * 게시글 댓글 업데이트 서비스.
     * 게시글의 댓글을 업데이트합니다.
     *
     * @param replyBoardUpdateDTO 게시글 댓글 업데이트에 필요한 DTO.
     * @return 업데이트된 댓글의 번호.
     * @throws DataNotFoundException 게시글 댓글 번호가 유효하지 않을 경우 발생하는 예외.
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

    /**
     * 게시글 댓글 조회 서비스.
     * 게시글의 댓글을 조회합니다.
     *
     * @param rno 조회할 댓글의 번호.
     * @return 조회된 댓글 정보.
     * @throws ReplyNumberNotFoundException 게시글 댓글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public ReplyBoardDTO readBoardReply(Long rno) {
        log.info("Is Running Read Board Reply ServiceImpl");
        validateBoardReplyNumber(rno);
        return replyMapper.readBoardReply(rno);
    }

    /**
     * 게시글 댓글 리스트 서비스.
     * 게시글의 댓글 리스트를 페이지별로 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보 및 정렬 기준.
     * @param bno            조회할 게시글의 번호.
     * @return 페이지별 댓글 리스트.
     * @throws BoardNumberNotFoundException 게시글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ReplyBoardListDTO> listBoardReply(PageRequestDTO pageRequestDTO, Long bno) {
        log.info("Is Running List Board Reply ServiceImpl");
        validateBoardNumber(bno); 
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

    /**
     * 게시글 댓글 총 수 카운트 서비스.
     * 게시글의 댓글 총 수를 반환합니다.
     *
     * @param bno 조회할 게시글의 번호.
     * @return 게시글의 댓글 총 수.
     * @throws BoardNumberNotFoundException 게시글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public Long countBoardReply(Long bno) {
        log.info("Is Running Count Board Reply ServiceImpl");
        validateBoardNumber(bno); 
        return replyMapper.countBoardReply(bno);
    }

    /**
     * 공지사항 댓글 과 대댓글 생성 서비스.
     * 공지사항에 대한 댓글을 생성합니다.
     * Gno 값이 null 또는 0이면 댓글을 생성하며, 그렇지 않으면 대댓글을 생성합니다.
     * 공지사항에 대한 댓글 또는 대댓글을 생성하며, 댓글 수를 증가시킵니다.
     *
     * @param replyBoardCreateDTO 게시글 댓글 생성에 필요한 데이터를 담고 있는 DTO.
     * @return 생성된 댓글의 번호.
     * @throws DataNotFoundException         필수 데이터가 없을 경우 발생하는 예외.
     * @throws NoticeNumberNotFoundException 공지사항 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional
    public Long createNoticeReply(ReplyNoticeCreateDTO replyNoticeCreateDTO) {
        log.info("Is Runing Create Notice Reply ServiceImpl");
        if (replyNoticeCreateDTO.getNno() == null || replyNoticeCreateDTO.getReply() == null
                || replyNoticeCreateDTO.getReplyer() == null) {
            throw new DataNotFoundException("공지사항 번호, 작성자, 댓글 내용은 필수입니다.");
        }
        validateNoticeNumber(replyNoticeCreateDTO.getNno()); 
        if (replyNoticeCreateDTO.getGno() == null || replyNoticeCreateDTO.getGno() == 0) {
            Long createNoticeReply = replyMapper.createNoticeReply(replyNoticeCreateDTO);

            validateNoticeReplyNumber(replyNoticeCreateDTO.getRno()); 
            replyMapper.updateNoticeReplyGno(replyNoticeCreateDTO.getRno(), replyNoticeCreateDTO.getRno());
            return replyMapper.incrementNoticeReplyCount(replyNoticeCreateDTO.getNno());
        } else {
            Long createNoticeReplyChild = replyMapper.createNoticeReplyChild(replyNoticeCreateDTO);

            validateNoticeReplyNumber(replyNoticeCreateDTO.getRno()); 
            return replyMapper.incrementNoticeReplyCount(replyNoticeCreateDTO.getNno());
        }
    }

    /**
     * 공지사항 댓글 삭제 서비스.
     * 공지사항의 댓글을 삭제하며, 댓글 수를 감소시킵니다.
     *
     * @param rno 삭제할 댓글의 번호.
     * @return 감소된 댓글의 총 수.
     * @throws ReplyNumberNotFoundException 공지사항 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional
    public Long deleteNoticeReply(Long rno) {
        log.info("Is Running Delete Notice Reply ServiceImpl");
        validateNoticeReplyNumber(rno);
        ReplyNoticeDTO replyNoticeDTO = replyMapper.readNoticeReply(rno);
        replyMapper.deleteNoticeReply(rno);
        return replyMapper.decrementNoticeReplyCount(replyNoticeDTO.getNno());
    }

    /**
     * 공지사항 게시글 댓글 업데이트 서비스.
     * 공지사항 댓글을 업데이트합니다.
     *
     * @param replyBoardUpdateDTO 게시글 댓글 업데이트에 필요한 DTO.
     * @return 업데이트된 댓글의 번호.
     * @throws DataNotFoundException 필수 데이터가 없을 경우 발생하는 예외.
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

    /**
     * 공지사항 댓글 조회 서비스.
     * 공지사항 댓글을 조회합니다.
     *
     * @param rno 조회할 댓글의 번호.
     * @return 조회된 댓글 정보.
     * @throws ReplyNumberNotFoundException 게시글 댓글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public ReplyNoticeDTO readNoticeReply(Long rno) {
        log.info("Is Running Read Notice Reply ServiceImpl");
        validateNoticeReplyNumber(rno); // Check Notice Reply Number
        return replyMapper.readNoticeReply(rno);
    }

    /**
     * 공지사항 게시글 댓글 리스트 서비스.
     * 공지사항 댓글 리스트를 페이지별로 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보 및 정렬 기준.
     * @param nno            조회할 공지사항의 번호.
     * @return 페이지별 댓글 리스트.
     * @throws BoardNumberNotFoundException 게시글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<ReplyNoticeListDTO> listNoticeReply(PageRequestDTO pageRequestDTO, Long nno) {
        validateNoticeNumber(nno); 
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

    /**
     * 공지사항 댓글 총 수 카운트 서비스.
     * 공지사항 댓글 총 수를 반환합니다.
     *
     * @param nno 조회할 공지사항 번호.
     * @return 공지사항 댓글 총 수.
     * @throws NoticeNumberNotFoundException 게시글 번호가 유효하지 않을 경우 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public Long countNoticeReply(Long nno) {
        log.info("Is Running Count Notice Reply ServiceImpl");
        validateNoticeNumber(nno);
        return replyMapper.countNoticeReply(nno);
    }

    /**
     * 공지사항 번호 유효성 검사 서비스.
     * 공지사항 번호의 유효성을 검증합니다.
     *
     * @param nno 검증하고자 하는 공지사항 번호.
     * @throws NoticeNumberNotFoundException 해당하는 공지사항 번호가 없을 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void validateNoticeNumber(Long nno) {
        log.info("Is Running Validate Notice Number ServiceImpl");
        if (replyMapper.findNoticeNno(nno) == null || replyMapper.findNoticeNno(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }

    /**
     * 게시글의 번호 유효성 검사 서비스.
     * 게시글에 번호의 유효성을 검증합니다.
     *
     * @param bno 검증하고자 하는 게시판 번호.
     * @throws BoardNumberNotFoundException 해당하는 게시물 번호가 없을 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void validateBoardNumber(Long bno) {
        log.info("Is Running Validate Board Number ServiceImpl");
        if (replyMapper.findBoardBno(bno) == null || replyMapper.findBoardBno(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
    }

    /**
     * 공지사항 댓글 유효성 검사 서비스.
     * 공지사항 댓글 번호의 유효성을 검증합니다.
     *
     * @param rno 검증하고자 하는 공지사항 댓글 번호.
     * @throws ReplyNumberNotFoundException 해당하는 공지사항 댓글 번호가 없을 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void validateNoticeReplyNumber(Long rno) {
        log.info("Is Running Validate Notice Reply Number ServiceImpl");
        if (replyMapper.findNoticeRno(rno) == null || replyMapper.findNoticeRno(rno) == 0) {
            throw new ReplyNumberNotFoundException("해당하는 공지사항 댓글 번호가 없습니다.");
        }
    }

    /**
     * 게시글 댓글 번호 유효성 검사 서비스.
     * 게시글 댓글 번호의 유효성을 검증합니다.
     *
     * @param rno 검증하고자 하는 게시글 댓글 번호.
     * @throws ReplyNumberNotFoundException 해당하는 게시글 댓글 번호가 없을 경우 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void validateBoardReplyNumber(Long rno) {
        log.info("Is Running Validate Board Reply Number ServiceImpl");
        if (replyMapper.findBoardRno(rno) == null || replyMapper.findBoardRno(rno) == 0) {
            throw new ReplyNumberNotFoundException("해당하는 게시글 댓글 번호가 없습니다.");
        }
    }
}
