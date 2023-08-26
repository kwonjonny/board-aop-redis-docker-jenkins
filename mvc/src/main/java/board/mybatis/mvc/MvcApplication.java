package board.mybatis.mvc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// MyBatis Mapper Scan Locations 
@SpringBootApplication
@MapperScan(basePackages = {"board.mybatis.mvc.**.mappers"})
public class MvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcApplication.class, args);
	}
}
