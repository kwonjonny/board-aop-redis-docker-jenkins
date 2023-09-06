package board.mybatis.mvc.service;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.page.PageRequestDTO;
import board.mybatis.mvc.util.page.PageResponseDTO;

/**
 * 공지사항 관련 서비스의 인터페이스입니다.
 */
public interface NoticeService {

    /**
     * 공지사항을 생성합니다.
     *
     * @param noticeCreateDTO 생성할 공지사항 정보가 담긴 DTO.
     * @return 생성된 공지사항의 고유 번호를 반환합니다.
     */
    Long createNotice(NoticeCreateDTO noticeCreateDTO);

    /**
     * 공지사항을 조회합니다.
     *
     * @param nno 조회할 공지사항의 고유 번호.
     * @return 조회된 공지사항 정보를 담은 DTO.
     */
    NoticeDTO readNotice(Long nno);

    /**
     * 공지사항을 업데이트합니다.
     *
     * @param noticeUpdateDTO 업데이트할 공지사항 정보가 담긴 DTO.
     * @return 업데이트된 공지사항의 고유 번호를 반환합니다.
     */
    Long updateNotice(NoticeUpdateDTO noticeUpdateDTO);

    /**
     * 공지사항을 삭제합니다.
     *
     * @param nno 삭제할 공지사항의 고유 번호.
     * @return 삭제된 공지사항의 고유 번호를 반환합니다.
     */
    Long deleteNotice(Long nno);

    /**
     * 공지사항 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 정보가 담긴 DTO.
     * @return 페이지에 해당하는 공지사항 리스트와 페이징 정보를 담은 DTO.
     */
    PageResponseDTO<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    /**
     * 공지사항의 조회수를 증가시킵니다.
     *
     * @param nno 조회수를 증가시킬 공지사항의 고유 번호.
     * @return 조회수가 증가된 후의 조회수를 반환합니다.
     */
    int countViewNotice(Long nno);
}