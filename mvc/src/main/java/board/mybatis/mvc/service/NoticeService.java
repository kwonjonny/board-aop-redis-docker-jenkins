package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;
import board.mybatis.mvc.util.PageResponseDTO;

// Notice Service Interface
public interface NoticeService {
    // Create Notice 
    Long createNotice(NoticeCreateDTO noticeCreateDTO);

    // Read Notice 
    NoticeDTO readNotice(Long nno);

    // Update Notice
    Long updateNotice(NoticeUpdateDTO noticeUpdateDTO);

    // Delete Notice 
    Long deleteNotice(Long nno);

    // List Notice 
    PageResponseDTO<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    // Find Notice Number
    void findNoticeNumber(Long nno);

    // Count View Notice 
    int countViewNotice(Long nno);
}
