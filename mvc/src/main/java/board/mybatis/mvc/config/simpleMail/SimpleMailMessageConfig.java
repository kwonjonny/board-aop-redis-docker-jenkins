package board.mybatis.mvc.config.simpleMail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleMailMessageConfig {
    @Bean
    public SimpleMailMessageConfig simpleMailMessage() {
        return new SimpleMailMessageConfig();
    }
}
