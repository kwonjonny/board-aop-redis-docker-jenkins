package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;

// Time Check Interface 
@Mapper
public interface TimeCheck {
    
    // Get Time 
    String getTime();
}
