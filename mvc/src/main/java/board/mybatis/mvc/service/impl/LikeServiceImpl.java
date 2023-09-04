package board.mybatis.mvc.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.like.boardlike.LikeToggleBoardDTO;
import board.mybatis.mvc.dto.like.noticelike.LikeToggleNoticeDTO;
import board.mybatis.mvc.exception.BoardNumberNotFoundException;
import board.mybatis.mvc.exception.InvalidEmailException;
import board.mybatis.mvc.exception.MemberNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.LikeMapper;
import board.mybatis.mvc.service.LikeService;
import board.mybatis.mvc.util.validator.MemberValidator;
import lombok.extern.log4j.Log4j2;

/**
 * 좋아요 서비스 구현 클래스.
 * 게시판 및 공지사항에 대한 좋아요 CRUD 관련 서비스를 제공합니다.
 */
@Log4j2
@Service
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    /**
     * LikeServiceImpl의 생성자.
     * LikeMapper의 의존성 주입을 수행합니다.
     * 
     * @param likeMapper 좋아요 관련 데이터 액세스 객체
     */
    @Autowired
    public LikeServiceImpl(LikeMapper likeMapper) {
        log.info("Inject LikeService");
        this.likeMapper = likeMapper;
    }

    /**
     * 게시물에 대한 좋아요 상태 토글 서비스.
     * 게시물에 대한 좋아요 상태를 토글하는 메서드.
     * 회원의 좋아요가 존재하면 삭제하고 좋아요 카운트를 감소시키며,
     * 존재하지 않으면 생성하고 카운트를 증가시킵니다.
     * 
     * @param bno   게시물 번호
     * @param email 회원 이메일
     * @return 업데이트된 좋아요 카운트
     * @throws BoardNumberNotFoundException 해당 게시물 번호가 없을 때 발생하는 예외
     * @throws InvalidEmailException        이메일 형식이 맞지 않을 때 발생하는 예외
     * @throws MemberNotFoundException      회원 이메일이 없을 때 발생하는 예외
     */
    @Override
    @Transactional
    public Long toggleLikeBoard(Long bno, String email) {
        log.info("Is Running Toggle Like Board ServiceImpl");
        validateBoardNumber(bno);
        validateMemberEmail(email);
        MemberValidator.validateEmail(email);
        LikeToggleBoardDTO likeToggleBoardDTO = LikeToggleBoardDTO.builder()
                .bno(bno)
                .email(email)
                .build();
        LikeToggleBoardDTO checkLike = likeMapper.checkToggleLikeBoard(email, bno);
        if (checkLike == null) {
            likeMapper.createLikeBoard(likeToggleBoardDTO);
            return likeMapper.incrementBoardLike(bno);
        } else {
            likeMapper.deleteLikeBoard(likeToggleBoardDTO);
            return likeMapper.decrementBoardLike(bno);
        }
    }

    /**
     * 게시물 좋아요 카운트 서비스.
     * 게시물의 좋아요 카운트를 조회하는 서비스.
     * 
     * @param bno 게시물 번호
     * @return 해당 게시물의 좋아요 카운트
     * @throws BoardNumberNotFoundException 해당 게시물 번호가 없을 때 발생하는 예외
     */
    @Override
    @Transactional(readOnly = true)
    public Long countLikeBoard(Long bno) {
        log.info("Is Running Count Like Board ServiceImpl");
        validateBoardNumber(bno);
        return likeMapper.countLikeBoard(bno);
    }

    /**
     * 회원 게시물 좋아요 상태 확인 서비스.
     * 회원의 게시물에 대한 좋아요 상태를 확인하는 메서드.
     * 
     * @param bno   게시물 번호
     * @param email 회원 이메일
     * @return 해당 게시물과 회원에 관한 좋아요 상태 DTO
     * @throws InvalidEmailException         이메일 형식이 맞지 않을 때 발생하는 예외
     * @throws NoticeNumberNotFoundException 해당 공지사항 번호가 없을 때 발생하는 예외
     */
    @Override
    @Transactional(readOnly = true)
    public LikeToggleBoardDTO checkToggleLikeBoard(Long bno, String email) {
        log.info("Is Running Check Toggle Like Board ServiceImpl");
        validateBoardNumber(bno);
        MemberValidator.validateEmail(email);
        return likeMapper.checkToggleLikeBoard(email, bno);
    }

    /**
     * 공지사항 좋아요 토글 서비스.
     * 공지사항에 대한 좋아요 상태를 토글하는 메서드.
     * 회원의 좋아요가 존재하면 삭제하고 좋아요 카운트를 감소시키며,
     * 존재하지 않으면 생성하고 카운트를 증가시킵니다.
     * 
     * @param nno   공지사항 번호
     * @param email 회원 이메일
     * @return 업데이트된 좋아요 카운트
     * @throws NoticeNumberNotFoundException 해당 공지사항 번호가 없을 때 발생하는 예외
     * @throws InvalidEmailException         이메일 형식이 맞지 않을 때 발생하는 예외
     * @throws MemberNotFoundException       회원 이메일이 없을 때 발생하는 예외
     */
    @Override
    @Transactional
    public Long toggleLikeNotice(Long nno, String email) {
        log.info("Is Running Toggle Like Notice ServiceImpl");
        validateNoticeNumber(nno);  
        validateMemberEmail(email); 
        MemberValidator.validateEmail(email); 
        LikeToggleNoticeDTO likeToggleNoticeDTO = LikeToggleNoticeDTO.builder()
                .nno(nno)
                .email(email)
                .build();
        LikeToggleNoticeDTO checkLikeNotice = likeMapper.checkToggleLikeNotice(email, nno);
        if (checkLikeNotice == null) {
            likeMapper.createLikeNotice(likeToggleNoticeDTO);
            return likeMapper.incrementNoticeLike(nno);
        } else {
            likeMapper.deleteLikeNotice(likeToggleNoticeDTO);
            return likeMapper.decrementNoticeLike(nno);
        }
    }

    /**
     * 공지사항 좋아요 카운트 조회 서비스.
     * 공지사항의 좋아요 카운트를 조회하는 메서드.
     * 
     * @param nno 공지사항 번호
     * @return 해당 공지사항의 좋아요 카운트
     * @throws NoticeNumberNotFoundException 해당 공지사항 번호가 없을 때 발생하는 예외
     */
    @Override
    @Transactional(readOnly = true)
    public Long countLikeNotice(Long nno) {
        validateNoticeNumber(nno);
        log.info("Is Running Count Like Notice ServiceImpl");
        return likeMapper.countLikeNotice(nno);
    }

    /**
     * 공지사항 회원 좋아요 상태 체크 서비스.
     * 공지사항의 회원 좋아요 상태를 체크하는 메서드.
     * 
     * @param nno    공지사항 번호
     * @param emaiil 회원 이메일
     * @return 공지사항의 좋아요 상태 정보
     * @throws InvalidEmailException         이메일 형식이 맞지 않을 때 발생하는 예외
     * @throws NoticeNumberNotFoundException 해당 공지사항 번호가 없을 때 발생하는 예외
     */
    @Override
    @Transactional(readOnly = true)
    public LikeToggleNoticeDTO checkToggleLikeNotice(Long nno, String emaiil) {
        log.info("Is Running Check Toggle Like Notice ServiceImpl");
        validateNoticeNumber(nno);
        MemberValidator.validateEmail(emaiil);
        return likeMapper.checkToggleLikeNotice(emaiil, nno);
    }

    /**
     * 게시물 번호 검증 서비스.
     * 게시물 번호를 검증하는 메서드.
     * 
     * @param bno 게시물 번호
     * @throws BoardNumberNotFoundException 해당 게시물 번호가 없을 때 발생하는 예외
     */
    @Transactional(readOnly = true)
    private void validateBoardNumber(Long bno) {
        log.info("Is Validate Board Number ServiceImpl");
        if (likeMapper.findBoardNumber(bno) == null || likeMapper.findBoardNumber(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
    }

    /**
     * 공지사항 번호 검증 서비스.
     * 공지사항 번호를 검증하는 메서드.
     * 
     * @param nno 공지사항 번호
     * @throws NoticeNumberNotFoundException 해당 공지사항 번호가 없을 때 발생하는 예외
     */
    @Transactional(readOnly = true)
    private void validateNoticeNumber(Long nno) {
        log.info("Is Validate Notice Number ServiceImpl");
        if (likeMapper.findNoticeNumber(nno) == null || likeMapper.findNoticeNumber(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }

    /**
     * 회원 이메일 검증 서비스.
     * 회원 이메일을 검증하는 메서드.
     * 
     * @param email 회원 이메일
     * @throws MemberNotFoundException 회원 이메일이 없을 때 발생하는 예외
     */
    @Transactional(readOnly = true)
    private void validateMemberEmail(String email) {
        log.info("Is Running Validate Member Email ServiceImpl");
        if (likeMapper.findMemberEmail(email) == 0 || likeMapper.findMemberEmail(email) == null) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 회원입니다.");
        }
    }
}