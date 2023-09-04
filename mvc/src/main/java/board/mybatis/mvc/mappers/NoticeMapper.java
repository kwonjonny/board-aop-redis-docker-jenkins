package board.mybatis.mvc.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.dto.notice.NoticeDTO;
import board.mybatis.mvc.dto.notice.NoticeListDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.PageRequestDTO;

/**
 * {@code NoticeMapper}는 공지사항 정보에 접근하는 매퍼 인터페이스입니다.
 */
@Mapper
public interface NoticeMapper {
    /**
     * 공지사항을 생성합니다.
     *
     * @param noticeCreateDTO 생성할 공지사항 정보를 담은 DTO
     * @return 생성된 공지사항의 ID
     */
    Long createNotice(NoticeCreateDTO noticeCreateDTO);

    /**
     * 특정 공지사항의 정보를 조회합니다.
     *
     * @param nno 조회할 공지사항의 번호
     * @return 조회된 공지사항 정보를 포함한 DTO
     */
    NoticeDTO readNotice(Long nno);

    /**
     * 공지사항 정보를 업데이트합니다.
     *
     * @param noticeUpdateDTO 업데이트할 공지사항 정보를 담은 DTO
     * @return 업데이트된 공지사항의 ID
     */
    Long updateNotice(NoticeUpdateDTO noticeUpdateDTO);

    /**
     * 공지사항을 삭제합니다.
     *
     * @param nno 삭제할 공지사항의 번호
     * @return 삭제된 공지사항의 ID
     */
    Long deleteNotice(Long nno);

    /**
     * 공지사항 리스트를 조회합니다.
     *
     * @param pageRequestDTO 페이지 요청 정보를 담은 DTO
     * @return 공지사항 목록 정보를 포함한 DTO 리스트
     */
    List<NoticeListDTO> listNotice(PageRequestDTO pageRequestDTO);

    /**
     * 공지사항의 총 개수를 조회합니다.
     *
     * @param pageRequestDTO 페이지 요청 정보를 담은 DTO
     * @return 공지사항의 총 개수
     */
    int total(PageRequestDTO pageRequestDTO);

    /**
     * 공지사항 번호의 존재 여부를 검증합니다.
     *
     * @param nno 검증할 공지사항 번호
     * @return 검증 결과를 나타내는 값 (존재하면 해당 공지사항의 ID, 없으면 null)
     */
    Long findNoticeNumber(Long nno);

    /**
     * 특정 공지사항의 조회수를 증가시킵니다.
     *
     * @param nno 조회수를 증가시킬 공지사항의 번호
     * @return 조회수 증가 후의 값
     */
    int countViewNotice(Long nno);

    /**
     * 조회수 테이블에 조회수를 생성합니다.
     *
     * @param nno 조회수를 생성할 공지사항의 번호
     * @return 생성된 조회수의 ID
     */
    Long createViewNoticeCount(Long nno);
}