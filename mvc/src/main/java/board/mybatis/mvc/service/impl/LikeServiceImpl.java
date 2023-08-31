package board.mybatis.mvc.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import lombok.extern.log4j.Log4j2;

// Like ServiceImpl Class
@Log4j2
@Service
public class LikeServiceImpl implements LikeService {

    // 의존성 주입
    private final LikeMapper likeMapper;

    // Autowired 명시적 표시
    @Autowired
    public LikeServiceImpl(LikeMapper likeMapper) {
        log.info("Inject LikeService");
        this.likeMapper = likeMapper;
    }

    /*
     * 게시물에 대한 좋아요 토글 기능 서비스 
     * 회원의 좋아요가 있을 시 삭제 및 좋아요 수 감소 
     * 회원의 좋아요가 없을 시 생성 및 좋아요 수 증가 
     */
    @Override
    @Transactional
    public Long toggleLikeBoard(Long bno, String email) {
        log.info("Is Running Toggle Like Board ServiceImpl");
        checkBoardNumber(bno); // Check Board Number
        checkMemberEmail(email); // Check Member Email
        invalidMemberEmail(email); // invalidMember Email
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

    /*
     * 게시물에 대한 좋아요 카운트 서비스 
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public Long countLikeBoard(Long bno) {
        log.info("Is Running Count Like Board ServiceImpl");
        checkBoardNumber(bno); // Check Board Number
        return likeMapper.countLikeBoard(bno);
    }

    /*
     * 게시물의 회원 좋아요 상태 체크 서비스 
     * 트랜잭션 readOnly 
     */
    @Override
    @Transactional(readOnly = true)
    public LikeToggleBoardDTO checkToggleLikeBoard(Long bno, String email) {
        log.info("Is Running Check Toggle Like Board ServiceImpl");
        return likeMapper.checkToggleLikeBoard(email, bno);
    }

     /*
     * 공지사항에 대한 좋아요 토글 기능 서비스 
     * 회원의 좋아요가 있을 시 삭제 및 좋아요 수 감소 
     * 회원의 좋아요가 없을 시 생성 및 좋아요 수 증가 
     */
    @Override
    @Transactional
    public Long toggleLikeNotice(Long nno, String email) {
        log.info("Is Running Toggle Like Notice ServiceImpl");
        checkNoticeNumber(nno); // Check Notice Number
        checkMemberEmail(email); // Check Member Email
        invalidMemberEmail(email); // Invalid Member Email
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
            return likeMapper.incrementNoticeLike(nno);
        }
    }

   /*
     * 공자사항에 대한 좋아요 카운트 서비스 
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public Long countLikeNotice(Long nno) {
        checkNoticeNumber(nno); // Check Notice Number
        log.info("Is Running Count Like Notice ServiceImpl");
        return likeMapper.countLikeNotice(nno);
    }

    /*
     * 공지사항의 회원 좋아요 상태 체크 서비스 
     * 트랜잭션 readOnly 
     */
    @Override
    @Transactional(readOnly = true)
    public LikeToggleNoticeDTO checkToggleLikeNotice(Long nno, String emaiil) {
        log.info("Is Running Check Toggle Like Notice ServiceImpl");
        return likeMapper.checkToggleLikeNotice(emaiil, nno);
    }

    /*
     * 게시물 번호 검증 
     * 트랜잭션 readOnly 
     */
    @Transactional(readOnly = true)
    private void checkBoardNumber(Long bno) {
        log.info("Is Running Check Board Number ServiceImpl");
        if (likeMapper.findBoardNumber(bno) == null || likeMapper.findBoardNumber(bno) == 0) {
            throw new BoardNumberNotFoundException("해당하는 게시물 번호가 없습니다.");
        }
    }

    // Check Notice Number
    @Transactional(readOnly = true)
    private void checkNoticeNumber(Long nno) {
        log.info("Is RUnning Check Notice Number ServiceImpl");
        if (likeMapper.findNoticeNumber(nno) == null || likeMapper.findNoticeNumber(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }

    // Check Member Email
    @Transactional(readOnly = true)
    private void checkMemberEmail(String email) {
        log.info("Is Running Check Member Email ServiceImpl");
        if (likeMapper.findMemberEmail(email) == 0 || likeMapper.findMemberEmail(email) == null) {
            throw new MemberNotFoundException("요청하신 이메일은 회원가입 되지 않은 회원입니다.");
        }
    }

    // Check Invalid Email
    @Transactional(readOnly = true)
    private void invalidMemberEmail(String email) {
        log.info("Is Running Invalid Member Email ServiceImpl");
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new InvalidEmailException("요청하신 이메일은 형식에 맞지 않은 이메일입니다.");
        }
    }
}
