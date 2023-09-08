package board.mybatis.mvc.config.mybatis;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import lombok.extern.log4j.Log4j2;

/**
 * MyBatis를 위한 라우팅 데이터 소스 구현입니다.
 * 현재의 트랜잭션 상태를 기반으로 마스터 또는 슬레이브 데이터 소스를 결정합니다.
 * 읽기 전용 트랜잭션인 경우 슬레이브를, 그렇지 않은 경우 마스터를 반환합니다.
 */
@Log4j2
public class MyBatisRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 현재의 트랜잭션 상태를 기반으로 데이터 소스의 키를 결정합니다.
     * 
     * @return "slave" 또는 "master" 중 하나의 문자열. 읽기 전용 트랜잭션인 경우 "slave", 그렇지 않으면
     *         "master"
     */
    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        Object lookupKey = isReadOnly ? "slave" : "master";
        log.info("Current DataSource is {}", lookupKey);
        return isReadOnly ? "slave" : "master";
    }
}
