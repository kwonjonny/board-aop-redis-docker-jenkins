package board.mybatis.mvc.security.service.handler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import board.mybatis.mvc.dto.member.MemberDTO;
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
        MemberDTO dto = (MemberDTO) authentication.getPrincipal();
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("email", dto.getEmail());

        // Encode the email for safe use in URL
        String encodedEmail = URLEncoder.encode(dto.getEmail(), StandardCharsets.UTF_8.toString());

        // MemberDTO를 사용 소셜 로그인에 성공했을 시(mpw가 "" 일때) 수정페이지로 가기
        if (dto.getMemberPw() == null || dto.getMemberPw().equals("")) {

            // DB에 소셜로그인 email이 없을때 Redirect Member Update Page
            response.sendRedirect("/spring/member/update/" + dto.getEmail());
            return;
        }
        // DB에 소셜로그인 email이 있으면 Redirect Member Read Page
        response.sendRedirect("/spring/member/read/" + dto.getEmail());
    }
}
