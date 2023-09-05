package board.mybatis.mvc.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.log4j.Log4j2;

/**
 * 회원 정보를 가져오는 유틸리티 클래스.
 */
@Log4j2
public class CurrentMember {

    /**
     * 현재 인증된 사용자의 이메일 주소를 가져옵니다.
     * 
     * @return 현재 인증된 사용자의 이메일 주소
     */
    public static String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증되지 않은 사용자의 경우 null을 반환하도록 할 수도 있습니다.
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return null;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
