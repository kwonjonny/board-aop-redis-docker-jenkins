package board.mybatis.mvc.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

// FileMapper Interface
@Mapper
public interface FileMapper {
    /*
     * 게시물 이미지 생성 
     */
    int createImage(List<Map<String,String>> imageList);
    
    /*
     * 게시물 이미지 삭제 
     */
    int deleteImage(Long bno);

    /*
     * 게시물 이미지 업데이트 
     */
    int updateImage(List<Map<String,String>> iamgeList);

    /*
     * 공지사항 이미지 생성 
     */
    int createNoticeImage(List<Map<String,String>> iamgeList);

    /*
     * 공지사항 이미지 삭제 
     */
    int deleteNoticeImage(Long nno);

    /*
     * 공지사항 이미지 업데이트 
     */
    int updateNoticeImage(List<Map<String,String>> iamgeList);
}