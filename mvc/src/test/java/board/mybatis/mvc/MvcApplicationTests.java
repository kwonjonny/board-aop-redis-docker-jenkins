package board.mybatis.mvc;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

// Connection Test Class
@Log4j2
@SpringBootTest
class MvcApplicationTests {

	// 의존성 주입
	@Autowired
	@Qualifier("slaveDataSource")
	private DataSource dataSource;

	// Database Connection Test
	@Test
	public void connectionTest() {
		try (Connection connection = dataSource.getConnection()) {
			log.info("Let's Do It");
		} catch (Exception e) {
			log.info("Find Some Errors Your Connection Is Not Ok");
		}
	}
}
