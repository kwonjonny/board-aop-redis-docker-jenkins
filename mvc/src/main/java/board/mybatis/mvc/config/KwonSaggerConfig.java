package board.mybatis.mvc.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

// Swagger Documnet Class
//http://localhost:8084/swagger-ui/index.html#/
@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Joonny Swagger", version = "v1"))
public class KwonSaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = { "/**" };

        return GroupedOpenApi.builder()
                .group("Joon OPEN API v1")
                .pathsToMatch(paths)
                .build();
    }
}