package board.mybatis.mvc.config.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

/**
 * MyBatis 설정을 담당하는 클래스입니다.
 * 주요 설정으로는 SqlSessionFactory와 SqlSessionTemplate을 설정하며,
 * MapperScan 어노테이션을 사용하여 mapper 패키지를 스캔합니다.
 */
@Log4j2
@Configuration
@MapperScan(basePackages = { "board.mybatis.mvc.**.mappers" })
public class MyBatisConfig {

    private final ApplicationContext applicationContext;

    /**
     * Spring의 ApplicationContext를 주입받아 저장합니다.
     *
     * @param applicationContext 스프링 ApplicationContext
     */
    @Autowired
    public MyBatisConfig(ApplicationContext applicationContext) {
        log.info("Inject ApplicationContext");
        this.applicationContext = applicationContext;
    }

    /**
     * SqlSessionFactory를 생성하고 구성합니다.
     * 데이터 소스와 매퍼 위치를 설정합니다.
     *
     * @param dataSource 데이터 소스
     * @return 구성된 SqlSessionFactory
     * @throws Exception SqlSessionFactoryBean의 getObject 호출 중 발생할 수 있는 예외
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "proxyDataSource") DataSource dataSource)
            throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mappers/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * SqlSessionTemplate을 생성합니다.
     * SqlSessionFactory를 주입받아 사용합니다.
     *
     * @param sqlSessionFactory SqlSessionFactory
     * @return 생성된 SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
