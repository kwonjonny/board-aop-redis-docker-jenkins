package board.mybatis.mvc.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * {@code FileMapper}는 게시물 및 공지사항의 이미지에 대한 데이터베이스 작업을 처리하기 위한 매퍼 인터페이스입니다.
 * MyBatis의 Mapper 어노테이션을 사용하여 데이터베이스 연동을 수행합니다.
 */
@Mapper
public interface FileMapper {
    /**
     * 게시물 이미지 생성
     *
     * @param imageList 이미지 정보를 담은 리스트
     * @return 생성된 이미지의 개수
     */
    int createImage(List<Map<String, String>> imageList);

    /**
     * 게시물 이미지 삭제
     *
     * @param bno 게시물 번호
     * @return 삭제된 이미지의 개수
     */
    int deleteImage(Long bno);

    /**
     * 게시물 이미지 업데이트
     *
     * @param imageList 이미지 정보를 담은 리스트
     * @return 업데이트된 이미지의 개수
     */
    int updateImage(List<Map<String, String>> imageList);

    /**
     * 공지사항 이미지 생성
     *
     * @param imageList 이미지 정보를 담은 리스트
     * @return 생성된 이미지의 개수
     */
    int createNoticeImage(List<Map<String, String>> imageList);

    /**
     * 공지사항 이미지 삭제
     *
     * @param nno 공지사항 번호
     * @return 삭제된 이미지의 개수
     */
    int deleteNoticeImage(Long nno);

    /**
     * 공지사항 이미지 업데이트
     *
     * @param imageList 이미지 정보를 담은 리스트
     * @return 업데이트된 이미지의 개수
     */
    int updateNoticeImage(List<Map<String, String>> imageList);
}