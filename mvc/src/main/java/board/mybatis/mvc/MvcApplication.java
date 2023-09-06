package board.mybatis.mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 애플리케이션의 시작점을 나타내는 클래스입니다.
 * EnableCaching 은 Redis 의 캐싱 작업을 수행하게 합니다.
 */
@EnableCaching
@SpringBootApplication
@MapperScan(basePackages = {"board.mybatis.mvc.**.mappers"})
public class MvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}
}
