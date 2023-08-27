package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

// Notice Mapper Interface
@Mapper
public interface NoticeMapper {
    // Create Notice 
    Long createNotice(NoticeCreateDTO noticeCreateDTO);

    // Read Notice 
    NoticeDTO readNotice(Long nno);

    // Update Notice 
    Long updateNotice(NoticeUpdateDTO noticeUpdateDTO);

    // Delete Notice 
    Long deleteNotice(Long nno);

    // List Notice 
    List<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    // Total Notice
    int total(PageRequestDTO pageRequestDTO);

    // Find Notice Number
    Long findNoticeNumber(Long nno);

    // Count View Notice 
    int countViewNotice(Long nno);
}
