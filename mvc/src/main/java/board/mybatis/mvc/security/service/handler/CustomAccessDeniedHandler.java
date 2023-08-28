package board.mybatis.mvc.security.service.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// Spring Security Access Denied Hanlder Class
@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Access Deined Method
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("Is Running AccessDenied Handler");
        log.info(accessDeniedException);
    }
}
