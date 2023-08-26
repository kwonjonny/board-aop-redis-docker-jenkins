package board.mybatis.mvc.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

// FileMapper Interface
@Mapper
public interface FileMapper {
    // Create Images
    int createImage(List<Map<String,String>> imageList);
    
    // Delete Image 
    int deleteImage(Long bno);

    // Update Image 
    int updateImage(List<Map<String,String>> iamgeList);
}
