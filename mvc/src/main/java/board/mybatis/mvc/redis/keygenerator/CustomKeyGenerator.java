package board.mybatis.mvc.redis.keygenerator;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import board.mybatis.mvc.dto.board.BoardUpdateDTO;
import board.mybatis.mvc.dto.member.MemberUpdateDTO;
import board.mybatis.mvc.dto.notice.NoticeUpdateDTO;
import board.mybatis.mvc.util.security.CurrentMember;
import lombok.extern.log4j.Log4j2;

/**
 * CustomKeyGenerator는 사용자 정의 캐시 키 생성기로, Redis 캐싱에서 사용됩니다.
 * 
 * 이 클래스는 Spring의 {@link KeyGenerator} 인터페이스를 구현하여 캐시 저장시 사용될 키의 형식을 지정합니다.
 * 생성된 키는 현재 사용자의 이메일 주소와 주어진 파라미터를 기반으로 합니다.
 */
@Log4j2
@Component("Key_Generator")
public class CustomKeyGenerator implements KeyGenerator {

    /**
     * 사용자 정의 캐시 키를 생성하는 메서드입니다.
     *
     * 이 메서드는 현재 사용자의 이메일 주소와 첫 번째 파라미터를 연결하여 캐시 키를 생성합니다.
     * 예를 들어, 현재 사용자의 이메일이 "user@example.com"이고 첫 번째 파라미터가 "paramValue"인 경우,
     * 생성된 키는 "user@example.com_paramValue"가 됩니다.
     *
     * @param target 대상 객체
     * @param method 실행 중인 메서드
     * @param params 메서드에 전달된 파라미터들
     * @return String 사용자 정의 캐시 키
     */
    @Override
    public Object generate(final Object target, final Method method, final Object... params) {
        String paramValue;
        if (params[0] instanceof MemberUpdateDTO) {
            paramValue = ((MemberUpdateDTO) params[0]).getEmail();
        } else if (params[0] instanceof BoardUpdateDTO) {
            paramValue = ((BoardUpdateDTO) params[0]).getBno().toString();
        } else if (params[0] instanceof NoticeUpdateDTO) {
            paramValue = ((NoticeUpdateDTO) params[0]).getNno().toString();
        } else {
            paramValue = params[0].toString();
        }
        return CurrentMember.getCurrentUserEmail() + "_" + paramValue;
    }
}