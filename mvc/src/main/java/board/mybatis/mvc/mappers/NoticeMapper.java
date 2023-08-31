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
    List<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    /*
     * 공지사항 총 개수 
     */
    int total(PageRequestDTO pageRequestDTO);

    /*
     * 공지사항 번호 검증 
     */
    Long findNoticeNumber(Long nno);

    /*
     * 공지사항 조회수 증가 
     */
    int countViewNotice(Long nno);
}