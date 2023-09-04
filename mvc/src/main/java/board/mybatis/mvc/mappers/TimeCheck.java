package board.mybatis.mvc.mappers;

import org.apache.ibatis.annotations.Mapper;

/**
 * {@code TimeCheck}는 마이바티스의 시간 체크를 위한 매퍼 인터페이스입니다.
 */
@Mapper
public interface TimeCheck {

    /**
     * 현재 시간을 조회합니다.
     *
     * @return 현재 시간 문자열
     */
    String getTime();
}