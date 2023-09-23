package board.mybatis.mvc.util.email;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmailTokenUtil {

    // 토큰 만료 시간 1 분 
    private static final int EXPIRY_DURATION = 1;

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plusMinutes(EXPIRY_DURATION);
    }

    public static boolean isTokenExpired(LocalDateTime expiryDate) {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
