package board.mybatis.mvc.config.simplemail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * 이 클래스는 SimpleMailMessage 객체를 Spring IoC 컨테이너에 등록하기 위한 설정 클래스입니다.
 * SimpleMailMessage는 Spring에서 제공하는 간단한 이메일 구성 객체입니다.
 * 이 설정을 통해 SimpleMailMessage 객체가 애플리케이션에서 자동으로 사용될 수 있습니다.
 */
@Configuration
public class SimpleMailMessageConfig {

    /**
     * SimpleMailMessage 객체를 생성하고 Spring IoC 컨테이너에 빈으로 등록합니다.
     * 
     * @return SimpleMailMessage 객체
     */
    @Bean
    public SimpleMailMessage simpleMailMessage() {
        return new SimpleMailMessage();
    }
}
