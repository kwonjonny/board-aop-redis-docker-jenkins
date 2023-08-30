package board.mybatis.mvc.notice.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import board.mybatis.mvc.controller.NoticeController;
import board.mybatis.mvc.service.NoticeService;
import board.mybatis.mvc.util.ManagementCookie;
import lombok.extern.log4j.Log4j2;

import static org.hamcrest.Matchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Notice Controller Test Class
@Log4j2
@SpringBootTest
@AutoConfigureMockMvc // Mcok MVC 자동 설정
public class NoticeControllerTests {

    // MockMvc 의존성 주입
    @Autowired
    private MockMvc mockMvc;

    // Mockito에 noticeController에 mock 객체 (noticeservice & managementCookie) 를 주입하도록
    // 지시
    @InjectMocks
    private NoticeController noticeController;

    // NoticeService의 mock을 생성하도록 Mockitodp wltl
    @Mock
    private NoticeService noticeService;

    // ManagementCookie의 mock을 생성하도록 Mockito에 지시
    @Mock
    private ManagementCookie managementCookie;

    // 테스트 시작 전 메모리 선 참조 
    
}
