package board.mybatis.mvc.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/**
 * {@code MainController}는 메인 페이지에 관련된 요청을 처리하는 컨트롤러 클래스입니다.
 */
@Log4j2
@Controller
@CrossOrigin
@RequestMapping("/spring/")
public class MainController {

    /**
     * 메인 페이지 요청을 처리하는 메서드입니다.
     */
    @GetMapping("index")
    public void mainPage() {
        log.info("Main Page Calling");
    }
}
