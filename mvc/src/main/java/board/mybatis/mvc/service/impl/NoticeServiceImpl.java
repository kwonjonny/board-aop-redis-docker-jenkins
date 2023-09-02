package board.mybatis.mvc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.mappers.NoticeMapper;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

// Notice ServiceImpl Class
@Log4j2
@Service
public class NoticeServiceImpl implements NoticeService {

    // 의존성 주입
    private final NoticeMapper noticeMapper;
    private final FileMapper fileMapper;

    /*
     * Autowired 명시적 표시
     */
    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, FileMapper fileMapper) {
        log.info("Inect NoticeMapper");
        this.noticeMapper = noticeMapper;
        this.fileMapper = fileMapper;
    }

    /*
     * 공지사항 생성 서비스
     * 부가 기능: 파일 업로드
     */
    @Override
    @Transactional
    public Long createNotice(NoticeCreateDTO noticeCreateDTO) {
        log.info("Is Running Create Notice ServiceImpl");
        if (noticeCreateDTO.getContent() == null || noticeCreateDTO.getWriter() == null
                || noticeCreateDTO.getTitle() == null) {
            throw new DataNotFoundException("공지사항 게시글, 작성자, 내용은 필수입니다.");
        }
        Long count = noticeMapper.createNotice(noticeCreateDTO);
        AtomicInteger index = new AtomicInteger(0);
        List<String> fileNames = noticeCreateDTO.getFileName();
        Long nno = noticeCreateDTO.getNno();

        if (!noticeCreateDTO.getFileName().isEmpty() && noticeCreateDTO.getFileName() != null) {
            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String[] splitData = str.split("_"); // "_" 를 기준으로 분리
                String uuid = splitData[0];
                String fileName = splitData[1];
                return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());
            fileMapper.createNoticeImage(list);
        }
        return noticeCreateDTO.getNno();
    }

    /*
     * 공지사항 조회 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public NoticeDTO readNotice(Long nno) {
        log.info("Is Running Read Notice ServiceImpl");
        validateNoticeNumber(nno); // Notice Number Check
        return noticeMapper.readNotice(nno);
    }

    /*
     * 공지사항 업데이트 서비스
     * 부가기능: 파일 업로드
     */
    @Override
    @Transactional
    public Long updateNotice(NoticeUpdateDTO noticeUpdateDTO) {
        log.info("Is Running Update Notice ServiceImpl");
        if (noticeUpdateDTO.getNno() == null || noticeUpdateDTO.getContent() == null
                || noticeUpdateDTO.getWriter() == null || noticeUpdateDTO.getTitle() == null) {
            throw new DataNotFoundException("공지사항 게시글, 작성자, 내용은 필수입니다,");
        }
        validateNoticeNumber(noticeUpdateDTO.getNno()); // Notice Number Check

        Long count = noticeMapper.updateNotice(noticeUpdateDTO);
        AtomicInteger index = new AtomicInteger(0);
        List<String> fileNames = noticeUpdateDTO.getFileName();
        Long nno = noticeUpdateDTO.getNno();

        fileMapper.deleteNoticeImage(nno);
        if (!noticeUpdateDTO.getFileName().isEmpty() && noticeUpdateDTO.getFileName() != null) {
            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String[] splitData = str.split("_"); // "_" 를 기준으로 분리
                String uuid = splitData[0];
                String fileName = splitData[1];
                return Map.of("uuid", uuid, "fileName", fileName, "nno", "" + nno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());
            fileMapper.updateNoticeImage(list);
        }
        return noticeUpdateDTO.getNno();
    }

    /*
     * 공지사항 삭제 서비스
     */
    @Override
    @Transactional
    public Long deleteNotice(Long nno) {
        log.info("Is Running Delete Notice ServiceImpl");
        validateNoticeNumber(nno); // Notice Number Check
        fileMapper.deleteNoticeImage(nno);
        return noticeMapper.deleteNotice(nno);
    }

    /*
     * 공지사항 리스트 서비스
     * 트랜잭션 readOnly
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Notice ServiceImpl");
        if (pageRequestDTO == null) {
            throw new DataNotFoundException("해당하는 공지사항 게시글 리스트가 없습니다.");
        }
        List<NoticeListDTO> list = noticeMapper.listNotice(pageRequestDTO);
        int total = noticeMapper.total(pageRequestDTO);
        return PageResponseDTO.<NoticeListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /*
     * 공지사항 조회수 증가 서비스
     */
    @Override
    @Transactional
    public int countViewNotice(Long nno) {
        log.info("Is Running Notice View Count");
        validateNoticeNumber(nno); // Check Notice Number
        if (nno == null) {
            throw new DataNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
        return noticeMapper.countViewNotice(nno);
    }

    /*
     * 공지사항 번호 검증 서비스
     * 트랜잭션 readOnly
     */
    @Transactional(readOnly = true)
    private void validateNoticeNumber(Long nno) {
        log.info("Is Running Validate Notice Number ServiceImpl");
        if (noticeMapper.findNoticeNumber(nno) == null || noticeMapper.findNoticeNumber(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }
}