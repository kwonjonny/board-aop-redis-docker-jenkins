package board.mybatis.mvc.main;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@CrossOrigin
@RequestMapping("/spring/")
public class MainController {
    
    // Main Page 
    @GetMapping("index")
    @PreAuthorize("permitAll")
    public void mainPage() {
        log.info("Main Page Calling");
    }
}
