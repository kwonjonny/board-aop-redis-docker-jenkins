package board.mybatis.mvc.timecheck;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import board.mybatis.mvc.mappers.TimeCheck;
import lombok.extern.log4j.Log4j2;

// Time Check Class
@Log4j2
@SpringBootTest
public class TimeCheckTests {
    
    // 의존성 주입 
    @Autowired(required = false)
    private TimeCheck timeCheck;

    // Time Check Test 
    @Test
    public void timeCheckTest() {
        log.info("=== Start Time Check ===");
        log.info("현재 시간: "+timeCheck.getTime());
        log.info("=== End Time Check ===");
    }
}
