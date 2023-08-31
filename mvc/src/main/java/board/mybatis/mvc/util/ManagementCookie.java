package board.mybatis.mvc.util;

import org.springframework.context.annotation.Configuration;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class ManagementCookie {

    private static final String COOKIE_NAME = "read_board_";

    /*
     * 조회수를 위한 쿠키 생성 
     */
    public boolean createCookie(HttpServletRequest request, HttpServletResponse response, Long bno) {
        String cookieName = COOKIE_NAME + bno;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    // 이미 쿠키가 존재하므로 조회수를 증가시키지 않습니다
                    return false;
                }
            }
        }
        /*
         * 쿠키 생성 
         * 24 시간 동안 쿠키 유지 후 
         * 쿠키가 없을 시 조회수 증가 하도록 true 반환 
         */
        Cookie cookie = new Cookie(cookieName, "true");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return true;
    }
}