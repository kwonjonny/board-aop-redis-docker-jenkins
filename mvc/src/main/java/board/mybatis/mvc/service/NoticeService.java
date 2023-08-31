package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Notice Service Interface
public interface NoticeService {
    /*
     * 공지사항 생성 
     */
    Long createNotice(NoticeCreateDTO noticeCreateDTO);

    /*
     * 공지사항 조회 
     */
    NoticeDTO readNotice(Long nno);

    /*
     * 공지사항 업데이트 
     */
    Long updateNotice(NoticeUpdateDTO noticeUpdateDTO);

    /*
     * 공지사항 삭제 
     */
    Long deleteNotice(Long nno);

    /*
     * 공지사항 리스트 
     */
    PageResponseDTO<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    /*
     * 공지사항 조회수 증가 
     */
    int countViewNotice(Long nno);
}
