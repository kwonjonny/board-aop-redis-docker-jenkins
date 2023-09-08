package board.mybatis.mvc.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

/**
 * {@code KwonSwaggerConfig}는 Swagger 문서를 설정하는 클래스입니다.
 * Swagger를 통해 API 문서를 생성하고 확인할 수 있습니다.
 * http://localhost:8084/swagger-ui/index.html#/
 */
@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(info = @Info(title = "Joonny Swagger", version = "v1"))
public class KwonSaggerConfig {

    /**
     * {@code chatOpenApi()} 메서드는 Swagger 그룹 설정을 생성하여 반환합니다.
     *
     * @return 생성된 Swagger 그룹 설정
     */
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = { "/**" };

        return GroupedOpenApi.builder()
                .group("Joon OPEN API v1")
                .pathsToMatch(paths)
                .build();
    }
}