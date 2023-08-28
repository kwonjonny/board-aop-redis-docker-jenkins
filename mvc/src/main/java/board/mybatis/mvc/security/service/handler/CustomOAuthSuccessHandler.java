package board.mybatis.mvc.security.service.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

// Kakao Login And Normal Login Success Handler Calss
@Log4j2
public class CustomOAuthSuccessHandler implements AuthenticationSuccessHandler {

    // Kakao Login Sucess Handler 
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
                log.info("Is Running Success Handler Method");
                log.info("authentication.getPrincipal" + authentication.getPrincipal());
                
    }
    
}
