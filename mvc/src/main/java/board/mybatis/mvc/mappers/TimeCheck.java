package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;

// Time Check Interface 
@Mapper
public interface TimeCheck {
    
    /*
     * 마이바티스 시간 체크 
     */
    String getTime();
}
