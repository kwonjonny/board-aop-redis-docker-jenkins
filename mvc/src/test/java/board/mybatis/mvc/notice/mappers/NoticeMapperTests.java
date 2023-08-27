package board.mybatis.mvc.notice.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import board.mybatis.mvc.dto.notice.NoticeCreateDTO;
import board.mybatis.mvc.mappers.NoticeMapper;
import lombok.extern.log4j.Log4j2;

// Notice Mapper Test Class
@Log4j2
@SpringBootTest
public class NoticeMapperTests {
    
    // 의존성 주입 
    @Autowired(required = false)
    private NoticeMapper noticeMapper;

    // 테스트가 시작시 Static 으로 메모리 선 참조 
    private static final String JUNIT_TEST_TITLE = "Junit_Test_Title";
    private static final String JUNIT_TEST_CONTENT = "Junit_Test_Content";
    private static final String JUNNIT_TEST_WRITER = "Junit_Test_Writer";
    private static final String JUNIT_TEST_FILE_NAME = "Junit_Test_File_Name.jpg";

    // BeforeEach 사용을 위한 DTO 정의 
    private NoticeCreateDTO noticeCreateDTO;
    private NoticeUpdateDTO noticeUpdateDTO;

}
