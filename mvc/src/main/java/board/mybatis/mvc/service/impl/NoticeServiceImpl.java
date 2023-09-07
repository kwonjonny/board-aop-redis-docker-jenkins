package board.mybatis.mvc.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.annotation.redis.KwonCacheEvict;
import board.mybatis.mvc.annotation.redis.KwonCacheable;
import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.exception.DataNotFoundException;
import board.mybatis.mvc.exception.NoticeNumberNotFoundException;
import board.mybatis.mvc.mappers.FileMapper;
import board.mybatis.mvc.mappers.NoticeMapper;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;
import lombok.extern.log4j.Log4j2;

/**
 * 공지사항 서비스 구현 클래스.
 * 공지사항에 대한 CRUD 관련 서비스를 제공합니다.
 */
@Log4j2
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper noticeMapper;
    private final FileMapper fileMapper;

    /**
     * NoticeServiceImpl 생성자.
     * noticeMapper 의존성 주입을 수행합니다.
     * fileMapper 의존성 주입을 수행합니다.
     * 
     * @param noticeMapper 공지사항 관련 데이터 엑세스 객체
     * @param fileMapper   파일 업로드 관련 데이터 엑세스 객체
     */
    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, FileMapper fileMapper) {
        log.info("Inect NoticeMapper");
        this.noticeMapper = noticeMapper;
        this.fileMapper = fileMapper;
    }

    /**
     * 새로운 공지사항 생성 서비스 메서드.
     * 부가 기능: 파일업로드.
     * 
     * @param noticeCreateDTO 생성할 공지사항의 세부 정보를 포함하는 DTO.
     * @return 생성된 공지사항의 ID 반환.
     * @throws DataNotFoundException 필수 데이터가 누락된 경우 발생하는 예외.
     */
    @Override
    @Transactional
    public Long createNotice(NoticeCreateDTO noticeCreateDTO) {
        log.info("Is Running Create Notice ServiceImpl");
        if (noticeCreateDTO.getContent() == null || noticeCreateDTO.getWriter() == null
                || noticeCreateDTO.getTitle() == null) {
            throw new DataNotFoundException("공지사항 게시글, 작성자, 내용은 필수입니다.");
        }
        noticeMapper.createNotice(noticeCreateDTO);
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

    /**
     * 공지사항 조회 서비스.
     * 해당 게시물 정보는 캐시에서 조회하며, 캐시에 없는 경우 DB에서 가져와 캐시에 저장합니다.
     * 캐시 키 생성은 "Key_Generator"를 사용합니다.
     * 
     * @param nno 조회할 공지사항의 번호.
     * @return 조회된 공지사항의 정보를 담고 있는 DTO.
     * @throws NoticeNumberNotFoundException 해당 번호의 공지사항이 없을 때 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    @KwonCacheable(value = "Notice", key = "")
    public NoticeDTO readNotice(Long nno) {
        log.info("Is Running Read Notice ServiceImpl");
        validateNoticeNumber(nno);
        return noticeMapper.readNotice(nno);
    }

    /**
     * 공지사항 수정 서비스 메서드.
     * 부가기능: 파일업로드.
     * 해당 게시물 정보는 캐시에서 제거됩니다. "Key_Generator"를 사용하여 캐시 키를 생성하여 해당 게시물 정보를 캐시에서
     * 제거합니다.
     * 
     * @param noticeUpdateDTO 업데이트 할 공지사항의 세부 정보를 포함하는 DTO.
     * @return 업데이트 된 공지사항의 ID 반환.
     * @throws DataNotFoundException         필수 데이터가 누락된 경우 발생하는 예외.
     * @throws NoticeNumberNotFoundException 해당 번호의 공지사항이 없을 때 발생하는 예외.
     */
    @Override
    @Transactional
    @KwonCacheEvict(value = "Notice", key = "")
    public Long updateNotice(NoticeUpdateDTO noticeUpdateDTO) {
        log.info("Is Running Update Notice ServiceImpl");
        if (noticeUpdateDTO.getNno() == null || noticeUpdateDTO.getContent() == null
                || noticeUpdateDTO.getWriter() == null || noticeUpdateDTO.getTitle() == null) {
            throw new DataNotFoundException("공지사항 게시글, 작성자, 내용은 필수입니다,");
        }
        validateNoticeNumber(noticeUpdateDTO.getNno());

        noticeMapper.updateNotice(noticeUpdateDTO);
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

    /**
     * 공지사항 삭제 서비스.
     * 해당 게시물 정보는 캐시에서 제거됩니다. "Key_Generator"를 사용하여 캐시 키를 생성하여 해당 게시물 정보를 캐시에서
     * 제거합니다.
     * 
     * @param nno 삭제할 공지사항의 번호.
     * @return 삭제된 공지사항의 번호 반환.
     * @throws NoticeNumberNotFoundException 해당 번호의 공지사항이 없을 때 발생하는 예외.
     */
    @Override
    @Transactional
    @CacheEvict(value = "Notice", key = "")
    public Long deleteNotice(Long nno) {
        log.info("Is Running Delete Notice ServiceImpl");
        validateNoticeNumber(nno);
        fileMapper.deleteNoticeImage(nno);
        return noticeMapper.deleteNotice(nno);
    }

    /**
     * 공지사항 리스트 조회 서비스.
     * 
     * @param pageRequestDTO 페이지 요청 정보를 포함하는 DTO.
     * @return 해당 페이지의 공지사항 리스트와 관련 정보를 담고 있는 응답 DTO.
     * @throws DataNotFoundException 해당하는 공지사항 리스트가 없을 때 발생하는 예외.
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO) {
        log.info("Is Running List Notice ServiceImpl");
        List<NoticeListDTO> list = noticeMapper.listNotice(pageRequestDTO);
        int total = noticeMapper.total(pageRequestDTO);
        return PageResponseDTO.<NoticeListDTO>withAll()
                .list(list)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }

    /**
     * 공지사항 조회수 증가 서비스.
     * 
     * @param nno 조회수를 증가시킬 공지사항의 번호.
     * @return 증가된 조회수 값 반환.
     * @throws DataNotFoundException         해당하는 공지사항 번호가 없을 때 발생하는 예외.
     * @throws NoticeNumberNotFoundException 해당 번호의 공지사항이 없을 때 발생하는 예외.
     */
    @Override
    @Transactional
    public int countViewNotice(Long nno) {
        log.info("Is Running Notice View Count");
        validateNoticeNumber(nno);
        noticeMapper.createViewNoticeCount(nno);
        return noticeMapper.countViewNotice(nno);
    }

    /**
     * 공지사항 번호 검증 서비스.
     * 
     * @param nno 검증할 공지사항의 번호.
     * @throws NoticeNumberNotFoundException 해당 번호의 공지사항이 없을 때 발생하는 예외.
     */
    @Transactional(readOnly = true)
    private void validateNoticeNumber(Long nno) {
        log.info("Is Running Validate Notice Number ServiceImpl");
        if (noticeMapper.findNoticeNumber(nno) == null || noticeMapper.findNoticeNumber(nno) == 0) {
            throw new NoticeNumberNotFoundException("해당하는 공지사항 번호가 없습니다.");
        }
    }
}