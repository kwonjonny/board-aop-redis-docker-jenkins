package board.mybatis.mvc.config.mybatis;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 마스터와 슬레이브 데이터 소스 간의 데이터베이스 연결을 라우팅 관리하는 클래스입니다.
 * AbstractRoutingDataSource를 확장하여 현재 조회 키를 결정합니다.
 */
@Configuration
@EnableTransactionManagement
// @PropertySource(value = { "classpath:/application.docker.properties" })
public class MyBatisDataSourceConfig {

    /**
     * 마스터 데이터 소스를 구성하고 반환합니다.
     *
     * @return 마스터 데이터 소스
     */
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 슬레이브 데이터 소스를 구성하고 반환합니다.
     *
     * @return 슬레이브 데이터 소스
     */
    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 라우팅 데이터 소스를 구성하고 반환합니다.
     * 마스터와 슬레이브 데이터 소스를 대상 데이터 소스로 설정합니다.
     *
     * @param masterDataSource 마스터 데이터 소스
     * @param slaveDataSource  슬레이브 데이터 소스
     * @return 라우팅 데이터 소스
     */
    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(@Qualifier(value = "masterDataSource") DataSource masterDataSource,
            @Qualifier(value = "slaveDataSource") DataSource slaveDataSource) {

        AbstractRoutingDataSource routingDataSource = new MyBatisRoutingDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();

        targetDataSources.put("master", masterDataSource);
        targetDataSources.put("slave", slaveDataSource);

        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    /**
     * 프록시 데이터 소스를 구성하고 반환합니다.
     * 지연 연결 데이터 소스 프록시를 사용하여 실제 데이터베이스 연결이 필요할 때까지 연결을 지연시킵니다.
     *
     * @param routingDataSource 라우팅 데이터 소스
     * @return 프록시 데이터 소스
     */
    @Bean(name = "proxyDataSource")
    public DataSource proxyDataSource(@Qualifier(value = "routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    /**
     * 트랜잭션 관리자를 구성하고 반환합니다.
     *
     * @param dataSource 프록시 데이터 소스
     * @return 플랫폼 트랜잭션 관리자
     */
    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier(value = "proxyDataSource") DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
